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
import {getCurrentDate} from '../utils/dateFormatter';
import {axios} from './liferay/api';
import {Liferay} from './liferay/liferay';

const DeliveryAPI = 'o/c/raylifepolicies';

const userId = Liferay.ThemeDisplay.getUserId();

export function getPolicies(parameters: Parameters = {}) {
	const parametersList = Object.keys(parameters);

	if (parametersList.length) {
		return axios.get(
			`${DeliveryAPI}/${parametersFormater(parametersList, parameters)}`
		);
	}

	return axios.get(`${DeliveryAPI}/`);
}

export function getPoliciesExpired(parameters: Parameters = {}) {
	const parametersList = Object.keys(parameters);

	if (parametersList.length) {
		return axios.get(
			`${DeliveryAPI}/${parametersFormater(
				parametersList,
				parameters
			)}&filter=endDate lt ${getCurrentDate}`
		);
	}

	return axios.get(`${DeliveryAPI}/`);
}

export function getNotExpiredPolicies(parameters: Parameters = {}) {
	const parametersList = Object.keys(parameters);

	if (parametersList.length) {
		return axios.get(
			`${DeliveryAPI}/${parametersFormater(
				parametersList,
				parameters
			)}&filter=endDate ge ${getCurrentDate}`
		);
	}

	return axios.get(`${DeliveryAPI}/`);
}

export function deletePolicyByExternalReferenceCode(
	externalReferenceCode: string
) {
	return axios.delete(
		`${DeliveryAPI}/by-external-reference-code/${externalReferenceCode}`
	);
}

export function getPoliciesForSalesGoal(
	currentYear: string,
	currentMonth: string,
	periodYear: string,
	periodMonth: string
) {
	return axios.get(
		`${DeliveryAPI}/?fields=boundDate,termPremium,productExternalReferenceCode,productName&pageSize=200&filter=policyStatus ne 'declined' and userId eq '${userId}' and boundDate le ${currentYear}-${currentMonth}-31 and boundDate ge ${periodYear}-${periodMonth}-01`
	);
}

export function getPolicyByExternalReferenceCode<T = unknown>(
	externalReferenceCode: string
) {
	return axios.get<T>(
		`${DeliveryAPI}/by-external-reference-code/${externalReferenceCode}`
	);
}
