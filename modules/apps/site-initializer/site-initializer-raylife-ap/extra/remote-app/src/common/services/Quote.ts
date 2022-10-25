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
			`${DeliveryAPI}/${parametersFormater(parametersList, parameters)}`
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
