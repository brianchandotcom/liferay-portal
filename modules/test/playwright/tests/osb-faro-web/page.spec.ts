/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {assetPublisherPagesTest} from '../../fixtures/assetPublisherPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analytics-settings';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import {switchChannel} from './utils/channel';
import {createIndividuals, generateIndividual} from './utils/individuals';
import {Nanites, runNanites} from './utils/nanites';
import {
	ACPage,
	navigateTo,
	navigateToACPageViaURL,
	navigateToACWorkspace,
} from './utils/navigation';
import {createSitePage, navigateToSitePage} from './utils/portal';
import {
	addSegmentField,
	addStaticMember,
	createDynamicSegment,
	createStaticSegment,
	deleteSegment,
	saveSegment,
	selectOperator,
	setSegmentName,
} from './utils/segments';
import {CardSelectors, SegmentConditions} from './utils/selectors';
import {closeSessions} from './utils/sessions';
import {changeTimeFilter} from './utils/time-filter';
import {
	searchByTerm,
	viewNameNotPresentOnTableList,
	viewNameOnTableList,
} from './utils/utils';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	assetPublisherPagesTest,
	pageEditorPagesTest,
	featureFlagsTest({
		'LPD-39304': true,
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

const goToWithReferrer = async function ({
	page,
	referrer,
	url,
}: {
	page: Page;
	referrer: string;
	url: string;
}) {
	await page.goto(referrer);

	await page.evaluate((url) => {
		const aTag = document.createElement('a');

		aTag.href = url;

		aTag.click();
	}, url);
};

const randomString = getRandomString();

const channelName = 'My Property ' + randomString;
const pageTitle = 'My Page';
const siteName = 'My Site ' + randomString;

let channel;
let project;
let site;

test.beforeEach(async ({apiHelpers, page}) => {
	site = await apiHelpers.headlessSite.createSite({
		name: siteName,
	});

	await createSitePage({
		apiHelpers,
		pageTitle,
		siteName,
	});

	const result = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
		siteName,
	});

	channel = result.channel;
	project = result.project;
});

test.afterEach(async ({apiHelpers, page}) => {
	await test.step('Delete channel and delete site on the DXP side', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);

		await page.goto(liferayConfig.environment.baseUrl);

		await apiHelpers.headlessSite.deleteSite(String(site.id));
	});
});

test(
	'Assert clicking on a page in the pages lists navigates to the page profile',
	{
		tag: '@LRAC-8112 Legacy',
	},

	async ({page}) => {
		await test.step('Go to My Page', async () => {
			await navigateToSitePage({
				page,
				pageName: pageTitle,
				siteName,
			});
			await page.waitForTimeout(10000);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Access one of the pages on the list', async () => {
			await navigateTo({
				page,
				pageName: pageTitle,
			});
		});

		await test.step('Assert Page Profile', async () => {
			await expect(page.locator('h1.title')).toContainText(pageTitle);
			await expect(page.locator('h1.title')).toContainText(siteName);

			const cardsNames = [
				'Unique Visitors',
				'Views',
				'Bounce Rate',
				'Time On Page',
				'Entrances',
				'Exit Rate',
			];

			for (const card of cardsNames) {
				await expect(
					page.getByRole('button').getByText(card, {exact: true})
				).toBeVisible();
			}
		});

		await test.step('View the time filter of Visitors Behavior is Last 24 Hours', async () => {
			await expect(
				page
					.locator('[id="container\\.report\\.visitorsBehaviorCard"]')
					.getByRole('button', {name: 'Last 24 hours'})
			).toBeVisible();
		});

		const tabNames = ['Path', 'Known Individuals'];

		for (const tab of tabNames) {
			await test.step('Switch the tab', async () => {
				await navigateTo({
					page,
					pageName: tab,
				});
			});

			await test.step('Set the time filter to the last 30 days', async () => {
				await changeTimeFilter({
					page,
					timeFilterPeriod: 'Last 30 days',
				});
			});

			await test.step('Switch to Overview tab and view the time filter of Visitors Behavior is Last 24 Hours', async () => {
				await navigateTo({
					page,
					pageName: 'Overview',
				});

				await expect(
					page
						.locator(
							'[id="container\\.report\\.visitorsBehaviorCard"]'
						)
						.getByRole('button', {name: 'Last 24 hours'})
				).toBeVisible();
			});
		}
	}
);

