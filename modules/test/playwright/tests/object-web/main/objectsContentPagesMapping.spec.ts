/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import createTempFile from '../../../utils/createTempFile';
import getRandomString from '../../../utils/getRandomString';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {generateObjectFields} from '../utils/generateObjectFields';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest,
	pagesAdminPagesTest
);

test(
	'Can map preview URL of image attachment to fragment on content page',
	{tag: '@LPS-182999'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: MapPreviewURLOfImageAttachmentToFragment

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Attachment'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const document = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '..', 'dependencies', 'astronaut.png')
			),
			{fileName: 'astronaut.png', title: getRandomString()}
		);

		const attachmentFieldName = objectFields[0].name!;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{[attachmentFieldName]: document.id},
			applicationName
		);

		const imageId = getRandomString();

		const imageFragment = getFragmentDefinition({
			id: imageId,
			key: 'BASIC_COMPONENT-image',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([imageFragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		await test.step('Map Image fragment to object entry Preview URL', async () => {
			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.selectEditable(imageId, 'image-square');

			await page.getByLabel('Source Selection').selectOption('Mapping');

			await pageEditorPage.setMappedItem({
				entity: objectDefinition.label['en_US'],
				entry: objectEntry.id.toString(),
				entryLocator: page
					.frameLocator('iframe[title="Select"]')
					.getByText(objectEntry.id.toString())
					.first(),
				field: 'Preview URL',
			});

			await pageEditorPage.waitForChangesSaved();
		});

		await test.step('Verify mapped image is shown in editor', async () => {
			await expect(
				page.locator('.component-image img')
			).toBeVisible();
		});

		await test.step('Publish and verify image in view mode', async () => {
			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect(
				page.locator('.component-image img')
			).toBeVisible();
		});
	}
);

test(
	'Can map preview URL of non-image attachment to fragment showing blank space',
	{tag: '@LPS-182999'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: MapPreviewURLOfNonImageAttachmentToFragment

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Attachment'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const pdfPath = createTempFile(
			'test-document.pdf',
			'%PDF-1.0 dummy content'
		);

		const document = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(pdfPath),
			{fileName: 'test-document.pdf', title: getRandomString()}
		);

		const attachmentFieldName = objectFields[0].name!;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{[attachmentFieldName]: document.id},
			applicationName
		);

		const imageId = getRandomString();

		const imageFragment = getFragmentDefinition({
			id: imageId,
			key: 'BASIC_COMPONENT-image',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([imageFragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		await test.step('Map Image fragment to object entry Preview URL', async () => {
			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.selectEditable(imageId, 'image-square');

			await page.getByLabel('Source Selection').selectOption('Mapping');

			await pageEditorPage.setMappedItem({
				entity: objectDefinition.label['en_US'],
				entry: objectEntry.id.toString(),
				entryLocator: page
					.frameLocator('iframe[title="Select"]')
					.getByText(objectEntry.id.toString())
					.first(),
				field: 'Preview URL',
			});

			await pageEditorPage.waitForChangesSaved();
		});

		await test.step('Publish and verify non-image shows blank space in view mode', async () => {
			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect
				.poll(async () =>
					page
						.locator('.component-image img')
						.evaluate(
							(image: HTMLImageElement) => image.naturalWidth
						)
				)
				.toBe(0);
		});
	}
);
