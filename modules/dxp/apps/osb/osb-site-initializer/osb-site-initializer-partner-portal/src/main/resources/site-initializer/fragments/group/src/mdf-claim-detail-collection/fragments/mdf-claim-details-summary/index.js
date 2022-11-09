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
const mdfClaimId = +currentPath.at(-1);

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

		const amountClaimed = formatCurrency(
			Liferay.Util.escape(data.amountClaimed)
		);
		const check = formatCurrency(Liferay.Util.escape(data.check));
		const paymentReceived = formatCurrency(
			Liferay.Util.escape(data.paymentReceived)
		);
		const type = Liferay.Util.escape(data.type);

		fragmentElement.querySelector('#mdf-claim-type').innerHTML = type;
		fragmentElement.querySelector(
			'#mdf-claim-amount-claimed'
		).innerHTML = amountClaimed;
		fragmentElement.querySelector(
			'#mdf-claim-payment-received'
		).innerHTML = paymentReceived;
		fragmentElement.querySelector('#mdf-claim-check').innerHTML = check;

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

if (layoutMode !== 'edit') {
	getMDFClaimSummary();
}
