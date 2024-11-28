/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionApi,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {notificationPagesTest} from '../../fixtures/notificationPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	notificationPagesTest
);

let objectDefinition: ObjectDefinition;

test.beforeEach(async ({apiHelpers}) => {
	objectDefinition = await apiHelpers.objectAdmin.postRandomObjectDefinition(
		{code: 0},
		undefined,
		'default'
	);
});

test.afterEach(async ({apiHelpers, notificationTemplatesPage, page}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	await objectDefinitionAPIClient.deleteObjectDefinition(objectDefinition.id);

	await notificationTemplatesPage.goto();

	const frontEndDatasetItemActions =
		await notificationTemplatesPage.frontEndDatasetItemAction.all();

	for (let i = 0; i < frontEndDatasetItemActions.length; i++) {
		try {
			const actionButton = page
				.getByRole('button', {name: 'Actions'})
				.first();
			if (actionButton) {
				await actionButton.click();
				const deleteButton =
					notificationTemplatesPage.frontEndDatasetItemActionDelete;

				if (deleteButton) {
					await deleteButton.click();
				}
			}
		}
		catch (error) {
			throw new Error(error);
		}
	}
});

test('can add rich text source code and verify that the source code is being persisted', async ({
	emailNotificationTemplatePage,
	notificationTemplatesPage,
	page,
}) => {
	await emailNotificationTemplatePage.goto();

	const notificationTemplateName =
		'Notification Template Name' + getRandomInt();

	await emailNotificationTemplatePage.basicInfoName.fill(
		notificationTemplateName
	);

	await emailNotificationTemplatePage.senderEmailAddress.fill(
		'test@liferay.com'
	);

	await emailNotificationTemplatePage.senderName.fill('test user');

	await emailNotificationTemplatePage.primaryRecipientUserEmailAddress.fill(
		'test@liferay.com'
	);

	await emailNotificationTemplatePage.contentSubject.fill('Content subject');

	await emailNotificationTemplatePage.richTextSourceButton.click();

	await emailNotificationTemplatePage.richTextSourceField.fill(
		'<h1>Hello World</h1>'
	);

	await emailNotificationTemplatePage.saveButton.click();

	await notificationTemplatesPage
		.getFrontEndDatasetItemLocator(notificationTemplateName)
		.click();

	await emailNotificationTemplatePage.richTextSourceButton.click();

	await expect(page.getByText('<h1>Hello World</h1>')).toBeVisible();
});

test('can save recipients roles in email notification template', async ({
	emailNotificationTemplatePage,
	notificationTemplatesPage,
	page,
}) => {
	const primaryRecipientsRoles = [
		'Account Administrator',
		'Account Member',
		'Administrator',
		'Analytics Administrator',
		'Account Manager',
		'Organization Administrator',
	];

	const secondaryRecipientsRolesCC = [
		'Account Supplier',
		'Buyer',
		'Owner',
		'Portal Content Reviewer',
		'Organization Content Reviewer',
		'Organization Owner',
	];

	const secondaryRecipientsRolesBCC = [
		'Order Manager',
		'Power User',
		'Publications User',
		'Organization User',
	];

	await emailNotificationTemplatePage.goto();

	const notificationTemplateName =
		'Notification Template Name' + getRandomInt();

	await emailNotificationTemplatePage.basicInfoName.fill(
		notificationTemplateName
	);

	await emailNotificationTemplatePage.senderEmailAddress.fill(
		'test@liferay.com'
	);

	await emailNotificationTemplatePage.senderName.fill('test user');

	await emailNotificationTemplatePage.primaryRecipientType.click();

	await page.getByRole('option', {name: 'Roles'}).click();

	await emailNotificationTemplatePage.primaryRecipientRoles.click();

	for (const role of primaryRecipientsRoles) {
		await page
			.getByLabel(role, {exact: true})
			.locator('visible=true')
			.check();
	}

	await emailNotificationTemplatePage.secondaryRecipientTypeCC.click();

	await page.getByRole('option', {name: 'Roles'}).click();

	await emailNotificationTemplatePage.secondaryRecipientRolesCC.click();

	for (const role of secondaryRecipientsRolesCC) {
		await page
			.getByLabel(role, {exact: true})
			.locator('visible=true')
			.check();
	}

	await emailNotificationTemplatePage.secondaryRecipientTypeBCC.click();

	await page.getByRole('option', {name: 'Roles'}).click();

	await emailNotificationTemplatePage.secondaryRecipientRolesBCC.click();

	for (const role of secondaryRecipientsRolesBCC) {
		await page
			.getByLabel(role, {exact: true})
			.locator('visible=true')
			.check();
	}

	await emailNotificationTemplatePage.contentSubject.fill('Content subject');

	await emailNotificationTemplatePage.saveButton.click();

	await notificationTemplatesPage
		.getFrontEndDatasetItemLocator(notificationTemplateName)
		.click();

	await emailNotificationTemplatePage.primaryRecipientRoles.click();

	for (const role of primaryRecipientsRoles) {
		await expect(
			page.getByLabel(role, {exact: true}).locator('visible=true')
		).toBeChecked();
	}

	await emailNotificationTemplatePage.secondaryRecipientRolesCC.click();

	for (const role of secondaryRecipientsRolesCC) {
		await expect(
			page.getByLabel(role, {exact: true}).locator('visible=true')
		).toBeChecked();
	}

	await emailNotificationTemplatePage.secondaryRecipientRolesBCC.click();

	for (const role of secondaryRecipientsRolesBCC) {
		await expect(
			page.getByLabel(role, {exact: true}).locator('visible=true')
		).toBeChecked();
	}
});

