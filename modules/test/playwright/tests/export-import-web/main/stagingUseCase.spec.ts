/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream, readdirSync} from 'fs';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {webContentDisplayPageTest} from '../../../fixtures/webContentDisplayPageTest';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {exportImportConfig} from './export_import.config';
import {stagingConfigurationPageTest} from './fixtures/stagingConfigurationPageTest';
import {stagingPageTest} from './fixtures/stagingPageTest';
import {unzipAndCheckFolder} from './utils/stagingUtil';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest(),
	pageViewModePagesTest,
	stagingConfigurationPageTest,
	stagingPageTest,
	webContentDisplayPageTest
);

test(
	'Non modified referred content cannot publish to live when enable include if modified option',
	{tag: '@LPS-167777'},
	async ({apiHelpers, stagingConfigurationPage, stagingPage}) => {
		const site = await apiHelpers.headlessSite.createSite({
			name: 'site-' + getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await stagingPage.goto(site.name);
		await stagingPage.enableLocalStaging();

		const stagingSite =
			await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
				`${site.friendlyUrlPath}-staging`
			);

		const webContentContent = getRandomString();
		let webContent = await apiHelpers.jsonWebServicesJournal.addWebContent({
			content: webContentContent,
			ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
			groupId: stagingSite.id,
			titleMap: {en_US: getRandomString()},
		});

		const document = await apiHelpers.headlessDelivery.postDocument(
			stagingSite.id,
			createReadStream(
				path.join(__dirname, '/dependencies/Document.jpg')
			),
			{
				fileName: 'Document.jpg',
				title: 'Document.jpg',
			}
		);

		webContent = await apiHelpers.jsonWebServicesJournal.editWebContent(
			{
				content: `<img alt="" data-fileentryid="${document.id}" src="/documents/d${stagingSite.friendlyUrlPath}/Document-jpg">&nbsp;<br>${webContentContent}`,
			},
			stagingSite.id,
			webContent
		);

		await stagingPage.goto(site.name + '-staging');
		await stagingPage.publish();

		await stagingConfigurationPage.goto(site.name);
		await stagingConfigurationPage.disableTemporaryLARdeletion();

		await apiHelpers.jsonWebServicesJournal.editWebContent(
			{title: getRandomString()},
			stagingSite.id,
			webContent
		);

		await stagingPage.goto(site.name + '-staging');
		await stagingPage.publish(['Web Content 1 Items Web']);

		const tomcatDir = exportImportConfig.environment.tomcatDir;

		const files = readdirSync(tomcatDir).filter((file) =>
			file.startsWith('tomcat-')
		);

		const hasFolder = await unzipAndCheckFolder(
			path.resolve(tomcatDir, files[0], 'temp')
		);

		expect(hasFolder).toEqual(false);
	}
);

test('Staging publish template with smoke', async ({
	apiHelpers,
	page,
	stagingPage,
	webContentDisplayPage,
	widgetPagePage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		options: {type: 'portlet'},
		title: getRandomString(),
	});

	const webContentTitle = getRandomString();
	const webContentContent = getRandomString();
	const webContent = await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: webContentContent,
		ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
		groupId: site.id,
		titleMap: {en_US: webContentTitle},
	});

	apiHelpers.data.push({
		id: `${site.id}_${webContent.articleId}`,
		type: 'webContent',
	});

	await stagingPage.goto(site.name);
	await stagingPage.enableLocalStaging();

	const stagingSite =
		await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
			`${site.friendlyUrlPath}-staging`
		);

	await page.waitForTimeout(2000);
	await widgetPagePage.goto(layout, stagingSite.friendlyUrlPath);
	await page.waitForLoadState('domcontentloaded');

	await widgetPagePage.addPortlet(
		'Web Content Display',
		'Content Management'
	);

	await webContentDisplayPage.addWebContentWithDisplay({
		pageType: 'widget',
		webContentName: webContentTitle,
	});

	await page.waitForTimeout(2000);

	await stagingPage.goto(site.name + '-staging');

	const templateName = getRandomString();
	await stagingPage.gotoTemplatePage();
	await stagingPage.addTemplate(templateName);
	await page.reload({waitUntil: 'domcontentloaded'});
	await stagingPage.publishTemplate(templateName);

	await widgetPagePage.goto(layout, site.friendlyUrlPath);

	expect(page.getByText(webContentTitle, {exact: true})).toBeVisible();
	expect(page.getByText(webContentContent, {exact: true})).toBeVisible();
});
