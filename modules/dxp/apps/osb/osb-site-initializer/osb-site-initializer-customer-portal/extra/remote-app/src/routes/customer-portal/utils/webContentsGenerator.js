import {
	PRODUCTS,
	SLA_NAMES,
	WEB_CONTENTS,
	WEB_CONTENTS_BY_LIFERAY_VERSION,
} from './constants';

const getDXPVersionWebContent = (dxpVersion) => {
	if (dxpVersion) {
		return WEB_CONTENTS_BY_LIFERAY_VERSION[dxpVersion];
	}

	return WEB_CONTENTS_BY_LIFERAY_VERSION['7.4'];
};

export function getWebContents(slaCurrentVersionAndProducts) {
	const [slaCurrent, dxpVersion, ...products] = slaCurrentVersionAndProducts;
	const webContents = [];

	if (
		products.some(
			(product) =>
				product === PRODUCTS.dxp ||
				product === PRODUCTS.portal ||
				product === PRODUCTS.commerce
		) ||
		!products.some(
			(product) => product === PRODUCTS.partnership || PRODUCTS.dxp_cloud
		)
	) {
		webContents.push(WEB_CONTENTS['WEB-CONTENT-ACTION-01']);
	}
	if (
		!products.some((product) => product === PRODUCTS.partnership) &&
		slaCurrent !== SLA_NAMES.limited_subscription
	) {
		webContents.push(WEB_CONTENTS['WEB-CONTENT-ACTION-02']);
	}
	if (
		products.some(
			(product) =>
				product === PRODUCTS.dxp || product === PRODUCTS.dxp_cloud
		)
	) {
		webContents.push(WEB_CONTENTS['WEB-CONTENT-ACTION-03']);
	}
	if (
		products.some(
			(product) =>
				product === PRODUCTS.dxp || product === PRODUCTS.dxp_cloud
		)
	) {
		webContents.push(getDXPVersionWebContent(dxpVersion));
	}
	if (
		!products.some((product) => product === PRODUCTS.analytics_cloud) &&
		(!products.some((product) => product === PRODUCTS.portal) ||
			products.some(
				(product) =>
					product === PRODUCTS.dxp || product === PRODUCTS.dxp_cloud
			))
	) {
		webContents.push(WEB_CONTENTS['WEB-CONTENT-ACTION-09']);
	}

	return webContents;
}
