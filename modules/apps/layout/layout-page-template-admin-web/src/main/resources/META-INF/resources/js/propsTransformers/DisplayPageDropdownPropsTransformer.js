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
} from 'frontend-js-web';

import {MODAL_TYPES} from '../constants/modalTypes';
import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';
import openDisplayPageModal from '../modal/openDisplayPageModal.es';

const ACTIONS = {
	changeContentType({changeContentTypeURL, mappingTypes}, namespace) {
		openDisplayPageModal({
			formSubmitURL: changeContentTypeURL,
			mappingTypes,
			namespace,
			spritemap: getSpritemap(),
			title: Liferay.Language.get('change-content-type'),
			type: MODAL_TYPES.edit,
			warningMessage: Liferay.Language.get(
				'changing-the-content-type-may-cause-some-elements-of-the-display-page-template-to-lose-their-previous-mapping'
			),
		});
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

								ACTIONS[action](
									{...child.data, ...additionalProps},
									portletNamespace
								);
							}
						},
					};
				}),
			};
		}),
		portletNamespace,
	};
}
