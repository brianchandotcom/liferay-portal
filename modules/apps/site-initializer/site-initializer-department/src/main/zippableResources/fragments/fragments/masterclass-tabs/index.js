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

const dropdown = fragmentElement.querySelector('.navbar-collapse');
const dropdownButton = fragmentElement.querySelector('.navbar-toggler-link');
const editMode = document.body.classList.contains('has-edit-mode-menu');
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
	tabItems.forEach(function (tabItem) {
		tabItem.setAttribute('aria-selected', false);
		tabItem.classList.remove('active');
	});
	item.setAttribute('aria-selected', true);
	item.classList.add('active');
}

function activeTabPanel(item) {
	tabPanelItems.forEach(function (tabPanelItem) {
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
		tabItems.forEach(function (item, i) {
			if (!i) {
				activeTab(item);
			}
			item.addEventListener('click', function (event) {
				openTabPanel(event, i);
			});
		});
		tabPanelItems.forEach(function (item, i) {
			if (!i) {
				activeTabPanel(item);
			}
		});
	}
	else {
		tabItemSelected = tabItems[this.tabIndex];
		tabItems.forEach(function (item, i) {
			activeTab(tabItems[this.tabIndex]);
			item.addEventListener('click', function (event) {
				openTabPanel(event, i);
			});
		});
		tabPanelItems.forEach(function () {
			activeTabPanel(tabPanelItems[this.tabIndex]);
		});
	}

	dropdownButton.addEventListener('click', function (event) {
		handleDropdown(event);
	});
	handleDropdownButtonName(tabItemSelected);
}

main();