test(
	'Assert page view accuracy between cards Visitor Behavior, Audience, and Page List number',
	{
		tag: '@LRAC-14813',
	},

	async ({apiHelpers, assetPublisherPage, page, pageEditorPage}) => {
		const blogTitle = 'My Blog ' + randomString;
		const blogPageTitle = 'My Blog Page ' + randomString;

		await test.step('Create a page with an Asset Publisher Widget and access to the configuration of the widget from the page editor', async () => {
			const widgetId = getRandomString();

			const widgetDefinition = getWidgetDefinition({
				id: widgetId,
				widgetName:
					'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: blogPageTitle,
			});

			await navigateToSitePage({
				page,
				pageName: blogPageTitle,
				siteName,
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.goToWidgetConfiguration(widgetId);

			await assetPublisherPage.changeAssetSelection('Dynamic');

			await page.keyboard.press('Escape');

			await pageEditorPage.publishPage();
		});

		await test.step('Create a Blog', async () => {
			await apiHelpers.headlessDelivery.postBlog(site.id, {
				headline: blogTitle,
			});
			await page.waitForTimeout(3000);
		});

		await test.step('Go to My Blog Page', async () => {
			await navigateToSitePage({
				page,
				pageName: blogPageTitle,
				siteName,
			});

			await page.locator('.asset-title').getByText(blogTitle).click();

			await page.waitForTimeout(3000);

			await page.reload();

			await page.waitForTimeout(10000);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});

			await page.reload();
		});

		await test.step('Access one of the pages on the list', async () => {
			await navigateTo({
				page,
				pageName: blogTitle,
			});
		});

		await test.step('View Unique Visitors metric value in Visitors Behavior card', async () => {
			await expect(
				page.getByRole('button', {name: 'Unique Visitors'})
			).toContainText('1');
		});

		await test.step('View Visitors metric value in Audience card', async () => {
			await expect(
				page
					.locator('[id="container\\.report\\.audienceCard"]')
					.getByText('1')
					.nth(1)
			).toBeVisible();
		});
	}
);

