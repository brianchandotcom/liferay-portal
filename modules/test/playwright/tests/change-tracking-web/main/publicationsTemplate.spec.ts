/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../../fixtures/changeTrackingPagesTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(changeTrackingPagesTest);

test('Can go to create a new publication after deleting default template', async ({
	changeTrackingPage,
	changeTrackingTemplatesPage,
}) => {
	await changeTrackingTemplatesPage.gotoCreateTemplate();

	const templateName = getRandomString();

	await changeTrackingTemplatesPage.addTemplate(templateName, true);

	await changeTrackingTemplatesPage.deleteTemplate(templateName);

	await changeTrackingPage.goToAddPublication();
});
