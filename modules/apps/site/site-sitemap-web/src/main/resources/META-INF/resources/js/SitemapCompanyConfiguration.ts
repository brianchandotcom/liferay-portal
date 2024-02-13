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

					const removeIcon = Liferay.Util.getLexiconIconTpl(
						'times-circle'
					);

					const removeButton = `<button
					aria-label="${title}"
					class="btn btn-monospaced btn-sm lfr-portal-tooltip remove-button" 
					data-rowid="${entityId}" 
					type="button" 
					title="${title}">
					<span class="inline-item">${removeIcon}</span>
					</button>`;

					rowColumns.push(sitesIcon);
					rowColumns.push(entityName);
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
