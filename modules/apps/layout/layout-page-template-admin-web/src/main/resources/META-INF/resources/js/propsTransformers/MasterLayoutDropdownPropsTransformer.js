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

import {
	openModal,
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';

const ACTIONS = {
	copyMasterLayout({copyMasterLayoutURL}) {
		send(copyMasterLayoutURL);
	},

	deleteMasterLayout({deleteMasterLayoutURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			send(deleteMasterLayoutURL);
		}
	},

	deleteMasterLayoutPreview({deleteMasterLayoutPreviewURL}) {
		send(deleteMasterLayoutPreviewURL);
	},

	discardDraft({discardDraftURL}) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
				)
			)
		) {
			send(discardDraftURL);
		}
	},

	markAsDefaultMasterLayout({markAsDefaultMasterLayoutURL, message}) {
		if (message !== '') {
			if (confirm(Liferay.Language.get(message))) {
				send(markAsDefaultMasterLayoutURL);
			}
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
