/* eslint-disable no-undef */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const siteURL = Liferay.ThemeDisplay.getLayoutRelativeURL()
	.split('/')
	.slice(0, 3)
	.join('/');

const buttonDealRegistration = fragmentElement.querySelector(
	'#deal-registrations'
);

const buttonPartnerOpportunities = fragmentElement.querySelector(
	'#partner-opportunities'
);

buttonDealRegistration.onclick = () =>
	Liferay.Util.navigate(`${siteURL}/sales/deal-registrations`);

buttonPartnerOpportunities.onclick = () =>
	Liferay.Util.navigate(`${siteURL}/sales/partner-opportunities`);

if (Liferay.currentURL.includes('opportunities')) {
	buttonPartnerOpportunities.classList.toggle('active');
}
else if (Liferay.currentURL.includes('deal')) {
	buttonDealRegistration.classList.toggle('active');
}
