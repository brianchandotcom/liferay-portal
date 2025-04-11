/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
const {exec} = require('child_process');
const fs = require('fs');
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
import {waitForAlert} from '../../utils/waitForAlert';
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

async function editWebcontent(page, webContentTitle: String) {
	const openProductButton = page.getByLabel('Open Product Menu');

	if (await openProductButton.isVisible()) {
		await openProductButton.click();
	}

	const contentAndDataTab = page.getByRole('menuitem', {
		name: 'Content & Data',
	});

	await contentAndDataTab.waitFor({state: 'visible'});

	await contentAndDataTab.click();

	const webContentButton = page.getByRole('menuitem', {
		name: 'Web Content',
	});

	await webContentButton.waitFor({state: 'visible'});

	await webContentButton.click();

	const webContentPage = page.getByRole('heading', {name: 'Web Content'});

	await webContentPage.waitFor({state: 'visible'});

	await page
		.getByRole('link', {name: webContentTitle})
		.waitFor({state: 'visible'});
	await page.getByText(webContentTitle).click();
}

async function readPropertiesFile(filePath, property) {
	const data = fs.readFileSync(filePath, 'utf-8');
	let propertyValue = '';
	data.split('\n').forEach((line) => {
		line = line.trim();
		if (line && !line.startsWith('#')) {
			const [key, value] = line.split('=');
			if (key && value && key === property) {
				propertyValue = value.trim();
			}
		}
	});

	return propertyValue;
}
test('Non Modified Referred Content Cannot Publish To Live When Enable Include If Modified Option', async ({
	apiHelpers,
	page,
	stagingPage,
}) => {
	const siteName = 'site-' + getRandomString();
	const site = await apiHelpers.headlessSite.createSite({
		name: siteName,
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	const basicWebContentStructureId =
		await getBasicWebContentStructureId(apiHelpers);

	const webContentTitle = getRandomString();

	const webContent = await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: getRandomString(),
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

	await page.goto(`/web${site.friendlyUrlPath}-staging`);

	await editWebcontent(page, webContentTitle);

	await page.getByLabel('Image', {exact: true}).click();
	const fileChooserPromise = page.waitForEvent('filechooser');

	const iframe = page.frameLocator('iframe[title="Select Item"]');
	await iframe
		.getByText('Drag & Drop Your Images or Browse to Upload')
		.click();
	const filePath = path.join(__dirname, '/dependencies/Document_1.jpg');

	const fileChooser = await fileChooserPromise;
	await fileChooser.setFiles(filePath);

	await iframe.getByText('Add').click();

	await page.getByRole('button', {name: 'Publish'}).click();
	await page.getByRole('menuitem', {name: 'Publish'}).click();

	await waitForAlert(page, `Success:`);

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
	await page.goto(`/web${site.friendlyUrlPath}-staging${layout.friendlyURL}`);

	await editWebcontent(page, webContentTitle);

	await page
		.getByPlaceholder('Untitled Basic Web Content')
		.fill('edit-' + getRandomString());
	await page.getByRole('button', {name: 'Publish'}).click();
	await page.getByRole('menuitem', {name: 'Publish'}).click();

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

	const workspacePath = path.resolve(__dirname, '../../../../../');

	await exec('ant -f build-test.xml check-folder-in-lar', {
		cwd: workspacePath,
	});

	const config = path.resolve(
		workspacePath,
		'./portal-web/poshi-ext.properties'
	);

	const bundlePath = await readPropertiesFile(
		config,
		'liferay.home.dir.name'
	);

	const data = fs.readFileSync(
		path.resolve(bundlePath, './result.txt'),
		'utf-8'
	);
	expect(data).not.toContain('not found');
});
