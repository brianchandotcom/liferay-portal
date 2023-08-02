/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

export default function ({
	namespace,
	removeButton,
	selectDDMStructureURL,
	workflowDefinitions,
	workflowEnabled,
}) {
	const searchContainer = Liferay.SearchContainer.get(
		`${namespace}ddmStructuresSearchContainer`
	);

	const selectDDMStructureButton = document.getElementById(
		`${namespace}selectDDMStructure`
	);

	const handleSelectDDMStructureButtonClick = () => {
		openSelectionModal({
			height: '70vh',
			iframeBodyCssClass: '',
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					const removeStructureButton = removeButton.replace(
						/REMOVE_BUTTON_ROW_ID/g,
						itemValue.ddmstructureid
					);

					if (workflowEnabled) {
						searchContainer.addRow(
							[
								itemValue.name,
								workflowDefinitions.replace(
									/WORKFLOW_NAME/g,
									'workflowDefinition' +
										itemValue.ddmstructureid
								),
								removeStructureButton,
							],
							itemValue.ddmstructureid
						);
					}
					else {
						searchContainer.addRow(
							[itemValue.name, removeStructureButton],
							itemValue.ddmstructureid
						);
					}

					searchContainer.updateDataStore();
				}
			},
			selectEventName: `${namespace}selectDDMStructure`,
			title: Liferay.Language.get('structures'),
			url: selectDDMStructureURL,
		});
	};

	searchContainer.get('contentBox').delegate(
		'click',
		(event) => {
			const link = event.currentTarget;

			const tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.attr('data-rowId'));
		},
		'.modify-link'
	);

	selectDDMStructureButton.addEventListener(
		'click',
		handleSelectDDMStructureButtonClick
	);

	return {
		dispose() {
			selectDDMStructureButton.removeEventListener(
				'click',
				handleSelectDDMStructureButtonClick
			);
		},
	};
}
