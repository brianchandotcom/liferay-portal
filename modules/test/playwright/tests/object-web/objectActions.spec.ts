/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {editObjectDefinitionPagesTest} from '../../fixtures/editObjectDefinitionPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	editObjectDefinitionPagesTest,
	loginTest(),
	objectPagesTest
);

const createdEntities = {
	notificationTemplatesId: [],
	objectActionsId: [],
	objectDefinition: {},
} as {
	notificationTemplatesId: number[];
	objectActionsId: number[];
	objectDefinition: ObjectDefinition;
};

test.beforeEach(async ({apiHelpers}) => {
	const newObjectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFolderExternalReferenceCode: 'default',
			status: {code: 0},
		});

	createdEntities.objectDefinition = newObjectDefinition;
});

test.afterEach(async ({apiHelpers}) => {
	await apiHelpers.objectAdmin.deleteObjectDefinition(
		createdEntities.objectDefinition.id
	);

	for (const templateId of createdEntities.notificationTemplatesId) {
		await apiHelpers.notification.deleteNotificationTemplate(templateId);

		createdEntities.notificationTemplatesId = [];
	}

	for (const actionId of createdEntities.objectActionsId) {
		await apiHelpers.objectAdmin.deleteObjectAction(actionId);

		createdEntities.objectActionsId = [];
	}
});

test.describe('Manage object actions through object actions tab', () => {
	test('notification action section must display all persisted notifications', async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		const names: string[] = [];

		const {notificationTemplatesId, objectDefinition} = createdEntities;

		for (let index = 1; index <= 21; index++) {
			const notificationTemplate =
				await apiHelpers.notification.postRandomNotificationTemplate(
					'notification template test ' + getRandomInt()
				);
			notificationTemplatesId.push(notificationTemplate.id);
			names.push(
				notificationTemplate.name + ' ' + notificationTemplate.type
			);
		}

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.chooseNotificationOption();

		await editObjectActionPage.clickInputNotificationsCombo();

		for (let index = 0; index < names.length; index++) {
			await expect(
				page
					.frameLocator('iframe')
					.getByRole('option', {name: names[index]})
			).toBeVisible();
		}
	});

	test('can create actions related to commerce order object', async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		const {objectActionsId} = createdEntities;

		await viewObjectActionsPage.goto('Commerce Order');

		const objectActionsMock = [
			{
				objectAction: 'On Order Status Update',
			},
			{
				objectAction: 'On Payment Status Update',
			},
			{
				objectAction: 'On Subscription Status Update',
			},
		] as {objectAction: string}[];

		for (const {objectAction} of objectActionsMock) {
			await editObjectActionPage.addNewAction(
				'Split Order by Catalog',
				objectAction
			);
		}

		const objectActions =
			await apiHelpers.objectAdmin.getObjectActionsByExternalReferenceCode(
				'L_COMMERCE_ORDER'
			);

		objectActions.items.forEach((objectAction: ObjectAction) =>
			objectActionsId.push(objectAction.id)
		);

		for (const {objectAction} of objectActionsMock) {
			await expect(page.getByText(objectAction)).toBeVisible();
		}
	});
});
