/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CommerceServiceProvider,
	ItemFinder,
	commerceEvents,
} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-components-web';

export default function ({
	cpConfigurationListExternalReferenceCode,
	cpConfigurationListId,
	dataSetId,
	portletId,
	spritemap,
}) {
	const CommerceProductConfigurationListResource =
		CommerceServiceProvider.AdminCatalogAPI('v1');

	function selectItem(channel) {
		const channelData = {
			channelExternalReferenceCode: channel.externalReferenceCode,
			channelId: channel.id,
			productConfigurationListExternalReferenceCode:
				cpConfigurationListExternalReferenceCode,
			productConfigurationListId: cpConfigurationListId,
		};

		return CommerceProductConfigurationListResource.addProductConfigurationListChannel(
			cpConfigurationListId,
			channelData
		)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: dataSetId,
				});
			})
			.catch((error) => {
				openToast({
					message: error.message || error.title,
					type: 'danger',
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
