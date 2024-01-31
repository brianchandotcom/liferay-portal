/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import {OnNetworkStatus} from './draftServiceFetch';
import type {FragmentEntryLink} from '../actions/addFragmentEntryLinks';
import type {Widget, WidgetSet} from '../actions/updateWidgets';
declare const _default: {
	addPortlet({
		onNetworkStatus,
		parentItemId,
		portletId,
		portletItemId,
		position,
		segmentsExperienceId,
	}: {
		onNetworkStatus: OnNetworkStatus;
		parentItemId: string;
		portletId: string;
		portletItemId: string | null;
		position: number;
		segmentsExperienceId: string;
	}): Promise<{
		addedItemId: string;
		fragmentEntryLink: FragmentEntryLink;
		layoutData: LayoutData;
	}>;
	getWidgets(): Promise<WidgetSet[]>;
	toggleWidgetHighlighted({
		highlighted,
		onNetworkStatus,
		portletId,
	}: {
		highlighted: boolean;
		onNetworkStatus: OnNetworkStatus;
		portletId: string;
	}): Promise<{
		highlightedPortlets: Widget[];
	}>;
};
export default _default;
