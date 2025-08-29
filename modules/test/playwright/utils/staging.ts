/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {DataApiHelpers} from '../helpers/ApiHelpers';
import {reloadUntilVisible} from './reloadUntilVisible';

export async function enableLocalStaging(
	apiHelpers: DataApiHelpers,
	page: Page,
	site: any,
	parameters?: any
) {
	await page.goto(`/web${site.friendlyUrlPath}`);

	await apiHelpers.jsonWebServicesStaging.enableLocalStaging({
		groupId: site.id,
		...parameters,
	});

	await reloadUntilVisible({
		myLocator: page
			.getByLabel('Control Menu')
			.getByRole('link', {name: 'Staging'}),
		page,
	});
}
