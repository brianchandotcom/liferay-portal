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

const buttonAccept = document.querySelector('.cookies-banner-button-accept');
const buttonConfiguration = document.querySelector(
	'.cookies-banner-button-configuration'
);
const buttonDecline = document.querySelector('.cookies-banner-button-decline');
const cookieBanner = document.querySelector('.cookies-banner');

const editMode = document.body.classList.contains('has-edit-mode-menu');

function hideBanner() {
	cookieBanner.style.display = 'none';
}

export default function ({configurationTitle, configurationUrl}) {
	if (!editMode) {
		if (
			localStorage.getItem('liferay.cookie.consent') === 'accepted' ||
			localStorage.getItem('liferay.cookie.consent') === 'decline'
		) {
			hideBanner();
		}
		else {
			buttonAccept.addEventListener(
				'click',
				function handleButtonClickAccept() {
					hideBanner();

					localStorage.setItem('liferay.cookie.consent', 'accepted2');
				}
			);
			buttonConfiguration.addEventListener(
				'click',
				function handleButtonClickConfiguration() {
					Liferay.Util.openModal({
						title: configurationTitle,
						url: configurationUrl,
					});
				}
			);
			buttonDecline.addEventListener(
				'click',
				function handleButtonClickDecline() {
					hideBanner();

					localStorage.setItem('liferay.cookie.consent', 'decline2');
				}
			);
		}
	}
}
