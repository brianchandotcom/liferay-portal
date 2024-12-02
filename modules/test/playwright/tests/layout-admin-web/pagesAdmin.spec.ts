/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-35220': true,
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest
);

test.describe('Keyboard movement and navigation', () => {
	test(
		'Keyboard movement works as expected',
		{tag: ['@LPD-35221']},
		async ({apiHelpers, page, pagesAdminPage, site}) => {

			// Create five pages and go to admin page

			for (const i of Array(5).keys()) {
				await apiHelpers.headlessDelivery.createSitePage({
					siteId: site.id,
					title: `Page ${i}`,
				});
			}

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await page
				.locator('.miller-columns-item', {hasText: 'Page 1'})
				.waitFor();

			// Check keyboard movement behavior
			// Move item 3 on top of item 2

			const getItem = (index: number) =>
				page.locator('.miller-columns-item', {
					hasText: `Page ${index}`,
				});

			const enableMovement = async (index: number) =>
				await page.getByLabel(`Move Page ${index}`).press('Enter');

			await expect(async () => {
				await enableMovement(3);

				await expect(
					page.locator('.drag-preview__content', {hasText: 'Page 3'})
				).toBeVisible({timeout: 1000});

				await expect(getItem(3)).toHaveClass(/drop-bottom/, {
					timeout: 1000,
				});
			}).toPass();

			await expect(async () => {
				await page.keyboard.press('ArrowUp');

				await expect(getItem(2)).toHaveClass(/drop-middle/, {
					timeout: 500,
				});
			}).toPass();

			await page.keyboard.press('ArrowUp');

			await expect(getItem(2)).toHaveClass(/drop-top/);

			await page.keyboard.press('Enter');

			await expect(
				page.locator('.miller-columns-item').nth(2)
			).toContainText('Page 3');

			// Check moved item keeps its handler focused

			await expect(getItem(3).locator('.drag-handler')).toBeFocused();

			// Move item 0 inside item 1

			await enableMovement(0);

			await expect(async () => {
				await page.keyboard.press('ArrowDown');

				await expect(getItem(1)).toHaveClass(/drop-middle/, {
					timeout: 500,
				});
			}).toPass();

			await page.keyboard.press('Enter');

			await expect(
				page
					.locator('.miller-columns-col')
					.nth(1)
					.locator('.miller-columns-item')
			).toContainText('Page 0');

			// Check source item is skipped when changing target

			await enableMovement(3);

			await expect(async () => {
				await page.keyboard.press('ArrowUp');

				await expect(getItem(1)).toHaveClass(/drop-middle/, {
					timeout: 500,
				});
			}).toPass();

			await page.keyboard.press('ArrowDown');

			await expect(getItem(2)).toHaveClass(/drop-middle/);

			// Check Escape cancels the movement

			await page.keyboard.press('Escape');

			await expect(getItem(2)).not.toHaveClass(/drop-middle/);

			// Check it's possible to move several items at a time

			await page.getByLabel('Select Page 1').click();
			await page.getByLabel('Select Page 3').click();

			await enableMovement(1);

			const page1Styles = await getItem(1).evaluate((element) =>
				getComputedStyle(element)
			);
			const page3Styles = await getItem(3).evaluate((element) =>
				getComputedStyle(element)
			);

			expect(page1Styles.opacity).toBe('0.4');
			expect(page3Styles.opacity).toBe('0.4');
		}
	);

	test(
		'Keyboard navigation works as expected',
		{tag: ['@LPD-35946']},
		async ({apiHelpers, page, pagesAdminPage, site}) => {

			// Create five pages and go to admin page

			for (const i of Array(5).keys()) {
				await apiHelpers.headlessDelivery.createSitePage({
					siteId: site.id,
					title: `Page ${i}`,
				});
			}

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await page
				.locator('.miller-columns-item', {hasText: 'Page 1'})
				.waitFor();

			// Focus until reach the first item

			const getItem = (index: number) =>
				page.locator('.miller-columns-item', {
					hasText: `Page ${index}`,
				});

			await expect(async () => {
				await page.keyboard.press('Tab');

				await expect(
					getItem(0).locator('.miller-columns-item-mask')
				).toBeFocused({timeout: 500});
			}).toPass();

			// Check we can go to last item with End key

			await page.keyboard.press('End');

			await expect(
				getItem(4).locator('.miller-columns-item-mask')
			).toBeFocused();

			// Check we can go to first item with Home key

			await page.keyboard.press('Home');

			await expect(
				getItem(0).locator('.miller-columns-item-mask')
			).toBeFocused();

			// Move to second and third item and check the focus moves well

			await page.keyboard.press('ArrowDown');

			await expect(
				getItem(1).locator('.miller-columns-item-mask')
			).toBeFocused();

			await page.keyboard.press('ArrowDown');

			await expect(
				getItem(2).locator('.miller-columns-item-mask')
			).toBeFocused();

			// Check we can move to item content with tab

			await page.keyboard.press('Tab');

			await expect(page.getByLabel('Move Page 2')).toBeFocused();

			// Move item 4 inside item 3

			const enableMovement = async (index: number) =>
				await page.getByLabel(`Move Page ${index}`).press('Enter');

			await enableMovement(4);

			await expect(async () => {
				await page.keyboard.press('ArrowUp');

				await expect(getItem(3)).toHaveClass(/drop-middle/, {
					timeout: 500,
				});
			}).toPass();

			await page.keyboard.press('Enter');

			await expect(
				page
					.locator('.miller-columns-col')
					.nth(1)
					.locator('.miller-columns-item')
			).toContainText('Page 4');

			// Check we can come back to parent with left arrow

			await page.keyboard.press('ArrowLeft');

			await expect(
				getItem(3).locator('.miller-columns-item-mask')
			).toBeFocused();
		}
	);

	test(
		'Accessibility checks',
		{tag: ['@LPD-35946']},
		async ({apiHelpers, page, pagesAdminPage, site}) => {

			// Create two pages at first level

			const firstPage = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'Page 1-1',
			});

			await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'Page 1-2',
			});

			// Create two pages at second level as child of Page 1-1

			await apiHelpers.headlessDelivery.createSitePage({
				parentSitePage: {
					friendlyUrlPath: firstPage.friendlyUrlPath,
				},
				siteId: site.id,
				title: 'Page 2-1',
			});

			await apiHelpers.headlessDelivery.createSitePage({
				parentSitePage: {
					friendlyUrlPath: firstPage.friendlyUrlPath,
				},
				siteId: site.id,
				title: 'Page 2-2',
			});

			// Go to pages admin

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await page
				.locator('.miller-columns-item', {hasText: 'Page 1-1'})
				.waitFor();

			// Focus until reach Page 1-1 item drag handler

			const getItem = (title: string) =>
				page.locator('.miller-columns-item', {hasText: title});

			await expect(async () => {
				await page.keyboard.press('Tab');

				await expect(
					getItem('Page 1-1').locator('.drag-handler')
				).toBeFocused({timeout: 500});
			}).toPass();

			// Enable and cancel movement and check drag handler keeps focus

			await page.getByLabel('Move Page 1-1').press('Enter');

			await expect(getItem('Page 1-1')).toHaveClass(/dragging/);

			await page.keyboard.press('Escape');

			await expect(getItem('Page 1-1')).not.toHaveClass(/dragging/);

			await expect(
				getItem('Page 1-1').locator('.drag-handler')
			).toBeFocused();

			// Focus the anchor again and press Enter to check item keeps focus after navigate

			await page.keyboard.press('Shift+Tab');

			await expect(
				getItem('Page 1-1').locator('.miller-columns-item-mask')
			).toBeFocused();

			await page.keyboard.press('Enter');

			await expect(getItem('Page 2-2')).toBeVisible();

			await expect(
				getItem('Page 1-1').locator('.miller-columns-item-mask')
			).toBeFocused();

			// Check loading children with Arrow Right

			await getItem('Page 1-2')
				.locator('.miller-columns-item-mask')
				.press('Enter');

			await expect(getItem('Page 2-1')).not.toBeVisible();

			await page.keyboard.press('ArrowUp');

			await expect(
				getItem('Page 1-1').locator('.miller-columns-item-mask')
			).toBeFocused();

			await page.keyboard.press('ArrowRight');

			await expect(
				getItem('Page 2-1').locator('.miller-columns-item-mask')
			).toBeFocused();

			// Check dropdowns work well

			await expect(async () => {
				await page.keyboard.press('Tab');

				await expect(
					getItem('Page 2-1').getByLabel('Add Child Page')
				).toBeFocused({timeout: 500});
			}).toPass();

			await page.keyboard.press('Enter');

			await expect(
				page.getByRole('menuitem', {name: 'Add Page'})
			).toBeVisible();

			await page.keyboard.press('Escape');

			await expect(
				getItem('Page 2-1').getByLabel('Add Child Page')
			).toBeFocused();

			await page.keyboard.press('Tab');

			await expect(
				getItem('Page 2-1').getByLabel('Open Page Options Menu')
			).toBeFocused();

			await page.keyboard.press('Enter');

			await expect(
				page.getByRole('menuitem', {name: 'Edit'})
			).toBeVisible();

			await page.keyboard.press('Escape');

			await expect(
				getItem('Page 2-1').getByLabel('Open Page Options Menu')
			).toBeFocused();

			// Check title link works well

			await expect(async () => {
				await page.keyboard.press('Shift+Tab');

				await expect(
					getItem('Page 2-1').getByRole('link', {name: 'Page 2-1'})
				).toBeFocused({timeout: 500});
			}).toPass();

			await page.keyboard.press('Enter');

			await expect(getItem('Page 1-1')).not.toBeVisible();
		}
	);
});

