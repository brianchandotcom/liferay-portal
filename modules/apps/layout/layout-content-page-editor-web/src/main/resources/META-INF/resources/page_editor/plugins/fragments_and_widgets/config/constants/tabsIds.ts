/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const TABS_IDS = {
	fragments: 0,
	widgets: 1,
} as const;

export type TabId = (typeof TABS_IDS)[keyof typeof TABS_IDS];
