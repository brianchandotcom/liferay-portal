/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect, mergeTests} from '@playwright/test';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../utils/fillAndClickOutside';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {structureBuilderPagesTest} from '../structure-builder/fixtures/structureBuilderPagesTest';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPS-179669': {enabled: true},
	}),
	loginTest(),
	pageEditorPagesTest,
	structureBuilderPagesTest
);

test(
	'Friendly URL is taken into account when creating contents',
	{tag: '@LPD-54566'},
	async ({contentsPage, page}) => {

		// Go to CMS Contents

		await contentsPage.goto();

		// Create new Knowledge Base content

		await contentsPage.createContent('Knowledge Base');

		// Fill data and save

		const title = getRandomString();
		const friendlyUrl = getRandomString();

		await page.getByLabel('Title').fill(title);
		await page.getByLabel('Friendly URL').fill(friendlyUrl);

		await contentsPage.saveContent();

		// Edit the content again and check values

		await contentsPage.editContent(title);

		await expect(page.getByLabel('Friendly URL')).toHaveValue(friendlyUrl);

		// Delete content

		await contentsPage.goto();

		await contentsPage.deleteContent(title);
	}
);

test(
	'Default structures take Content Editor Master and fragments work',
	{tag: '@LPD-50371'},
	async ({contentsPage, page}) => {

		// Go to CMS Contents

		await contentsPage.goto();

		// Create new Knowledge Base content

		await contentsPage.createContent('Knowledge Base');

		// Fill data

		const titleEnglish = getRandomString();
		const titleSpanish = getRandomString();
		const friendlyUrl = getRandomString();

		await page.getByLabel('Title').fill(titleEnglish);
		await page.getByLabel('Friendly URL').fill(friendlyUrl);

		await fillAndClickOutside(page, page.getByLabel('Title'), titleEnglish);
		await fillAndClickOutside(
			page,
			page.getByLabel('Friendly URL'),
			friendlyUrl
		);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option').filter({hasText: 'es-ES'}),
			trigger: page.getByLabel('Select a language, current language:'),
		});

		await fillAndClickOutside(page, page.getByLabel('Title'), titleSpanish);

		// Check side panel works

		await contentsPage.openSidePanel('General');

		await contentsPage.closeSidePanel();

		await contentsPage.saveContent();

		// Edit the content again and check values

		await contentsPage.editContent(titleEnglish);

		await expect(page.getByLabel('Title')).toHaveValue(titleEnglish);
		await expect(page.getByLabel('Friendly URL')).toHaveValue(friendlyUrl);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option').filter({hasText: 'es-ES'}),
			trigger: page.getByLabel('Select a language, current language:'),
		});

		await expect(page.getByLabel('Title')).toHaveValue(titleSpanish);

		// Delete content

		await contentsPage.goto();

		await contentsPage.deleteContent(titleEnglish);
	}
);

test(
	'Check the functionality of the Space List fragment CMS',
	{tag: ['@LPD-52223']},
	async ({contentsPage, page, structureBuilderPage}) => {

		// Create new structure for Default space

		await structureBuilderPage.createStructure();

		await structureBuilderPage.selectSpaces(['Default']);

		const label = getRandomString();

		await structureBuilderPage.changeStructureSettings({
			label,
			name: `StructureName${getRandomInt()}`,
		});

		// Publish the structure

		const {id} = await structureBuilderPage.saveStructure();

		await structureBuilderPage.publishStructure();

		// Create a content of the new structure and check Spaces fragment

		await contentsPage.goto();

		await contentsPage.createContent(label);

		const fragment = page.locator(
			'[class*="spacelistcomponentsectionfragmentrenderer"]'
		);

		await fragment.waitFor();

		await expect(
			fragment.locator('label').filter({hasText: 'Space'})
		).toBeVisible();

		await expect(
			fragment.locator('.sticker-overlay').filter({hasText: 'D'})
		).toBeVisible();

		await expect(fragment.filter({hasText: 'Default'})).toBeVisible();

		// Delete structure

		await structureBuilderPage.deleteStructure(id);
	}
);