test.describe('Miller Columns drag and drop', () => {
	test(
		'Drag and drop with mouse works as expected',
		{tag: ['@LPS-110108', '@LPS-114527', '@LPS-110108']},
		async ({apiHelpers, page, pagesAdminPage, site}) => {

			// Create two pages at first level

			const firstPage = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'Page 1-1',
			});

			await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'Page 1-2',
			});

			// Create two pages at second level as child of Page 1-1

			await apiHelpers.headlessDelivery.createSitePage({
				parentSitePage: {
					friendlyUrlPath: firstPage.friendlyUrlPath,
				},
				siteId: site.id,
				title: 'Page 2-1',
			});

			await apiHelpers.headlessDelivery.createSitePage({
				parentSitePage: {
					friendlyUrlPath: firstPage.friendlyUrlPath,
				},
				siteId: site.id,
				title: 'Page 2-2',
			});

			// Go to pages administration and click Page 1-1 to show its children

			const getColumn = (index: number) =>
				page.locator('.miller-columns-col').nth(index);

			const getItem = (title: string) =>
				page.locator('.miller-columns-item', {hasText: title});

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await getItem('Page 1-1').click();

			await expect(getItem('Page 2-1')).toBeVisible();

			// Drag Page 1-1 to second position of same level

			const itemRect = await getItem('Page 1-1').evaluate((element) =>
				element.getBoundingClientRect()
			);

			const dragItem = async (
				source: Locator,
				target: Locator,
				position: 'bottom' | 'middle' | 'top'
			) => {
				let targetPosition: {x: number; y: number};

				if (position === 'bottom') {
					targetPosition = {
						x: itemRect.width / 2,
						y: itemRect.height - 2,
					};
				}
				else if (position === 'top') {
					targetPosition = {
						x: itemRect.width / 2,
						y: itemRect.y + 2,
					};
				}
				else {
					targetPosition = {
						x: itemRect.width / 2,
						y: itemRect.height / 2,
					};
				}

				await source.locator('.drag-handler').dragTo(target, {
					targetPosition,
				});
			};

			const checkItemTitle = async ({
				columnIndex,
				itemIndex,
				title,
			}: {
				columnIndex: number;
				itemIndex: number;
				title: string;
			}) => {
				await expect(
					getColumn(columnIndex)
						.locator(
							'.miller-columns-item .miller-columns-item-mask'
						)
						.nth(itemIndex)
				).toHaveText(title);
			};

			await dragItem(getItem('Page 1-1'), getItem('Page 1-2'), 'bottom');

			await checkItemTitle({
				columnIndex: 0,
				itemIndex: 1,
				title: 'Page 1-1',
			});

			// Move child page to parent level

			await dragItem(getItem('Page 2-1'), getItem('Page 1-2'), 'bottom');

			await checkItemTitle({
				columnIndex: 0,
				itemIndex: 1,
				title: 'Page 2-1',
			});

			// Move two pages from different levels

			await getItem('Page 1-1').click();

			await expect(getItem('Page 2-2')).toBeVisible();

			await page.getByLabel('Select Page 1-2').click();
			await page.getByLabel('Select Page 2-2').click();

			await dragItem(getItem('Page 1-2'), getItem('Page 1-1'), 'bottom');

			await checkItemTitle({
				columnIndex: 0,
				itemIndex: 2,
				title: 'Page 1-2',
			});

			await checkItemTitle({
				columnIndex: 0,
				itemIndex: 3,
				title: 'Page 2-2',
			});

			// Move two pages from same level

			await page.getByLabel('Select Page 2-1').click();
			await page.getByLabel('Select Page 1-1').click();

			await dragItem(getItem('Page 2-1'), getItem('Page 2-2'), 'bottom');

			await checkItemTitle({
				columnIndex: 0,
				itemIndex: 2,
				title: 'Page 2-1',
			});

			await checkItemTitle({
				columnIndex: 0,
				itemIndex: 3,
				title: 'Page 1-1',
			});
		}
	);
});

