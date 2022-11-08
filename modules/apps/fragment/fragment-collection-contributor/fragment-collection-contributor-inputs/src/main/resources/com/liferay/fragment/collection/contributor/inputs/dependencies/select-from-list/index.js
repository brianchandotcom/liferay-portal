const RAPID_TEXT_DELAY = 300;

let rapidText = '';
let rapidTextTime = Date.now();

const wrapper = fragmentElement;

const button = wrapper.querySelector('.form-control');
const buttonLabel = button.querySelector('.forms-select-from-list-label');
const defaultOptionLabel = document.getElementById(
	`${fragmentNamespace}-option--`
).textContent;
const dropdown = wrapper.querySelector('.dropdown-menu');
const inputElement = wrapper.querySelector(`input[name="${input.name}"]`);
const labelInputElement = wrapper.querySelector(
	`input[name="${input.name}-label"]`
);
const listbox = wrapper.querySelector('.list-unstyled');
const loadingResultsMessage = wrapper.querySelector(
	'.forms-select-from-list-loading-results'
);
const noResultsMessage = wrapper.querySelector(
	'.forms-select-from-list-no-results'
);
const searchInput = wrapper.querySelector('.forms-select-from-list-search');
const searchInputWrapper = wrapper.querySelector(
	'.forms-select-from-list-search-wrapper'
);

const MAX_ITEMS = 10;

let defaultOptions = (input.attributes.options || []).slice(0, MAX_ITEMS);
let lastSearchAbortController = new AbortController();
let lastSearchQuery = null;

function debounce(fn, delay) {
	let debounceId = null;

	return function (...args) {
		clearTimeout(debounceId);
		debounceId = setTimeout(() => fn(...args), delay);
	};
}

function hideElement(element) {
	element.classList.add('d-none');
	element.setAttribute('aria-hidden', 'true');
}

function isVisible(element) {
	return !element.hasAttribute('aria-hidden');
}
const combobox = fragmentElement.querySelector('.combobox-list');
const inputNode = combobox.querySelector('.combobox-input');
const buttonNode = combobox.querySelector('.combobox-button');
const optionList = combobox.querySelector('.combobox-optionList');

buttonNode.addEventListener("click", toggleCombobox);
buttonNode.addEventListener("blur", handleComboboxBlur);
inputNode.addEventListener("click", toggleCombobox);
inputNode.addEventListener("input", handleInputChange);
inputNode.addEventListener("blur", handleComboboxBlur);
inputNode.addEventListener("keydown", handleComboboxKeyDown);
optionList.addEventListener("click", handleClickOptionList);

const allOptions = Array.from(optionList.getElementsByTagName("LI")).map((option) => {
	return { id: option.id, textContent: option.textContent, textValue: option.textContent.toLowerCase() };
});

