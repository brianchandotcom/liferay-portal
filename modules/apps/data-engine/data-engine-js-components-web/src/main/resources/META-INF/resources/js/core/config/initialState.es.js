/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const INITIAL_PAGES = [
	{
		description: '',
		localizedDescription: {
			[themeDisplay.getDefaultLanguageId()]: '',
		},
		localizedTitle: {
			[themeDisplay.getDefaultLanguageId()]: '',
		},
		rows: [],
		title: '',
	},
];

export const INITIAL_STATE = {
	activePage: 0,
	defaultLanguageId: themeDisplay.getDefaultLanguageId(),
	editingLanguageId: themeDisplay.getDefaultLanguageId(),
	focusedField: {},
	pages: INITIAL_PAGES,
	rules: [],
};