test(
	'Assert the pages list shows a list of pages',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const pageTitle1 = 'My Page 1';
		const pageTitle2 = 'My Page 2';

		await createSitePage({
			apiHelpers,
			pageTitle: pageTitle1,
			siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle: pageTitle2,
			siteName,
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		const pageTitle3 = 'My Page';

		await test.step('Sign in with the new user to visit the site pages', async () => {
			await performLogout(page);
			await performLogin(page, user.alternateName);

			await navigateToSitePage({
				page,
				pageName: pageTitle3,
				siteName,
			});
			await page.reload();
			await page.waitForTimeout(10000);
		});

		await test.step('Go to My Page 2', async () => {
			await page.getByText(pageTitle1, {exact: true}).click();
			await page.waitForTimeout(10000);
		});

		await test.step('Go to My Page 3', async () => {
			await page.getByText(pageTitle2, {exact: true}).click();
			await page.waitForTimeout(10000);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Assert the page list', async () => {
			await expect(
				page.getByRole('link', {name: `${pageTitle3} - ${siteName}`})
			).toBeVisible();
			await expect(
				page.getByRole('link', {name: `${pageTitle1} - ${siteName}`})
			).toBeVisible();
			await expect(
				page.getByRole('link', {name: `${pageTitle2} - ${siteName}`})
			).toBeVisible();
		});
	}
);

test(
	'Check that the Dynamic Segment does not continue to appear in the audience card after the segment is deleted',
	{
		tag: '@LPD-27586',
	},
	async ({apiHelpers, page}) => {
		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
		});

		const date2 = new Date();
		date2.setDate(date2.getDate() - 5);

		await test.step('Create an event for the individual to appear in periods different than the Last 24 hours in AC', async () => {
			const pageDaily = individuals.map((individual) => ({
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date2.toISOString(),
				title: pageTitle,
				userId: individual.id,
				views: 1,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createPagesDaily(pageDaily);
		});

		await test.step('Create Individual Session in periods different than the Last 24 hours in AC', async () => {
			const sessions = individuals.map((individual) => ({
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date2.toISOString(),
				sessionStart: date2.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
		});

		await test.step('Go to Segments', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.segmentPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		const dynamicSegmentName = 'Test Dynamic Segment';

		await test.step('Create dynamic segment', async () => {
			await createDynamicSegment(page);

			await addSegmentField({
				criterionName: 'email',
				criterionType: 'Individual Attributes',
				page,
			});

			await selectOperator({
				operator: 'is known',
				operatorField: SegmentConditions.criteriaCondition,
				page,
			});

			await setSegmentName({
				page,
				segmentName: dynamicSegmentName,
			});

			await saveSegment(page);
		});

		await test.step('Go to Segments', async () => {
			await navigateTo({
				page,
				pageName: 'Segments',
			});
		});

		const staticSegmentName = 'Test Static Segment';

		await test.step('Create static segment', async () => {
			await createStaticSegment(page);

			await setSegmentName({
				page,
				segmentName: staticSegmentName,
			});

			await addStaticMember({
				memberNames: individualName,
				page,
			});

			await saveSegment(page);
		});

		await test.step('Run the Segment Nanite', async () => {
			await runNanites({
				apiHelpers,
				naniteNames: [Nanites.UpdateMembershipsNanite],
				page,
			});
		});

		await test.step('Go to Sites > Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Sites',
			});

			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Access one of the pages on the list', async () => {
			await navigateTo({
				page,
				pageName: pageTitle,
			});
		});

		const segmentsName = [dynamicSegmentName, staticSegmentName];

		await test.step('Check that the created segment appears on the Audience card', async () => {
			for (const itemName of segmentsName) {
				await expect(page.getByText(itemName)).toBeVisible({
					timeout: 100 * 1000,
				});
			}
		});

		await test.step('Change the time filter of the Audience card to Last 24 hours', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Audience,
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Check that the created segment appears on the Audience card', async () => {
			for (const itemName of segmentsName) {
				await expect(page.getByText(itemName)).toBeVisible({
					timeout: 100 * 1000,
				});
			}
		});

		await test.step('Go to Segments', async () => {
			await navigateTo({
				page,
				pageName: 'Segments',
			});
		});

		await test.step('Delete the segments', async () => {
			await deleteSegment({
				page,
				segmentName: dynamicSegmentName,
			});

			await deleteSegment({
				page,
				segmentName: staticSegmentName,
			});
		});

		await test.step('Reload the page to clear the Audience card cache', async () => {
			await page.reload();
		});

		await test.step('Go to Sites > Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Sites',
			});

			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Access one of the pages on the list', async () => {
			await navigateTo({
				page,
				pageName: pageTitle,
			});
		});

		await test.step('Check that no segments appear on the Audience card', async () => {
			await expect(
				page.locator('.audience-report-chart-bar li')
			).toBeHidden();
		});

		await test.step('Change the time filter of the Audience card to Last 24 hours', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Audience,
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Check that no segments appear on the Audience card', async () => {
			await expect(
				page.locator('.audience-report-chart-bar li')
			).toBeHidden();
		});
	}
);

test(
	'Create the page name with special characters makes the correct name appear in the path part of AC pages',
	{
		tag: '@LRAC-8988',
	},

	async ({apiHelpers, page}) => {
		const pageTitle = 'Snúið Vinsælar þú';
		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		await test.step('Go to Snúið Vinsælar þú Page', async () => {
			await navigateToSitePage({
				page,
				pageName: pageTitle,
				siteName,
			});
			await page.waitForTimeout(10000);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Access one of the pages on the list > Go to Path Tab', async () => {
			await navigateTo({
				page,
				pageName: pageTitle,
			});
			await navigateTo({
				page,
				pageName: 'Path',
			});
		});

		await test.step('Check that Snúið Vinsælar þú Page appear as referral page', async () => {
			await expect(
				page.getByText('Snúið Vinsælar ...', {exact: true}).first()
			).toBeVisible();
		});
	}
);

