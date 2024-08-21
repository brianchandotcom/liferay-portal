/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {collectionsPagesTest} from '../../fixtures/collectionsPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import {createObjectField} from './utils/mockObjectFields';

export const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fragmentsPagesTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	pageEditorPagesTest
);

let objectDefinitions: ObjectDefinition[] = [];
let objectFolders: ObjectFolder[] = [];

test.afterEach(async ({apiHelpers}) => {
	if (objectDefinitions.length) {
		for (const objectDefinition of objectDefinitions) {
			await apiHelpers.objectAdmin.deleteObjectDefinition(
				objectDefinition.id
			);
		}

		objectDefinitions = [];
	}

	if (objectFolders.length) {
		for (const objectFolder of objectFolders) {
			await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
		}

		objectFolders = [];
	}
});

test.describe('Manage object definitions through Model Builder', () => {
	test.beforeEach(({page}) => {
		page.setViewportSize({height: 1080, width: 1920});
	});

	test('assert presence of selected node style on click and its transition after dragging an unselected one', async ({
		apiHelpers,
		modelBuilderPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		const commerceOrderItemLabel = 'Commerce Order Item';

		objectDefinitions.push(objectDefinition);

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.toggleSidebarsButton.click();

		await modelBuilderPage.fitViewButton.click();

		await modelBuilderPage.objectDefinitionNodes
			.filter({hasText: commerceOrderItemLabel})
			.click();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: commerceOrderItemLabel,
			})
		).toHaveClass(/selected/);

		await modelBuilderPage.dragNodeThroughDiagram(
			objectDefinition.label['en_US'],
			1400,
			940
		);

		await modelBuilderPage.fitViewButton.click();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: commerceOrderItemLabel,
			})
		).not.toHaveClass(/selected/);

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toHaveClass(/selected/);
	});

	test('can create an object definition by model builder', async ({
		modalAddObjectDefinitionPage,
		modelBuilderPage,
	}) => {
		await modelBuilderPage.goto({objectFolderName: 'Default'});

		const objectDefinitionLabel = 'ObjectDefinitionLabel' + getRandomInt();

		modelBuilderPage.createNewObjectDefinitionButton.click();

		const objectDefinition =
			await modalAddObjectDefinitionPage.createObjectDefinition(
				objectDefinitionLabel
			);

		objectDefinitions.push(objectDefinition);

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();
	});

	test('can create an object definition inside a folder and see if it renders correctly in the model builder', async ({
		modalAddObjectDefinitionPage,
		modelBuilderPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		await viewObjectDefinitionsPage.goto();

		const objectDefinitionLabel = 'ObjectDefinitionLabel' + getRandomInt();

		viewObjectDefinitionsPage.createObjectDefinitionButton.click();

		const objectDefinition =
			await modalAddObjectDefinitionPage.createObjectDefinition(
				objectDefinitionLabel
			);

		objectDefinitions.push(objectDefinition);

		expect(page.getByText(objectDefinitionLabel)).toBeVisible();

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();
	});

	test('can delete an object definition by model builder leftsidebar', async ({
		apiHelpers,
		modelBuilderPage,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		objectDefinitions.push(objectDefinition1);

		objectDefinitions.push(objectDefinition2);

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.clickLeftSideBarItem(
			objectDefinition1.label['en_US']
		);

		await modelBuilderPage.clickObjectDefinitionActionsButtonInLeftSidebar(
			objectDefinition1.label['en_US']
		);

		await modelBuilderPage.deleteObjectDefinitionOption.click();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeHidden();
	});

	test('can delete an published object definition by model builder', async ({
		apiHelpers,
		modalAddObjectDefinitionPage,
		modelBuilderPage,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.createNewObjectDefinitionButton.click();

		const objectDefinition2 =
			await modalAddObjectDefinitionPage.createObjectDefinition(
				'ObjectDefinition' + getRandomInt()
			);

		objectDefinitions.push(objectDefinition1);

		objectDefinitions.push(objectDefinition2);

		await modelBuilderPage.toggleSidebarsButton.click();

		await modelBuilderPage.fitViewButton.click();

		await modelBuilderPage.clickObjectDefinitionActionsButton(
			objectDefinition1.label['en_US']
		);

		await modelBuilderPage.deleteObjectDefinition(objectDefinition1.name);

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeHidden();
	});

	test('linked object definitions are created when object definitions are related and put into different folders', async ({
		apiHelpers,
		modelBuilderPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		objectDefinitions.push(objectDefinition1);

		objectDefinitions.push(objectDefinition2);

		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const objectRelationshipData: Partial<ObjectRelationship> = {
			label: {
				en_US: objectRelationshipLabel,
			},
			name: objectRelationshipName,
			objectDefinitionExternalReferenceCode1:
				objectDefinition1.externalReferenceCode,
			objectDefinitionExternalReferenceCode2:
				objectDefinition2.externalReferenceCode,
			objectDefinitionId1: objectDefinition1.id,
			objectDefinitionId2: objectDefinition2.id,
			objectDefinitionName2: objectDefinition2.name,
			type: 'oneToMany' as ObjectRelationshipType,
		};

		await apiHelpers.objectAdmin.postObjectRelationship(
			objectRelationshipData
		);

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await expect(
			modelBuilderPage.getLinkedObjectDefinitionIconLocator(
				objectDefinition1.label['en_US']
			)
		).toBeVisible();

		await expect(
			modelBuilderPage.getLinkedObjectDefinitionIconLocator(
				objectDefinition2.label['en_US']
			)
		).toBeHidden();

		await modelBuilderPage.leftSidebarItems
			.filter({hasText: objectFolder.name})
			.hover();

		await modelBuilderPage.goToFolderButton.click();

		await expect(
			modelBuilderPage.getLinkedObjectDefinitionIconLocator(
				objectDefinition1.label['en_US']
			)
		).toBeHidden();

		await expect(
			modelBuilderPage.getLinkedObjectDefinitionIconLocator(
				objectDefinition2.label['en_US']
			)
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
	});

	test('navigate to edit object definition page', async ({
		context,
		modelBuilderPage,
	}) => {
		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.clickObjectDefinitionActionsButton(
			'organization'
		);

		await modelBuilderPage.editInPageViewOption.click();

		const pagePromise = context.waitForEvent('page');

		await modelBuilderPage.openPageViewButton.click();

		const editObjectDefinitionPage = await pagePromise;

		await expect(
			editObjectDefinitionPage.getByText('ERC:L_ORGANIZATION')
		).toBeVisible();
	});

	test('see object definition details', async ({
		apiHelpers,
		modelBuilderPage,
		page,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		objectFolders.push(objectFolder);

		const department = await apiHelpers.objectAdmin.postObjectDefinition({
			active: true,
			label: {
				en_US: 'Department',
				pt_BR: 'Departamento',
			},
			name: 'Department',
			objectFields: [
				createObjectField('text', {
					label: 'Name',
					name: 'name',
				}),
			],
			objectFolderExternalReferenceCode:
				objectFolder.externalReferenceCode,
			panelCategoryKey: 'control_panel.object',
			pluralLabel: {
				en_US: 'Departments',
				pt_BR: 'Departamentos',
			},
			scope: 'company',
			status: {code: 0},
			titleObjectFieldName: 'id',
		});

		objectDefinitions.push(department);

		const employee = await apiHelpers.objectAdmin.postObjectDefinition({
			active: false,
			label: {
				en_US: 'Employee',
				pt_BR: 'Funcionario',
			},
			name: 'Employee',
			objectFolderExternalReferenceCode:
				objectFolder.externalReferenceCode,
			panelCategoryKey: 'site_administration.design',
			pluralLabel: {
				en_US: 'Employees',
				pt_BR: 'Funcionarios',
			},
			scope: 'site',
			status: {code: 1},
			titleObjectFieldName: 'name',
		});

		objectDefinitions.push(employee);

		await modelBuilderPage.goto({objectFolderName: objectFolder.name});

		for (const objectDefinition of [department, employee]) {
			await modelBuilderPage.leftSidebarItems
				.filter({hasText: objectDefinition.label['en_US']})
				.click();

			await expect(
				modelBuilderPage.rightSidebar.getByTitle(
					`${objectDefinition.label['en_US']} Details`
				)
			).toBeVisible();

			// Object Data Container

			await expect(
				modelBuilderPage.rightSidebarObjectDefinitionLabel
			).toHaveValue(objectDefinition.label['en_US']);

			await modelBuilderPage.rightSidebarObjectDefinitionLabelLocalizationButton.click();

			await page
				.getByRole('menuitem', {name: 'pt_BR Translated'})
				.click();

			await expect(
				modelBuilderPage.rightSidebarObjectDefinitionLabel
			).toHaveValue(objectDefinition.label['pt_BR']);

			await page.keyboard.press('Escape');

			await expect(
				modelBuilderPage.rightSidebarObjectDefinitionPluralLabel
			).toHaveValue(objectDefinition.pluralLabel['pt_BR']);

			await modelBuilderPage.rightSidebarObjectDefinitionPluralLabelLocalizationButton.click();

			await page.getByRole('menuitem', {name: 'en_US Default'}).click();

			await expect(
				modelBuilderPage.rightSidebarObjectDefinitionPluralLabel
			).toHaveValue(objectDefinition.pluralLabel['en_US']);

			await page.keyboard.press('Escape');

			await expect(
				modelBuilderPage.rightSidebarObjectDefinitionActivateObject
			).toBeChecked({checked: objectDefinition.active});

			// Entry Display Container

			await expect(
				modelBuilderPage.rightSidebarObjectDefinitionEntryTitleField
			).toHaveText(objectDefinition.titleObjectFieldName, {
				ignoreCase: true,
			});

			// Scope Container

			await expect(
				modelBuilderPage.rightSidebarObjectDefinitionScope
			).toHaveText(objectDefinition.scope, {ignoreCase: true});

			const [_, panelLink] = objectDefinition.panelCategoryKey.split('.');

			await expect(
				modelBuilderPage.rightSidebarObjectDefinitionPanelLink
			).toHaveText(panelLink, {ignoreCase: true});
		}
	});

	test('show object definition details in "RightSidebar" after create object definition', async ({
		modalAddObjectDefinitionPage,
		modelBuilderPage,
	}) => {
		await modelBuilderPage.goto({objectFolderName: 'Default'});

		const objectDefinitionLabel = 'ObjectDefinitionLabel' + getRandomInt();

		modelBuilderPage.createNewObjectDefinitionButton.click();

		const objectDefinition =
			await modalAddObjectDefinitionPage.createObjectDefinition(
				objectDefinitionLabel
			);

		objectDefinitions.push(objectDefinition);

		await expect(
			modelBuilderPage.rightSidebar.getByTitle(
				objectDefinitionLabel + ' Details'
			)
		).toBeVisible();
	});
});

test.describe('Manage object definitions through View Object Definitions', () => {
	test('can delete an object definition by FDS action', async ({
		apiHelpers,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		objectDefinitions.push(objectDefinition1);

		objectDefinitions.push(objectDefinition2);

		await viewObjectDefinitionsPage.goto();

		await page.locator('.dnd-td.item-actions').first().waitFor();

		await page
			.locator('.dnd-td.item-actions')
			.last()
			.locator('.dropdown-toggle')
			.click();

		await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

		await expect(
			viewObjectDefinitionsPage.frontendDataSetEntries.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			viewObjectDefinitionsPage.frontendDataSetEntries.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeHidden();
	});
});

test.describe('Manage object definitions through a Page', () => {
	test('can display an object reactivated on the Collection Providers', async ({
		apiHelpers,
		collectionsPage,
		page,
		site,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		objectDefinitions.push(objectDefinition);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.changeObjectActivateStatus(
			objectDefinition.name
		);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.changeObjectActivateStatus(
			objectDefinition.name
		);

		await collectionsPage.goto(site.friendlyUrlPath);

		await page.getByRole('link', {name: 'Collection Providers'}).click();

		await expect(
			page.getByText(objectDefinition.name).first()
		).toBeVisible();
	});

	test('can display an object reactivated on the Page Item Selector', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		objectDefinitions.push(objectDefinition);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.changeObjectActivateStatus(
			objectDefinition.name
		);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.changeObjectActivateStatus(
			objectDefinition.name
		);

		const headingDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([headingDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await page.getByText('Heading Example', {exact: true}).dblclick();

		await page.getByLabel('Select Item').click();

		await expect(
			page
				.frameLocator('iframe[title="Select"]')
				.getByRole('menuitem', {name: objectDefinition.name})
		).toBeVisible();
	});
});
