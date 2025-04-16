/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {uiElementsPageTest} from '../../fixtures/uiElementsTest';
import {webContentDisplayPageTest} from '../../fixtures/webContentDisplayPageTest';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {stagingPageTest} from '../export-import-web/fixtures/stagingPageTest';
import {stagingConfigurationPageTest} from '../staging-configuration-web/fixtures/stagingConfigurationPageTest';
import {companyExportImportPageTest} from './fixtures/companyExportImportPagesTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	companyExportImportPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest(),
	pageEditorPagesTest,
	productMenuPageTest,
	uiElementsPageTest,
	stagingPageTest,
	stagingConfigurationPageTest,
	webContentDisplayPageTest
);

test('Non Modified Referred Content Cannot Publish To Live When Enable Include If Modified Option', async ({
	apiHelpers,
	page,
	stagingPage,
}) => {
	const siteName = 'site-' + getRandomString();
	const siteERC = 'ERC-' + getRandomString();
	const site = await apiHelpers.headlessSite.createSite({
		externalReferenceCode: siteERC,
		name: siteName,
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	const basicWebContentStructureId =
		await getBasicWebContentStructureId(apiHelpers);

	const webContentTitle = getRandomString();
	const webContentContent = getRandomString();

	let webContent = await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: webContentContent,
		ddmStructureId: basicWebContentStructureId,
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

	const document = await apiHelpers.headlessDelivery.postDocument(
		stagingSite.id,
		createReadStream(path.join(__dirname, '/dependencies/Document_1.jpg')),
		{
			fileName: 'Document_1.jpg',
			title: 'Document_1.jpg',
		}
	);

	webContent = await apiHelpers.jsonWebServicesJournal.editWebContent(
		{
			content: `<img alt="" data-fileentryid="${document.id}" src="/documents/d${stagingSite.friendlyUrlPath}/Document_1-jpg">&nbsp;<br>${webContentContent}`,
		},
		stagingSite.id,
		webContent
	);

	await page.goto(`/web${stagingSite.friendlyUrlPath}`);

	await page.goto(
		`/group/${site.name}/~/control_panel/manage?p_p_id=com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_factoryPid=com.liferay.staging.configuration.StagingConfiguration&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_mvcRenderCommandName=%2Fconfiguration_admin%2Fedit_configuration`
	);
	await page
		.getByLabel(
			'Delete temporary LAR during a successful staging publish process'
		)
		.uncheck();

	if (
		await page.getByRole('button', {name: 'Save'}).isVisible({timeout: 200})
	) {
		await page.getByRole('button', {name: 'Save'}).click();
	}

	await page.getByRole('button', {name: 'Update'}).click();

	webContent = await apiHelpers.jsonWebServicesJournal.editWebContent(
		{title: getRandomString()},
		stagingSite.id,
		webContent
	);

	await stagingPage.goto(site.name + '-staging');

	await page.getByRole('link', {name: 'Custom Publish Process'}).click();
	await page
		.getByText('Web Content 1 Items Web')
		.getByRole('button', {name: 'Change'})
		.click();

	await page
		.getByRole('radio', {exact: true, name: 'Include If Modified'})
		.click();
	await page
		.getByRole('button', {exact: true, name: 'Publish to Live'})
		.click();
});
