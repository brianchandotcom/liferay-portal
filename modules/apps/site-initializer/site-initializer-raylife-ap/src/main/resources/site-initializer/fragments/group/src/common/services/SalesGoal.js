/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifesalesgoals';
const userId = Liferay.ThemeDisplay.getUserId();

export function getSalesGoal(
	currentYear,
	currentMonth,
	periodYear,
	periodMonth
) {
	return axios.get(
		`${DeliveryAPI}/?fields=finalReferenceDate,goalValue,initialReferenceDate&pageSize=200&filter=finalReferenceDate le ${currentYear}-${currentMonth}-31 and finalReferenceDate ge ${periodYear}-${periodMonth}-01 and userId eq '${userId}'`
	);
}
