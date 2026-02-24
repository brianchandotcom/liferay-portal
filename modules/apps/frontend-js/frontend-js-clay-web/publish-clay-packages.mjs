/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-console */

/**
 * Usage:
 *   node publish-clay-packages.js --target-version=<version>
 *
 * Optional flags:
 *   --dry-run          Preview package changes only; skips build and publish
 *   --preview-changes  Preview package.json version/dependency updates only
 *
 * Optional env vars:
 *   NPM_TAG=latest|next        Publish dist-tag (defaults to latest)
 *   SKIP_VERSION_BUMP=true     Skip rewriting versions in package.json files
 *   DRY_RUN=true               Same behavior as --dry-run
 *   PREVIEW_CHANGES=true       Same behavior as --preview-changes
 *
 * Notes:
 *   - Requires existing npm auth (`npm whoami` must succeed)
 *   - Applies publish-only package.json entry fields temporarily per package
 *     and restores local files after each publish
 */

import execa from 'execa';
import fs from 'fs';
import path from 'path';
import {fileURLToPath} from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const ROOT_DIR = __dirname;
const CLAY_DIR = path.join(ROOT_DIR, 'clay');
const UNIFIED_CHANGELOG_PATH = path.join(CLAY_DIR, 'CHANGELOG.md');
const COMMIT_LINK_BASE = 'https://github.com/liferay/liferay-portal/commit';

const NPM_TAG = process.env.NPM_TAG || 'latest';
const SKIP_VERSION_BUMP = process.env.SKIP_VERSION_BUMP === 'true';

let DRY_RUN = process.env.DRY_RUN === 'true';
let PREVIEW_CHANGES = process.env.PREVIEW_CHANGES === 'true';
let TARGET_VERSION = '';

const usage =
	'Usage: node publish-clay-packages.js --target-version=<version> [--dry-run] [--preview-changes]';

for (const arg of process.argv.slice(2)) {
	if (arg.startsWith('--target-version=')) {
		TARGET_VERSION = arg.slice('--target-version='.length).trim();
		continue;
	}

	switch (arg) {
		case '--dry-run':
			DRY_RUN = true;
			break;
		case '--preview-changes':
			PREVIEW_CHANGES = true;
			break;
		default:
			console.error(`ERROR: Unknown argument '${arg}'`);
			console.error(usage);
			process.exit(1);
	}
}

if (!TARGET_VERSION) {
	console.error('ERROR: --target-version is required');
	console.error(usage);
	process.exit(1);
}

if (!fs.existsSync(CLAY_DIR) || !fs.statSync(CLAY_DIR).isDirectory()) {
	console.error(`ERROR: Expected clay directory at ${CLAY_DIR}`);
	process.exit(1);
}

const packageDirs = fs
	.readdirSync(CLAY_DIR, {withFileTypes: true})
	.filter(
		(entry) =>
			entry.isDirectory() &&
			entry.name.startsWith('clay-') &&
			entry.name !== 'clay-charts' &&
			fs.existsSync(path.join(CLAY_DIR, entry.name, 'package.json'))
	)
	.map((entry) => path.join(CLAY_DIR, entry.name))
	.sort((a, b) => a.localeCompare(b));

if (!packageDirs.length) {
	console.error('ERROR: No clay-* package directories found');
	process.exit(1);
}

async function run(command, args, options = {}) {
	const result = await execa(command, args, {
		cwd: options.cwd,
		env: options.env || process.env,
		reject: !options.allowFailure,
		stdio: options.stdio || 'inherit',
	});

	return {
		status: result.exitCode,
		stdout: result.stdout,
	};
}

function packageJsonPath(dir) {
	return path.join(dir, 'package.json');
}

function readJson(filePath) {
	return JSON.parse(fs.readFileSync(filePath, 'utf8'));
}

function writeJson(filePath, data) {
	fs.writeFileSync(filePath, `${JSON.stringify(data, null, '\t')}\n`);
}

