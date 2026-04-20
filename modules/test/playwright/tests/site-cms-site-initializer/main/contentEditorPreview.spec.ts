/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';
import {DisplayPageTemplatesPage} from '../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {structureBuilderPagesTest} from '../structure-builder/fixtures/structureBuilderPagesTest';
import {cmsPagesTest} from './fixtures/cmsPagesTest';
import {ContentsPage} from './pages/ContentsPage';
import {SpaceSummaryPage} from './pages/SpaceSummaryPage';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	fragmentsPagesTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-44507': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	structureBuilderPagesTest
);

const createAndEditContent = async ({
	contentsPage,
	page,
	spaceName,
	title,
}: {
	contentsPage: ContentsPage;
	page: Page;
	spaceName?: string;
	title: string;
}) => {
	await contentsPage.goto();

	await contentsPage.createContent('Basic Web Content', spaceName);

	await page.getByLabel('Title').fill(title);

	await contentsPage.saveContent();

	await contentsPage.editContent(title);
};

const createDisplayPageTemplate = async ({
	displayPageTemplateName,
	displayPageTemplatesPage,
	page,
	pageEditorPage,
	site,
}: {
	displayPageTemplateName: string;
	displayPageTemplatesPage: DisplayPageTemplatesPage;
	page: Page;
	pageEditorPage: PageEditorPage;
	site: Site;
}) => {
	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	await displayPageTemplatesPage.createTemplate({
		contentType: 'Basic Web Content',
		name: displayPageTemplateName,
	});

	await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

	await pageEditorPage.addFragment('Basic Components', 'Heading');

	const headingId = await pageEditorPage.getFragmentId('Heading');

	await pageEditorPage.selectEditable(headingId, 'element-text');

	await page
		.getByRole('combobox', {exact: true, name: 'Field'})
		.selectOption('Friendly URL');

	await pageEditorPage.waitForChangesSaved();

	await pageEditorPage.publishPage();
};

const createSpaceAndConnectSites = async ({
	apiHelpers,
	site,
	spaceName,
	spaceSummaryPage,
}: {
	apiHelpers: ApiHelpers;
	site: Site;
	spaceName: string;
	spaceSummaryPage: SpaceSummaryPage;
}) => {
	await apiHelpers.headlessAssetLibrary.createAssetLibrary({
		name: spaceName,
		settings: {},
		type: 'Space',
	});

	await spaceSummaryPage.goto(spaceName);
	await spaceSummaryPage.connectSite('Global');
	await spaceSummaryPage.connectSite(site.name);
};

test(
	'The content preview opens, resizes and closes correctly, taking focus into account',
	{tag: '@LPD-84613'},
	async ({contentsPage, page}) => {
		const title = getRandomString();
		const previewTitle = `${title} Preview`;

		try {
			await test.step('Create a new basic web content and edit it', async () => {
				await createAndEditContent({contentsPage, page, title});
			});

			const preview = page.getByLabel(previewTitle);

			await test.step('Open the content preview', async () => {
				await page.getByRole('button', {name: 'Open Preview'}).click();

				await expect(page.getByText(previewTitle)).toBeVisible();
				await expect(preview).toBeFocused();
			});

			await test.step('Resize the content preview', async () => {
				await page.keyboard.press('Tab');

				await expect(
					preview.getByRole('button', {name: 'Close Preview'})
				).toBeFocused();

				await page.keyboard.press('Tab');
				await page.keyboard.press('Tab');
				await page.keyboard.press('Tab');

				const resizeHandle = preview.getByRole('separator');

				await expect(resizeHandle).toBeFocused();

				const initWidth = (await preview.boundingBox())?.width ?? 0;
				const resizeHandleBox = await resizeHandle.boundingBox();

				if (resizeHandleBox) {
					await page.mouse.move(
						resizeHandleBox.x + resizeHandleBox.width / 2,
						100
					);
					await page.mouse.down();
					await page.mouse.move(
						resizeHandleBox.x + resizeHandleBox.width / 2 + 200,
						100,
						{steps: 20}
					);
					await page.mouse.up();
				}

				expect((await preview.boundingBox())?.width).toBeLessThan(
					initWidth
				);
			});

			await test.step('Close the content preview from the close button', async () => {
				await preview
					.getByRole('button', {name: 'Close Preview'})
					.click();

				await expect(contentsPage.previewButton).toBeFocused();
				await expect(page.getByText(previewTitle)).not.toBeVisible();
			});

			await test.step('Close the content preview from the preview button', async () => {
				await page.getByRole('button', {name: 'Open Preview'}).click();

				await expect(page.getByText(previewTitle)).toBeVisible();

				await page
					.locator('.content-editor__toolbar')
					.getByRole('button', {name: 'Close Preview'})
					.click();

				await expect(contentsPage.previewButton).toBeFocused();
				await expect(page.getByText(previewTitle)).not.toBeVisible();
			});
		}
		finally {
			await test.step('Delete content', async () => {
				await contentsPage.goto();

				await contentsPage.deleteContent(title);
			});
		}
	}
);

