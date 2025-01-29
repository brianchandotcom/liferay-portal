const inputElement = document.getElementById(`${fragmentNamespace}-checkbox`);

if (inputElement) {
	if (input.attributes?.readOnly) {
		inputElement.addEventListener('click', (event) =>
			event.preventDefault()
		);
	}
	else if (layoutMode === 'edit') {
		inputElement.setAttribute('disabled', true);
	}
	else {
		if (Liferay.FeatureFlags['LPD-37927']) {
			import('@liferay/fragment-impl').then(
				({registerLocalizedInput, registerUnlocalizedInput}) => {
					if (input.localizable) {
						const {onChange} = registerLocalizedInput({
							defaultLanguageId:
								themeDisplay.getDefaultLanguageId(),
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
						registerUnlocalizedInput({
							defaultLanguageId:
								themeDisplay.getDefaultLanguageId(),
							inputElement,
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
