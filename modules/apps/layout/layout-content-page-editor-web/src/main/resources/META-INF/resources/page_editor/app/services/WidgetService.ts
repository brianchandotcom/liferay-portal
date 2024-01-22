/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import {config} from '../config/index';
import draftServiceFetch, {OnNetworkStatus} from './draftServiceFetch';
import serviceFetch from './serviceFetch';

import type {FragmentEntryLink} from '../actions/addFragmentEntryLinks';
import type {Widget, WidgetSet} from '../actions/updateWidgets';

export default {
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
	}) {
		return draftServiceFetch<{
			addedItemId: string;
			fragmentEntryLink: FragmentEntryLink;
			layoutData: LayoutData;
		}>(
			config.addPortletURL,
			{
				body: {
					parentItemId,
					portletId,
					portletItemId,
					position,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	getWidgets() {
		return serviceFetch<WidgetSet[]>(config.getPortletsURL, {});
	},

	toggleWidgetHighlighted({
		highlighted,
		onNetworkStatus,
		portletId,
	}: {
		highlighted: boolean;
		onNetworkStatus: OnNetworkStatus;
		portletId: string;
	}) {
		return draftServiceFetch<{highlightedPortlets: Widget[]}>(
			config.updatePortletsHighlightedConfigurationURL,
			{
				body: {
					highlighted,
					portletId,
				},
			},
			onNetworkStatus
		);
	},
};
