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

const currentPath = Liferay.currentURL.split('/');
const mdfRequestId = +currentPath[currentPath.length - 1];

const updateMDFDetailsSummary = async () => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`/o/c/mdfrequests/${mdfRequestId}`, {
		headers: {
			'accept': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (response.ok) {
		const data = await response.json();

		const startDate = new Intl.DateTimeFormat(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			{day: 'numeric', month: 'short'}
		).format(new Date(data.minDateActivity));

		const endDate = new Intl.DateTimeFormat(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			{day: 'numeric', month: 'short', year: 'numeric'}
		).format(new Date(data.maxDateActivity));

		const totalCost = formatCurrency(data.totalCostOfExpense);

		const requestedCost = formatCurrency(data.totalMDFRequestAmount);

		fragmentElement.querySelector(
			'#dateField'
		).innerHTML = `${startDate} - ${endDate}`;

		fragmentElement.querySelector('#totalCost').innerHTML = `${totalCost}`;

		fragmentElement.querySelector(
			'#requestedCost'
		).innerHTML = `${requestedCost}`;

		return;
	}

	Liferay.Util.openToast({
		message: 'An unexpected error occured.',
		type: 'danger',
	});
};

const formatCurrency = (value) =>
	new Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		currency: 'USD',
		style: 'currency',
	}).format(value);

updateMDFDetailsSummary();
