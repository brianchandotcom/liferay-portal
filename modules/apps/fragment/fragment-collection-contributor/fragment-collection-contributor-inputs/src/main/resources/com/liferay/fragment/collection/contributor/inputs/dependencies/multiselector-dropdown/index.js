// Constants

const DEFAULT_LOCALE = themeDisplay.getDefaultLanguageId();
const IS_RTL = document.documentElement.classList.contains('rtl');

// DOM elements

const inputElement = document.getElementById(`${fragmentElementId}-input`);
const dropdown = document.getElementById(`${fragmentElementId}-dropdown`);
const optionList = document.getElementById(`${fragmentElementId}-option-list`);

const labelContainer = document.getElementById(
	`${fragmentElementId}-label-container`
);

const hiddenInputContainer = document.getElementById(
	`${fragmentElementId}-hidden-input-container`
);

const noResultsMessage = document.getElementById(
	`${fragmentElementId}-no-results-message`
);

const loadingIndicator = document.getElementById(
	`${fragmentElementId}-loading-indicator`
);

const loadingMessage = document.getElementById(
	`${fragmentElementId}-loading-message`
);

const clearButton = document.getElementById(
	`${fragmentElementId}-clear-button`
);

// Util to remove loading elements

function removeLoadingElements() {
	loadingIndicator.remove();

	loadingMessage.remove();
}

// Main logic

