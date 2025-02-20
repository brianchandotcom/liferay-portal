/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Args = {
	defaultLanguageId: string;
	initialValues: Record<string, any>;
	inputName: string;
	localizationInputsContainer: HTMLElement;
	namespace: string;
	onLocaleChange: (input: HTMLInputElement, languageId: string) => void;
};

export function registerLocalizedFileInput({
	defaultLanguageId,
	initialValues,
	inputName,
	localizationInputsContainer,
	namespace,
	onLocaleChange,
}: Args) {
	let currentLanguageId = defaultLanguageId;

	if (Object.keys(initialValues).length) {
		Object.entries(initialValues).forEach(([languageId, {fileEntryId}]) => {
			const input = getOrCreateTranslationInput(
				inputName,
				languageId,
				localizationInputsContainer,
				namespace
			);

			input.value = fileEntryId;
		});
	}

	Liferay.on('localizationSelect:localeChanged', ({languageId}) => {
		currentLanguageId = languageId;

		const {input: translationInput} = getTranslationInput(
			namespace,
			languageId
		);

		if (translationInput) {
			onLocaleChange(translationInput, languageId);
		}
		else {
			const {input: defaultTranslationInput} = getTranslationInput(
				namespace,
				defaultLanguageId
			);

			onLocaleChange(defaultTranslationInput, defaultLanguageId);
		}
	});

	return {
		onChange: (files: FileList) => {
			const translationInput = getOrCreateTranslationInput(
				inputName,
				currentLanguageId,
				localizationInputsContainer,
				namespace,
				'file'
			);

			if (files?.length) {
				transferFilesToInput(files, translationInput);
			}

			Liferay.fire('localizationSelect:updateTranslationStatus', {
				languageId: currentLanguageId,
			});
		},
		onRemoveFile: () => {
			const {input: translationInput} = getTranslationInput(
				namespace,
				currentLanguageId
			);

			translationInput.type = 'hidden';
			translationInput.value = '';
		},
	};
}

function getOrCreateTranslationInput(
	inputName: string,
	languageId: string,
	localizationInputsContainer: HTMLElement,
	namespace: string,
	type: 'file' | 'hidden' = 'hidden'
) {
	const {input, inputId} = getTranslationInput(namespace, languageId);

	let translationInput = input;

	if (!translationInput) {
		translationInput = document.createElement('input');
		translationInput.type = type;
		translationInput.id = inputId;
		translationInput.name = `${inputName}_${languageId}`;
		translationInput.className = 'd-none';
		localizationInputsContainer.appendChild(translationInput);
	}
	else if (translationInput.type === 'hidden' && type === 'file') {
		translationInput.value = '';
		translationInput.type = 'file';
	}

	return translationInput as HTMLInputElement;
}

function getTranslationInput(namespace: string, languageId: string) {
	const inputId = `${namespace}-file-upload-_${languageId}`;

	return {
		input: document.getElementById(inputId) as HTMLInputElement,
		inputId,
	};
}

function transferFilesToInput(files: FileList, input: HTMLInputElement) {
	const dataTransfer = new DataTransfer();

	if (files?.length) {
		[...files].forEach((file) => {
			dataTransfer.items.add(file);
		});
	}

	input.files = dataTransfer.files;
}
