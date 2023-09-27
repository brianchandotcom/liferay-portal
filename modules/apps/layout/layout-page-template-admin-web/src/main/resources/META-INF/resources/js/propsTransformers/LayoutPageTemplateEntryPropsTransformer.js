/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	createPortletURL,
	openConfirmModal,
	openModal,
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';

const ACTIONS = {
	deleteLayoutPageTemplateEntry({deleteLayoutPageTemplateEntryURL}) {
		openDeletePageTemplateModal({
			onDelete: () => {
				send(deleteLayoutPageTemplateEntryURL);
			},
			title: Liferay.Language.get('page-template'),
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

	moveLayoutPageTemplateEntry(
		{itemSelectorURL, moveLayoutPageTemplateEntryURL},
		namespace
	) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (!selectedItem) {
					return;
				}

				const value = JSON.parse(selectedItem.value);

				const portletURL = new createPortletURL(
					moveLayoutPageTemplateEntryURL,
					{
						targetLayoutPageTemplateCollectionId:
							value.layoutPageTemplateCollectionId,
					}
				);

				send(portletURL.toString());
			},
			selectEventName: `${namespace}selectItem`,
			title: Liferay.Language.get('select-destination'),
			url: itemSelectorURL,
		});
	},

	permissionsLayoutPageTemplateEntry({
		permissionsLayoutPageTemplateEntryURL,
	}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsLayoutPageTemplateEntryURL,
		});
	},

	renameLayoutPageTemplateEntry(
		{
			idFieldName,
			idFieldValue,
			layoutPageTemplateEntryName,
			updateLayoutPageTemplateEntryURL,
		},
		namespace
	) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-display-page-template'),
			formSubmitURL: updateLayoutPageTemplateEntryURL,
			idFieldName,
			idFieldValue,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: layoutPageTemplateEntryName,
			namespace,
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

export default function LayoutPageTemplateEntryPropsTransformer({
	actions,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		actions: actions?.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => {
					return {
						...child,
						onClick(event) {
							const action = child.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action](child.data, portletNamespace);
							}
						},
					};
				}),
			};
		}),
		portletNamespace,
	};
}
