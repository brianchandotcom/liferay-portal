/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {styleBookPageTest} from '../../fixtures/styleBookPageTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {blogsPagesTest} from '../blogs-web/fixtures/blogsPagesTest';

const test = mergeTests(
	apiHelpersTest,
	blogsPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	styleBookPageTest,
	workflowPagesTest
);

test('Add a heading fragment and check that the display page templates are shown in the preview type selector', async ({
	apiHelpers,
	displayPageTemplatesPage,
	page,
	pageEditorPage,
	site,
	styleBooksPage,
}) => {
	const displayPageTemplateNameBlogsEntry = getRandomString();
	const displayPageTemplateNameWebContent = getRandomString();
	const styleBookName = getRandomString();

	const placeHeadingFragmentAndSelectText = async () => {
		await pageEditorPage.addFragment('Basic Components', 'Heading');

		await page.getByLabel('Browser').click();

		await page.getByLabel('Select Heading').click();

		await page.getByLabel('Select element-text').click();
	};

	await test.step('Create and publish a page template for a web content article', async () => {
		const className =
			await apiHelpers.jsonWebServicesClassName.fetchClassName(
				'com.liferay.journal.model.JournalArticle'
			);

		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addDisplayPageLayoutPageTemplateEntry(
			{
				classNameId: className.classNameId,
				classTypeId: String(
					await getBasicWebContentStructureId(apiHelpers)
				),
				groupId: site.id,
				name: displayPageTemplateNameWebContent,
			}
		);

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		await displayPageTemplatesPage.editTemplate(
			displayPageTemplateNameWebContent
		);

		await placeHeadingFragmentAndSelectText();

		await page.getByLabel('Field').selectOption('JournalArticle_title');

		await displayPageTemplatesPage.publishTemplate();
	});

	await test.step('Create and publish a page template for a blogs entry', async () => {
		const className =
			await apiHelpers.jsonWebServicesClassName.fetchClassName(
				'com.liferay.blogs.model.BlogsEntry'
			);

		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addDisplayPageLayoutPageTemplateEntry(
			{
				classNameId: className.classNameId,
				groupId: site.id,
				name: displayPageTemplateNameBlogsEntry,
			}
		);

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		await displayPageTemplatesPage.editTemplate(
			displayPageTemplateNameBlogsEntry
		);

		await placeHeadingFragmentAndSelectText();

		await page.getByLabel('Source').selectOption('structure');

		await page.getByLabel('Field').selectOption('BlogsEntry_content');

		await displayPageTemplatesPage.publishTemplate();
	});

	await test.step('Create a style book and check that the display page template is selected in the preview type selector', async () => {
		await styleBooksPage.goto(site.friendlyUrlPath);

		await styleBooksPage.create(styleBookName);

		await styleBooksPage.publish();

		await styleBooksPage.edit(styleBookName);

		await expect(
			page.getByRole('button', {name: 'Display Page Templates'})
		).toBeVisible();
	});

	await test.step('View the items shown in the dropdown menu of the preview item selector and change the preview item for each display page template', async () => {
		const selectOptionAndUpdateColor = async (
			selectButton: string,
			selectMenuItem: string,
			colorHEX: string,
			colorRGB: string
		) => {
			await page.getByRole('button', {name: selectButton}).click();

			await page.getByRole('menuitem', {name: selectMenuItem}).click();

			await styleBooksPage.updateTokenInputColor('Body Color', colorHEX);

			expect(page.getByText('Saved')).toBeVisible();

			const heading = page
				.frameLocator('iframe')
				.getByRole('heading', {name: 'Heading Example'});

			const fragmentColor = await heading.evaluate((element) => {
				const computedStyle = window.getComputedStyle(element);

				return computedStyle.color;
			});

			expect(fragmentColor).toBe(colorRGB);
		};

		await page.getByRole('button', {name: 'Color System'}).click();

		await page.getByRole('menuitem', {name: 'General'}).click();

		await selectOptionAndUpdateColor(
			displayPageTemplateNameBlogsEntry,
			displayPageTemplateNameBlogsEntry,
			'#995511',
			'rgb(153, 85, 17)'
		);

		await selectOptionAndUpdateColor(
			displayPageTemplateNameBlogsEntry,
			displayPageTemplateNameWebContent,
			'#666666',
			'rgb(102, 102, 102)'
		);
	});
});

test('Use the view more button to select a display page template from the select modal and change preview page', async ({
	displayPageTemplatesPage,
	page,
	site,
	styleBooksPage,
}) => {
	const displayPageTemplateNameBlogsEntry = getRandomString();
	const displayPageTemplateNameCategory = getRandomString();
	const displayPageTemplateNameDocuments = getRandomString();
	const displayPageTemplateNameWebContent1 = getRandomString();
	const displayPageTemplateNameWebContent2 = getRandomString();
	const styleBookName = getRandomString();

	await test.step('Create and publish multiple display page templates', async () => {
		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		await displayPageTemplatesPage.createTemplate({
			contentSubtype: 'Basic Web Content',
			contentType: 'Web Content Article',
			name: displayPageTemplateNameWebContent1,
		});

		await displayPageTemplatesPage.createTemplate({
			contentSubtype: 'Basic Web Content',
			contentType: 'Web Content Article',
			name: displayPageTemplateNameWebContent2,
		});

		await displayPageTemplatesPage.createTemplate({
			contentType: 'Blogs Entry',
			name: displayPageTemplateNameBlogsEntry,
		});

		await displayPageTemplatesPage.createTemplate({
			contentSubtype: 'Basic Document',
			contentType: 'Document',
			name: displayPageTemplateNameDocuments,
		});

		await displayPageTemplatesPage.createTemplate({
			contentType: 'Category',
			name: displayPageTemplateNameCategory,
		});
	});

	await test.step('Click on the view more button to select a new display page template from the modal', async () => {
		await styleBooksPage.goto(site.friendlyUrlPath);

		await styleBooksPage.create(styleBookName);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('button', {name: 'More'}),
			trigger: page.getByRole('button', {
				name: displayPageTemplateNameCategory,
			}),
		});

		await page
			.frameLocator('iframe[title="Select"]')
			.getByRole('button', {
				name: 'Select ' + displayPageTemplateNameWebContent1,
			})
			.click();

		const loadingAnimation = page.locator('.loading-animation').first();

		await loadingAnimation.waitFor();

		await loadingAnimation.waitFor({state: 'hidden'});

		expect(
			page.getByRole('button', {name: displayPageTemplateNameWebContent1})
		).toBeVisible();

		await styleBooksPage.publish();
	});
});
