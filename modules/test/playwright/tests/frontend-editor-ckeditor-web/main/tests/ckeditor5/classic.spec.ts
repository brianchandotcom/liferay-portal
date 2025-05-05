/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {ckeditorSamplePageTest} from '../../fixtures/ckeditorSamplePageTest';
import {classicPageTest} from './fixtures/classicPageTest';

export const test = mergeTests(
	apiHelpersTest,
	ckeditorSamplePageTest,
	classicPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({ckeditorSamplePage, site}) => {
	await ckeditorSamplePage.createAndGotoSitePage({site});

	const productMenuToggle =
		ckeditorSamplePage.page.getByLabel('Close Product Menu');

	if (await productMenuToggle.isVisible()) {
		await productMenuToggle.click();
	}

	await ckeditorSamplePage.selectTab('CKEditor 5');
	await ckeditorSamplePage.selectTab('Classic');
});

test(
	'Toolbar contains all advanced preset controls',
	{tag: '@LPD-11235'},
	async ({classicPage}) => {
		await expect(
			classicPage.editable.getByText('Lorem ipsum dolor sit amet')
		).toBeVisible();

		await expect(
			classicPage.page.locator(
				'p[data-placeholder="This placeholder is set from EditorConfigContributor."]'
			)
		).toBeAttached();

		await expect(classicPage.toolbar).toBeVisible();

		const advancedPresetControls = [
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
			'Video',
			'Horizontal line',
			'Text alignment',
			'Source',
		];

		const controls = await classicPage.toolbar
			.getByRole('button')
			.locator('.ck-button__label')
			.allInnerTexts();

		expect(controls).toEqual(advancedPresetControls);
	}
);

test(
	'Select image from document library',
	{tag: '@LPD-11235'},
	async ({classicPage}) => {
		await classicPage.toolbar.getByRole('button', {name: 'Image'}).click();

		const itemSelectorFrame = classicPage.itemSelectorFrame;

		// Attached droppable area is an indicator that loading in iframe is done.

		const droppableArea = itemSelectorFrame.getByText(
			'Drag & Drop Your Images'
		);

		await expect(droppableArea).toBeAttached();

		await itemSelectorFrame
			.getByRole('link', {name: 'Sites and Libraries'})
			.click();

		await itemSelectorFrame.getByLabel('Liferay').click();

		await expect(droppableArea).toBeAttached();

		await itemSelectorFrame
			.getByRole('link', {name: 'Provided by Liferay'})
			.click();

		await itemSelectorFrame
			.locator('.image-card[data-title="moon.png"]')
			.click();

		await expect(
			classicPage.editable.locator(
				'img[src="/documents/d/guest/moon-png"]'
			)
		).toBeVisible();
	}
);

test(
	'Select video by modal URL input',
	{tag: '@LPD-11235'},
	async ({classicPage}) => {
		await classicPage.toolbar.getByRole('button', {name: 'Video'}).click();

		const itemSelectorFrame = classicPage.itemSelectorFrame;

		const videoURLInput = itemSelectorFrame.getByLabel('Video URL');

		await expect(videoURLInput).toBeEnabled();

		const addButton = itemSelectorFrame.getByRole('button', {
			exact: true,
			name: 'Add',
		});

		await expect(addButton).toBeDisabled();

		await videoURLInput.fill('https://www.youtube.com/watch?v=2EPZxIC5ogU');

		await expect(addButton).toBeEnabled();

		await addButton.click();

		await expect(
			classicPage.editable
				.frameLocator('iframe')
				.getByLabel('YouTube Video Player')
		).toBeVisible();
	}
);

test(
	'Opening source editing disables all custom controls',
	{tag: '@LPD-11235'},
	async ({classicPage}) => {
		const imageButton = classicPage.toolbar.getByRole('button', {
			name: 'Image',
		});
		const sourceButton = classicPage.toolbar.getByRole('button', {
			name: 'Source',
		});
		const videoButton = classicPage.toolbar.getByRole('button', {
			name: 'Video',
		});

		await sourceButton.click();

		await expect(imageButton).toBeDisabled();
		await expect(videoButton).toBeDisabled();

		await sourceButton.click();

		await expect(imageButton).toBeEnabled();
		await expect(videoButton).toBeEnabled();
	}
);
