/* eslint-disable no-undef */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const dropdown = fragmentElement.querySelector('.navbar-collapse');
const dropdownButton = fragmentElement.querySelector('.navbar-toggler-link');
const editMode = layoutMode === 'edit';
const persistedTabKey = 'tabsFragment_' + fragmentNamespace + '_persistedTabId';

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

const persistedTab = (function () {
	if (!configuration.persistSelectedTab) {
		let persistedId;

		return {
			getId() {
				return persistedId;
			},

			setId(nextId) {
				persistedId = nextId;
			},
		};
	}

	return {
		getId() {
			return Number(
				Liferay.Util.SessionStorage.getItem(
					persistedTabKey,
					Liferay.Util.SessionStorage.TYPES.PERSONALIZATION
				)
			);
		},

		setId(id) {
			Liferay.Util.SessionStorage.setItem(
				persistedTabKey,
				id,
				Liferay.Util.SessionStorage.TYPES.PERSONALIZATION
			);
		},
	};
})();

function activeTab(item) {
	const getAriaLabel = () => {
		const label = dropdownButton.getAttribute('aria-label');
		const [currentSelectionText] = label.split(':');

		return `${currentSelectionText}: ${item.textContent.trim()}`;
	};

	tabItems.forEach((tabItem) => {
		tabItem.setAttribute('aria-selected', false);
		tabItem.classList.remove('active');
	});

	dropdownButton.setAttribute('aria-label', getAriaLabel());
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

function main() {
	const tabItemId = tabItems[persistedTab.getId()] ? persistedTab.getId() : 0;

	tabItems.forEach((item, index) => {
		item.addEventListener('click', (event) => {
			openTabPanel(event, index);

			dropdownButton.focus();
		});
	});

	dropdownButton.addEventListener('click', (event) => {
		handleDropdown(event);
	});

	activeTab(tabItems[tabItemId]);
	activeTabPanel(tabPanelItems[tabItemId]);
	handleDropdownButtonName(tabItems[tabItemId]);
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
		else {
			handleDropdownButtonName(currentTarget);
		}

		currentTarget.focus();

		activeTab(currentTarget, i);
		activeTabPanel(tabPanelItems[i]);
		persistedTab.setId(i);

		Liferay.fire('tabsFragment:activePanel', {panel: tabPanelItems[i]});
	}
}

main();
