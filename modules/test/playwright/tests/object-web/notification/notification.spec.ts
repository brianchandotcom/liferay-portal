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
import {smtpPagesTest} from '../../../fixtures/smtpPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';

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
	smtpPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'LPD-78504 Can send email via Action on Add',
	{tag: '@LPD-78504'},
	async ({apiHelpers, mockMockPage, site: _site}) => {
		await mockMockPage.deleteAllEmails();

		const subject = 'Subject ' + getRandomInt();
		const toEmail = 'toadd' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				body: {en_US: 'Email Body'},
				editorType: 'richText',
				name: 'Notification Template ' + getRandomInt(),
				recipientType: 'email',
				recipients: [
					{
						from: 'noreply@example.com',
						fromName: {en_US: 'Liferay'},
						to: {en_US: toEmail},
						toType: 'email',
					},
				],
				subject: {en_US: subject},
				type: 'email',
			});

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
					label: {en_US: 'Send email on add'},
					name: 'sendEmailOnAdd',
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

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Trigger'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await mockMockPage.assertEmail({
			body: 'Email Body',
			from: 'Liferay',
			subject,
			to: toEmail,
		});
	}
);

test(
	'LPD-78504 Can send email via Action on Delete',
	{tag: '@LPD-78504'},
	async ({apiHelpers, mockMockPage, site: _site}) => {
		await mockMockPage.deleteAllEmails();

		const subject = 'Subject ' + getRandomInt();
		const toEmail = 'todel' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				body: {en_US: 'Email Body'},
				editorType: 'richText',
				name: 'Notification Template ' + getRandomInt(),
				recipientType: 'email',
				recipients: [
					{
						from: 'noreply@example.com',
						fromName: {en_US: 'Liferay'},
						to: {en_US: toEmail},
						toType: 'email',
					},
				],
				subject: {en_US: subject},
				type: 'email',
			});

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
					label: {en_US: 'Send email on delete'},
					name: 'sendEmailOnDelete',
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'onAfterDelete',
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

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Entry Test'},
			applicationName
		);

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(entry.id)
		);

		await mockMockPage.assertEmail({
			body: 'Email Body',
			from: 'Liferay',
			subject,
			to: toEmail,
		});
	}
);

test(
	'LPD-78504 Can send email via Standalone Action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		mockMockPage,
		page,
		site: _site,
		viewObjectEntriesPage,
	}) => {
		await mockMockPage.deleteAllEmails();

		const subject = 'Subject ' + getRandomInt();
		const toEmail = 'tostd' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				body: {en_US: 'Email Body'},
				editorType: 'richText',
				name: 'Notification Template ' + getRandomInt(),
				recipientType: 'email',
				recipients: [
					{
						from: 'noreply@example.com',
						fromName: {en_US: 'Test Test'},
						to: {en_US: toEmail},
						toType: 'email',
					},
				],
				subject: {en_US: subject},
				type: 'email',
			});

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

		const actionLabel = 'Send Email Action';

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const objectAction =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					label: {en_US: actionLabel},
					name: 'sendEmailStandalone',
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'standalone',
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

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Entry'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await page
			.getByRole('row', {name: 'Entry'})
			.getByRole('button', {name: 'Actions'})
			.click();

		await page.getByRole('menuitem', {name: actionLabel}).click();

		await mockMockPage.assertEmail({
			body: 'Email Body',
			from: 'Test Test',
			subject,
			to: toEmail,
		});
	}
);

test(
	'LPD-78504 Can send email via Action on Update',
	{tag: '@LPD-78504'},
	async ({apiHelpers, mockMockPage, site: _site}) => {
		await mockMockPage.deleteAllEmails();

		const subject = 'Subject ' + getRandomInt();
		const toEmail = 'toupd' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				body: {en_US: 'Email Body'},
				editorType: 'richText',
				name: 'Notification Template ' + getRandomInt(),
				recipientType: 'email',
				recipients: [
					{
						from: 'noreply@example.com',
						fromName: {en_US: 'Liferay'},
						to: {en_US: toEmail},
						toType: 'email',
					},
				],
				subject: {en_US: subject},
				type: 'email',
			});

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
					label: {en_US: 'Send email on update'},
					name: 'sendEmailOnUpdate',
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'onAfterUpdate',
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

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Entry Test'},
			applicationName
		);

		await apiHelpers.objectEntry.patchObjectEntry(
			{textField: 'Trigger'},
			applicationName,
			entry.id
		);

		await mockMockPage.assertEmail({
			body: 'Email Body',
			from: 'Liferay',
			subject,
			to: toEmail,
		});
	}
);

test(
	'LPD-78504 Can send email with terms from related Custom Objects',
	{tag: '@LPD-78504'},
	async ({apiHelpers, mockMockPage, site: _site}) => {
		await mockMockPage.deleteAllEmails();

		const objectDefinitions: ObjectDefinition[] = [];

		for (const letter of ['A', 'B']) {
			const objectName = `ObjectRelEmail${letter}${getRandomInt()}`;

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectDefinitionExternalReferenceCode: objectName,
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							externalReferenceCode: `customField${letter}`,
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

		const erc = (def: ObjectDefinition) =>
			def.externalReferenceCode.toUpperCase();

		const subjectTerm = `[%RELATIONSHIP_${erc(objectDefA)}_CUSTOMFIELD%] | [%${erc(objectDefB)}_CUSTOMFIELD%]`;

		const toEmail = 'torel' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				body: {en_US: 'Email Body'},
				editorType: 'richText',
				name: 'Notification Template ' + getRandomInt(),
				recipientType: 'email',
				recipients: [
					{
						from: 'noreply@example.com',
						fromName: {en_US: 'Liferay'},
						to: {en_US: toEmail},
						toType: 'email',
					},
				],
				subject: {en_US: subjectTerm},
				type: 'email',
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
					label: {en_US: 'Send email on add'},
					name: 'sendEmailOnAdd',
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

		const entryA = await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test A'},
			'c/' + objectDefA.name.toLowerCase() + 's'
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				customField: 'Entry Test B',
				r_relationship_c_customObjectId: entryA.id,
			},
			'c/' + objectDefB.name.toLowerCase() + 's'
		);

		await mockMockPage.assertEmail({
			body: 'Email Body',
			subject: 'Entry Test A | Entry Test B',
			to: toEmail,
		});
	}
);

