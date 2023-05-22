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

import {PaymentConfirmationFilterType} from '../../types';
import fetcher from './fetcher';

const resource = 'o/c/evppaymentconfirmations/';

const nestedFields =
	'?nestedFields=r_financial_c_evpFinancial,r_requestId_c_evpRequest';

const createURLFilter = async (data: PaymentConfirmationFilterType) => {
	const filter = '/&filter=';
	const filterUrl = [];

	if (data.initialPaymentDate && data.finalPaymentDate) {
		filterUrl.push(
			`paymentDate ge ${data.initialPaymentDate} and paymentDate le ${data.finalPaymentDate}`
		);
	}
	else if (data.initialPaymentDate) {
		filterUrl.push(`paymentDate ge ${data.initialPaymentDate}`);
	}
	else if (data.finalPaymentDate) {
		filterUrl.push(`paymentDate le ${data.finalPaymentDate}`);
	}

	return filter + filterUrl.filter((item) => item).join(' and ');
};

export async function getPaymentConfirmation(
	data: PaymentConfirmationFilterType
) {
	const filter = await createURLFilter(data);

	const response = await fetcher(`${resource}${nestedFields}${filter}`);

	return response;
}
