const currentLength = document.getElementById(
	`${fragmentElementId}-current-length`
);
const error = document.getElementById(`${fragmentElementId}-text-input-error`);
const errorMessage = document.getElementById(
	`${fragmentElementId}-text-input-error-message`
);
const formGroup = document.getElementById(`${fragmentElementId}-form-group`);
const inputElement = document.getElementById(`${fragmentElementId}-text-input`);
const lengthInfo = document.getElementById(`${fragmentElementId}-length-info`);

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

				const defaultLanguageId = input.attributes.defaultLanguageId;

				if (input.localizable) {
					const {onChange} = registerLocalizedInput({
						availableLanguageIds:
							input.attributes.availableLanguageIds,
						defaultLanguageId,
						initialValues: input.valueI18n,
						inputElement,
						inputName: input.name,
						localizationInputsContainer: inputElement.parentNode,
						namespace: fragmentElementId,
					});

					inputElement.addEventListener('change', (event) => {
						onChange(event.target.value);
					});
				}
				else {
					registerUnlocalizedInput({
						defaultLanguageId,
						inputElement,
						readOnlyInputLabel: document.getElementById(
							`${fragmentElementId}-text-input-readonly`
						),
						unlocalizedFieldsState:
							input.attributes.unlocalizedFieldsState,
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
