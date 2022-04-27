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

import openDeleteFragmentModal from './openDeleteFragmentModal';

export default function propsTransformer({
	additionalProps: {
		copyFragmentEntryURL,
		deleteFragmentCompositionsAndFragmentEntriesURL,
		exportFragmentCompositionsAndFragmentEntriesURL,
		fragmentCollectionId,
		moveFragmentCompositionsAndFragmentEntriesURL,
		selectFragmentCollectionURL,
	},
	portletNamespace,
	...otherProps
}) {
	const addFragmentEntry = (itemData) => {
		openSimpleInputModal({
			dialogTitle: itemData?.title,
			formSubmitURL: itemData?.addFragmentEntryURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			namespace: `${portletNamespace}`,
		});
	};

	const copySelectedFragmentEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (!form) {
			return;
		}

		const fragmentEntryIds = Liferay.Util.listCheckedExcept(
			form,
			`${portletNamespace}allRowIds`
		);

		const fragmentCollectionIdElement = document.getElementById(
			`${portletNamespace}fragmentCollectionId`
		);

		if (fragmentCollectionIdElement) {
			fragmentCollectionIdElement.setAttribute(
				'value',
				fragmentCollectionId
			);
		}

		const fragmentEntryIdsElement = document.getElementById(
			`${portletNamespace}fragmentEntryIds`
		);

		if (fragmentEntryIdsElement) {
			fragmentEntryIdsElement.setAttribute('value', fragmentEntryIds);
		}

		const fragmentEntryForm = document.getElementById(
			`${portletNamespace}fragmentEntryFm`
		);

		if (fragmentEntryForm) {
			submitForm(fragmentEntryForm, copyFragmentEntryURL);
		}
	};

	const deleteFragmentCompositionsAndFragmentEntries = () => {
		openDeleteFragmentModal({
			multiple: true,
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(
						form,
						deleteFragmentCompositionsAndFragmentEntriesURL
					);
				}
			},
		});
	};

	const exportFragmentCompositionsAndFragmentEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			submitForm(form, exportFragmentCompositionsAndFragmentEntriesURL);
		}
	};

	const moveFragmentCompositionsAndFragmentEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (!form) {
			return;
		}

		const fragmentCompositionIds = Liferay.Util.listCheckedExcept(
			form,
			`${portletNamespace}allRowIds`,
			`${portletNamespace}rowIdsFragmentComposition`
		);

		const fragmentEntryIds = Liferay.Util.listCheckedExcept(
			form,
			`${portletNamespace}allRowIds`,
			`${portletNamespace}rowIdsFragmentEntry`
		);

		openSelectionModal({
			id: `${portletNamespace}selectFragmentCollection`,
			onSelect(selectedItem) {
				if (selectedItem) {
					const form = document.getElementById(
						`${portletNamespace}fragmentEntryFm`
					);

					if (!form) {
						return;
					}

					const fragmentCollectionIdElement = document.getElementById(
						`${portletNamespace}fragmentCollectionId`
					);

					if (fragmentCollectionIdElement) {
						fragmentCollectionIdElement.setAttribute(
							'value',
							selectedItem.id
						);
					}

					const fragmentCompositionIdsElement = document.getElementById(
						`${portletNamespace}fragmentCompositionIds`
					);

					if (fragmentCompositionIdsElement) {
						fragmentCompositionIdsElement.setAttribute(
							'value',
							fragmentCompositionIds
						);
					}

					const fragmentEntryIdsElement = document.getElementById(
						`${portletNamespace}fragmentEntryIds`
					);

					if (fragmentEntryIdsElement) {
						fragmentEntryIdsElement.setAttribute(
							'value',
							fragmentEntryIds
						);
					}

					submitForm(
						form,
						moveFragmentCompositionsAndFragmentEntriesURL
					);
				}
			},
			selectEventName: `${portletNamespace}selectFragmentCollection`,
			title: Liferay.Language.get('select-fragment-set'),
			url: selectFragmentCollectionURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item.data?.action;

			if (action === 'copySelectedFragmentEntries') {
				copySelectedFragmentEntries();
			}
			else if (
				action === 'deleteFragmentCompositionsAndFragmentEntries'
			) {
				deleteFragmentCompositionsAndFragmentEntries();
			}
			else if (
				action === 'exportFragmentCompositionsAndFragmentEntries'
			) {
				exportFragmentCompositionsAndFragmentEntries();
			}
			else if (
				action === 'moveFragmentCompositionsAndFragmentEntries'
			) {
				moveFragmentCompositionsAndFragmentEntries();
			}
		},
		onCreateButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'addFragmentEntry') {
				addFragmentEntry(data);
			}
		},
	};
}
