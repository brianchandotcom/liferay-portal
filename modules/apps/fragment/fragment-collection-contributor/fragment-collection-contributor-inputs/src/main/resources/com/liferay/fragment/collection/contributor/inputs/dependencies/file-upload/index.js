/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const wrapper = fragmentElement;

const fileInput = document.getElementById(`${fragmentNamespace}-file-upload`);
const fileName = wrapper.querySelector('.forms-file-upload-file-name');
const hiddenFileInput = document.getElementById(
	`${fragmentNamespace}-file-upload-hidden`
);
const removeButton = document.getElementById(
	`${fragmentNamespace}-file-upload-remove-button`
);
const selectButton = wrapper.querySelector('.btn-secondary');

function showRemoveButton() {
	removeButton.classList.remove('d-none');
	removeButton.addEventListener('click', onRemoveFile);
}

function onInputChange() {
	fileName.innerText = fileInput.files[0].name;
	fileInput.setAttribute('name', input.name);

	hiddenFileInput.setAttribute('name', '');
	hiddenFileInput.value = '';

	showRemoveButton();
}

function onRemoveFile() {
	fileInput.value = '';
	fileName.innerText = '';

	hiddenFileInput.value = '';

	removeButton.classList.add('d-none');
	removeButton.removeEventListener('click', onRemoveFile);
}

function onSelectFile(event) {
	event.preventDefault();

	Liferay.Util.openSelectionModal({
		onSelect(selectedItem) {
			const {fileEntryId, title} = JSON.parse(selectedItem.value);

			fileInput.value = fileEntryId;
			fileName.innerText = title;

			showRemoveButton();
		},
		selectEventName: `${fragmentNamespace}selectFileEntry`,
		url: input.attributes.selectFromDocumentLibraryURL,
	});
}

if (layoutMode === 'edit') {
	selectButton.classList.add('disabled');
}
else {
	fileInput.addEventListener('change', onInputChange);

	if (input.attributes.selectFromDocumentLibrary) {
		selectButton.addEventListener('click', onSelectFile);
	}
	else {
		selectButton.addEventListener('click', () => {
			fileInput.click();
		});
	}

	if (fileName.innerText !== '') {
		showRemoveButton();
	}
}
