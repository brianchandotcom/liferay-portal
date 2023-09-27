/* eslint-disable eqeqeq */
/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const findRequestIdUrl = (paramsUrl) => {
	const splitParamsUrl = paramsUrl.split('?');

	return splitParamsUrl[0];
};
const currentPath = Liferay.currentURL.split('/');
const evpRequestId = findRequestIdUrl(currentPath.at(-1));
const text = document.querySelector('.txt');

const paymentData = [];

const getPaymentData = async () => {
	await fetch(`/o/c/evppaymentconfirmations`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'GET',
	})
		.then((response) => response.json())
		.then((data) => paymentData.push(data));
};

const getPaymentDataFromRequest = async () => {
	await getPaymentData();

	const requestFiltered = paymentData[0]?.items.filter((item) => {
		return item?.r_requestId_c_evpRequestId == evpRequestId;
	});

	if (requestFiltered[0]?.paymentDate) {
		const date = requestFiltered[0]?.paymentDate;
		const year = date.slice(0, 4);
		const month = date.slice(5, 7);
		const day = date.slice(8, 10);

		text.innerHTML = Liferay.Util.escape(month + '/' + day + '/' + year);
	}
};

getPaymentDataFromRequest();
