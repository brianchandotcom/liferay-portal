/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import print from '../../print.mjs';

export default function printEslintErrors(filePath, messages) {
	if (messages.length) {
		print(
			2,
			print.error('ERROR:'),
			'File',
			print.underline(filePath),
			'has format errors'
		);
	}

	messages.forEach(({column, line, message, ruleId, severity}) => {
		const label = ruleId || 'syntax-error';

		print(
			3,
			`${line}:${column}`,
			severity === 2 ? print.red('error') : print.yellow('warning'),
			message,
			`(${label})`
		);
	});

	if (messages.length) {
		print(2, '');
	}
}
