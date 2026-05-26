const currentLength = document.getElementById(
	`${fragmentElementId}-current-length`
);
const dropdown = document.getElementById(`${fragmentElementId}-dropdown`);
const error = document.getElementById(`${fragmentElementId}-email-input-error`);
const errorMessage = document.getElementById(
	`${fragmentElementId}-email-input-error-message`
);
const formGroup = document.getElementById(`${fragmentElementId}-form-group`);
const inputElement = document.getElementById(
	`${fragmentElementId}-email-input`
);
const lengthInfo = document.getElementById(`${fragmentElementId}-length-info`);

function main() {
	if (layoutMode === 'edit' && inputElement) {
		inputElement.setAttribute('disabled', true);

		return;
	}

	setupAutocomplete();

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

			if (input.localizable) {
				const {onChange} = registerLocalizedInput({
					availableLanguageIds: input.attributes.availableLanguageIds,
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
						`${fragmentElementId}-email-input-readonly`
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

main();

function setupAutocomplete() {
	if (!dropdown) {
		return;
	}

	const options = Array.from(dropdown.querySelectorAll('[role="option"]'));

	const getVisibleOptions = () =>
		options.filter(
			(option) => !option.parentElement.classList.contains('d-none')
		);

	const setDropdownVisible = (visible) => {
		dropdown.classList.toggle('d-none', !visible);
		dropdown.classList.toggle('show', visible);

		inputElement.setAttribute('aria-expanded', String(visible));
	};

	const hideDropdown = () => setDropdownVisible(false);

	const refreshDropdown = () => {
		const value = inputElement.value;
		const atIndex = value.lastIndexOf('@');
		const suffix = atIndex === -1 ? null : value.slice(atIndex);

		let hasMatches = false;

		options.forEach((option) => {
			const {domain} = option.dataset;
			const matches =
				suffix !== null &&
				domain.startsWith(suffix) &&
				suffix.length < domain.length;

			option.parentElement.classList.toggle('d-none', !matches);

			if (matches) {
				hasMatches = true;
			}
		});

		setDropdownVisible(hasMatches);
	};

	const moveFocusToOption = (delta) => {
		const visibleOptions = getVisibleOptions();

		if (!visibleOptions.length) {
			return;
		}

		const current = visibleOptions.indexOf(document.activeElement);
		const startIndex =
			current === -1 && delta < 0 ? visibleOptions.length : current;
		const next =
			(startIndex + delta + visibleOptions.length) %
			visibleOptions.length;

		visibleOptions[next].focus();
	};

	const selectOption = (option) => {
		const value = inputElement.value;
		const atIndex = value.lastIndexOf('@');
		const prefix = atIndex === -1 ? value : value.slice(0, atIndex);

		inputElement.value = prefix + option.dataset.domain;
		inputElement.dispatchEvent(new Event('change', {bubbles: true}));

		hideDropdown();

		inputElement.focus();
	};

	options.forEach((option) => {
		option.addEventListener('click', () => selectOption(option));
	});

	inputElement.addEventListener('focus', refreshDropdown);

	inputElement.addEventListener('input', refreshDropdown);

	fragmentElement.addEventListener('focusout', (event) => {
		if (!fragmentElement.contains(event.relatedTarget)) {
			hideDropdown();
		}
	});

	fragmentElement.addEventListener('keydown', (event) => {
		if (dropdown.classList.contains('d-none')) {
			return;
		}

		if (event.key === 'ArrowDown') {
			event.preventDefault();
			moveFocusToOption(1);
		}
		else if (event.key === 'ArrowUp') {
			event.preventDefault();
			moveFocusToOption(-1);
		}
		else if (
			event.key === 'Enter' &&
			document.activeElement.matches('[role="option"]')
		) {
			event.preventDefault();
			selectOption(document.activeElement);
		}
		else if (event.key === 'Escape') {
			hideDropdown();
			inputElement.focus();
		}
	});
}
