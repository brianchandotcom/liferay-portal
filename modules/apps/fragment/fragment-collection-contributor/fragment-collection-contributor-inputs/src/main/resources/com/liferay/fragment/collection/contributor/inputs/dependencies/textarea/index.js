const currentLength = document.getElementById(
	`${fragmentElementId}-current-length`
);
const error = document.getElementById(`${fragmentElementId}-textarea-error`);
const errorMessage = document.getElementById(
	`${fragmentElementId}-textarea-error-message`
);
const formGroup = document.getElementById(`${fragmentElementId}-form-group`);
const lengthInfo = document.getElementById(`${fragmentElementId}-length-info`);
const textarea = document.getElementById(`${fragmentElementId}-textarea`);

function main() {
	if (layoutMode === 'edit' && textarea) {
		textarea.setAttribute('disabled', true);
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
					focusInput(textarea);
				}

				if (currentLength) {
					currentLength.innerText = textarea.value.length;
				}

				if (
					!hasError &&
					textarea.value.length > input.attributes.maxLength
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

				textarea.addEventListener('keyup', onKeyup);

				const defaultLanguageId = input.attributes.defaultLanguageId;

				if (input.localizable) {
					const {onChange} = registerLocalizedInput({
						availableLanguageIds:
							input.attributes.availableLanguageIds,
						defaultLanguageId,
						initialValues: input.valueI18n,
						inputElement: textarea,
						inputName: input.name,
						localizationInputsContainer: textarea.parentNode,
						namespace: fragmentElementId,
					});

					textarea.addEventListener('change', (event) => {
						onChange(event.target.value);
					});
				}
				else {
					registerUnlocalizedInput({
						defaultLanguageId,
						inputElement: textarea,
						readOnlyInputLabel: document.getElementById(
							`${fragmentElementId}-textarea-readonly`
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