test('Changes the permissions of a group of pages', async ({
	apiHelpers,
	page,
	pagesAdminPage,
	site,
}) => {

	// Create two random pages

	const firstName = getRandomString();
	const secondName = getRandomString();

	for (const pageName of [firstName, secondName]) {
		await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: pageName,
		});
	}

	// Go to admin page

	await pagesAdminPage.goto(site.friendlyUrlPath);

	// Change permissions for first page

	await pagesAdminPage.changePagesPermissions(
		[firstName],
		['guest_ACTION_VIEW']
	);

	// Select first and second page and open the modal of permissions

	await pagesAdminPage.selectPages([firstName, secondName]);

	await page.getByRole('button', {name: 'Permissions'}).click();

	const permissionsFrame = page.frameLocator('iframe[title="Permissions"]');

	await permissionsFrame
		.getByRole('cell', {exact: true, name: 'Role'})
		.waitFor();

	// Check that the Guest-View permission value for both pages is indeterminate

	const permission = permissionsFrame.locator('#guest_ACTION_VIEW');

	await expect(permission).toHaveValue('indeterminate');

	await page.getByLabel('close', {exact: true}).click();

	// Change the Guest-View permission for both pages

	await pagesAdminPage.changePagesPermissions(
		[firstName, secondName],
		['guest_ACTION_VIEW']
	);

	// Refresh the admin page

	await pagesAdminPage.goto(site.friendlyUrlPath);

	// Check if the pages are retricted pages

	for (const pageName of [firstName, secondName]) {
		await expect(
			page.getByLabel(`${pageName}. Restricted Page`)
		).toBeVisible();
	}
});

