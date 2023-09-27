/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	actions,
	additionalProps,
	items,
	portletNamespace,
	...props
}) {
	const updateItem = (item) => {
		const newItem = {
			...item,
			onClick(event) {
				event.preventDefault();

				openSelectionModal({
					customSelectEvent: true,
					multiple: true,
					onSelect(data) {
						if (data.value && data.value.length) {
							const selectedItems = data.value;

							let assetClassName = '';

							Array.prototype.forEach.call(
								selectedItems,
								(selectedItem) => {
									const assetEntry = JSON.parse(selectedItem);

									assetClassName = assetEntry.className;
								}
							);

							Liferay.Util.postForm(
								document[`${portletNamespace}fm`],
								{
									data: {
										assetEntryIds: Array.from(selectedItems)
											.map((selectedItem) => {
												const assetEntry = JSON.parse(
													selectedItem
												);

												return assetEntry.assetEntryId;
											})
											.join(','),
										assetEntryType: assetClassName,
										cmd: 'add-selection',
										redirect: additionalProps.currentURL,
									},
								}
							);
						}
					},
					selectEventName: `${portletNamespace}selectAsset`,
					title: item.data.title,
					url: item.data.href,
				});
			},
		};

		if (Array.isArray(item.items)) {
			newItem.items = item.items.map(updateItem);
		}

		return newItem;
	};

	return {
		...props,
		actions: actions?.map(updateItem),
		items: items?.map(updateItem),
	};
}
