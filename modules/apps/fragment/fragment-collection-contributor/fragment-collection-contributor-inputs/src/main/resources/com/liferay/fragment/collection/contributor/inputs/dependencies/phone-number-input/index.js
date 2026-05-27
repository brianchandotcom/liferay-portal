// Constants

const FIXED_COUNTRY_A2 = input.attributes.country || '';
const IS_FIXED = input.attributes.countrySource === 'fixed';
const SHOW_FLAG = configuration.showCountryFlag;

const SPRITEMAP =
	typeof themeDisplay !== 'undefined'
		? themeDisplay.getPathThemeImages() + '/clay/icons.svg'
		: '';

// DOM elements

const inputElement = document.getElementById(`${fragmentElementId}-input`);

const countryCode = document.getElementById(
	`${fragmentElementId}-country-code`
);

const countryCodeFlag = document.getElementById(
	`${fragmentElementId}-country-code-flag`
);

const countryCodeMenu = document.getElementById(
	`${fragmentElementId}-country-code-menu`
);

const countryCodePicker = document.getElementById(
	`${fragmentElementId}-country-code-picker`
);

const countryCodeTrigger = document.getElementById(
	`${fragmentElementId}-country-code-trigger`
);

const countryCodeValueInput = document.getElementById(
	`${fragmentElementId}-country-code-value`
);

const hiddenInputContainer = document.getElementById(
	`${fragmentElementId}-hidden-input-container`
);

// Main logic

