/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, openSelectionModal, sub} from 'frontend-js-web';

interface Props {
	groupSelectorURL: string;
	namespace: string;
	selectEventName: string;
}

type SelectedItem = {
	groupdescriptivename: string;
	groupid: string;
	hasvirtualhost: string;
};

export default function ({
	groupSelectorURL,
	namespace,
	selectEventName,
}: Props) {
	const groupIdsInput = document.getElementById(
		`${namespace}groupsSearchContainerPrimaryKeys`
	) as HTMLInputElement;

	const selectSiteButton = document.getElementById(
		`${namespace}selectSiteLink`
	) as HTMLButtonElement;

	// @ts-ignore

	const searchContainer = Liferay.SearchContainer.get(
		`${namespace}groupsSearchContainer`
	);

	const searchContainerContentBox = searchContainer.get('contentBox');

	const getGroupIds = (searchContainer: any) => {
		const searchContainerData = searchContainer.getData();

		return !searchContainerData.length
			? []
			: searchContainerData.split(',');
	};

	const onSelectClick = () => {
		const groupIds = getGroupIds(searchContainer);

		openSelectionModal({
			onSelect: (selectedItem: SelectedItem) => {
				if (selectedItem) {
					const {
						groupdescriptivename: entityName,
						groupid: entityId,
						hasvirtualhost: hasVirtualHost,
					} = selectedItem;
					const rowColumns = [];

					const title = sub(
						Liferay.Language.get('remove-x'),
						entityName
					);

					const sitesIcon = Liferay.Util.getLexiconIconTpl(
						'sites',
						'c-ml-2 text-secondary text-4'
					);

					const removeIcon =
						Liferay.Util.getLexiconIconTpl('times-circle');

					let siteName;

					if (hasVirtualHost === 'true') {
						const warningIcon = Liferay.Util.getLexiconIconTpl(
							'warning-full',
							'text-warning'
						);

						const warningTitle = Liferay.Language.get(
							'this-site-is-not-included-in-the-companys-xml-sitemap-because-it-already-has-a-virtual-host'
						);

						siteName = `<span class="text-truncate">
							${entityName}
							<span
								class="c-ml-2 d-inline lfr-portal-tooltip"
								title="${warningTitle}"
							>
								${warningIcon}
							</span>
						</span>`;
					}
					else {
						siteName = `<span class="text-truncate">${entityName}</span>`;
					}

					const removeButton = `<button
						aria-label="${title}"
						class="btn btn-monospaced btn-outline-borderless btn-outline-secondary
							btn-sm lfr-portal-tooltip remove-button text-secondary" 
						data-rowid="${entityId}" 
						type="button" 
						title="${title}"
					>
						<span class="inline-item">${removeIcon}</span>
					</button>`;

					rowColumns.push(sitesIcon);
					rowColumns.push(siteName);
					rowColumns.push(removeButton);

					searchContainer.addRow(rowColumns, entityId);
					searchContainer.updateDataStore();

					groupIds.push(entityId);
					groupIdsInput!.value = groupIds.join(',');
				}
			},
			selectEventName,
			selectedData: [groupIds],
			title: sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('site')
			),
			url: groupSelectorURL,
		});
	};

	const onRemoveSite = searchContainerContentBox.delegate(
		'click',
		({currentTarget: removeButton}: {currentTarget: any}) => {
			searchContainer.deleteRow(
				removeButton.ancestor('tr'),
				removeButton.attr('data-rowid')
			);

			const groupIds = getGroupIds(searchContainer);

			groupIdsInput.value = groupIds.join(',');
		},
		'.remove-button'
	);

	const selectDelegate = delegate(
		selectSiteButton,
		'click',
		'.btn',
		onSelectClick
	);

	return {
		dispose() {
			onRemoveSite.dispose();
			selectDelegate.dispose();
		},
	};
}
