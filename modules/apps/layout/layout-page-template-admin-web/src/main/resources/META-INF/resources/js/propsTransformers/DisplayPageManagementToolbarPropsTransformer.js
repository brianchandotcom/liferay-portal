/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openCreationModal} from '@liferay/layout-js-components-web';
import {
	getCheckedCheckboxes,
	openSelectionModal,
	setFormValues,
} from 'frontend-js-web';

import openDeletePageTemplateModal from '../commands/openDeletePageTemplateModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const copySelectedEntries = (itemData) => {
		openSelectionModal({
			height: '70vh',
			onSelect: (selectedItem) => {
				const form = document.getElementById(
					`${portletNamespace}actionEntriesFm`
				);

				setFormValues(form, {
					copyPermissions: true,
					layoutPageTemplateCollectionsIds: getCheckedCheckboxes(
						document.getElementById(`${portletNamespace}fm`),
						'',
						`${portletNamespace}rowIdsLayoutPageTemplateCollection`
					),
					layoutPageTemplateEntriesIds: getCheckedCheckboxes(
						document.getElementById(`${portletNamespace}fm`),
						'',
						`${portletNamespace}rowIds`
					),
					layoutParentPageTemplateCollectionId:
						selectedItem.resourceid,
				});

				submitForm(form, itemData?.copySelectedEntriesURL);
			},
			selectEventName: 'selectFolder',
			size: 'md',
			title: Liferay.Language.get('copy-entries'),
			url: itemData.itemSelectorURL,
		});
	};

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

	const exportSelectedEntries = (itemData) => {
		const form = document.getElementById(
			`${portletNamespace}actionEntriesFm`
		);

		setFormValues(form, {
			layoutPageTemplateCollectionsIds: getCheckedCheckboxes(
				document.getElementById(`${portletNamespace}fm`),
				'',
				`${portletNamespace}rowIdsLayoutPageTemplateCollection`
			),
			layoutPageTemplateEntriesIds: getCheckedCheckboxes(
				document.getElementById(`${portletNamespace}fm`),
				'',
				`${portletNamespace}rowIds`
			),
		});

		submitForm(form, itemData?.exportSelectedEntriesURL);
	};

	const moveSelectedEntries = (itemData) => {
		openSelectionModal({
			height: '70vh',
			onSelect: (selectedItem) => {
				const form = document.getElementById(
					`${portletNamespace}actionEntriesFm`
				);

				setFormValues(form, {
					layoutPageTemplateCollectionsIds: getCheckedCheckboxes(
						document.getElementById(`${portletNamespace}fm`),
						'',
						`${portletNamespace}rowIdsLayoutPageTemplateCollection`
					),
					layoutPageTemplateEntriesIds: getCheckedCheckboxes(
						document.getElementById(`${portletNamespace}fm`),
						'',
						`${portletNamespace}rowIds`
					),
					layoutParentPageTemplateCollectionId:
						selectedItem.resourceid,
				});

				submitForm(form, itemData?.moveSelectedEntriesURL);
			},
			selectEventName: 'selectFolder',
			size: 'md',
			title: Liferay.Language.get('move-entries'),
			url: itemData.itemSelectorURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'copySelectedEntries') {
				copySelectedEntries(data);
			}
			else if (action === 'deleteSelectedEntries') {
				deleteSelectedEntries(data);
			}
			else if (action === 'exportSelectedEntries') {
				exportSelectedEntries(data);
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
