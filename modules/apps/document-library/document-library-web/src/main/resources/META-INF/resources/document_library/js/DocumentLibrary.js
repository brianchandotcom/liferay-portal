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

export default function DocumentLibrary({
	editEntryUrl,
	namespace,
	searchContainerId,
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
}
