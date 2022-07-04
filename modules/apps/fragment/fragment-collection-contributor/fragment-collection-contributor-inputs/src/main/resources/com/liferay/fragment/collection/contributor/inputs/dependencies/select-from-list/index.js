const RAPID_TEXT_DELAY = 300;

let rapidText = '';
let rapidTextTime = Date.now();

const wrapper = fragmentElement;

const button = wrapper.querySelector('.form-control');
const buttonLabel = button.querySelector('.forms-select-from-list-label');
const dropdown = wrapper.querySelector('.dropdown-menu');
const inputElement = wrapper.querySelector('input');
const listbox = wrapper.querySelector('.list-unstyled');
const loadingResultsMessage = wrapper.querySelector(
	'.forms-select-from-list-loading-results'
);
const noResultsMessage = wrapper.querySelector(
	'.forms-select-from-list-no-results'
);
const searchInput = wrapper.querySelector('.forms-select-from-list-search');

let currentSearch = {
	abortController: new AbortController(),
	query: '',
};

function debounce(fn, delay) {
	let debounceId = null;

	return function (...args) {
		clearTimeout(debounceId);
		debounceId = setTimeout(() => fn(...args), delay);
	};
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
	repositionDropdown();
	button.setAttribute('aria-expanded', 'true');
	dropdown.classList.add('show');
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

function setActiveDescendant(item) {
	const previousItem = getActiveDesdendant();

	if (previousItem && previousItem !== item) {
		previousItem.classList.remove('active');
		previousItem.removeAttribute('aria-selected');
	}

	if (item) {
		buttonLabel.textContent = item.textContent;
		listbox.setAttribute('aria-activedescendant', item.id);
		inputElement.value = item.dataset.optionValue;

		item.classList.add('active');
		item.setAttribute('aria-selected', 'true');

		item.scrollIntoView({
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

		if (searchInput) {
			searchInput.focus();

			if (searchInput) {
				searchInput.setSelectionRange(0, searchInput.value.length);
			}
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
	if (event.target.dataset?.optionValue) {
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

function filterLocalOptions() {
	const preferedItems = [];
	const restItems = [];

	for (const item of input.attributes.options) {
		if (preferedItems.length + restItems.length === 10) {
			break;
		}

		const label = item.label.trim().toLowerCase();

		if (label.startsWith(currentSearch.query)) {
			preferedItems.push(item);
		}
		else if (label.includes(currentSearch.query)) {
			restItems.push(item);
		}
	}

	return Promise.resolve({
		items: [...preferedItems, ...restItems].slice(0, 10).map((item) => ({
			key: item.value,
			name: item.label,
		})),
	});
}

function fetchRemoteOptions() {
	const url = new URL(input.attributes.optionsURL);
	url.searchParams.set('search', currentSearch.query);

	return Liferay.Util.fetch(url, {
		headers: new Headers({
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		}),
		method: 'GET',
		signal: currentSearch.abortController.signal,
	}).then((response) => response.json());
}

function handleSearchKeyup() {
	if (searchInput.value === currentSearch.query) {
		return;
	}

	currentSearch.abortController.abort();

	currentSearch = {
		abortController: new AbortController(),
		query: searchInput.value,
	};

	const setListboxItems = (items) => {
		listbox.innerHTML = '';

		items.forEach((item) => {
			const element = document.createElement('li');

			element.classList.add('dropdown-item');
			element.dataset.optionValue = item.value;
			element.id = `${fragmentNamespace}-option-${item.value}`;
			element.setAttribute('role', 'option');
			element.textContent = item.label;

			listbox.appendChild(element);
		});

		if (items.length) {
			setActiveDescendant(listbox.firstElementChild);
		}
	};

	if (currentSearch.query) {
		listbox.innerHTML = '';
	}
	else {
		setListboxItems(input.attributes.options.slice(0, 10));

		if (listbox.children.length) {
			listbox.removeAttribute('aria-hidden');
			listbox.classList.remove('d-none');
			noResultsMessage.setAttribute('aria-hidden', 'true');
			noResultsMessage.classList.add('d-none');
		}

		return;
	}

	loadingResultsMessage.classList.remove('d-none');
	loadingResultsMessage.removeAttribute('aria-hidden');

	const fetcher = input.attributes.optionsURL
		? fetchRemoteOptions
		: filterLocalOptions;

	fetcher()
		.then((result) => {
			setListboxItems(
				result.items.map((entry) => ({
					label: entry.name,
					value: entry.key,
				}))
			);

			if (listbox.children.length) {
				listbox.removeAttribute('aria-hidden');
				listbox.classList.remove('d-none');
				noResultsMessage.setAttribute('aria-hidden', 'true');
				noResultsMessage.classList.add('d-none');
			}
			else {
				listbox.setAttribute('aria-hidden', 'true');
				listbox.classList.add('d-none');
				noResultsMessage.removeAttribute('aria-hidden');
				noResultsMessage.classList.remove('d-none');
			}
		})
		.finally(() => {
			loadingResultsMessage.classList.add('d-none');
			loadingResultsMessage.setAttribute('aria-hidden', 'true');
		});
}

if (listbox.children.length) {
	button.addEventListener('click', handleButtonClick);
	button.addEventListener('keydown', handleKeydown);
	listbox.addEventListener('keydown', handleKeydown);
	listbox.addEventListener('click', handleListboxClick);
	document.addEventListener('click', handleDocumentClick);

	if (searchInput) {
		searchInput.addEventListener('keydown', handleMovementKeys);
		searchInput.addEventListener('keyup', debounce(handleSearchKeyup, 500));
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
}
