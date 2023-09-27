/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {
	ACTIVATION_KEYS_LICENSE_FILTER_TYPES as FILTER_TYPES,
	ACTIVATION_STATUS,
} from '../utils/constants';
import {getGroupButtons} from '../utils/getGroupButtons';

export default function useStatusCountNavigation(activationKeys) {
	const [statusCountNavigation, setStatusCountNavigation] = useState();
	const [statusFilter, setStatusFilter] = useState(ACTIVATION_STATUS.all.id);

	useEffect(() => {
		if (activationKeys) {
			const statusCountMap = {
				activatedTotalCount: 0,
				allTotalCount: activationKeys.length,
				expiredTotalCount: 0,
				notActiveTotalCount: 0,
			};

			for (const activationKey of activationKeys) {
				const isNotActivate = FILTER_TYPES.notActivated(activationKey);
				const isActivate = FILTER_TYPES.activated(activationKey);
				const isExpired = FILTER_TYPES.expired(activationKey);

				if (isNotActivate) {
					statusCountMap.notActiveTotalCount = ++statusCountMap.notActiveTotalCount;
				}
				else if (isActivate) {
					statusCountMap.activatedTotalCount = ++statusCountMap.activatedTotalCount;
				}
				else if (isExpired) {
					statusCountMap.expiredTotalCount = ++statusCountMap.expiredTotalCount;
				}
			}

			setStatusCountNavigation(statusCountMap);
		}
	}, [activationKeys]);

	const navigationGroupButtons = useMemo(
		() => [
			getGroupButtons(
				ACTIVATION_STATUS.all,
				statusCountNavigation?.allTotalCount
			),
			getGroupButtons(
				ACTIVATION_STATUS.activated,
				statusCountNavigation?.activatedTotalCount
			),
			getGroupButtons(
				ACTIVATION_STATUS.notActivated,
				statusCountNavigation?.notActiveTotalCount
			),
			getGroupButtons(
				ACTIVATION_STATUS.expired,
				statusCountNavigation?.expiredTotalCount
			),
		],
		[
			statusCountNavigation?.activatedTotalCount,
			statusCountNavigation?.allTotalCount,
			statusCountNavigation?.expiredTotalCount,
			statusCountNavigation?.notActiveTotalCount,
		]
	);

	return {
		navigationGroupButtons,
		statusfilterByTitle: [statusFilter, setStatusFilter],
	};
}
