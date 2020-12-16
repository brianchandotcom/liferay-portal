/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const toggle = fragmentElement.querySelector('.dropdown-fragment-toggle');
const toggleEditable = toggle.querySelector('[data-lfr-editable-id]');
const menu = fragmentElement.querySelector('.dropdown-fragment-menu');
const withinMasterLayout = fragmentElement.parentElement.classList.contains(
	'page-editor__fragment-content--master'
);
const editMode = document.body.classList.contains('has-edit-mode-menu');

let alignMenuInterval;

function menuHasChildren() {
	const contentElement = editMode
		? menu.querySelector('lfr-drop-zone')
		: menu;

	return (
		contentElement &&
		Array.from(contentElement.firstElementChild.children).filter(
			(child) => child.tagName !== 'STYLE'
		).length > 0
	);
}

function alignMenu() {
	const toggleRect = toggle.getBoundingClientRect();

	const parent =
		document.querySelector('.page-editor__layout-viewport__resizer') ||
		document.body;
	const parentRect = parent.getBoundingClientRect();

	menu.style.top = `${toggleRect.bottom}px`;

	if (configuration.panelType === 'mega-menu') {
		menu.style.left = `${parentRect.left}px`;
		menu.style.width = `${parentRect.width}px`;
	}
	else if (configuration.panelType === 'regular') {
		menu.style.width = '240px';
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
