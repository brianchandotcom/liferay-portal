const inputElement = document.getElementById(
	`${fragmentNamespace}-friendly-url-input`
);

function main() {
	if (layoutMode === 'edit' && inputElement) {
		inputElement.setAttribute('disabled', true);
	}
	else {
		if (Liferay.FeatureFlags['LPD-37927']) {
			import('@liferay/fragment-impl').then(
				({registerLocalizedInput}) => {
					const {onChange} = registerLocalizedInput({
						defaultLanguageId: themeDisplay.getDefaultLanguageId(),
						initialValues: input.valueI18n,
						inputElement,
						inputName: input.name,
						localizationInputsContainer: inputElement.parentNode,
						namespace: fragmentNamespace,
					});

					inputElement.addEventListener('change', (event) => {
						onChange(event.target.value);
					});
				}
			);
		}
	}
}

main();
