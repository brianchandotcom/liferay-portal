/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SiteTemplate} from '../types/SiteTemplate';
import ApiHelper, {RequestResult} from './ApiHelper';

const SITE_TEMPLATES_URL = '/o/headless-admin-site/v1.0/site-templates';

async function getActiveSiteTemplates() {
	return await ApiHelper.get<{items: SiteTemplate[]}>(
		`${SITE_TEMPLATES_URL}?active=true`
	);
}

async function getConnectedSiteTemplates(externalReferenceCode: string) {
	const filter = encodeURIComponent(
		`siteExternalReferenceCode eq '${externalReferenceCode}'`
	);

	return await ApiHelper.get<{items: SiteTemplate[]}>(
		`${SITE_TEMPLATES_URL}?active=true&filter=${filter}`
	);
}

// TODO LPD-82494: replace with the real call once the headless write endpoint
// lands. The endpoint shape (sub-resource on the asset library vs PATCH on
// SiteTemplate.siteExternalReferenceCode) is the open question on LPD-82494.

async function connectSiteTemplateToSpace(
	_siteTemplateId: string,
	_spaceExternalReferenceCode: string
): Promise<RequestResult<SiteTemplate>> {
	return {
		data: null,
		error: 'Connecting site templates is not yet supported.',
	};
}

// TODO LPD-82494: replace with the real call once the headless write endpoint
// lands.

async function disconnectSiteTemplateFromSpace(
	_siteTemplateId: string
): Promise<RequestResult<null>> {
	return {
		data: null,
		error: 'Disconnecting site templates is not yet supported.',
	};
}

export default {
	connectSiteTemplateToSpace,
	disconnectSiteTemplateFromSpace,
	getActiveSiteTemplates,
	getConnectedSiteTemplates,
};
