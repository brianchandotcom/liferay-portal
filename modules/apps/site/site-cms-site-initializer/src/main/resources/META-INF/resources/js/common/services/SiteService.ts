/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Site} from '../types/Site';
import ApiHelper from './ApiHelper';

async function connectSiteToSpace(
	groupId: string,
	siteId: string,
	searchable?: string
) {
	return await ApiHelper.put<Site>(
		`/o/headless-asset-library/v1.0/asset-libraries/${groupId}/connected-sites/${siteId}`,
		{
			searchable: searchable ? searchable : 'true',
		}
	);
}

async function disconnectSiteFromSpace(groupId: string, siteId: string) {
	return await ApiHelper.delete(
		`/o/headless-asset-library/v1.0/asset-libraries/${groupId}/connected-sites/${siteId}`
	);
}

async function getConnectedSitesFromSpace(groupId: string) {
	return await ApiHelper.get<{items: Site[]}>(
		`/o/headless-asset-library/v1.0/asset-libraries/${groupId}/connected-sites`
	);
}

async function getAllSites() {
	return await ApiHelper.get<{items: Site[]}>(
		`/o/headless-site/v1.0/sites?active=true`
	);
}

export default {
	connectSiteToSpace,
	disconnectSiteFromSpace,
	getAllSites,
	getConnectedSitesFromSpace,
};
