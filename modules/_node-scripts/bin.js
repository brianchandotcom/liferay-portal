#!/usr/bin/env node
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const args = process.argv.slice(2);

switch (args[0]) {
	case 'build':
		import('./index.mjs')
			.then(({default: main}) => main())
			.catch(console.error);
		break;

	default:
		console.error('Usage: node-scripts build');
		break;
}
