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

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';
import openConfirm from '../util/openConfirm';

const ACTIONS = {
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
		openConfirm({
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
			openConfirm({
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
		openConfirm({
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
