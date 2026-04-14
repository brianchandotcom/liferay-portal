/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectActionAPI,
	ObjectDefinition,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	notificationPagesTest,
	objectPagesTest
);

test(
	'LPD-78504 Can add attachment to notification template',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		emailNotificationTemplatePage,
		notificationTemplatesPage,
		page,
		site: _site,
	}) => {
		const objectName = 'ObjectAttach' + getRandomInt();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: objectName,
				objectFields: [
					{
						DBType: 'Long',
						businessType: 'Attachment',
						externalReferenceCode: 'attachmentField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'From Computer And Show Files In DM'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'attachmentField',
						objectFieldSettings: [
							{
								name: 'acceptedFileExtensions',
								value: 'jpeg, jpg, pdf, png' as unknown as object,
							},
							{
								name: 'fileSource',
								value: 'userComputer' as unknown as object,
							},
							{
								name: 'maximumFileSize',
								value: 100 as unknown as object,
							},
						],
						required: false,
						system: false,
						type: 'Long',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await notificationTemplatesPage.goto();

		await notificationTemplatesPage.newNotificationTemplateButton.click();

		await notificationTemplatesPage.emailNotificationDropdownItem.click();

		const templateName = 'Notification Template ' + getRandomInt();

		await emailNotificationTemplatePage.basicInfoName.fill(templateName);
		await emailNotificationTemplatePage.senderEmailAddress.fill(
			'test@liferay.com'
		);
		await emailNotificationTemplatePage.senderName.fill('Test Test');
		await emailNotificationTemplatePage.contentSubject.fill('Subject');

		await page.getByLabel('Data Source').click();
		await page.getByRole('option', {name: objectName}).click();

		await page.getByPlaceholder('Select a Field').click();
		await page
			.getByRole('option', {
				name: 'From Computer And Show Files In DM',
			})
			.click();

		await emailNotificationTemplatePage.saveButton.click();

		await waitForAlert(page);

		apiHelpers.data.push({
			id: await page
				.locator('span:has-text("ID:") + strong')
				.textContent()
				.then((text) => Number(text)),
			type: 'notificationTemplate',
		});

		await notificationTemplatesPage.goto();

		await notificationTemplatesPage
			.getFrontEndDatasetItemLocator(templateName)
			.click();

		await expect(page.getByLabel('Data Source')).toContainText(objectName);

		await expect(
			page.getByText('From Computer And Show Files In DM')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can add email notification to queue via API',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can change notification template data source',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		emailNotificationTemplatePage: _emailNotificationTemplatePage,
		notificationTemplatesPage,
		page,
		site: _site,
	}) => {
		const objectDefinitions: ObjectDefinition[] = [];

		for (const letter of ['A', 'B']) {
			const objectName = `ObjectDS${letter}${getRandomInt()}`;

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectDefinitionExternalReferenceCode: objectName,
					objectFields: [
						{
							DBType: 'Long',
							businessType: 'Attachment',
							externalReferenceCode: `attachmentField${letter}`,
							indexed: true,
							indexedAsKeyword: false,
							indexedLanguageId: '',
							label: {
								en_US: `Custom Attachment Field ${letter}`,
							},
							listTypeDefinitionId: 0,
							localized: false,
							name: `customAttachmentField${letter}`,
							objectFieldSettings: [
								{
									name: 'acceptedFileExtensions',
									value: 'jpeg, jpg, pdf, png' as unknown as object,
								},
								{
									name: 'fileSource',
									value: 'userComputer' as unknown as object,
								},
								{
									name: 'maximumFileSize',
									value: 100 as unknown as object,
								},
							],
							required: false,
							system: false,
							type: 'Long',
						},
					],
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			objectDefinitions.push(objectDefinition);
		}

		const objectNameA = objectDefinitions[0].name;
		const objectNameB = objectDefinitions[1].name;

		await notificationTemplatesPage.goto();

		await notificationTemplatesPage.newNotificationTemplateButton.click();

		await notificationTemplatesPage.emailNotificationDropdownItem.click();

		await page.getByLabel('Data Source').click();
		await page.getByRole('option', {name: objectNameA}).click();

		await page.getByPlaceholder('Select a Field').click();
		await page
			.getByRole('option', {name: 'Custom Attachment Field A'})
			.click();

		await page.getByLabel('Data Source').click();
		await page.getByRole('option', {name: objectNameB}).click();

		await page.getByPlaceholder('Select a Field').click();

		await expect(
			page.getByRole('option', {name: 'Custom Attachment Field B'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete attachment from notification template',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		emailNotificationTemplatePage,
		notificationTemplatesPage,
		page,
		site: _site,
	}) => {
		const objectName = 'ObjectDelAttach' + getRandomInt();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: objectName,
				objectFields: [
					{
						DBType: 'Long',
						businessType: 'Attachment',
						externalReferenceCode: 'attachmentField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'From Computer And Show Files In DM'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'attachmentField',
						objectFieldSettings: [
							{
								name: 'acceptedFileExtensions',
								value: 'jpeg, jpg, pdf, png' as unknown as object,
							},
							{
								name: 'fileSource',
								value: 'userComputer' as unknown as object,
							},
							{
								name: 'maximumFileSize',
								value: 100 as unknown as object,
							},
						],
						required: false,
						system: false,
						type: 'Long',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await notificationTemplatesPage.goto();

		await notificationTemplatesPage.newNotificationTemplateButton.click();

		await notificationTemplatesPage.emailNotificationDropdownItem.click();

		const templateName = 'Notification Template ' + getRandomInt();

		await emailNotificationTemplatePage.basicInfoName.fill(templateName);
		await emailNotificationTemplatePage.senderEmailAddress.fill(
			'test@liferay.com'
		);
		await emailNotificationTemplatePage.senderName.fill('Test Test');
		await emailNotificationTemplatePage.contentSubject.fill('Subject');

		await page.getByLabel('Data Source').click();
		await page.getByRole('option', {name: objectName}).click();

		await page.getByPlaceholder('Select a Field').click();
		await page
			.getByRole('option', {
				name: 'From Computer And Show Files In DM',
			})
			.click();

		await emailNotificationTemplatePage.saveButton.click();

		await waitForAlert(page);

		apiHelpers.data.push({
			id: await page
				.locator('span:has-text("ID:") + strong')
				.textContent()
				.then((text) => Number(text)),
			type: 'notificationTemplate',
		});

		await notificationTemplatesPage.goto();

		await notificationTemplatesPage
			.getFrontEndDatasetItemLocator(templateName)
			.click();

		await page.getByRole('button', {name: 'Delete'}).last().click();

		await expect(page.getByPlaceholder('Select a Field')).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete email notification template',
	{tag: '@LPD-78504'},
	async ({apiHelpers, notificationTemplatesPage, page, site: _site}) => {
		const templateName = 'Notification Template ' + getRandomInt();

		const _notificationTemplate =
			await apiHelpers.notification.postRandomNotificationTemplate(
				templateName
			);

		await notificationTemplatesPage.goto();

		await expect(
			notificationTemplatesPage.getFrontEndDatasetItemLocator(
				templateName
			)
		).toBeVisible();

		const actionButton = page
			.getByRole('row', {name: templateName})
			.getByRole('button', {name: 'Actions'});

		await actionButton.click();

		await notificationTemplatesPage.frontEndDatasetItemActionDelete.click();

		await waitForAlert(page);

		await expect(
			notificationTemplatesPage.getFrontEndDatasetItemLocator(
				templateName
			)
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can delete a notification',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page: _page, site: _site}) => {
		const templateName = 'Notification Template ' + getRandomInt();

		const notificationTemplate =
			await apiHelpers.notification.postRandomNotificationTemplate(
				templateName,
				'test@liferay.com'
			);

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const objectAction =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					label: {
						en_US: 'Custom Action',
					},
					name: 'CustomAction',
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						notificationTemplateId: notificationTemplate.id,
						type: 'email',
					},
				}
			);

		apiHelpers.data.push({
			id: objectAction.body.id,
			type: 'objectAction',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Trigger'},
			applicationName
		);

		const notificationQueueEntries =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				objectDefinition.name
			);

		expect(notificationQueueEntries.items.length).toBeGreaterThan(0);

		const notificationQueueEntryId =
			notificationQueueEntries.items[0].id;

		await apiHelpers.notification.deleteNotificationQueueEntry(
			notificationQueueEntryId
		);

		const updatedNotificationQueueEntries =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				objectDefinition.name
			);

		const remainingIds = updatedNotificationQueueEntries.items.map(
			(item: any) => item.id
		);

		expect(remainingIds).not.toContain(notificationQueueEntryId);
	}
);

test(
	'LPD-78504 Can delete user notification template',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers: _apiHelpers,
		notificationTemplatesPage,
		page,
		site: _site,
		userNotificationTemplatePage,
	}) => {
		const templateName = 'User Notification Template ' + getRandomInt();

		await userNotificationTemplatePage.goto();

		await userNotificationTemplatePage.basicInfoName.fill(templateName);
		await userNotificationTemplatePage.contentSubject.fill(
			'Subject content'
		);

		await userNotificationTemplatePage.selectNotificationRecipient('User');

		await page.getByPlaceholder('Enter a user.').click();
		await page.getByLabel('Test Test', {exact: true}).check();
		await page.keyboard.press('Escape');

		await userNotificationTemplatePage.saveButton.click();

		await notificationTemplatesPage.goto();

		await expect(
			notificationTemplatesPage.getFrontEndDatasetItemLocator(
				templateName
			)
		).toBeVisible();

		const actionButton = page
			.getByRole('row', {name: templateName})
			.getByRole('button', {name: 'Actions'});

		await actionButton.click();

		await notificationTemplatesPage.frontEndDatasetItemActionDelete.click();

		await waitForAlert(page);

		await expect(
			notificationTemplatesPage.getFrontEndDatasetItemLocator(
				templateName
			)
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can search for notifications',
	{tag: '@LPD-78504'},
	async ({apiHelpers, notificationTemplatesPage, page, site: _site}) => {
		const templateNames = [];

		for (const suffix of ['Test', 'Liferay', 'Site']) {
			const name = `Notification ${suffix} ${getRandomInt()}`;

			const notificationTemplate =
				await apiHelpers.notification.postRandomNotificationTemplate(
					name
				);

			apiHelpers.data.push({
				id: notificationTemplate.id,
				type: 'notificationTemplate',
			});

			templateNames.push(name);
		}

		await notificationTemplatesPage.goto();

		for (const name of templateNames) {
			await expect(
				notificationTemplatesPage.getFrontEndDatasetItemLocator(name)
			).toBeVisible();
		}

		const searchTerm = templateNames[1].split(' ')[1];

		await page.getByPlaceholder('Search').fill(searchTerm);
		await page.keyboard.press('Enter');

		await expect(
			notificationTemplatesPage.getFrontEndDatasetItemLocator(
				templateNames[1]
			)
		).toBeVisible();

		await expect(
			notificationTemplatesPage.getFrontEndDatasetItemLocator(
				templateNames[0]
			)
		).not.toBeVisible();

		await expect(
			notificationTemplatesPage.getFrontEndDatasetItemLocator(
				templateNames[2]
			)
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can send email via Action on Add',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server (MockMock) which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can send email via Action on Delete',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server (MockMock) which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can send email via Standalone Action',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server (MockMock) which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can send email via Action on Update',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server (MockMock) which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can send email with terms from related Custom Objects',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server (MockMock) which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can send email with terms from related System Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server (MockMock) which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can send user notification with terms from related Custom Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page: _page, site: _site}) => {
		const objectDefinitions: ObjectDefinition[] = [];

		for (const letter of ['A', 'B']) {
			const objectName = `ObjectRel${letter}${getRandomInt()}`;

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectDefinitionExternalReferenceCode: objectName,
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							externalReferenceCode: `textField${letter}`,
							indexed: true,
							indexedAsKeyword: false,
							indexedLanguageId: '',
							label: {en_US: `Text Field ${letter}`},
							listTypeDefinitionId: 0,
							localized: false,
							name: 'customField',
							required: false,
							system: false,
							type: 'String',
						},
					],
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			objectDefinitions.push(objectDefinition);
		}

		const objectDefA = objectDefinitions[0];
		const objectDefB = objectDefinitions[1];

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const {body: _relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefA.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: 'relationship',
					objectDefinitionExternalReferenceCode1:
						objectDefA.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefB.externalReferenceCode,
					type: 'oneToMany',
				}
			);

		const objectNameA = objectDefA.externalReferenceCode.toUpperCase();
		const subjectTerm = `[%RELATIONSHIP_${objectNameA}_CUSTOMFIELD%] | [%${objectDefB.externalReferenceCode.toUpperCase()}_CUSTOMFIELD%]`;

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				editorType: 'richText',
				name: 'User Notification Template',
				recipientType: 'term',
				recipients: [
					{
						term: '[%CURRENT_USER_EMAIL_ADDRESS%]',
					},
				],
				subject: {
					en_US: subjectTerm,
				},
				type: 'userNotification',
			});

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const objectAction =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefB.externalReferenceCode,
				{
					active: true,
					label: {
						en_US: 'Send notification after entry is created',
					},
					name: 'sendNotification',
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						notificationTemplateId: notificationTemplate.id,
						type: 'userNotification',
					},
				}
			);

		apiHelpers.data.push({
			id: objectAction.body.id,
			type: 'objectAction',
		});

		const applicationNameA =
			'c/' + objectDefA.name.toLowerCase() + 's';

		const entryA = await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test A'},
			applicationNameA
		);

		const applicationNameB =
			'c/' + objectDefB.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{
				customField: 'Entry Test B',
				r_relationship_c_customObjectId: entryA.id,
			},
			applicationNameB
		);

		const notificationQueueEntries =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				'Entry Test'
			);

		for (const item of notificationQueueEntries.items) {
			apiHelpers.data.push({
				id: item.id,
				type: 'notificationQueueEntry',
			});
		}

		expect(notificationQueueEntries.items.length).toBeGreaterThan(0);
	}
);

