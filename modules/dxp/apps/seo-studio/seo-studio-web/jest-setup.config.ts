/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-nocheck

(globalThis as any).Liferay = {
	...(globalThis.Liferay || {}),
	Language: {
		...((globalThis.Liferay || {}).Language || {}),
		get: (key: string) => key,
	},
	ThemeDisplay: {
		...((globalThis.Liferay || {}).ThemeDisplay || {}),
		getLanguageId: () => 'en_US',
	},
};

(globalThis as any).ResizeObserver = jest.fn().mockImplementation(() => ({
	disconnect: jest.fn(),
	observe: jest.fn(),
	unobserve: jest.fn(),
}));
