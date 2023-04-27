/* eslint-disable no-undef */
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
	tabItems.forEach((tabItem) => {
		tabItem.setAttribute('aria-selected', false);
		tabItem.classList.remove('active');
		tabItem.parentElement.classList.remove('active');
	});
	item.setAttribute('aria-selected', true);
	item.classList.add('active');
	item.parentElement.classList.add('active');
}

function activeTabPanel(item) {
	tabPanelItems.forEach((tabPanelItem) => {
		if (!tabPanelItem.classList.contains('d-none')) {
			tabPanelItem.classList.add('d-none');
		}
	});
	item.classList.remove('d-none');
}

function openTabPanel(event, i) {
	const currentTarget = event.currentTarget;
	const target = event.target;
	const isEditable =
		target.hasAttribute('data-lfr-editable-id') ||
		target.hasAttribute('contenteditable');

	if (!isEditable || !editMode) {
		currentTarget.focus();

		activeTab(currentTarget, i);
		activeTabPanel(tabPanelItems[i]);

		this.tabIndex = i;
	}
}

function main() {
	const initialState = !this.tabIndex || this.tabIndex >= tabItems.length;

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
	} else {
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
}

main();
