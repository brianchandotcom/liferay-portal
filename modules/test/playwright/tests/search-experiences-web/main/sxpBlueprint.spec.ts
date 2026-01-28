/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../fixtures/isolatedLayoutTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {searchExperiencesPagesTest} from '../../../fixtures/searchExperiencesPageTest';
import {searchPageTest} from '../../../fixtures/searchPageTest';
import {DEFAULT_SXP_BLUEPRINT_CONFIGURATION} from '../../../helpers/SearchExperiencesApiHelper';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import getDataStructureDefinition from '../../journal-web/main/utils/getDataStructureDefinition';

export const test = mergeTests(
	isolatedLayoutTest({type: 'portlet'}),
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	pageEditorPagesTest,
	loginTest(),
	searchPageTest,
	searchExperiencesPagesTest
);

test.describe('Table View', () => {
	const tableFieldsList = [
		'Description',
		'ID',
		'Author',
		'Created',
		'Modified',
	];

	test.beforeEach(async ({apiHelpers}) => {
		await test.step('Create blueprint with API', async () => {
			await apiHelpers.searchExperiences.createSXPBlueprint();
		});
	});

	test.afterEach(async ({page, sxpBlueprintsAndElementsViewPage}) => {
		await test.step('Select all blueprint table fields to view', async () => {
			for (const tableField of tableFieldsList) {
				const tableFieldMenuItem = page.getByRole('menuitem', {
					exact: true,
					name: tableField,
				});

				if (!(await tableFieldMenuItem.isVisible())) {
					await sxpBlueprintsAndElementsViewPage.blueprintElementTableOpenFieldsMenuButton.click();
				}

				if (
					!(await tableFieldMenuItem
						.locator('.lexicon-icon-check')
						.isVisible())
				) {
					await tableFieldMenuItem.click();
				}

				await expect(
					sxpBlueprintsAndElementsViewPage.blueprintElementTableHeading
				).toContainText(tableField);
			}
		});
	});

	test('Deselect blueprint table fields from view', async ({
		page,
		sxpBlueprintsAndElementsViewPage,
	}) => {
		await test.step('Navigate to created blueprint', async () => {
			await sxpBlueprintsAndElementsViewPage.goto();
		});

		await test.step('Assert blueprint table fields are available', async () => {
			for (const tableField of tableFieldsList) {
				await expect(
					sxpBlueprintsAndElementsViewPage.blueprintElementTable.getByText(
						tableField,
						{exact: true}
					)
				).toBeVisible();
			}
		});

		await test.step('Toggle off blueprint table fields', async () => {
			for (const tableField of tableFieldsList) {
				const tableFieldMenuItem = page.getByRole('menuitem', {
					exact: true,
					name: tableField,
				});

				if (!(await tableFieldMenuItem.isVisible())) {
					await sxpBlueprintsAndElementsViewPage.blueprintElementTableOpenFieldsMenuButton.click();
				}

				await tableFieldMenuItem.click();

				await expect(
					tableFieldMenuItem.locator('.lexicon-icon-check')
				).not.toBeVisible();
			}
		});

		await test.step('Assert blueprint table fields are not available', async () => {
			for (const tableField of tableFieldsList) {
				await expect(
					sxpBlueprintsAndElementsViewPage.blueprintElementTableHeading
				).not.toContainText(tableField);
			}
		});
	});
});

test.describe('Manual Creation', () => {
	let sxpBlueprintId: string;

	test.beforeEach(
		async ({editSXPBlueprintPage, sxpBlueprintsAndElementsViewPage}) => {
			const sxpBlueprintTitle = `Blueprint${getRandomInt()}`;

			await test.step('Create a blueprint via page', async () => {
				await sxpBlueprintsAndElementsViewPage.goto();

				await sxpBlueprintsAndElementsViewPage.createBlueprint(
					sxpBlueprintTitle
				);
			});

			await test.step('Save ID for created blueprint', async () => {
				await expect(editSXPBlueprintPage.editTitleButton).toHaveText(
					sxpBlueprintTitle
				);

				sxpBlueprintId = await editSXPBlueprintPage.getSXPBlueprintId();
			});
		}
	);

	test.afterEach(async ({apiHelpers}) => {
		if (sxpBlueprintId) {
			await apiHelpers.searchExperiences.deleteSXPBlueprint(
				sxpBlueprintId
			);
		}
	});

	test('Newly created blueprint starts with "Enable All" clause contributors @LPD-22974', async ({
		editSXPBlueprintPage,
	}) => {
		await test.step('Confirm clause contributors is set as Enable All', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.assertSourceRadioPropertySelection(
				'Enable All'
			);
		});
	});
});