test(
	'LPD-78504 Can send email with terms from related System Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, mockMockPage, site: _site}) => {
		await mockMockPage.deleteAllEmails();

		const objectName = 'ObjectSysRel' + getRandomInt();

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
						label: {en_US: 'Text Field'},
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

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			objectDefinition.externalReferenceCode,
			{
				label: {en_US: 'Relationship'},
				name: 'relationshipUser',
				objectDefinitionExternalReferenceCode1: 'USER',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				type: 'oneToMany',
			}
		);

		const subjectTerm =
			'[%RELATIONSHIP_USER_AUTHOR_FIRST_NAME%] [%RELATIONSHIP_USER_AUTHOR_LAST_NAME%]';

		const toEmail = 'tosysrel' + getRandomInt() + '@liferay.com';

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				body: {en_US: `[%${objectName.toUpperCase()}_CUSTOMFIELD%]`},
				editorType: 'richText',
				name: 'Notification Template ' + getRandomInt(),
				recipientType: 'email',
				recipients: [
					{
						from: 'noreply@example.com',
						fromName: {en_US: 'Liferay'},
						to: {en_US: toEmail},
						toType: 'email',
					},
				],
				subject: {en_US: subjectTerm},
				type: 'email',
			});

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		const currentUser =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'test@liferay.com'
			);

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const objectAction =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode,
				{
					active: true,
					label: {en_US: 'Send email on add'},
					name: 'sendEmailOnAdd',
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

		await apiHelpers.objectEntry.postObjectEntry(
			{
				customField: 'Entry Test',
				r_relationshipUser_userId: currentUser.id,
			},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await mockMockPage.assertEmail({
			body: 'Entry Test',
			subject: 'Test Test',
			to: toEmail,
		});
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
	async ({apiHelpers, mockMockPage, page, site: _site}) => {
		await mockMockPage.deleteAllEmails();

		const objectName = 'ObjectAuthor' + getRandomInt();

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: objectName,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customObjectField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Object Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customObjectField',
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
			{customObjectField: 'Before'},
			applicationName
		);

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				body: {en_US: 'Email Body'},
				editorType: 'richText',
				name: 'Notification Template ' + getRandomInt(),
				recipientType: 'email',
				recipients: [
					{
						from: '[%CURRENT_USER_EMAIL_ADDRESS%]',
						fromName: {en_US: '[%CURRENT_USER_FIRST_NAME%]'},
						to: {
							en_US: '[%CURRENT_USER_EMAIL_ADDRESS%]',
						},
						toType: 'email',
					},
				],
				subject: {
					en_US: `[%${objectName.toUpperCase()}_CUSTOMOBJECTFIELD%]`,
				},
				type: 'email',
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
					label: {en_US: 'Send email on update'},
					name: 'sendEmailOnUpdate',
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'onAfterUpdate',
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

		await performUserSwitch(page, 'demo.company.admin');

		await apiHelpers.objectEntry.patchObjectEntry(
			{customObjectField: 'After'},
			applicationName,
			entry.id
		);

		const demoAdmin = userData['demo.company.admin'];

		await mockMockPage.assertEmail({
			body: 'Email Body',
			from: demoAdmin.name,
			subject: 'After',
			to: 'demo.company.admin@liferay.com',
		});
	}
);

test(
	'LPD-78504 Can use Object Author and Current User terms in email notification on System Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, mockMockPage, page, site: _site}) => {
		await mockMockPage.deleteAllEmails();

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				body: {en_US: 'Email Body'},
				editorType: 'richText',
				name: 'Notification Template ' + getRandomInt(),
				recipientType: 'email',
				recipients: [
					{
						from: '[%USER_AUTHOR_FIRST_NAME%]',
						fromName: {en_US: '[%CURRENT_USER_EMAIL_ADDRESS%]'},
						to: {
							en_US: '[%CURRENT_USER_EMAIL_ADDRESS%]',
						},
						toType: 'email',
					},
				],
				subject: {en_US: '[%USER_AUTHOR_LAST_NAME%]'},
				type: 'email',
			});

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const objectAction =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				'USER',
				{
					active: true,
					label: {en_US: 'Send email on user update'},
					name: 'sendEmailOnUserUpdate' + getRandomInt(),
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'onAfterUpdate',
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

		await performUserSwitch(page, 'demo.company.admin');

		const demoAdmin =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'demo.company.admin@liferay.com'
			);

		await apiHelpers.headlessAdminUser.patchUserAccount(demoAdmin, {
			additionalName: 'Updated ' + getRandomInt(),
		});

		const demoAdminData = userData['demo.company.admin'];

		await mockMockPage.assertEmail({
			body: 'Email Body',
			subject: demoAdminData.surname,
			to: 'demo.company.admin@liferay.com',
		});
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
