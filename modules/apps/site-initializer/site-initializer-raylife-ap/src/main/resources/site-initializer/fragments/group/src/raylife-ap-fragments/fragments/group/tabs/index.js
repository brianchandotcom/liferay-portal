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
