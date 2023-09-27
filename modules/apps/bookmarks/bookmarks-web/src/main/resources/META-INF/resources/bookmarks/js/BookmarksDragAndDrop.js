/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {setFormValues} from 'frontend-js-web';

export default function BookmarksDragAndDrop({
	editEntryURL,
	moveEntryURL,
	namespace,
	searchContainerId,
}) {
	const searchContainer = Liferay.SearchContainer.get(
		`${namespace}${searchContainerId}`
	);

	searchContainer.registerAction('move-to-folder', moveToFolder);

	searchContainer.registerAction('move-to-trash', moveToTrash);

	function moveToTrash() {
		processAction('move_to_trash', editEntryURL);
	}

	function moveToFolder(object) {
		const {selectedItems, targetItem: dropTarget} = object;

		const folderId = dropTarget.getAttribute('data-folder-id');

		if (folderId) {
			if (
				searchContainer.select ||
				selectedItems.indexOf(
					dropTarget.querySelector('input[type=checkbox]')
				)
			) {
				const form = document.getElementById(`${namespace}fm`);

				form.querySelector(`#${namespace}newFolderId`).value = folderId;

				setFormValues(form, {
					newFolderId: folderId,
				});

				processAction('move', moveEntryURL);
			}
		}
	}

	function processAction(action, url) {
		const form = document.getElementById(`${namespace}fm`);

		form.setAttribute('method', 'POST');

		setFormValues(form, {
			cmd: action,
			redirect: location.href,
		});

		submitForm(form, url);
	}
}
