#!/usr/bin/env node
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const COMMANDS = {
	'build': {
		description: `
		Builds current project.
`,
		parameters: '',
		script: './bundle/index.mjs',
	},
	'build:custom': {
		description: `
		Builds artifacts for the current project using a custom esbuild configuration.
`,
		parameters: '',
		script: './bundle/custom.mjs',
	},
	'build:report': {
		description: `
		Generates an aggregated report of build timings.

		The <timings directory> arguments falls back to LIFERAY_NPM_SCRIPTS_TIMING environment
		variable when not provided.
`,
		parameters: '[<timings directory>]',
		script: './bundle/report.mjs',
	},
	'build:theme': {
		description: `
		Build a theme project with liferay-theme-tasks and gulp.
`,
		script: './bundle/theme.mjs',
	},
	'check:ci': {
		description: `
		Runs checks as in CI's pull requests.

		Typical checks are:

		  - Preflight checks (see below).
		  - Source format checks for modified files.
		  - Correct generation of tsconfig.json files.
		  - TypeScript checks for modified files.
		  - ...
`,
		script: './check/ci.mjs',
	},
	'check:preflight': {
		description: `
		Runs "lightweight" global checks not implemented by ESLint or Prettier.

		Typical preflight checks are:

		  - No forbidden configuration file names are used.
		  - All package.json files are correctly formatted.
		  - The yarn.lock file is correct (eg: doesn't point to local npm registries).
		  - The node-scripts hash is correct.
		  - ...
`,
		script: './check/preflight.mjs',
	},
	'check:tsc': {
		description: `
		Runs TypeScript checks in the current project or globally (when run from modules).

		See this help's introduction to find the meaning of --all, --current-branch, ... parameters.
`,
		parameters: '[{--all|--current-branch|--local-changes}]',
		script: './check/tsc.mjs',
	},
	'format': {
		description: `
		Formats and lints source files with eslint and prettier in the current project or globally
		(when run from modules).

		If --check is passed no file is modified and the command just outputs what files need to be
		formatted.

		If --emit-suppressed is passed, the list of errors that are suppressed will be logged as well.

		See this help's introduction to find the meaning of --all, --current-branch, ... parameters.
`,
		parameters:
			'[--check] [--emit-suppressed] [{--all|--current-branch|--local-changes}]',
		script: './format/index.mjs',
	},
	'format:file': {
		description: `
		Formats a single source file with eslint and prettier.
`,
		parameters: '<source file path>',
		script: './format/file.mjs',
	},
	'format:self': {
		description: `
		Formats node-scripts.

		This is a internal command that must not to be used from any other project nor from the
		command	line.
`,
		parameters: '',
		script: './format/self.mjs',
	},
	'generate:tsconfig': {
		description: `
		Generates tsconfig.json files for all projects.
`,
		parameters: '',
		script: './generate/tsconfig.mjs',
	},
	'gitmerge:self': {
		description: `implements a Git merge driver for node-scripts' package.json file`,
		parameters:
			'--current=<current file> --base=<base file> --other=<other file>',
		script: './gitmerge/self.mjs',
	},
	'gitmerge:setup': {
		description: 'adds gitmerge:self to .git/config file',
		parameters: '',
		script: './gitmerge/setup.mjs',
	},
	'setup': {
		description: `
		Setup working environment used by node-scripts (for example: download the binary Sass
		compiler when necessary).

		This task is usually invoked by yarn when locally installing node-scripts but it can also be
		run manually for troubleshooting purposes.
`,
		parameters: '',
		script: './setup.mjs',
	},
	'test': {
		description: `
		Runs unit tests in a single or multiple projects.

		When multiple projects are tested and --sync argument is given, project tests are run
		serially.
`,
		parameters: '[--sync]',
		script: './test/index.mjs',
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

	Where <command> is an action (like 'build', 'check', 'format', 'generate', 'test', ...)
	optionally qualified by a subject (like ':custom', ':theme', ...).

	Actions can usually be executed:

	  - Only globally (eg: check:preflight).
	  - Only per project (eg: build).
	  - Globally or per project based on the directory of invocation (eg: format). Typically these
	    commands run globally when invoked at 'modules' and per project when invoked from a
	    project's directory.

	Some actions may receive one of the arguments {--all|--current-branch|--local-changes} to
	restrict the set of files to which they must be	applied. Typically --all is assumed if none is
	given.

	The meaning of such flags is:

	  --all:            check everything (may take long to run for some tasks)
	  --current-branch: only check changed stuff that has been committed to the active branch
	  --local-changes:  only check locally uncommitted changed stuff

	The word "changed" should be interpreted as "changed as of master branch".


Available commands:
`);

	for (const [command, {description, parameters}] of Object.entries(
		COMMANDS
	)) {
		let line = '\t• ';

		line += command;

		if (parameters) {
			line += ` ${parameters}`;
		}

		line += '\n';
		line += description
			.split('\n')
			.map((line) => `\t  ${line.replaceAll('\t', '')}`)
			.join('\n');
		line += '\n';

		console.error(line);
	}

	process.exit(2);
}
