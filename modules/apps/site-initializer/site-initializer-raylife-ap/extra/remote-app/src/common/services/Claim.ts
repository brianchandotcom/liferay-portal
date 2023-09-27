/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export function getClaimsData(id: number) {
	return axios.get(
		`${DeliveryAPI}/${id}/?nestedFields=raylifePolicy.raylifeQuote.raylifeApplication&fields=dataJSON,claimCreateDate,incidentDate,r_policyToClaims_c_raylifePolicyId,r_policyToClaims_c_raylifePolicyERC,claimStatus,settledDate,claimAmount,r_policyToClaims_c_raylifePolicy.r_quoteToPolicies_c_raylifeQuote.r_applicationToQuotes_c_raylifeApplication&nestedFieldsDepth=4`
	);
}
