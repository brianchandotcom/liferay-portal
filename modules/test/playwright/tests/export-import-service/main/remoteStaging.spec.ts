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
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {
	JournalContentPage,
	createLayoutHierarchy,
} from '../../../utils/createLayoutHierarchy';
import getGlobalSiteId from '../../../utils/getGlobalSiteId';
import getRandomString from '../../../utils/getRandomString';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {reloadUntilVisible} from '../../../utils/reloadUntilVisible';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import getDataStructureDefinition from '../../journal-web/main/utils/getDataStructureDefinition';
import {pagesPagesTest} from '../../layout-admin-web/main/fixtures/pagesPagesTest';
import {remoteStagingPagesTest} from './fixtures/remoteStagingPagesTest';
import {safeTeardown} from './utils/safeTeardown';

const remotePort = '9080';
const remotePage = remotePageTest(remotePort);

const test = mergeTests(
	dataApiHelpersTest,
	dataRemoteApiHelpersTest(remotePage, remotePort),
	loginTest(),
	featureFlagsTest({
		'LPD-39304': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	journalPagesTest,
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
	'Can publish web content with URL references to live via remote staging',
	{tag: '@LPS-159626'},
	async ({
		apiHelpers,
		journalEditTemplatePage,
		page,
		pageEditorPage,
		remoteApiHelpers,
		remotePage,
		remoteStagingPage,
		webContentDisplayPage,
	}) => {
		test.slow();

		let layouts: Array<JournalContentPage> = [];
		let remoteSite: Site;
		let site: Site;

		await test.step('Setup remote staging and pages', async () => {
			site = await apiHelpers.headlessSite.createSite({
				name: `site-${getRandomString()}`,
			});

			apiHelpers.data.push({
				id: site.externalReferenceCode,
				type: 'site',
			});

			remoteSite = await remoteApiHelpers.headlessSite.createSite({
				name: site.name,
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
		});

		const WC_DISPLAY =
			'com_liferay_journal_content_web_portlet_JournalContentPortlet';

		const widgets = [WC_DISPLAY, WC_DISPLAY];

		await test.step('Create a hierarchy of pages with Web Content Display portlets on the local site', async () => {
			layouts = await createLayoutHierarchy({
				apiHelpers,
				pageNodes: [
					{
						children: [
							{
								children: [
									{
										pageNumber: '111',
										widgets,
									},
								],
								pageNumber: '11',
								widgets,
							},
							{
								pageNumber: '12',
								widgets,
							},
						],
						pageNumber: '1',
						widgets,
					},
					{
						children: [
							{
								pageNumber: '21',
								widgets,
							},
							{
								pageNumber: '22',
								widgets,
							},
						],
						pageNumber: '2',
						widgets,
					},
					{
						children: [
							{
								pageNumber: '31',
								widgets,
							},
							{
								pageNumber: '32',
								widgets,
							},
						],
						pageNumber: '3',
						widgets,
					},
				],
				siteId: site.id,
			});
		});

		const webContentTitle = getRandomString();

		let structure: any;
		let templateKey: string;

		await test.step('Create a data structure and template for page links', async () => {
			const structureName = getRandomString();
			const fields: Array<any> = layouts.flatMap((layout) => [
				{name: `Openpage${layout.pageNumber}`, repeatable: false},
				{name: `URL${layout.pageNumber}`, repeatable: false},
			]);

			const dataDefinition = getDataStructureDefinition({
				defaultLanguageId: 'en_US',
				fields,
				name: structureName,
			});

			structure = await apiHelpers.dataEngine.createStructure(
				site.id,
				dataDefinition
			);

			const templateScript = layouts
				.map((layout) => {
					return `<p><a href="\${URL${layout.pageNumber}.getData()}">\${Openpage${layout.pageNumber}.getData()}</a></p>`;
				})
				.join('\n');
			const templateName = 'template1';

			await journalEditTemplatePage.goto(site.friendlyUrlPath);
			await journalEditTemplatePage.selectStructure(structureName);
			await journalEditTemplatePage.editTemplate(
				templateName,
				templateScript
			);
			await journalEditTemplatePage.saveTemplate();
			await journalEditTemplatePage.selectTemplateToEdit(templateName);

			templateKey = await journalEditTemplatePage.getDDMTemplateKey();
		});

		await test.step('Create a web content article with page links', async () => {
			const contentFields: Array<{name: string; value: string}> =
				layouts.flatMap((layout) => {
					return [
						{
							name: `Openpage${layout.pageNumber}`,
							value: layout.title,
						},
						{
							name: `URL${layout.pageNumber}`,
							value: `/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`,
						},
					];
				});

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				contentFields,
				ddmStructureId: structure.id,
				ddmTemplateKey: templateKey,
				groupId: site.id,
				titleMap: {en_US: webContentTitle},
			});
		});

		let structure2: any;
		let templateKey2: string;

		await test.step('Create a data structure and template for individual page content', async () => {
			const structureName2 = getRandomString();
			const dataDefinition2 = getDataStructureDefinition({
				defaultLanguageId: 'en_US',
				fields: [
					{name: 'Content1', repeatable: false},
					{name: 'Content2', repeatable: false},
				],
				name: structureName2,
			});

			structure2 = await apiHelpers.dataEngine.createStructure(
				site.id,
				dataDefinition2
			);

			await journalEditTemplatePage.goto(site.friendlyUrlPath);
			await journalEditTemplatePage.selectStructure(structureName2);

			const templateScript2 =
				'<h1>${Content1.getData()}</h1>\n' +
				'<p>${Content2.getData()}</p>';
			const templateName2 = 'template2';
			await journalEditTemplatePage.editTemplate(
				templateName2,
				templateScript2
			);
			await journalEditTemplatePage.saveTemplate();
			await journalEditTemplatePage.selectTemplateToEdit(templateName2);

			templateKey2 = await journalEditTemplatePage.getDDMTemplateKey();
		});

		await test.step('Create individual web content articles for each page', async () => {
			await webContentDisplayPage.gotoWebContentAdmin(site.name);

			for (const layout of layouts) {
				await apiHelpers.jsonWebServicesJournal.addWebContent({
					contentFields: [
						{name: `Content1`, value: layout.contentTitle},
						{
							name: `Content2`,
							value: `Text Content for ${layout.contentTitle}`,
						},
					],
					ddmStructureId: structure2.id,
					ddmTemplateKey: templateKey2,
					groupId: site.id,
					titleMap: {en_US: layout.contentTitle},
				});

				await reloadUntilVisible({
					myLocator: page.getByRole('link', {
						name: layout.contentTitle,
					}),
					page,
				});
			}
		});

		await test.step('Add web content articles to the display portlets on each page of the local site', async () => {
			for (const layout of layouts) {
				await pageEditorPage.goto(layout, site.friendlyUrlPath);

				await webContentDisplayPage.addWebContentWithDisplay({
					pageType: 'content',
					webContentName: webContentTitle,
				});

				await webContentDisplayPage.addWebContentWithDisplay({
					pageType: 'content',
					webContentName: layout.contentTitle,
				});

				await pageEditorPage.publishPage();
			}
		});
		await test.step('Publish to live and verify content and links on the remote site', async () => {
			await remoteStagingPage.publishToLive({
				layoutFriendlyURL: layouts[0].friendlyUrlPath,
				siteFriendlyUrl: site.friendlyUrlPath,
			});

			await remotePage.goto(
				`/web${remoteSite.friendlyUrlPath}${layouts[0].friendlyUrlPath}`
			);

			for (const contentTitle of ['Title-111', 'Title-21', 'Title-3']) {
				const layout = layouts.find(
					(l) => l.contentTitle === contentTitle
				);

				await clickAndExpectToBeVisible({
					target: remotePage.locator('h1', {
						hasText: layout.contentTitle,
					}),
					trigger: remotePage.getByRole('link', {
						exact: true,
						name: layout.title,
					}),
				});

				await expect(remotePage).toHaveURL(
					new RegExp(`/web/${site.name}${layout.friendlyUrlPath}`)
				);
			}
		});
	}
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
