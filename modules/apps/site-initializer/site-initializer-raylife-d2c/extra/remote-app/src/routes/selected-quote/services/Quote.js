/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
