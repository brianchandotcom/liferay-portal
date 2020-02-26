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

import toCharCode from '../util/to_char_code.es';

const EVENT_SHOW_TAB = 'showTab';

/**
 * Prepares and fires the an event that will show a tab
 * @param {String} namespace The portlet's namespace
 * @param {Array} names Names of all tabs
 * @param {String} id Tab id.
 * @param {Function} callback The callback function.
 */
export function showTab(namespace, names, id, callback) {
	const namespacedId = namespace + toCharCode(id);

	const selectedTab = document.getElementById(namespacedId + 'TabsId');
	const selectedTabSection = document.getElementById(
		namespacedId + 'TabsSection'
	);

	if (selectedTab && selectedTabSection) {
		const details = {
			id,
			names,
			namespace,
			selectedTab,
			selectedTabSection,
		};

		if (callback && typeof callback === 'function') {
			callback.call(this, namespace, names, id, details);
		}

		try {
			Liferay.on(EVENT_SHOW_TAB, applyTabSelectionDOMChanges);

			Liferay.fire(EVENT_SHOW_TAB, details);
		}
		finally {
			Liferay.detach(EVENT_SHOW_TAB, applyTabSelectionDOMChanges);
		}
	}
}

/**
 * Applies DOM changes that represent tab selection
 * @param {String} namespace The portlet's namespace
 * @param {Array} names Names of all tabs
 * @param {String} id Tab id.
 * @param {HTMLElement} selectedTab Selected tab element
 * @param {HTMLElement} selectedTabSection Selected tab section element
 */
export function applyTabSelectionDOMChanges({
	id,
	names,
	namespace,
	selectedTab,
	selectedTabSection,
}) {
	const selectedTabLink = selectedTab.querySelector('a');

	if (selectedTab && selectedTabLink) {
		const activeTab = selectedTab.parentElement.querySelector('.active');

		if (activeTab) {
			activeTab.classList.remove('active');
		}

		selectedTabLink.classList.add('active');
	}

	if (selectedTabSection) {
		selectedTabSection.classList.remove('hide');
	}

	const tabTitle = document.getElementById(namespace + 'dropdownTitle');

	if (tabTitle && selectedTabLink) {
		tabTitle.innerHTML = selectedTabLink.textContent;
	}

	names.splice(names.indexOf(id), 1);

	let tabSection;

	for (var i = 0; i < names.length; i++) {
		tabSection = document.getElementById(
			namespace + toCharCode(names[i]) + 'TabsSection'
		);

		if (tabSection) {
			tabSection.classList.add('hide');
		}
	}
}
