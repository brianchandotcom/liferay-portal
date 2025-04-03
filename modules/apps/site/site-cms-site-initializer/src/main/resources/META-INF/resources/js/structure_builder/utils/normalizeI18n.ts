/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function normalizeI18n(
	name: Liferay.Language.LocalizedValue<string>
) {
	const entries = Object.keys(name).map((key) => [
		key.replace('-', '_'),
		name[key as keyof typeof name],
	]);

	return Object.fromEntries(entries);
}
