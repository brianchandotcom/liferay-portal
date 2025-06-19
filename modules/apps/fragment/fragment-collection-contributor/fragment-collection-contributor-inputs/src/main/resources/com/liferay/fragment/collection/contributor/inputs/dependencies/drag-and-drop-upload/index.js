/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const changeButton = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-change-button`
);
const dropzone = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-dropzone`
);
const dropzoneText = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-dropzone-text`
);
const defaultDropzone = dropzone.querySelector('.dropzone-default-content');
const fileInput = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload`
);
const fileNameLabel = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-file-name-label`
);
const helpText = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-help-text`
);
const hiddenFileInput = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-hidden`
);
const noPreviewDropzone = dropzone.querySelector('.dropzone-no-preview');
const previewContainer = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-preview`
);
const previewContent = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-preview-content`
);
const removeButton = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-remove-button`
);
const selectButton = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-button`
);
const previewButtons = document.getElementById(
	`${fragmentNamespace}-drag-and-drop-upload-preview-buttons`
);

let hasSelectedFile = false;

function showDropzone(dropzonePreview) {
	dropzonePreview.classList.remove('d-none');

	if (dropzonePreview === noPreviewDropzone) {
		previewContainer.classList.add('d-none');
		defaultDropzone.classList.add('d-none');
	}
	else if (dropzonePreview === previewContainer) {
		defaultDropzone.classList.add('d-none');
		noPreviewDropzone.classList.add('d-none');
	}
	else {
		previewContainer.classList.add('d-none');
		noPreviewDropzone.classList.add('d-none');
		previewButtons.classList.add('d-none');
		updateFileNameLabel('');
		selectButton.focus();
	}
}

function showPreview(fileOrUrl) {
	hasSelectedFile = true;

	if (!fileOrUrl) {
		showDropzone(defaultDropzone);

		return;
	}

	let imageURL = null;

	if (fileOrUrl instanceof File && fileOrUrl.type?.startsWith('image/')) {
		imageURL = URL.createObjectURL(fileOrUrl);
	}
	else {
		imageURL = fileOrUrl;
	}

	if (imageURL) {
		showDropzone(previewContainer);

		previewContent.innerHTML = '';

		const image = document.createElement('img');
		image.src = imageURL;
		image.alt = '';
		image.style.width = '100%';

		previewContent.appendChild(image);
	}
	else {
		showDropzone(noPreviewDropzone);
	}
}

function updateFileNameLabel(fileName) {
	if (fileNameLabel) {
		if (fileName) {
			fileNameLabel.textContent = fileName;
		}
		else {
			fileNameLabel.textContent =
				Liferay.Language.get('no-file-selected');
		}
	}

	changeButton.setAttribute(
		'aria-label',
		Liferay.Util.sub(previewButtons.dataset.changeLabel, fileName)
	);

	removeButton.setAttribute(
		'aria-label',
		Liferay.Util.sub(previewButtons.dataset.removeLabel, fileName)
	);
}

function onInputChange() {
	const file = fileInput.files[0];

	showPreview(file);
	fileInput.setAttribute('name', input.name);

	hiddenFileInput.setAttribute('name', '');
	hiddenFileInput.value = '';

	previewButtons.classList.remove('d-none');

	updateFileNameLabel(file?.name);

	changeButton.focus();
}

function getTranslationInput(namespace, languageId, inputId) {
	return document.getElementById(`${namespace}${inputId}_${languageId}`);
}

function onSelectFile(event, onChange, setTranslationInputValue) {
	event.preventDefault();

	Liferay.Util.openSelectionModal({
		onSelect(selectedItem) {
			const {fileEntryId, title, url} = JSON.parse(selectedItem.value);

			if (onChange) {
				setTranslationInputValue({
					previewURL: url,
					title: title,
					value: fileEntryId,
				});

				onChange();
			}

			fileInput.value = fileEntryId;

			showPreview(url);
			previewButtons.classList.remove('d-none');

			updateFileNameLabel(title || '');
		},
		selectEventName: `${fragmentNamespace}selectFileEntry`,
		url: input.attributes.selectFromDocumentLibraryURL,
	});
}

function onSelectFromUserComputer() {
	fileInput.click();
}

let selectFileEvent = onSelectFromUserComputer;

if (layoutMode === 'edit') {
	selectButton.classList.add('disabled');
}
else {
	if (input.attributes.selectFromDocumentLibrary) {
		selectFileEvent = onSelectFile;
	}

	fileInput.addEventListener('change', onInputChange);

	const defaultLanguageId = themeDisplay.getDefaultLanguageId();
	const inputElement = fileInput;

	let currentLanguageId = defaultLanguageId;

	import('@liferay/fragment-impl/api').then(
		({
			getOrCreateTranslationInput,
			registerLocalizedInput,
			registerUnlocalizedInput,
		}) => {
			const isFromDocumentLibrary =
				input.attributes.selectFromDocumentLibrary;

			defaultDropzone.addEventListener('dragover', (event) => {
				if (!isFromDocumentLibrary) {
					event.preventDefault();
					defaultDropzone.classList.add('dropzone-hover');
				}
			});

			defaultDropzone.addEventListener('dragleave', () => {
				if (!isFromDocumentLibrary) {
					defaultDropzone.classList.remove('dropzone-hover');
				}
			});

			if (input.localizable) {

				// Set initial values

				const initialValues = Object.keys(input.valueI18n).map(
					(key) => [
						key,
						{
							fileEntryId: input.valueI18n[key],
							previewURL:
								input.attributes.previewURLI18n[key] || '',
						},
					]
				);

				initialValues.forEach(([languageId, value]) => {
					const translationInput = getOrCreateTranslationInput(
						inputElement.id,
						input.name,
						languageId,
						inputElement.parentNode,
						fragmentNamespace
					);
					translationInput.value = value.fileEntryId;
					translationInput.dataset.previewURL = value.previewURL;
				});

				if (input.attributes?.previewURL) {
					showPreview(input.attributes.previewURL);

					previewButtons.classList.remove('d-none');
				}

				if (input.attributes?.fileName) {
					updateFileNameLabel(input.attributes.fileName);
				}

				const {onChange} = registerLocalizedInput({
					changeTextDirection: false,
					customLocaleChangeHandler: true,
					defaultLanguageId,
					onLocaleChange: ({languageId}) => {
						currentLanguageId = languageId;

						const translationInput = getTranslationInput(
							fragmentNamespace,
							languageId,
							inputElement.id
						);

						let previewURL = translationInput?.dataset?.previewURL;

						const fileName =
							translationInput?.dataset?.fileName || '';

						if (previewURL) {
							showPreview(previewURL);
							previewButtons.classList.remove('d-none');
							updateFileNameLabel(fileName);
						}
						else {
							const defaultInput = getOrCreateTranslationInput(
								inputElement.id,
								input.name,
								defaultLanguageId,
								inputElement.parentNode,
								fragmentNamespace
							);

							previewURL = defaultInput?.dataset?.previewURL;

							if (previewURL) {
								showPreview(defaultInput?.dataset?.previewURL);
								previewButtons.classList.remove('d-none');
								updateFileNameLabel(
									defaultInput?.dataset?.fileName
								);
							}
							else {
								showDropzone(defaultDropzone);
							}
						}
					},
				});

				const setTranslationInputValue = ({
					previewURL,
					title,
					value,
				}) => {
					const type =
						isFromDocumentLibrary === false ? 'file' : 'hidden';

					const translationInput = getOrCreateTranslationInput(
						inputElement.id,
						input.name,
						currentLanguageId,
						inputElement.parentNode,
						fragmentNamespace,
						type
					);

					if (isFromDocumentLibrary) {
						translationInput.value = value;
						translationInput.dataset.previewURL = previewURL;
					}
					else {
						const files = value;
						if (files?.length) {
							const dataTransfer = new DataTransfer();

							[...files].forEach((file) => {
								dataTransfer.items.add(file);
							});

							translationInput.files = dataTransfer.files;
							translationInput.dataset.previewURL =
								URL.createObjectURL(dataTransfer.files[0]);
						}
					}

					showPreview(translationInput.dataset.previewURL);
					previewButtons.classList.remove('d-none');
					updateFileNameLabel(title);
				};

				if (isFromDocumentLibrary) {
					dropzoneText.classList.add('d-none');

					changeButton.addEventListener('click', (event) => {
						onSelectFile(event, onChange, setTranslationInputValue);
					});

					selectButton.addEventListener('click', (event) => {
						onSelectFile(event, onChange, setTranslationInputValue);
					});
				}
				else {
					dropzoneText.classList.remove('d-none');

					defaultDropzone.addEventListener('drop', (event) => {
						event.preventDefault();
						defaultDropzone.classList.remove('dropzone-hover');

						onInputChange();

						setTranslationInputValue({
							value: event.dataTransfer.files,
						});

						onChange();
					});

					inputElement.addEventListener('change', (event) => {
						setTranslationInputValue({
							value: event.target.files,
						});

						onChange();
					});

					selectButton.addEventListener(
						'click',
						onSelectFromUserComputer
					);

					changeButton.addEventListener(
						'click',
						onSelectFromUserComputer
					);
				}

				removeButton.addEventListener('click', () => {
					hasSelectedFile = false;

					fileInput.value = '';
					hiddenFileInput.value = '';

					const translationInput = getOrCreateTranslationInput(
						inputElement.id,
						input.name,
						currentLanguageId,
						inputElement.parentNode,
						fragmentNamespace
					);

					translationInput.value = '';
					translationInput.dataset.previewURL = '';

					if (currentLanguageId === defaultLanguageId) {
						showDropzone(defaultDropzone);
					}
					else {
						const defaultInput = getOrCreateTranslationInput(
							inputElement.id,
							input.name,
							defaultLanguageId,
							inputElement.parentNode,
							fragmentNamespace
						);

						if (defaultInput.dataset?.previewURL) {
							showPreview(defaultInput.dataset?.previewURL);
							previewButtons.classList.remove('d-none');
							updateFileNameLabel(defaultInput.dataset.fileName);
						}
					}
				});
			}
			else {
				if (isFromDocumentLibrary) {
					dropzoneText.classList.add('d-none');
				}
				else {
					dropzoneText.classList.remove('d-none');
				}

				const unlocalizedFieldsState =
					input.attributes.unlocalizedFieldsState;

				if (input.attributes?.previewURL) {
					showPreview(input.attributes.previewURL);
					previewButtons.classList.remove('d-none');
				}

				if (input.attributes?.fileName) {
					changeButton.setAttribute(
						'aria-label',
						Liferay.Util.sub(
							previewButtons.dataset.changeLabel,
							input.attributes?.fileName
						)
					);

					removeButton.setAttribute(
						'aria-label',
						Liferay.Util.sub(
							previewButtons.dataset.removeLabel,
							input.attributes?.fileName
						)
					);
				}

				registerUnlocalizedInput({
					changeTextDirection: false,
					customLocaleChangeHandler: true,
					defaultLanguageId,
					inputElement,
					onLocaleChange: (languageId) => {
						currentLanguageId = languageId;

						if (defaultLanguageId !== languageId) {
							selectButton.setAttribute('disabled', true);

							if (hasSelectedFile) {
								changeButton.setAttribute('disabled', true);
								removeButton.setAttribute('disabled', true);
								fileNameLabel.classList.remove('d-none');
							}
							else {
								dropzone.style.opacity = '0.4';
							}

							if (unlocalizedFieldsState === 'disabled') {
								dropzone.style.opacity = '0.4';
								helpText.style.opacity = '0.4';

								fileNameLabel.classList.add('d-none');
							}

							if (input.attributes?.fileName) {
								updateFileNameLabel(input.attributes.fileName);
							}
						}
						else {
							selectButton.removeAttribute('disabled');

							dropzone.style.opacity = '1';
							helpText.style.opacity = '1';

							if (hasSelectedFile) {
								previewButtons.classList.remove('d-none');

								changeButton.removeAttribute('disabled');
								removeButton.removeAttribute('disabled');
							}
						}
					},
					readOnlyInputLabel: document.getElementById(
						`${fragmentEntryLinkNamespace}-drag-and-drop-upload-read-only`
					),
					unlocalizedFieldsState,
					unlocalizedMessageContainer: document.getElementById(
						`${fragmentNamespace}-unlocalized-info`
					),
				});

				selectButton.addEventListener('click', selectFileEvent);

				changeButton.addEventListener('click', selectFileEvent);

				removeButton.addEventListener('click', () => {
					showDropzone(defaultDropzone);

					hasSelectedFile = false;

					fileInput.value = '';
					hiddenFileInput.value = '';
				});

				defaultDropzone.addEventListener('drop', (event) => {
					event.preventDefault();
					defaultDropzone.classList.remove('dropzone-hover');

					const files = event.dataTransfer.files;

					if (files.length) {
						const dataTransfer = new DataTransfer();
						[...files].forEach((file) =>
							dataTransfer.items.add(file)
						);
						fileInput.files = dataTransfer.files;

						onInputChange();
					}
				});
			}
		}
	);
}