test.describe('Data Persistence - Clause Contributors', () => {
	let sxpBlueprint: SXPBlueprint;

	test.beforeEach(async ({apiHelpers, sxpBlueprintsAndElementsViewPage}) => {
		await test.step('Create blueprint with API', async () => {
			sxpBlueprint =
				await apiHelpers.searchExperiences.createSXPBlueprint();
		});

		await test.step('Navigate to created blueprint', async () => {
			await sxpBlueprintsAndElementsViewPage.goto();

			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);
		});
	});

	test('Saving "Enable All" clause contributors persists @LPD-22974', async ({
		editSXPBlueprintPage,
		sxpBlueprintsAndElementsViewPage,
	}) => {
		await test.step('Set clause contributors to "Enable All"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Enable All');

			await editSXPBlueprintPage.saveBlueprint();
		});

		await test.step('Check that "Enable All" setting persists', async () => {
			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);

			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.assertSourceRadioPropertySelection(
				'Enable All'
			);
		});
	});

	test('Saving "Disable All" clause contributors persists @LPD-22974', async ({
		editSXPBlueprintPage,
		sxpBlueprintsAndElementsViewPage,
	}) => {
		await test.step('Set clause contributors to "Disable All"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Disable All');

			await editSXPBlueprintPage.saveBlueprint();
		});

		await test.step('Check that "Disable All" setting persists', async () => {
			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);

			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.assertSourceRadioPropertySelection(
				'Disable All'
			);
		});
	});

	test('Saving "Customize - All Enabled" clause contributors persists @LPD-22974', async ({
		editSXPBlueprintPage,
		sxpBlueprintsAndElementsViewPage,
	}) => {
		await test.step('Set clause contributors to "Customize"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Customize');
		});

		await test.step('Assert all clause contributors are enabled by default', async () => {
			await editSXPBlueprintPage.openClauseContributorsSidebar();

			await editSXPBlueprintPage.assertClauseContributorSelection({
				labels: ['*'],
				value: true,
			});

			await editSXPBlueprintPage.saveBlueprint();
		});

		await test.step('Check that all enabled customized clause contributors persists', async () => {
			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);

			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.assertSourceRadioPropertySelection(
				'Customize'
			);

			await editSXPBlueprintPage.openClauseContributorsSidebar();

			await editSXPBlueprintPage.assertClauseContributorSelection({
				labels: ['*'],
				value: true,
			});
		});
	});

	test('Saving "Customize - All Disabled" clause contributors is set to "Disable All" @LPD-22974', async ({
		editSXPBlueprintPage,
		sxpBlueprintsAndElementsViewPage,
	}) => {
		await test.step('Set clause contributors to "Customize"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Customize');
		});

		await test.step('Disable all contributors', async () => {
			await editSXPBlueprintPage.openClauseContributorsSidebar();

			await editSXPBlueprintPage.selectClauseContributors({
				labels: ['*'],
				value: false,
			});

			await editSXPBlueprintPage.saveBlueprint();
		});

		await test.step('Check that all disabled customized clause contributors persists as "Disable All"', async () => {
			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);

			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.assertSourceRadioPropertySelection(
				'Disable All'
			);
		});
	});

	test('Saving "Customize - Varied" clause contributors persists @LPD-22974', async ({
		editSXPBlueprintPage,
		sxpBlueprintsAndElementsViewPage,
	}) => {
		await test.step('Set clause contributors to "Customize"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Customize');
		});

		await test.step('Vary clause contributors selection', async () => {
			await editSXPBlueprintPage.openClauseContributorsSidebar();

			await editSXPBlueprintPage.selectClauseContributors({
				labels: [
					'Account Entry Keyword Query Contributor',
					'Address Model Pre Filter Contributor',
					'Group Id Query Pre Filter Contributor',
				],
				value: false,
			});

			await editSXPBlueprintPage.saveBlueprint();
		});

		await test.step('Check that customized clause contributors persists', async () => {
			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);

			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.assertSourceRadioPropertySelection(
				'Customize'
			);

			await editSXPBlueprintPage.openClauseContributorsSidebar();

			await editSXPBlueprintPage.assertClauseContributorSelection({
				labels: [
					'Account Entry Keyword Query Contributor',
					'Address Model Pre Filter Contributor',
					'Group Id Query Pre Filter Contributor',
				],
				value: false,
			});
		});
	});
});