function escapeRegExp(value) {
	return value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

function todayIsoDate() {
	return new Date().toISOString().slice(0, 10);
}

function isReleaseCommitSubject(subject) {
	const normalizedSubject = subject.trim();

	return (
		/\bRelease clay v\d+\.\d+\.\d+\b/i.test(normalizedSubject) ||
		/\bpublish\s+v?\d+\.\d+\.\d+\b/i.test(normalizedSubject)
	);
}

function insertAfterUnifiedChangelogIntro(content, section) {
	const lines = content.split('\n');

	if (lines[0].trim() !== '# Change Log') {
		return `${section}\n${content}`;
	}

	let index = 1;

	while (index < lines.length && lines[index].trim() === '') {
		index += 1;
	}

	while (index < lines.length && lines[index].trim() !== '') {
		index += 1;
	}

	while (index < lines.length && lines[index].trim() === '') {
		index += 1;
	}

	const before = lines.slice(0, index).join('\n').replace(/\s*$/, '');
	const after = lines.slice(index).join('\n').replace(/^\s*/, '');

	if (!after) {
		return `${before}\n\n${section}\n`;
	}

	return `${before}\n\n${section}\n\n${after}\n`;
}

async function getClayReleaseBoundaryCommit() {
	const history = await run(
		'git',
		['log', '--pretty=%H%x09%s', '--', 'clay'],
		{cwd: ROOT_DIR, stdio: 'pipe'}
	);

	const lines = (history.stdout || '')
		.split('\n')
		.map((line) => line.trim())
		.filter(Boolean);

	for (const line of lines) {
		const [hash, ...subjectParts] = line.split('\t');
		const subject = subjectParts.join('\t').trim();

		if (!hash || !subject) {
			continue;
		}

		if (isReleaseCommitSubject(subject)) {
			return hash;
		}
	}

	throw new Error(
		'Unable to find prior Clay release commit in git history. Expected a commit subject containing "Release clay vX.Y.Z" or "publish X.Y.Z".'
	);
}

async function getPackageCommitsSinceBoundary(boundaryCommitHash, packageDir) {
	const packagePath = path.relative(ROOT_DIR, packageDir);
	const result = await run(
		'git',
		[
			'log',
			'--reverse',
			'--pretty=%H%x09%s',
			`${boundaryCommitHash}..HEAD`,
			'--',
			packagePath,
		],
		{cwd: ROOT_DIR, stdio: 'pipe'}
	);

	return (result.stdout || '')
		.split('\n')
		.map((line) => line.trim())
		.filter(Boolean)
		.map((line) => {
			const [hash, ...subjectParts] = line.split('\t');
			const subject = subjectParts.join('\t').trim();

			return {
				hash,
				subject,
			};
		})
		.filter(({hash, subject}) => hash && subject)
		.filter(({subject}) => !isReleaseCommitSubject(subject));
}

function renderUnifiedChangelogSection(targetVersion, packageChanges) {
	const date = todayIsoDate();
	const lines = [`# [${targetVersion}] (${date})`, ''];

	for (const {commits, packageName} of packageChanges) {
		lines.push(`## ${packageName}`);
		lines.push('');
		lines.push('### Commits');
		lines.push('');

		for (const commit of commits) {
			const shortHash = commit.hash.slice(0, 7);
			lines.push(
				`- ${commit.subject} ([${shortHash}](${COMMIT_LINK_BASE}/${commit.hash}))`
			);
		}

		lines.push('');
	}

	return lines.join('\n').trimEnd();
}

function updateUnifiedChangelog(targetVersion, section, previewOnly) {
	if (!fs.existsSync(UNIFIED_CHANGELOG_PATH)) {
		const createdContent =
			'# Change Log\n\nAll notable changes to Clay packages are documented in this file.\n\n';

		if (previewOnly) {
			console.log(`PREVIEW ${UNIFIED_CHANGELOG_PATH}`);
			console.log(section);

			return;
		}

		fs.writeFileSync(UNIFIED_CHANGELOG_PATH, `${createdContent}${section}\n`);

		return;
	}

	const current = fs.readFileSync(UNIFIED_CHANGELOG_PATH, 'utf8');
	const targetVersionHeaderPattern = new RegExp(
		`^# \\[(?:${escapeRegExp(targetVersion)})\\]`,
		'm'
	);

	if (targetVersionHeaderPattern.test(current)) {
		console.log(
			`Unified changelog already contains ${targetVersion}; skipping changelog update`
		);

		return;
	}

	if (previewOnly) {
		console.log(`PREVIEW ${UNIFIED_CHANGELOG_PATH}`);
		console.log(section);

		return;
	}

	const updated = insertAfterUnifiedChangelogIntro(current, section);
	fs.writeFileSync(UNIFIED_CHANGELOG_PATH, updated);
}

async function updateUnifiedChangelogForTargetVersion() {
	const boundaryCommitHash = await getClayReleaseBoundaryCommit();
	const packageChanges = [];

	for (const dir of packageDirs) {
		const pkgPath = packageJsonPath(dir);
		const pkg = readJson(pkgPath);
		const packageName = pkg.name || '';

		if (pkg.private || !packageName.startsWith('@clayui/')) {
			continue;
		}

		const commits = await getPackageCommitsSinceBoundary(boundaryCommitHash, dir);

		if (!commits.length) {
			continue;
		}

		packageChanges.push({
			commits,
			packageName,
		});
	}

	packageChanges.sort((a, b) => a.packageName.localeCompare(b.packageName));

	if (!packageChanges.length) {
		console.log(
			`No package changes detected since release boundary ${boundaryCommitHash}`
		);

		return;
	}

	const section = renderUnifiedChangelogSection(TARGET_VERSION, packageChanges);

	updateUnifiedChangelog(TARGET_VERSION, section, PREVIEW_CHANGES);
}

const temporaryPackageJsonBackups = new Map();

function restoreTemporaryPackageJsons() {
	for (const [filePath, contents] of temporaryPackageJsonBackups.entries()) {
		fs.writeFileSync(filePath, contents);
	}

	temporaryPackageJsonBackups.clear();
}

process.on('exit', restoreTemporaryPackageJsons);
process.on('SIGINT', () => {
	restoreTemporaryPackageJsons();
	process.exit(130);
});
process.on('SIGTERM', () => {
	restoreTemporaryPackageJsons();
	process.exit(143);
});

function applyPublishPackageFields(pkg) {
	if (pkg.name !== '@clayui/css') {
		pkg.main = 'lib/cjs/index.js';
		pkg.module = 'lib/esm/index.js';
		pkg.exports = {
			'.': {
				types: './lib/index.d.ts',

				// eslint-disable-next-line sort-keys
				import: './lib/esm/index.js',
				require: './lib/cjs/index.js',
			},
		};
		pkg.types = 'lib/index.d.ts';
		pkg['ts:main'] = 'src/index.tsx';
	}
}

function writePublishPackageJson(pkgPath) {
	if (!temporaryPackageJsonBackups.has(pkgPath)) {
		temporaryPackageJsonBackups.set(
			pkgPath,
			fs.readFileSync(pkgPath, 'utf8')
		);
	}

	const pkg = readJson(pkgPath);
	applyPublishPackageFields(pkg);
	writeJson(pkgPath, pkg);
}

function updatePackageVersions(pkg) {
	const dependencyFields = [
		'dependencies',
		'devDependencies',
		'peerDependencies',
		'optionalDependencies',
	];

	const changes = [];

	if (pkg.version !== TARGET_VERSION) {
		changes.push(`version: ${pkg.version} -> ${TARGET_VERSION}`);
	}
	pkg.version = TARGET_VERSION;

	for (const field of dependencyFields) {
		const deps = pkg[field];
		if (!deps) {
			continue;
		}

		for (const depName of Object.keys(deps)) {
			if (!depName.startsWith('@clayui/')) {
				continue;
			}

			if (deps[depName] !== TARGET_VERSION) {
				changes.push(
					`${field}.${depName}: ${deps[depName]} -> ^${TARGET_VERSION}`
				);
			}
			deps[depName] = `^${TARGET_VERSION}`;
		}
	}

	return changes;
}

async function ensureNpmAuth() {
	const whoami = await run(
		'npm',
		['whoami', '--registry', 'https://registry.npmjs.org/'],
		{
			allowFailure: true,
			stdio: 'pipe',
		}
	);

	if (whoami.status !== 0) {
		console.error('ERROR: npm authentication required for npmjs.org');
		console.error(
			'Run `npm login` (or configure your local .npmrc) and retry.'
		);
		process.exit(whoami.status || 1);
	}

	const username = (whoami.stdout || '').trim();
	console.log(`Authenticated to npm as ${username || 'unknown user'}`);
}

async function main() {
	if (!PREVIEW_CHANGES) {
		await ensureNpmAuth();

		console.log('Running test suite before versioning/publish steps');
		try {
			await run('yarn', ['test'], {cwd: ROOT_DIR, env: process.env});
		}
		catch (error) {
			console.error(
				'ERROR: Test suite failed; exiting before publish steps.'
			);
			throw error;
		}
	}

	if (!SKIP_VERSION_BUMP) {
		console.log(
			`Setting all clay-* package versions and @clayui/* deps to ${TARGET_VERSION}`
		);

		const dirs = [...packageDirs, __dirname];

		for (const dir of dirs) {
			console.log(`Updating ${path.basename(dir)}`);

			const pkgPath = packageJsonPath(dir);
			const pkg = readJson(pkgPath);
			const changes = updatePackageVersions(pkg);

			if (!changes.length) {
				continue;
			}

			if (PREVIEW_CHANGES) {
				console.log(`PREVIEW ${pkgPath}`);
				for (const change of changes) {
					console.log(`  - ${change}`);
				}
				continue;
			}

			writeJson(pkgPath, pkg);
		}
	}

	console.log(`Generating unified Clay changelog for ${TARGET_VERSION}`);
	await updateUnifiedChangelogForTargetVersion();

	if (PREVIEW_CHANGES) {
		console.log('----');
		console.log(
			'Preview mode enabled; skipping build/publish/promote steps'
		);

		return;
	}

	const env = process.env;

	let publishCount = 0;
	let skipCount = 0;
	const publishQueue = [];

	for (const dir of packageDirs) {
		const pkgPath = packageJsonPath(dir);
		const pkg = readJson(pkgPath);
		const name = pkg.name || '';
		const version = pkg.version || '';
		const isPrivate = Boolean(pkg.private);

		if (isPrivate || !name.startsWith('@clayui/')) {
			console.log(`SKIP ${path.basename(dir)} (private or non-@clayui)`);
			skipCount += 1;
			continue;
		}

		if (version !== TARGET_VERSION) {
			console.log(
				`SKIP ${name}@${version} (target is ${TARGET_VERSION})`
			);
			skipCount += 1;
			continue;
		}

		const existing = await run(
			'npm',
			['view', `${name}@${version}`, 'version'],
			{
				allowFailure: true,
				env,
			}
		);

		if (existing.status === 0) {
			console.log(`SKIP ${name}@${version} (already published)`);
			skipCount += 1;
			continue;
		}

		publishQueue.push({
			dir,
			name,
			pkg,
			pkgPath,
			version,
		});
	}

	for (const item of publishQueue) {
		console.log('----');
		console.log(`Building ${item.name}@${item.version}`);

		writePublishPackageJson(item.pkgPath);

		if (item.pkg.scripts && item.pkg.scripts.build) {
			await run('yarn', ['build'], {cwd: item.dir, env});
		}

		if (item.pkg.scripts && item.pkg.scripts.buildTypes) {
			await run('yarn', ['buildTypes'], {cwd: item.dir, env});
		}
	}

	for (const item of publishQueue) {
		console.log('----');
		console.log(
			`Publishing ${item.name}@${item.version} with tag '${NPM_TAG}'`
		);

		const publishArgs = ['publish', '--access', 'public', '--tag', NPM_TAG];
		if (DRY_RUN) {
			publishArgs.push('--dry-run');
		}

		await run('npm', publishArgs, {cwd: item.dir, env});

		publishCount += 1;
	}

	restoreTemporaryPackageJsons();

	console.log('----');
	console.log(`Done. Published: ${publishCount}, Skipped: ${skipCount}`);
	console.log(`Target version: ${TARGET_VERSION}`);
	console.log(`Tag used: ${NPM_TAG}`);
}

main().catch((error) => {
	restoreTemporaryPackageJsons();
	console.error(error);
	process.exit(1);
});
