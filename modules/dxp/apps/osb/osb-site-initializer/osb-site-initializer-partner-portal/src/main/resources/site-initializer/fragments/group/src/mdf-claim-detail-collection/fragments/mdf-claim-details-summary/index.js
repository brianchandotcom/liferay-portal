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
const mdfClaimId = findRequestIdUrl(currentPath.at(-1));

const getMDFClaimSummary = async () => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`/o/c/mdfclaims/${mdfClaimId}`, {
		headers: {
			'accept': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (response.ok) {
		const data = await response.json();

		const totalClaimAmount = formatCurrency(
			Liferay.Util.escape(data.totalClaimAmount),
			data.currency ? Liferay.Util.escape(data.currency.key) : 'USD'
		);
		const check = Liferay.Util.escape(data.checkNumber);

		const claimPaid = formatCurrency(
			Liferay.Util.escape(data.claimPaid),
			data.currency ? Liferay.Util.escape(data.currency.key) : 'USD'
		);
		const type = Liferay.Util.escape(data.partial ? 'Partial' : 'Full');

		fragmentElement.querySelector('#mdf-claim-type').innerHTML = type;
		fragmentElement.querySelector(
			'#mdf-claim-amount-claimed'
		).innerHTML = totalClaimAmount;
		fragmentElement.querySelector(
			'#mdf-claim-payment-received'
		).innerHTML = claimPaid;
		fragmentElement.querySelector('#mdf-claim-check').innerHTML = check;

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

if (layoutMode !== 'edit' && !isNaN(mdfClaimId)) {
	getMDFClaimSummary();
}
