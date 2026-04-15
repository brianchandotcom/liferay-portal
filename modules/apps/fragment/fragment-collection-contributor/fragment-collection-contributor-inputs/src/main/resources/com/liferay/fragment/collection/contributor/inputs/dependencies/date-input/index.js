const error = document.getElementById(`${fragmentElementId}-date-input-error`);
const inputElement = document.getElementById(`${fragmentElementId}-date-input`);

if (inputElement) {
	if (input.attributes?.readOnly) {
		inputElement.addEventListener('keydown', (event) => {
			if (event.code === 'Space') {
				event.preventDefault();
			}
		});
	}

	if (layoutMode === 'edit') {
		inputElement.setAttribute('disabled', true);
	}
	else {
		const defaultLanguageId = input.attributes.defaultLanguageId;

		import('@liferay/fragment-impl/api').then(
			({
				focusInput,
				registerLocalizedInput,
				registerUnlocalizedInput,
			}) => {
				if (error) {
					focusInput(inputElement);
				}

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
							`${fragmentElementId}-date-read-only`
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
