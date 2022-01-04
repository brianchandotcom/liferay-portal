import {
	PRODUCTS,
	SLA_NAMES,
	WEB_CONTENTS_BY_LIFERAY_VERSION,
} from './constants';

export function getWebContents({dxpVersion, slaCurrent, subscriptionGroups}) {
	const webContents = [];

	if (
		subscriptionGroups.some(
			({name}) =>
				name === PRODUCTS.dxp ||
				name === PRODUCTS.portal ||
				name === PRODUCTS.commerce
		) ||
		!subscriptionGroups.some(
			({name}) =>
				name === PRODUCTS.partnership || name === PRODUCTS.dxp_cloud
		)
	) {
		webContents.push('WEB-CONTENT-ACTION-01');
	}
	if (
		!subscriptionGroups.some(({name}) => name === PRODUCTS.partnership) &&
		slaCurrent !== SLA_NAMES.limited_subscription
	) {
		webContents.push('WEB-CONTENT-ACTION-02');
	}
	if (
		subscriptionGroups.some(
			({name}) => name === PRODUCTS.dxp || name === PRODUCTS.dxp_cloud
		)
	) {
		webContents.push('WEB-CONTENT-ACTION-03');
	}
	if (
		subscriptionGroups.some(
			({name}) => name === PRODUCTS.dxp || name === PRODUCTS.dxp_cloud
		)
	) {
		webContents.push(
			dxpVersion
				? WEB_CONTENTS_BY_LIFERAY_VERSION[dxpVersion]
				: WEB_CONTENTS_BY_LIFERAY_VERSION['7.4']
		);
	}
	if (
		!subscriptionGroups.some(
			({name}) => name === PRODUCTS.analytics_cloud
		) &&
		(!subscriptionGroups.some(({name}) => name === PRODUCTS.portal) ||
			(subscriptionGroups.some(({name}) => name === PRODUCTS.portal) &&
				subscriptionGroups.some(
					({name}) =>
						name === PRODUCTS.dxp || name === PRODUCTS.dxp_cloud
				)))
	) {
		webContents.push('WEB-CONTENT-ACTION-09');
	}

	return webContents;
}
