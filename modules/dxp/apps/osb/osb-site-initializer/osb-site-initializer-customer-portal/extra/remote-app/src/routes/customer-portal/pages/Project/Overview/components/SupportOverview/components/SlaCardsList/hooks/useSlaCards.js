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
import {SLA_CARD_NAMES} from '../../../../../../../../../../common/utils/constants/slaCardNames';
import getSlaCard from '../utils/getSlaCard';

const useSlaCards = (koroneikiAccount) =>
	useMemo(() => {
		if (!koroneikiAccount) {
			return null;
		}

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
				getSlaCard(
					slaCurrent === slaFuture
						? slaFutureEndDate
						: slaCurrentEndDate,
					slaCurrent === slaExpired
						? slaExpiredStartDate
						: slaCurrentStartDate,
					slaCurrent.split(' ')[0],
					SLA_CARD_NAMES.current
				)
			);
		}

		if (!!slaExpired && slaExpired !== slaCurrent) {
			slaCards.push(
				getSlaCard(
					slaExpiredEndDate,
					slaExpiredStartDate,
					slaExpired,
					SLA_CARD_NAMES.expired
				)
			);
		}

		if (!!slaFuture && slaFuture !== slaCurrent) {
			slaCards.push(
				getSlaCard(
					slaFutureEndDate,
					slaFutureStartDate,
					slaFuture,
					SLA_CARD_NAMES.future
				)
			);
		}

		return slaCards;
	}, [koroneikiAccount]);

export default useSlaCards;
