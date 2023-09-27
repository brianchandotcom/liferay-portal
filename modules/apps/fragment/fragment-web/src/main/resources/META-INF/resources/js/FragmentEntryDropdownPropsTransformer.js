/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import {
	openConfirmModal,
	openSelectionModal,
	openSimpleInputModal,
	setFormValues,
} from 'frontend-js-web';

import CopyFragmentModal from './CopyFragmentModal';
import openDeleteFragmentModal from './openDeleteFragmentModal';

const ACTIONS = {
	copyFragmentEntry(
		{copyFragmentEntryURL, fragmentCollectionId, fragmentEntryId},
		portletNamespace
	) {
		const form = document.getElementById(
			`${portletNamespace}fragmentEntryFm`
		);

		if (form) {
			setFormValues(form, {
				fragmentCollectionId,
				fragmentEntryIds: fragmentEntryId,
			});

			submitForm(form, copyFragmentEntryURL);
		}
	},

	copyToFragmentEntry(
		{copyFragmentEntryURL, fragmentEntryId},
		portletNamespace,
		fragmentCollections
	) {
		render(
			CopyFragmentModal,
			{
				copyFragmentEntriesURL: copyFragmentEntryURL,
				fragmentCollections,
				fragmentEntryIds: [fragmentEntryId],
				portletNamespace,
			},
			document.createElement('div')
		);
	},

	deleteDraftFragmentEntry({deleteDraftFragmentEntryURL}) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, deleteDraftFragmentEntryURL);
				}
			},
		});
	},

	deleteFragmentEntry({deleteFragmentEntryURL}) {
		openDeleteFragmentModal({
			onDelete: () => {
				submitForm(document.hrefFm, deleteFragmentEntryURL);
			},
		});
	},

	deleteFragmentEntryPreview({deleteFragmentEntryPreviewURL}) {
		submitForm(document.hrefFm, deleteFragmentEntryPreviewURL);
	},

	markAsCacheableFragmentEntry({markAsCacheableFragmentEntryURL}) {
		openConfirmModal({
			message: Liferay.Language.get('cacheable-fragment-help'),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(
						document.hrefFm,
						markAsCacheableFragmentEntryURL
					);
				}
			},
		});
	},

	moveFragmentEntry(
		{fragmentEntryId, moveFragmentEntryURL, selectFragmentCollectionURL},
		portletNamespace
	) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const form = document.getElementById(
						`${portletNamespace}fragmentEntryFm`
					);

					const itemValue = JSON.parse(selectedItem.value);

					if (form) {
						setFormValues(form, {
							fragmentCollectionId:
								itemValue.fragmentCollectionId,
							fragmentEntryIds: fragmentEntryId,
						});
					}

					submitForm(form, moveFragmentEntryURL);
				}
			},
			selectEventName: `${portletNamespace}selectFragmentCollection`,
			title: Liferay.Language.get('select-fragment-set'),
			url: selectFragmentCollectionURL,
		});
	},

	renameFragmentEntry(
		{fragmentEntryId, fragmentEntryName, updateFragmentEntryURL},
		portletNamespace
	) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-fragment'),
			formSubmitURL: updateFragmentEntryURL,
			idFieldName: 'id',
			idFieldValue: fragmentEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: fragmentEntryName,
			namespace: portletNamespace,
		});
	},

	unmarkAsCacheableFragmentEntry({unmarkAsCacheableFragmentEntryURL}) {
		submitForm(document.hrefFm, unmarkAsCacheableFragmentEntryURL);
	},

	updateFragmentEntryPreview(
		{fragmentEntryId, itemSelectorURL},
		portletNamespace
	) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					const form = document.getElementById(
						`${portletNamespace}fragmentEntryPreviewFm`
					);

					if (form) {
						setFormValues(form, {
							fileEntryId: itemValue.fileEntryId,
							fragmentEntryId,
						});

						submitForm(form);
					}
				}
			},
			selectEventName: `${portletNamespace}changePreview`,
			title: Liferay.Language.get('fragment-thumbnail'),
			url: itemSelectorURL,
		});
	},
};

export default function propsTransformer({
	actions,
	additionalProps: {fragmentCollections},
	portletNamespace,
	...props
}) {
	const transformAction = (actionItem) => {
		if (actionItem.type === 'group') {
			return {
				...actionItem,
				items: actionItem.items?.map(transformAction),
			};
		}

		return {
			...actionItem,
			onClick(event) {
				const action = actionItem.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action](
						actionItem.data,
						portletNamespace,
						fragmentCollections
					);
				}
			},
		};
	};

	return {
		...props,
		actions: (actions || []).map(transformAction),
	};
}
