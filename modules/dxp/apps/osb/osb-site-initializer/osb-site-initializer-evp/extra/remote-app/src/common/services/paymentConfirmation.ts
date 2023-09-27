/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