test(
	'LPD-78504 Can use Object Author and Current User terms in email notification on Custom Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server (MockMock) which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can use Object Author and Current User terms in email notification on System Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		// This test requires a mock SMTP server (MockMock) which is not available
		// in the Playwright test infrastructure

		test.fixme();
	}
);

test(
	'LPD-78504 Can use Object Author and Current User terms in user notification on Custom Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page: _page, site: _site}) => {
		const objectName = 'ObjectUserNotif' + getRandomInt();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: objectName,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		const subjectTerm = `[%CURRENT_USER_EMAIL_ADDRESS%] [%CURRENT_USER_FIRST_NAME%] [%CURRENT_USER_LAST_NAME%] [%${objectName.toUpperCase()}_CUSTOMFIELD%]`;

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				editorType: 'richText',
				name: 'User Notification Template',
				recipientType: 'term',
				recipients: [
					{
						term: '[%CURRENT_USER_EMAIL_ADDRESS%]',
					},
				],
				subject: {
					en_US: subjectTerm,
				},
				type: 'userNotification',
			});

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const objectAction =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					label: {
						en_US: 'Action name',
					},
					name: 'actionName',
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'onAfterUpdate',
					parameters: {
						notificationTemplateId: notificationTemplate.id,
						type: 'userNotification',
					},
				}
			);

		apiHelpers.data.push({
			id: objectAction.body.id,
			type: 'objectAction',
		});

		await apiHelpers.objectEntry.patchObjectEntry(
			{customField: 'Entry Test Edited'},
			applicationName,
			entry.id
		);

		const notificationQueueEntries =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				'Entry Test Edited'
			);

		for (const item of notificationQueueEntries.items) {
			apiHelpers.data.push({
				id: item.id,
				type: 'notificationQueueEntry',
			});
		}

		expect(notificationQueueEntries.items.length).toBeGreaterThan(0);

		const queueEntry = notificationQueueEntries.items[0];

		expect(queueEntry.subject).toContain('Entry Test Edited');
	}
);
