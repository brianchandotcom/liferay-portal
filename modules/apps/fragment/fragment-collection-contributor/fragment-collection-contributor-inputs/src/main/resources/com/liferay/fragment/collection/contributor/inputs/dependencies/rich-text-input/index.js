const inputElement = document.getElementById(
	`${fragmentNamespace}-rich-text-input`
);

const inputLabelElement = document.getElementById(
	`${fragmentEntryLinkNamespace}-rich-text-input-label`
);

const editorName = `${fragmentEntryLinkNamespace}-${input.name}`;

document.getElementById(editorName).name = input.name;

if (input.attributes?.readOnly) {
	if (inputElement) {
		inputElement.innerHTML = input.value;
	}
}
else if (layoutMode === 'edit') {
	if (inputElement) {
		inputElement.setAttribute('disabled', true);
	}
}
else if (Liferay.FeatureFlags['LPD-37927']) {
	const editorPromise = new Promise((resolve) => {
		CKEDITOR.on('instanceReady', (editorEvent) => {
			if (editorEvent.editor.name === editorName) {
				resolve(editorEvent.editor);
			}
		});
	});

	import('@liferay/fragment-impl').then(
		({registerLocalizedInput, registerUnlocalizedInput}) => {
			if (input.localizable) {
				const {onChange} = registerLocalizedInput({
					defaultLanguageId: themeDisplay.getDefaultLanguageId(),
					initialValues: input.valueI18n,
					inputName: input.name,
					localizationInputsContainer:
						inputLabelElement.parentElement,
					namespace: fragmentNamespace,
					onLocaleChange: ({value}) => {
						editorPromise.then((editor) => {
							editor.setData(value);
						});
					},
				});

				editorPromise.then((editor) => {
					editor.on('change', () => {
						const value = editor.getData();

						onChange(value);
					});
				});
			}
			else {
				registerUnlocalizedInput({
					defaultLanguageId: themeDisplay.getDefaultLanguageId(),
					onLocaleChange: (languageId) => {
						editorPromise.then((editor) => {
							if (
								languageId ===
								themeDisplay.getDefaultLanguageId()
							) {
								editor.setReadOnly(false);
							}
							else {
								editor.setReadOnly(true);
							}
						});
					},
					unlocalizedFieldsState:
						input.attributes.unlocalizedFieldsState,

					unlocalizedMessageContainer: document.getElementById(
						`${fragmentNamespace}-unlocalized-info`
					),
				});
			}
		}
	);
}