test('Checks the correct label for restricted pages in pages administration', async ({
	apiHelpers,
	page,
	pagesAdminPage,
	site,
}) => {

	// Create a page with only one permission

	const pageName = getRandomString();

	await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	// Go to admin page and check if the Restricted Page label is in the Miller Columns item

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await expect(
		page
			.locator('.miller-columns-item')
			.getByLabel(`${pageName}. Restricted Page`)
	).toBeVisible();
});

test('Can add and delete a child page', async ({
	apiHelpers,
	page,
	pagesAdminPage,
	site,
}) => {

	// Create parent page

	const parentPageName = getRandomString();

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: parentPageName,
	});

	// Create child page and check it actually appears as child

	const childPageName = getRandomString();

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pagesAdminPage.createNewPage({
		draft: true,
		name: childPageName,
		parent: parentPageName,
	});

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await page
		.getByRole('menuitem', {exact: true, name: parentPageName})
		.click({position: {x: 10, y: 10}});

	await expect(page.getByRole('link', {name: childPageName})).toBeVisible();

	// Check Draft label is shown and we can preview the draft

	await expect(
		page
			.locator('li', {has: page.getByText(childPageName)})
			.getByText('Draft')
	).toBeVisible();

	await clickAndExpectToBeVisible({
		target: page.getByRole('menuitem', {
			name: 'Preview Draft',
		}),
		trigger: page
			.locator('li', {has: page.getByText(childPageName)})
			.getByRole('button', {name: 'Open Page Options Menu'}),
	});

	// Delete child page

	await pagesAdminPage.deletePage(childPageName);

	await expect(
		page.getByRole('link', {name: childPageName})
	).not.toBeVisible();
});

