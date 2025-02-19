/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest(),
	productMenuPageTest
);

test('can see corresponding elements at site level', async ({
	productMenuPage,
}) => {
	await productMenuPage.openProductMenuIfClosed();
	await productMenuPage.goToPublishingExport();
	await productMenuPage.page
		.getByRole('link', {name: 'Custom Export'})
		.click();

	await expect(
		productMenuPage.page.getByText('Comments, Ratings')
	).toBeVisible();
});