test(
	'Page Profile known individuals list shows individuals who have viewed the page',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const firstIndividual = 'user1';
		const secondIndividual = 'user2';
		const thirdIndividual = 'user3';

		const individuals = [
			generateIndividual({
				name: firstIndividual,
			}),
			generateIndividual({
				name: secondIndividual,
			}),
			generateIndividual({
				name: thirdIndividual,
			}),
		];

		await test.step('Create 3 Individuals directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create events for two of the individuals to appear within the Last 24 hours period in AC', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createEvents(
				individuals.slice(0, 2).map((individual) => ({
					applicationId: 'Page',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date1.toISOString(),
					eventId: 'pageViewed',
					title: 'Liferay',
					userId: individual.id,
				}))
			);
		});

		await test.step('Create events for one of the individuals to appear in periods different than the Last 24 hours in AC', async () => {
			const date2 = new Date();
			date2.setDate(date2.getDate() - 5);

			await apiHelpers.jsonWebServicesOSBAsah.createPagesDaily(
				individuals.slice(2, 3).map((individual) => ({
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date2.toISOString(),
					title: 'Liferay',
					userId: individual.id,
					views: 1,
				}))
			);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Access one of the pages on the list > Go to Known Individuals Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Liferay',
			});
			await navigateTo({
				page,
				pageName: 'Known Individuals',
			});
		});

		const individualPresentIn30Days = [thirdIndividual];

		await test.step('Check that User3 User3 is appearing in the list', async () => {
			await viewNameOnTableList({
				itemNames: individualPresentIn30Days,
				page,
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		const individualsPresentIn24Hours = [firstIndividual, secondIndividual];

		await test.step('Check that User1 User1 and User2 User2 are appearing in the list', async () => {
			await viewNameOnTableList({
				itemNames: individualsPresentIn24Hours,
				page,
			});
		});

		await test.step('Check that User3 User3 is appearing in the list', async () => {
			await viewNameNotPresentOnTableList({
				itemNames: individualPresentIn30Days,
				page,
			});
		});
	}
);

test(
	'Page profile views by technology shows which browsers are being used',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		await test.step('Go to My Page', async () => {
			await navigateToSitePage({
				page,
				pageName: pageTitle,
				siteName,
			});
			await page.waitForTimeout(3000);

			await page.reload();

			await page.waitForTimeout(10000);

			await closeSessions(apiHelpers, page);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Access one of the pages on the list', async () => {
			await navigateTo({
				page,
				pageName: pageTitle,
			});
		});

		await test.step('View Technology Browsers Metrics', async () => {
			await page
				.getByText('Views by Technology')
				.scrollIntoViewIfNeeded();

			await page.getByRole('button', {name: 'Browsers'}).click();

			await expect(page.getByText('Views by Technology')).toBeVisible();

			await expect(page.getByText('Chrome')).toBeVisible();

			await expect(page.locator('.legend-percentage')).toContainText(
				'100%'
			);
		});
	}
);

test(
	'Path analysis shows the outside pages that were interacted with in DXP',
	{
		tag: '@LRAC-14827',
	},

	async ({page}) => {
		await test.step('Access the DXP Home Page using Google Page as a reference page', async () => {
			await goToWithReferrer({
				page,
				referrer: 'https://www.google.com',
				url: `${liferayConfig.environment.baseUrl}/web/${siteName}`,
			});

			await page.waitForTimeout(10000);
		});

		await test.step('Go to My Page', async () => {
			await page.getByText(pageTitle).first().click();
			await page.waitForTimeout(10000);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Access one of the pages on the list > Go to Path Tab', async () => {
			await navigateTo({
				page,
				pageName: pageTitle,
			});
			await navigateTo({
				page,
				pageName: 'Path',
			});
		});

		await test.step('Check that Google Page appears the referral pages and the number of views', async () => {
			await expect(page.getByText('https://www.goo...')).toBeVisible();

			await expect(
				page.getByText('1', {exact: true}).first()
			).toBeVisible();
		});

		await test.step('Check that Home Page appears with one view', async () => {
			await expect(
				page.getByText('1', {exact: true}).nth(1)
			).toBeVisible();
		});

		await test.step('Check that My Page appears as exit pages and the number of views', async () => {
			await expect(page.getByTitle('Go to Dashboard Page')).toContainText(
				pageTitle
			);

			await expect(
				page.getByText('1', {exact: true}).nth(2)
			).toBeVisible();
		});
	}
);

test(
	'Path Analysis shows the pages that were interacted with in DXP',
	{
		tag: '@LRAC-14827',
	},

	async ({apiHelpers, page}) => {
		const pageTitle1 = 'My Page';
		const pageTitle2 = 'My Page 2';

		await createSitePage({
			apiHelpers,
			pageTitle: pageTitle2,
			siteName,
		});

		await test.step('Go to My Page 1', async () => {
			await navigateToSitePage({
				page,
				pageName: pageTitle1,
				siteName,
			});
			await page.waitForTimeout(10000);
		});

		await test.step('Go to My Page 2', async () => {
			await page.getByText(pageTitle2, {exact: true}).click();
			await page.waitForTimeout(10000);
		});

		await test.step('Go to My Page 1', async () => {
			await page.getByText(pageTitle1, {exact: true}).click();
			await page.waitForTimeout(10000);
			await closeSessions(apiHelpers, page);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Access one of the pages on the list > Go to Path Tab', async () => {
			await navigateTo({
				page,
				pageName: pageTitle1,
			});
			await navigateTo({
				page,
				pageName: 'Path',
			});
		});

		await test.step('Check that My Page 2 and Direct Traffic appear as referral pages', async () => {
			await expect(page.getByText(pageTitle2).first()).toBeVisible();

			await expect(page.getByText('Direct Traffic')).toBeVisible();
		});

		await test.step('Check that My Page 2 and Drop Offs appear as exit pages', async () => {
			await expect(page.getByText(pageTitle2).first()).toBeVisible();

			await expect(page.getByText('Drop Offs')).toBeVisible();
		});
	}
);

test(
	'Remove segments from the filter',
	{
		tag: '@LRAC-14836',
	},

	async ({page}) => {
		await test.step('Go to created Page', async () => {
			await navigateToSitePage({
				page,
				pageName: pageTitle,
				siteName,
			});
			await page.waitForTimeout(10000);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Segments Dashboard and create a Static Segment', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.segmentPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});

			await createStaticSegment(page);

			await setSegmentName({page, segmentName: 'Test Static Segment'});
		});

		await test.step('Add static member and save segment', async () => {
			await addStaticMember({
				memberNames: [`test`],
				page,
			});

			await saveSegment(page);
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});

			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Access one of the pages on the list > Go to Path Tab', async () => {
			await navigateTo({
				page,
				pageName: pageTitle,
			});
			await navigateTo({
				page,
				pageName: 'Path',
			});
		});

		const filterLabel = page
			.locator('span')
			.filter({hasText: 'Test Static Segment'})
			.first();

		await test.step('Add filter with created segment', async () => {
			await page.getByRole('button', {name: 'Filter'}).click();

			await page
				.getByRole('menuitem', {name: 'Test Static Segment'})
				.click();

			expect(filterLabel).toBeVisible();
		});

		const numberOfViews = page.getByText('1', {exact: true}).first();

		await test.step('Check the number of views with the filter', async () => {
			expect(numberOfViews).toBeVisible();
		});

		await test.step('Remove segment from filter', async () => {
			await page.getByLabel('Close').click();

			expect(filterLabel).not.toBeVisible();
		});

		await test.step('Check the number of views without the filter', async () => {
			expect(numberOfViews).toBeVisible();
		});
	}
);

test(
	'Search for a Page in Pages list by its canonical URL',
	{
		tag: '@LRAC-14794',
	},

	async ({apiHelpers, page}) => {
		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await test.step('Sign in with the new user to visit the site pages', async () => {
			await performLogout(page);
			await performLogin(page, user.alternateName);

			const siteNameURL = siteName.replace(/ /g, '-').toLowerCase();

			await page.goto(
				`${liferayConfig.environment.baseUrl}/web/${siteNameURL}`
			);
			await page.reload();
			await page.waitForTimeout(10000);
		});

		const href = page.url();

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACWorkspace({page});
			await switchChannel({
				channelName,
				page,
			});
		});

		await test.step('Go to Pages Tab', async () => {
			await navigateTo({
				page,
				pageName: 'Pages',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Assert the page list', async () => {
			await searchByTerm({page, searchTerm: href});

			await expect(
				page.getByRole('link', {name: `${pageTitle} - ${siteName}`})
			).toBeVisible();
		});
	}
);
