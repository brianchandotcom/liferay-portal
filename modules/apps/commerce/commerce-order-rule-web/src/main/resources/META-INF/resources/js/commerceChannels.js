/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CommerceServiceProvider,
	ItemFinder,
	commerceEvents,
} from 'commerce-frontend-js';

export default function ({
	dataSetId,
	orderRuleExternalReferenceCode,
	orderRuleId,
	rootPortletId,
}) {
	const orderRuleChannelsResource =
		CommerceServiceProvider.AdminOrderAPI('v1');

	function selectItem(channel) {
		const channelData = {
			channelExternalReferenceCode: channel.externalReferenceCode,
			channelId: channel.id,
			orderRuleExternalReferenceCode,
			orderRuleId,
		};

		return orderRuleChannelsResource
			.addOrderRuleChannel(orderRuleId, channelData)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: dataSetId,
				});
			});
	}

	ItemFinder('itemFinder', 'item-finder-root-channel', {
		apiUrl: '/o/headless-commerce-admin-channel/v1.0/channels',
		getSelectedItems: () => Promise.resolve([]),
		inputPlaceholder: Liferay.Language.get('find-a-channel'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('channel-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [dataSetId],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-channels'),
		portletId: rootPortletId,
		schema: [
			{
				fieldName: 'name',
			},
		],
		titleLabel: Liferay.Language.get('add-existing-channel'),
	});
}
