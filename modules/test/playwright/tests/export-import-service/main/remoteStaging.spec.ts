/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {dataRemoteApiHelpersTest} from '../../../fixtures/dataRemoteApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
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
		widgetPagePage
	}) => {
		test.slow();

		const layouts: Array<Layout> = [];
		let remoteSite: Site;
		let remoteUrl: string;
		let site: Site;

			await test.step('Setup remote staging and pages', async () => {
				site = await apiHelpers.headlessSite.createSite({
					name: `site-${getRandomString()}`,
				});

				apiHelpers.data.push({id: site.id, type: 'site'});

				remoteSite = await remoteApiHelpers.headlessSite.createSite({
					name: site.name,
				});

				remoteApiHelpers.data.push({id: remoteSite.id, type: 'site'});

				remoteUrl = remoteApiHelpers.baseUrl.substring(
					0,
					remoteApiHelpers.baseUrl.length - 3
				);

				await apiHelpers.jsonWebServicesStaging.enableRemoteStaging({
					groupId: site.id,
					remoteGroupId: remoteSite.id,
					remotePort,
				});

				for (const i of [1, 2, 3]) {
					let layout =
						await apiHelpers.jsonWebServicesLayout.addLayout({
							groupId: site.id,
							title: `Page ${i}`,
						});

					layouts.push(layout);

					for (const j of [1, 2]) {
						layout =
							await apiHelpers.jsonWebServicesLayout.addLayout({
								groupId: site.id,
								parentLayoutId: layout.layoutId,
								title: `Page ${i}${j}`,
							});

						layouts.push(layout);

						if (i === 1 && j === 1) {
							layout =
								await apiHelpers.jsonWebServicesLayout.addLayout(
									{
										groupId: site.id,
										parentLayoutId: layout.layoutId,
										title: 'Page 111',
									}
								);
							layouts.push(layout);
						}
					}
				}
			});

			const pageNumbers = [1, 11, 111, 12, 2, 21, 22, 3, 31, 32];
			await test.step('Setup pages with web content display', async () => {
				for (const layout of layouts) {
					await pageEditorPage.goto(layout, site.friendlyUrlPath);

					await widgetPagePage.addPortlet('Web Content Display');
					await widgetPagePage.addPortlet('Web Content Display');
				}
			});
			const webContentTitle = getRandomString();
			await test.step('Create structures, templates and web contents', async () => {
				const fields: Array<any> = [];

				for (const num of pageNumbers) {
					fields.push({name: `Openpage${num}`, repeatable: false});
					fields.push({name: `URL${num}`, repeatable: false});
				}

				const structureName = getRandomString();
				const dataDefinition = getDataStructureDefinition({
					defaultLanguageId: 'en_US',
					fields,
					name: structureName,
				});
				const structure = await apiHelpers.dataEngine.createStructure(
					site.id,
					dataDefinition
				);

				let i = 0;
				const contentFields: Array<any> = [];
				for (const layout of layouts) {
					contentFields.push({
						name: `Openpage${pageNumbers[i]}`,
						value: layout.nameCurrentValue,
					});
					contentFields.push({
						name: `URL${pageNumbers[i]}`,
						value:
							`/web${site.friendlyUrlPath}` + layout.friendlyURL,
					});
					i++;
				}
				const templateName = 'template1';

				const templateScript = pageNumbers
					.map((number) => {
						return `<p><a href="\${URL${number}.getData()}">\${Openpage${number}.getData()}</a></p>`;
					})
					.join('\n');

				await journalEditTemplatePage.goto(site.friendlyUrlPath);
				await journalEditTemplatePage.selectStructure(structureName);
				await journalEditTemplatePage.editTemplate(
					templateName,
					templateScript
				);
				await journalEditTemplatePage.saveTemplate();
				await journalEditTemplatePage.selectTemplateToEdit(
					templateName
				);

				const templateKey =
					await journalEditTemplatePage.getDDMTemplateKey();

				await apiHelpers.jsonWebServicesJournal.addWebContent({
					contentFields,
					ddmStructureId: structure.id,
					ddmTemplateKey: templateKey,
					groupId: site.id,
					titleMap: {en_US: webContentTitle},
				});

				const structureName2 = getRandomString();
				const dataDefinition2 = getDataStructureDefinition({
					defaultLanguageId: 'en_US',
					fields: [
						{name: 'Content1', repeatable: false},
						{name: 'Content2', repeatable: false},
					],
					name: structureName2,
				});

				const structure2 = await apiHelpers.dataEngine.createStructure(
					site.id,
					dataDefinition2
				);

				const templateName2 = 'template2';
				const templateScript2 =
					'<h1>${Content1.getData()}</h1>\n' +
					'<p>${Content2.getData()}</p>';

				await journalEditTemplatePage.goto(site.friendlyUrlPath);
				await journalEditTemplatePage.selectStructure(structureName2);
				await journalEditTemplatePage.editTemplate(
					templateName2,
					templateScript2
				);
				await journalEditTemplatePage.saveTemplate();
				await journalEditTemplatePage.selectTemplateToEdit(
					templateName2
				);

				const templateKey2 =
					await journalEditTemplatePage.getDDMTemplateKey();

				await webContentDisplayPage.gotoWebContentAdmin(site.name);

				for (const num of pageNumbers) {
					await apiHelpers.jsonWebServicesJournal.addWebContent({
						contentFields: [
							{name: `Content1`, value: `Title-${num}`},
							{
								name: `Content2`,
								value: `Text Content-${num}`,
							},
						],
						ddmStructureId: structure2.id,
						ddmTemplateKey: templateKey2,
						groupId: site.id,
						titleMap: {en_US: `Title-${num}`},
					});

					await reloadUntilVisible({
						myLocator: page.getByRole('link', {
							name: `Title-${num}`,
						}),
						page,
					});
				}
			});

			let i = 0;
			await test.step('Add web content to pages', async () => {
				for (const layout of layouts) {
					await pageEditorPage.goto(layout, site.friendlyUrlPath);

					await expect(async () => {
						await page.reload();
						await webContentDisplayPage.addWebContentWithDisplay({
							customLocator: await page
								.locator('#wrapper, [id^="portlet-topper-toolbar_com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_"]:visible')
								.nth(1),
							pageType: 'content',
							waitAfterAddingWebcontent: true,
							webContentName: webContentTitle,
						});

						await expect( 
							page
								.getByText(webContentTitle, {exact: true})).toBeVisible({timeout: 1500});
						}).toPass({
							intervals: [1_000, 2_000],
							timeout: 120_000
						});
				
						await expect(async () => {
							await page.reload();
							await webContentDisplayPage.addWebContentWithDisplay({
								customLocator: await page
									.locator('#wrapper, [id^="portlet-topper-toolbar_com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_"]:visible')
									.nth(2),
								pageType: 'content',
								waitAfterAddingWebcontent: true,
								webContentName: `Title-${pageNumbers[i]}`,
							});


							await expect(page
								.getByText(`Title-${pageNumbers[i]}`, {
									exact: true,
								})
								.first()).toBeVisible({timeout: 1500});
						}).toPass({
							intervals: [1_000, 2_000],
							timeout: 120_000
						});
					i++;
				}
			});

			await test.step('Publish to live and verify on remote site', async () => {
				await remoteStagingPage.publishToLive({
					layoutFriendlyURL: layouts[0].friendlyURL,
					siteFriendlyUrl: site.friendlyUrlPath,
				});
			
				await remotePage.goto(
					`${remoteUrl}/web${remoteSite.friendlyUrlPath}${layouts[0].friendlyURL}`
				);

				for (const num of [111, 21, 3]) {
					await remotePage
						.getByRole('link', {exact: true, name: `Page ${num}`})
						.click();
					await remotePage.waitForLoadState('domcontentloaded');

					await expect(
						remotePage
							.locator('h1')
							.filter({hasText: `Title-${num}`})
					).toBeVisible();

					await expect(remotePage.url()).toContain(
						`/web/${site.name}/page-${num}`
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
