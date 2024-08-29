/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const LAYOUT_PAGE_TEMPLATE_ENTRY_TYPES: Record<
	LayoutPageTemplateEntryType,
	string
> = {
	basic: '0',
	display_page: '1',
	master_layout: '3',
	widget_page: '2',
} as const;
