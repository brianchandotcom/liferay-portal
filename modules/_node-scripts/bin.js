#!/usr/bin/env node
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const COMMANDS = {
	'build': {
		description: 'builds frontend stuff of current project',
		parameters: '',
		script: './bundle/index.mjs',
	},
	'build:custom': {
		description:
			'builds frontend stuff using a custom esbuild configuration',
		parameters: '',
		script: './bundle/custom.mjs',
	},
	'build:report': {
		description: 'generates an aggregated report of build timings',
		parameters:
			'[<timings directory> (falls back to LIFERAY_NPM_SCRIPTS_TIMING env var)]',
		script: './bundle/report.mjs',
	},
	'check:preflight': {
		description: 'runs several other infra-type checks',
		parameters: '',
		script: './preflight/index.mjs',
	},
	'check:tsc': {
		description:
			'runs TypeScript checks in the current project or globally (if run from modules)',
		parameters: '[--modified-since=<git commit>]',
		script: './tsc/index.mjs',
	},
	'format': {
		description:
			'formats source files or optionally only checks with "--check" flag (when run from ' +
			'modules it also runs check:preflight)',
		parameters: '[--all] [--check]',
		script: './format/index.mjs',
	},
	'format:file': {
		description: 'formats a single source file.',
		parameters: '<source file path>',
		script: './format/file.mjs',
	},
	'format:self': {
		description:
			'formats node-scripts (not to be used externally in any other project).',
		parameters: '',
		script: './format/self.mjs',
	},
	'generate:tsconfig': {
		description: 'generates tsconfig.json files for all projects',
		parameters: '',
		script: './tsconfig/index.mjs',
	},
	'setup': {
		description: 'setup working environment used by node-scripts',
		parameters: '',
		script: './setup.mjs',
	},
	'test': {
		description: 'runs unit tests in a single or multiple projects.',
		script: './test/index.mjs',
	},
	'test:sync': {
		description: 'Synchronously runs tests across multiple projects.',
		script: './test/sync.mjs',
	},
};

const command = process.argv[2];

if (COMMANDS[command] === undefined) {
	showHelpAndExit();
}

const {script} = COMMANDS[command];

const mainPromise = import(script);

mainPromise
	.then(({default: main}) => main())
	.catch((error) => {
		console.error(error);

		process.exit(1);
	});

function showHelpAndExit() {
	console.error(`
Usage: node-scripts <command>

Available commands:
`);

	const maxCommandLength = Object.entries(COMMANDS).reduce(
		(max, [command, {parameters}]) =>
			Math.max(max, getCommandDisplayLength(command, parameters)),
		0
	);

	for (const [command, {description, parameters}] of Object.entries(
		COMMANDS
	)) {
		let line = '    ';

		line += command;

		if (parameters) {
			line += ` ${parameters}`;
		}

		for (
			let i = getCommandDisplayLength(command, parameters);
			i < maxCommandLength + 4;
			i++
		) {
			line += ' ';
		}

		line += description;

		console.error(line);
	}

	console.error('');

	process.exit(2);
}

function getCommandDisplayLength(command, parameters) {
	if (!parameters) {
		return command.length;
	}

	return command.length + 1 + parameters.length;
}
