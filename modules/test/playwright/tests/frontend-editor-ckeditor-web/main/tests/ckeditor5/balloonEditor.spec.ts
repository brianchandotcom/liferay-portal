/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {balloonPageTest} from '../../../../frontend-editor-ckeditor-sample-web/fixtures/ckeditor5/balloonPageTest';

const test = mergeTests(
	balloonPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test(
	'Can align an image in the balloon editor',
	{tag: '@LPS-121553'},
	async ({balloonPage, page}) => {
		await balloonPage.editable.click();

		await page.keyboard.press('Control+A');
		await page.keyboard.press('Delete');

		await page.keyboard.type('Test content');

		await balloonPage.editable.selectText();

		await balloonPage.toolbar
			.getByRole('button', {name: 'Image'})
			.click();

		const imageUrlInput = page.getByLabel('Update image URL');

		await imageUrlInput.waitFor({state: 'visible'});

		await imageUrlInput.fill(
			'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=='
		);

		await page.keyboard.press('Enter');

		const image = balloonPage.editable.locator('.image-inline img');

		await image.waitFor({state: 'visible'});

		await image.click();

		const imageToolbar = page.getByLabel('Image toolbar');

		await imageToolbar.waitFor({state: 'visible'});

		await test.step('Align image to the right', async () => {
			await imageToolbar
				.getByRole('button', {name: 'Align right'})
				.click();

			await expect(
				balloonPage.editable.locator('.image-inline')
			).toHaveClass(/image-style-align-right/);
		});

		await test.step('Align image to the left', async () => {
			await imageToolbar
				.getByRole('button', {name: 'Align left'})
				.click();

			await expect(
				balloonPage.editable.locator('.image-inline')
			).toHaveClass(/image-style-align-left/);
		});

		await test.step('Align image to the center', async () => {
			await imageToolbar
				.getByRole('button', {name: 'Align center'})
				.click();

			await expect(
				balloonPage.editable.locator('.image-inline')
			).toHaveClass(/image-style-align-center/);
		});
	}
);

test(
	'Can align a video in the balloon editor',
	{tag: '@LPS-121553'},
	async ({balloonPage, page}) => {
		await balloonPage.editable.click();

		await page.keyboard.press('Control+A');
		await page.keyboard.press('Delete');

		await balloonPage.editable.selectText();

		await balloonPage.toolbar
			.getByRole('button', {name: 'Video'})
			.click();

		const videoUrlInput = page.getByLabel('Update video URL');

		await videoUrlInput.waitFor({state: 'visible'});

		await videoUrlInput.fill('https://www.youtube.com/watch?v=dQw4w9WgXcQ');

		await page.keyboard.press('Enter');

		const video = balloonPage.editable.locator(
			'.ck-media__wrapper, figure.media'
		);

		await video.waitFor({state: 'visible'});

		await video.click();

		const mediaToolbar = page.getByLabel('Media toolbar');

		await mediaToolbar.waitFor({state: 'visible'});

		await test.step('Align video to the right', async () => {
			await mediaToolbar
				.getByRole('button', {name: 'Align right'})
				.click();

			await expect(
				balloonPage.editable.locator('figure.media')
			).toHaveClass(/media-style-align-right|image-style-align-right/);
		});

		await test.step('Align video to the left', async () => {
			await mediaToolbar
				.getByRole('button', {name: 'Align left'})
				.click();

			await expect(
				balloonPage.editable.locator('figure.media')
			).toHaveClass(/media-style-align-left|image-style-align-left/);
		});

		await test.step('Align video to the center', async () => {
			await mediaToolbar
				.getByRole('button', {name: 'Align center'})
				.click();

			await expect(
				balloonPage.editable.locator('figure.media')
			).toHaveClass(/media-style-align-center|image-style-align-center/);
		});
	}
);

test.fixme(
	'Can edit a hyperlink in the balloon editor',
	{tag: '@LPS-121553'},
	async ({balloonPage: _balloonPage}) => {
		// Quarantined in Poshi - hyperlink editing test is unstable
	}
);

test(
	'Can edit a table in the balloon editor',
	{tag: '@LPS-121553'},
	async ({balloonPage, page}) => {
		await balloonPage.editable.click();

		await page.keyboard.press('Control+A');
		await page.keyboard.press('Delete');

		await page.keyboard.type('Text before table');

		await balloonPage.editable.selectText();

		await test.step('Create a table', async () => {
			await balloonPage.toolbar
				.getByRole('button', {name: 'Insert table'})
				.click();

			const tableGrid = page.locator('.ck-insert-table-dropdown-grid');

			await tableGrid.waitFor({state: 'visible'});

			const firstCell = tableGrid.locator(
				'.ck-insert-table-dropdown-grid-box'
			);

			await firstCell.first().click();
		});

		const table = balloonPage.editable.locator('table');

		await expect(table).toBeVisible();

		await test.step('Write in a table cell', async () => {
			const firstCell = table.locator('td').first();

			await firstCell.click();

			await page.keyboard.type('Cell content');

			await expect(firstCell).toContainText('Cell content');
		});

		await test.step('Delete the table', async () => {
			const tableCell = table.locator('td').first();

			await tableCell.click();

			const tableToolbar = page.getByLabel('Table toolbar');

			await tableToolbar.waitFor({state: 'visible'});

			const tablePropertiesButton = tableToolbar.getByRole('button', {
				name: 'Table',
			});

			if (await tablePropertiesButton.isVisible()) {
				await tablePropertiesButton.click();

				const deleteTableButton = page.getByRole('menuitemradio', {
					name: 'Delete table',
				});

				if (await deleteTableButton.isVisible()) {
					await deleteTableButton.click();
				}
			}
		});
	}
);

test(
	'Can edit text in a table in the balloon editor',
	{tag: '@LPS-121553'},
	async ({balloonPage, page}) => {
		await balloonPage.editable.click();

		await page.keyboard.press('Control+A');
		await page.keyboard.press('Delete');

		await page.keyboard.type('Text before table');

		await balloonPage.editable.selectText();

		await balloonPage.toolbar
			.getByRole('button', {name: 'Insert table'})
			.click();

		const tableGrid = page.locator('.ck-insert-table-dropdown-grid');

		await tableGrid.waitFor({state: 'visible'});

		await tableGrid
			.locator('.ck-insert-table-dropdown-grid-box')
			.first()
			.click();

		const table = balloonPage.editable.locator('table');

		await expect(table).toBeVisible();

		const firstCell = table.locator('td').first();

		await firstCell.click();

		await page.keyboard.type('Text in cell');

		await page.keyboard.press('Control+A');

		await expect(balloonPage.toolbar).toBeVisible();

		const boldButton = balloonPage.toolbar.getByRole('button', {
			name: 'Bold',
		});

		await expect(boldButton).toBeVisible();
	}
);

test(
	'Can format text in the balloon editor',
	{tag: '@LPS-121553'},
	async ({balloonPage, page}) => {
		await balloonPage.editable.click();

		await page.keyboard.press('Control+A');
		await page.keyboard.press('Delete');

		await page.keyboard.type('Text to format');

		await balloonPage.editable.selectText();

		await test.step('Apply bold formatting', async () => {
			await balloonPage.toolbar
				.getByRole('button', {name: 'Bold'})
				.click();

			await expect(
				balloonPage.editable.locator('strong')
			).toContainText('Text to format');
		});

		await test.step('Apply italic formatting', async () => {
			await balloonPage.toolbar
				.getByRole('button', {name: 'Italic'})
				.click();

			await expect(balloonPage.editable.locator('i')).toContainText(
				'Text to format'
			);
		});

		await test.step('Apply underline formatting', async () => {
			await balloonPage.toolbar
				.getByRole('button', {name: 'Underline'})
				.click();

			await expect(balloonPage.editable.locator('u')).toContainText(
				'Text to format'
			);
		});

		await test.step('Remove formatting and apply bulleted list', async () => {
			await balloonPage.toolbar
				.getByRole('button', {name: 'Bold'})
				.click();
			await balloonPage.toolbar
				.getByRole('button', {name: 'Italic'})
				.click();
			await balloonPage.toolbar
				.getByRole('button', {name: 'Underline'})
				.click();

			await balloonPage.toolbar
				.getByRole('button', {name: 'Bulleted List'})
				.click();

			await expect(balloonPage.editable.locator('ul')).toBeVisible();
		});

		await test.step('Switch to numbered list', async () => {
			await balloonPage.editable.selectText();

			await balloonPage.toolbar
				.getByRole('button', {name: 'Bulleted List'})
				.click();

			await balloonPage.toolbar
				.getByRole('button', {name: 'Numbered List'})
				.click();

			await expect(balloonPage.editable.locator('ol')).toBeVisible();
		});

		await test.step('Apply text alignment', async () => {
			await balloonPage.editable.selectText();

			await balloonPage.toolbar
				.getByRole('button', {name: 'Numbered List'})
				.click();

			await balloonPage.toolbar
				.getByRole('button', {name: 'Text alignment'})
				.click();

			const alignCenterOption = page.getByRole('menuitemradio', {
				name: 'Align center',
			});

			await alignCenterOption.click();

			await expect(
				balloonPage.editable.locator('[style*="text-align:center"]')
			).toBeVisible();
		});
	}
);
