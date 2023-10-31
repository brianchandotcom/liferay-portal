/* eslint-disable no-undef */
/* eslint-disable no-unused-expressions */
/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

Liferay.on('copy-link', () => {
	const url = window.location.href;
	navigator.clipboard.writeText(url);
	Liferay.Util.openToast({
		message: 'Copied link to the clipboard',
		type: 'success',
	});
});

Liferay.on('contact-publisher', () => {
	const emailAddress = fragmentElement.querySelector(
		'.banner__contact-button'
	).value;
	const mailtoLink = `mailto: ${emailAddress}`;

	window.location.href = mailtoLink;
});

Liferay.on('start-trial', () => {
	const finalURL = `purchase-product-form?productId=${configuration.productId}`;
	window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/${finalURL}`;
});

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.includes('web')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};
