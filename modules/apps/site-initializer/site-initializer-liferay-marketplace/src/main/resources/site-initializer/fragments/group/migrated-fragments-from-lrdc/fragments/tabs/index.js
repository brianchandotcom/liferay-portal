/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	}
	else {
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
