#!/usr/bin/env node
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const fs = require('fs');
const path = require('path');

const COMMANDS = {
	'build': {
		description: 'builds frontend stuff of current project',
		parameters: '',
	},
	'check:tsc': {
		description:
			'runs TypeScript checks in the current project or globally (if run from modules)',
		parameters: '[<tsc arguments>]',
	},
	'generate:tsconfig': {
		description: 'generates tsconfig.json files for all projects',
		parameters: '',
	},
};

const command = process.argv[2];

if (COMMANDS[command] === undefined) {
	showHelpAndExit();
}

const commandPath = command.split(':');

let modulePath = path.join(__dirname, ...commandPath, 'index.mjs');

if (!fs.existsSync(modulePath)) {
	showHelpAndExit();
}

// Node.js wants "file://" in front of absolute paths because otherwise it thinks "C:" is a URL
// protocol and refuses to load the module.

modulePath = `file://${modulePath}`;

const mainPromise = import(modulePath);

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
