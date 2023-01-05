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

const resource = 'o/c/evprequests/';

const nestedFields = '?nestedFields=r_organization_c_evpOrganization';

const formatArrayUrl = async (key: string, values: String[]) => {
	let operator = `${key} in ({values})`;

	operator = operator
		.replace(
			'{values}',
			values.map((value: String) => `'${value}'`).join(',')
		)
		.trim();

	return operator;
};

const finalFormatRequestTime = (dateFinal: string) => {
	return dateFinal.split('T')[0] + 'T23:59:59.999Z';
};

const formattedFieldsUrl = (key: string, value: string[]) => {
	const formatUrl = formatArrayUrl(key, value);

	return formatUrl;
};

const createURLFilter = async (data: RequestFilterType) => {
	const filter = '/&filter=';
	const filterUrl = [];
	let ISOFormattedInitialRequestDate;
	let ISOFormattedFinalRequestDate;
	let formattedTimeFinalRequestDate;

	if (data.fullName) {
		filterUrl.push(`contains(${FIELDSREPORT.FULLNAME},'${data.fullName}')`);
	}

	if (data.requestStatus.length !== 0) {
		filterUrl.push(
			await formattedFieldsUrl(
				FIELDSREPORT.REQUESTSTATUS,
				data.requestStatus
			)
		);
	}

	if (data.liferayBranch.length !== 0) {
		filterUrl.push(
			await formattedFieldsUrl(
				FIELDSREPORT.LIFERAYBRANCH,
				data.liferayBranch
			)
		);
	}

	if (data.organizationName) {
		filterUrl.push(
			`organization/organizationName eq '${data.organizationName}'`
		);
	}

	if (data.initialCompanyId && data.finalCompanyId) {
		filterUrl.push(
			`organization/idNumber ge ${data.initialCompanyId} and organization/idNumber le ${data.finalCompanyId}`
		);
	}
	else if (data.finalCompanyId) {
		filterUrl.push(`organization/idNumber le ${data.finalCompanyId}`);
	}
	else if (data.initialCompanyId) {
		filterUrl.push(`organization/idNumber ge ${data.initialCompanyId}`);
	}

	if (data.initialRequestDate && data.finalRequestDate) {
		ISOFormattedInitialRequestDate = dayjs(
			data.initialRequestDate
		).toISOString();
		ISOFormattedFinalRequestDate = dayjs(
			data.finalRequestDate
		).toISOString();

		formattedTimeFinalRequestDate = finalFormatRequestTime(
			ISOFormattedFinalRequestDate
		);

		filterUrl.push(
			`dateCreated ge ${ISOFormattedInitialRequestDate} and dateCreated le ${formattedTimeFinalRequestDate}`
		);
	}
	else if (data.initialRequestDate) {
		ISOFormattedInitialRequestDate = dayjs(
			data.initialRequestDate
		).toISOString();

		filterUrl.push(`dateCreated ge ${ISOFormattedInitialRequestDate}`);
	}
	else if (data.finalRequestDate) {
		ISOFormattedFinalRequestDate = dayjs(
			data.finalRequestDate
		).toISOString();

		formattedTimeFinalRequestDate = finalFormatRequestTime(
			ISOFormattedFinalRequestDate
		);

		filterUrl.push(`dateCreated le ${formattedTimeFinalRequestDate}`);
	}

	return filter + filterUrl.filter((item) => item).join(' and ');
};

export async function getRequestsByFilter(data: RequestFilterType) {
	const filter = await createURLFilter(data);

	const response = await fetcher(`${resource}${nestedFields}${filter}`);

	return response;
}
