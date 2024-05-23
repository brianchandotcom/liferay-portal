#!/usr/bin/env node
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const fs = require('fs');
const path = require('path');

const COMMAND_DESCRIPTIONS = {
	'build': 'builds frontend stuff of current project',
};

const command = process.argv[2];

if (COMMAND_DESCRIPTIONS[command] === undefined) {
	showHelpAndExit();
}

const commandPath = command.split(':');

const modulePath = path.join(__dirname, ...commandPath, 'index.mjs');

if (!fs.existsSync(modulePath)) {
	showHelpAndExit();
}

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

	for (const [command, description] of Object.entries(COMMAND_DESCRIPTIONS)) {
		console.error(`    ${command}    ${description}`);
	}

	console.error('');

	process.exit(2);
}
