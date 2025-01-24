const currentLength = document.getElementById(
	`${fragmentNamespace}-current-length`
);
const errorMessage = document.getElementById(
	`${fragmentNamespace}-textarea-error-message`
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

	if (errorMessage) {
		errorMessage.remove();
	}

	if (length > input.attributes.maxLength) {
		enableLengthWarning();
	}
	else if (formGroup.classList.contains('has-error')) {
		disableLengthWarning();
	}
}

function main() {
	if (layoutMode === 'edit' && textarea) {
		textarea.setAttribute('disabled', true);
	}
	else {
		currentLength.innerText = textarea.value.length;

		if (
			!errorMessage &&
			textarea.value.length > input.attributes.maxLength
		) {
			enableLengthWarning();
		}

		textarea.addEventListener('keyup', onInputKeyup);

		if (Liferay.FeatureFlags['LPD-37927']) {
			import('@liferay/fragment-impl').then(
				({registerLocalizedInput, registerUnlocalizedInput}) => {
					if (input.localizable) {
						const {onChange} = registerLocalizedInput({
							defaultLanguageId:
								themeDisplay.getDefaultLanguageId(),
							initialValues: input.valueI18n,
							inputElement: textarea,
							inputName: input.name,
							localizationInputsContainer: textarea.parentNode,
							namespace: fragmentNamespace,
						});

						textarea.addEventListener('change', (event) => {
							onChange(event.target.value);
						});
					}
					else {
						registerUnlocalizedInput({
							defaultLanguageId:
								themeDisplay.getDefaultLanguageId(),
							inputElement: textarea,
							unlocalizedFieldsState:
								input.attributes.unlocalizedFieldsState,
							unlocalizedMessageContainer:
								document.getElementById(
									`${fragmentNamespace}-unlocalized-info`
								),
						});
					}
				}
			);
		}
	}
}

main();
