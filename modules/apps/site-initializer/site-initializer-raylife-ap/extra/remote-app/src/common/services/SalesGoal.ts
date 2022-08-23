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
