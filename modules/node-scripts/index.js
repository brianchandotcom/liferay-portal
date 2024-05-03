/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import minimist from 'minimist';

export async function main() {
	const ARGS_ARRAY = process.argv.slice(2);

	const {
		_: [type],
	} = minimist(ARGS_ARRAY);

	// eslint-disable-next-line no-console
	console.log(`You ran '${type}'!`);
}
