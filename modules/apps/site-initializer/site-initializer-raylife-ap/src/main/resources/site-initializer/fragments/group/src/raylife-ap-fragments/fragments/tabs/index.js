/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const quantityOfTabs =
	configuration.quantityOfTabs !== 0 ? configuration.quantityOfTabs : 1;
const activeTab = configuration.activeTab;

const removeActiveClasses = (activeTab, activeDropZoneElement) => {
	const allTabsElement = fragmentElement.querySelectorAll(
		'.tab-container .nav-item .nav-link'
	);
	const allDropZoneElement = fragmentElement.querySelectorAll(
		'.tab-container .tab-panel-item'
	);

	allTabsElement.forEach((tabElement) => {
		if (
			tabElement !== activeTab &&
			tabElement.classList.contains('active-tab')
		) {
			tabElement.classList.remove('active-tab');
		}
	});

	allDropZoneElement.forEach((dropZoneElement) => {
		if (
			dropZoneElement !== activeDropZoneElement &&
			!dropZoneElement.classList.contains('d-none')
		) {
			dropZoneElement.classList.add('d-none');
		}
	});
};

const setTabCounter = (tabNumber) => {
	const tabCounterElement = fragmentElement.querySelector(
		`.tab-container .nav-item .tab-counter-${tabNumber}`
	);
	tabCounterElement.textContent = 3;
};

for (let i = 0; i < quantityOfTabs; i++) {
	const tabNumber = i + 1;
	const dropZoneElement = fragmentElement.querySelector(
		`.tab-container .tab-panel-item-${tabNumber}`
	);
	const divElement = fragmentElement.querySelector(
		`.tab-container .nav-item .nav-link-${tabNumber}`
	);

	if (activeTab === tabNumber) {
		divElement.classList.add('active-tab');
		dropZoneElement.classList.remove('d-none');
	}

	if (configuration.showTabCount) {
		setTabCounter(tabNumber);
	}

	divElement.onclick = () => {
		divElement.classList.add('active-tab');
		dropZoneElement.classList.remove('d-none');

		removeActiveClasses(divElement, dropZoneElement);
	};
}
