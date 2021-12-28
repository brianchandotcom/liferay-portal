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

function checkCookiesConsent(cookieBanner) {
	if (
		localStorage.getItem('liferay.cookie.consent.functional') ===
			'accepted' ||
		localStorage.getItem('liferay.cookie.consent.functional') ===
			'decline' ||
		localStorage.getItem('liferay.cookie.consent.performance') ===
			'accepted' ||
		localStorage.getItem('liferay.cookie.consent.performance') ===
			'decline' ||
		localStorage.getItem('liferay.cookie.consent.personalization') ===
			'accepted' ||
		localStorage.getItem('liferay.cookie.consent.personalization') ===
			'decline'
	) {
		cookieBanner.style.display = 'none';
	}
	else {
		cookieBanner.style.display = 'block';
	}
}

function cookiesAcceptAll() {
	localStorage.setItem('liferay.cookie.consent.functional', 'accepted');
	localStorage.setItem('liferay.cookie.consent.performance', 'accepted');
	localStorage.setItem('liferay.cookie.consent.personalization', 'accepted');
}

function cookiesDeclineAll() {
	localStorage.setItem('liferay.cookie.consent.functional', 'decline');
	localStorage.setItem('liferay.cookie.consent.performance', 'decline');
	localStorage.setItem('liferay.cookie.consent.personalization', 'decline');
}

export default function ({configurationTitle, configurationUrl}) {
	const buttonAccept = document.querySelector(
		'.cookies-banner-button-accept'
	);
	const buttonConfiguration = document.querySelector(
		'.cookies-banner-button-configuration'
	);
	const buttonDecline = document.querySelector(
		'.cookies-banner-button-decline'
	);
	const cookieBanner = document.querySelector('.cookies-banner');
	const editMode = document.body.classList.contains('has-edit-mode-menu');

	if (!editMode) {
		checkCookiesConsent(cookieBanner);

		buttonAccept.addEventListener(
			'click',
			function handleButtonClickAccept() {
				cookieBanner.style.display = 'none';

				cookiesAcceptAll();
			}
		);

		buttonConfiguration.addEventListener(
			'click',
			function handleButtonClickConfiguration() {
				Liferay.Util.openModal({
					buttons: [
						{
							label: 'Decline all',
							onClick() {
								cookiesDeclineAll();

								checkCookiesConsent(cookieBanner);

								Liferay.Util.getOpener().Liferay.fire(
									'closeModal',
									{
										id: 'cookiesBannerConfiguration',
									}
								);
							},
						},
						{
							displayType: 'secondary',
							label: 'Confirm',
							onClick() {
								checkCookiesConsent(cookieBanner);

								Liferay.Util.getOpener().Liferay.fire(
									'closeModal',
									{
										id: 'cookiesBannerConfiguration',
									}
								);
							},
						},
						{
							displayType: 'secondary',
							label: 'Accept all',
							onClick() {
								cookiesAcceptAll();

								checkCookiesConsent(cookieBanner);

								Liferay.Util.getOpener().Liferay.fire(
									'closeModal',
									{
										id: 'cookiesBannerConfiguration',
									}
								);
							},
						},
					],
					displayType: 'primary',
					height: '70vh',
					id: 'cookiesBannerConfiguration',
					size: 'lg',
					title: configurationTitle,
					url: configurationUrl,
				});
			}
		);

		buttonDecline.addEventListener(
			'click',
			function handleButtonClickDecline() {
				cookieBanner.style.display = 'none';

				cookiesDeclineAll();
			}
		);
	}
}
