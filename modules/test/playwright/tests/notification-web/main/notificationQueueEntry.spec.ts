/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectActionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {smtpPagesTest} from '../../../fixtures/smtpPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	smtpPagesTest
);

test.describe('Notification queue entry', () => {
	test(
		'can delete a notification',
		{tag: '@LPD-78504'},
		async ({apiHelpers}) => {
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

			const expectedSubject = notificationTemplate.subject.en_US;

			let notificationQueueEntryId: number | undefined;

			await expect
				.poll(
					async () => {
						const {items = []} =
							await apiHelpers.notification.getNotificationQueueEntriesPage(
								expectedSubject
							);

						const match = items.find(
							(item: {subject: string}) =>
								item.subject === expectedSubject
						);

						notificationQueueEntryId = match?.id;

						return notificationQueueEntryId;
					},
					{timeout: 15_000}
				)
				.toBeDefined();

			await apiHelpers.notification.deleteNotificationQueueEntry(
				notificationQueueEntryId!
			);

			await expect
				.poll(
					async () => {
						const {items = []} =
							await apiHelpers.notification.getNotificationQueueEntriesPage(
								expectedSubject
							);

						return items.some(
							(item: {id: number}) =>
								item.id === notificationQueueEntryId
						);
					},
					{timeout: 15_000}
				)
				.toBe(false);
		}
	);
});
