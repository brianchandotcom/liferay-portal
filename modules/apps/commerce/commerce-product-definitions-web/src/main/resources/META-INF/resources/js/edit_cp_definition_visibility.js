/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider, commerceEvents} from 'commerce-frontend-js';
import {openSelectionModal, openToast} from 'frontend-js-web';

function handleEvent({
	dataSetId,
	fieldName,
	fieldValueName,
	filterFieldName,
	productId,
	selectedItems,
}) {
	const AdminCatalogResource = CommerceServiceProvider.AdminCatalogAPI('v1');

	const formattedItems = [];

	selectedItems.map((item) => {
		formattedItems.push({[fieldValueName]: parseInt(item.value, 10)});
	});

	const formattedData = {
		[fieldName]: formattedItems,
		[filterFieldName]: true,
	};

	return AdminCatalogResource.updateProduct(productId, formattedData).finally(
		() => {
			window.location.reload();

			Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
				id: dataSetId,
			});

			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});
		}
	);
}

export default function ({
	accountGroupDataSetId,
	accountGroupItemSelectorURL,
	channelDataSetId,
	channelItemSelectorURL,
	checkedAccountGroupIds,
	checkedChannelIds,
	namespace,
	productId,
}) {
	const eventHandlers = [];

	const selectCommerceAccountGroupHandler = Liferay.on(
		`${namespace}selectCommerceAccountGroup`,
		() => {
			openSelectionModal({
				multiple: true,
				onSelect: (selectedItems) => {
					if (!selectedItems || !selectedItems.length) {
						return;
					}

					const accountGroupIds = checkedAccountGroupIds.split(',');

					accountGroupIds.map((accountGroupId) => {
						selectedItems.push({value: accountGroupId});
					});

					handleEvent({
						dataSetId: accountGroupDataSetId,
						fieldName: 'productAccountGroups',
						fieldValueName: 'accountGroupId',
						filterFieldName: 'productAccountGroupFilter',
						productId,
						selectedItems,
					});
				},
				title: Liferay.Language.get('select-account-group'),
				url: accountGroupItemSelectorURL,
			});
		}
	);

	eventHandlers.push(selectCommerceAccountGroupHandler);

	const selectCommerceChannelHandler = Liferay.on(
		`${namespace}selectCommerceChannel`,
		() => {
			openSelectionModal({
				multiple: true,
				onSelect: (selectedItems) => {
					if (!selectedItems || !selectedItems.length) {
						return;
					}

					const channelIds = checkedChannelIds.split(',');

					channelIds.map((channelId) => {
						selectedItems.push({value: channelId});
					});

					handleEvent({
						dataSetId: channelDataSetId,
						fieldName: 'productChannels',
						fieldValueName: 'channelId',
						filterFieldName: 'productChannelFilter',
						productId,
						selectedItems,
					});
				},
				title: Liferay.Language.get('select-channel'),
				url: channelItemSelectorURL,
			});
		}
	);

	eventHandlers.push(selectCommerceChannelHandler);

	Liferay.on('destroyPortlet', () => {
		eventHandlers.forEach((eventHandler) => {
			eventHandler.detach();
		});
	});
}
