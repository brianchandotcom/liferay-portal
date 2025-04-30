/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default async function countSubstringOccurrences(
	text: string,
	substring: string
) {
	const regex = new RegExp(substring, 'g');

	const matches = text.match(regex);

	return matches ? matches.length : 0;
}
