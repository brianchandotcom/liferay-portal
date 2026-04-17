/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

const notificationTemplateInfo = {
	description: 'This is a description',
	subject: 'Subject',
	term: '[%CURRENT_USER_FIRST_NAME%]',
};

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	notificationPagesTest
);

test.describe('User notification template', () => {
	test('can be created and saved correctly', async ({
		page,
		userNotificationTemplatePage,
	}) => {
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

		await page.getByRole('link', {name: notificationTemplateName}).click();

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

	test(
		'Support for User Groups in User Notification template',
		{tag: '@LPD-57578'},
		async ({apiHelpers, page, userNotificationTemplatePage}) => {
			const userGroup1 =
				await apiHelpers.headlessAdminUser.postUserGroup();
			const userGroup2 =
				await apiHelpers.headlessAdminUser.postUserGroup();

			await test.step('AC1: Display "User Group" option in the Recipient field.', async () => {
				await userNotificationTemplatePage.goto();
				await userNotificationTemplatePage.selectNotificationRecipient(
					'User Group'
				);
			});

			await test.step('AC2: Display existing user groups in the "User Group" field', async () => {
				await userNotificationTemplatePage.page
					.getByRole('combobox', {name: 'Select User Group'})
					.click();
				await expect(
					userNotificationTemplatePage.page.getByText(userGroup1.name)
				).toBeVisible();
				await expect(
					userNotificationTemplatePage.page.getByText(userGroup2.name)
				).toBeVisible();
			});

			await test.step('AC3: Multi-Selection Support', async () => {
				for (const userGroupName of [
					userGroup1.name,
					userGroup2.name,
				]) {
					await userNotificationTemplatePage.page
						.getByLabel(userGroupName)
						.click();
					await expect(
						userNotificationTemplatePage.page.getByLabel(
							userGroupName,
							{exact: true}
						)
					).toBeVisible();
				}
			});

			await test.step('AC4: Support Search and Filtering', async () => {
				userNotificationTemplatePage.page
					.getByRole('textbox', {name: 'Search for a User Group.'})
					.fill(userGroup1.name);
				await expect(
					userNotificationTemplatePage.page.getByLabel(
						userGroup1.name,
						{
							exact: true,
						}
					)
				).toBeVisible();
				await expect(
					userNotificationTemplatePage.page.getByLabel(
						userGroup2.name,
						{
							exact: true,
						}
					)
				).not.toBeVisible();
			});

			const notificationTemplateName = getRandomString();

			await userNotificationTemplatePage.basicInfoName.fill(
				notificationTemplateName
			);

			await userNotificationTemplatePage.contentSubject.fill(
				getRandomString()
			);

			await userNotificationTemplatePage.saveButton.click();

			await page
				.getByRole('link', {name: notificationTemplateName})
				.click();

			await test.step('AC5: Save User Group Selection', async () => {
				for (const userGroupName of [
					userGroup1.name,
					userGroup2.name,
				]) {
					await expect(
						userNotificationTemplatePage.page.getByRole('row', {
							name: `${userGroupName} Remove`,
						})
					).toBeVisible();
				}
			});
		}
	);

	test(
		'can delete user notification template',
		{tag: '@LPD-78504'},
		async ({
			notificationTemplatesPage,
			page,
			userNotificationTemplatePage,
		}) => {
			const templateName =
				'User Notification Template ' + getRandomInt();

			await userNotificationTemplatePage.goto();

			await userNotificationTemplatePage.basicInfoName.fill(templateName);
			await userNotificationTemplatePage.contentSubject.fill(
				'Subject content'
			);

			await userNotificationTemplatePage.selectNotificationRecipient('User');

			await page.getByPlaceholder('Enter user name.').click();
			await page.getByLabel('Test', {exact: true}).check();
			await page.keyboard.press('Escape');

			await userNotificationTemplatePage.saveButton.click();

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
});
