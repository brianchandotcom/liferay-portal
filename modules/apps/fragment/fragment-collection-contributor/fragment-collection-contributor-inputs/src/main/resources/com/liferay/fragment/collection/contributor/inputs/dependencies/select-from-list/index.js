const buttonElement = fragmentElement.querySelector('.btn');
const optionListElement = fragmentElement.querySelector('.dropdown-menu');

const labelInputElement = document.getElementById(
	// eslint-disable-next-line no-undef
	`${fragmentEntryLinkNamespace}-label-input`
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
uiInputElement.addEventListener('blur', handleResultListBlur);
uiInputElement.addEventListener('keydown', handleInputKeyDown);
optionListElement.addEventListener('click', handleOptionListClick);

const KEYS = {
	ArrowDown: 'ArrowDown',
	ArrowUp: 'ArrowUp',
	End: 'End',
	Enter: 'Enter',
	Home: 'Home',
};

const optionList = Array.from(optionListElement.getElementsByTagName('LI')).map(
	(option) => ({
		id: option.id,
		textContent: option.textContent,
		textValue: option.textContent.toLowerCase(),
	})
);

function handleOptionListClick(event) {
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

	if (event.key === KEYS.ArrowDown && event.altKey) {
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
	openResultList();

	const filterValue = uiInputElement.value.toLowerCase();

	optionListElement.innerHTML = '';

	if (filterValue) {
		optionList.forEach((option) => {
			if (option.textValue.startsWith(filterValue)) {
				optionListElement.appendChild(createOptionElement(option));
			}
		});

		optionList.forEach((option) => {
			if (option.textValue.includes(filterValue)) {
				if (!document.getElementById(option.id)) {
					optionListElement.appendChild(createOptionElement(option));
				}
			}
		});
	}
	else {
		optionList.forEach((option) => {
			optionListElement.appendChild(createOptionElement(option));
		});
	}

	setFocusedOption(optionListElement.firstElementChild);
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

	optionElement.id = option.id;
	optionElement.textContent = option.textContent;

	optionElement.classList.add('dropdown-item');
	optionElement.setAttribute('role', 'option');

	return optionElement;
}

function setSelectedOption(option) {
	const selectedOption = document.getElementById(valueInputElement.id);

	if (selectedOption) {
		selectedOption.classList.remove('active');
	}

	option.classList.add('active');

	uiInputElement.value = option.textContent;

	labelInputElement.value = option.textContent;
	valueInputElement.value = option.id;

	closeResultList();
}

function checkIsOpenResultList() {
	return (
		uiInputElement.getAttribute('aria-expanded') === 'true' &&
		buttonElement.getAttribute('aria-expanded') === 'true'
	);
}

function openResultList() {
	optionListElement.style.display = 'block';

	uiInputElement.setAttribute('aria-expanded', 'true');
	buttonElement.setAttribute('aria-expanded', 'true');
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
