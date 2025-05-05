/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {editObjectDefinitionPagesTest} from '../../../fixtures/editObjectDefinitionPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	editObjectDefinitionPagesTest,
	loginTest(),
	notificationPagesTest,
	objectPagesTest,
	usersAndOrganizationsPagesTest
);

const notificationTemplateInfo = {
	description: 'This is a description',
	subject: 'Subject',
	term: '[%CURRENT_USER_FIRST_NAME%]',
};

test('can create a template', async ({page, userNotificationTemplatePage}) => {
	await userNotificationTemplatePage.goto();

	const notificationTemplateName =
		'Notification Template Name' + getRandomInt();

	await userNotificationTemplatePage.basicInfoName.fill(
		notificationTemplateName
	);

	await userNotificationTemplatePage.descriptionInput.fill(
		notificationTemplateInfo.description
	);

	await userNotificationTemplatePage.toInput.fill(
		notificationTemplateInfo.term
	);

	await userNotificationTemplatePage.contentSubject.fill(
		notificationTemplateInfo.subject
	);

	await userNotificationTemplatePage.saveButton.click();

	await page.getByText(notificationTemplateName).click();

	await expect(userNotificationTemplatePage.basicInfoName).toHaveValue(
		notificationTemplateName
	);

	await expect(userNotificationTemplatePage.descriptionInput).toHaveValue(
		notificationTemplateInfo.description
	);

	await expect(userNotificationTemplatePage.toInput).toHaveValue(
		notificationTemplateInfo.term
	);

	await expect(userNotificationTemplatePage.contentSubject).toHaveValue(
		notificationTemplateInfo.subject
	);
});

test('user notification is sent to regular role', async ({
	apiHelpers,
	editObjectActionPage,
	notificationsPage,
	page,
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

	await page.getByText(notificationTemplateName).click();

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

	await editObjectActionPage.addNewAction(
		'Notification',
		'On After Add',
		notificationTemplateName
	);

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';

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
});
