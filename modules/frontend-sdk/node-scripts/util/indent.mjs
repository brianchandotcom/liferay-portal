/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function indent(spaces, output) {
	let prefix = '';

	for (let i = 0; i < spaces; i++) {
		prefix += ' ';
	}

	return output
		.split('\n')
		.map((line) => `${prefix}${line}`)
		.join('\n');
}
