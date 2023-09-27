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

				const searchContainerName = `${portletNamespace}assetLinksSearchContainer`;

				const searchContainer = Liferay.SearchContainer.get(
					searchContainerName
				);

				let searchContainerData = searchContainer.getData();

				if (searchContainerData) {
					searchContainerData = searchContainerData.split(',');
				}
				else {
					searchContainerData = [];
				}

				openSelectionModal({
					buttonAddLabel: Liferay.Language.get('done'),
					customSelectEvent: true,
					multiple: true,
					onSelect(data) {
						if (data.value && data.value.length) {
							const selectedItems = data.value;

							Array.from(selectedItems).forEach(
								(selectedItem) => {
									const assetEntry = JSON.parse(selectedItem);

									const entityId = assetEntry.assetEntryId;

									if (
										searchContainerData.indexOf(
											entityId
										) === -1
									) {
										const rowColumns = [];

										rowColumns.push(`<h4 class="list-group-title">
												${Liferay.Util.escapeHTML(assetEntry.title)}
											</h4>
											<p class="list-group-subtitle">
												${Liferay.Util.escapeHTML(assetEntry.assetType)}
											</p>
											<p class="list-group-subtitle">
												${Liferay.Language.get('scope')}: ${Liferay.Util.escapeHTML(
											assetEntry.groupDescriptiveName
										)}
											</p>`);

										rowColumns.push(
											`<button 
												aria-label=${Liferay.Language.get('remove')}
												class="btn btn-monospaced btn-outline-borderless btn-outline-secondary float-right lfr-portal-tooltip modify-link"
												data-rowId="${entityId}"
												title=${Liferay.Language.get('remove')}
												type="button"
											>
												${additionalProps.removeIcon}
											</button>`
										);

										searchContainer.addRow(
											rowColumns,
											entityId
										);

										searchContainer.updateDataStore();
									}
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
