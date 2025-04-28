/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests, expect} from '@playwright/test';
import {createReadStream} from 'fs';
const fs = require('fs');
import path from 'path';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import { stagingConfigurationPageTest} from '../export-import-web/fixtures/stagingConfigurationPageTest';
import {stagingPageTest} from '../export-import-web/fixtures/stagingPageTest';
import { liferayConfig } from '../../liferay.config';

export const test = mergeTests(
	applicationsMenuPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest(),
	stagingPageTest,
	stagingConfigurationPageTest,
	
);

async function readPropertiesFile(filePath, property){
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

test('Non Modified Referred Content Cannot Publish To Live When Enable Include If Modified Option',{tag: '@LPS-167777'}, async ({
	apiHelpers,
	stagingConfigurationPage,
	stagingPage

}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'site-' + getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	const webContentContent = getRandomString();

	let webContent = await apiHelpers.jsonWebServicesJournal.addWebContent({
		content: webContentContent,
		ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
		groupId: site.id,
		titleMap: {en_US: getRandomString()},
	});

	await stagingPage.goto(site.name);
	await stagingPage.enableLocalStaging();

	const stagingSite =
		await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
			`${site.friendlyUrlPath}-staging`
		);

	const document = await apiHelpers.headlessDelivery.postDocument(
		stagingSite.id,
		createReadStream(path.join(__dirname, '/dependencies/Document.jpg')),
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

	await stagingConfigurationPage.goto(site.name);
	await stagingConfigurationPage.disableTemporaryLARdeletion();

	webContent = await apiHelpers.jsonWebServicesJournal.editWebContent(
		{title: getRandomString()},
		stagingSite.id,
		webContent
	);

	await stagingPage.goto(site.name + '-staging');
 
	await stagingPage.publish(['Web Content 1 Items Web']);

	const bundlesDir = path.resolve(__dirname, '..', '..', '..', '..', '..', '..', 'bundles');
	
	const files = await fs.readdirSync(bundlesDir);
	let tomcatFolder: string;
	for (const file of files){
		if(file.startsWith('tomcat-')){
			tomcatFolder = path.resolve(bundlesDir, file);
			break;
		}
	}

	expect(fs.existsSync(path.resolve(tomcatFolder,'temp','adaptive-media'))).toEqual(true);
});