test(
	'Blog can be published again without changing the content',
	{tag: '@LPD-57478'},
	async ({contentsPage, page}) => {

		// Go to CMS Contents

		await contentsPage.goto();

		// Create new Blog content

		await contentsPage.createContent('Blog');

		// Fill data and save

		const title = getRandomString();

		await page.getByPlaceholder('New Blog').fill(title);

		// Select file from computer in the default language

		const fileChooserPromise = page.waitForEvent('filechooser');

		const firstFileUploadFragment = page.locator('.file-upload').first();

		await firstFileUploadFragment
			.getByText('Select File', {exact: true})
			.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(
			path.join(__dirname, '/dependencies/file_upload_image_1.jpg')
		);

		await expect(
			firstFileUploadFragment.getByText('file_upload_image_1.jpg')
		).toBeVisible();

		await contentsPage.saveContent();

		// Edit the content again and check values

		await contentsPage.editContent(title);

		await expect(
			firstFileUploadFragment.getByText('file_upload_image_1.jpg')
		).toBeVisible();

		// Save content

		await contentsPage.saveContent();

		// Check the content is published

		await expect(page).toHaveURL(/\/web\/cms\/contents$/);

		await contentsPage.deleteContent(title);
	}
);

test(
	'When publishing a content in a folder the browser is redirected to the folder',
	{tag: '@LPD-57478'},
	async ({contentsPage, folderPage, page}) => {

		// Go to CMS Contents

		await contentsPage.goto();

		// Create new Folder and a Knowledge Base content

		const folderName = getRandomString();

		await folderPage.createFolder(folderName);

		await folderPage.clickOption(folderName, 'View Folder');

		await contentsPage.createContent('Knowledge Base');

		// Fill data and save

		const title = getRandomString();

		await page.getByLabel('Title').fill(title);

		await contentsPage.saveContent();

		// Check that the content is visible that means we redirected to the folder

		await expect(page.getByText(title)).toBeVisible();

		// Delete content and folder

		await contentsPage.deleteContent(title);

		await contentsPage.goto();

		await folderPage.deleteFolder(folderName);

		await expect(page.getByText(folderName)).not.toBeVisible();
	}
);

