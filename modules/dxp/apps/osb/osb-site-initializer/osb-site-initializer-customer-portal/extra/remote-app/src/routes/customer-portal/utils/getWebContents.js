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

import {SLA_TYPES} from '../../../common/utils/constants/slaTypes';
import {
	PRODUCT_TYPES,
	SLA_NAMES,
	WEB_CONTENT_DXP_VERSION_TYPES,
} from './constants';

export function getWebContents({dxpVersion, slaCurrent, subscriptionGroups}) {
	const webContents = [];
	const hasProjectSLA = Object.values(SLA_TYPES).some((slaType) =>
		slaCurrent?.includes(slaType)
	);

	const initialSubscriptions = Object.keys(
		PRODUCT_TYPES
	).map((productKey) => [productKey, false]);

	const hasSubscriptionGroup = subscriptionGroups?.reduce(
		(subscriptionGroupsAccumulator, subscriptionGroup) => {
			const [currentProduct] = Object.entries(PRODUCT_TYPES).find(
				([, productName]) => productName === subscriptionGroup?.name
			);

			if (currentProduct) {
				subscriptionGroupsAccumulator[currentProduct] = true;
			}

			return subscriptionGroupsAccumulator;
		},
		Object.fromEntries(initialSubscriptions)
	);

	if (hasProjectSLA) {
		webContents.push('WEB-CONTENT-ACTION-01');
	}
	if (
		!hasSubscriptionGroup.partnership &&
		slaCurrent !== SLA_NAMES.limitedSubscription
	) {
		webContents.push('WEB-CONTENT-ACTION-02');
	}
	if (
		hasSubscriptionGroup.partnership ||
		hasSubscriptionGroup.dxpCloud ||
		hasSubscriptionGroup.dxp
	) {
		webContents.push('WEB-CONTENT-ACTION-03');
	}
	if (hasSubscriptionGroup.dxp || hasSubscriptionGroup.dxpCloud) {
		webContents.push(
			dxpVersion
				? WEB_CONTENT_DXP_VERSION_TYPES[dxpVersion]
				: WEB_CONTENT_DXP_VERSION_TYPES['7.4']
		);
	}
	if (
		!hasSubscriptionGroup.partnership &&
		!hasSubscriptionGroup.analyticsCloud &&
		(!hasSubscriptionGroup.portal ||
			(hasSubscriptionGroup.portal &&
				(hasSubscriptionGroup.dxp || hasSubscriptionGroup.dxpCloud)))
	) {
		webContents.push('WEB-CONTENT-ACTION-09');
	}
	if (
		hasSubscriptionGroup.dxp ||
		hasSubscriptionGroup.portal ||
		hasSubscriptionGroup.commerce ||
		!(hasSubscriptionGroup.partnership || hasSubscriptionGroup.dxpCloud)
	) {
		webContents.push('WEB-CONTENT-ACTION-10');
	}

	return webContents;
}
