/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {waitForFDS} from '../../../../../utils/waitFor';
import {reactClassicPageTest} from '../../../../frontend-editor-ckeditor-sample-web/fixtures/ckeditor5/classicPageTest';

export const test = mergeTests(
	reactClassicPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test(
	'Editor configuration is applied',
	{tag: '@LPD-11235'},
	async ({classicPage, page}) => {
		await test.step('Initial data is set', async () => {
			await expect(
				classicPage.editable.getByText('Lorem ipsum dolor sit amet')
			).toBeVisible();
		});

		await test.step('Toolbar contains custom toobar configuration, including added custom and official plugins', async () => {
			const expectedButtons = [
				'Undo',
				'Redo',
				'Bold',
				'Italic',
				'Bookmark',
				'Timestamp',
				'Image',
				'Video',
			];

			const availableButtons =
				await classicPage.toolbar.buttonLabels.allInnerTexts();

			expect(availableButtons).toEqual(expectedButtons);
		});

		await test.step('Toolbar does not contain removed plugin', async () => {
			await expect(
				classicPage.toolbar.buttonLabels.getByLabel('Underline')
			).toBeHidden();
		});

		await test.step('"Timestamp" custom plugin has Clay icon', async () => {
			const timestampButton = classicPage.toolbar.container.getByRole(
				'button',
				{
					name: 'Timestamp',
				}
			);

			await expect(
				timestampButton.locator('svg use[href*="/clay/"]')
			).toBeAttached();
		});

		await test.step('Item selector controls open item selector modal', async () => {
			const imageButton = classicPage.toolbar.container.getByRole(
				'button',
				{
					name: 'Image',
				}
			);

			await imageButton.click();

			await expect(page.getByText('Select Image')).toBeVisible();

			await waitForFDS({empty: true, page});

			const modal = page.locator('.modal');

			const cancelButton = modal.getByRole('button', {
				name: 'Cancel',
			});

			await cancelButton.click();

			await expect(page.getByText('Select Image')).not.toBeAttached();

			const videoButton = classicPage.toolbar.container.getByRole(
				'button',
				{
					name: 'Video',
				}
			);

			await videoButton.click();

			await expect(page.getByText('Select Video')).toBeVisible();

			await cancelButton.click();

			await expect(page.getByText('Select Video')).not.toBeAttached();
		});
	}
);

test(
	'Editor can be disabled/enabled',
	{tag: '@LPD-80293'},
	async ({classicPage, page}) => {
		let imageButton: Locator;
		let toggleDisableEditorButton: Locator;
		let videoButton: Locator;

		await test.step('Initial data is set', async () => {
			await expect(
				classicPage.editable.getByText('Lorem ipsum dolor sit amet')
			).toBeVisible();
		});

		await test.step('"Toggle editor ReadOnly mode" button is present', async () => {
			toggleDisableEditorButton = page.getByRole('button', {
				name: 'Toggle editor ReadOnly mode',
			});

			await expect(toggleDisableEditorButton).toBeVisible();
		});

		await test.step('Editor toolbar buttons are enabled', async () => {
			imageButton = classicPage.toolbar.container.getByRole('button', {
				name: 'Image',
			});

			await expect(imageButton).toBeEnabled();

			videoButton = classicPage.toolbar.container.getByRole('button', {
				name: 'Video',
			});

			await expect(videoButton).toBeEnabled();
		});

		await test.step('Click in the "Toggle editor ReadOnly mode" disables the editor: toolbar and content', async () => {
			await toggleDisableEditorButton.click();

			await classicPage.toolbar.container.waitFor();

			await expect(imageButton).toBeDisabled();
			await expect(videoButton).toBeDisabled();

			await expect(page.locator('.lfr-ck-disabled')).toBeInViewport();
		});

		await test.step('Click in the "Toggle editor ReadOnly mode" enables the editor: toolbar and content', async () => {
			await toggleDisableEditorButton.click();

			await classicPage.toolbar.container.waitFor();

			await expect(imageButton).toBeEnabled();
			await expect(videoButton).toBeEnabled();

			await expect(page.locator('.lfr-ck-disabled')).not.toBeInViewport();
		});
	}
);
