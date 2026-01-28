/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-components-web';
import {createPortletURL} from 'frontend-js-web';

import {LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {FragmentEntry} from '../actions/updateFragments';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {config} from '../config/index';
import {Dispatch} from '../contexts/StoreContext';
import swapFragment from '../thunks/swapFragment';

export default function openSwapFragmentModal({
	dispatch,
	fragmentEntryLinks,
	item,
}: {
	dispatch: Dispatch;
	fragmentEntryLinks: FragmentEntryLinkMap;
	item: LayoutDataItem;
}) {
	if (item.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
		return;
	}

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	const url = createPortletURL(config.getFragmentEntryInputURL, {
		inputTypes: fragmentEntryLink.fieldTypes.join(','),
	});

	openSelectionModal<{
		fragmententrykey: FragmentEntry['fragmentEntryKey'];
		groupid: string;
	}>({
		height: '70vh',
		onSelect: (response) => {
			dispatch(
				swapFragment({
					editableValues: JSON.stringify(
						fragmentEntryLink.editableValues
					),
					fragmentEntryKey: response.fragmententrykey,
					fragmentEntryLinkId: fragmentEntryLink.fragmentEntryLinkId,
					groupId: response.groupid,
				})
			);
		},
		selectEventName: 'selectFragment',
		size: 'lg',
		title: Liferay.Language.get('swap-fragment'),
		url: url.toString(),
	});
}
