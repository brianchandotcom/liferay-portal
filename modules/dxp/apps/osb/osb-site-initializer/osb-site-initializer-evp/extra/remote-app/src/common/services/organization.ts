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

import {OrganizationFilterType} from '../../types';
import fetcher from './fetcher';
const resource = 'o/c/evporganizations';

export async function getOrganizations(organizationName: string) {
	const response = await fetcher(
		`${resource}/?filter=contains(organizationName,'${organizationName}')`
	);

	return response;
}

export async function getIdGreaterOrEqualOrganization(
	idOrganizationInitial: Number
) {
	const response = await fetcher(
		`${resource}/?filter=idNumber ge ${idOrganizationInitial}`
	);

	return response;
}

export async function filteredOrganizationsERC(organization: any) {
	const externalReferenceCodes: String[] = [];

	organization.items.forEach((organization: OrganizationFilterType) => {
		externalReferenceCodes.push(organization.externalReferenceCode);
	});

	return externalReferenceCodes;
}

export async function getIdLessOrEqualOrganization(
	idOrganizationFinal: Number
) {
	const response = await fetcher(
		`${resource}/?filter=idNumber le ${idOrganizationFinal}`
	);

	return response;
}

export async function getOrganizationBetweenIds(
	idOrganizationInitial: Number,
	idOrganizationFinal: Number
) {
	const response = await fetcher(
		`${resource}/?filter=idNumber ge ${idOrganizationInitial} and idNumber le ${idOrganizationFinal}`
	);

	// console.log(response);

	return response;
}

// export async function eachOrganization(
// 	organizationName: any,
// 	idOrganizationInitial: Number
// ) {
// 	const externalReferenceCodes: String[] = [];
// 	const filteredOrganization = organizationName.items.filter(
// 		(organization: OrganizationFilterType) =>
// 			organization.idNumber.includes(idOrganizationInitial)
// 	);
// 	filteredOrganization.forEach((organization: OrganizationFilterType) => {
// 		externalReferenceCodes.push(organization.externalReferenceCode);
// 	});

// 	return externalReferenceCodes;
// }
