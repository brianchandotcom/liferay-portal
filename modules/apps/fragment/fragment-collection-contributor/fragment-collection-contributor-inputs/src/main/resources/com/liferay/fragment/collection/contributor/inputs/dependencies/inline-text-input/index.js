const currentLength = document.getElementById(
	`${fragmentElementId}-current-length`
);
const error = document.getElementById(
	`${fragmentElementId}-inline-text-input-error`
);
const errorMessage = document.getElementById(
	`${fragmentElementId}-inline-text-input-error-message`
);
const formGroup = document.getElementById(`${fragmentElementId}-form-group`);
const inputElement = document.getElementById(
	`${fragmentElementId}-inline-text-input`
);
const lengthInfo = document.getElementById(`${fragmentElementId}-length-info`);
const localizedText = document.getElementById(
	`${fragmentElementId}-localized-text`
);

function main() {
	if (layoutMode === 'edit' && inputElement) {
		inputElement.setAttribute('disabled', true);
	}
	else {
		import('@liferay/fragment-impl/api').then(
			({
				focusInput,
				handleInputLengthError,
				registerLocalizedInput,
				registerUnlocalizedInput,
				showInputError,
			}) => {
				if (input.required) {
					inputElement.addEventListener('invalid', (event) => {
						event.preventDefault();

						focusInput(inputElement);

						showInputError({
							errorContainer: error,
							errorMessageContainer: errorMessage,
							errorType: 'required',
							formGroup,
						});
					});
				}

				const hasError = formGroup.classList.contains('has-error');

				if (hasError) {
					focusInput(inputElement);
				}

				if (currentLength) {
					currentLength.innerText = inputElement.value.length;
				}

				if (
					!hasError &&
					inputElement.value.length > input.attributes.maxLength
				) {
					showInputError({
						errorType: 'length',
						formGroup,
						lengthInfoContainer: lengthInfo,
					});
				}

				const onKeyup = (event) =>
					handleInputLengthError({
						currentLength,
						errorContainer: error,
						errorMessageContainer: errorMessage,
						event,
						formGroup,
						input,
						lengthInfoContainer: lengthInfo,
					});

				inputElement.addEventListener('keyup', onKeyup);

				const defaultLanguageId = themeDisplay.getDefaultLanguageId();

				if (input.localizable) {
					const {onBlur, onChange} = registerLocalizedInput({
						defaultLanguageId,
						initialValues: input.valueI18n,
						inputElement,
						inputName: input.name,
						localizationInputsContainer: inputElement.parentNode,
						localizedTextContainer: localizedText,
						namespace: fragmentElementId,
					});

					inputElement.addEventListener('change', (event) => {
						onChange(event.target.value);
					});

					inputElement.addEventListener('blur', (event) => {
						onBlur(event.target.value);
					});
				}
				else {
					registerUnlocalizedInput({
						defaultLanguageId,
						inputElement,
						readOnlyInputLabel: document.getElementById(
							`${fragmentElementId}-inline-text-input-readonly`
						),
						unlocalizedFieldsState:
							input.attributes.unlocalizedFieldsState,
						unlocalizedLabelTextContainer: document.getElementById(
							`${fragmentElementId}-unlocalized-label-text`
						),
						unlocalizedMessageContainer: document.getElementById(
							`${fragmentElementId}-unlocalized-info`
						),
					});
				}
			}
		);
	}
}

main();
