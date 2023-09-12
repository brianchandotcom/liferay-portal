/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const searchBarElement = document.querySelector('.speedwell-search');

Liferay.on('search-bar-toggled', ({active}) => {
	document.querySelectorAll('.js-toggle-search').forEach((element) => {
		element.classList.toggle('is-active', active);
	});

	document.getElementById('speedwell').classList.toggle('has-search', active);

	if (searchBarElement) {
		searchBarElement.classList.toggle('is-open', active);
	}
});
