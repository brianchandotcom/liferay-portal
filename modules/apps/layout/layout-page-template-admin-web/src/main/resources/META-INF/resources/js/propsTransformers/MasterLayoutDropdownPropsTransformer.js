/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	openConfirmModal,
	openModal,
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';

const ACTIONS = {
	copyMasterLayout({copyMasterLayoutURL}) {
		send(copyMasterLayoutURL);
	},

	deleteMasterLayout({deleteMasterLayoutURL}) {
		openDeletePageTemplateModal({
			onDelete: () => {
				send(deleteMasterLayoutURL);
			},
			title: Liferay.Language.get('master'),
		});
	},

	deleteMasterLayoutPreview({deleteMasterLayoutPreviewURL}) {
		send(deleteMasterLayoutPreviewURL);
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

	markAsDefaultMasterLayout({markAsDefaultMasterLayoutURL, message}) {
		if (message !== '') {
			openConfirmModal({
				message: Liferay.Language.get(message),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						send(markAsDefaultMasterLayoutURL);
					}
				},
			});
		}
		else {
			send(markAsDefaultMasterLayoutURL);
		}
	},

	permissionsMasterLayout({permissionsMasterLayoutURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsMasterLayoutURL,
		});
	},

	renameMasterLayout(
		{
			layoutPageTemplateEntryId,
			layoutPageTemplateEntryName,
			updateMasterLayoutURL,
		},
		namespace
	) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-master-page'),
			formSubmitURL: updateMasterLayoutURL,
			idFieldName: 'layoutPageTemplateEntryId',
			idFieldValue: layoutPageTemplateEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: layoutPageTemplateEntryName,
			namespace,
		});
	},

	updateMasterLayoutPreview(
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
							`${namespace}masterLayoutPreviewFm`
						)
					);
				}
			},
			selectEventName: Liferay.Util.ns(namespace, 'changePreview'),
			title: Liferay.Language.get('master-page-thumbnail'),
			url: itemSelectorURL,
		});
	},
};

function send(url) {
	submitForm(document.hrefFm, url);
}

export default function MasterLayoutDropdownPropsTransformer({
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
