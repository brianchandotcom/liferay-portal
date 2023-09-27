/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const VIEWPORT_SIZES = {
	desktop: 'desktop',
	landscapeMobile: 'landscapeMobile',
	portraitMobile: 'portraitMobile',
	tablet: 'tablet',
} as const;

export type ViewportSize = typeof VIEWPORT_SIZES[keyof typeof VIEWPORT_SIZES];
