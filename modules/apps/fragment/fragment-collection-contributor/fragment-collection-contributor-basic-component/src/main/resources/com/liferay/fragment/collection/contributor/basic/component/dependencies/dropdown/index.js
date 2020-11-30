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
const editMode = document.body.classList.contains('has-edit-mode-menu');

let alignMenuInterval;

function alignMenu() {
	const toggleRect = toggle.getBoundingClientRect();

	const parent =
		document.querySelector('.page-editor__layout-viewport__resizer') ||
		document.body;
	const parentRect = parent.getBoundingClientRect();

	menu.style.position = 'fixed';
	menu.style.left = `${parentRect.left}px`;
	menu.style.top = `${toggleRect.bottom}px`;
	menu.style.width = `${parent.getBoundingClientRect().width}px`;
}

function toggleMenu() {
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

		if (configuration.panelType === 'mega-menu') {
			alignMenu();

			window.addEventListener('resize', alignMenu);
			window.addEventListener('scroll', alignMenu);

			// In edit mode, we align the dropdown menu every second when it has
			// type mega menu and it's kept open to avoid aligning problems when
			// opening product menu and sidebar

			if (editMode && configuration.keepOpen) {
				alignMenuInterval = setInterval(alignMenu, 1000);
			}
		}
	}
}

function isShown() {
	return toggle.getAttribute('aria-expanded') === 'true';
}

function handleToggleClick(event) {
	if (!toggleEditable.contains(event.target)) {
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
	if (configuration.keepOpen && editMode) {
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