async function main() {
	const {registerLocalizedInput, registerUnlocalizedInput} = await import(
		'@liferay/fragment-impl/api'
	);

	// State

	const selectionMap = initializeSelectionMap(); // Map with selection for all languages

	let selection = selectionMap.get(DEFAULT_LOCALE); // Selection for current language

	let initialOptions = input.attributes.options || [];

	let options = initialOptions;

	let filter = '';

	let loading = true;

	// Util to render hidden inputs with selected values

	let renderHiddenInputs = (values) => {
		hiddenInputContainer.innerHTML = '';

		const appendInput = (value) => {
			const hiddenInput = document.createElement('input');

			hiddenInput.type = 'hidden';
			hiddenInput.name = input.name;
			hiddenInput.value = value;

			hiddenInputContainer.appendChild(hiddenInput);
		};

		if (values.length) {
			for (const value of values) {
				appendInput(value);
			}
		}
		else {
			appendInput('');
		}
	};

	// If input is localizable, register it and replace function to render hidden inputs

	if (input.localizable) {
		const {onChange} = registerLocalizedInput({
			customLocaleChangeHandler: true,
			defaultLanguageId: themeDisplay.getDefaultLanguageId(),
			hasMultipleValues: true,
			initialValues: input.valueI18n,
			inputElement,
			inputName: input.name,
			localizationInputsContainer: hiddenInputContainer,
			onLocaleChange: ({languageId}) => {

				// If we have selection for this language, just set it and refresh

				if (selectionMap.has(languageId)) {
					selection = selectionMap.get(languageId);

					refreshUI();

					return;
				}

				// Otherwise, refresh with default language selection

				selection = selectionMap.get(DEFAULT_LOCALE);

				refreshUI();

				// Create selection for new language

				selectionMap.set(languageId, new Set(selection));

				selection = selectionMap.get(languageId);
			},
			onMarkAsTranslated: () => {
				renderHiddenInputs([...selection]);
			},
			onResetTranslation: () => {
				renderHiddenInputs([]);

				selection = selectionMap.get(DEFAULT_LOCALE);

				refreshUI();
			},
		});

		renderHiddenInputs = onChange;
	}

	// Otherwise, register unlocalized input and render initial hidden inputs

	else {
		registerUnlocalizedInput({
			defaultLanguageId: themeDisplay.getDefaultLanguageId(),
			inputElement,
			readOnlyInputLabel: document.getElementById(
				`${fragmentElementId}-read-only-label`
			),
			unlocalizedFieldsState: input.attributes.unlocalizedFieldsState,
			unlocalizedMessageContainer: document.getElementById(
				`${fragmentElementId}-unlocalized-info`
			),
		});

		renderHiddenInputs([...selection]);
	}

	// Utils to manage dropdown

	function isDropdownOpen() {
		return inputElement.getAttribute('aria-expanded') === 'true';
	}

	function openDropdown() {
		if (isDropdownOpen()) {
			return;
		}

		dropdown.classList.remove('d-none');
		dropdown.classList.add('show');

		inputElement.setAttribute('aria-expanded', 'true');

		const wrapperWidth = `${fragmentElement.getBoundingClientRect().width}px`;

		dropdown.style.maxWidth = wrapperWidth;
		dropdown.style.minWidth = wrapperWidth;
		dropdown.style.width = wrapperWidth;

		requestAnimationFrame(() => {
			positionDropdown();
		});
	}

	function closeDropdown() {
		dropdown.classList.remove('show');
		dropdown.classList.add('d-none');

		inputElement.setAttribute('aria-expanded', 'false');
	}

	function positionDropdown() {
		if (!isDropdownOpen()) {
			return;
		}

		const inputGroup = fragmentElement.querySelector(
			'.form-control-tag-group'
		);
		const inputGroupRect = inputGroup.getBoundingClientRect();

		const fragmentRect = fragmentElement.getBoundingClientRect();

		const top = inputGroupRect.bottom - fragmentRect.top;

		const left = IS_RTL
			? inputGroupRect.right - fragmentRect.left - dropdown.offsetWidth
			: inputGroupRect.left - fragmentRect.left;

		dropdown.style.top = `${top}px`;
		dropdown.style.left = `${left}px`;
	}

	// Utils to manage state and DOM

	function getRelationshipOptions() {
		if (
			!input.attributes.relationshipLabelFieldName ||
			!input.attributes.relationshipURL ||
			!input.attributes.relationshipValueFieldName
		) {
			return Promise.resolve({items: []});
		}

		const url = new URL(input.attributes.relationshipURL);

		url.searchParams.set('pageSize', 0);

		return Liferay.Util.fetch(url, {
			headers: new Headers({
				'Accept': 'application/json',
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type': 'application/json',
			}),
			method: 'GET',
		})
			.then((response) => response.json())
			.then((result) => {
				return result.items.map((entry) => {
					const finalEntry = entry.embedded || entry;

					let label =
						finalEntry[input.attributes.relationshipLabelFieldName];

					if (Array.isArray(label)) {
						label = label.map((label) => label.name).join(', ');
					}
					else if (typeof label === 'object') {
						label = label.name;
					}

					return {
						label,
						value: String(
							finalEntry[
								input.attributes.relationshipValueFieldName
							]
						),
					};
				});
			});
	}

	function initializeSelectionMap() {
		const map = new Map();

		map.set(DEFAULT_LOCALE, new Set());

		if (input.localizable) {
			for (const [locale, value] of Object.entries(input.valueI18n)) {
				map.set(locale, new Set(value.split(',')));
			}
		}
		else {
			map.set(
				DEFAULT_LOCALE,
				new Set(input.value ? input.value.split(',') : [])
			);
		}

		return map;
	}

	function setFilter(value) {
		filter = value;

		inputElement.value = value;

		if (!filter) {
			options = initialOptions;
		}
		else {
			options = initialOptions.filter(({label}) =>
				label.toLowerCase().includes(filter)
			);
		}
	}

	function markOptionLabel(label) {
		const text = label.textContent;

		label.innerHTML = '';

		const index = text.toLowerCase().indexOf(filter);

		const before = text.substring(0, index);
		const match = text.substring(index, index + filter.length);
		const after = text.substring(index + filter.length);

		if (before) {
			const beforeSpan = document.createElement('span');

			beforeSpan.textContent = before;

			label.appendChild(beforeSpan);
		}

		const matchSpan = document.createElement('strong');

		matchSpan.className = 'mark';
		matchSpan.textContent = match;

		label.appendChild(matchSpan);

		if (after) {
			const afterSpan = document.createElement('span');

			afterSpan.textContent = after;

			label.appendChild(afterSpan);
		}
	}

	function renderOptions() {
		optionList.innerHTML = '';

		noResultsMessage.classList.add('d-none');

		if (loading) {
			return;
		}

		if (!options.length) {
			noResultsMessage.classList.remove('d-none');

			return;
		}

		for (const option of options) {
			const isSelected = selection.has(option.value);

			const element = isSelected
				? cloneTemplate('.selected-option-template')
				: cloneTemplate('.option-template');

			const label = element.querySelector('.option-label');

			label.textContent = option.label;

			if (filter) {
				markOptionLabel(label);
			}

			const button = element.querySelector('button');

			button.addEventListener('click', (event) => {
				onSelectOption(option);

				if (event.detail === 0) {
					inputElement.focus();
				}
			});

			button.addEventListener('keydown', handleKeydown);

			optionList.appendChild(element);
		}
	}

	function renderLabels() {
		labelContainer.innerHTML = '';

		if (selection.size) {
			inputElement.removeAttribute('required');
		}
		else {
			inputElement.setAttribute('required', '');
		}

		if (!options.length) {
			return;
		}

		for (const value of [...selection]) {
			const option = options.find((option) => option.value === value);

			// Append label element

			const label = cloneTemplate('.label-template');

			const text = label.querySelector('.label-item');

			text.textContent = option.label;

			labelContainer.appendChild(label);

			// Add click listener to remove button

			const removeButton = label.querySelector('button');

			removeButton.addEventListener('click', () => {
				label.remove();

				selection.delete(option.value);

				refreshUI();

				renderHiddenInputs([...selection]);

				inputElement.focus();
			});
		}
	}

	function enableLoading() {
		loading = true;

		loadingMessage.classList.remove('d-none');

		clearButton.setAttribute('disabled', '');
	}

	function disableLoading() {
		loading = false;

		removeLoadingElements();

		clearButton.removeAttribute('disabled');
	}

	function showClearButton() {
		clearButton.classList.remove('d-none');
		clearButton.setAttribute('aria-hidden', 'false');
	}

	function hideClearButton() {
		clearButton.classList.add('d-none');
		clearButton.setAttribute('aria-hidden', 'true');
	}

	function refreshUI() {
		if (loading) {
			return;
		}

		renderLabels();
		renderOptions();

		if (selection.size) {
			showClearButton();
		}
		else {
			hideClearButton();
		}
	}

	function onSelectOption(option) {
		if (!selection.has(option.value)) {
			selection.add(option.value);
		}

		renderHiddenInputs([...selection]);

		closeDropdown();
		setFilter('');
		refreshUI();
	}

	function cloneTemplate(className) {
		const template = fragmentElement.querySelector(className);

		const node = template.content.cloneNode(true);

		return node.firstElementChild;
	}

	// Event handlers

	function handleInputBlur(event) {
		const relatedTarget = event.relatedTarget;

		if (
			relatedTarget &&
			relatedTarget.classList.contains('dropdown-item')
		) {
			return;
		}

		closeDropdown();
	}

	function handleInputInput(event) {
		setFilter(event.target.value.toLowerCase());

		renderOptions();

		openDropdown();
	}

	function handleKeydown(event) {
		openDropdown();

		const buttons = Array.from(
			optionList.querySelectorAll('button.dropdown-item')
		);

		if (!buttons.length) {
			return;
		}

		// We are navigating through the option list

		if (event.key === 'ArrowDown') {
			if (event.target === inputElement) {
				buttons[0].focus();
			}
			else {
				const currentIndex = buttons.indexOf(event.target);

				const nextIndex = (currentIndex + 1) % buttons.length;

				buttons[nextIndex].focus();
			}
		}
		else if (event.key === 'ArrowUp') {
			if (!event.target.classList.contains('dropdown-item')) {
				return;
			}

			const currentIndex = buttons.indexOf(event.target);

			const prevIndex =
				currentIndex === 0 ? buttons.length - 1 : currentIndex - 1;

			buttons[prevIndex].focus();
		}

		// We are selecting an option

		else if (event.key === 'Enter') {
			if (event.target !== inputElement) {
				return;
			}

			event.preventDefault();

			for (const option of options) {
				if (filter === option.label.toLowerCase()) {
					onSelectOption(option);
				}
			}
		}
	}

	function handleClearButtonClick() {
		selection.clear();

		renderHiddenInputs([...selection]);

		refreshUI();
	}

	function handleDropdownBlur(event) {
		const relatedTarget = event.relatedTarget;

		if (
			relatedTarget &&
			(relatedTarget === inputElement || dropdown.contains(relatedTarget))
		) {
			return;
		}

		closeDropdown();
	}

	// Add listeners

	inputElement.addEventListener('click', openDropdown);
	inputElement.addEventListener('blur', handleInputBlur);
	inputElement.addEventListener('input', handleInputInput);
	inputElement.addEventListener('keydown', handleKeydown);

	dropdown.addEventListener('focusout', handleDropdownBlur, true);

	clearButton.addEventListener('click', handleClearButtonClick);

	window.addEventListener('resize', positionDropdown, {
		passive: true,
	});

	window.addEventListener('scroll', positionDropdown, {
		passive: true,
	});

	// Ask for options if it's a relationship

	if (input.attributes.relationshipURL) {
		enableLoading();

		refreshUI();

		initialOptions = await getRelationshipOptions();

		options = initialOptions;
	}

	// Disable loading and perform initial render

	disableLoading();

	refreshUI();
}

// Just disable the input if we are in edit mode

if (layoutMode === 'edit') {
	inputElement.setAttribute('disabled', true);

	removeLoadingElements();
}

// Otherwise, execute main logic

else if (!input.readOnly) {
	main();
}
