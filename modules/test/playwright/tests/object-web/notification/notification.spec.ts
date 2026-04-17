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
import {editObjectDefinitionPagesTest} from '../../../fixtures/editObjectDefinitionPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	dataApiHelpersTest,
	editObjectDefinitionPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	notificationPagesTest,
	objectPagesTest,
	usersAndOrganizationsPagesTest
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

test(
	'LPD-78504 can be sent to a regular role',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		notificationsPage,
		page,
		site: _site,
		userNotificationTemplatePage,
		viewObjectActionsPage,
	}) => {
		const roleName = getRandomString();

		const role = await apiHelpers.headlessAdminUser.postRole({
			externalReferenceCode: getRandomString(),
			name: roleName,
			name_i18n: {en_US: getRandomString()},
			roleType: 'regular',
		});

		apiHelpers.data.push({
			id: role.id,
			type: 'role',
		});

		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'test@liferay.com'
			);

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		await userNotificationTemplatePage.goto();

		const notificationTemplateName = getRandomString();

		await userNotificationTemplatePage.basicInfoName.fill(
			notificationTemplateName
		);

		const contentSubject = getRandomString();

		await userNotificationTemplatePage.contentSubject.fill(contentSubject);

		await userNotificationTemplatePage.selectNotificationRecipient('Role');

		await userNotificationTemplatePage.selectRole(roleName);

		await userNotificationTemplatePage.saveButton.click();

		await page.getByRole('link', {name: notificationTemplateName}).click();

		const notificationTemplateId = await page
			.locator('span:has-text("ID:") + strong')
			.textContent();

		apiHelpers.data.push({
			id: notificationTemplateId,
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

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await editObjectActionPage.addNewAction({
			notificationTemplateName,
			thenOption: 'Notification',
			whenOption: 'On After Add',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		const objectFieldValue = getRandomString();

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: objectFieldValue},
			applicationName
		);

		await notificationsPage.goto();

		await page.getByText(contentSubject).click();

		await expect(page.getByLabel('textField', {exact: true})).toHaveValue(
			objectFieldValue
		);
	}
);
