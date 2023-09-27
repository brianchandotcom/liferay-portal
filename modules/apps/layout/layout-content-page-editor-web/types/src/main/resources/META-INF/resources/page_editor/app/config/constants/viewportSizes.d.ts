/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const VIEWPORT_SIZES: {
	readonly desktop: 'desktop';
	readonly landscapeMobile: 'landscapeMobile';
	readonly portraitMobile: 'portraitMobile';
	readonly tablet: 'tablet';
};
export declare type ViewportSize = typeof VIEWPORT_SIZES[keyof typeof VIEWPORT_SIZES];
