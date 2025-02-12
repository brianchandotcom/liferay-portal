/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Args = {
	defaultLanguageId: string;
	inputElement?: HTMLInputElement;
	inputName: string;
	localizationInputsContainer: HTMLElement;
	namespace: string;
	onFileChange?: ({
		files,
		languageId,
	}: {
		files: FileList | null;
		languageId: string;
	}) => void;
	removeButton: HTMLButtonElement;
};

export function registerLocalizedFileInput({
	defaultLanguageId,
	inputElement,
	inputName,
	localizationInputsContainer,
	namespace,
	onFileChange,
	removeButton,
}: Args) {
	let currentLanguageId = defaultLanguageId;

	const setDefaultValue = (languageId: string) => {
		const defaultLanguageInput = getOrCreateTranslationInput(
			inputName,
			defaultLanguageId,
			localizationInputsContainer,
			namespace
		);

		if (inputElement) {
			if (defaultLanguageId !== currentLanguageId) {
				removeButton.disabled = true;
			}

			copyFilesToInput(defaultLanguageInput.files, inputElement);

			onFileChange?.({
				files: defaultLanguageInput.files,
				languageId,
			});
		}
	};

	const onRemoveFile = () => {
		const translationInput = getOrCreateTranslationInput(
			inputName,
			currentLanguageId,
			localizationInputsContainer,
			namespace
		);

		const dataTransfer = new DataTransfer();

		translationInput.files = dataTransfer.files;

		setDefaultValue(currentLanguageId);
	};

	removeButton.addEventListener('click', onRemoveFile);

	Liferay.on('localizationSelect:localeChanged', ({languageId}) => {
		currentLanguageId = languageId;

		const translationInput = getOrCreateTranslationInput(
			inputName,
			languageId,
			localizationInputsContainer,
			namespace
		);

		if (inputElement && translationInput.files?.length) {
			removeButton.disabled = false;

			copyFilesToInput(translationInput.files, inputElement);

			onFileChange?.({files: translationInput.files, languageId});
		}
		else {
			setDefaultValue(languageId);
		}
	});

	return {
		onChange: (value: FileList) => {
			const translationInput = getOrCreateTranslationInput(
				inputName,
				currentLanguageId,
				localizationInputsContainer,
				namespace
			);

			if (value?.length) {
				removeButton.disabled = false;

				copyFilesToInput(value, translationInput);
			}

			Liferay.fire('localizationSelect:updateTranslationStatus', {
				languageId: currentLanguageId,
			});
		},
	};
}

function copyFilesToInput(fileList: FileList | null, input: HTMLInputElement) {
	const dataTransfer = new DataTransfer();

	if (fileList?.length) {
		const filesArray = Array.from(fileList);

		filesArray.forEach((file) => {
			dataTransfer.items.add(file);
		});
	}

	input.files = dataTransfer.files;
}

export default function getOrCreateTranslationInput(
	inputName: string,
	languageId: string,
	localizationInputsContainer: HTMLElement,
	namespace: string
) {
	const inputId = `${namespace}${inputName}_${languageId}`;

	let translationInput = document.getElementById(inputId) as HTMLInputElement;

	if (!translationInput) {
		translationInput = document.createElement('input');
		translationInput.type = 'file';
		translationInput.id = inputId;
		translationInput.name = `${inputName}_${languageId}`;
		translationInput.className = 'd-none';
		localizationInputsContainer.appendChild(translationInput);
	}

	return translationInput;
}
