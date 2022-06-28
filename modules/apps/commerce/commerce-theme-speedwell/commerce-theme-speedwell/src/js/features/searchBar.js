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

(function (w) {
	const searchToggles = w.document.querySelectorAll('.js-toggle-search');
	const HAS_SEARCH_CLASS = 'has-search';
	const IS_OPEN_CLASS = 'is-open';
	const IS_ACTIVE_CLASS = 'is-active';
	const SEARCHBAR_SELECTOR = '.speedwell-search';

	let searchBarElement;

	const searchBar = w.Liferay.component('search-bar');

	if (searchBar) {
		searchBarElement = w.document.querySelector(SEARCHBAR_SELECTOR);

		searchBar.on('toggled', (status) => {
			searchToggles.forEach((element) => {
				element.classList.toggle(IS_ACTIVE_CLASS, status);
			});

			w.document
				.getElementById('speedwell')
				.classList.toggle(HAS_SEARCH_CLASS, status);

			if (searchBarElement) {
				searchBarElement.classList.toggle(IS_OPEN_CLASS, status);
			}
		});
	}
})(window);
