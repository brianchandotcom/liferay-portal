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

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';

export default function getMDFActivityPeriod(
	minDateActivity?: string,
	maxDateActivity?: string
) {
	if (minDateActivity && maxDateActivity) {
		const startDate = getDateCustomFormat(
			minDateActivity,
			customFormatDateOptions.SHORT_MONTH_YEAR
		);

		const endDate = getDateCustomFormat(
			maxDateActivity,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[MDFColumnKey.ACTIVITY_PERIOD]: `${startDate} - ${endDate}`,
		};
	}
}
