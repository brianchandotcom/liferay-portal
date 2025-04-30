/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests, expect} from '@playwright/test';
import {createReadStream, readdirSync, statSync} from 'fs';
import path from 'path';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {checkFolderInZip} from '../../utils/zip';
import {stagingConfigurationPageTest} from '../export-import-web/fixtures/stagingConfigurationPageTest';
import {stagingPageTest} from '../export-import-web/fixtures/stagingPageTest';
import {exportImportConfig} from './export_import.config';

export const test = mergeTests(
	applicationsMenuPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35914': {enabled: true, system: true},
	}),
	loginTest(),
	stagingPageTest,
	stagingConfigurationPageTest
);

test(
	'Non Modified Referred Content Cannot Publish To Live When Enable Include If Modified Option',
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

		const hasFolder = await _unzipAndCheckFolder(
			path.resolve(tomcatDir, files[0], 'temp')
		);

		expect(hasFolder).toEqual(false);
	}
);

const _unzipAndCheckFolder = async (
	tempDir: string,
	folderName: string = 'adaptive-media'
): Promise<boolean> => {
	const files = readdirSync(tempDir)
		.filter((file) => file.endsWith('.lar'))
		.map((file) => ({
			file,
			time: statSync(path.join(tempDir, file)).mtime.getTime(),
		}));

	if (!files.length) {
		console.log('No LAR files found');

		return null;
	}

	// Sort files by most recent modification time

	files.sort((a, b) => b.time - a.time);

	const mostRecentFilePath = path.join(tempDir, files[0].file);

	try {
		const hasFolder = await checkFolderInZip(
			mostRecentFilePath,
			folderName
		);
				return hasFolder;
	}
	catch (error) {
		console.error(`Error reading file ${files[0].file}: ${error}`);
	}

	return null;
};
