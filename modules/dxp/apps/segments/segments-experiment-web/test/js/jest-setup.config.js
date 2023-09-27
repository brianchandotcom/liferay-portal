/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const Liferay = {
	Language: {
		available: {
			en_US: 'English (United States)',
		},
		get: (key) => {
			let counter = 0;

			return key.replace(new RegExp('(^x-)|(-x-)|(-x$)', 'gm'), (match) =>
				match.replace('x', `{${counter++}}`)
			);
		},
	},
	ThemeDisplay: {
		getDefaultLanguageId: () => 'en_US',
	},
};

window.Liferay = Liferay;
