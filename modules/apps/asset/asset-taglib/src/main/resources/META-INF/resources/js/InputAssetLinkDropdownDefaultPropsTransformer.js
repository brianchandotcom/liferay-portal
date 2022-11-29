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
					multiple: true,
					onSelect(selectedItems) {
						Array.from(selectedItems).forEach((selectedItem) => {
							const assetEntry = JSON.parse(selectedItem.value);

							const entityId = assetEntry.assetEntryId;

							if (searchContainerData.indexOf(entityId) === -1) {
								const entryLink = `<div class="text-right">
												<button class="modify-link btn btn-unstyled" data-rowId="${entityId}" type="button">
													<span class="lfr-portal-tooltip" tabindex="0" title="${Liferay.Language.get(
														'remove'
													)}">
														<span class="c-inner" tabindex="=-1">
															<svg aria-hidden="true" class="lexicon-icon lexicon-icon-info-circle" focusable="false">
																<use xlink:href="${item.data.removeIcon}#times-circle" />
															</svg>
														</span>
													</span>
												</button>
											</div>`;

								const entryHtml = `<h4 class="list-group-title">
												${Liferay.Util.escapeHTML(assetEntry.title)}
											</h4>
											<p class="list-group-subtitle">
												${Liferay.Util.escapeHTML(assetEntry.assetType)}
											</p>
											<p class="list-group-subtitle">
												${Liferay.Language.get('scope')}: ${Liferay.Util.escapeHTML(
									assetEntry.groupDescriptiveName
								)}
											</p>
												
										`;

								searchContainer.addRow(
									[entryHtml, entryLink],
									entityId
								);

								searchContainer.updateDataStore();
							}
						});
					},
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
