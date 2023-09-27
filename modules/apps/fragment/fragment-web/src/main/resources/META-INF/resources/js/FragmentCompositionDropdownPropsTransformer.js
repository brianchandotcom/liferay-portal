/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	openSelectionModal,
	openSimpleInputModal,
	setFormValues,
} from 'frontend-js-web';

import openDeleteFragmentModal from './openDeleteFragmentModal';

const ACTIONS = {
	deleteFragmentComposition({deleteFragmentCompositionURL}) {
		openDeleteFragmentModal({
			onDelete: () => {
				submitForm(document.hrefFm, deleteFragmentCompositionURL);
			},
		});
	},

	deleteFragmentCompositionPreview({deleteFragmentCompositionPreviewURL}) {
		submitForm(document.hrefFm, deleteFragmentCompositionPreviewURL);
	},

	moveFragmentComposition(
		{
			fragmentCompositionId,
			moveFragmentCompositionURL,
			selectFragmentCollectionURL,
		},
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
							fragmentCompositionId,
						});
					}

					submitForm(form, moveFragmentCompositionURL);
				}
			},
			selectEventName: `${portletNamespace}selectFragmentCollection`,
			title: Liferay.Language.get('select-fragment-set'),
			url: selectFragmentCollectionURL,
		});
	},

	renameFragmentComposition(
		{
			fragmentCompositionId,
			fragmentCompositionName,
			renameFragmentCompositionURL,
		},
		portletNamespace
	) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-fragment'),
			formSubmitURL: renameFragmentCompositionURL,
			idFieldName: 'id',
			idFieldValue: fragmentCompositionId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: fragmentCompositionName,
			namespace: portletNamespace,
		});
	},

	updateFragmentCompositionPreview(
		{fragmentCompositionId, itemSelectorURL},
		portletNamespace
	) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					const form = document.getElementById(
						`${portletNamespace}fragmentCompositionPreviewFm`
					);

					if (form) {
						setFormValues(form, {
							fileEntryId: itemValue.fileEntryId,
							fragmentCompositionId,
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

					ACTIONS[action](actionItem.data, portletNamespace);
				}
			},
		};
	};

	return {
		...props,
		actions: (actions || []).map(transformAction),
	};
}
