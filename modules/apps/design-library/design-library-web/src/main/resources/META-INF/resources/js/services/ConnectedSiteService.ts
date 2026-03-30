/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEFAULT_FETCH_HEADERS} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';

import {Site} from '../types';

async function connectSiteToDesignLibrary(
	externalReferenceCode: string,
	siteExternalReferenceCode: string
): Promise<Site> {
	const response = await fetch(
		`/o/headless-asset-library/v1.0/asset-libraries/${externalReferenceCode}/connected-sites/${siteExternalReferenceCode}`,
		{
			body: JSON.stringify({
				searchable: true,
			}),
			headers: DEFAULT_FETCH_HEADERS,
			method: 'PUT',
		}
	);

	if (!response.ok) {
		const errorData = await response.json().catch(() => {
			return null;
		});

		throw errorData;
	}

	return await response.json();
}

async function disconnectSiteFromDesignLibrary(
	externalReferenceCode: string,
	siteExternalReferenceCode: string
): Promise<void> {
	const response = await fetch(
		`/o/headless-asset-library/v1.0/asset-libraries/${externalReferenceCode}/connected-sites/${siteExternalReferenceCode}`,
		{
			headers: DEFAULT_FETCH_HEADERS,
			method: 'DELETE',
		}
	);

	if (!response.ok) {
		const errorData = await response.json().catch(() => {
			return null;
		});

		throw errorData;
	}
}

async function getConnectedSitesFromDesignLibrary(
	externalReferenceCode: string
): Promise<{items: Site[]}> {
	const response = await fetch(
		`/o/headless-asset-library/v1.0/asset-libraries/${externalReferenceCode}/connected-sites`
	);

	if (!response.ok) {
		const errorData = await response.json().catch(() => {
			return null;
		});

		throw errorData;
	}

	return await response.json();
}

export default {
	connectSiteToDesignLibrary,
	disconnectSiteFromDesignLibrary,
	getConnectedSitesFromDesignLibrary,
};
