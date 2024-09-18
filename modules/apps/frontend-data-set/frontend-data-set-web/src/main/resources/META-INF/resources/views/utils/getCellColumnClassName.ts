/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getCellColumnClassName(
	columnName: string | Array<string>
): string {
	let className: string;

	const languageIdentifierPosition = columnName.indexOf('LANG');

	if (Array.isArray(columnName) && languageIdentifierPosition > 0) {
		className = columnName.slice(0, languageIdentifierPosition)[0];
	}
	else if (Array.isArray(columnName)) {
		className = columnName.join('-');
	}
	else {
		className = columnName;
	}

	className = className.replace(/[\W]+/g, '-');

	return `cell-${className}`;
}
