/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getSpritemap} from '@liferay/frontend-icons-web';
import {
	openConfirmModal,
	openModal,
	openSelectionModal,
	openSimpleInputModal,
	setFormValues,
	sub,
} from 'frontend-js-web';

import openContentTypeModal from '../commands/openContentTypeModal';
import openDeletePageTemplateModal from '../commands/openDeletePageTemplateModal';
import openInUseModal from '../commands/openInUseModal';
import {MODAL_TYPES} from '../constants/modalTypes';

const ACTIONS = {
	changeContentType(
		{
			assetType,
			changeContentTypeURL,
			classNameId,
			classTypeId,
			hasMissingType,
			mappingTypes,
			viewUsagesURL,
		},
		namespace
	) {
		if (viewUsagesURL) {
			openInUseModal({assetType, status: 'info', viewUsagesURL});
		}
		else {
			openContentTypeModal({
				description: hasMissingType
					? Liferay.Language.get(
							'this-display-page-template-does-not-have-any-content-type-assigned-you-must-select-one-to-edit-it'
						)
					: '',
				disableWarning: Boolean(hasMissingType) || !assetType,
				formSubmitURL: changeContentTypeURL,
				mappingTypes,
				namespace,
				selectedSubtype: classTypeId,
				selectedType: classNameId,
				spritemap: getSpritemap(),
				title: hasMissingType
					? Liferay.Language.get('select-content-type')
					: Liferay.Language.get('change-content-type'),
				type: MODAL_TYPES.edit,
				warningMessage: Liferay.Language.get(
					'changing-the-content-type-may-cause-some-elements-of-the-display-page-template-to-lose-their-previous-mapping'
				),
			});
		}
	},

	copyDisplayPage({copyDisplayPageURL}) {
		send(copyDisplayPageURL);
	},

	deleteDisplayPage({deleteDisplayPageMessage, deleteDisplayPageURL}) {
		openDeletePageTemplateModal({
			message: deleteDisplayPageMessage,
			onDelete: () => {
				send(deleteDisplayPageURL);
			},
			title: Liferay.Language.get('display-page-template'),
		});
	},

	deleteLayoutPageTemplateEntryPreview({
		deleteLayoutPageTemplateEntryPreviewURL,
	}) {
		send(deleteLayoutPageTemplateEntryPreviewURL);
	},

	discardDraft({discardDraftURL}) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					send(discardDraftURL);
				}
			},
		});
	},

	markAsDefaultDisplayPage({markAsDefaultDisplayPageURL, message}) {
		if (message !== '') {
			openConfirmModal({
				message: Liferay.Language.get(message),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						send(markAsDefaultDisplayPageURL);
					}
				},
			});
		}
		else {
			send(markAsDefaultDisplayPageURL);
		}
	},

	moveDisplayPage(
		{
			itemSelectorURL,
			layoutPageTemplateEntryId,
			layoutPageTemplateEntryName,
			moveSelectedDisplayPageURL,
		},
		portletNamespace
	) {
		openSelectionModal({
			height: '70vh',
			onSelect: (selectedItem) => {
				const form = document.getElementById(
					`${portletNamespace}actionEntriesFm`
				);

				setFormValues(form, {
					copyPermissions: true,
					layoutPageTemplateEntriesIds: layoutPageTemplateEntryId,
					layoutParentPageTemplateCollectionId:
						selectedItem.resourceid,
				});

				submitForm(form, moveSelectedDisplayPageURL);
			},
			selectEventName: 'selectFolder',
			size: 'md',
			title: sub(
				Liferay.Language.get('move-x-to'),
				`"${layoutPageTemplateEntryName}"`
			),
			url: itemSelectorURL,
		});
	},

	permissionsDisplayPage({permissionsDisplayPageURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsDisplayPageURL,
		});
	},

	renameDisplayPage(
		{
			layoutPageTemplateEntryId,
			layoutPageTemplateEntryName,
			updateDisplayPageURL,
		},
		namespace
	) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-display-page-template'),
			formSubmitURL: updateDisplayPageURL,
			idFieldName: 'layoutPageTemplateEntryId',
			idFieldValue: layoutPageTemplateEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: layoutPageTemplateEntryName,
			namespace,
		});
	},

	unmarkAsDefaultDisplayPage({unmarkAsDefaultDisplayPageURL}) {
		openConfirmModal({
			message: Liferay.Language.get('unmark-default-confirmation'),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					send(unmarkAsDefaultDisplayPageURL);
				}
			},
		});
	},

	updateLayoutPageTemplateEntryPreview(
		{itemSelectorURL, layoutPageTemplateEntryId},
		namespace
	) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					document.getElementById(
						`${namespace}layoutPageTemplateEntryId`
					).value = layoutPageTemplateEntryId;

					document.getElementById(`${namespace}fileEntryId`).value =
						itemValue.fileEntryId;

					submitForm(
						document.getElementById(
							`${namespace}layoutPageTemplateEntryPreviewFm`
						)
					);
				}
			},
			selectEventName: Liferay.Util.ns(namespace, 'changePreview'),
			title: Liferay.Language.get('page-template-thumbnail'),
			url: itemSelectorURL,
		});
	},
};

function send(url) {
	submitForm(document.hrefFm, url);
}

export default function DisplayPageDropdownPropsTransformer({
	actions,
	additionalProps,
	inputName,
	inputValue,
	portletNamespace,
	title,
	...otherProps
}) {
	const updateItem = (item) => {
		const newItem = {
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action](
						{...item.data, ...additionalProps},
						portletNamespace
					);
				}
			},
		};

		if (Array.isArray(item.items)) {
			newItem.items = newItem.items.map(updateItem);
		}

		return newItem;
	};

	return {
		...otherProps,
		actions: actions?.map(updateItem),
		checkboxProps: {
			'aria-label': sub(Liferay.Language.get('select-x'), title),
			'name': inputName,
			'value': inputValue,
		},
		title,
	};
}
