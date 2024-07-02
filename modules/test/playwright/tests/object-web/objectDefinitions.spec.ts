/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {collectionsPagesTest} from '../../fixtures/CollectionsPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
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

test.afterEach(async ({apiHelpers}) => {
	if (objectDefinitions.length) {
		for (const objectDefinition of objectDefinitions) {
			await apiHelpers.objectAdmin.deleteObjectDefinition(
				objectDefinition.id
			);
		}

		objectDefinitions = [];
	}
});

test.describe('Manage object definitions through Model Builder', () => {
	test.beforeEach(({page}) => {
		page.setViewportSize({height: 1080, width: 1920});
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

		await viewObjectDefinitionsPage.viewInModelBuilder();

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

		await modelBuilderPage.clickDeleteObjectDefinition();

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

		await modelBuilderPage.clickFitViewButton();

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

		await modelBuilderPage.clickGoToFolderButton();

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
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		objectDefinitions.push(objectDefinition);

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition.name,
			})
		).toBeVisible();

		await modelBuilderPage.leftSidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.name,
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.rightSidebar.getByTitle(
				objectDefinition.name + ' Details'
			)
		).toBeVisible();

		const details = [
			'Label',
			'Plural Label',
			'Table Name',
			'Activate Object',
			'Entry Title Field',
			'Scope',
			'Panel Link',
		];

		for (let i = 0; i < details.length; i++) {
			const detail = details[i];

			await expect(
				modelBuilderPage.rightSidebar
					.filter({hasText: detail})
					.filter({hasText: objectDefinition.name})
			).toBeVisible();
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

		await viewObjectDefinitionsPage.clickDeleteObjectDefinition();

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

		await pageEditorPage.selectFragment(headingDefinition.id);

		await page.getByLabel('Select element-text').click();

		await page.getByLabel('Select Item').click();

		await expect(
			page
				.frameLocator('iframe[title="Select"]')
				.getByRole('menuitem', {name: objectDefinition.name})
		).toBeVisible();
	});
});
