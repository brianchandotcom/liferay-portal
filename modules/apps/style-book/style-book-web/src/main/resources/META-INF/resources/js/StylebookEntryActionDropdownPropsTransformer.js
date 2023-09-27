/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	openConfirmModal,
	openSelectionModal,
	openSimpleInputModal,
	setFormValues,
} from 'frontend-js-web';

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
		openConfirmModal({
			message,
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, markAsDefaultStyleBookEntryURL);
				}
			},
		});
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
						setFormValues(form, {
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
		actions: (actions || []).map((item) => {
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
