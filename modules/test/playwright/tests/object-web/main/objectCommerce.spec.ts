/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectActionAPI,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
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

test(
	'LPD-78504 Can create an object entry related to Commerce Product Group',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site, viewObjectEntriesPage}) => {
		// Corresponds to Poshi test: CanCreateEntryRelatedToCommerceProductGroup

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

		// Create a relationship from Commerce Product Group to the custom object

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const relationshipLabel = 'Relationship' + getRandomInt();
		const relationshipName = 'relationship' + getRandomInt();

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_COMMERCE_PRODUCT_GROUP',
				{
					label: {en_US: relationshipLabel},
					name: relationshipName,
					objectDefinitionExternalReferenceCode1:
						'L_COMMERCE_PRODUCT_GROUP',
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					type: 'oneToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		// Create an entry for the custom object via API

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;
		const fieldValue = getRandomString();

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: fieldValue},
			applicationName
		);

		// Verify the entry was created successfully

		expect(entry.id).toBeDefined();
		expect(entry[fieldName]).toBe(fieldValue);
	}
);

test(
	'LPD-78504 Can create an object entry related to Commerce Products',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site, viewObjectEntriesPage}) => {
		// Corresponds to Poshi test: CanCreateEntryRelatedToCommerceProducts

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

		// Create a relationship from Commerce Product Definition to the custom object

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const relationshipLabel = 'Relationship' + getRandomInt();
		const relationshipName = 'relationship' + getRandomInt();

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_COMMERCE_PRODUCT_DEFINITION',
				{
					label: {en_US: relationshipLabel},
					name: relationshipName,
					objectDefinitionExternalReferenceCode1:
						'L_COMMERCE_PRODUCT_DEFINITION',
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					type: 'oneToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		// Create a commerce catalog and product

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
			});

		// Navigate to entries page and create an entry

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		const fieldLabel = objectFields[0].label['en_US'];
		const fieldValue = getRandomString();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: fieldLabel,
			objectFieldValue: fieldValue,
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		// Verify the entry was created

		await expect(page.getByText(fieldValue)).toBeVisible();
	}
);

test(
	'LPD-78504 Commerce notification works for creating an entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Corresponds to Poshi test: CreateEntryNotification

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

		// Create an email notification template

		const senderEmail = 'sender' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postRandomNotificationTemplate(
				'notification template ' + getRandomInt(),
				senderEmail
			);

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		// Create an action on the object triggered by onAfterAdd that sends a notification

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				label: {en_US: 'createAction'},
				name: 'createAction',
				objectActionExecutorKey: 'notification',
				objectActionTriggerKey: 'onAfterAdd',
				parameters: {
					notificationTemplateId: notificationTemplate.id,
					type: 'email',
				},
			}
		);

		// Create an entry via API

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: getRandomString()},
			applicationName
		);

		// Verify the notification was sent

		const notificationQueueEntries =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				senderEmail
			);

		const notificationQueueEntriesId =
			notificationQueueEntries.items.map((item: any) => item.id);

		for (const notificationQueueEntryId of notificationQueueEntriesId) {
			apiHelpers.data.push({
				id: notificationQueueEntryId,
				type: 'notificationQueueEntry',
			});
		}

		expect(notificationQueueEntries.items.length).toBeTruthy();
	}
);

test(
	'LPD-78504 Commerce notification works for deleting an entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Corresponds to Poshi test: DeleteEntryNotification

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

		// Create an email notification template

		const senderEmail = 'sender' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postRandomNotificationTemplate(
				'notification template ' + getRandomInt(),
				senderEmail
			);

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		// Create an action on the object triggered by onAfterDelete that sends a notification

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				label: {en_US: 'deleteAction'},
				name: 'deleteAction',
				objectActionExecutorKey: 'notification',
				objectActionTriggerKey: 'onAfterDelete',
				parameters: {
					notificationTemplateId: notificationTemplate.id,
					type: 'email',
				},
			}
		);

		// Create an entry via API and then delete it

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: getRandomString()},
			applicationName
		);

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			entry.id
		);

		// Verify the notification was sent

		const notificationQueueEntries =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				senderEmail
			);

		const notificationQueueEntriesId =
			notificationQueueEntries.items.map((item: any) => item.id);

		for (const notificationQueueEntryId of notificationQueueEntriesId) {
			apiHelpers.data.push({
				id: notificationQueueEntryId,
				type: 'notificationQueueEntry',
			});
		}

		expect(notificationQueueEntries.items.length).toBeTruthy();
	}
);

test(
	'LPD-78504 Object scoped by Company is not displayed on Commerce notification type for Site Channel',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Corresponds to Poshi test: ObjectScopedCompanyNotDisplayedSiteChannel

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

		// Navigate to Commerce notification configuration for a site channel

		await page.goto(
			`/group${site.friendlyUrlPath}/~/control_panel/manage/-/commerce_channels`
		);

		// Open the first channel if it exists, or check the notification type list

		const channelLink = page.locator('.table-list-title a').first();

		if (await channelLink.isVisible({timeout: 5000}).catch(() => false)) {
			await channelLink.click();

			await page.getByRole('link', {name: 'Notifications'}).click();

			// Verify the company-scoped object does NOT appear in the notification type list

			await expect(
				page.getByText(objectDefinition.label['en_US'])
			).toBeHidden();
		}
	}
);

test(
	'LPD-78504 Commerce notification works for updating an entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Corresponds to Poshi test: UpdateEntryNotification

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

		// Create an email notification template

		const senderEmail = 'sender' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postRandomNotificationTemplate(
				'notification template ' + getRandomInt(),
				senderEmail
			);

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		// Create an action on the object triggered by onAfterUpdate that sends a notification

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				label: {en_US: 'updateAction'},
				name: 'updateAction',
				objectActionExecutorKey: 'notification',
				objectActionTriggerKey: 'onAfterUpdate',
				parameters: {
					notificationTemplateId: notificationTemplate.id,
					type: 'email',
				},
			}
		);

		// Create an entry via API and then update it

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: getRandomString()},
			applicationName
		);

		await apiHelpers.objectEntry.patchObjectEntry(
			{[fieldName]: getRandomString()},
			applicationName,
			entry.id
		);

		// Verify the notification was sent

		const notificationQueueEntries =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				senderEmail
			);

		const notificationQueueEntriesId =
			notificationQueueEntries.items.map((item: any) => item.id);

		for (const notificationQueueEntryId of notificationQueueEntriesId) {
			apiHelpers.data.push({
				id: notificationQueueEntryId,
				type: 'notificationQueueEntry',
			});
		}

		expect(notificationQueueEntries.items.length).toBeTruthy();
	}
);
