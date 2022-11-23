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
