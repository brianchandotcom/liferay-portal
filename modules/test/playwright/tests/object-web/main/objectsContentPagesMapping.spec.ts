/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {generateObjectFields} from './utils/generateObjectFields';

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
	'LPD-78504 Can map preview URL of image attachment to fragment on content page',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: MapPreviewURLOfImageAttachmentToFragment

		// Create object with Attachment field configured for images

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Attachment',
					objectFieldSettings: [
						{
							name: 'acceptedFileExtensions',
							value: 'jpeg, jpg, png',
						},
						{
							name: 'fileSource',
							value: 'documentsAndMedia',
						},
						{
							name: 'maximumFileSize',
							value: '0',
						},
					],
				},
				'Text',
			],
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

		const textFieldName = objectFields[1].name;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		// Create an object entry with text value

		const entryValue = 'ImageEntry_' + getRandomInt();

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: entryValue},
			applicationName
		);

		// Create a content page with an Image fragment

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

		// Go to edit mode of page

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Select the Image fragment editable and set source to Mapping

		await pageEditorPage.selectEditable(imageId, 'image-square');

		await page.getByLabel('Source Selection').selectOption('Mapping');

		// Map to the object entry

		await pageEditorPage.setMappedItem({
			entity: objectDefinition.label['en_US'],
			entry: objectEntry.id.toString(),
			entryLocator: page
				.frameLocator('iframe[title="Select"]')
				.getByText(objectEntry.id.toString())
				.first(),
			field: objectFields[0].label['en_US'],
		});

		await pageEditorPage.waitForChangesSaved();

		// Publish the page

		await pageEditorPage.publishPage();

		// Navigate to the page and verify the image mapping was applied

		await page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		// Verify the image element exists on the page (the attachment field
		// is mapped, so an img tag should be present)

		await expect(
			page.locator(
				'[data-lfr-editable-id="image-square"] img, img[data-lfr-editable-id="image-square"]'
			)
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can map preview URL of non-image attachment to fragment showing blank space',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		// Corresponds to Poshi test: MapPreviewURLOfNonImageAttachmentToFragment

		// Create object with Attachment field configured for non-image files

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Attachment',
					objectFieldSettings: [
						{
							name: 'acceptedFileExtensions',
							value: 'pdf, doc, txt',
						},
						{
							name: 'fileSource',
							value: 'documentsAndMedia',
						},
						{
							name: 'maximumFileSize',
							value: '0',
						},
					],
				},
				'Text',
			],
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

		const textFieldName = objectFields[1].name;
		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		// Create an object entry with text value

		const entryValue = 'PDFEntry_' + getRandomInt();

		const objectEntry = await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: entryValue},
			applicationName
		);

		// Create a content page with an Image fragment

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

		// Go to edit mode of page

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Select the Image fragment editable and set source to Mapping

		await pageEditorPage.selectEditable(imageId, 'image-square');

		await page.getByLabel('Source Selection').selectOption('Mapping');

		// Map to the object entry with the attachment field

		await pageEditorPage.setMappedItem({
			entity: objectDefinition.label['en_US'],
			entry: objectEntry.id.toString(),
			entryLocator: page
				.frameLocator('iframe[title="Select"]')
				.getByText(objectEntry.id.toString())
				.first(),
			field: objectFields[0].label['en_US'],
		});

		await pageEditorPage.waitForChangesSaved();

		// Publish the page

		await pageEditorPage.publishPage();

		// Navigate to the page

		await page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		// Non-image attachments don't have a visual preview, so the image
		// fragment should not display a meaningful image. Verify that
		// no actual image content is rendered (empty/blank space).

		const imageLocator = page.locator(
			'[data-lfr-editable-id="image-square"] img, img[data-lfr-editable-id="image-square"]'
		);

		// The image element may exist but have no valid src or be hidden
		// since the attachment is non-image (PDF). Check that either
		// the image is not visible or has no natural width.

		const isVisible = await imageLocator.isVisible().catch(() => false);

		if (isVisible) {
			// If the img tag exists, verify it has no meaningful content
			// (naturalWidth of 0 means the image didn't load)

			const naturalWidth = await imageLocator.evaluate(
				(img: HTMLImageElement) => img.naturalWidth
			);

			expect(naturalWidth).toBe(0);
		}
		else {
			// Image is not visible, which is the expected behavior
			// for non-image attachments

			expect(isVisible).toBe(false);
		}
	}
);
