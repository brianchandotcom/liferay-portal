/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

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

			openSelectionModal({
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
