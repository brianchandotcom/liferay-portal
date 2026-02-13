/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {configure} from '@testing-library/dom';

configure({
	testIdAttribute: 'data-qa-id',
});

(globalThis as any).Liferay = {
	...(globalThis.Liferay || {}),
	Language: {
		...(globalThis.Liferay.Language || {}),
		direction: {en_US: 'rtl'},
		get: (key: string) => key,
	},
	ThemeDisplay: {
		...(globalThis.Liferay.ThemeDisplay || {}),
		getBCP47LanguageId: () => 'en-US',
		getDefaultLanguageId: () => 'en_US',
		getLanguageId: () => 'en_US',
		getUserId: () => '1',
	},
	Util: {
		...(globalThis.Liferay.Util || {}),
		escapeHTML: (str: string) => str,
		formatStorage: (size: number) => `${size / 1024} KB`,
	},
	authToken: 'mocked-auth-token',
};

(globalThis as any).ResizeObserver = jest.fn().mockImplementation(() => ({
	disconnect: jest.fn(),
	observe: jest.fn(),
	unobserve: jest.fn(),
}));
