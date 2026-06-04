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
const prefixCode = document.getElementById(`${fragmentElementId}-prefix-code`);
const prefixFlag = document.getElementById(`${fragmentElementId}-prefix-flag`);
const prefixMenu = document.getElementById(`${fragmentElementId}-prefix-menu`);

const prefixPicker = document.getElementById(
	`${fragmentElementId}-prefix-picker`
);

const prefixTrigger = document.getElementById(
	`${fragmentElementId}-prefix-trigger`
);

const prefixValueInput = document.getElementById(
	`${fragmentElementId}-prefix-value`
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
		idd: country.prefix,
		name: country.name,
	}));

	const FIXED_COUNTRY = IS_FIXED
		? COUNTRIES.find((country) => country.a2 === FIXED_COUNTRY_A2)
		: null;

	const FIXED_PREFIX = FIXED_COUNTRY ? `+${FIXED_COUNTRY.idd}` : '';

	// Utils

	function getSelectedCountry() {
		if (!prefixValueInput?.value) {
			return null;
		}

		return COUNTRIES.find(
			(country) => country.a2 === prefixValueInput.value
		);
	}

	function getCombinedValue() {
		const digits = inputElement.value.replace(/\D/g, '');

		if (!digits) {
			return '';
		}

		if (IS_FIXED) {
			return `${FIXED_PREFIX}${digits}`;
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

		prefixValueInput.value = country.a2;
		prefixCode.textContent = `+${country.idd}`;

		if (SHOW_FLAG && prefixFlag) {
			prefixFlag.innerHTML = renderClayIcon(country.flagSymbol);
		}

		for (const item of prefixMenu.querySelectorAll(
			'.phone-number-input-prefix-menu-item'
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

	function togglePrefixMenu(open) {
		const next =
			typeof open === 'boolean'
				? open
				: !prefixMenu.classList.contains('show');

		prefixMenu.classList.toggle('show', next);
		prefixTrigger.setAttribute('aria-expanded', next);
	}

	function syncFromValue(value) {
		if (IS_FIXED) {
			if (value && value.startsWith(FIXED_PREFIX)) {
				inputElement.value = value.slice(FIXED_PREFIX.length);
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
		togglePrefixMenu();
	}

	function handleTriggerKeydown(event) {
		if (event.key === 'ArrowDown' || event.key === 'Enter') {
			event.preventDefault();
			togglePrefixMenu(true);

			const activeItem =
				prefixMenu.querySelector(
					'.phone-number-input-prefix-menu-item.active'
				) ||
				prefixMenu.querySelector(
					'.phone-number-input-prefix-menu-item'
				);

			activeItem?.focus();
		}
		else if (event.key === 'Escape') {
			togglePrefixMenu(false);
		}
	}

	function handleMenuClick(event) {
		const item = event.target.closest(
			'.phone-number-input-prefix-menu-item'
		);

		if (!item) {
			return;
		}

		selectCountry(item.dataset.a2);
		togglePrefixMenu(false);

		prefixTrigger.dispatchEvent(new Event('change', {bubbles: true}));
	}

	function handleDocumentClick(event) {
		if (!prefixPicker.contains(event.target)) {
			togglePrefixMenu(false);
		}
	}

	// Initial sync of input value into local number + country

	syncFromValue(input.value);

	// Autofocus on backend error

	if (input.errorMessage) {
		focusInput(inputElement);
	}

	// Restrict typed characters to digits, spaces, dashes, parentheses and dots

	inputElement.addEventListener('input', () => {
		const filtered = inputElement.value.replace(/[^0-9\s\-().]/g, '');

		if (filtered !== inputElement.value) {
			inputElement.value = filtered;
		}
	});

	// Add prefix picker listeners

	if (!IS_FIXED && prefixTrigger && prefixMenu) {
		prefixTrigger.addEventListener('click', handleTriggerClick);
		prefixTrigger.addEventListener('keydown', handleTriggerKeydown);

		prefixMenu.addEventListener('click', handleMenuClick);

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
		prefixTrigger?.addEventListener('change', handleChange);

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

	prefixTrigger?.setAttribute('disabled', true);
}

// Otherwise, execute main logic

else if (!input.readOnly) {
	main();
}
