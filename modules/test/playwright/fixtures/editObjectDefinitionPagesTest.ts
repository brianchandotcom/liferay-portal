/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EditObjectDefinitionPage} from '../pages/object-web/EditObjectDefinitionPage';
import {EditObjectActionPage} from '../pages/object-web/object-action/EditObjectActionPage';
import {ViewObjectActionsPage} from '../pages/object-web/object-action/ViewObjectActionsPage';
import {ObjectLayoutsPage} from '../pages/object-web/object-layout/ObjectLayoutsPage';

const editObjectDefinitionPagesTest = test.extend<{
	editObjectActionPage: EditObjectActionPage;
	editObjectDefinitionPage: EditObjectDefinitionPage;
	objectLayoutsPage: ObjectLayoutsPage;
	viewObjectActionsPage: ViewObjectActionsPage;
}>({
	editObjectActionPage: async ({page}, use) => {
		await use(new EditObjectActionPage(page));
	},
	editObjectDefinitionPage: async ({page}, use) => {
		await use(new EditObjectDefinitionPage(page));
	},
	objectLayoutsPage: async ({page}, use) => {
		await use(new ObjectLayoutsPage(page));
	},
	viewObjectActionsPage: async ({page}, use) => {
		await use(new ViewObjectActionsPage(page));
	},
});

export {editObjectDefinitionPagesTest};
