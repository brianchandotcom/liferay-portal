/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';
import {visualizationModesPageTest} from './fixtures/visualizationModesPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	visualizationModesPageTest,
	loginTest(),
	dataSetManagerSetupTest
);

let dataSetERC: string;
let viewERC: string;

const dataSetLabel: string = getRandomString();
const viewLabel: string = getRandomString();

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	viewERC = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
	await dataSetManagerApiHelpers.createDataSetView({
		erc: viewERC,
		label: viewLabel,
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC: dataSetERC,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({
		erc: dataSetERC,
	});
});

test.describe('Visualization Modes in Data Set Manager', () => {
	test('Configure cards visualization mode @LPD-10735', async ({
		visualizationModesPage,
	}) => {
		await test.step('Navigate to cards visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
				viewLabel,
			});

			await visualizationModesPage.selectTab('Cards');

			await expect(
				visualizationModesPage.cardsVisualizationModeContainer
			).toBeVisible();
		});

		await test.step('Check if cards sections are correct', async () => {
			await expect(
				visualizationModesPage.cardsVisualizationModeContainer.locator(
					'.cards-section-label'
				)
			).toHaveText([
				'Card Element',
				'Title',
				'Description',
				'Image',
				'Symbol',
			]);
		});

		await test.step('Assign a field to title section', async () => {
			const fieldName = 'name';
			const sectionLabel = 'Title';

			const container =
				visualizationModesPage.cardsVisualizationModeContainer;

			await visualizationModesPage.openAssignFieldModal({
				container,
				sectionLabel,
			});

			await visualizationModesPage.fieldSelectModalContainer
				.getByLabel(fieldName)
				.click();

			await expect(
				visualizationModesPage.page.getByLabel(fieldName)
			).toBeChecked();

			await visualizationModesPage.saveFieldSelection();

			const assignedFieldLocator =
				await visualizationModesPage.getAssignedFieldLocator({
					container,
					sectionLabel,
				});

			expect(assignedFieldLocator).toHaveText(fieldName);
		});

		await test.step('Edit field to title section', async () => {
			const newFieldName = 'rendererType';
			const oldFieldName = 'name';
			const sectionLabel = 'Title';

			const container =
				visualizationModesPage.cardsVisualizationModeContainer;

			await visualizationModesPage.openChangeFieldModal({
				container,
				sectionLabel,
			});

			await expect(
				visualizationModesPage.page.getByLabel(oldFieldName)
			).toBeChecked();

			await visualizationModesPage.fieldSelectModalContainer
				.getByLabel(newFieldName)
				.click();

			await expect(
				visualizationModesPage.page.getByLabel(newFieldName)
			).toBeChecked();

			await visualizationModesPage.saveFieldSelection();

			const assignedFieldLocator =
				await visualizationModesPage.getAssignedFieldLocator({
					container,
					sectionLabel,
				});

			expect(assignedFieldLocator).toHaveText(newFieldName);
		});
	});

	test('Configure list visualization mode @LPD-10735', async ({
		visualizationModesPage,
	}) => {
		await test.step('Navigate to list visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
				viewLabel,
			});

			await visualizationModesPage.selectTab('List');

			await expect(
				visualizationModesPage.listVisualizationModeContainer
			).toBeVisible();
		});

		await test.step('Check if list sections are correct', async () => {
			await expect(
				visualizationModesPage.listVisualizationModeContainer.locator(
					'.list-section-label'
				)
			).toHaveText([
				'List Element',
				'Title',
				'Description',
				'Image',
				'Symbol',
			]);
		});

		await test.step('Assign a field to title section', async () => {
			const fieldName = 'name';
			const sectionLabel = 'Title';

			const container =
				visualizationModesPage.listVisualizationModeContainer;

			await visualizationModesPage.openAssignFieldModal({
				container,
				sectionLabel,
			});

			await visualizationModesPage.fieldSelectModalContainer
				.getByLabel(fieldName)
				.click();

			await expect(
				visualizationModesPage.page.getByLabel(fieldName)
			).toBeChecked();

			await visualizationModesPage.saveFieldSelection();

			const assignedFieldLocator =
				await visualizationModesPage.getAssignedFieldLocator({
					container,
					sectionLabel,
				});

			expect(assignedFieldLocator).toHaveText(fieldName);
		});

		await test.step('Edit field to title section', async () => {
			const newFieldName = 'rendererType';
			const oldFieldName = 'name';
			const sectionLabel = 'Title';

			const container =
				visualizationModesPage.listVisualizationModeContainer;

			await visualizationModesPage.openChangeFieldModal({
				container,
				sectionLabel,
			});

			await expect(
				visualizationModesPage.page.getByLabel(oldFieldName)
			).toBeChecked();

			await visualizationModesPage.fieldSelectModalContainer
				.getByLabel(newFieldName)
				.click();

			await expect(
				visualizationModesPage.page.getByLabel(newFieldName)
			).toBeChecked();

			await visualizationModesPage.saveFieldSelection();

			const assignedFieldLocator =
				await visualizationModesPage.getAssignedFieldLocator({
					container,
					sectionLabel,
				});

			expect(assignedFieldLocator).toHaveText(newFieldName);
		});
	});

	test('Configure table visualization mode @LPD-11049', async ({
		visualizationModesPage,
	}) => {
		const SAMPLE_SCALAR_FIELD = 'id';
		const SAMPLE_OBJECT_FIELD = 'fdsViewFDSFieldRelationship';
		const SAMPLE_OBJECT_CHILD_FIELD = 'id';
		const SORTABLE_COLUMN_INDEX = 5;

		await test.step('Navigate to table visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
				viewLabel,
			});

			await visualizationModesPage.selectTab('Table');

			await expect(
				visualizationModesPage.page.getByPlaceholder('Search')
			).toBeVisible();
		});

		await test.step('Add fields', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.addRootField(SAMPLE_SCALAR_FIELD);
			await visualizationModesPage.addRootField(SAMPLE_OBJECT_FIELD);
			await visualizationModesPage.addChildField(
				[SAMPLE_OBJECT_FIELD],
				SAMPLE_OBJECT_CHILD_FIELD
			);

			await visualizationModesPage.saveAddFieldsModal();
		});

		await test.step('Check if field defaults are correct', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('true');

			await expect(
				visualizationModesPage
					.getRowByText(`${SAMPLE_OBJECT_FIELD}.*`)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('false');

			await expect(
				visualizationModesPage
					.getRowByText(
						`${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`
					)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('true');
		});

		await test.step('Edit a field', async () => {
			await visualizationModesPage
				.getRowByText(SAMPLE_SCALAR_FIELD)
				.locator('.actions-cell button')
				.click();

			const editButton = visualizationModesPage.page.getByRole(
				'menuitem',
				{
					name: 'Edit',
				}
			);

			await expect(editButton).toBeInViewport();

			await editButton.click();

			const sortableInput =
				visualizationModesPage.page.getByLabel('Sortable');

			await expect(sortableInput).toBeInViewport();
			await expect(sortableInput).toBeEnabled();
			await expect(sortableInput).toBeChecked();

			await sortableInput.click();

			await expect(sortableInput).not.toBeChecked();

			await visualizationModesPage.saveAddFieldsModal();

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('false');
		});

		await test.step('Check if object field has disabled sortable option', async () => {
			await visualizationModesPage
				.getRowByText(`${SAMPLE_OBJECT_FIELD}.*`)
				.locator('.actions-cell button')
				.click();

			const editButton = visualizationModesPage.page.getByRole(
				'menuitem',
				{
					name: 'Edit',
				}
			);

			await expect(editButton).toBeInViewport();

			await editButton.click();

			const sortableLabel =
				visualizationModesPage.page.getByLabel('Sortable');

			await expect(sortableLabel).toBeInViewport();

			await expect(sortableLabel).toBeDisabled();

			await visualizationModesPage.cancelAddFieldsModal();
		});
	});

	test('Configure table visualization mode with scalar array fields @LPD-11769', async ({
		visualizationModesPage,
	}) => {
		const SAMPLE_SCALAR_ARRAY_FIELD = 'keywords';
		const TYPE_COLUMN_INDEX = 3;

		await test.step('Navigate to table visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
				viewLabel,
			});

			await visualizationModesPage.selectTab('Table');

			await expect(
				visualizationModesPage.page.getByPlaceholder('Search')
			).toBeVisible();
		});

		await test.step('Add scalar array field', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.addRootField(
				SAMPLE_SCALAR_ARRAY_FIELD
			);

			await visualizationModesPage.saveAddFieldsModal();
		});

		await test.step('Check if field shows the correct type', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_ARRAY_FIELD)
					.locator('td')
					.nth(TYPE_COLUMN_INDEX)
			).toHaveText('array');
		});
	});
});

