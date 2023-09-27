/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../../common/services/liferay/api';
import {STORAGE_KEYS, getItem} from '../../../common/services/liferay/storage';

const headlessRaylifeQuotesAPI = 'o/c/raylifequotes';
const quoteId = getItem(STORAGE_KEYS.QUOTE_ID);

export function updateQuoteOrder(orderId) {
	const payload = {
		r_commerceOrderToQuotes_commerceOrderId: orderId,
	};

	return axios.patch(`${headlessRaylifeQuotesAPI}/${quoteId}`, payload);
}

export function updateQuoteBillingOption(id) {
	return axios.patch(`${headlessRaylifeQuotesAPI}/${quoteId}`, {
		billingOption: {
			key: id === 0 ? 'payInFull' : 'installments',
			name: id === 0 ? 'Pay in Full' : 'Installments',
		},
	});
}
