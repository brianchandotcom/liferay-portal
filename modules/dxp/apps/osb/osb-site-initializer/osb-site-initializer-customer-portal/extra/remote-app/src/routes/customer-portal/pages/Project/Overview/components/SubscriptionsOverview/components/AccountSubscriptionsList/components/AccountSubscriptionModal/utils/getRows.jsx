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

import {StatusTag} from '../../../../../../../../../../../../common/components';
import {
	FORMAT_DATE_TYPES,
	SLA_STATUS_TYPES,
} from '../../../../../../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../../../../../../common/utils/getDateCustomFormat';

export default function getRows(orderItems) {
	return orderItems?.map(({options, quantity, reducedCustomFields}) => {
		const datesDisplay = `${getDateCustomFormat(
			options?.startDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		)} - ${getDateCustomFormat(
			options?.endDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		)}`;

		return {
			'instance-size': options?.instanceSize,
			'provisioned': reducedCustomFields?.provisionedCount,
			quantity,
			'start-end-date': datesDisplay,
			'subscription-term-status': reducedCustomFields?.status && (
				<StatusTag
					currentStatus={
						SLA_STATUS_TYPES[
							reducedCustomFields?.status.toLowerCase()
						]
					}
				/>
			),
		};
	});
}
