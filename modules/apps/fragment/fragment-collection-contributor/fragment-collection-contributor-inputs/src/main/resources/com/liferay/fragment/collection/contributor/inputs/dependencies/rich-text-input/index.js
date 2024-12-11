const inputElement = document.getElementById(
	`${fragmentNamespace}-rich-text-input`
);

const inputLabelElement = document.getElementById(
	`${fragmentEntryLinkNamespace}-rich-text-input-label`
);

let currentLanguageId = themeDisplay.getDefaultLanguageId();

if (input.attributes?.readOnly) {
	if (inputElement) {
		inputElement.innerHTML = input.value;
	}
}
else if (layoutMode === 'edit') {
	if (inputElement) {
		inputElement.setAttribute('disabled', true);
	}
}
else if (layoutMode !== 'edit' && input.localizable) {
	CKEDITOR.on('instanceReady', (editorEvent) => {
		if (editorEvent.editor.name === input.name) {
			editorEvent.editor.on('change', () => {
				const value = editorEvent.editor.getData();

				const translationInput =
					getOrCreateTranslationInput(currentLanguageId);

				translationInput.value = value;

				Liferay.fire('localizationSelect:updateTranslationStatus', {
					languageId: currentLanguageId,
				});
			});
		}

		Liferay.on('localizationSelect:localeChanged', (event) => {
			currentLanguageId = event.languageId;

			const translationInput =
				getOrCreateTranslationInput(currentLanguageId);

			if (translationInput.getAttribute('value') !== null) {
				editorEvent.editor.setData(translationInput.value);
			}
		});
	});

	if (input.valueI18n) {
		Object.entries(input.valueI18n).forEach(([languageId, value]) => {
			const translationInput = getOrCreateTranslationInput(languageId);

			translationInput.value = value;
		});
	}
}

function getOrCreateTranslationInput(languageId) {
	const inputId = `${fragmentNamespace}${input.name}_${languageId}`;

	let translationInput = document.getElementById(inputId);

	if (!translationInput) {
		translationInput = document.createElement('input');
		translationInput.type = 'hidden';
		translationInput.id = inputId;
		translationInput.name = `${input.name}_${languageId}`;

		inputLabelElement.parentElement.appendChild(translationInput);
	}

	return translationInput;
}
