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
			({
				registerLocalizedFileInput,
				registerLocalizedInput,
				registerUnlocalizedInput,
			}) => {
				if (input.localizable) {
					if (input.attributes.selectFromDocumentLibrary) {
						const translationFileMap = new Map();
						let currentLanguageId = defaultLanguageId;

						const {onChange} = registerLocalizedInput({
							defaultLanguageId:
								themeDisplay.getDefaultLanguageId(),
							initialValues: input.valueI18n,
							inputElement,
							inputName: input.name,
							localizationInputsContainer:
								inputElement.parentNode,
							namespace: fragmentNamespace,
							onLocaleChange: ({languageId}) => {
								let fileNameText = '';

								const defaultFileName =
									translationFileMap.get(defaultLanguageId);
								if (defaultFileName) {
									fileNameText = defaultFileName;
								}

								const translatedFileName =
									translationFileMap.get(languageId);
								if (translatedFileName) {
									fileNameText = translatedFileName;
								}

								fileName.innerText = fileNameText;

								if (fileNameText !== '') {
									showRemoveButton();
								}
								else {
									onRemoveFile();
								}

								currentLanguageId = languageId;
							},
						});

						inputElement.addEventListener('change', (event) => {
							onChange(event.target.value);
						});

						const onSelectFromDocumentLibrary = (event) => {
							event.preventDefault();

							Liferay.Util.openSelectionModal({
								onSelect: (selectedItem) => {
									const {fileEntryId, title} = JSON.parse(
										selectedItem.value
									);

									fileInput.value = fileEntryId;
									fileName.innerText = title;

									translationFileMap.set(
										currentLanguageId,
										title
									);

									onChange(fileEntryId);

									showRemoveButton();
								},
								selectEventName: `${fragmentNamespace}selectFileEntry`,
								url: input.attributes
									.selectFromDocumentLibraryURL,
							});
						};

						selectButton.addEventListener(
							'click',
							onSelectFromDocumentLibrary
						);
					}
					else {
						const initialValues = Object.fromEntries(
							Object.keys(input.valueI18n).map((key) => [
								key,
								{
									fileEntryId: input.valueI18n[key],
									name:
										input.attributes.fileNameI18n[key] ||
										'',
								},
							])
						);

						const {onChange} = registerLocalizedFileInput({
							defaultLanguageId,
							initialValues,
							inputElement,
							inputName: input.name,
							localizationInputsContainer:
								inputElement.parentNode,
							namespace: fragmentNamespace,
							onLocaleChange: (input, languageId) => {
								if (
									input.type === 'file' &&
									input.files?.length
								) {
									fileName.innerText = input.files[0].name;
								}
								else if (input.type === 'hidden') {
									fileName.innerText = input.value
										? initialValues[languageId]?.name
										: '';
								}

								if (fileName.innerText) {
									removeButton.classList.remove('d-none');
								}
								else {
									removeButton.classList.add('d-none');
								}
							},
						});

						inputElement.addEventListener('change', (event) => {
							onChange(event.target.files);
						});

						selectButton.addEventListener(
							'click',
							onSelectFromUserComputer
						);
					}
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
									selectButton.classList.add('d-none');

									fileName.setAttribute('readonly', 'true');
									fileName.setAttribute('tabindex', '0');
									fileName.classList.add('form-control');

									if (!fileName.innerText) {
										fileName.innerText =
											Liferay.Language.get(
												'not-selected'
											);
									}
								}
								else {
									selectButton.setAttribute('disabled', true);

									fileName.classList.add('text-secondary');
								}

								removeButton.classList.add('d-none');
							}
							else {
								if (unlocalizedFieldsState === 'read-only') {
									selectButton.classList.remove('d-none');

									fileName.removeAttribute('readonly');
									fileName.removeAttribute('tabindex');
									fileName.classList.remove('form-control');

									if (
										fileName.innerText ===
										Liferay.Language.get('not-selected')
									) {
										fileName.innerText = '';
									}
								}
								else {
									selectButton.removeAttribute('disabled');

									fileName.classList.remove('text-secondary');
								}

								if (fileName.innerText) {
									removeButton.classList.remove('d-none');
								}
							}
						},
						readOnlyInputLabel: document.getElementById(
							`${fragmentNamespace}-file-upload-read-only`
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
