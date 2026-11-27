const container = document.getElementById(
	`${fragmentElementId}-location-input-container`
);
const error = document.getElementById(
	`${fragmentElementId}-location-input-error`
);
const errorMessage = document.getElementById(
	`${fragmentElementId}-location-input-error-message`
);
const formGroup = document.getElementById(
	`${fragmentElementId}-location-input-form-group`
);
const valueInput = document.getElementById(
	`${fragmentElementId}-location-input-value`
);

if (container) {
	import('@liferay/fragment-impl/api').then(
		({renderLocationInput, showInputError}) => {
			if (input.required && layoutMode !== 'edit') {
				valueInput.addEventListener('invalid', (event) => {
					event.preventDefault();

					showInputError({
						errorContainer: error,
						errorMessageContainer: errorMessage,
						formGroup,
						message: errorMessage.getAttribute(
							'data-required-feedback'
						),
					});

					formGroup.scrollIntoView({
						behavior: 'smooth',
						block: 'center',
					});
				});

				valueInput.addEventListener('change', () => {
					if (valueInput.value) {
						error.classList.add('sr-only');
						formGroup.classList.remove('has-error');
					}
				});
			}

			renderLocationInput(container, {
				availableLanguageIds: input.attributes.availableLanguageIds,
				defaultLanguageId: input.attributes.defaultLanguageId,
				disabled: input.attributes.disabled === true,
				editMode: layoutMode === 'edit',
				focus: formGroup.classList.contains('has-error'),
				googleMapsAPIKey: input.attributes.googleMapsAPIKey,
				localizable: input.localizable,
				mapProviderKey: input.attributes.mapProviderKey,
				name: input.name,
				namespace: fragmentElementId,
				placeholder: configuration.placeholder,
				readOnly: input.readOnly,
				readOnlyLabelId: `${fragmentElementId}-location-read-only`,
				unlocalizedFieldsState: input.attributes.unlocalizedFieldsState,
				value: input.value,
				valueI18n: input.valueI18n,
			});
		}
	);
}
