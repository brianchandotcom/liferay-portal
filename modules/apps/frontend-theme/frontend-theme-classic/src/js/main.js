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

/* eslint-disable prefer-arrow-callback */
(function () {
	var signInLink = document.querySelector('.sign-in > a');

	if (signInLink && signInLink.dataset.redirect === 'false') {
		signInLink.addEventListener('click', function (event) {
			event.preventDefault();

			var modalSignInURL = Liferay.Util.addParams(
				'windowState=exclusive',
				signInLink.href
			);

			Liferay.Util.fetch(modalSignInURL)
				.then(function (response) {
					return response.text();
				})
				.then(function (response) {
					if (response) {
						Liferay.Util.openModal({
							bodyHTML: response,
							title: Liferay.Language.get('sign-in'),
						});
					}
					else {
						redirectPage();
					}
				})
				.catch(function () {
					redirectPage();
				});
		});
	}

	function redirectPage() {
		window.location.href = signInLink.href;
	}
})();
