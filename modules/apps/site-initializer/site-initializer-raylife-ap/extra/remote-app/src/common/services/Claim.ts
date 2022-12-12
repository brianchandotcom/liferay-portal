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
import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifeclaims';

export function getClaims(parameters: Parameters = {}) {
	const parametersList = Object.keys(parameters);
	if (parametersList.length) {
		return axios.get(
			`${DeliveryAPI}?nestedFields=r_policyToClaims_c_raylifePolicyId&${parametersFormater(
				parametersList,
				parameters
			)}`
		);
	}

	return axios.get(`${DeliveryAPI}/`);
}

export function getClaimsByPolicyId(policyId: number) {
	return axios.get(
		`${DeliveryAPI}/?filter=r_policyToClaims_c_raylifePolicyId eq '${policyId}'`
	);
}

export function deleteClaimByExternalReferenceCode(
	externalReferenceCode: string
) {
	return axios.delete(
		`${DeliveryAPI}/by-external-reference-code/${externalReferenceCode}`
	);
}
