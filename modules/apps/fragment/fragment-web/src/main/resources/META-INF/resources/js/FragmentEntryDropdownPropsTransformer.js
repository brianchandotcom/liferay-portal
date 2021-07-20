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
	copyFragmentEntry(
		{copyFragmentEntryURL, fragmentCollectionId, fragmentEntryId},
		portletNamespace
	) {
		const form = document.getElementById(
			`${portletNamespace}fragmentEntryFm`
		);

		if (form) {
			Liferay.Util.setFormValues(form, {
				fragmentCollectionId,
				fragmentEntryIds: fragmentEntryId,
			});

			submitForm(form, copyFragmentEntryURL);
		}
	},

	copyToFragmentEntry(
		{copyFragmentEntryURL, fragmentEntryId, selectFragmentCollectionURL},
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
							fragmentEntryIds: fragmentEntryId,
						});
					}

					submitForm(form, copyFragmentEntryURL);
				}
			},
			selectEventName: `${portletNamespace}selectFragmentCollection`,
			title: Liferay.Language.get('select-collection'),
			url: selectFragmentCollectionURL,
		});
	},

	deleteDraftFragmentEntry({deleteDraftFragmentEntryURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, deleteDraftFragmentEntryURL);
		}
	},

	deleteFragmentEntry({deleteFragmentEntryURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, deleteFragmentEntryURL);
		}
	},

	deleteFragmentEntryPreview({deleteFragmentEntryPreviewURL}) {
		submitForm(document.hrefFm, deleteFragmentEntryPreviewURL);
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

					if (form) {
						Liferay.Util.setFormValues(form, {
							fragmentCollectionId: selectedItem.id,
							fragmentEntryIds: fragmentEntryId,
						});
					}

					submitForm(form, moveFragmentEntryURL);
				}
			},
			selectEventName: `${portletNamespace}selectFragmentCollection`,
			title: Liferay.Language.get('select-collection'),
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
						Liferay.Util.setFormValues(form, {
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
