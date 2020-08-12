/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

window.AlloyEditor = {
	...window.AlloyEditor,
	Selections: [
		{
			buttons: ['linkEdit'],
			name: 'link',
		},
		{
			buttons: [
				'styles',
				'bold',
				'italic',
				'underline',
				'link',
				'twitter',
			],
			name: 'text',
		},
	],
};

window.AUI = () => ({
	...window.AUI,
	use: (...modules) => {
		const callback = modules[modules.length - 1];

		callback({
			LiferayAlloyEditor: () => ({
				render: () => ({
					destroy: () => {},
					getHTML: () => 'test',
					getNativeEditor: () => ({
						on: () => true,
						setData: () => false,
					}),
				}),
			}),
			one: () => ({
				innerHTML: () => {},
			}),
		});
	},
});

window.Liferay.PortletKeys = {
	DOCUMENT_LIBRARY: 'DOCUMENT_LIBRARY',
	ITEM_SELECTOR: 'ITEM_SELECTOR',
};

window.themeDisplay = {
	...window.themeDisplay,
	getDefaultLanguageId: () => 'en_US',
	getLayoutRelativeControlPanelURL: () => 'layoutRelativeControlPanelURL',
	getLayoutRelativeURL: () => 'getLayoutRelativeURL',
	getScopeGroupId: () => 'scopeGroupId',
	isSignedIn: () => true,
};

window.util = {
	...window.util,
	selectEntity: () => {},
};

const languageMap = {
	'days-abbreviation': 'd',
	'decimal-delimiter': '.',
	'hours-abbreviation': 'h',
	'minutes-abbreviation': 'min',
	'mmm-dd': 'MMM DD',
	'mmm-dd-hh-mm': 'MMM DD, HH:mm',
	'mmm-dd-hh-mm-a': 'MMM DD, hh:mm A',
	'mmm-dd-lt': 'MMM DD, LT',
	'mmm-dd-yyyy': 'MMM DD, YYYY',
	'mmm-dd-yyyy-lt': 'MMM DD, YYYY, LT',
	'thousand-abbreviation': 'K',
};

window.Liferay = {
	...(window.Liferay || {}),
	AUI: {
		getDateFormat: () => '%m/%d/%Y',
	},
	Language: {
		get: (key) => {
			if (languageMap[key]) {
				return languageMap[key];
			}

			return key;
		},
	},
	ThemeDisplay: window.themeDisplay,
	Util: window.util,
};
