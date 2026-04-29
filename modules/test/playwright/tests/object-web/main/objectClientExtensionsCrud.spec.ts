/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {editObjectDefinitionPagesTest} from '../../../fixtures/editObjectDefinitionPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';

const test = mergeTests(
	dataApiHelpersTest,
	editObjectDefinitionPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Can trigger action with unmodifiable system object definition using client extension',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

		// Corresponds to Poshi test: CanTriggerActionWithUnmodifiableSystemObjectDefinition

		const notificationTemplate =
			await apiHelpers.notification.postRandomNotificationTemplate(
				'notificationTemplate' + getRandomInt()
			);

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		// Navigate to a system object's actions page (User is an unmodifiable system object)

		await viewObjectActionsPage.goto('User');

		// Add a new action using notification type on the system object

		await editObjectActionPage.addNewAction({
			notificationTemplateName: notificationTemplate.name,
			thenOption: 'Notification',
			whenOption: 'On After Add',
		});

		// Verify the action was created by checking it appears in the actions list

		await expect(page.getByText('On After Add')).toBeVisible();
	}
);