test('can see all roles groups in email notification template recipients', async ({
	emailNotificationTemplatePage,
	page,
}) => {
	await emailNotificationTemplatePage.goto();

	await emailNotificationTemplatePage.primaryRecipientType.click();

	await page.getByRole('option', {name: 'Roles'}).click();

	await emailNotificationTemplatePage.primaryRecipientRoles.click();

	await expect(
		emailNotificationTemplatePage.accountRolesGroupTitle
	).toBeVisible();

	await expect(
		emailNotificationTemplatePage.regularRolesGroupTitle
	).toBeVisible();

	await expect(
		emailNotificationTemplatePage.organizationRolesGroupTitle
	).toBeVisible();

	await page.keyboard.press('Escape');

	await emailNotificationTemplatePage.secondaryRecipientTypeCC.click();

	await page.getByRole('option', {name: 'Roles'}).click();

	await emailNotificationTemplatePage.secondaryRecipientRolesCC.click();

	await expect(
		emailNotificationTemplatePage.accountRolesGroupTitle
	).toBeVisible();

	await expect(
		emailNotificationTemplatePage.regularRolesGroupTitle
	).toBeVisible();

	await expect(
		emailNotificationTemplatePage.organizationRolesGroupTitle
	).toBeVisible();

	await page.keyboard.press('Escape');

	await emailNotificationTemplatePage.secondaryRecipientTypeBCC.click();

	await page.getByRole('option', {name: 'Roles'}).click();

	await emailNotificationTemplatePage.secondaryRecipientRolesBCC.click();

	await expect(
		emailNotificationTemplatePage.accountRolesGroupTitle
	).toBeVisible();

	await expect(
		emailNotificationTemplatePage.regularRolesGroupTitle
	).toBeVisible();

	await expect(
		emailNotificationTemplatePage.organizationRolesGroupTitle
	).toBeVisible();
});

test('can see cc/bcc fields in UI when creating notification via API without passing them', async ({
	apiHelpers,
	notificationTemplatesPage,
	page,
}) => {
	const notificationTemplate =
		await apiHelpers.notification.postNotificationTemplate({
			editorType: 'richText',
			name: 'Test Email',
			recipientType: 'email',
			recipients: [
				{
					from: 'test@liferay.com',
					fromName: {
						en_US: 'Test',
					},
					to: [
						{
							roleName: 'Account Administrator',
						},
					],
					toType: 'role',
				},
			],
			subject: {
				en_US: 'Subject',
			},
			type: 'email',
		});

	await notificationTemplatesPage.goto();

	await notificationTemplatesPage.openNotificationTemplate(
		notificationTemplate.name
	);

	await expect(page.locator('#secondaryRecipientsCC')).toBeVisible();
	await expect(page.locator('#secondaryRecipientsBCC')).toBeVisible();
});

