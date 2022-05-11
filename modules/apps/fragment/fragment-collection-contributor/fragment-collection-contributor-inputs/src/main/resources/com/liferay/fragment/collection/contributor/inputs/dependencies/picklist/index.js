const RAPID_TEXT_DELAY = 300;

let rapidText = '';
let rapidTextTime = Date.now();

const editMode = document.body.classList.contains('has-edit-mode-menu');
const wrapper = fragmentElement;

const button = wrapper.querySelector('.form-control');
const dropdown = wrapper.querySelector('.dropdown-menu');
const input = wrapper.querySelector('input');
const listbox = wrapper.querySelector('.list-unstyled');

function repositionDropdown() {
	if (document.body.contains(wrapper)) {
		if (wrapper.contains(dropdown)) {
			document.body.appendChild(dropdown);
		}
	}
	else if (document.body.contains(dropdown)) {
		document.body.removeChild(dropdown);
	}

	const buttonRect = button.getBoundingClientRect();

	dropdown.style.transform = `
		translateX(${buttonRect.left}px)
		translateY(${buttonRect.bottom}px)
	`;
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

	button.textContent = item.textContent;
	listbox.setAttribute('aria-activedescendant', item.id);
	input.value = item.dataset.optionValue;

	item.classList.add('active');
	item.setAttribute('aria-selected', 'true');

	item.scrollIntoView({
		block: 'nearest',
	});
}

function handleButtonClick() {
	if (button.hasAttribute('aria-expanded')) {
		hideDropdown();
	}
	else {
		showDropdown();
	}
}

function handleButtonKeydown(event) {
	const currentActiveDescendant = getActiveDesdendant();

	if (event.key === 'ArrowDown') {
		showDropdown();

		setActiveDescendant(
			currentActiveDescendant.nextElementSibling ||
				currentActiveDescendant ||
				listbox.firstElementChild
		);

		event.preventDefault();
	}
	else if (event.key === 'ArrowUp') {
		showDropdown();

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
		setActiveDescendant(listbox.firstElementChild);
		event.preventDefault();
	}
	else if (event.key === 'End') {
		setActiveDescendant(listbox.lastElementChild);
		event.preventDefault();
	}
	else if (event.key.length === 1) {
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
			setActiveDescendant(rapidItem);
			event.preventDefault();
		}
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

	if (event.target !== wrapper && !wrapper.contains(event.target)) {
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

if (!editMode && listbox.children.length) {
	button.addEventListener('click', handleButtonClick);
	button.addEventListener('keydown', handleButtonKeydown);
	listbox.addEventListener('click', handleListboxClick);
	document.addEventListener('click', handleDocumentClick);

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
	dropdown.style.position = 'absolute';
	dropdown.style.top = '0';

	repositionDropdown();
}
