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
	openConfirmModal,
	openModal,
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';

import openDeleteLayoutModal from './openDeleteLayoutModal';

const ACTIONS = {
	copyLayoutUtilityPageEntry({copyLayoutUtilityPageEntryURL}) {
		send(copyLayoutUtilityPageEntryURL);
	},

	deleteLayoutUtilityPageEntry({
		deleteLayoutUtilityPageEntryMessage,
		deleteLayoutUtilityPageEntryURL,
	}) {
		openDeleteLayoutModal({
			message: deleteLayoutUtilityPageEntryMessage,
			onDelete: () => {
				send(deleteLayoutUtilityPageEntryURL);
			},
			title: Liferay.Language.get('utility-pages'),
		});
	},

	deleteLayoutUtilityPageEntryPreview({
		deleteLayoutUtilityPageEntryPreviewURL,
	}) {
		send(deleteLayoutUtilityPageEntryPreviewURL);
	},

	markAsDefaultLayoutUtilityPageEntry({
		markAsDefaultLayoutUtilityPageEntryURL,
		message,
	}) {
		if (message !== '') {
			openConfirmModal({
				message: Liferay.Language.get(message),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						send(markAsDefaultLayoutUtilityPageEntryURL);
					}
				},
			});
		}
		else {
			send(markAsDefaultLayoutUtilityPageEntryURL);
		}
	},

	permissionsLayoutUtilityPageEntry({permissionsLayoutUtilityPageEntryURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsLayoutUtilityPageEntryURL,
		});
	},

	renameLayoutUtilityPageEntry(
		{
			layoutUtilityPageEntryId,
			layoutUtilityPageEntryName,
			updateLayoutUtilityPageEntryURL,
		},
		namespace
	) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-utility-page'),
			formSubmitURL: updateLayoutUtilityPageEntryURL,
			idFieldName: 'layoutUtilityPageEntryId',
			idFieldValue: layoutUtilityPageEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: layoutUtilityPageEntryName,
			namespace,
		});
	},

	unmarkAsDefaultLayoutUtilityPageEntry({
		unmarkAsDefaultLayoutUtilityPageEntryURL,
	}) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-unmark-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					send(unmarkAsDefaultLayoutUtilityPageEntryURL);
				}
			},
		});
	},

	updateLayoutUtilityPageEntryPreview(
		{itemSelectorURL, layoutUtilityPageEntryId},
		namespace
	) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					document.getElementById(
						`${namespace}layoutUtilityPageEntryId`
					).value = layoutUtilityPageEntryId;

					document.getElementById(`${namespace}fileEntryId`).value =
						itemValue.fileEntryId;

					submitForm(
						document.getElementById(
							`${namespace}layoutUtilityPageEntryPreviewFm`
						)
					);
				}
			},
			selectEventName: Liferay.Util.ns(namespace, 'changePreview'),
			title: Liferay.Language.get('utility-page-thumbnail'),
			url: itemSelectorURL,
		});
	},
};

function send(url) {
	submitForm(document.hrefFm, url);
}

export default function LayoutUtilityPageEntryDropdownPropsTransformer({
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
