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

import {quarterIndexes} from './constants/quartersIndex';

export default function getChartQuarterCount(values: any[], dateCreated: any) {
	const quarter = Math.ceil((new Date(dateCreated).getMonth() + 1) / 3);

	if (quarter === 1) {
		values[quarterIndexes.QUARTER_1_INDEX]++;
	}
	if (quarter === 2) {
		values[quarterIndexes.QUARTER_2_INDEX]++;
	}
	if (quarter === 3) {
		values[quarterIndexes.QUARTER_3_INDEX]++;
	}
	if (quarter === 4) {
		values[quarterIndexes.QUARTER_4_INDEX]++;
	}

	return values;
}
