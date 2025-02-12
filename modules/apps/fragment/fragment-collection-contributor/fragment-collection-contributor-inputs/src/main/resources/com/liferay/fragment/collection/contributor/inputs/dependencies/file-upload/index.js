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
const selectButton = document.getElementById(
	`${fragmentNamespace}-file-upload-button-label`
);

function showRemoveButton() {
	removeButton.classList.remove('d-none');
	removeButton.addEventListener('click', onRemoveFile);
}

let previousFiles = null;

function onInputChange() {
	if (!fileInput.files.length && previousFiles) {
		const dataTransfer = new DataTransfer();

		dataTransfer.items.add(previousFiles);

		fileInput.files = dataTransfer.files;
	}

	fileName.innerText = fileInput.files[0].name;
	fileInput.setAttribute('name', input.name);

	hiddenFileInput.setAttribute('name', '');
	hiddenFileInput.value = '';

	showRemoveButton();
}

function onRemoveFile() {
	previousFiles = null;

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

const onSelectFromUserComputer = () => {
	previousFiles = fileInput.files[0] || null;

	fileInput.click();
};

if (layoutMode === 'edit') {
	selectButton.classList.add('disabled');
}
else {
	let selectFileEvent = onSelectFromUserComputer;

	if (input.attributes.selectFromDocumentLibrary) {
		selectFileEvent = onSelectFile;
	}

	fileInput.addEventListener('change', onInputChange);

	if (fileName.innerText !== '') {
		showRemoveButton();
	}

	if (Liferay.FeatureFlags['LPD-37927']) {
		const defaultLanguageId = themeDisplay.getDefaultLanguageId();
		const inputElement = fileInput;

		import('@liferay/fragment-impl').then(
			({registerLocalizedFileInput, registerUnlocalizedInput}) => {
				if (input.localizable) {
					const {onChange} = registerLocalizedFileInput({
						defaultLanguageId,
						initialValues: input.valueI18n,
						inputElement,
						inputName: input.name,
						localizationInputsContainer: inputElement.parentNode,
						namespace: fragmentNamespace,
						onFileChange: ({files}) => {
							if (files?.length) {
								fileName.innerText = files[0].name;
								showRemoveButton();
							}
							else {
								onRemoveFile();
							}
						},
						removeButton,
					});

					inputElement.addEventListener('change', (event) => {
						onChange(event.target.files);
					});
				}
				else {
					const unlocalizedFieldsState =
						input.attributes.unlocalizedFieldsState;

					registerUnlocalizedInput({
						defaultLanguageId,
						inputElement,
						onLocaleChange: (languageId) => {
							if (defaultLanguageId !== languageId) {
								if (unlocalizedFieldsState === 'read-only') {
									selectButton.style.display = 'none';
									fileName.tabIndex = 0;
									if (!fileName.innerText) {
										fileName.innerText =
											Liferay.Language.get(
												'not-selected'
											);
									}
								}
								else {
									selectButton.disabled = true;
									fileName.classList.add('text-secondary');
								}
								removeButton.style.display = 'none';
							}
							else {
								if (unlocalizedFieldsState === 'read-only') {
									selectButton.style.display = '';
									fileName.tabIndex = -1;
									if (
										fileName.innerText ===
										Liferay.Language.get('not-selected')
									) {
										fileName.innerText = '';
									}
								}
								else {
									selectButton.disabled = false;
									fileName.classList.remove('text-secondary');
								}
								removeButton.style.display = '';
							}
						},
						readOnlyInputLabel: document.getElementById(
							`${fragmentNamespace}-checkbox-read-only`
						),
						unlocalizedFieldsState,
						unlocalizedMessageContainer: document.getElementById(
							`${fragmentNamespace}-unlocalized-info`
						),
					});

					selectButton.addEventListener('click', selectFileEvent);
				}
			}
		);
	}
	else {
		selectButton.addEventListener('click', selectFileEvent);
	}
}
