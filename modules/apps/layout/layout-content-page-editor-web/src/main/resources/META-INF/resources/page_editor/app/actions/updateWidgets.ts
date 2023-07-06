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

import {UPDATE_WIDGETS} from './types';

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
}) {
	return {
		fragmentEntryLinks,
		type: UPDATE_WIDGETS,
		widgets,
	} as const;
}
