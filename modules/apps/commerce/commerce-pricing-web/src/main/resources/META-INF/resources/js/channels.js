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
	commerceDiscountId,
	discountExternalReferenceCode,
	portletId,
	pricingFDSName,
	spritemap,
}) {
	const CommerceDiscountChannelsResource =
		CommerceServiceProvider.AdminPricingAPI('v2');

	const id = commerceDiscountId;

	function selectItem(channel) {
		const channelData = {
			channelExternalReferenceCode: channel.externalReferenceCode,
			channelId: channel.id,
			discountExternalReferenceCode,
			discountId: id,
		};

		return CommerceDiscountChannelsResource.addDiscountChannel(
			id,
			channelData
		)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: pricingFDSName,
				});
			})
			.catch((error) => {
				return Promise.reject(error.title);
			});
	}

	function getSelectedItems() {
		return Promise.resolve([]);
	}

	ItemFinder('itemFinder', 'item-finder-root-channel', {
		apiUrl: '/o/headless-commerce-admin-channel/v1.0/channels',
		getSelectedItems,
		inputPlaceholder: Liferay.Language.get('find-a-channel'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('channel-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [pricingFDSName],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-channels'),
		portletId,
		schema: [
			{
				fieldName: 'name',
			},
		],
		spritemap,
		titleLabel: Liferay.Language.get('add-existing-channel'),
	});
}
