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
import {SLA_LABELS} from '../utils/constants/slaLabels';
import getSLACard from '../utils/getSLACard';

export default function useSLACards(koroneikiAccount) {
	return useMemo(() => {
		if (koroneikiAccount) {
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
			} = koroneikiAccount;

			const slaCards = [];

			if (slaCurrent) {
				slaCards.push(
					getSLACard(
						slaCurrent === slaFuture
							? slaFutureEndDate
							: slaCurrentEndDate,
						slaCurrent === slaExpired
							? slaExpiredStartDate
							: slaCurrentStartDate,
						slaCurrent,
						SLA_LABELS.current
					)
				);
			}

			if (!!slaFuture && slaFuture !== slaCurrent) {
				slaCards.push(
					getSLACard(
						slaFutureEndDate,
						slaFutureStartDate,
						slaFuture,
						SLA_LABELS.future
					)
				);
			}

			if (!!slaExpired && slaExpired !== slaCurrent) {
				slaCards.push(
					getSLACard(
						slaExpiredEndDate,
						slaExpiredStartDate,
						slaExpired,
						SLA_LABELS.expired
					)
				);
			}

			return slaCards;
		}
	}, [koroneikiAccount]);
}
