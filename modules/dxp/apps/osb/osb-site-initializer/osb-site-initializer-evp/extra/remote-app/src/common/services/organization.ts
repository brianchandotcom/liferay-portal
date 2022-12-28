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

import {OrganizationFilterType} from '../../types/index';
import fetcher from './fetcher';
const resource = 'o/c/evporganizations';

export async function getOrganizations(organizationName: string) {
	const response = await fetcher(
		`${resource}/?filter=contains(organizationName,'${organizationName}')`
	);

	return response;
}

export async function getIdGreaterOrEqualOrganization(
	idOrganizationInitial: number
) {
	const response = await fetcher(
		`${resource}/?filter=idNumber ge ${idOrganizationInitial}`
	);

	return response;
}

export async function filteredOrganizationsERC(organization: any) {
	const externalReferenceCodes: string[] = [];

	organization.items.forEach((organization: OrganizationFilterType) => {
		externalReferenceCodes.push(organization.externalReferenceCode);
	});

	return externalReferenceCodes;
}

export async function getIdLessOrEqualOrganization(
	idOrganizationFinal: number
) {
	const response = await fetcher(
		`${resource}/?filter=idNumber le ${idOrganizationFinal}`
	);

	return response;
}

export async function getOrganizationBetweenIds(
	idOrganizationInitial: number,
	idOrganizationFinal: number
) {
	const response = await fetcher(
		`${resource}/?filter=idNumber ge ${idOrganizationInitial} and idNumber le ${idOrganizationFinal}`
	);

	return response;
}

export async function getOrganizationName(organizationName: string) {
	const organizations = await getOrganizations(organizationName);

	const externalReferenceCodes: String[] = [];

	const filteredOrganization = organizations.items.filter(
		(organization: OrganizationFilterType) =>
			organization.organizationName.includes(organizationName)
	);

	filteredOrganization.forEach((organization: OrganizationFilterType) => {
		externalReferenceCodes.push(organization.externalReferenceCode);
	});

	return externalReferenceCodes;
}
