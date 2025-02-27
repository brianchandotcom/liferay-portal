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

	if (input.readOnly || input.attributes?.disabled) {
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
				const defaultLanguageId = themeDisplay.getDefaultLanguageId();

				if (input.localizable) {
					const {onChange} = registerLocalizedInput({
						changeTextDirection: false,
						defaultLanguageId,
						initialValues: input.valueI18n,
						inputName: input.name,
						localizationInputsContainer: inputContainer,
						namespace: fragmentNamespace,
						onLocaleChange: ({languageId, value}) => {
							editorPromise.then((editor) => {
								editor.config.contentsLangDirection =
									Liferay.Language.direction[languageId];

								editor.setData(value);
							});
						},
					});

					editorPromise.then((editor) => {
						editor.config.contentsLangDirection =
							Liferay.Language.direction[defaultLanguageId];

						editor.on('change', () => {
							const value = editor.getData();

							onChange({value});
						});
					});
				}
				else {
					registerUnlocalizedInput({
						changeTextDirection: false,
						defaultLanguageId,
						onLocaleChange: (languageId) => {
							editorPromise.then((editor) => {
								const editorWrapper = document.getElementById(
									`cke_${editorName}`
								);
								const iframe =
									editorWrapper.querySelector('iframe');
								const inputLabel = document.querySelector(
									`label[for="${editorName}"]`
								);
								const isReadOnly =
									input.attributes.unlocalizedFieldsState ===
									'read-only';

								editor.config.contentsLangDirection =
									Liferay.Language.direction[languageId];

								editor.setData(editor.getData());

								if (languageId === defaultLanguageId) {
									editor.setReadOnly(false);

									if (isReadOnly) {
										inputLabel.innerHTML = input.label;
									}
									else {
										editorWrapper.classList.remove(
											'rich-text-input--disabled'
										);

										iframe.setAttribute('tabindex', '0');

										iframe.contentDocument.body.removeAttribute(
											'aria-disabled'
										);
									}
								}
								else {
									editor.setReadOnly(true);

									if (isReadOnly) {
										inputLabel.innerHTML =
											inputContainer.dataset.readonlyLabel;
									}
									else {
										editorWrapper.classList.add(
											'rich-text-input--disabled'
										);

										iframe.setAttribute('tabindex', '-1');

										iframe.contentDocument.body.setAttribute(
											'aria-disabled',
											'true'
										);
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

					editorPromise.then((editor) => {
						editor.config.contentsLangDirection =
							Liferay.Language.direction[defaultLanguageId];
					});
				}
			}
		);
	}
}
