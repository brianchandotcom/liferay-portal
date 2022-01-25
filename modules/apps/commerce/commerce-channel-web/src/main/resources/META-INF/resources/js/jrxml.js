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

export default function ({itemSelectorURL, namespace}) {
	const fileEntryIdInput = document.getElementById(`${namespace}fileEntryId`);
	const fileEntryNameInput = document.getElementById(
		`${namespace}fileEntryNameInput`
	);
	const fileEntryRemoveIcon = document.getElementById(
		`${namespace}fileEntryRemoveIcon`
	);
	const selectFileButton = document.getElementById(
		`${namespace}selectFileButton`
	);

	if (fileEntryNameInput && fileEntryRemoveIcon && selectFileButton) {
		selectFileButton.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openSelectionModal({
				onSelect: (selectedItem) => {
					if (!selectedItem) {
						return;
					}

					const value = JSON.parse(selectedItem.value);

					if (fileEntryIdInput) {
						fileEntryIdInput.value = value.fileEntryId;
					}

					fileEntryRemoveIcon.classList.remove('hide');

					fileEntryNameInput.innerHTML =
						'<a>' + Liferay.Util.escape(value.title) + '</a>';
				},
				selectEventName: 'addFileEntry',
				title: Liferay.Language.get('select-file'),
				url: itemSelectorURL,
			});
		});

		fileEntryRemoveIcon.addEventListener('click', (event) => {
			event.preventDefault();

			if (fileEntryIdInput) {
				fileEntryIdInput.value = 0;
			}

			fileEntryNameInput.innerText = '';

			fileEntryRemoveIcon.classList.add('hide');
		});
	}
}