test.describe('Search Preview - Clause Contributors', () => {
	let sxpBlueprint: SXPBlueprint;

	test.beforeEach(async ({apiHelpers, sxpBlueprintsAndElementsViewPage}) => {
		await test.step('Create blueprint with API', async () => {
			sxpBlueprint =
				await apiHelpers.searchExperiences.createSXPBlueprint();
		});

		await test.step('Navigate to created blueprint', async () => {
			await sxpBlueprintsAndElementsViewPage.goto();

			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);
		});
	});

	test('Searching with "Enable All" clause contributors returns expected result @LPD-22974', async ({
		editSXPBlueprintPage,
	}) => {
		await test.step('Set clause contributors to "Enable All"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Enable All');
		});

		await test.step('Search for "tree" in preview sidebar and find tree.png', async () => {
			await editSXPBlueprintPage.openPreviewSidebar();

			await editSXPBlueprintPage.searchInPreviewSidebar('tree');

			await editSXPBlueprintPage.assertPreviewSidebarSearchResult(
				'tree.png',
				[
					{
						label: 'entryClassName',
						value: 'com.liferay.document.library.kernel.model.DLFileEntry',
					},
					{label: 'userName', value: 'test test'},
				]
			);
		});
	});

	test('Searching with "Disable All" clause contributors returns no results @LPD-22974', async ({
		editSXPBlueprintPage,
	}) => {
		await test.step('Set clause contributors to "Disable All"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Disable All');
		});

		await test.step('Search for "tree" in preview sidebar and find no results', async () => {
			await editSXPBlueprintPage.openPreviewSidebar();

			await editSXPBlueprintPage.searchInPreviewSidebar('tree');

			await editSXPBlueprintPage.assertPreviewSidebarNoResults();
		});
	});

	test('Searching with "Customize - All Disabled" returns no results @LPD-22974', async ({
		editSXPBlueprintPage,
	}) => {
		await test.step('Set clause contributors to "Customize"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Customize');
		});

		await test.step('Disable all clause contributors', async () => {
			await editSXPBlueprintPage.openClauseContributorsSidebar();

			await editSXPBlueprintPage.selectClauseContributors({
				labels: ['*'],
				value: false,
			});
		});

		await test.step('Search for "tree" in preview sidebar and find no results', async () => {
			await editSXPBlueprintPage.openPreviewSidebar();

			await editSXPBlueprintPage.searchInPreviewSidebar('tree');

			await editSXPBlueprintPage.assertPreviewSidebarNoResults();
		});
	});

	test('Searching with only "DL File Entry Keyword Query Contributor" returns expected result @LPD-22974', async ({
		editSXPBlueprintPage,
	}) => {
		await test.step('Set clause contributors to "Customize"', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Search Framework Query Contributors'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty('Customize');
		});

		await test.step('Enable only "DL File Entry" clause contributor', async () => {
			await editSXPBlueprintPage.openClauseContributorsSidebar();

			await editSXPBlueprintPage.selectClauseContributors({
				labels: ['*'],
				value: false,
			});

			await editSXPBlueprintPage.selectClauseContributors({
				labels: ['DL File Entry Keyword Query Contributor'],
				value: true,
			});
		});

		await test.step('Search for "tree" in preview sidebar and find file tree.png', async () => {
			await editSXPBlueprintPage.openPreviewSidebar();

			await editSXPBlueprintPage.searchInPreviewSidebar('tree');

			await editSXPBlueprintPage.assertPreviewSidebarSearchResult(
				'tree.png',
				[
					{
						label: 'entryClassName',
						value: 'com.liferay.document.library.kernel.model.DLFileEntry',
					},
					{label: 'userName', value: 'test test'},
				]
			);
		});
	});
});