test(
	'Can search a child page',
	{tag: ['@LPS-154130', '@LPS-149161', '@LPS-76825']},
	async ({apiHelpers, page, pagesAdminPage, site}) => {

		// Add a child page

		const layoutTitle = 'Parent Layout';

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: layoutTitle,
		});

		const childLayoutTitle = 'Child Layout';

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			parentLayoutId: layout.layoutId,
			title: childLayoutTitle,
		});

		// Go to admin page

		await pagesAdminPage.goto(site.friendlyUrlPath);

		// Search for parent page

		await pagesAdminPage.searchPage('Parent');

		await expect(
			page
				.locator('.lfr-title-column')
				.getByRole('link', {name: childLayoutTitle})
		).not.toBeVisible();

		await expect(
			page
				.locator('.lfr-title-column')
				.getByRole('link', {name: layoutTitle})
		).toBeVisible();

		// Search for child page

		await pagesAdminPage.searchPage('Child');

		await expect(
			page
				.locator('.lfr-title-column')
				.getByRole('link', {name: childLayoutTitle})
		).toBeVisible();

		await expect(
			page
				.locator('.lfr-title-column')
				.getByRole('link', {name: layoutTitle})
		).not.toBeVisible();

		// Order by create date

		await pagesAdminPage.searchPage('Layout');

		const listItem = page.locator('.lfr-title-column');

		await expect(listItem.nth(1)).toHaveText(layoutTitle);
		await expect(listItem.nth(2)).toHaveText(childLayoutTitle);

		// Navigate to page via relative path

		await page
			.locator('.breadcrumb-item')
			.getByRole('link', {name: layoutTitle})
			.click();

		await expect(page.getByText('Search Results')).not.toBeVisible();

		await expect(page.locator('.breadcrumb-item.active')).toHaveText(
			layoutTitle
		);
	}
);

test(
	'View the XSS is escaped when store it in widget page name',
	{
		tag: '@LPS-178476',
	},
	async ({apiHelpers, page, pagesAdminPage, site}) => {

		// Add listener with expect so it fails when a browser dialog is shown

		page.on('dialog', async (dialog) => {
			dialog.accept();

			expect(
				dialog.message(),
				'This alert should not be shown'
			).toBeNull();
		});

		// Create page and go to view mode to check dialog is not shown

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: '<script>alert(123);</script>',
		});

		await pagesAdminPage.goto(site.friendlyUrlPath);
	}
);

test(
	'toastData parameter is escaped to avoid Javascript execution',
	{
		tag: '@LPD-35827',
	},
	async ({page}) => {

		// Add listener with expect so it fails when a browser dialog is shown

		page.on('dialog', async (dialog) => {
			dialog.accept();

			expect(
				dialog.message(),
				'This alert should not be shown'
			).toBeNull();
		});

		const data = {
			message: '<img src=x onerror=alert(123)>',
			title: 'test',
		};

		const url = page.url();

		await page.goto(
			`${url}?toastData=${encodeURIComponent(JSON.stringify(data))}`
		);
	}
);

test(
	'The sort button for pages is not shown',
	{
		tag: '@LPD-36041',
	},
	async ({apiHelpers, page, pagesAdminPage, site}) => {

		// Create a page

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		// Go to admin page

		await pagesAdminPage.goto(site.friendlyUrlPath);

		// Check the button is not shown

		await expect(
			page.getByLabel('Reverse Order Direction:')
		).not.toBeAttached();
	}
);

test(
	'Can resize columns',
	{
		tag: '@LPD-36861',
	},
	async ({apiHelpers, page, pagesAdminPage, site}) => {

		// Create a page

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		// Go to admin page

		await pagesAdminPage.goto(site.friendlyUrlPath);

		// Check the column have the default layout

		await expect(page.locator('.miller-columns-col')).toHaveClass(
			/col-lg-4 col-md-6 col-11/
		);

		// Check that we can resize the column

		const resizeColumn = page.getByLabel('Resize column');

		await resizeColumn.focus();

		await expect(resizeColumn).toBeFocused();

		await page.keyboard.press('Home');

		await expect(page.locator('.miller-columns-col')).not.toHaveClass(
			/col-lg-4 col-md-6 col-11/
		);

		await expect(page.locator('.miller-columns-col')).toHaveAttribute(
			'style',
			'max-width: 286px; min-width: 286px; width: 286px;'
		);

		await page.keyboard.press('ArrowRight');

		await expect(page.locator('.miller-columns-col')).toHaveAttribute(
			'style',
			'max-width: 306px; min-width: 306px; width: 306px;'
		);

		await page.keyboard.press('End');

		await expect(page.locator('.miller-columns-col')).toHaveAttribute(
			'style',
			'max-width: 672px; min-width: 672px; width: 672px;'
		);

		await page.keyboard.press('ArrowLeft');

		await expect(page.locator('.miller-columns-col')).toHaveAttribute(
			'style',
			'max-width: 652px; min-width: 652px; width: 652px;'
		);
	}
);
