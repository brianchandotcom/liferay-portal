/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openCreationModal} from '@liferay/layout-js-components-web';
import {
	createPortletURL,
	fetch,
	getCheckedCheckboxes,
	objectToFormData,
	openSelectionModal,
	openToast,
	sub,
} from 'frontend-js-web';

import openDeletePageTemplateModal from '../commands/openDeletePageTemplateModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedEntries = (itemData) => {
		openDeletePageTemplateModal({
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form, itemData?.deleteSelectedEntriesURL);
				}
			},
			title: Liferay.Language.get('entries'),
		});
	};

	const exportDisplayPages = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			submitForm(form, itemData?.exportDisplayPageURL);
		}
	};

	const moveSelectedEntries = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		const searchContainer = Liferay.SearchContainer.get(
			`${portletNamespace}displayPages`
		);

		const elementsSelected = searchContainer.select
			.getAllSelectedElements()
			.get('value').length;

		openSelectionModal({
			height: '70vh',
			onSelect: (selectedItems) => {
				fetch(itemData.moveSelectedEntriesURL, {
					body: objectToFormData({
						[`${portletNamespace}targetLayoutPageTemplateCollectionId`]: selectedItems.resourceid,
						[`${portletNamespace}layoutPageTemplateEntriesIds`]: getCheckedCheckboxes(
							form,
							'',
							`${portletNamespace}rowIds`
						),
						[`${portletNamespace}layoutPageTemplateCollectionsIds`]: getCheckedCheckboxes(
							form,
							'',
							`${portletNamespace}rowIdsLayoutPageTemplateCollection`
						),
					}),
					method: 'POST',
				})
					.then((response) => {
						if (!response.ok) {
							throw new Error();
						}

						window.location.reload();
					})
					.catch(
						({
							message = Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						}) => {
							openToast({
								message,
								type: 'danger',
							});
						}
					);
			},
			selectEventName: 'selectFolder',
			size: 'md',
			title: sub(
				Liferay.Language.get('move-x-elements-to'),
				elementsSelected
			),
			url: createPortletURL(itemData.itemSelectorURL, {
				selectedFolders: searchContainer.select
					.getAllSelectedElements()
					.get('value'),
			}),
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteSelectedEntries') {
				deleteSelectedEntries(data);
			}
			else if (action === 'exportDisplayPages') {
				exportDisplayPages(data);
			}
			else if (action === 'moveSelectedEntries') {
				moveSelectedEntries(data);
			}
		},
		onCreationMenuItemClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'addDisplayPageCollection') {
				openCreationModal({
					buttonLabel: Liferay.Language.get('create'),
					formSubmitURL: data.addDisplayPageCollectionURL,
					heading: Liferay.Language.get('new-folder'),
					portletNamespace,
				});
			}
		},
	};
}
