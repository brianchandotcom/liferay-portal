/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const toggle = fragmentElement.querySelector('.dropdown-fragment-toggle');
const toggleEditable = toggle.querySelector('[data-lfr-editable-id]');
const menu = fragmentElement.querySelector('.dropdown-fragment-menu');
const withinMasterLayout = fragmentElement.parentElement.classList.contains(
	'page-editor__fragment-content--master'
);
const editMode = layoutMode === 'edit';
const regularMenuWidth = 240;

let alignMenuInterval;

function menuHasChildren() {
	const contentElement = editMode
		? menu.querySelector('lfr-drop-zone')
		: menu;

	return (
		contentElement &&
		!!Array.from(contentElement.firstElementChild.children).filter(
			(child) => child.tagName !== 'STYLE'
		).length
	);
}

function alignMenu() {
	const toggleRect = toggle.getBoundingClientRect();

	const parent =
		document.querySelector('.page-editor__layout-viewport__resizer') ||
		document.body;
	const parentRect = parent.getBoundingClientRect();

	const wrapperRect = document
		.querySelector('#wrapper')
		?.getBoundingClientRect();
	const isRTL =
		Liferay.Language.direction?.[themeDisplay?.getLanguageId()] === 'rtl';

	menu.style.top = `${toggleRect.bottom}px`;

	if (configuration.panelType === 'mega-menu') {
		menu.style.left = `${parentRect.left}px`;
		menu.style.width = `${parentRect.width}px`;
	}
	else if (configuration.panelType === 'regular') {
		menu.style.width = `${regularMenuWidth}px`;

		// If the menu overflows to the right it should be align to the right of the toggle button

		if (
			toggleRect.left + regularMenuWidth >= window.innerWidth ||
			(wrapperRect &&
				toggleRect.left + regularMenuWidth >= wrapperRect.width)
		) {
			menu.style.right = `${window.innerWidth - toggleRect.right}px`;
		}
		else {
			menu.style.right = null;
		}

		// If rtl check the overflow to the left

		if (isRTL && toggleRect.right - regularMenuWidth < 0) {
			menu.style.left = `${toggleRect.left}px`;
		}
	}
	else if (configuration.panelType === 'full-width') {
		menu.style.width = `${fragmentElement.getBoundingClientRect().width}px`;
	}
}

function toggleMenu() {
	if (!menuHasChildren()) {
		return;
	}

	if (isShown()) {
		menu.style.display = 'none';

		toggle.setAttribute('aria-expanded', 'false');

		window.removeEventListener('resize', handleWindowEvent);
		window.removeEventListener('scroll', handleWindowEvent);

		clearInterval(alignMenuInterval);
	}
	else {
		menu.style.display = 'block';

		toggle.setAttribute('aria-expanded', 'true');

		alignMenu();

		window.addEventListener('resize', alignMenu);
		window.addEventListener('scroll', alignMenu);

		// In edit mode, we align the dropdown menu every second when it's kept
		// open to avoid aligning problems when opening product menu or sidebar

		if (editMode && configuration.keepOpen) {
			alignMenuInterval = setInterval(alignMenu, 1000);
		}
	}
}

function isShown() {
	return toggle.getAttribute('aria-expanded') === 'true';
}

function handleToggleClick(event) {
	if (!toggleEditable.contains(event.target) || !editMode) {
		toggleMenu();
	}
}

function handleBodyClick(event) {
	if (!toggle.isConnected) {
		document.body.removeEventListener('click', handleBodyClick);

		return;
	}

	if (
		isShown() &&
		!toggle.contains(event.target) &&
		!menu.contains(event.target)
	) {
		toggleMenu();
	}
}

function handleDropdownHover() {
	if (!isShown()) {
		toggleMenu();
	}
}

function handleDropdownLeave() {
	if (isShown()) {
		toggleMenu();
	}
}

function handleWindowEvent() {
	if (!toggle.isConnected) {
		window.removeEventListener('resize', handleWindowEvent);
		window.removeEventListener('scroll', handleWindowEvent);

		return;
	}

	alignMenu();
}

function main() {
	if (configuration.keepOpen && editMode && !withinMasterLayout) {
		toggleMenu();
	}
	else if (configuration.displayOnHover) {
		toggle.addEventListener('mouseenter', handleDropdownHover);
		toggle.addEventListener('mouseleave', handleDropdownLeave);

		menu.addEventListener('mouseenter', handleDropdownHover);
		menu.addEventListener('mouseleave', handleDropdownLeave);
	}
	else {
		toggle.addEventListener('click', handleToggleClick);
		document.body.addEventListener('click', handleBodyClick);
	}
}

main();
