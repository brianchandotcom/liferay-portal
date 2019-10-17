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

(function() {
	AUI().ready('liferay-sign-in-modal', A => {
		var signIn = A.one('.sign-in > a');

		if (signIn && signIn.getData('redirect') !== 'true') {
			signIn.plug(Liferay.SignInModal);
		}
	});

	var porygonSearch = document.querySelector('.porygon-search');
	var porygonSearchButton = document.querySelector('.porygon-search-button');
	var porygonSearchInput = document.querySelector(
		'.porygon-search .search-portlet-keywords-input'
	);

	if (porygonSearch && porygonSearchButton && porygonSearchInput) {
		porygonSearchButton.addEventListener('click', _event => {
			porygonSearch.classList.toggle('active');
			porygonSearchInput.focus();
		});

		porygonSearch.addEventListener('keydown', event => {
			if (event.keyCode === 27) {
				porygonSearch.classList.remove('active');
			}
		});
	}
})();