test.describe('Collection Provider', () => {
	test('Setup a blueprint with web content subtype as a collection provider', async ({
		apiHelpers,
		editSXPBlueprintPage,
		page,
		pageEditorPage,
		site,
		sxpBlueprintsAndElementsViewPage,
	}) => {
		let layout;
		let sxpBlueprint: SXPBlueprint;

		const fieldName = `CustomTextField${getRandomInt()}`;
		const structureName = `WebContentStructure${getRandomInt()}`;

		await test.step('Create a web content structure with a custom text field', async () => {
			await apiHelpers.dataEngine.createStructure(
				site.id,
				getDataStructureDefinition({
					defaultLanguageId: 'en_US',
					fields: [
						{fieldType: 'text', name: fieldName, repeatable: true},
					],
					name: structureName,
				})
			);
		});

		await test.step('Create a blueprint with collection provider enabled', async () => {
			sxpBlueprint =
				await apiHelpers.searchExperiences.createSXPBlueprint({
					configuration: {
						...DEFAULT_SXP_BLUEPRINT_CONFIGURATION,
						generalConfiguration: {
							...DEFAULT_SXP_BLUEPRINT_CONFIGURATION.generalConfiguration,
							collectionProvider: true,
						},
					},
				});
		});

		await test.step('Navigate to created blueprint', async () => {
			await sxpBlueprintsAndElementsViewPage.goto();

			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);
		});

		await test.step('Add the custom web content structure to blueprint subtypes', async () => {
			await editSXPBlueprintPage.expandPanel(
				'Searchable Types and Subtypes'
			);

			await editSXPBlueprintPage.selectSourceRadioProperty(
				'Selected Types'
			);

			await editSXPBlueprintPage.selectAssetTypes([
				'Web Content Article',
			]);

			await editSXPBlueprintPage.selectAssetSubtypes(
				[structureName],
				'Web Content Article'
			);

			await editSXPBlueprintPage.saveBlueprint();
		});

		await test.step('Create a content page with the collection display fragment', async () => {
			layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.addFragment(
				'Content Display',
				'Collection Display'
			);
		});

		await test.step('Select the blueprint as the collection provider', async () => {
			await pageEditorPage.chooseCollectionDisplayCollection(
				'Collection Providers',
				sxpBlueprint.title
			);

			await pageEditorPage.waitForChangesSaved();
		});

		await test.step('Add an HTML fragment to the collection display fragment', async () => {
			await pageEditorPage.addFragment(
				'Basic Components',
				'HTML',
				page.locator('.page-editor__collection-item.empty').first()
			);
		});

		await test.step('Open the sidebar to edit HTML fragment', async () => {
			const htmlFragmentId = await pageEditorPage.getFragmentId('HTML');

			await pageEditorPage.selectEditable(htmlFragmentId, 'element-html');
		});

		await test.step('Assert the type and subtype are correct (as established from blueprint)', async () => {
			await expect(
				page.locator('.page-editor__mapping-panel__type-label', {
					hasText: /Content Type/,
				})
			).toHaveText(/Web Content Article/);

			await expect(
				page.locator('.page-editor__mapping-panel__type-label', {
					hasText: /Subtype/,
				})
			).toHaveText(new RegExp(structureName));
		});

		await test.step('Map the custom text field to the HTML fragment', async () => {
			await page
				.getByLabel('Field', {exact: true})
				.selectOption(`DDMStructure_${fieldName}`);

			await pageEditorPage.waitForChangesSaved();
		});

		await test.step('Check that several basic fields are also available to map', async () => {
			['Title', 'Description', 'Publish Date', 'Author Name'].forEach(
				async (field) => {
					expect(
						page
							.getByLabel('Field', {exact: true})
							.locator('option', {hasText: field})
					).toBeDefined();
				}
			);
		});
	});
});

