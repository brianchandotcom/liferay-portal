/* eslint-disable no-console */
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

import {OrganizationFilterType, RequestFilterType} from '../../types';
import fetcher from './fetcher';
import {
	filteredOrganizationsERC,
	getIdGreaterOrEqualOrganization,
	getIdLessOrEqualOrganization,
	getOrganizationBetweenIds,
	getOrganizations,
} from './organization';

const resource = 'o/c/evprequests';

const nestedFields = '&nestedFields=r_organization_c_evpOrganization';

const getOrganizationName = async (organizationName: string) => {
	const organizations = await getOrganizations(organizationName);

	const externalReferenceCodes: String[] = [];

	const filteredOrganization = organizations.items.filter(
		(organization: OrganizationFilterType) =>
			organization.organizationName.includes(organizationName)
	);

	filteredOrganization.forEach((organization: OrganizationFilterType) => {
		externalReferenceCodes.push(organization.externalReferenceCode);
	});

	// console.log(filteredOrganization);

	return externalReferenceCodes;
};

const formatArrayUrl = (key: string, value: String[]) => {
	let operator = `${key} in ({values})`;

	operator = operator
		.replace('{values}', value.map((value) => `'${value}'`).join(','))
		.trim();

	return operator;
};

const createURLFilter = async (filters: Array<Array<string>>) => {
	let filter = '/?filter=';
	let formatUrl;
	let externalReferenceCodeOrganization;
	const fieldsCompanyIds = [];

	// console.log(filters);

	for (let i = 0; i < filters.length; i++) {
		const key = filters[i][0];
		const value = filters[i][1];

		if (key) {
			if (Array.isArray(value)) {
				formatUrl = formatArrayUrl(key, value);

				filter += formatUrl;
			} else if (key.includes('fullName')) {
				filter += `contains(${key},'${value}')`;
			} else if (key.includes('organizationName')) {
				externalReferenceCodeOrganization = await getOrganizationName(
					value
				);

				filter += `contains(r_organization_c_evpOrganizationERC,'${externalReferenceCodeOrganization}')`;
			} else if (
				key.includes('initialCompanyId') ||
				key.includes('finalCompanyId')
			) {
				fieldsCompanyIds.push(value);
				if (key.includes('initialCompanyId')) {
					const organizationsGreaterOrEqual = await getIdGreaterOrEqualOrganization(
						Number(value)
					);

					externalReferenceCodeOrganization = await filteredOrganizationsERC(
						organizationsGreaterOrEqual
					);

					formatUrl = formatArrayUrl(
						'r_organization_c_evpOrganizationERC',
						externalReferenceCodeOrganization
					);

					filter += formatUrl;
				}
				if (key.includes('finalCompanyId')) {
					const organizationsLessOrEqual = await getIdLessOrEqualOrganization(
						Number(value)
					);

					externalReferenceCodeOrganization = await filteredOrganizationsERC(
						organizationsLessOrEqual
					);

					formatUrl = formatArrayUrl(
						'r_organization_c_evpOrganizationERC',
						externalReferenceCodeOrganization
					);

					filter += formatUrl;
				}

				if (fieldsCompanyIds.length === 2) {
					const organizationsBetweenIds = await getOrganizationBetweenIds(
						Number(fieldsCompanyIds[0]),
						Number(fieldsCompanyIds[1])
					);

					externalReferenceCodeOrganization = await filteredOrganizationsERC(
						organizationsBetweenIds
					);

					formatUrl = formatArrayUrl(
						'r_organization_c_evpOrganizationERC',
						externalReferenceCodeOrganization
					);

					filter += formatUrl;
				}
			} else {
				filter += `${key} eq '${value}'`;
			}

			console.log(filter);
		}

		// console.log(filter);

		if (i < filters.length - 1) {
			filter += ' and ';
		}
	}

	return filter;
};

export async function getRequestsByFilter(data: RequestFilterType) {
	const filteredFields = [];
	console.log(data);
	for (const [key, value] of Object.entries(data)) {
		if (value && !!value.length) {
			filteredFields.push([key, value]);
		}
	}

	const filter = await createURLFilter(filteredFields);

	const response = await fetcher(`${resource}${filter}${nestedFields}`);

	console.log(response);

	return response;
}
