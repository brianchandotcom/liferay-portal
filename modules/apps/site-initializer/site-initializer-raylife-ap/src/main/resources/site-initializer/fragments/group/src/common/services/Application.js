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

import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifeapplications';

export function getApplicationsStatus(status) {
	return axios.get(`${DeliveryAPI}/?filter=applicationStatus eq '${status}'`);
}

export function getApplicationsStatusTotal() {
	return axios.get(`${DeliveryAPI}/?aggregationTerms=applicationStatus`);
}

export function getApplications() {
	return axios.get(`${DeliveryAPI}/`);
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
