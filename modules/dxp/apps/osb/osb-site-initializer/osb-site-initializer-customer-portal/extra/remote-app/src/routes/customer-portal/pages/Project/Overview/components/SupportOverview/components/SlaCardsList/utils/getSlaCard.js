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

import {FORMAT_DATE} from '../../../../../../../../../../common/utils/constants/slaCardDate';
import getDateCustomFormat from '../../../../../../../../../../common/utils/getDateCustomFormat';

export default function getSlaCard(endDate, startDate, title, label) {
	return {
		endDate: getDateCustomFormat(endDate, FORMAT_DATE, 'en-US'),
		label,
		startDate: getDateCustomFormat(startDate, FORMAT_DATE, 'en-US'),
		title: title.split(' ')[0],
	};
}