test.describe('Search Preview - SXP Elements', () => {
	test('Searching on preview with attributes is accurate', async ({
		apiHelpers,
		editSXPBlueprintPage,
		page,
		site,
		sxpBlueprintsAndElementsViewPage,
	}) => {
		let categoryId: number;
		let sxpBlueprint: SXPBlueprint;

		const categoryName = `Category ${getRandomInt()}`;
		const vocabularyName = `Vocabulary ${getRandomInt()}`;

		await test.step('Create a vocabulary + category with API', async () => {
			const {id: vocabularyId} =
				await apiHelpers.headlessAdminTaxonomy.postSiteTaxonomyVocabulary(
					{
						name: vocabularyName,
						siteId: site.id,
					}
				);

			const {id} =
				await apiHelpers.headlessAdminTaxonomy.postTaxonomyVocabularyTaxonomyCategory(
					{
						name: categoryName,
						vocabularyId,
					}
				);

			categoryId = id;

			apiHelpers.data.push({
				id: vocabularyId,
				type: 'taxonomyVocabulary',
			});
		});

		await test.step('Create web contents, one connected to the category', async () => {
			const basicWebContentStructureId =
				await getBasicWebContentStructureId(apiHelpers);

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				content: 'apple',
				ddmStructureId: basicWebContentStructureId,
				groupId: site.id,
				serviceContext: {
					assetCategoryIds: [categoryId],
				},
				titleMap: {en_US: 'gala'},
			});

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: basicWebContentStructureId,
				groupId: site.id,
				titleMap: {en_US: 'apple apple apple'},
			});
		});

		await test.step('Create blueprint with API', async () => {
			sxpBlueprint =
				await apiHelpers.searchExperiences.createSXPBlueprint();
		});

		await test.step('Navigate to created blueprint', async () => {
			await sxpBlueprintsAndElementsViewPage.goto();

			await sxpBlueprintsAndElementsViewPage.selectTableLink(
				sxpBlueprint.title
			);
		});

		await test.step('Add element to blueprint', async () => {
			await editSXPBlueprintPage.addQueryElement(
				'Boost Contents in a Category'
			);
		});

		await test.step('Configure element with new category and high boost', async () => {
			await editSXPBlueprintPage.querySXPElements
				.getByRole('combobox', {name: 'Asset Category External'})
				.fill(categoryName);

			await clickAndExpectToBeVisible({
				target: page.getByRole('gridcell', {
					name: new RegExp(`^${categoryName} \\(ERC:`),
				}),
				trigger: page.getByRole('option', {name: categoryName}),
			});

			await editSXPBlueprintPage.querySXPElements
				.getByLabel('Boost')
				.fill('2000');
		});

		await test.step('Search for "apple" in preview sidebar with attributes', async () => {
			await editSXPBlueprintPage.openPreviewSidebar();

			await editSXPBlueprintPage.addPreviewAttributes([
				{
					key: 'search.experiences.scope.group.id',
					value: String(site.id),
				},
			]);

			await editSXPBlueprintPage.searchInPreviewSidebar('apple');
		});

		await test.step('Assert the two web contents in preview results', async () => {
			await editSXPBlueprintPage.assertPreviewSidebarSearchResult(
				'gala',
				[
					{
						label: 'entryClassName',
						value: 'com.liferay.journal.model.JournalArticle',
					},
				]
			);

			await editSXPBlueprintPage.assertPreviewSidebarSearchResult(
				'apple apple apple',
				[
					{
						label: 'entryClassName',
						value: 'com.liferay.journal.model.JournalArticle',
					},
				]
			);
		});

		await test.step('Assert "gala" is the first result', async () => {
			await expect(
				page
					.getByTestId('previewSidebarResultListItem')
					.nth(0)
					.filter({has: page.getByText('gala')})
			).toBeVisible();
		});
	});
});