test(
	'Preview a content',
	{tag: '@LPD-85584'},
	async ({
		apiHelpers,
		contentsPage,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
		spaceSummaryPage,
	}) => {
		const displayPageTemplateName = getRandomString();
		const title = getRandomString();
		const previewTitle = `${title} Preview`;
		const spaceName = `Space ${getRandomString()}`;

		try {
			await test.step('Create a display page template', async () => {
				await createDisplayPageTemplate({
					displayPageTemplateName,
					displayPageTemplatesPage,
					page,
					pageEditorPage,
					site,
				});
			});

			await test.step('Create a space and sites to the space', async () => {
				await createSpaceAndConnectSites({
					apiHelpers,
					site,
					spaceName,
					spaceSummaryPage,
				});
			});

			await test.step('Create a new basic web content and edit it', async () => {
				await createAndEditContent({
					contentsPage,
					page,
					spaceName,
					title,
				});
			});

			await test.step('Go to the content preview and select a channel that does not have a display page template', async () => {
				await contentsPage.previewButton.click();

				await expect(page.getByText(previewTitle)).toBeVisible();

				await clickAndExpectToBeVisible({
					autoClick: true,
					target: page.getByRole('option', {
						name: 'Global',
					}),
					trigger: page.getByLabel('Select Channel'),
				});

				await expect(
					page.getByText(
						'No display page templates available for preview in this channel'
					)
				).toBeVisible();
			});

			await test.step('Select a channel and a display page template', async () => {
				await clickAndExpectToBeVisible({
					autoClick: true,
					target: page.getByRole('option', {
						name: site.name,
					}),
					trigger: page.getByLabel('Select Channel'),
				});

				await expect(
					page.getByText(
						'No display page templates available for preview in this channel'
					)
				).not.toBeVisible();

				await clickAndExpectToBeVisible({
					autoClick: true,
					target: page.getByRole('option', {
						name: displayPageTemplateName,
					}),
					trigger: page.getByLabel('Select Display Page'),
				});
			});

			await test.step('Check that the display page appears in the preview', async () => {
				const friendlyURL = await page
					.getByLabel('Friendly URL')
					.inputValue();

				const iframe = page.frameLocator('iframe');

				await expect(iframe.getByText(friendlyURL)).toBeVisible();
			});

			await test.step('Check the button to open the preview in another tab', async () => {
				const openPreviewNewTabButton = page.getByTitle(
					'Open Preview in a new tab.'
				);

				await expect(openPreviewNewTabButton).toBeVisible();
				await expect(openPreviewNewTabButton).toHaveRole('link');
				await expect(openPreviewNewTabButton).toHaveAttribute(
					'target',
					'_blank'
				);
			});
		}
		finally {
			await test.step('Delete content', async () => {
				await contentsPage.goto();

				await contentsPage.deleteContent(title);
			});
		}
	}
);

test(
	'Preview a content from the preview modal in mobile view',
	{tag: '@LPD-85583'},
	async ({
		apiHelpers,
		contentsPage,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
		spaceSummaryPage,
	}) => {
		const displayPageTemplateName = getRandomString();
		const title = getRandomString();
		const modalTitle = `Preview ${title}`;
		const spaceName = `Space ${getRandomString()}`;

		try {
			await test.step('Create a display page template', async () => {
				await createDisplayPageTemplate({
					displayPageTemplateName,
					displayPageTemplatesPage,
					page,
					pageEditorPage,
					site,
				});
			});

			await test.step('Create a space and sites to the space', async () => {
				await createSpaceAndConnectSites({
					apiHelpers,
					site,
					spaceName,
					spaceSummaryPage,
				});
			});

			await test.step('Create a new basic web content and edit it', async () => {
				await createAndEditContent({
					contentsPage,
					page,
					spaceName,
					title,
				});
			});

			await test.step('Set viewport to mobile size', async () => {
				await page.setViewportSize({height: 932, width: 430});
			});

			await test.step('Open the preview modal from the mobile preview button', async () => {
				await page
					.locator('.content-editor__toolbar')
					.getByTitle('Preview')
					.click();

				await expect(page.getByText(modalTitle)).toBeVisible();
			});

			await test.step('Select a channel without display page templates and check that the alert appears', async () => {
				await clickAndExpectToBeVisible({
					autoClick: true,
					target: page.getByRole('option', {
						name: 'Global',
					}),
					trigger: page.getByLabel('Select Channel'),
				});

				await expect(
					page.getByText(
						'No display page templates available for preview in this channel'
					)
				).toBeVisible();

				const previewInNewTabButton = page.getByRole('button', {
					name: 'Preview in a new tab',
				});

				await expect(previewInNewTabButton).toBeDisabled();
			});

			await test.step('Select a channel with display page templates and check that the preview button is a link', async () => {
				await clickAndExpectToBeVisible({
					autoClick: true,
					target: page.getByRole('option', {
						name: site.name,
					}),
					trigger: page.getByLabel('Select Channel'),
				});

				await expect(
					page.getByText(
						'No display page templates available for preview in this channel'
					)
				).not.toBeVisible();

				await clickAndExpectToBeVisible({
					autoClick: true,
					target: page.getByRole('option', {
						name: displayPageTemplateName,
					}),
					trigger: page.getByLabel('Select Display Page'),
				});

				const previewInNewTabLink = page.getByRole('link', {
					name: 'Preview in a new tab',
				});

				await expect(previewInNewTabLink).toHaveAttribute('href', /.+/);
				await expect(previewInNewTabLink).toHaveAttribute(
					'target',
					'_blank'
				);
			});
		}
		finally {
			await test.step('Delete content', async () => {
				await contentsPage.goto();

				await contentsPage.deleteContent(title);
			});
		}
	}
);
