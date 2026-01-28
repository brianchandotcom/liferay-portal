/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {appManagerPagesTest} from '../../../fixtures/appManagerPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {masterPagesPagesTest} from '../../../fixtures/masterPagesPagesTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageTemplatesPagesTest} from '../../../fixtures/pageTemplatesPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import {StyleBooksPage} from '../../../pages/style-book-web/StyleBooksPage';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	pagesAdminPagesTest,
	loginTest(),
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	pageEditorPagesTest,
	styleBookPageTest,
	pagesAdminPagesTest,
	masterPagesPagesTest,
	pageTemplatesPagesTest,
	appManagerPagesTest
);

const COLORS = {
	GREEN_HEX: '#00FF00',
	GREEN_RGB: 'rgb(0, 255, 0)',
};

const FRAGMENTS = {
	BASIC_COMPONENTS: 'Basic Components',
	BUTTON_PRIMARY: 'Button Primary',
};

const STYLEBOOK_NAME = getRandomString();

const TOKENS = {
	BACKGROUND_COLOR: 'Background Color',
};

const expectButtonBackgroundColor = async (
	styleBooksPage: StyleBooksPage,
	color: string
) => {
	const button = styleBooksPage.framePreview
		.getByRole('link', {name: 'Go Somewhere'})
		.first();

	await button.waitFor();
	await expect(button).toHaveCSS('background-color', color);
};

