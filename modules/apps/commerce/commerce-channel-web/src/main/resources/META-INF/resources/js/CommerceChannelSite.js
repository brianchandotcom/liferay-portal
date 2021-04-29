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

import {delegate, openSelectionModal} from 'frontend-js-web';

export default function ({
	itemSelectorUrl,
	portletNamespace,
	removeCommerceChannelSiteIcon,
	searchContainerId,
}) {
	Liferay.componentReady(`${portletNamespace}${searchContainerId}`).then(
		(searchContainer) => {
			const selectSiteButton = document.getElementById(
				`${portletNamespace}selectSite`
			);

			if (selectSiteButton) {
				selectSiteButton.addEventListener('click', (event) => {
					event.preventDefault();

					openSelectionModal({
						onSelect: (selectedItem) => {
							if (!selectedItem) {
								return;
							}

							const searchContainerData = searchContainer.getData();

							const link = document.querySelector(
								`a[data-rowid="${searchContainerData}"]`
							);

							if (link) {
								const row = link.closest('tr');

								if (row) {
									searchContainer.deleteRow(
										row,
										link.dataset.rowid
									);
								}
							}

							const rowColumns = [];

							rowColumns.push(selectedItem.name);
							rowColumns.push(
								`<a class="float-right modify-link" data-rowId="${selectedItem.id}" href="javascript:;">${removeCommerceChannelSiteIcon}</a>`
							);

							const siteGroupInput = document.getElementById(
								`${portletNamespace}siteGroupId`
							);

							if (siteGroupInput) {
								siteGroupInput.value = selectedItem.id;
							}

							searchContainer.addRow(rowColumns, selectedItem.id);

							searchContainer.updateDataStore();
						},
						selectEventName: 'sitesSelectItem',
						title: Liferay.Util.sub(
							Liferay.Language.get('select-x'),
							Liferay.Language.get('site')
						),
						url: itemSelectorUrl,
					});
				});
			}

			const searchContainerContentBox = searchContainer.get('contentBox');

			delegate(
				searchContainerContentBox.getDOMNode(),
				'click',
				'.modify-link',
				(event) => {
					const link = event.delegateTarget;

					const row = link.closest('tr');

					searchContainer.deleteRow(row, link.dataset.rowid);

					const siteGroupInput = document.getElementById(
						`${portletNamespace}siteGroupId`
					);

					if (siteGroupInput) {
						siteGroupInput.value = 0;
					}
				}
			);
		}
	);
}