async function main() {
	const {
		focusInput,
		getFlagSymbol,
		parsePhoneValue,
		registerLocalizedInput,
		registerUnlocalizedInput,
	} = await import('@liferay/fragment-impl/api');

	const COUNTRIES = (input.attributes.countries || []).map((country) => ({
		a2: country.a2,
		flagSymbol: getFlagSymbol(country.a2),
		idd: country.idd,
		name: country.name,
	}));

	const fixedCountry = COUNTRIES.find(
		(country) => country.a2 === FIXED_COUNTRY_A2
	);

	const fixedPrefix = fixedCountry ? `+${fixedCountry.idd}` : '';

	// Utils

	function getSelectedCountry() {
		if (!countryCodeValueInput?.value) {
			return null;
		}

		return COUNTRIES.find(
			(country) => country.a2 === countryCodeValueInput.value
		);
	}

	function getCombinedValue() {
		const digits = inputElement.value.replace(/\D/g, '');

		if (!digits) {
			return '';
		}

		if (IS_FIXED) {
			return `${fixedPrefix}${digits}`;
		}

		const country = getSelectedCountry();

		return country ? `+${country.idd}${digits}` : digits;
	}

	function renderClayIcon(symbol) {
		if (!symbol) {
			return '';
		}

		return `<svg class="lexicon-icon lexicon-icon-${symbol}" role="presentation"><use href="${SPRITEMAP}#${symbol}" /></svg>`;
	}

	function selectCountry(a2) {
		const country = COUNTRIES.find((c) => c.a2 === a2);

		if (!country) {
			return;
		}

		countryCodeValueInput.value = country.a2;
		countryCode.textContent = `+${country.idd}`;

		if (SHOW_FLAG && countryCodeFlag) {
			countryCodeFlag.innerHTML = renderClayIcon(country.flagSymbol);
		}

		for (const item of countryCodeMenu.querySelectorAll(
			'.phone-number-input-country-code-menu-item'
		)) {
			const selected = item.dataset.a2 === country.a2;

			item.classList.toggle('active', selected);
			item.setAttribute('aria-selected', selected);

			const indicator = item.querySelector(
				'.dropdown-item-indicator-start'
			);

			if (indicator) {
				indicator.innerHTML = selected
					? renderClayIcon('check-small')
					: '';
			}
		}
	}

	function toggleCountryCodeMenu(open) {
		const next =
			typeof open === 'boolean'
				? open
				: !countryCodeMenu.classList.contains('show');

		countryCodeMenu.classList.toggle('show', next);
		countryCodeTrigger.setAttribute('aria-expanded', next);
	}

	function syncFromValue(value) {
		if (IS_FIXED) {
			if (value && value.startsWith(fixedPrefix)) {
				inputElement.value = value.slice(fixedPrefix.length);
			}

			return;
		}

		const {countryA2, localNumber} = parsePhoneValue(
			value || '',
			COUNTRIES
		);

		inputElement.value = localNumber;

		if (countryA2) {
			selectCountry(countryA2);
		}
	}

	// Event handlers

	function handleTriggerClick() {
		toggleCountryCodeMenu();
	}

	function handleTriggerKeydown(event) {
		if (event.key === 'ArrowDown' || event.key === 'Enter') {
			event.preventDefault();
			toggleCountryCodeMenu(true);

			const activeItem =
				countryCodeMenu.querySelector(
					'.phone-number-input-country-code-menu-item.active'
				) ||
				countryCodeMenu.querySelector(
					'.phone-number-input-country-code-menu-item'
				);

			activeItem?.focus();
		}
		else if (event.key === 'Escape') {
			toggleCountryCodeMenu(false);
		}
	}

	function handleMenuClick(event) {
		const item = event.target.closest(
			'.phone-number-input-country-code-menu-item'
		);

		if (!item) {
			return;
		}

		selectCountry(item.dataset.a2);
		toggleCountryCodeMenu(false);

		countryCodeTrigger.dispatchEvent(new Event('change', {bubbles: true}));
	}

	function handleDocumentClick(event) {
		if (!countryCodePicker.contains(event.target)) {
			toggleCountryCodeMenu(false);
		}
	}

	// Initial sync of input value into local number + country

	syncFromValue(input.value);

	// Autofocus on backend error

	if (input.errorMessage) {
		focusInput(inputElement);
	}

	// Add country code picker listeners

	if (!IS_FIXED && countryCodeTrigger && countryCodeMenu) {
		countryCodeTrigger.addEventListener('click', handleTriggerClick);
		countryCodeTrigger.addEventListener('keydown', handleTriggerKeydown);

		countryCodeMenu.addEventListener('click', handleMenuClick);

		document.addEventListener('click', handleDocumentClick);
	}

	// Register input

	const defaultLanguageId = themeDisplay.getDefaultLanguageId();

	if (input.localizable) {
		const {onChange} = registerLocalizedInput({
			availableLanguageIds: input.attributes.availableLanguageIds,
			defaultLanguageId,
			initialValues: input.valueI18n,
			inputElement,
			inputName: input.name,
			localizationInputsContainer: hiddenInputContainer,
			namespace: fragmentElementId,
		});

		const handleChange = () => onChange(getCombinedValue());

		inputElement.addEventListener('input', handleChange);
		countryCodeTrigger?.addEventListener('change', handleChange);

		Liferay.on('localizationSelect:localeChanged', () => {
			requestAnimationFrame(() => {
				syncFromValue(inputElement.value);
			});
		});
	}
	else {
		registerUnlocalizedInput({
			defaultLanguageId,
			inputElement,
			readOnlyInputLabel: document.getElementById(
				`${fragmentElementId}-read-only-label`
			),
			unlocalizedFieldsState: input.attributes.unlocalizedFieldsState,
			unlocalizedMessageContainer: document.getElementById(
				`${fragmentElementId}-unlocalized-info`
			),
		});

		inputElement.closest('form')?.addEventListener(
			'submit',
			() => {
				inputElement.value = getCombinedValue();
			},
			true
		);
	}
}

// Just disable the input if we are in edit mode

if (layoutMode === 'edit') {
	inputElement.setAttribute('disabled', true);

	countryCodeTrigger?.setAttribute('disabled', true);
}

// Otherwise, execute main logic

else if (!input.readOnly) {
	main();
}
