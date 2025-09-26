/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {frontendSPAInfrastructureConfigurationTest} from '../../../../../fixtures/frontendSPAInfrastructureConfigurationTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {EFDSVisualizationMode, waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';
import {FDSSamplePage} from '../../pages/FDSSamplePage';

interface IStateInURL {
	delta: number;
	filters: any[];
	page: number;
	q: string;
	sorts: any[];
	vf: any;
	view: string;
}

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	frontendSPAInfrastructureConfigurationTest,
	featureFlagsTest({
		'LPD-22473': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

const getStateFromURL = (url: string, fdsId: string): Partial<IStateInURL> => {
	const params = new URLSearchParams(url);

	const stateParam = params.get(
		`com_liferay_frontend_data_set_sample_web_internal_portlet_FDSSamplePortlet-${fdsId}_fdsState`
	);

	if (!stateParam) {
		return null;
	}

	let state = {};

	try {
		state = JSON.parse(stateParam);
	}
	catch (error) {
		return null;
	}

	return state;
};

const assertDelta = async (
	delta: number,
	fdsId: string,
	fdsSamplePage: FDSSamplePage,
	page: Page
) => {
	const state = getStateFromURL(new URL(page.url()).search, fdsId);

	expect(state.delta).toBe(delta);

	await expect(fdsSamplePage.paginator.itemsPerPageSelector).toHaveText(
		`${delta} Items`
	);
};

const assertView = async (
	fdsId: string,
	page: Page,
	visualizationMode: EFDSVisualizationMode,
	viewName?: string
) => {
	const state = getStateFromURL(new URL(page.url()).search, fdsId);

	await waitForFDS({page, visualizationMode});

	expect(state.view).toBe(viewName ? viewName : visualizationMode);
};

const changeDelta = async (
	delta: number,
	fdsId: string,
	fdsSamplePage: FDSSamplePage,
	page: Page
) => {
	await fdsSamplePage.changeItemsPerPage({delta: `${delta} Items`});

	await waitForFDS({page, visualizationMode: EFDSVisualizationMode.TABLE});

	await assertDelta(delta, fdsId, fdsSamplePage, page);
};

const spaConfigurations = [
	{
		configure: async ({frontendSPAInfrastructureConfigurationPage}) => {
			await frontendSPAInfrastructureConfigurationPage.goto();
			await frontendSPAInfrastructureConfigurationPage.disableSPA();
		},
		name: 'SPA is disabled',
	},
	{
		configure: async ({frontendSPAInfrastructureConfigurationPage}) => {
			await frontendSPAInfrastructureConfigurationPage.goto();
			await frontendSPAInfrastructureConfigurationPage.enableSPA();
		},
		name: 'SPA is enabled',
	},
];

for (const spaConfiguration of spaConfigurations) {
	test.describe(`State in URL, ${spaConfiguration.name}`, () => {
		test.beforeEach(
			async ({
				fdsSamplePage,
				frontendSPAInfrastructureConfigurationPage,
				page,
				site,
			}) => {
				await test.step('Configure SPA', async () => {
					await spaConfiguration.configure({
						frontendSPAInfrastructureConfigurationPage,
					});
				});

				await fdsSamplePage.setupFDSSampleWidget({site});

				await fdsSamplePage.selectTab('Advanced');

				await waitForFDS({
					page,
					visualizationMode: EFDSVisualizationMode.TABLE,
				});
			}
		);

		test(
			'URL in state, push history, delta',
			{tag: '@LPD-20947'},
			async ({fdsSamplePage, page}) => {
				const setDelta = (delta) =>
					changeDelta(delta, 'advanced', fdsSamplePage, page);

				const checkDelta = (delta) =>
					assertDelta(delta, 'advanced', fdsSamplePage, page);

				await test.step('Change delta via UI several times', async () => {
					await setDelta(40);
					await setDelta(60);
					await setDelta(20);
				});

				await test.step('Check back navigation', async () => {
					await page.goBack();
					await checkDelta(60);
					await page.goBack();
					await checkDelta(40);

					// initial delta is 20

					await page.goBack();
					await checkDelta(20);
				});

				await test.step('Check forward navigation', async () => {
					await page.goForward();
					await checkDelta(40);
					await page.goForward();
					await checkDelta(60);
					await page.goForward();
					await checkDelta(20);
				});

				await test.step('Mix navigation and change via UI', async () => {
					await page.goBack();
					await checkDelta(60);

					await setDelta(40); // last delta value (20) is removed from history

					await page.goBack();
					await checkDelta(60);
					await page.goForward();
					await checkDelta(40);
					expect(await page.goForward()).toBeNull();
				});
			}
		);

		test(
			'URL in state, push history, view name',
			{tag: '@LPD-20947'},
			async ({fdsSamplePage, page}) => {
				const checkView = (visualizationMode: EFDSVisualizationMode) =>
					assertView(
						'advanced',
						page,
						visualizationMode,
						visualizationMode === EFDSVisualizationMode.TABLE
							? 'customizedTable'
							: undefined
					);

				await test.step('Change view name via UI several times', async () => {
					await fdsSamplePage.changeVisualizationMode({
						page,
						visualizationMode: EFDSVisualizationMode.CARDS,
					});
					await checkView(EFDSVisualizationMode.CARDS);
					await fdsSamplePage.changeVisualizationMode({
						page,
						visualizationMode: EFDSVisualizationMode.LIST,
					});
					await checkView(EFDSVisualizationMode.LIST);
					await fdsSamplePage.changeVisualizationMode({
						page,
						visualizationMode: EFDSVisualizationMode.TABLE,
					});
					await checkView(EFDSVisualizationMode.TABLE);
				});

				await test.step('Check back navigation', async () => {
					await page.goBack();
					await checkView(EFDSVisualizationMode.LIST);
					await page.goBack();
					await checkView(EFDSVisualizationMode.CARDS);

					// initial view is table

					await page.goBack();
					await checkView(EFDSVisualizationMode.TABLE);
				});

				await test.step('Check forward navigation', async () => {
					await page.goForward();
					await checkView(EFDSVisualizationMode.CARDS);
					await page.goForward();
					await checkView(EFDSVisualizationMode.LIST);
					await page.goForward();
					await checkView(EFDSVisualizationMode.TABLE);
				});

				await test.step('Mix navigation and change via UI', async () => {
					await page.goBack();
					await checkView(EFDSVisualizationMode.LIST);
					await fdsSamplePage.changeVisualizationMode({
						page,
						visualizationMode: EFDSVisualizationMode.CARDS,
					});

					// last view value (table) is removed from history

					await page.goBack();
					await checkView(EFDSVisualizationMode.LIST);
					await page.goForward();
					await checkView(EFDSVisualizationMode.CARDS);
					expect(await page.goForward()).toBeNull();
				});
			}
		);

		test(
			'URL in state, replace history',
			{tag: '@LPD-20947'},
			async ({fdsSamplePage, page}) => {
				const checkDelta = (delta) =>
					assertDelta(
						delta,
						'customInternalView',
						fdsSamplePage,
						page
					);

				const checkView = (visualizationMode: EFDSVisualizationMode) =>
					assertView('customInternalView', page, visualizationMode);

				const setDelta = (delta) =>
					changeDelta(
						delta,
						'customInternalView',
						fdsSamplePage,
						page
					);

				await test.step('Change to custom view component tab', async () => {
					await fdsSamplePage.selectTab('Custom Internal View');
					await page.locator('.fds-carousel-view-sample').waitFor();
				});

				await test.step('Make several state changes via UI', async () => {
					await fdsSamplePage.changeVisualizationMode({
						page,
						visualizationMode: EFDSVisualizationMode.TABLE,
					});
					await checkView(EFDSVisualizationMode.TABLE);

					await setDelta(40);
				});

				await test.step('Check back navigation', async () => {
					await page.goBack();

					// we are now in the advanced sample because we are not stacking state changes onto URL history

					await assertView(
						'advanced',
						page,
						EFDSVisualizationMode.TABLE,
						'customizedTable'
					);
					await assertDelta(20, 'advanced', fdsSamplePage, page);
				});

				await test.step('Check forward navigation', async () => {
					await page.goForward();
					await checkView(EFDSVisualizationMode.TABLE);
					await checkDelta(40);
				});
			}
		);

		test(
			'URL in state, push history, page number',
			{tag: '@LPD-20947'},
			async ({page}) => {
				const assertPageNumber = async (
					pageNumber: number,
					fdsId: string,
					page: Page
				) => {
					const state = getStateFromURL(
						new URL(page.url()).search,
						fdsId
					);

					expect(state.page).toBe(pageNumber);
				};

				const changePageNumber = async (
					pageNumber: number,
					fdsId: string,
					page: Page
				) => {
					await page.getByLabel(`Go to page, ${pageNumber}`).click();

					await waitForFDS({
						page,
						visualizationMode: EFDSVisualizationMode.TABLE,
					});

					await assertPageNumber(pageNumber, fdsId, page);
				};

				const setPageNumber = (pageNumber: number) =>
					changePageNumber(pageNumber, 'advanced', page);

				const checkPageNumber = (pageNumber: number) =>
					assertPageNumber(pageNumber, 'advanced', page);

				await test.step('Change page number via UI several times', async () => {
					await setPageNumber(2);
					await setPageNumber(3);
					await setPageNumber(1);
				});

				await test.step('Check back navigation', async () => {
					await page.goBack();
					await checkPageNumber(3);

					await page.goBack();
					await checkPageNumber(2);

					await page.goBack();
					await checkPageNumber(1);
				});

				await test.step('Check forward navigation', async () => {
					await page.goForward();
					await checkPageNumber(2);

					await page.goForward();
					await checkPageNumber(3);

					await page.goForward();
					await checkPageNumber(1);
				});

				await test.step('Mix navigation and change via UI', async () => {
					await page.goBack();
					await checkPageNumber(3);

					await setPageNumber(2);

					await page.goBack();
					await checkPageNumber(3);

					await page.goForward();
					await checkPageNumber(2);

					expect(await page.goForward()).toBeNull();
				});
			}
		);

		test(
			'URL in state, push history, sorting',
			{tag: '@LPD-20947'},
			async ({fdsSamplePage, page}) => {
				await test.step('Click on Sortable Content button', async () => {
					const secondColumnHeader = page
						.getByRole('columnheader')
						.nth(2);

					const sortButton = secondColumnHeader.getByRole('button');

					await sortButton.click();
				});

				await test.step('Check sorting in the UI', async () => {
					const tableBodyRows = await page
						.locator('.table tbody tr')
						.all();

					const textsFromSecondColumn = await Promise.all(
						tableBodyRows.map((row) =>
							row.locator('td').nth(2).innerText()
						)
					);

					textsFromSecondColumn.forEach((text, index) => {
						if (index < textsFromSecondColumn.length - 1) {
							expect(
								text > textsFromSecondColumn[index + 1]
							).toBeTruthy();
						}
					});
				});

				await test.step('Check back navigation', async () => {
					await page.goBack();

					const state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.sorts).toBeDefined();
					expect(state.sorts).toHaveLength(1);
					expect(state.sorts[0].key).toBe('title');
					expect(state.sorts[0].direction).toBe('asc');
				});

				await test.step('Check forward navigation', async () => {
					await page.goForward();

					const state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);
					expect(state.sorts).toBeDefined();
					expect(state.sorts).toHaveLength(1);
					expect(state.sorts[0].key).toBe('title');
					expect(state.sorts[0].direction).toBe('desc');
				});

				await test.step('Mix navigation and change via UI', async () => {
					await page.goBack();

					let state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.sorts).toBeDefined();
					expect(state.sorts).toHaveLength(1);
					expect(state.sorts[0].key).toBe('title');
					expect(state.sorts[0].direction).toBe('asc');

					const secondColumnHeader = page
						.getByRole('columnheader')
						.nth(2);

					const sortButton = secondColumnHeader.getByRole('button');

					await sortButton.click();

					await waitForFDS({
						page,
						visualizationMode: EFDSVisualizationMode.TABLE,
					});

					state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.sorts).toBeDefined();
					expect(state.sorts).toHaveLength(1);
					expect(state.sorts[0].key).toBe('title');
					expect(state.sorts[0].direction).toBe('desc');

					await page.goBack();

					state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.sorts).toBeDefined();
					expect(state.sorts).toHaveLength(1);
					expect(state.sorts[0].key).toBe('title');
					expect(state.sorts[0].direction).toBe('asc');

					await page.goForward();

					state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.sorts).toBeDefined();
					expect(state.sorts).toHaveLength(1);
					expect(state.sorts[0].key).toBe('title');
					expect(state.sorts[0].direction).toBe('desc');

					expect(await page.goForward()).toBeNull();
				});
			}
		);

		test(
			'URL in state, push history, search parameter',
			{tag: '@LPD-20947'},
			async ({fdsSamplePage, page}) => {
				const assertSearchParam = async (
					searchParam: string,
					fdsId: string,
					page: Page
				) => {
					const state = getStateFromURL(
						new URL(page.url()).search,
						fdsId
					);

					if (searchParam) {
						expect(state.q).toBe(searchParam);
					}
					else {
						expect(state.q).toBeUndefined();
					}
				};

				const changeSearchParam = async (
					searchParam: string,
					fdsId: string,
					fdsSamplePage: FDSSamplePage,
					page: Page
				) => {
					await fdsSamplePage.managementToolbar.searchInput.fill(
						searchParam
					);
					await fdsSamplePage.managementToolbar.searchButton.click();

					await page.waitForTimeout(1000);

					await assertSearchParam(searchParam, fdsId, page);
				};

				const setSearchParam = (searchParam: string) =>
					changeSearchParam(
						searchParam,
						'advanced',
						fdsSamplePage,
						page
					);

				const checkSearchParam = (searchParam: string) =>
					assertSearchParam(searchParam, 'advanced', page);

				await test.step('Change search parameter via UI several times', async () => {
					await setSearchParam('test1');
					await setSearchParam('test2');
					await setSearchParam('test3');
				});

				await test.step('Check back navigation', async () => {
					await page.goBack();
					await checkSearchParam('test2');
					await page.goBack();
					await checkSearchParam('test1');

					await page.goBack();
					await checkSearchParam('');
				});

				await test.step('Check forward navigation', async () => {
					await page.goForward();
					await checkSearchParam('test1');
					await page.goForward();
					await checkSearchParam('test2');
					await page.goForward();
					await checkSearchParam('test3');
				});

				await test.step('Mix navigation and change via UI', async () => {
					await page.goBack();
					await checkSearchParam('test2');

					await setSearchParam('test4');

					await page.goBack();
					await checkSearchParam('test2');
					await page.goForward();
					await checkSearchParam('test4');
					expect(await page.goForward()).toBeNull();
				});
			}
		);

		test(
			'URL in state is turned off by default',
			{tag: '@LPD-20947'},
			async ({fdsSamplePage, page}) => {
				await test.step('Change to single selection tab', async () => {
					await fdsSamplePage.selectTab('Single Selection');
					await waitForFDS({
						page,
						visualizationMode: EFDSVisualizationMode.TABLE,
					});
				});

				await test.step('Assert there is no state un URL', async () => {
					expect(
						getStateFromURL(
							new URL(page.url()).search,
							'singleSelection'
						)
					).toBeNull();
				});
			}
		);

		test(
			'URL in state, push history, visible fields',
			{tag: '@LPD-20947'},
			async ({fdsSamplePage, page}) => {
				const initialBodyCellText = await page
					.locator('td')
					.nth(1)
					.innerText();

				await test.step('Update visible fields via UI', async () => {
					const button = page.getByLabel('Manage Columns Visibility');

					await expect(button).toBeAttached();

					await button.click();

					const menuItem = page.getByRole('menuitem').nth(0);

					await menuItem.click();
				});

				await test.step('Check visible fields in the UI', async () => {
					await expect(page.locator('td').nth(1)).not.toHaveText(
						initialBodyCellText
					);

					await expect(fdsSamplePage.table.headerCells).toHaveCount(
						9
					);
				});

				await test.step('Assert that the URL state is updated', async () => {
					const state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.vf).toBeDefined();
					expect(state.vf.id).toBe(false);
				});

				await test.step('Check back navigation', async () => {
					await page.goBack();

					const state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);
					expect(state.vf).toBeUndefined();
				});

				await test.step('Check forward navigation', async () => {
					await page.goForward();

					const state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);
					expect(state.vf).toBeDefined();
					expect(state.vf.id).toBe(false);
				});

				await test.step('Mix navigation and change via UI', async () => {
					await page.goBack();

					let state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);
					expect(state.vf).toBeUndefined();

					await fdsSamplePage.table.manageColumnsVisibilityButton.click();
					await page.getByRole('menu').waitFor();

					const titleMenuItem = page
						.getByRole('menu')
						.getByRole('menuitem', {name: 'title'});

					await titleMenuItem.click();

					await page.waitForTimeout(1000);

					state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.vf).toBeDefined();
					expect(state.vf.title).toBe(false);

					await page.goBack();

					state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.vf).toBeUndefined();

					await page.goForward();

					state = getStateFromURL(
						new URL(page.url()).search,
						'advanced'
					);

					expect(state.vf).toBeDefined();
					expect(state.vf.title).toBe(false);

					expect(await page.goForward()).toBeNull();
				});
			}
		);
	});
}
