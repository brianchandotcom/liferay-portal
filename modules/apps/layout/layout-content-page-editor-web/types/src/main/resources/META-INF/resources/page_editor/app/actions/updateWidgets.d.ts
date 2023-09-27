/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {FragmentEntryLink} from './addFragmentEntryLinks';
export interface Widget {
	highlighted: boolean;
	instanceable: boolean;
	portletId: string;
	portletItems: Widget[];
	title: string;
}
export interface WidgetSet {
	categories: string[];
	path: string;
	portlets: Widget[];
	title: string;
}
export default function updateWidgets({
	fragmentEntryLinks,
	widgets,
}: {
	fragmentEntryLinks: FragmentEntryLink[];
	widgets?: WidgetSet[];
}): {
	readonly fragmentEntryLinks: FragmentEntryLink<string, string>[];
	readonly type: 'UPDATE_WIDGETS';
	readonly widgets: WidgetSet[] | undefined;
};
