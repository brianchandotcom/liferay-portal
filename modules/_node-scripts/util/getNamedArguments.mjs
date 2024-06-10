/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getNamedArguments(argumentNames) {
	const namedArgs = {};

	let argv = process.argv.slice(3);

	while (argv.length) {
		for (const [argName, argSwitch] of Object.entries(argumentNames)) {
			if (argv[0].startsWith(`${argSwitch}=`)) {
				namedArgs[argName] = argv[0].substring(argSwitch.length + 1);

				break;
			}
		}

		argv = argv.slice(1);
	}

	return namedArgs;
}
