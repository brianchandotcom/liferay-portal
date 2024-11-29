const currentLength = document.getElementById(
	`${fragmentNamespace}-current-length`
);
const formGroup = document.getElementById(`${fragmentNamespace}-form-group`);
const lengthInfo = document.getElementById(`${fragmentNamespace}-length-info`);
const lengthWarning = document.getElementById(
	`${fragmentNamespace}-length-warning`
);
const lengthWarningText = document.getElementById(
	`${fragmentNamespace}-length-warning-text`
);
const inputElement = document.getElementById(`${fragmentNamespace}-text-input`);

function enableLengthWarning() {
	formGroup.classList.add('has-error');
	lengthInfo.classList.add('text-danger', 'font-weight-semi-bold');
	lengthWarning.classList.remove('sr-only');

	const warningText = lengthWarningText.getAttribute('data-error-message');
	lengthWarningText.innerText = warningText;

	if (!configuration.showCharactersCount) {
		lengthInfo.classList.remove('sr-only');
	}
}

function disableLengthWarning() {
	formGroup.classList.remove('has-error');
	lengthInfo.classList.remove('text-danger', 'font-weight-semi-bold');
	lengthWarning.classList.add('sr-only');

	const validText = lengthWarningText.getAttribute('data-valid-message');
	lengthWarningText.innerText = validText;

	if (!configuration.showCharactersCount) {
		lengthInfo.classList.add('sr-only');
	}
}

function onInputKeyup(event) {
	const length = event.target.value.length;

	currentLength.innerText = length;

	if (length > input.attributes.maxLength) {
		enableLengthWarning();
	}
	else if (formGroup.classList.contains('has-error')) {
		disableLengthWarning();
	}
}

let currentLanguageId = themeDisplay.getDefaultLanguageId();

function main() {
	if (layoutMode === 'edit' && inputElement) {
		inputElement.setAttribute('disabled', true);
	}
	else {
		currentLength.innerText = inputElement.value.length;

		if (inputElement.value.length > input.attributes.maxLength) {
			enableLengthWarning();
		}

		inputElement.addEventListener('keyup', onInputKeyup);

		if (input.attributes.localizable) {
			Liferay.on('localizationSelect:localeChanged', (event) => {
				currentLanguageId = event.languageId;

				const translationInput =
					getOrCreateTranslationInput(currentLanguageId);

				if (translationInput.getAttribute('value') !== null) {
					inputElement.value = translationInput.value;
				}
			});

			inputElement.addEventListener('input', (event) => {
				const value = event.target.value;

				const translationInput =
					getOrCreateTranslationInput(currentLanguageId);

				translationInput.value = value;
			});

			inputElement.addEventListener('change', () => {
				Liferay.fire('localizationSelect:updateTranslationStatus', {
					languageId: currentLanguageId,
				});
			});
		}
	}
}

function getOrCreateTranslationInput(languageId) {
	const inputId = `${fragmentNamespace}${input.name}_${languageId}`;

	let translationInput = document.getElementById(inputId);

	if (!translationInput) {
		translationInput = document.createElement('input');
		translationInput.type = 'hidden';
		translationInput.id = inputId;
		translationInput.name = `${input.name}_${currentLanguageId}`;
		inputElement.parentNode.appendChild(translationInput);
	}

	return translationInput;
}

main();
