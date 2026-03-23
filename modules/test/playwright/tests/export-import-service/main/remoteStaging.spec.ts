/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {dataRemoteApiHelpersTest} from '../../../fixtures/dataRemoteApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {remotePageTest} from '../../../fixtures/remotePageTest';
import {uiElementsPageTest} from '../../../fixtures/uiElementsTest';
import {webContentDisplayPageTest} from '../../../fixtures/webContentDisplayPageTest';
import getGlobalSiteId from '../../../utils/getGlobalSiteId';
import getRandomString from '../../../utils/getRandomString';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {reloadUntilVisible} from '../../../utils/reloadUntilVisible';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {pagesPagesTest} from '../../layout-admin-web/main/fixtures/pagesPagesTest';
import {remoteStagingPagesTest} from './fixtures/remoteStagingPagesTest';
import {safeTeardown} from './utils/safeTeardown';

const remotePort = '9080';
const remotePage = remotePageTest(remotePort);

export const test = mergeTests(
	dataApiHelpersTest,
	dataRemoteApiHelpersTest(remotePage, remotePort),
	loginTest(),
	featureFlagsTest({
		'LPD-39304': {enabled: true},
	}),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	pagesPagesTest,
	pageViewModePagesTest,
	productMenuPageTest,
	remoteStagingPagesTest,
	uiElementsPageTest,
	webContentDisplayPageTest
);

test(
	'Check Web contents can be published via their portlet using remote staging',
	{tag: '@LPS-81950'},
	async ({
		apiHelpers,
		pageEditorPage,
		remoteApiHelpers,
		remotePage,
		remoteStagingPage,
		uiElementsPage,
		webContentDisplayPage,
		widgetPagePage,
	}) => {
		test.slow();

		const site = await apiHelpers.headlessSite.createSite({
			name: 'Site Name',
		});

		apiHelpers.data.push({id: site.externalReferenceCode, type: 'site'});

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {
				type: 'portlet',
			},
			title: 'Staging Test Page',
		});

		const remoteSite = await remoteApiHelpers.headlessSite.createSite({
			name: 'Remote Site Name',
		});

		remoteApiHelpers.data.push({
			id: remoteSite.externalReferenceCode,
			type: 'site',
		});

		await apiHelpers.jsonWebServicesStaging.enableRemoteStaging({
			groupId: site.id,
			remoteGroupId: remoteSite.id,
			remotePort,
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
		await widgetPagePage.addPortlet(
			'Web Content Display',
			'Content Management'
		);
		await webContentDisplayPage.addWebContentWithDisplay({
			pageType: 'widget',
			webContentName: 'WC WebContent Title',
		});

		await remoteStagingPage.publishToLive({
			layoutFriendlyURL: layout.friendlyURL,
			siteFriendlyUrl: site.friendlyUrlPath,
		});

		const remoteUrl = remoteApiHelpers.baseUrl.substring(
			0,
			remoteApiHelpers.baseUrl.length - 3
		);

		await remotePage.goto(`${remoteUrl}/web${remoteSite.friendlyUrlPath}`);

		await reloadUntilVisible({
			myLocator: remotePage.getByRole('heading', {
				name: 'WC WebContent Title',
			}),
			page: remotePage,
		});

		await expect(
			remotePage.getByRole('heading', {name: 'WC WebContent Title'})
		).toBeVisible();
		await expect(
			remotePage.getByText('WC WebContent Content')
		).toBeVisible();
	}
);

test(
	'Can publish vocabulary deletion from the Global Site using remote staging',
	{tag: ['@LPS-89981', '@LPS-88298']},
	async (
		{
			apiHelpers,
			configStagingPage,
			page,
			portletStagingPage,
			remoteApiHelpers,
			remotePage,
		},
		testInfo
	) => {
		const vocabularyName = `Vocabulary: ${getRandomString()}`;
		let globalSiteId;
		let vocabularyId;

		await test.step('Setup remote staging', async () => {
			globalSiteId = await getGlobalSiteId(apiHelpers);
			const remoteGlobalSiteId = await getGlobalSiteId(remoteApiHelpers);

			await apiHelpers.jsonWebServicesStaging.enableRemoteStaging({
				groupId: globalSiteId,
				remoteGroupId: remoteGlobalSiteId,
				remotePort,
			});
		});

		try {
			await test.step('Create vocabulary', async () => {
				const {id} =
					await apiHelpers.headlessAdminTaxonomy.postSiteTaxonomyVocabulary(
						{
							name: vocabularyName,
							siteId: globalSiteId,
						}
					);
				apiHelpers.data.push({
					id,
					type: 'taxonomyVocabulary',
				});

				vocabularyId = id;
			});

			await test.step('Publish vocabulary and verify on remote site', async () => {
				await page.goto(`/group/global${PORTLET_URLS.categoriesAdmin}`);
				await portletStagingPage.openIframe();
				await portletStagingPage.publishToLive();

				await remotePage.goto(
					`/group/global${PORTLET_URLS.categoriesAdmin}`
				);
				await expect(
					remotePage.getByRole('menuitem', {name: vocabularyName})
				).toBeVisible();
			});

			await test.step('Delete vocabulary, publish deletion and verify removal on remote site', async () => {
				await apiHelpers.headlessAdminTaxonomy.deleteTaxonomyVocabulary(
					vocabularyId
				);

				await portletStagingPage.openIframe();

				const contentCheckbox =
					portletStagingPage.publishStagingIframe.getByLabel(
						/Content\s+\d+\s+Deletions/i
					);
				await expect(async () => {
					await expect(contentCheckbox).not.toBeChecked();
				}).toPass();
				await contentCheckbox.check();

				await portletStagingPage.publishStagingIframe
					.getByLabel('Replicate Individual')
					.check();

				await portletStagingPage.publishToLive();

				await remotePage.goto(
					`/group/global${PORTLET_URLS.categoriesAdmin}`
				);
				await expect(
					remotePage.getByRole('menuitem', {name: vocabularyName})
				).toBeHidden();
			});
		}
		finally {
			await test.step('Teardown: Disabling staging on global site', async () => {
				await safeTeardown(
					async () =>
						await configStagingPage.disableStaging('/global'),
					testInfo.timeout * 0.5
				);
			});
		}
	}
);
