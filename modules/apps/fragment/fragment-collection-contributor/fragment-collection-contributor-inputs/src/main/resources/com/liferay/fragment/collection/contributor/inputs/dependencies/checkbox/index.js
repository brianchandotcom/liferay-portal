const inputElement = document.getElementById(`${fragmentNamespace}-checkbox`);

const preventClick = (event) => event.preventDefault();

if (inputElement) {
	if (input.readOnly) {
		inputElement.addEventListener('click', preventClick);
	}
	else if (layoutMode === 'edit') {
		inputElement.setAttribute('disabled', true);
	}
	else {
		if (Liferay.FeatureFlags['LPD-37927']) {
			const defaultLanguageId = themeDisplay.getDefaultLanguageId();

			import('@liferay/fragment-impl').then(
				({registerLocalizedInput, registerUnlocalizedInput}) => {
					if (input.localizable) {
						const {onChange} = registerLocalizedInput({
							defaultLanguageId,
							initialValues: input.valueI18n,
							inputElement,
							inputName: input.name,
							localizationInputsContainer:
								inputElement.parentNode,
							namespace: fragmentNamespace,
						});

						inputElement.addEventListener('change', (event) => {
							onChange(event.target.checked);
						});
					}
					else {
						const unlocalizedFieldsState =
							input.attributes.unlocalizedFieldsState;

						registerUnlocalizedInput({
							defaultLanguageId,
							inputElement,
							onLocaleChange: (languageId) => {
								if (
									defaultLanguageId !== languageId &&
									unlocalizedFieldsState === 'read-only'
								) {
									inputElement.addEventListener(
										'click',
										preventClick
									);
								}
								else {
									inputElement.removeEventListener(
										'click',
										preventClick
									);
								}
							},
							readOnlyInputLabel: document.getElementById(
								`${fragmentNamespace}-checkbox-read-only`
							),
							unlocalizedFieldsState,
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
