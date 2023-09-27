/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getAvailableLocalesObject(displayNames, languageIds) {
	return languageIds.map((id, index) => {
		return {
			displayName: displayNames[index],
			id,
			label: id,
			symbol: id.toLowerCase().replaceAll('_', '-'),
		};
	});
}
