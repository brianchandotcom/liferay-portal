const currentLength = document.getElementById(
	`${fragmentElementId}-current-length`
);
const error = document.getElementById(
	`${fragmentElementId}-video-previewer-error`
);
const errorMessage = document.getElementById(
	`${fragmentElementId}-video-previewer-error-message`
);
const formGroup = document.getElementById(`${fragmentElementId}-form-group`);
const inputElement = document.getElementById(
	`${fragmentElementId}-video-previewer-input`
);
const lengthInfo = document.getElementById(`${fragmentElementId}-length-info`);
const videoPreview = document.getElementById(
	`${fragmentElementId}-video-preview`
);

function getFragmentTranslationInput(namespace, languageId, inputId) {
	return document.getElementById(`${namespace}${inputId}_${languageId}`);
}

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
				updateDLVideo,
			}) => {
				let previousUrl = null;

				const onUpdate = (html, title) => {
					videoPreview.innerHTML = html;

					if (html) {
						const iframe = videoPreview.querySelector('iframe');

						iframe.title =
							configuration.videoTitle ||
							title ||
							configuration.previewLabel;
					}
				};

				const updateVideoPreview = (url) => {
					if (previousUrl !== url) {
						updateDLVideo({onUpdate, url});
					}

					previousUrl = url;
				};

				if (input.value) {
					updateVideoPreview(input.value);
				}

				inputElement.addEventListener('blur', (event) => {
					updateVideoPreview(event.target.value);
				});

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

				let currentLanguageId = defaultLanguageId;

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
						onLocaleChange: ({languageId, value}) => {
							currentLanguageId = languageId;

							updateVideoPreview(value);
						},
						onResetTranslation: () => {
							const defaultTranslationInput =
								getFragmentTranslationInput(
									fragmentElementId,
									defaultLanguageId,
									inputElement.id
								);

							const translationInput =
								getFragmentTranslationInput(
									fragmentElementId,
									currentLanguageId,
									inputElement.id
								);

							updateVideoPreview(defaultTranslationInput.value);

							inputElement.value = defaultTranslationInput.value;

							translationInput.removeAttribute('value');
						},
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
							`${fragmentElementId}-video-previewer-readonly`
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
