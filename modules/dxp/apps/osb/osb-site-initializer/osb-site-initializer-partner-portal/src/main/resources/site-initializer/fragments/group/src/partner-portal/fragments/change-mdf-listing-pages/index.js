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

const buttonMDFRequest = fragmentElement.querySelector('#mdf-request');
const buttonMDFClaim = fragmentElement.querySelector('#mdf-claim');

const queryParams = new URLSearchParams(window.location.search);
const newSuccess = queryParams.get('new-success');
const editSuccess = queryParams.get('edit-success');

if (newSuccess) {
	Liferay.Util.openToast({
		message: 'Your MDF Request was successfully submited.',
		type: 'success',
	});
}

if (editSuccess) {
	Liferay.Util.openToast({
		message: 'Your MDF Request was successfully edited.',
		type: 'success',
	});
}

if (buttonMDFRequest) {
	buttonMDFRequest.onclick = () =>
		Liferay.Util.navigate(`${siteURL}/marketing/mdf-requests`);
	if (Liferay.currentURL.includes('request')) {
		buttonMDFRequest.classList.toggle('active');
	}
}

if (buttonMDFClaim) {
	buttonMDFClaim.onclick = () =>
		Liferay.Util.navigate(`${siteURL}/marketing/mdf-claim`);
	if (Liferay.currentURL.includes('claim')) {
		buttonMDFClaim.classList.toggle('active');
	}
}
