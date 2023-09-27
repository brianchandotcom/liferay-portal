/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
/* eslint-disable no-undef */
const findRequestIdUrl = (paramsUrl) => {
	const splitParamsUrl = paramsUrl.split('?');

	return splitParamsUrl[0];
};

const currentPath = Liferay.currentURL.split('/');
const mdfRequestId = findRequestIdUrl(currentPath.at(-1));

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

		const startDate = formatNewDate(
			Liferay.Util.escape(data.minDateActivity)
		);
		const endDate = formatEndDate(
			Liferay.Util.escape(data.maxDateActivity)
		);
		const totalCost = formatCurrency(
			Liferay.Util.escape(data.totalCostOfExpense),
			data.currency ? Liferay.Util.escape(data.currency.key) : 'USD'
		);
		const requestedCost = formatCurrency(
			Liferay.Util.escape(data.totalMDFRequestAmount),
			data.currency ? Liferay.Util.escape(data.currency.key) : 'USD'
		);

		fragmentElement.querySelector(
			'#mdf-request-date-field'
		).innerHTML = `${startDate} - ${endDate}`;
		fragmentElement.querySelector(
			'#mdf-request-total-cost'
		).innerHTML = totalCost;
		fragmentElement.querySelector(
			'#mdf-request-requested-cost'
		).innerHTML = requestedCost;

		return;
	}

	Liferay.Util.openToast({
		message: 'An unexpected error occured.',
		type: 'danger',
	});
};

const formatCurrency = (value, currencyKey) =>
	new Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		currency: currencyKey ? currencyKey : 'USD',
		style: 'currency',
	}).format(value);

const formatEndDate = (value) =>
	new Intl.DateTimeFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		day: 'numeric',
		month: 'short',
		timeZone: 'UTC',
		year: 'numeric',
	}).format(new Date(value));

const formatNewDate = (value) =>
	new Intl.DateTimeFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		day: 'numeric',
		month: 'short',
		timeZone: 'UTC',
	}).format(new Date(value));

if (layoutMode !== 'edit') {
	updateMDFDetailsSummary();
}
