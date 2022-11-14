const buttonElement = fragmentElement.querySelector('.btn');
const optionListElement = fragmentElement.querySelector('.dropdown-menu');

const labelInputElement = document.getElementById(
	// eslint-disable-next-line no-undef
	`${fragmentEntryLinkNamespace}-label-input`
);
const loadingResultsElement = document.getElementById(
	// eslint-disable-next-line no-undef
	`${fragmentEntryLinkNamespace}-loading-results-message`
);
const noResultsElement = document.getElementById(
	// eslint-disable-next-line no-undef
	`${fragmentEntryLinkNamespace}-no-results-message`
);
const uiInputElement = document.getElementById(
	// eslint-disable-next-line no-undef
	`${fragmentEntryLinkNamespace}-ui-input`
);
const valueInputElement = document.getElementById(
	// eslint-disable-next-line no-undef
	`${fragmentEntryLinkNamespace}-value-input`
);

buttonElement.addEventListener('click', toggleResultList);
buttonElement.addEventListener('blur', handleResultListBlur);
uiInputElement.addEventListener('click', toggleResultList);
uiInputElement.addEventListener('input', handleInputChange);
uiInputElement.addEventListener('blur', handleInputBlur);
uiInputElement.addEventListener('keydown', handleInputKeyDown);
optionListElement.addEventListener('click', handleResultListClick);

window.addEventListener('resize', handleWindowResizeOrScroll, {
	passive: true,
});
window.addEventListener('scroll', handleWindowResizeOrScroll, {
	passive: true,
});

const MAX_ITEMS = 10;

let lastSearchAbortController = new AbortController();
let lastSearchQuery = null;

const KEYS = {
	ArrowDown: 'ArrowDown',
	ArrowUp: 'ArrowUp',
	End: 'End',
	Enter: 'Enter',
	Home: 'Home',
};

const optionList = (input.attributes.options || []).map((option) => ({
	textContent: option.label,
	textValue: option.label.toLowerCase(),
	value: option.value,
}));

function handleResultListClick(event) {
	let selectedOption = null;

	if (event.target.matches('.dropdown-item')) {
		selectedOption = event.target;
	}
	else if (event.target.closest('.dropdown-item')) {
		selectedOption = event.target.closest('.dropdown-item');
	}

	if (selectedOption) {
		setSelectedOption(selectedOption);
	}
}

function handleInputBlur() {
	uiInputElement.value = labelInputElement.value;

	if (checkIsOpenResultList()) {
		setTimeout(() => closeResultList(), 500);
	}
}

function handleResultListBlur() {
	if (checkIsOpenResultList()) {
		setTimeout(() => closeResultList(), 500);
	}
}

function handleInputKeyDown(event) {
	if (!optionListElement.firstElementChild) {
		return;
	}

	const currentFocusedOption = document.getElementById(
		optionListElement.getAttribute('aria-activedescendant')
	);

	if (KEYS[event.key]) {
		openResultList();
		event.preventDefault();
	}

	if (event.key === KEYS.ArrowDown && !event.altKey) {
		if (currentFocusedOption) {
			setFocusedOption(
				currentFocusedOption.nextElementSibling ||
					optionListElement.firstElementChild
			);
		}
		else {
			setFocusedOption(optionListElement.firstElementChild);
		}
	}
	else if (event.key === KEYS.ArrowUp) {
		if (currentFocusedOption) {
			setFocusedOption(
				currentFocusedOption.previousElementSibling ||
					optionListElement.lastElementChild
			);
		}
		else {
			setFocusedOption(optionListElement.lastElementChild);
		}
	}
	else if (event.key === KEYS.Home) {
		setFocusedOption(optionListElement.firstElementChild);
	}
	else if (event.key === KEYS.End) {
		setFocusedOption(optionListElement.lastElementChild);
	}
	else if (event.key === KEYS.Enter && currentFocusedOption) {
		setSelectedOption(currentFocusedOption);
	}
}

function handleInputChange() {
	const filterValue = uiInputElement.value.toLowerCase();

	if (filterValue !== lastSearchQuery) {
		openResultList();

		lastSearchQuery = filterValue;

		loadingResultsElement.classList.remove('d-none');

		filterOptions(filterValue).then((filteredOptions) => {
			loadingResultsElement.classList.add('d-none');
			renderOptionList(filteredOptions);

			if (optionListElement.firstElementChild) {
				noResultsElement.classList.add('d-none');
				setFocusedOption(optionListElement.firstElementChild);
			}
			else {
				noResultsElement.classList.remove('d-none');
			}
		});
	}
}

