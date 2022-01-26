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

import {
	PRODUCT_TYPES,
	SLA_NAMES,
	WEB_CONTENT_DXP_VERSION_TYPES,
} from './constants';

export function getWebContents({dxpVersion, slaCurrent, subscriptionGroups}) {
	const webContents = [];

	if (
		subscriptionGroups.some(
			({name}) =>
				name === PRODUCT_TYPES.dxp ||
				name === PRODUCT_TYPES.portal ||
				name === PRODUCT_TYPES.commerce
		) ||
		!subscriptionGroups.some(
			({name}) =>
				name === PRODUCT_TYPES.partnership ||
				name === PRODUCT_TYPES.dxpCloud
		)
	) {
		webContents.push('WEB-CONTENT-ACTION-01');
	}
	if (
		!subscriptionGroups.some(
			({name}) => name === PRODUCT_TYPES.partnership
		) &&
		slaCurrent !== SLA_NAMES.limitedSubscription
	) {
		webContents.push('WEB-CONTENT-ACTION-02');
	}
	if (
		subscriptionGroups.some(
			({name}) =>
				name === PRODUCT_TYPES.dxp || name === PRODUCT_TYPES.dxpCloud
		)
	) {
		webContents.push('WEB-CONTENT-ACTION-03');
	}
	if (
		subscriptionGroups.some(
			({name}) =>
				name === PRODUCT_TYPES.dxp || name === PRODUCT_TYPES.dxpCloud
		)
	) {
		webContents.push(
			dxpVersion
				? WEB_CONTENT_DXP_VERSION_TYPES[dxpVersion]
				: WEB_CONTENT_DXP_VERSION_TYPES['7.4']
		);
	}
	if (
		!subscriptionGroups.some(
			({name}) => name === PRODUCT_TYPES.analytics_cloud
		) &&
		(!subscriptionGroups.some(({name}) => name === PRODUCT_TYPES.portal) ||
			(subscriptionGroups.some(
				({name}) => name === PRODUCT_TYPES.portal
			) &&
				subscriptionGroups.some(
					({name}) =>
						name === PRODUCT_TYPES.dxp ||
						name === PRODUCT_TYPES.dxpCloud
				)))
	) {
		webContents.push('WEB-CONTENT-ACTION-09');
	}

	return webContents;
}
