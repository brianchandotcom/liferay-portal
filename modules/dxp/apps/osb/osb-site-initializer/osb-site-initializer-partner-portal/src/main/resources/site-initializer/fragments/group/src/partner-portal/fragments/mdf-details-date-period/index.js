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

const currentPath = Liferay.currentURL.split('/');
const mdfRequestId = +currentPath[currentPath.length - 1];
const BASE_URL = window.location.origin;

const getMDFRequest = async () => {
	fetch(`${BASE_URL}/o/c/mdfrequests/${mdfRequestId}`, {
		headers: {
			'accept': 'application/json',
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'GET',
	}).then((response) => {
		if (response.ok) {
			const data = response.json();
			const startDate = new Intl.DateTimeFormat(
				`${Liferay.ThemeDisplay.getBCP47LanguageId()}`,
				{day: 'numeric', month: 'short'}
			).format(new Date(data.minDateActivity));
			const endDate = new Intl.DateTimeFormat(
				`${Liferay.ThemeDisplay.getBCP47LanguageId()}`,
				{day: 'numeric', month: 'short', year: 'numeric'}
			).format(new Date(data.maxDateActivity));
	
			document.getElementById(
				'dateField'
			).innerHTML = `${startDate} - ${endDate}`;
		}
	});


};

getMDFRequest();
