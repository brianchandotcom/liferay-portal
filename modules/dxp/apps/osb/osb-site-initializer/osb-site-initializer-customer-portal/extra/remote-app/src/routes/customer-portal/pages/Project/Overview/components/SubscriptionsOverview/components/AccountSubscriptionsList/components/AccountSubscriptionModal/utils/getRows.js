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
import {FORMAT_DATE_TYPES} from '../../../../../../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../../../../../../common/utils/getDateCustomFormat';
import {STATUS_TAG_TYPES} from '../../../../../../../../../../utils/constants/statusTag';

export default function getRows(orderItems) {
	return orderItems?.map(({customFields, options, quantity}) => {
		const datesDisplay = `${getDateCustomFormat(
			options.startDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		)} - ${getDateCustomFormat(
			options.endDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		)}`;

		return {
			'instance-size': options.instanceSize,
			'provisioned': customFields.provisionedCount,
			quantity,
			'start-end-date': datesDisplay,
			'subscription-term-status': customFields.status && (
				<StatusTag
					currentStatus={
						STATUS_TAG_TYPES[customFields.status.toLowerCase()]
					}
				/>
			),
		};
	});
}
