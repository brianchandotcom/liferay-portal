/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EEntityFieldType} from '../utils/types';
import type {FilterImplementation, FilterImplementationArgs} from '../Filter';
export interface DateRangeFilterImplementationArgs
	extends FilterImplementationArgs<SelectedData> {
	entityFieldType: EEntityFieldType;
	max: Date;
	min: Date;
	placeholder: string;
}
interface Date {
	day: number;
	month: number;
	year: number;
}
interface SelectedData {
	from: Date;
	to: Date;
}
declare const filterImplementation: FilterImplementation<DateRangeFilterImplementationArgs>;
export default filterImplementation;
