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

import {useEffect, useMemo, useState} from 'react';
import {
	ACTIVATION_KEYS_LICENSE_FILTER_TYPES as FILTER_TYPES,
	ACTIVATION_STATUS,
} from '../utils/constants';
import {getGroupButtons} from '../utils/getGroupButtons';

export default function useStatusCountNavigation(activationKeys) {
	const [statusCountNavigation, setStatusCountNavigation] = useState({
		activatedTotalCount: 0,
		allTotalCount: 0,
		expiredTotalCount: 0,
		notActiveTotalCount: 0,
	});
	const [statusFilter, setStatusFilter] = useState(ACTIVATION_STATUS.all.id);

	useEffect(() => {
		if (activationKeys.length) {
			setStatusCountNavigation((previousStatusCountNavigation) => {
				const statusCount = activationKeys?.reduce(
					(statusCountAccumulator, activationKey) => {
						const isActivate = FILTER_TYPES.activated(
							activationKey
						);
						if (isActivate) {
							statusCountAccumulator.activatedTotalCount = ++statusCountAccumulator.activatedTotalCount;

							return statusCountAccumulator;
						}

						const isExpired = FILTER_TYPES.expired(activationKey);
						if (isExpired) {
							statusCountAccumulator.expiredTotalCount = ++statusCountAccumulator.expiredTotalCount;

							return statusCountAccumulator;
						}

						const isNotActivate = FILTER_TYPES.notActivated(
							activationKey
						);
						if (isNotActivate) {
							statusCountAccumulator.notActiveTotalCount = ++statusCountAccumulator.notActiveTotalCount;

							return statusCountAccumulator;
						}
					},
					previousStatusCountNavigation
				);

				statusCount.allTotalCount = activationKeys.length;

				return statusCount;
			});
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
