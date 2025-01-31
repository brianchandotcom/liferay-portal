const editorName = `${fragmentEntryLinkNamespace}-${input.name}`;

if (layoutMode !== 'edit') {
	const editor = document.getElementById(editorName);
	editor.name = input.name;

	const editorPromise = new Promise((resolve) => {
		CKEDITOR.on('instanceReady', (editorEvent) => {
			if (editorEvent.editor.name === editorName) {
				resolve(editorEvent.editor);
			}
		});
	});

	if (input.readOnly) {
		editorPromise.then((editor) => {
			editor.setReadOnly(true);
		});
	}
	else if (Liferay.FeatureFlags['LPD-37927']) {
		const inputContainer = document.getElementById(
			`${fragmentEntryLinkNamespace}-rich-text-input`
		);

		import('@liferay/fragment-impl').then(
			({registerLocalizedInput, registerUnlocalizedInput}) => {
				if (input.localizable) {
					const {onChange} = registerLocalizedInput({
						defaultLanguageId: themeDisplay.getDefaultLanguageId(),
						initialValues: input.valueI18n,
						inputName: input.name,
						localizationInputsContainer: inputContainer,
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
								const inputLabel = document.querySelector(
									`label[for="${editorName}"]`
								);
								const isReadOnly =
									input.attributes.unlocalizedFieldsState ===
									'read-only';

								if (
									languageId ===
									themeDisplay.getDefaultLanguageId()
								) {
									editor.setReadOnly(false);

									if (isReadOnly) {
										inputLabel.innerHTML = input.label;
									}
								}
								else {
									editor.setReadOnly(true);

									if (isReadOnly) {
										inputLabel.innerHTML =
											inputContainer.dataset.readonlyLabel;
									}
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
}
