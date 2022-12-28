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

import dayjs from 'dayjs';

import {FIELDSREPORT, RequestFilterType} from '../../types';
import fetcher from './fetcher';
import {
	filteredOrganizationsERC,
	getIdGreaterOrEqualOrganization,
	getIdLessOrEqualOrganization,
	getOrganizationBetweenIds,
	getOrganizationName,
} from './organization';

const resource = 'o/c/evprequests';

const nestedFields = '&nestedFields=r_organization_c_evpOrganization';

const formatArrayUrl = (key: string, value: String[]) => {
	let operator = `${key} in ({values})`;

	operator = operator
		.replace('{values}', value.map((value) => `'${value}'`).join(','))
		.trim();

	return operator;
};

const finalFormatRequestTime = (dateFinal: string) => {
	return dateFinal.split('T')[0] + 'T23:59:59.999Z';
};

const createURLFilter = async (data: RequestFilterType) => {
	let formatUrl;
	let externalReferenceCodeOrganization;
	const filterAnd = '/?filter=';
	const filter = [];
	let ISOFormattedInitialRequestDate;
	let ISOFormattedFinalRequestDate;
	let formattedTimeFinalRequestDate;

	if (data.fullName) {
		filter.push(`contains(${FIELDSREPORT.FULLNAME},'${data.fullName}')`);
	}

	if (data.requestStatus.length !== 0) {
		formatUrl = formatArrayUrl(
			FIELDSREPORT.REQUESTSTATUS,
			data.requestStatus
		);
		filter.push(formatUrl);
	}

	if (data.liferayBranch.length !== 0) {
		formatUrl = formatArrayUrl(
			FIELDSREPORT.LIFERAYBRANCH,
			data.liferayBranch
		);
		filter.push(formatUrl);
	}

	if (data.organizationName) {
		externalReferenceCodeOrganization = await getOrganizationName(
			data.organizationName
		);
		filter.push(
			`contains(r_organization_c_evpOrganizationERC,'${externalReferenceCodeOrganization}')`
		);
	}

	if (data.initialCompanyId && data.finalCompanyId) {
		const organizationsBetweenIds = await getOrganizationBetweenIds(
			Number(data.initialCompanyId),
			Number(data.finalCompanyId)
		);

		externalReferenceCodeOrganization = await filteredOrganizationsERC(
			organizationsBetweenIds
		);
		if (externalReferenceCodeOrganization.length !== 0) {
			formatUrl = formatArrayUrl(
				'r_organization_c_evpOrganizationERC',
				externalReferenceCodeOrganization
			);
			filter.push(formatUrl);
		}
	}

	if (data.finalCompanyId) {
		const organizationsLessOrEqual = await getIdLessOrEqualOrganization(
			Number(data.finalCompanyId)
		);

		externalReferenceCodeOrganization = await filteredOrganizationsERC(
			organizationsLessOrEqual
		);

		if (externalReferenceCodeOrganization.length !== 0) {
			formatUrl = formatArrayUrl(
				'r_organization_c_evpOrganizationERC',
				externalReferenceCodeOrganization
			);
			filter.push(formatUrl);
		}
	}

	if (data.initialCompanyId) {
		const organizationsGreaterOrEqual = await getIdGreaterOrEqualOrganization(
			Number(data.initialCompanyId)
		);
		externalReferenceCodeOrganization = await filteredOrganizationsERC(
			organizationsGreaterOrEqual
		);

		if (externalReferenceCodeOrganization.length !== 0) {
			formatUrl = formatArrayUrl(
				'r_organization_c_evpOrganizationERC',
				externalReferenceCodeOrganization
			);
			filter.push(formatUrl);
		}
	}

	if (data.initialRequestDate && data.finalRequestDate) {
		ISOFormattedInitialRequestDate = dayjs(
			data.initialRequestDate
		).toISOString();
		ISOFormattedFinalRequestDate = dayjs(
			data.finalRequestDate
		).toISOString();

		if (ISOFormattedInitialRequestDate === ISOFormattedFinalRequestDate) {
			formattedTimeFinalRequestDate = finalFormatRequestTime(
				ISOFormattedFinalRequestDate
			);

			filter.push(
				`dateCreated ge ${ISOFormattedInitialRequestDate} and dateCreated le ${formattedTimeFinalRequestDate}`
			);
		}
		else {
			formattedTimeFinalRequestDate = finalFormatRequestTime(
				ISOFormattedFinalRequestDate
			);

			filter.push(
				`dateCreated ge ${ISOFormattedInitialRequestDate} and dateCreated le ${formattedTimeFinalRequestDate}`
			);
		}
	}
	else if (data.initialRequestDate) {
		ISOFormattedInitialRequestDate = dayjs(
			data.initialRequestDate
		).toISOString();

		filter.push(`dateCreated ge ${ISOFormattedInitialRequestDate}`);
	}
	else if (data.finalRequestDate) {
		ISOFormattedFinalRequestDate = dayjs(
			data.finalRequestDate
		).toISOString();

		formattedTimeFinalRequestDate = finalFormatRequestTime(
			ISOFormattedFinalRequestDate
		);

		filter.push(`dateCreated le ${formattedTimeFinalRequestDate}`);
	}

	return filterAnd + filter.join(' and ');
};

export async function getRequestsByFilter(data: RequestFilterType) {
	const filter = await createURLFilter(data);

	const response = await fetcher(`${resource}${filter}${nestedFields}`);

	return response;
}