test.describe('Comments Panel', () => {
	const addComment = async ({
		content = 'New Comment',
		page,
		parentComment,
	}: {
		content?: string;
		page: Page;
		parentComment?: Locator;
	}) => {
		const rootComment = parentComment || page;

		const editor = rootComment.getByLabel('Add Comment.');

		await expect(editor).toBeVisible();

		await editor.scrollIntoViewIfNeeded();

		await editor.click();

		await page.keyboard.type(content);

		const saveButton = rootComment.getByRole('button', {name: 'Save'});

		await expect(saveButton).toBeEnabled();

		await saveButton.click();

		await expect(saveButton).toBeDisabled();

		await waitForAlert(page, 'Success:Your comment has been posted.', {
			autoClose: true,
		});

		if (parentComment) {
			await expect(saveButton).not.toBeAttached();
			await expect(editor).not.toBeAttached();
		}
		else {
			await expect(saveButton).toBeEnabled();
			await expect(editor).not.toContainText(content);
		}

		// Check that the comment has been added

		const comment = rootComment.locator('article');

		await expect(comment.filter({hasText: content})).toBeAttached();

		if (parentComment) {
			await expect(comment.getByText('Reply')).not.toBeAttached();
		}

		return {comment, editor};
	};

	test(
		'Add and edit comments in the comments panel',
		{tag: '@LPD-59851'},
		async ({contentsPage, page}) => {
			await contentsPage.goto();

			await contentsPage.createContent('Blog');

			await contentsPage.openSidePanel('Comments');

			// Add a comment

			const parentCommentContent = 'New Comment';

			const {comment, editor} = await addComment({
				content: parentCommentContent,
				page,
			});

			// Check that the text typed is removed when the button cancel is pressed

			await editor.click({force: true});

			await page.keyboard.type('New comment to cancel');

			await page.getByRole('button', {name: 'Cancel'}).click();

			await expect(editor).not.toContainText('New comment to cancel');

			// Add a reply the comment

			await comment.getByText('Reply').click();

			const {comment: childComment} = await addComment({
				content: 'New child comment',
				page,
				parentComment: comment,
			});

			// Edit the parent comment

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem').filter({hasText: 'edit'}),
				trigger: page.getByTitle('actions').first(),
			});

			await page.getByText(parentCommentContent).selectText();

			await page.keyboard.type('Editing the comment');

			await comment.getByRole('button', {name: 'Save'}).click();

			await waitForAlert(page, 'Success:Your comment has been edited.', {
				autoClose: true,
			});

			await expect(comment.first()).toContainText('Editing the comment');

			// Edit the child comment

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem').filter({hasText: 'edit'}),
				trigger: page.getByTitle('actions').nth(1),
			});

			await page.getByText('New child comment').selectText();

			await page.keyboard.type('Editing the child comment');

			await childComment.getByRole('button', {name: 'Save'}).click();

			await expect(childComment).toContainText(
				'Editing the child comment'
			);
		}
	);

	test('Error when a comment is edited', async ({contentsPage, page}) => {
		await page.route(
			'**/c/cms/edit_content_item_comment?**',
			async (route) => {
				await route.fulfill({
					body: JSON.stringify({error: ''}),
					status: 500,
				});
			}
		);

		await contentsPage.goto();

		await contentsPage.createContent('Blog');

		await contentsPage.openSidePanel('Comments');

		const {comment} = await addComment({
			page,
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem').filter({hasText: 'edit'}),
			trigger: page.getByTitle('actions'),
		});

		await page.getByText('New Comment').selectText();

		await page.keyboard.type('Editing the comment');

		await comment.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page, 'Error:An unexpected error occurred.', {
			autoClose: true,
			type: 'danger',
		});
	});

	test('Error when a comment is added', async ({contentsPage, page}) => {
		await page.route(
			'**/c/cms/add_content_item_comment?**',
			async (route) => {
				await route.fulfill({
					body: JSON.stringify({error: ''}),
					status: 500,
				});
			}
		);

		await contentsPage.goto();

		await contentsPage.createContent('Blog');

		await contentsPage.openSidePanel('Comments');

		// Try to add comment

		const editor = page.getByLabel('Add Comment.');

		await expect(editor).toBeVisible();

		await editor.scrollIntoViewIfNeeded();

		await editor.click();

		await page.keyboard.type('New Comment');

		const saveButton = page.getByRole('button', {name: 'Save'});

		await saveButton.click();

		await waitForAlert(page, 'Error:An unexpected error occurred.', {
			autoClose: true,
			type: 'danger',
		});
	});
});

test.describe('Schedule Panel', () => {
	test(
		'Do not allow publishing if there are errors in the fields',
		{tag: '@LPD-62099'},
		async ({contentsPage, page}) => {

			// Create a Blog

			await contentsPage.goto();

			await contentsPage.createContent('Basic Web Content');

			await contentsPage.openSidePanel('Schedule');

			const title = getRandomString();

			await page.getByPlaceholder('New Basic Web Content').fill(title);

			// Fill the input with an error

			const expireCheckbox = page.getByLabel('Never Expire').first();

			await expireCheckbox.uncheck();

			const expirationDateField = page.getByRole('textbox', {
				name: 'Expiration Date',
			});

			await expirationDateField.fill('05/12/2025');

			// Try to publish the content

			await contentsPage.publishButton.click();

			const error = page.getByText('The field value is invalid.');

			await expect(error).toBeVisible();

			await expect(expirationDateField).toBeFocused();

			// Close the panel and try to publish again

			await page.getByTitle('Close', {exact: true}).click();

			await expect(error).not.toBeVisible();

			await contentsPage.publishButton.click();

			await expect(error).toBeVisible();

			await expect(expirationDateField).toBeFocused();

			// Set a valid date and publish

			const nextYear = new Date().getFullYear() + 1;

			await expirationDateField.fill(`05/12/${nextYear} 12:55 PM`);

			await expect(error).not.toBeVisible();

			await contentsPage.publishButton.click();

			await expect(
				page.locator('.table-list-title a', {hasText: title})
			).toBeAttached();

			// Delete content

			await contentsPage.deleteContent(title);
		}
	);
});
