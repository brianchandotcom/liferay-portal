/* eslint-disable eqeqeq */
/* eslint-disable @liferay/portal/no-global-fetch */
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
