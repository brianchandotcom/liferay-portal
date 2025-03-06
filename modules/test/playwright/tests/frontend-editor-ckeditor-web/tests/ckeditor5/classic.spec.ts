/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {ckeditorSamplePageTest} from '../../fixtures/ckeditorSamplePageTest';

export const test = mergeTests(
	apiHelpersTest,
	ckeditorSamplePageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({ckeditorSamplePage, site}) => {
	await ckeditorSamplePage.createAndGotoSitePage({site});

	await ckeditorSamplePage.selectTab('CKEditor 5');
	await ckeditorSamplePage.selectTab('Classic');
});

test(
	'Assert editor is rendered with features based on configuration',
	{tag: '@LPD-11235'},
	async ({page}) => {
		await test.step('Check initial UI', async () => {
			await expect(
				page.getByText('Lorem ipsum dolor sit amet')
			).toBeVisible();

			await expect(
				page.locator(
					'p[data-placeholder="This placeholder is set from EditorConfigContributor."]'
				)
			).toBeAttached();

			const editorToolbar = page.getByLabel('Editor toolbar');

			await expect(editorToolbar).toBeVisible();

			const expectedButtons = [
				'Undo',
				'Redo',
				'Styles',
				'Normal',
				'Bold',
				'Italic',
				'Underline',
				'Strikethrough',
				'Font Color',
				'Font Background Color',
				'Remove Format',
				'Numbered List',
				'Bulleted List',
				'Increase indent',
				'Decrease indent',
				'Block quote',
				'Link',
				'Insert table',
				'Image',
				'Horizontal line',
				'Text alignment',
				'Source',
			];

			const availableButtons = await editorToolbar
				.getByRole('button')
				.locator('.ck-button__label')
				.allInnerTexts();

			expect(availableButtons).toEqual(expectedButtons);
		});

		await test.step('Select an image from document library', async () => {
			await page.getByRole('button', {name: 'Image'}).click();

			const itemSelectorFrame = page.frameLocator(
				'iframe[title="Select Item"]'
			);

			// Attached droppable area is an indicator that loading in iframe is done.

			const droppableArea = itemSelectorFrame.getByText(
				'Drag & Drop Your Images'
			);

			await expect(droppableArea).toBeAttached();

			await itemSelectorFrame
				.getByRole('link', {name: 'Sites and Libraries'})
				.click();

			await itemSelectorFrame.getByLabel('Liferay DXP').click();

			await expect(droppableArea).toBeAttached();

			await itemSelectorFrame
				.getByRole('link', {name: 'Provided by Liferay'})
				.click();

			await itemSelectorFrame
				.locator('.image-card[data-title="moon.png"]')
				.click();

			await expect(
				page.locator(
					'.ck-editor__editable img[src="/documents/d/guest/moon-png"]'
				)
			).toBeVisible();
		});
	}
);
