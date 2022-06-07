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

const TPL_MOVE_FORM =
	'<form action="{actionUrl}" class="hide" method="POST"><input name="{namespace}cmd" value="move"/>' +
	'<input name="{namespace}newFolderId" value="{newFolderId}"/>' +
	'<input name="{namespace}{parameterName}" value="{parameterValue}"/>' +
	'<input name="{namespace}redirect" value="{redirectUrl}"/>' +
	'</form>';

export default function DocumentLibrary({
	editEntryUrl,
	namespace,
	searchContainerId,
	selectFolderURL,
}) {
	let searchContainer;

	const form = document[`${namespace}fm2`];

	Liferay.componentReady(`${namespace}${searchContainerId}`).then(
		(searchContainerComponent) => {
			searchContainer = searchContainerComponent;

			searchContainer.registerAction('move-to-folder', _moveToFolder);
			searchContainer.registerAction('move-to-trash', _moveToTrash);
			searchContainer.on('rowToggled', _handleSearchContainerRowToggled);
		}
	);

	function _handleSearchContainerRowToggled() {
		const bulkSelection =
			searchContainer.select &&
			searchContainer.select.get('bulkSelection');

		form.elements[`${namespace}selectAll`].value = bulkSelection;
	}

	function _moveCurrentSelection(newFolderId) {
		form.action = editEntryUrl;
		form.method = 'POST';
		form.enctype = 'multipart/form-data';

		form.elements[`${namespace}cmd`].value = 'move';
		form.elements[`${namespace}newFolderId`].value = newFolderId;

		submitForm(form, editEntryUrl, false);
	}

	function _moveSingleElement(newFolderId, parameterName, parameterValue) {
		const redirectUrl = form.elements[`${namespace}redirect`].value;

		const newForm = document.createElement('div');

		newForm.innerHTML = Liferay.Util.sub(TPL_MOVE_FORM, {
			actionUrl: editEntryUrl,
			namespace,
			newFolderId,
			parameterName,
			parameterValue,
			redirectUrl,
		});

		const formNode = newForm.firstElementChild;

		form.append(formNode);

		submitForm(formNode, editEntryUrl, false);
	}

	function _moveToFolder(object) {
		const dropTarget = object.targetItem;

		const selectedItems = object.selectedItems;

		const folderId = dropTarget.attr('data-folder-id');

		if (folderId) {
			if (
				!searchContainer.select ||
				selectedItems.indexOf(dropTarget.one('input[type=checkbox]'))
			) {
				_moveCurrentSelection(folderId);
			}
		}
	}

	function _moveToTrash() {
		const action = 'move_to_trash';

		if (form.elements[`${namespace}javax-portlet-action`]) {
			form.elements[`${namespace}javax-portlet-action`].value = action;
		}
		else {
			form.elements[`${namespace}cmd`].value = action;
		}

		submitForm(form, editEntryUrl, false);
	}

	window[`${namespace}move`] = function (
		selectedItems,
		parameterName,
		parameterValue
	) {
		const dialogTitle =
			selectedItems === 1
				? Liferay.Language.get('select-destination-folder-for-x-item')
				: Liferay.Language.get('select-destination-folder-for-x-items');

		Liferay.Util.openSelectionModal({
			height: '480px',
			id: namespace,
			onSelect: (selectedItem) => {
				if (parameterName && parameterValue) {
					_moveSingleElement(
						selectedItem.folderid,
						parameterName,
						parameterValue
					);
				}
				else {
					_moveCurrentSelection(selectedItem.folderid);
				}
			},
			selectEventName: `${namespace}selectFolder`,
			size: 'lg',
			title: Liferay.Util.sub(dialogTitle, [selectedItems]),
			url: selectFolderURL,
		});
	};
}
