/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import preflight from '../preflight/index.mjs';
import getNamedArguments from '../util/getNamedArguments.mjs';
import format from './format.mjs';

export default async function main() {
	const {all, check} = getNamedArguments({
		all: '--all',
		check: '--check',
	});

	if (check) {
		console.log('🛫 Running preflight...');
		await preflight();
	}

	console.log('📝 Running format...');
	await format(!check, {allFiles: all});
}
