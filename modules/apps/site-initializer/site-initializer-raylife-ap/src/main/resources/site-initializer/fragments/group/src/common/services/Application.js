/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifeapplications';

export function getApplicationsStatus(status) {
	return axios.get(`${DeliveryAPI}/?filter=applicationStatus eq '${status}'`);
}

export function getApplicationsStatusTotal() {
	return axios.get(`${DeliveryAPI}/?aggregationTerms=applicationStatus`);
}

export function getApplications() {
	return axios.get(`${DeliveryAPI}/?pageSize=300`);
}

export function getNewSubmissions(
	currentYear,
	currentMonth,
	periodYear,
	periodMonth
) {
	return axios.get(
		`${DeliveryAPI}/?fields=applicationStatus,applicationCreateDate&filter=applicationStatus ne 'bound' and applicationStatus ne 'reviewed' and applicationCreateDate le ${currentYear}-${currentMonth}-31 and applicationCreateDate ge ${periodYear}-${periodMonth}-01&pageSize=200`
	);
}
