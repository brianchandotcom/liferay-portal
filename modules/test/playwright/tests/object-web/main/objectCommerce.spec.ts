/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

// Poshi stub: @ignore = "Test Stub"
// Original: LPS-136741 - Verify that Commerce notification works for
// creating an entry. No Poshi implementation exists (TODO LPS-145736).

test.skip(
	'LPS-136741 CreateEntryNotification',
	{tag: '@LPS-136741'},
	// eslint-disable-next-line @typescript-eslint/no-empty-function
	async () => {}
);

// Poshi stub: @ignore = "Test Stub"
// Original: LPS-136741 - Verify that Commerce notification works for
// deleting an entry. No Poshi implementation exists (TODO LPS-145738).

test.skip(
	'LPS-136741 DeleteEntryNotification',
	{tag: '@LPS-136741'},
	// eslint-disable-next-line @typescript-eslint/no-empty-function
	async () => {}
);

// Poshi stub: @ignore = "Test Stub"
// Original: LPS-136741 - Verify that Commerce notification works for
// updating an entry. No Poshi implementation exists (TODO LPS-145737).

test.skip(
	'LPS-136741 UpdateEntryNotification',
	{tag: '@LPS-136741'},
	// eslint-disable-next-line @typescript-eslint/no-empty-function
	async () => {}
);

test(
	'LPS-151766 Can create entry related to Commerce Product Group',
	{tag: '@LPS-151766'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Create a custom object with a Text field

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		// Get the CommercePricingClass system object definition

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: pricingClassDefPage} =
			await objectDefinitionAPIClient.getObjectDefinitionsPage(
				undefined,
				"name eq 'CommercePricingClass'",
				1,
				1
			);

		const pricingClassDef = pricingClassDefPage.items[0];

		// Create a oneToMany relationship from CommercePricingClass to the
		// custom object

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				pricingClassDef.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Create a Commerce Product Group via Headless Catalog API

		const pricingClassName = 'PG' + getRandomInt();

		const pricingClass = await apiHelpers.post(
			`${apiHelpers.baseUrl}headless-commerce-admin-catalog/v1.0/product-groups`,
			{
				data: {
					title: {en_US: pricingClassName},
				},
			}
		);

		// Navigate to custom object entries and add an entry selecting the
		// Product Group from the relationship field

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.selectDropdownItemWithSearch(
			pricingClassName
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			viewObjectEntriesPage.successMessage
		).toBeVisible();

		// Go back and verify the pricing class name appears in entries list

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page.locator('table td').getByText(pricingClassName).first()
		).toBeVisible();

		// Clean up

		await apiHelpers.delete(
			`${apiHelpers.baseUrl}headless-commerce-admin-catalog/v1.0/product-groups/${pricingClass.id}`
		);

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPS-136741 Object scoped by company not displayed on site channel notifications',
	{tag: '@LPS-136741'},
	async ({apiHelpers, page}) => {
		// Create a company-scoped custom object with a Text field

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				scope: 'company',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		// Create a Commerce Channel of type "site" via API

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				currencyCode: 'USD',
				name: 'TestChannel' + getRandomInt(),
				type: 'site',
			});

		// Navigate to Commerce Channels admin

		await page.goto(
			'/group/guest/~/control_panel/manage?p_p_id=com_liferay_commerce_channel_web_internal_portlet_CommerceChannelsPortlet&p_p_lifecycle=0&p_p_state=maximized',
			{waitUntil: 'networkidle'}
		);

		// Click on the channel name to open its details

		await page
			.getByRole('link', {exact: true, name: channel.name})
			.click();

		// Navigate to "Notification Templates" tab (may include
		// "DEPRECATED" suffix)

		await page
			.getByRole('link', {name: /Notification Templates/})
			.click();

		await page.waitForLoadState('networkidle');

		// Click Add button to open the notification template form

		await page
			.getByRole('button', {name: 'Add Notification Template'})
			.first()
			.click();

		// Verify that Create, Delete, Update options are NOT present
		// for the company-scoped object in the notification type dropdown

		for (const optionType of ['Create', 'Delete', 'Update']) {
			await expect(
				page.locator(`option:text-is("${optionType}")`)
			).toBeHidden();
		}
	}
);

test(
	'LPS-152408 Can create entry related to Commerce Products',
	{tag: '@LPS-152408'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Create a custom object with a Text field

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		// Create a oneToMany relationship from CPDefinition to the custom
		// object

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: cpDefinitionsPage} =
			await objectDefinitionAPIClient.getObjectDefinitionsPage(
				undefined,
				"name eq 'CPDefinition'",
				1,
				1
			);

		const cpDefinition = cpDefinitionsPage.items[0];

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				cpDefinition.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Create a Commerce Product via API

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const productName = 'Simple T-Shirt ' + getRandomInt();

		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			catalogId: catalog.id,
			name: {en_US: productName},
			productType: 'simple',
		});

		// Navigate to custom object entries and add an entry selecting the
		// Commerce Product from the relationship field

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.selectDropdownItemWithSearch(
			productName
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			viewObjectEntriesPage.successMessage
		).toBeVisible();

		// Go back and verify the product name appears in entries list

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page.locator('table td').getByText(productName).first()
		).toBeVisible();

		// Clean up relationship

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);
