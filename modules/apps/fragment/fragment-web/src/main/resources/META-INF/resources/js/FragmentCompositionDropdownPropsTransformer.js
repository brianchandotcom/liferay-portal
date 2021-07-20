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

const ACTIONS = {
	deleteFragmentComposition({deleteFragmentCompositionURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, deleteFragmentCompositionURL);
		}
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

					if (form) {
						Liferay.Util.setFormValues(form, {
							fragmentCollectionId: selectedItem.id,
							fragmentCompositionId,
						});
					}

					submitForm(form, moveFragmentCompositionURL);
				}
			},
			selectEventName: `${portletNamespace}selectFragmentCollection`,
			title: Liferay.Language.get('select-collection'),
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
						Liferay.Util.setFormValues(form, {
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
		actions: actions.map(transformAction),
	};
}
