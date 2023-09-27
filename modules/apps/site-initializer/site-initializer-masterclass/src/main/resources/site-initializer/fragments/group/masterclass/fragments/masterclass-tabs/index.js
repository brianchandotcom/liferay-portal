/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const dropdown = fragmentElement.querySelector('.navbar-collapse');
const dropdownButton = fragmentElement.querySelector('.navbar-toggler-link');
const editMode = layoutMode === 'edit';
const tabItems = [].slice.call(
	fragmentElement.querySelectorAll(
		'[data-fragment-namespace="' + fragmentNamespace + '"].nav-link'
	)
);
const tabPanelItems = [].slice.call(
	fragmentElement.querySelectorAll(
		'[data-fragment-namespace="' + fragmentNamespace + '"].tab-panel-item'
	)
);

function activeTab(item) {
	tabItems.forEach((tabItem) => {
		tabItem.setAttribute('aria-selected', false);
		tabItem.classList.remove('active');
	});
	item.setAttribute('aria-selected', true);
	item.classList.add('active');
}

function activeTabPanel(item) {
	tabPanelItems.forEach((tabPanelItem) => {
		if (!tabPanelItem.classList.contains('d-none')) {
			tabPanelItem.classList.add('d-none');
		}
	});
	item.classList.remove('d-none');
}

function handleDropdown(event, item) {
	event.preventDefault();
	dropdown.classList.toggle('show');

	const ariaExpanded = dropdownButton.getAttribute('aria-expanded');
	dropdownButton.setAttribute(
		'aria-expanded',
		ariaExpanded === 'false' ? true : false
	);

	if (item) {
		handleDropdownButtonName(item);
	}
}

function handleDropdownButtonName(item) {
	const tabText =
		item.querySelector('lfr-editable') ||
		item.querySelector('.navbar-text-truncate');

	if (tabText) {
		dropdownButton.querySelector('.navbar-text-truncate').innerHTML =
			tabText.textContent;
	}
}

function openTabPanel(event, i) {
	const currentTarget = event.currentTarget;
	const target = event.target;
	const isEditable =
		target.hasAttribute('data-lfr-editable-id') ||
		target.hasAttribute('contenteditable');
	const dropdownIsOpen = JSON.parse(
		dropdownButton.getAttribute('aria-expanded')
	);

	if (!isEditable || !editMode) {
		if (dropdownIsOpen) {
			handleDropdown(event, currentTarget);
		}

		currentTarget.focus();

		activeTab(currentTarget, i);
		activeTabPanel(tabPanelItems[i]);

		this.tabIndex = i;
	}
}

function main() {
	const initialState = !this.tabIndex || this.tabIndex >= tabItems.length;
	let tabItemSelected = tabItems[0];

	if (initialState) {
		tabItems.forEach((item, i) => {
			if (!i) {
				activeTab(item);
			}
			item.addEventListener('click', (event) => {
				openTabPanel(event, i);
			});
		});
		tabPanelItems.forEach((item, i) => {
			if (!i) {
				activeTabPanel(item);
			}
		});
	}
	else {
		tabItemSelected = tabItems[this.tabIndex];
		tabItems.forEach(function (item, i) {
			activeTab(tabItems[this.tabIndex]);
			item.addEventListener('click', (event) => {
				openTabPanel(event, i);
			});
		});
		tabPanelItems.forEach(function () {
			activeTabPanel(tabPanelItems[this.tabIndex]);
		});
	}

	dropdownButton.addEventListener('click', (event) => {
		handleDropdown(event);
	});
	handleDropdownButtonName(tabItemSelected);
}

main();
