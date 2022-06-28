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

import {useMemo} from 'react';
import {FORMAT_DATE} from '../../../../../common/utils/constants/slaCardDate';
import {SLA_CARD_NAMES} from '../../../../../common/utils/constants/slaCardNames';
import getDateCustomFormat from '../../../../../common/utils/getDateCustomFormat';

const useGetSlaData = (project) => {
	const memoizedSlaCards = useMemo(() => {
		const {
			slaCurrent,
			slaCurrentEndDate,
			slaCurrentStartDate,
			slaExpired,
			slaExpiredEndDate,
			slaExpiredStartDate,
			slaFuture,
			slaFutureEndDate,
			slaFutureStartDate,
		} = project;

		const slaCurrentStatus = !!slaCurrent && {
			endDate: getDateCustomFormat(
				slaCurrent === slaFuture ? slaFutureEndDate : slaCurrentEndDate,
				FORMAT_DATE,
				'en-US'
			),
			label: SLA_CARD_NAMES.current,
			startDate: getDateCustomFormat(
				slaCurrent === slaExpired
					? slaExpiredStartDate
					: slaCurrentStartDate,
				FORMAT_DATE,
				'en-US'
			),
			title: slaCurrent.split(' ')[0],
		};

		const slaCards = [];

		if (slaCurrentStatus) {
			slaCards.push(slaCurrentStatus);
		}

		if (!!slaExpired && slaExpired !== slaCurrent) {
			slaCards.push({
				endDate: getDateCustomFormat(
					slaExpiredEndDate,
					FORMAT_DATE,
					'en-US'
				),
				label: SLA_CARD_NAMES.expired,
				startDate: getDateCustomFormat(
					slaExpiredStartDate,
					FORMAT_DATE,
					'en-US'
				),
				title: slaExpired.split(' ')[0],
			});
		}

		if (!!slaFuture && slaFuture !== slaCurrent) {
			slaCards.push({
				endDate: getDateCustomFormat(
					slaFutureEndDate,
					FORMAT_DATE,
					'en-US'
				),
				label: SLA_CARD_NAMES.future,
				startDate: getDateCustomFormat(
					slaFutureStartDate,
					FORMAT_DATE,
					'en-US'
				),
				title: slaFuture.split(' ')[0],
			});
		}

		return slaCards;
	}, [project]);

	return {memoizedSlaCards};
};

export default useGetSlaData;
