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

import {FORMAT_DATE_TYPES} from '../../../../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../../../../common/utils/getDateCustomFormat';

export default function getSLACard(endDate, startDate, title, label) {
	return {
		endDate: getDateCustomFormat(
			endDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		),
		label,
		startDate: getDateCustomFormat(
			startDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		),
		title: title.split(' ')[0],
	};
}
