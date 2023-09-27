/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getUniqueName(
	items: Array<{name: string}>,
	languageKey: string
): string {
	let name = languageKey;

	const names = new Set<string>([...items.map((item) => item.name)]);

	items.forEach((_, index) => {
		if (names.has(name)) {
			name = `${languageKey} ${index + 2}`;
		}
	});

	return name;
}
