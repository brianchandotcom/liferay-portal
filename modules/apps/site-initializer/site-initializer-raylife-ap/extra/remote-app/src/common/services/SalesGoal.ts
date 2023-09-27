/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';
import {Liferay} from './liferay/liferay';

const DeliveryAPI = 'o/c/raylifesalesgoals';
const userId = Liferay.ThemeDisplay.getUserId();

export function getSalesGoal(
	limitYear: string,
	limitMonth: string,
	baseYear: string,
	baseMonth: string
) {
	return axios.get(
		`${DeliveryAPI}/?fields=finalReferenceDate,goalValue,initialReferenceDate,productExternalReferenceCode&pageSize=200&filter=finalReferenceDate le ${limitYear}-${limitMonth}-31 and finalReferenceDate ge ${baseYear}-${baseMonth}-01 and userId eq '${userId}'`
	);
}
