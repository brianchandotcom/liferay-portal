/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, openSelectionModal, sub} from 'frontend-js-web';

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
								`<a class="float-right modify-link" data-rowId="${selectedItem.id}" href="javascript:void(0);">${removeCommerceChannelSiteIcon}</a>`
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
						title: sub(
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
