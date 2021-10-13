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
	deleteLayoutPageTemplateEntry({deleteLayoutPageTemplateEntryURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			send(deleteLayoutPageTemplateEntryURL);
		}
	},

	deleteLayoutPageTemplateEntryPreview({
		deleteLayoutPageTemplateEntryPreviewURL,
	}) {
		send(deleteLayoutPageTemplateEntryPreviewURL);
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

	moveLayoutPageTemplateEntry(
		{itemSelectorURL, moveLayoutPageTemplateEntryURL},
		namespace
	) {
		Liferay.Util.openSelectionModal({
			onSelect: (selectedItem) => {
				if (!selectedItem) {
					return;
				}

				var value = JSON.parse(selectedItem.value);

				var portletURL = new Liferay.Util.PortletURL.createPortletURL(
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