test('can use notification terms and freeMarker variables in notification template', async ({
	emailNotificationTemplatePage,
	notificationTemplatesPage,
	page,
}) => {
	await emailNotificationTemplatePage.goto();

	const notificationTemplateName =
		'Notification Template Name' + getRandomInt();

	await emailNotificationTemplatePage.basicInfoName.fill(
		notificationTemplateName
	);

	await emailNotificationTemplatePage.senderEmailAddress.fill(
		'test@liferay.com'
	);

	await emailNotificationTemplatePage.senderName.fill('test user');

	await emailNotificationTemplatePage.primaryRecipientUserEmailAddress.fill(
		'[%CURRENT_USER_EMAIL_ADDRESS%]'
	);

	await emailNotificationTemplatePage.contentSubject.fill('Content subject');

	await emailNotificationTemplatePage.definitionOfTermsEntity.click();

	await page
		.getByRole('option', {name: objectDefinition.externalReferenceCode})
		.click();

	const objectDefinitionTerm =
		objectDefinition.externalReferenceCode.toUpperCase();

	const objectFieldName = objectDefinition.objectFields.find(
		(objectField) => !objectField.system
	).name;

	const terms = [
		'[%CURRENT_USER_FIRST_NAME%]',
		'[%CURRENT_USER_PREFIX%]',
		'[%CURRENT_DATE%]',
		'[%CURRENT_USER_LAST_NAME%]',
		'[%CURRENT_USER_MIDDLE_NAME%]',
		'[%CURRENT_USER_EMAIL_ADDRESS%]',
		'[%CURRENT_USER_ID%]',
		'[%CURRENT_USER_SUFFIX%]',
		`[%${objectDefinitionTerm}_CREATEDATE%]`,
		`[%${objectDefinitionTerm}_AUTHOR_EMAIL_ADDRESS%]`,
		`[%${objectDefinitionTerm}_AUTHOR_SUFFIX%]`,
		`[%${objectDefinitionTerm}_AUTHOR_PREFIX%]`,
		`[%${objectDefinitionTerm}_AUTHOR_FIRST_NAME%]`,
		`[%${objectDefinitionTerm}_AUTHOR_LAST_NAME%]`,
		`[%${objectDefinitionTerm}_AUTHOR_MIDDLE_NAME%]`,
		`[%${objectDefinitionTerm}_AUTHOR_ID%]`,
		`[%${objectDefinitionTerm}_EXTERNALREFERENCECODE%]`,
		`[%${objectDefinitionTerm}_ID%]`,
		`[%${objectDefinitionTerm}_MODIFIEDDATE%]`,
		`[%${objectDefinitionTerm}_STATUS%]`,
		`[%${objectDefinitionTerm}_${objectFieldName.toUpperCase()}%]`,
	];

	for (const term of terms) {
		await expect(page.locator('div.dnd-td').getByText(term)).toBeVisible();
	}

	const copyButtons = [
		emailNotificationTemplatePage.copyButton.first(),
		emailNotificationTemplatePage.copyButton.last(),
	];

	for (const copyButton of copyButtons) {
		await copyButton.click();

		await emailNotificationTemplatePage.richTextField.click();

		await page.keyboard.press('PageDown');

		await page.keyboard.press('Control+V');
	}

	await emailNotificationTemplatePage.saveButton.click();

	await notificationTemplatesPage
		.getFrontEndDatasetItemLocator(notificationTemplateName)
		.click();

	await expect(
		emailNotificationTemplatePage.primaryRecipientUserEmailAddress
	).toHaveValue('[%CURRENT_USER_EMAIL_ADDRESS%]');

	await expect(
		emailNotificationTemplatePage.richTextField.getByText(
			'[%CURRENT_USER_FIRST_NAME%]' +
				`[%${objectDefinitionTerm}_${objectFieldName.toUpperCase()}%]`
		)
	).toBeVisible();

	await emailNotificationTemplatePage.editorType.click();

	await page.getByRole('option', {name: 'FreeMarker Template'}).click();

	await expect(page.getByText('Elements')).toBeVisible();

	await emailNotificationTemplatePage.freeMarkerEntity.click();

	await page
		.getByRole('option', {name: objectDefinition.label['en_US']})
		.click();

	const freeMarkerVariables = [
		'Author',
		'Create Date',
		'Default',
		'External Reference Code',
		'ID',
		'Locale',
		'Modified Date',
		'Portal URL',
		'Publish Date',
		'Status',
		'User Profile Image',
		objectFieldName,
	];

	for (const freeMarkerVariable of freeMarkerVariables) {
		await expect(
			page.getByRole('button', {exact: true, name: freeMarkerVariable})
		).toBeVisible();
	}

	await page.getByRole('button', {name: objectFieldName}).click();

	await emailNotificationTemplatePage.saveButton.click();

	await notificationTemplatesPage
		.getFrontEndDatasetItemLocator(notificationTemplateName)
		.click();

	await expect(
		page
			.locator('.CodeMirror-lines')
			.getByText(`{ObjectField_${objectFieldName}.getData()}`)
	).toBeVisible();
});