test.describe('Style Book Editor Preview', () => {
	test.beforeEach(async ({site, styleBooksPage}) => {
		await test.step('Create a style book', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await styleBooksPage.create(STYLEBOOK_NAME);
		});
	});

	test(
		'The user can preview the effects on current site fragments in styles book editor.',
		{tag: '@LPS-140774'},
		async ({
			apiHelpers,
			page,
			pageEditorPage,
			pagesAdminPage,
			site,
			styleBooksPage,
		}) => {
			const fragmentCollectionName = getRandomString();

			const fragmentEntryName = getRandomString();

			const previewFragmentExample =
				styleBooksPage.framePreview.locator('.fragment-example');

			const pageName = getRandomString();

			await test.step('Create custom fragment', async () => {
				const {fragmentCollectionId} =
					await apiHelpers.jsonWebServicesFragmentCollection.addFragmentCollection(
						{
							groupId: site.id,
							name: fragmentCollectionName,
						}
					);

				await apiHelpers.jsonWebServicesFragmentEntry.addFragmentEntry({
					fragmentCollectionId,
					groupId: site.id,
					html: `<div class="fragment-example">
				  <h1>Example fragment</h1>
				</div>`,
					name: fragmentEntryName,
					type: 'component',
				});
			});

			await test.step('Create a new page then add the new custom fragment', async () => {
				await pagesAdminPage.goto(site.friendlyUrlPath);

				await pagesAdminPage.createNewPage({
					draft: true,
					name: pageName,
				});

				await pageEditorPage.addFragment(
					fragmentCollectionName,
					fragmentEntryName
				);

				await pageEditorPage.waitForChangesSaved();

				await pageEditorPage.publishPage();
			});

			await test.step('View the custom fragment set is shown in dropdown list of preview item selector', async () => {
				await styleBooksPage.goto(site.friendlyUrlPath);

				await styleBooksPage.edit(STYLEBOOK_NAME);

				await page
					.getByRole('button', {name: 'Fragments'})
					.or(page.getByRole('button', {name: 'Pages'}))
					.click();

				await page.getByRole('menuitem', {name: 'Fragments'}).click();

				await expect(
					page.getByRole('button', {name: fragmentCollectionName})
				).toBeVisible();

				await page
					.getByRole('button', {name: fragmentCollectionName})
					.click();
			});

			await test.step('Assert that the custom fragment is shown in preview page of the custom set fragment collection', async () => {
				await expect(previewFragmentExample).toHaveText(
					'Example fragment'
				);
			});

			await test.step('Define the Heading 1 Font Size of Headings', async () => {
				await styleBooksPage.selectTokenCategory('Typography');

				await styleBooksPage.updateTokenInput(
					'Heading 1 Font Size',
					'2',
					'Headings'
				);

				await styleBooksPage.waitForAutoSave();
			});

			await test.step('View the defined Heading 1 Font Size applied on Heading fragment', async () => {
				await expect(previewFragmentExample.locator('h1')).toHaveCSS(
					'font-size',
					'32px'
				);
			});
		}
	);

	test(
		'The user can preview the effects on default fragments in styles book editor.',
		{tag: '@LPS-140774'},
		async ({site, styleBooksPage}) => {
			await test.step('Select the Basic Components set for preview', async () => {
				await styleBooksPage.goto(site.friendlyUrlPath);

				await styleBooksPage.edit(STYLEBOOK_NAME);

				await styleBooksPage.previewFragmentCollection(
					FRAGMENTS.BASIC_COMPONENTS
				);
			});

			await test.step('Define the background color for button primary', async () => {
				await styleBooksPage.selectTokenCategory('Buttons');

				await styleBooksPage.updateTokenInput(
					TOKENS.BACKGROUND_COLOR,
					COLORS.GREEN_HEX,
					FRAGMENTS.BUTTON_PRIMARY
				);

				await styleBooksPage.waitForAutoSave();
			});

			await test.step('View the defined background color is applied on button primary fragment', async () => {
				await expectButtonBackgroundColor(
					styleBooksPage,
					COLORS.GREEN_RGB
				);
			});
		}
	);

	test(
		'The designer could preview the effects of styles on Master Page.',
		{tag: '@LPS-137065'},
		async ({
			masterPagesPage,
			page,
			pageEditorPage,
			site,
			styleBooksPage,
		}) => {
			const masterPageName = getRandomString();

			await test.step('Create new Master Page with a Button fragment', async () => {
				await masterPagesPage.goto(site.friendlyUrlPath);

				await masterPagesPage.createNewMaster(masterPageName);

				await masterPagesPage.editMaster(masterPageName);

				await pageEditorPage.addFragment(
					FRAGMENTS.BASIC_COMPONENTS,
					'Button'
				);

				await pageEditorPage.waitForChangesSaved();

				await pageEditorPage.publishPage();
			});

			await test.step('View the Masters is shown in preview type selector', async () => {
				await styleBooksPage.goto(site.friendlyUrlPath);

				await styleBooksPage.edit(STYLEBOOK_NAME);

				await expect(
					page.getByRole('button', {name: 'Masters'})
				).toBeVisible();
				await expect(
					page.getByRole('button', {name: masterPageName})
				).toBeVisible();
			});

			await test.step('Define the background color for button primary', async () => {
				await styleBooksPage.selectTokenCategory('Buttons');

				await styleBooksPage.updateTokenInput(
					TOKENS.BACKGROUND_COLOR,
					COLORS.GREEN_HEX,
					FRAGMENTS.BUTTON_PRIMARY
				);

				await styleBooksPage.waitForAutoSave();
			});

			await test.step('View the defined background color is applied on button primary fragment', async () => {
				await expectButtonBackgroundColor(
					styleBooksPage,
					COLORS.GREEN_RGB
				);
			});
		}
	);

	test(
		'The designer could preview the effects of styles on all page templates.',
		{tag: '@LPS-137065'},
		async ({
			page,
			pageEditorPage,
			pageTemplatesPage,
			site,
			styleBooksPage,
		}) => {
			const firstContentPageTemplateName = getRandomString();
			const secondContentPageTemplateName = getRandomString();

			await test.step('Create new Template Collection and two new Template Page with a Button fragment', async () => {
				await pageTemplatesPage.goto(site.friendlyUrlPath);

				const pageTemplateCollectionName = getRandomString();

				await pageTemplatesPage.addPageTemplateCollection(
					pageTemplateCollectionName
				);

				// Create content pages template with button fragment

				for (const name of [
					firstContentPageTemplateName,
					secondContentPageTemplateName,
				]) {
					await pageTemplatesPage.addContentPageTemplate(name);
					await pageEditorPage.addFragment(
						FRAGMENTS.BASIC_COMPONENTS,
						'Button',
						undefined,
						undefined
					);
					await pageEditorPage.waitForChangesSaved();
					await pageEditorPage.publishPage();
				}
			});

			await test.step('View the second Content Page Template created is selected in preview type selector', async () => {
				await styleBooksPage.goto(site.friendlyUrlPath);

				await styleBooksPage.edit(STYLEBOOK_NAME);

				await expect(
					page.getByRole('button', {name: 'Page Templates'})
				).toBeVisible();
				await expect(
					page.getByRole('button', {
						name: secondContentPageTemplateName,
					})
				).toBeVisible();
			});

			await test.step('Define the background color for button primary', async () => {
				await styleBooksPage.selectTokenCategory('Buttons');

				await styleBooksPage.updateTokenInput(
					TOKENS.BACKGROUND_COLOR,
					COLORS.GREEN_HEX,
					FRAGMENTS.BUTTON_PRIMARY
				);

				await styleBooksPage.waitForAutoSave();
			});

			await test.step('View the defined background color is applied on second Content Page Template', async () => {
				await expectButtonBackgroundColor(
					styleBooksPage,
					COLORS.GREEN_RGB
				);
			});

			await test.step('Select the first Content Page Template on preview item selector', async () => {
				await page
					.getByRole('button', {name: secondContentPageTemplateName})
					.click();

				await expect(
					page.getByRole('menuitem', {
						name: firstContentPageTemplateName,
					})
				).toBeVisible();

				await page
					.getByRole('menuitem', {name: firstContentPageTemplateName})
					.click();
			});

			await test.step('View the defined background color is applied on first Content Page Template', async () => {
				await expectButtonBackgroundColor(
					styleBooksPage,
					COLORS.GREEN_RGB
				);
			});
		}
	);

	test(
		'The user could view the empty state when the site is empty.',
		{tag: ['@LPS-137065', '@LPS-116078']},
		async ({appManagerPage, page, site, styleBooksPage}) => {
			test.slow();

			const APP_SYMBOLIC_NAMES = [
				'com.liferay.fragment.collection.contributor.basic.component',
				'com.liferay.fragment.collection.contributor.cookie.banner',
				'com.liferay.fragment.collection.contributor.featured.content',
				'com.liferay.fragment.collection.contributor.footers',
				'com.liferay.fragment.collection.contributor.inputs',
				'com.liferay.fragment.collection.contributor.navigation.bars',
				'com.liferay.commerce.fragment.collection.contributor.account',
				'com.liferay.commerce.fragment.collection.contributor.cart',
			];

			try {
				await test.step('Deactivate the Default fragment sets', async () => {
					for (const name of APP_SYMBOLIC_NAMES) {
						await appManagerPage.deactivateApp(name, true);
					}
				});

				await test.step('View the empty state in style book editor', async () => {
					await styleBooksPage.goto(site.friendlyUrlPath);

					await styleBooksPage.edit(STYLEBOOK_NAME);

					await expect(
						page.getByText(
							'You cannot preview the style book because your site is empty.'
						)
					).toBeVisible();

					await expect(
						page.locator('.style-book-editor__preview-selector')
					).not.toBeVisible();

					await expect(
						page.getByRole('button', {name: 'Publish'})
					).toBeVisible();
				});
			}
			finally {
				await test.step('Activate the Default fragment sets', async () => {
					for (const name of APP_SYMBOLIC_NAMES) {
						await appManagerPage.activateApp(name, true);
					}
				});
			}
		}
	);
});

test(
	'The user can preview the effects on global fragments in styles book editor.',
	{tag: '@LPS-140774'},
	async ({styleBooksPage}) => {
		await test.step('Create a style book', async () => {
			await styleBooksPage.goto('/global');

			await styleBooksPage.create(STYLEBOOK_NAME);
		});

		await test.step('Select the Basic Components set for preview', async () => {
			await styleBooksPage.previewFragmentCollection(
				FRAGMENTS.BASIC_COMPONENTS
			);
		});

		await test.step('Define the background color for button primary', async () => {
			await styleBooksPage.selectTokenCategory('Buttons');

			await styleBooksPage.updateTokenInput(
				TOKENS.BACKGROUND_COLOR,
				COLORS.GREEN_HEX,
				FRAGMENTS.BUTTON_PRIMARY
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('View the defined background color is applied on button primary fragment', async () => {
			await expectButtonBackgroundColor(styleBooksPage, COLORS.GREEN_RGB);
		});

		await test.step('Delete the created style book', async () => {
			await styleBooksPage.goto('/global');

			await styleBooksPage.delete(STYLEBOOK_NAME);
		});
	}
);
