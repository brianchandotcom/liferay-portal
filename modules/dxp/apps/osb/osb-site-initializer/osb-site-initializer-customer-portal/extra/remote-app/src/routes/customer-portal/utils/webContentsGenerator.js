import {
	PRODUCTS,
	SLA_NAMES,
	WEB_CONTENTS,
	WEB_CONTENTS_BY_LIFERAY_VERSION,
} from './constants';

export function getWebContents(slaCurrentVersionAndProducts) {
	const [slaCurrent, dxpVersion, ...products] = slaCurrentVersionAndProducts;
	const webContents = [];

	if (
		products.includes(PRODUCTS.dxp_cloud) ||
		products.includes(PRODUCTS.portal) ||
		products.includes(PRODUCTS.commerce) ||
		(!products.includes(PRODUCTS.partnership) &&
			slaCurrent !== SLA_NAMES.limited_subscription)
	) {
		webContents.push(WEB_CONTENTS['WEB-CONTENT-ACTION-01']);
	}

	if (
		!products.includes(PRODUCTS.partnership) &&
		slaCurrent !== SLA_NAMES.limited_subscription
	) {
		webContents.push(WEB_CONTENTS['WEB-CONTENT-ACTION-02']);
	}

	if (
		!products.includes(PRODUCTS.dxp) ||
		!products.includes(PRODUCTS.dxp_cloud)
	) {
		webContents.push(WEB_CONTENTS['WEB-CONTENT-ACTION-03']);
	}

	if (dxpVersion) {
		webContents.push(getDXPVersionWebContent(dxpVersion));
	}

	if (!products.includes(PRODUCTS.analytics_cloud)) {
		webContents.push(WEB_CONTENTS['WEB-CONTENT-ACTION-09']);
	}

	return webContents;
}

const getDXPVersionWebContent = (dxpVersion) =>
	WEB_CONTENTS_BY_LIFERAY_VERSION[dxpVersion];
