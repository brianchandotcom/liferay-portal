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
const textarea = document.getElementById(`${fragmentNamespace}-textarea`);

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
	if (layoutMode === 'edit' && textarea) {
		textarea.setAttribute('disabled', true);
	}
	else {
		currentLength.innerText = textarea.value.length;

		if (textarea.value.length > input.attributes.maxLength) {
			enableLengthWarning();
		}

		textarea.addEventListener('keyup', onInputKeyup);

		if (input.attributes.localizable) {
			Liferay.on('localizationSelect:localeChanged', (event) => {
				currentLanguageId = event.languageId;

				const translationInput =
					getOrCreateTranslationInput(currentLanguageId);

				if (translationInput.getAttribute('value') !== null) {
					textarea.value = translationInput.value;
				}
			});

			textarea.addEventListener('input', (event) => {
				const value = event.target.value;

				const translationInput =
					getOrCreateTranslationInput(currentLanguageId);

				translationInput.value = value;
			});

			textarea.addEventListener('change', () => {
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
		textarea.parentNode.appendChild(translationInput);
	}

	return translationInput;
}

main();
