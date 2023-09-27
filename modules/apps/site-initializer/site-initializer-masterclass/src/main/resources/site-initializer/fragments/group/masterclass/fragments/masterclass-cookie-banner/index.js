/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const button = fragmentElement.querySelector('.cookie-banner-button');
const cookieBanner = fragmentElement.querySelector('.cookie-banner');

const editMode = layoutMode === 'edit';

function handleButtonClick() {
	hideBanner();

	localStorage.setItem('liferay.cookie.consent', 'accepted');
}

function hideBanner() {
	cookieBanner.style.display = 'none';
}

function main() {
	if (!editMode) {
		if (localStorage.getItem('liferay.cookie.consent') === 'accepted') {
			hideBanner();
		}
		else {
			button.addEventListener('click', handleButtonClick);
		}
	}
}

main();
