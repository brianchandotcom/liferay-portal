/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {initializeCache} from './src/main/resources/META-INF/resources/page_editor/app/utils/cache';

initializeCache();

Liferay.Util.sub.mockImplementation((key, ...args) => {
	const argsArray = args.flatMap((arg) => arg);

	return key
		.replace(/^x-/, () => `${argsArray.shift()}-`)
		.replace(/-x(\.?)-/g, (_, dot) => `-${argsArray.shift()}${dot}-`)
		.replace(/-x$/, () => `-${argsArray.shift()}`);
});

if (typeof Array.prototype.flatMap !== 'function') {
	Array.prototype.flatMap = function () {
		return Array.prototype.map
			.apply(this, arguments)
			.reduce((acc, x) => acc.concat(x), []);
	};
}

// eslint-disable-next-line
jest.mock(
	'./src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			availableLanguages: {
				ar_SA: {
					default: false,
					displayName: 'Arabic (Saudi Arabia)',
					languageIcon: 'ar-sa',
					languageId: 'ar_SA',
					w3cLanguageId: 'ar-SA',
				},
				en_US: {
					default: false,
					displayName: 'English (United States)',
					languageIcon: 'en-us',
					languageId: 'en_US',
					w3cLanguageId: 'en-US',
				},
				es_ES: {
					default: true,
					displayName: 'Spanish (Spain)',
					languageIcon: 'es-es',
					languageId: 'es_ES',
					w3cLanguageId: 'es-ES',
				},
			},
			availableViewportSizes: {
				desktop: {label: 'Desktop', sizeId: 'desktop'},
				mobile: {label: 'Mobile', sizeId: 'mobile'},
				tablet: {label: 'Tablet', sizeId: 'tablet'},
			},
			commonStyles: [
				{
					styles: [
						{
							defaultValue: 'left',
							name: 'textAlign',
						},
					],
				},
			],
			defaultLanguageId: 'en_US',
			defaultSegmentsExperienceId: '0',
			frontendTokens: {},
			layoutType: 'content',
			panels: [['browser']],
			portletNamespace: 'page-editor-portlet-namespace',
			selectedViewportSize: 'desktop',
			sidebarPanels: {
				browser: {label: 'Browser', sidebarPanelId: 'browser'},
			},
		},
	})
);
