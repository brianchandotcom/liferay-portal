/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {remoteDataApiHelpersTest} from '../../fixtures/remoteDataApiHelpersTest';
import {remoteStagingApiHelperTest} from '../../fixtures/remoteStagingApiHelpersTest';
import {uiElementsPageTest} from '../../fixtures/uiElementsTest';
import {webContentDisplayPageTest} from '../../fixtures/webContentDisplayPageTest';
import {performLoginViaApi} from '../../utils/performLogin';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';
import {remoteStagingPagesTest} from './fixtures/remoteStagingPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	featureFlagsTest({
		'LPD-39304': {enabled: true},
	}),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	pagesPagesTest,
	productMenuPageTest,
	remoteDataApiHelpersTest,
	remoteStagingApiHelperTest,
	remoteStagingPagesTest,
	uiElementsPageTest,
	webContentDisplayPageTest
);

test(
	'Check Web contents can be published via their portlet using remote staging',
	{tag: '@LPS-81950'},
	async ({
		apiHelpers,
		page,
		pageEditorPage,
		remoteStagingApiHelper,
		remoteStagingPage,
		uiElementsPage,
		webContentDisplayPage,
	}) => {
		const site = await apiHelpers.headlessSite.createSite({
			name: 'Site Name',
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {
				type: 'portlet',
			},
			title: 'Staging Test Page',
		});

		const remoteUrl = remoteStagingApiHelper.baseUrl.substring(
			0,
			remoteStagingApiHelper.baseUrl.length - 3
		);

		await performLoginViaApi({
			apiHelpers: remoteStagingApiHelper,
			loginUrl: remoteUrl,
			page,
			screenName: 'test',
		});

		const remoteSite = await remoteStagingApiHelper.headlessSite.createSite(
			{
				name: 'Remote Site Name',
			}
		);

		await performLoginViaApi({
			page,
			screenName: 'test',
		});

		await apiHelpers.jsonWebServicesStaging.enableRemoteStaging({
			groupId: site.id,
			remoteGroupId: remoteSite.id,
			remotePort: 9080,
		});

		await remoteStagingPage.publishToLive({
			layoutFriendlyURL: layout.friendlyURL,
			siteFriendlyUrl: site.friendlyUrlPath,
		});

		const basicWebContentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		await apiHelpers.jsonWebServicesJournal.addWebContent({
			content: 'WC WebContent Content',
			ddmStructureId: basicWebContentStructureId,
			groupId: site.id,
			titleMap: {en_US: 'WC WebContent Title'},
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);
		await uiElementsPage.addButton.click();
		await pageEditorPage.addWidgetToWidgetPageTemplate(
			'Content Management',
			'Web Content Display'
		);
		await webContentDisplayPage.addWebContentWithDisplay({
			pageType: 'widget',
			webContentName: 'WC WebContent Title',
		});

		await remoteStagingPage.publishToLive({
			layoutFriendlyURL: layout.friendlyURL,
			siteFriendlyUrl: site.friendlyUrlPath,
		});

		await performLoginViaApi({
			apiHelpers: remoteStagingApiHelper,
			loginUrl: remoteUrl,
			page,
			screenName: 'test',
		});

		await page.goto(`${remoteUrl}/web${remoteSite.friendlyUrlPath}`);

		expect(
			page.getByRole('heading', {name: 'WC WebContent Title'})
		).toBeVisible();
		expect(page.getByText('WC WebContent Content')).toBeVisible();
	}
);