export const fragmentTest = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	fdsFragmentPageTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	loginTest(),
	dataSetManagerSetupTest,
	isolatedSiteTest,
);

fragmentTest.describe('Visualization Modes in the fragment', () => {
	fragmentTest(
		'Show mapped fields in the fragment',
		async ({
			apiHelpers,
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			page,
			site,
		}) => {
			const SAMPLE_SCALAR_FIELD = 'id';
			const SAMPLE_OBJECT_FIELD = 'fdsViewFDSFieldRelationship';
			const SAMPLE_OBJECT_CHILD_FIELD = 'label';

			await fragmentTest.step('Create table fields', async () => {
				await dataSetManagerApiHelpers.createDataSetViewFields({
					label_i18n: {en_US: 'Label'},
					name: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: viewERC,
					type: 'string',
				});
				await dataSetManagerApiHelpers.createDataSetViewFields({
					label_i18n: {en_US: 'Id'},
					name: `${SAMPLE_SCALAR_FIELD}`,
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: viewERC,
					type: 'string',
				});
			});

			await fragmentTest.step('Create cards section fields', async () => {
				await dataSetManagerApiHelpers.createDataSetViewCardsSection({
					fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
					name: 'title',
					r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC: viewERC,
				});
				await dataSetManagerApiHelpers.createDataSetViewCardsSection({
					fieldName: `${SAMPLE_SCALAR_FIELD}`,
					name: 'description',
					r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC: viewERC,
				});
			});

			await fragmentTest.step('Create list section fields', async () => {
				await dataSetManagerApiHelpers.createDataSetViewListSection({
					fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
					name: 'title',
					r_fdsViewFDSListSectionRelationship_c_fdsViewERC: viewERC,
				});
				await dataSetManagerApiHelpers.createDataSetViewListSection({
					fieldName: `${SAMPLE_SCALAR_FIELD}`,
					name: 'description',
					r_fdsViewFDSListSectionRelationship_c_fdsViewERC: viewERC,
				});
			});

			const layout = await fragmentTest.step(
				'Create a new page',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel,
					});
				}
			);

			await fragmentTest.step(
				'Check Data Set Cards are present',
				async () => {
					await fdsFragmentPage.fdsCardsWrapper.waitFor({
						state: 'visible',
					});

					expect(
						await fdsFragmentPage.fdsCardsWrapper
					).toBeInViewport();

					await fdsFragmentPage.page
						.locator('.card')
						.first()
						.waitFor();

					const firstCard = await fdsFragmentPage.page
						.locator('.card')
						.first();

					expect(
						await firstCard.locator('.card-title')
					).toContainText(viewLabel);

					expect(
						await firstCard.locator('.card-subtitle')
					).not.toBeEmpty();
				}
			);

			await fragmentTest.step(
				'Change visualization mode to List',
				async () => {
					await fdsFragmentPage.changeVisualizationMode('List');
				}
			);

			await fragmentTest.step(
				'Check Data Set List is present',
				async () => {
					await fdsFragmentPage.fdsListWrapper.waitFor({
						state: 'visible',
					});

					expect(
						await fdsFragmentPage.fdsListWrapper
					).toBeInViewport();

					await fdsFragmentPage.page
						.locator('.list-group-item')
						.first()
						.waitFor();

					const firstListItem = await fdsFragmentPage.page
						.locator('.list-group-item')
						.first();

					expect(
						await firstListItem.locator('.list-group-title')
					).toContainText(viewLabel);

					expect(
						await firstListItem.locator('.list-group-text')
					).not.toBeEmpty();
				}
			);

			await fragmentTest.step(
				'Change visualization mode to Table',
				async () => {
					await fdsFragmentPage.changeVisualizationMode('Table');
				}
			);

			await fragmentTest.step(
				'Data Set Table is in the page',
				async () => {
					await fdsFragmentPage.fdsTableWrapper.waitFor({
						state: 'visible',
					});

					expect(
						await fdsFragmentPage.fdsTableWrapper
					).toBeInViewport();

					expect(
						await page
							.locator('.dnd-thead > div')
							.first()
							.locator('.dnd-th')
							.allInnerTexts()
					).toEqual(['Label', 'Id', '']);
				}
			);
		}
	);

	fragmentTest(
		'Show mapped scalar array field in the fragment @LPD-11769',
		async ({
			apiHelpers,
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			page,
			site,
		}) => {
			const SAMPLE_SCALAR_ARRAY_FIELD = 'keywords';
			const SAMPLE_SCALAR_ARRAY_CONTENT = ['one', 'two', 'three'];

			await fragmentTest.step('Create table fields', async () => {
				await dataSetManagerApiHelpers.createDataSetViewFields({
					extraBodyParams: {
						keywords: SAMPLE_SCALAR_ARRAY_CONTENT,
					},
					label_i18n: {en_US: SAMPLE_SCALAR_ARRAY_FIELD},
					name: SAMPLE_SCALAR_ARRAY_FIELD,
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: viewERC,
					type: 'array',
				});
			});

			const layout = await fragmentTest.step(
				'Create a new page',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel,
					});
				}
			);

			await fragmentTest.step(
				'Data Set Table is in the page',
				async () => {
					await fdsFragmentPage.fdsTableWrapper.waitFor({
						state: 'visible',
					});

					expect(
						await fdsFragmentPage.fdsTableWrapper
					).toBeInViewport();

					expect(
						await page
							.locator('.dnd-thead > div')
							.first()
							.locator('.dnd-th')
							.allInnerTexts()
					).toEqual([SAMPLE_SCALAR_ARRAY_FIELD, '']);

					expect(
						await page
							.locator('.dnd-tbody > div')
							.first()
							.locator('.dnd-td')
							.allInnerTexts()
					).toEqual(['one, two, three', '']);
				}
			);
		}
	);
});
