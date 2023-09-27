/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Parameters, parametersFormater} from '.';
import {currentDate} from '../utils/dateFormatter';
import {axios} from './liferay/api';
const nowDate = currentDate;

const DeliveryAPI = 'o/c/raylifequotes';

type ApplicationStateType = {
	applicationId: string;
};

export function getQuotes(parameters: Parameters = {}) {
	const parametersList = Object.keys(parameters);

	if (parametersList.length) {
		return axios.get(
			`${DeliveryAPI}/?${parametersFormater(parametersList, parameters)}`
		);
	}

	return axios.get(`${DeliveryAPI}/`);
}

export function getQuotesById(id: number) {
	return axios.get(`${DeliveryAPI}/?filter=id eq '${id}'`);
}

const adaptQuoteRequest = ({applicationId}: ApplicationStateType) => ({
	billingOption: {
		key: 'payInFull',
		name: 'Pay in Full',
	},
	quoteCreateDate: nowDate,
	r_applicationToQuotes_c_raylifeApplicationId: applicationId,
});

export function createRaylifeAutoQuote(state: ApplicationStateType) {
	const payload = adaptQuoteRequest(state);

	return axios.post(`${DeliveryAPI}/`, payload);
}
