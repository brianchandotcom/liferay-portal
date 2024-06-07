/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const LAYOUT_TYPES = {
	content: '-1',
	display: '1',
	master: '3',
	pageTemplate: '0',
} as const;

export type LayoutType = (typeof LAYOUT_TYPES)[keyof typeof LAYOUT_TYPES];