function filterOptions(query) {
	return new Promise((resolve) => {
		if (input.attributes.relationshipURL) {
			lastSearchAbortController.abort();
			lastSearchAbortController = new AbortController();
			filterRemoteOptions(query, lastSearchAbortController).then(resolve);
		}
		else if (query) {
			resolve(filterLocalOptions(query));
		}
		else {
			resolve(optionList);
		}
	});
}

function filterLocalOptions(query) {
	const options = [];

	optionList.forEach((option) => {
		if (!option.value) {
			return;
		}

		if (option.textValue.startsWith(query)) {
			options.push(option);
		}
	});

	optionList.forEach((option) => {
		if (!option.value) {
			return;
		}

		if (option.textValue.includes(query) && !options.includes(option)) {
			options.push(option);
		}
	});

	return options;
}

function filterRemoteOptions(query, abortController) {
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
				textContent: entry[input.attributes.relationshipLabelFieldName],
				textValue: entry[input.attributes.relationshipLabelFieldName],
				value: entry[input.attributes.relationshipValueFieldName],
			}));
		});
}

function handleWindowResizeOrScroll() {
	if (!document.body.contains(fragmentElement)) {
		window.removeEventListener('resize', handleWindowResizeOrScroll);
		window.removeEventListener('scroll', handleWindowResizeOrScroll);

		if (document.body.contains(optionListElement)) {
			optionListElement.parentElement.removeChild(optionListElement);
		}

		return;
	}

	if (checkIsOpenResultList()) {
		repositionResultListElement();
	}
}

function setFocusedOption(optionElement) {
	const currentFocusedOption = document.getElementById(
		optionListElement.getAttribute('aria-activedescendant')
	);

	if (currentFocusedOption) {
		currentFocusedOption.removeAttribute('aria-selected');
	}

	if (optionElement) {
		optionListElement.setAttribute(
			'aria-activedescendant',
			optionElement.id
		);

		optionElement.setAttribute('aria-selected', 'true');
		optionElement.scrollIntoView({block: 'center'});
	}
}

function createOptionElement(option) {
	const optionElement = document.createElement('li');

	optionElement.dataset.optionValue = option.value;
	// eslint-disable-next-line no-undef
	optionElement.id = `${fragmentEntryLinkNamespace}-option-${option.value}`;
	optionElement.textContent = option.textContent;

	optionElement.classList.add('dropdown-item');
	optionElement.setAttribute('role', 'option');

	if (valueInputElement.value === option.value) {
		optionElement.classList.add('active');
	}

	return optionElement;
}

function setSelectedOption(optionElement) {
	const selectedOption = document.getElementById(
		// eslint-disable-next-line no-undef
		`${fragmentEntryLinkNamespace}-option-${valueInputElement.value}`
	);

	if (selectedOption) {
		selectedOption.classList.remove('active');
	}

	if (optionElement.dataset.optionValue) {
		optionElement.classList.add('active');
	}

	labelInputElement.value = optionElement.dataset.optionValue
		? optionElement.textContent
		: '';

	uiInputElement.value = optionElement.dataset.optionValue
		? optionElement.textContent
		: '';

	valueInputElement.value = optionElement.dataset.optionValue;

	closeResultList();
}

function checkIsOpenResultList() {
	return (
		uiInputElement.getAttribute('aria-expanded') === 'true' &&
		buttonElement.getAttribute('aria-expanded') === 'true'
	);
}

function openResultList() {
	const canFetchOptions = input.attributes.relationshipURL;

	if (!canFetchOptions && !optionList.length) {
		return;
	}

	optionListElement.style.display = 'block';

	uiInputElement.setAttribute('aria-expanded', 'true');
	buttonElement.setAttribute('aria-expanded', 'true');

	requestAnimationFrame(() => {
		handleInputChange();
		repositionResultListElement();
	});
}

function closeResultList() {
	setFocusedOption(null);

	optionListElement.style.display = 'none';

	uiInputElement.setAttribute('aria-expanded', 'false');
	buttonElement.setAttribute('aria-expanded', 'false');
}

function toggleResultList() {
	if (checkIsOpenResultList()) {
		closeResultList();
	}
	else {
		openResultList();
	}
}

function repositionResultListElement() {
	const uiInputRect = uiInputElement.getBoundingClientRect();

	if (document.body.contains(fragmentElement)) {
		if (fragmentElement.contains(optionListElement)) {
			document.body.appendChild(optionListElement);
		}
	}
	else if (document.body.contains(optionListElement)) {
		optionListElement.parentNode.removeChild(optionListElement);
	}

	optionListElement.style.transform = `
		translateX(${uiInputRect.left + window.scrollX}px)
		translateY(${uiInputRect.bottom + window.scrollY}px)
	`;
}

function renderOptionList(options) {
	optionListElement.innerHTML = '';

	options
		.slice(0, MAX_ITEMS)
		.forEach((option) =>
			optionListElement.appendChild(createOptionElement(option))
		);
}
