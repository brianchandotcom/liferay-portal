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

import {useEffect, useState} from 'react';
import {FORMAT_DATE} from '../../../../../common/utils/constants/slaCardDate';
import {SLA_CARD_NAMES} from '../../../../../common/utils/constants/slaCardNames';
import getDateCustomFormat from '../../../../../common/utils/getDateCustomFormat';

const useGetSlaData = (project) => {
	const [slaData, setSlaData] = useState();
	const [slaSelected, setSlaSelected] = useState();

	useEffect(() => {
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

		const slaRawData = {
			current: {
				dateEnd: getDateCustomFormat(slaCurrentEndDate, FORMAT_DATE),
				dateStart: getDateCustomFormat(
					slaCurrentStartDate,
					FORMAT_DATE
				),
				label: SLA_CARD_NAMES.current,
				title: slaCurrent?.split(' ')[0],
			},
			expired: {
				dateEnd: getDateCustomFormat(slaExpiredEndDate, FORMAT_DATE),
				dateStart: getDateCustomFormat(
					slaExpiredStartDate,
					FORMAT_DATE
				),
				label: SLA_CARD_NAMES.expired,
				title: slaExpired?.split(' ')[0],
			},
			future: {
				dateEnd: getDateCustomFormat(slaFutureEndDate, FORMAT_DATE),
				dateStart: getDateCustomFormat(slaFutureStartDate, FORMAT_DATE),
				label: SLA_CARD_NAMES.future,
				title: slaFuture?.split(' ')[0],
			},
		};

		const slaFiltedData = [];

		if (
			slaRawData.current.title === slaRawData.expired.title &&
			slaRawData.current.title === slaRawData.future.title
		) {
			slaRawData.current.dateStart = slaRawData.expired.dateStart;
			slaRawData.current.dateEnd = slaRawData.future.dateEnd;
			slaFiltedData.push(slaRawData.current);
		} else if (slaRawData.current.title === slaRawData.expired.title) {
			slaRawData.current.dateStart = slaRawData.expired.dateStart;
			slaFiltedData.push(slaRawData.current);
			slaFiltedData.push(slaRawData.future);
		} else if (slaRawData.current.title === slaRawData.future.title) {
			slaRawData.current.dateEnd = slaRawData.future.dateEnd;
			slaFiltedData.push(slaRawData.current);
			slaFiltedData.push(slaRawData.expired);
		} else {
			slaFiltedData.push(slaRawData.current);
			slaFiltedData.push(slaRawData.expired);
			slaFiltedData.push(slaRawData.future);
		}

		const slaSelectedCards = slaFiltedData.filter((sla) => sla.title);

		if (!slaSelected) {
			setSlaSelected(slaSelectedCards[0]?.label);
		}

		setSlaData(slaSelectedCards);
	}, [project, slaSelected]);

	return {setSlaSelected, slaData, slaSelected};
};

export default useGetSlaData;
