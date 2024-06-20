/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getNamedArguments(argumentNames) {
	const namedArgs = {};

	let argv = process.argv.slice(3);

	while (argv.length) {
		for (const [argName, argSwitch] of Object.entries(argumentNames)) {
			if (argv[0].startsWith(argSwitch)) {

				// Default value to true if flag exists

				let value = true;

				// If flag has an '=' always use the value after

				if (argv[0].includes('=')) {
					value = argv[0].split('=')[1];
				}

				namedArgs[argName] = value || true;

				break;
			}
		}

		argv = argv.slice(1);
	}

	return namedArgs;
}