function showElement(element) {
	element.classList.remove('d-none');
	element.removeAttribute('aria-hidden');
function checkIsOpenCombobox() {
	return (
		inputNode.getAttribute("aria-expanded") === "true" &&
		buttonNode.getAttribute("aria-expanded") === "true"
	);
}

function repositionDropdown() {
	const buttonRect = button.getBoundingClientRect();

	if (layoutMode === 'edit') {
		dropdown.style.position = 'fixed';

		dropdown.style.transform = `
			translateX(${buttonRect.left}px)
			translateY(${buttonRect.bottom}px)
		`;
	}
	else {
		if (document.body.contains(wrapper)) {
			if (wrapper.contains(dropdown)) {
				document.body.appendChild(dropdown);
			}
		}
		else if (document.body.contains(dropdown)) {
			dropdown.parentNode.removeChild(dropdown);
		}

		dropdown.style.position = 'absolute';
function closeCombobox() {
	const currentFocusedOption =
		document.getElementById(optionList.getAttribute(
			"aria-activedescendant"
		));

		dropdown.style.transform = `
			translateX(${buttonRect.left + window.scrollX}px)
			translateY(${buttonRect.bottom + window.scrollY}px)
		`;
	}
}
	optionList.setAttribute("aria-activedescendant", "");

function showDropdown() {
	const canFetchOptions = input.attributes.relationshipURL && searchInput;

	if (canFetchOptions || defaultOptions.length) {
		repositionDropdown();
		button.setAttribute('aria-expanded', 'true');
		dropdown.classList.add('show');
	if (currentFocusedOption) {
		currentFocusedOption.removeAttribute("aria-selected");
	}

	if (canFetchOptions && !defaultOptions.length) {
		showElement(loadingResultsMessage);

		fetchRemoteOptions('', lastSearchAbortController)
			.then((items) => {
				defaultOptions = items.slice(0, MAX_ITEMS);
	optionList.style.display = "none";

				if (items.length > MAX_ITEMS) {
					showElement(searchInputWrapper);
				}
				else {
					hideElement(searchInputWrapper);
				}

				setListboxItems(defaultOptions);
			})
			.finally(() => {
				hideElement(loadingResultsMessage);
			});
	}
	inputNode.setAttribute("aria-expanded", "false");
	buttonNode.setAttribute("aria-expanded", "false");
}

function hideDropdown() {
	button.removeAttribute('aria-expanded');
	dropdown.classList.remove('show');
function createOptionElement(option) {
	const optionElement = document.createElement('li');
	optionElement.id = option.id;
	optionElement.classList.add('dropdown-item');
	optionElement.setAttribute('role', 'option');
	optionElement.textContent = option.textContent;
	return optionElement;
}

function getActiveDesdendant() {
	return document.getElementById(
		listbox.getAttribute('aria-activedescendant')
	);
}

function setActiveDescendant(element) {
	const previousItem = getActiveDesdendant();

	if (previousItem && previousItem !== element) {
		previousItem.classList.remove('active');
		previousItem.removeAttribute('aria-selected');
	}

	if (element) {
		buttonLabel.textContent = element.textContent;

		buttonLabel.classList.toggle(
			'text-muted',
			!element.dataset.optionValue
		);
function handleClickOptionList(event){
	let optionSelected = null;

		listbox.setAttribute('aria-activedescendant', element.id);
		inputElement.value = element.dataset.optionValue;
		labelInputElement.value = element.textContent;

		element.classList.add('active');
		element.setAttribute('aria-selected', 'true');

		element.scrollIntoView({
			block: 'nearest',
		});
	if (event.target.matches('.dropdown-item')) {
		optionSelected = event.target;
	}
	else {
		buttonLabel.textContent = '';
		listbox.removeAttribute('aria-activedescendant');
		inputElement.value = '';
	else if (event.target.closest('.dropdown-item')) {
		optionSelected = event.target.closest('.dropdown-item');
	}
}

function handleButtonClick() {
	if (button.hasAttribute('aria-expanded')) {
		hideDropdown();
		button.focus();
	}
	else {
		showDropdown();

		if (isVisible(searchInputWrapper)) {
			searchInput.focus();
			searchInput.setSelectionRange(0, searchInput.value.length);
		}
		else {
			listbox.focus();
		}
	if (optionSelected) {
		setSelectedOption(optionSelected);
	}
}

function handleMovementKeys(event) {
	const currentActiveDescendant = getActiveDesdendant();

	if (event.key === 'Enter' && dropdown.classList.contains('show')) {
		hideDropdown();
		button.focus();
		event.preventDefault();
	}
	else if (event.key === 'ArrowDown') {
		showDropdown();
		listbox.focus();

		setActiveDescendant(
			currentActiveDescendant.nextElementSibling ||
				currentActiveDescendant ||
				listbox.firstElementChild
		);

		event.preventDefault();
	}
	else if (event.key === 'ArrowUp') {
		showDropdown();
		listbox.focus();

		setActiveDescendant(
			currentActiveDescendant.previousElementSibling ||
				currentActiveDescendant ||
				listbox.firstElementChild
		);

		event.preventDefault();
	}
	else if (event.key === 'Escape') {
		hideDropdown();
		button.focus();
		event.preventDefault();
	}
	else if (event.key === 'Home') {
		showDropdown();
		listbox.focus();
		setActiveDescendant(listbox.firstElementChild);
		event.preventDefault();
	}
	else if (event.key === 'End') {
		showDropdown();
		listbox.focus();
		setActiveDescendant(listbox.lastElementChild);
		event.preventDefault();
function handleComboboxBlur() {
	if (checkIsOpenCombobox()) {
		setTimeout(() => closeCombobox(),500);
	}
}

function handleKeydown(event) {
	if (event.key.length === 1) {
		const now = Date.now();

		if (now - rapidTextTime > RAPID_TEXT_DELAY) {
			rapidText = '';
		}
function handleComboboxKeyDown(event) {
	if (!optionList.firstElementChild) {
		return;
	}

		rapidText += event.key.toLowerCase();
		rapidTextTime = now;
	const keyOptions = [
		"ArrowDown", "ArrowUp", "Home", "End"
	]

		const rapidItem = Array.from(listbox.children).find(
			(child) =>
				child.dataset.optionValue &&
				child.textContent.trim().toLowerCase().startsWith(rapidText)
		);
	const currentFocusedOption =
		document.getElementById(optionList.getAttribute(
			"aria-activedescendant"
		));

		if (rapidItem) {
			showDropdown();
			listbox.focus();
			setActiveDescendant(rapidItem);
			event.preventDefault();
	if (keyOptions.includes(event.key)) {
		if (!checkIsOpenCombobox()) {
			openCombobox();
		}
	}
	else {
		handleMovementKeys(event);
	}
}

function handleListboxClick(event) {
	if (event.target.dataset?.optionValue !== undefined) {
		setActiveDescendant(event.target);
		hideDropdown();
		button.focus();
		event.preventDefault();
	}
}

function handleDocumentClick(event) {
	if (!document.body.contains(wrapper)) {
		document.removeEventListener('click', handleDocumentClick);

		return;
	}

	if (
		event.target !== wrapper &&
		!wrapper.contains(event.target) &&
		event.target !== dropdown &&
		!dropdown.contains(event.target)
	) {
		hideDropdown();
	}
}
	switch (event.key) {
		case "ArrowDown":
			if (event.altKey) {
				break;
			}

function handleWindowResizeOrScroll() {
	if (!document.body.contains(wrapper)) {
		document.removeEventListener('click', handleDocumentClick);
			if (currentFocusedOption) {
				setActiveDescendantCombobox(currentFocusedOption.nextElementSibling || optionList.firstElementChild);
			}
			else {
				setActiveDescendantCombobox(optionList.firstElementChild);
			}
			break;
		case "ArrowUp":
			if (currentFocusedOption) {
				setActiveDescendantCombobox(currentFocusedOption.previousElementSibling || optionList.lastElementChild);
			}
			else {
				setActiveDescendantCombobox(optionList.lastElementChild);
			}
			break;

		return;
	}
		case "Home":
			setActiveDescendantCombobox(optionList.firstElementChild);
			break;
		case "End":
			setActiveDescendantCombobox(optionList.lastElementChild);
			break;
		case "Enter":
			if (!currentFocusedOption) {
				break;
			}

	if (dropdown.classList.contains('show')) {
		repositionDropdown();
			setSelectedOption(currentFocusedOption);
	}
}

function filterLocalOptions(query) {
	const preferedItems = [];
	const restItems = [];

	for (const item of input.attributes.options) {
		if (preferedItems.length + restItems.length === MAX_ITEMS) {
			break;
		}
function handleInputChange() {
	if (!checkIsOpenCombobox()) {
		openCombobox();
	}

		const label = item.label.trim().toLowerCase();
	optionList.setAttribute("aria-activedescendant", "");

		if (label.startsWith(query)) {
			preferedItems.push(item);
		}
		else if (label.includes(query)) {
			restItems.push(item);
		}
	}
	const filterValue = inputNode.value.toLowerCase();

	return Promise.resolve(
		[...preferedItems, ...restItems].slice(0, MAX_ITEMS)
	);
}
	optionList.innerHTML = "";

function fetchRemoteOptions(query, abortController) {
	if (
		!input.attributes.relationshipLabelFieldName ||
		!input.attributes.relationshipURL ||
		!input.attributes.relationshipValueFieldName
	) {
		return Promise.resolve({items: []});
	}
	if (filterValue) {
		allOptions.forEach((option) => {
			if (
				option.textValue.startsWith(filterValue)
			) {
				optionList.appendChild(createOptionElement(option));
			}

	const url = new URL(input.attributes.relationshipURL);
	url.searchParams.set('search', query);

	return Liferay.Util.fetch(url, {
		headers: new Headers({
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		}),
		method: 'GET',
		signal: abortController.signal,
	})
		.then((response) => response.json())
		.then((result) => {
			return result.items.map((entry) => ({
				label: entry[input.attributes.relationshipLabelFieldName],
				value: entry[input.attributes.relationshipValueFieldName],
			}));
		});
}

function setListboxItems(items) {
	listbox.innerHTML = '';

	if (items.length) {
		showElement(listbox);
		hideElement(noResultsMessage);
		allOptions.forEach((option) => {
			if (
				option.textValue.includes(filterValue)
			) {
				if (!document.getElementById(option.id)) {
					optionList.appendChild(createOptionElement(option));
				}
			}
		});
	}
	else {
		hideElement(listbox);
		showElement(noResultsMessage);
		allOptions.forEach((option) => optionList.appendChild(createOptionElement(option)));
	}
}

	[{label: defaultOptionLabel, value: ''}, ...items].forEach((item) => {
		const element = document.createElement('li');

		element.classList.add('dropdown-item');

		if (!item.value) {
			element.classList.add('text-muted');
		}

		element.dataset.optionValue = item.value;
		element.id = `${fragmentNamespace}-option-${item.value}`;
		element.setAttribute('role', 'option');
		element.textContent = item.label;
function openCombobox() {
	optionList.style.display = "block";

		listbox.appendChild(element);
	});
	inputNode.setAttribute("aria-expanded", "true");
	buttonNode.setAttribute("aria-expanded", "true");

	setActiveDescendant(listbox.firstElementChild);
	handleInputChange();
}

function handleSearchInput() {
	if (searchInput.value === lastSearchQuery) {
		return;
	}

	lastSearchAbortController.abort();
	lastSearchAbortController = new AbortController();
	lastSearchQuery = searchInput.value;
function setActiveDescendantCombobox(option) {
	const currentFocusedOption =
		document.getElementById(optionList.getAttribute(
			"aria-activedescendant"
		));

	if (!lastSearchQuery) {
		setListboxItems(defaultOptions);

		return;
	if (currentFocusedOption) {
		currentFocusedOption.removeAttribute("aria-selected");
	}

	showElement(loadingResultsMessage);
	optionList.setAttribute("aria-activedescendant", option.id);
	option.setAttribute("aria-selected", "true");

	const fetcher = input.attributes.relationshipURL
		? fetchRemoteOptions
		: filterLocalOptions;

	fetcher(lastSearchQuery, lastSearchAbortController)
		.then((items) => {
			setListboxItems(items.slice(0, MAX_ITEMS));
		})
		.finally(() => {
			hideElement(loadingResultsMessage);
		});
	option.scrollIntoView({ block: "end" });
}

button.addEventListener('click', handleButtonClick);
button.addEventListener('keydown', handleKeydown);
listbox.addEventListener('keydown', handleKeydown);
listbox.addEventListener('click', handleListboxClick);
document.addEventListener('click', handleDocumentClick);
function setSelectedOption (option) {
	const hiddenInput = document.getElementById("hiddenInput");
	const selectedOption = document.getElementById(hiddenInput.id);

searchInput.addEventListener('keydown', handleMovementKeys);
searchInput.addEventListener('input', debounce(handleSearchInput, 500));
	if (selectedOption) {
		selectedOption.classList.remove("active");
	}

if ((input.attributes.options || []).length > MAX_ITEMS) {
	showElement(searchInputWrapper);
}
	option.classList.add("active");

window.addEventListener('resize', handleWindowResizeOrScroll, {
	passive: true,
});
window.addEventListener('scroll', handleWindowResizeOrScroll, {
	passive: true,
});
	inputNode.value = option.textContent;
	hiddenInput.value = option.id;

if (!getActiveDesdendant()) {
	setActiveDescendant(listbox.firstElementChild);
	closeCombobox();
}

dropdown.style.left = '0';
dropdown.style.top = '0';

repositionDropdown();
function toggleCombobox() {
	if (checkIsOpenCombobox()) {
		closeCombobox();
	}
	else {
		openCombobox();
	}
}
