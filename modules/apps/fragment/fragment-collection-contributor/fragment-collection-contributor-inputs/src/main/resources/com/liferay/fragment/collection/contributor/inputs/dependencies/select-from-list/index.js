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

function showElement(element) {
	element.classList.remove('d-none');
	element.removeAttribute('aria-hidden');
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

		dropdown.style.transform = `
			translateX(${buttonRect.left + window.scrollX}px)
			translateY(${buttonRect.bottom + window.scrollY}px)
		`;
	}
}

function showDropdown() {
	const canFetchOptions = input.attributes.relationshipURL && searchInput;

	if (canFetchOptions || defaultOptions.length) {
		repositionDropdown();
		button.setAttribute('aria-expanded', 'true');
		dropdown.classList.add('show');
	}

	if (canFetchOptions && !defaultOptions.length) {
		showElement(loadingResultsMessage);

		fetchRemoteOptions('', lastSearchAbortController)
			.then((items) => {
				defaultOptions = items.slice(0, MAX_ITEMS);

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
}

function hideDropdown() {
	button.removeAttribute('aria-expanded');
	dropdown.classList.remove('show');
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

		listbox.setAttribute('aria-activedescendant', element.id);
		inputElement.value = element.dataset.optionValue;
		labelInputElement.value = element.textContent;

		element.classList.add('active');
		element.setAttribute('aria-selected', 'true');

		element.scrollIntoView({
			block: 'nearest',
		});
	}
	else {
		buttonLabel.textContent = '';
		listbox.removeAttribute('aria-activedescendant');
		inputElement.value = '';
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
	}
}

function handleKeydown(event) {
	if (event.key.length === 1) {
		const now = Date.now();

		if (now - rapidTextTime > RAPID_TEXT_DELAY) {
			rapidText = '';
		}

		rapidText += event.key.toLowerCase();
		rapidTextTime = now;

		const rapidItem = Array.from(listbox.children).find(
			(child) =>
				child.dataset.optionValue &&
				child.textContent.trim().toLowerCase().startsWith(rapidText)
		);

		if (rapidItem) {
			showDropdown();
			listbox.focus();
			setActiveDescendant(rapidItem);
			event.preventDefault();
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

function handleWindowResizeOrScroll() {
	if (!document.body.contains(wrapper)) {
		document.removeEventListener('click', handleDocumentClick);

		return;
	}

	if (dropdown.classList.contains('show')) {
		repositionDropdown();
	}
}

function filterLocalOptions(query) {
	const preferedItems = [];
	const restItems = [];

	for (const item of input.attributes.options) {
		if (preferedItems.length + restItems.length === MAX_ITEMS) {
			break;
		}

		const label = item.label.trim().toLowerCase();

		if (label.startsWith(query)) {
			preferedItems.push(item);
		}
		else if (label.includes(query)) {
			restItems.push(item);
		}
	}

	return Promise.resolve(
		[...preferedItems, ...restItems].slice(0, MAX_ITEMS)
	);
}

function fetchRemoteOptions(query, abortController) {
	if (
		!input.attributes.relationshipLabelFieldName ||
		!input.attributes.relationshipURL ||
		!input.attributes.relationshipValueFieldName
	) {
		return Promise.resolve({items: []});
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
	}
	else {
		hideElement(listbox);
		showElement(noResultsMessage);
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

		listbox.appendChild(element);
	});

	setActiveDescendant(listbox.firstElementChild);
}

function handleSearchInput() {
	if (searchInput.value === lastSearchQuery) {
		return;
	}

	lastSearchAbortController.abort();
	lastSearchAbortController = new AbortController();
	lastSearchQuery = searchInput.value;

	if (!lastSearchQuery) {
		setListboxItems(defaultOptions);

		return;
	}

	showElement(loadingResultsMessage);

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
}

button.addEventListener('click', handleButtonClick);
button.addEventListener('keydown', handleKeydown);
listbox.addEventListener('keydown', handleKeydown);
listbox.addEventListener('click', handleListboxClick);
document.addEventListener('click', handleDocumentClick);

searchInput.addEventListener('keydown', handleMovementKeys);
searchInput.addEventListener('input', debounce(handleSearchInput, 500));

if ((input.attributes.options || []).length > MAX_ITEMS) {
	showElement(searchInputWrapper);
}

window.addEventListener('resize', handleWindowResizeOrScroll, {
	passive: true,
});
window.addEventListener('scroll', handleWindowResizeOrScroll, {
	passive: true,
});

if (!getActiveDesdendant()) {
	setActiveDescendant(listbox.firstElementChild);
}

dropdown.style.left = '0';
dropdown.style.top = '0';

repositionDropdown();
