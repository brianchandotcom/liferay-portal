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

import {openSelectionModal, openSimpleInputModal} from 'frontend-js-web';

import openDeleteStyleBookModal from './openDeleteStyleBookModal';

const ACTIONS = {
	copyStyleBookEntry({copyStyleBookEntryURL}) {
		submitForm(document.hrefFm, copyStyleBookEntryURL);
	},

	deleteStyleBookEntry({deleteStyleBookEntryURL}) {
		openDeleteStyleBookModal({
			onDelete: () => {
				submitForm(document.hrefFm, deleteStyleBookEntryURL);
			},
		});
	},

	deleteStyleBookEntryPreview({deleteStyleBookEntryPreviewURL}) {
		submitForm(document.hrefFm, deleteStyleBookEntryPreviewURL);
	},

	discardDraftStyleBookEntry({discardDraftStyleBookEntryURL}) {
		submitForm(document.hrefFm, discardDraftStyleBookEntryURL);
	},

	markAsDefaultStyleBookEntry({markAsDefaultStyleBookEntryURL, message}) {
		if (confirm(message)) {
			submitForm(document.hrefFm, markAsDefaultStyleBookEntryURL);
		}
	},

	renameStyleBookEntry(
		{styleBookEntryId, styleBookEntryName, updateStyleBookEntryURL},
		portletNamespace
	) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-style-book'),
			formSubmitURL: updateStyleBookEntryURL,
			idFieldName: 'id',
			idFieldValue: styleBookEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: styleBookEntryName,
			namespace: portletNamespace,
		});
	},

	updateStyleBookEntryPreview(
		{itemSelectorURL, styleBookEntryId},
		portletNamespace
	) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					const form = document.getElementById(
						`${portletNamespace}styleBookEntryPreviewFm`
					);

					if (form) {
						Liferay.Util.setFormValues(form, {
							fileEntryId: itemValue.fileEntryId,
							styleBookEntryId,
						});

						submitForm(form);
					}
				}
			},
			selectEventName: `${portletNamespace}changePreview`,
			title: Liferay.Language.get('style-book-thumbnail'),
			url: itemSelectorURL,
		});
	},
};

export default function propsTransformer({
	actions,
	portletNamespace,
	...otherProps
}) {
	const onClick = (event, item) => {
		const action = item.data?.action;

		if (action) {
			event.preventDefault();

			ACTIONS[action](item.data, portletNamespace);
		}
	};

	return {
		...otherProps,
		actions: actions.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => {
					return {
						...child,
						onClick: (event) => onClick(event, child),
					};
				}),
				onClick: item.items
					? () => {}
					: (event) => onClick(event, item),
			};
		}),
	};
}
