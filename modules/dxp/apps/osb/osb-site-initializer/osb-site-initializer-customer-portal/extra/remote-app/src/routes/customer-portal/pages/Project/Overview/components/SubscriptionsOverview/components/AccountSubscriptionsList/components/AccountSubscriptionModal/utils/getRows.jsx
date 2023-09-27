/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
