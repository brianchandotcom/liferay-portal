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
	addParams,
	navigate,
	openModal,
	openSelectionModal,
} from 'frontend-js-web';

import {collectDigitalSignature} from './digital-signature/DigitalSignatureUtil';

export default function propsTransformer({
	additionalProps: {
		collectDigitalSignaturePortlet,
		downloadEntryURL,
		editEntryURL,
		folderConfiguration,
		openViewMoreFileEntryTypesURL,
		selectFileEntryTypeURL,
		selectFolderURL,
		trashEnabled,
		viewFileEntryTypeURL,
	},
	portletNamespace,
	...otherProps
}) {
	const getAllSelectedElements = () => {
		const searchContainer = Liferay.SearchContainer.get(
			otherProps.searchContainerId
		);

		return searchContainer.select.getAllSelectedElements();
	};

	const processAction = (action, url) => {
		if (!action) {
			return;
		}

		const form = document.getElementById(`${portletNamespace}fm2`);

		if (!form) {
			return;
		}

		form.setAttribute('method', 'post');

		const actionInputElement = form.querySelector(
			`#${portletNamespace}javax-portlet-action`
		);

		if (actionInputElement) {
			actionInputElement.setAttribute('value', action);
		}

		const commandInputElement = form.querySelector(
			`#${portletNamespace}cmd`
		);

		if (commandInputElement) {
			commandInputElement.setAttribute('value', action);
		}

		submitForm(form, url, false);
	};

	const checkIn = () => {
		Liferay.componentReady(
			`${portletNamespace}DocumentLibraryCheckinModal`
		).then((documentLibraryCheckinModal) => {
			documentLibraryCheckinModal.open((versionIncrease, changeLog) => {
				const form = document.getElementById(`${portletNamespace}fm2`);

				if (!form) {
					return;
				}

				const changeLogInput = form.querySelector(
					`#${portletNamespace}changeLog`
				);

				if (changeLogInput) {
					changeLogInput.setAttribute('value', changeLog);
				}

				const versionIncreaseInput = form.querySelector(
					`#${portletNamespace}versionIncrease`
				);

				if (versionIncreaseInput) {
					versionIncreaseInput.setAttribute('value', versionIncrease);
				}

				processAction('checkin', editEntryURL);
			});
		});
	};

	const deleteEntries = () => {
		let action;

		if (trashEnabled) {
			action = 'move_to_trash';
		}
		else if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-entries'
				)
			)
		) {
			action = 'delete';
		}

		processAction(action, editEntryURL);
	};

	const editCategories = () => {
		const searchContainer = Liferay.SearchContainer.get(
			otherProps.searchContainerId
		);

		Liferay.componentReady(
			`${portletNamespace}EditCategoriesComponent`
		).then((editCategoriesComponent) => {
			const bulkSelection = searchContainer.select?.get('bulkSelection');

			const selectedFileEntries = searchContainer.select
				.getAllSelectedElements()
				.get('value');

			editCategoriesComponent.open(
				selectedFileEntries,
				bulkSelection,
				folderConfiguration.defaultParentFolderId
			);
		});
	};

	const editTags = () => {
		const searchContainer = Liferay.SearchContainer.get(
			otherProps.searchContainerId
		);

		Liferay.componentReady(`${portletNamespace}EditTagsComponent`).then(
			(editTagsComponent) => {
				const bulkSelection = searchContainer.select?.get(
					'bulkSelection'
				);

				const selectedFileEntries = searchContainer.select
					.getAllSelectedElements()
					.get('value');

				editTagsComponent.open(
					selectedFileEntries,
					bulkSelection,
					folderConfiguration.defaultParentFolderId
				);
			}
		);
	};

	const move = () => {
		const searchContainer = Liferay.SearchContainer.get(
			otherProps.searchContainerId
		);

		let selectedItems = 0;

		if (searchContainer.select) {
			selectedItems = searchContainer.select
				.getAllSelectedElements()
				.filter(':enabled')
				.size();
		}

		const dialogTitle =
			selectedItems === 1
				? Liferay.Language.get('select-destination-folder-for-x-item')
				: Liferay.Language.get('select-destination-folder-for-x-items');

		openSelectionModal({
			height: '480px',
			id: `${portletNamespace}selectFolder`,
			onSelect(selectedItem) {
				const newFolderId = selectedItem.folderid;

				const form = document.getElementById(`${portletNamespace}fm2`);

				if (!form) {
					return;
				}

				form.setAttribute('action', editEntryURL);
				form.setAttribute('enctype', 'multipart/form-data');
				form.setAttribute('method', 'post');

				const cmdInput = form.querySelector(`#${portletNamespace}cmd`);

				if (cmdInput) {
					cmdInput.setAttribute('value', 'move');
				}

				const newFolderIdInput = form.querySelector(
					`#${portletNamespace}newFolderId`
				);

				if (newFolderIdInput) {
					newFolderIdInput.setAttribute('value', newFolderId);
				}

				submitForm(form, editEntryURL, false);
			},
			selectEventName: `${portletNamespace}selectFolder`,
			size: 'lg',
			title: Liferay.Util.sub(dialogTitle, [selectedItems]),
			url: selectFolderURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item?.data?.action;

			if (action === 'checkin') {
				checkIn();
			}
			else if (action === 'checkout') {
				processAction('checkout', editEntryURL);
			}
			else if (action === 'collectDigitalSignature') {
				collectDigitalSignature(
					getAllSelectedElements().get('value'),
					collectDigitalSignaturePortlet
				);
			}
			else if (action === 'deleteEntries') {
				deleteEntries();
			}
			else if (action === 'download') {
				processAction('download', downloadEntryURL);
			}
			else if (action === 'editCategories') {
				editCategories();
			}
			else if (action === 'editTags') {
				editTags();
			}
			else if (action === 'move') {
				move();
			}
		},
		onFilterDropdownItemClick(event, {item}) {
			if (item?.data?.action === 'openDocumentTypesSelector') {
				openSelectionModal({
					onSelect(selectedItem) {
						if (selectedItem) {
							const url = addParams(
								`${portletNamespace}fileEntryTypeId=${selectedItem.value}`,
								viewFileEntryTypeURL
							);
							navigate(url);
						}
					},
					selectEventName: `${portletNamespace}selectFileEntryType`,
					title: Liferay.Language.get('select-document-type'),
					url: selectFileEntryTypeURL,
				});
			}
		},
		onShowMoreButtonClick() {
			openModal({
				title: Liferay.Language.get('more'),
				url: openViewMoreFileEntryTypesURL,
			});
		},
	};
}
