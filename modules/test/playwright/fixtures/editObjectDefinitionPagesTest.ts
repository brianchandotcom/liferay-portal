/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EditObjectDefinitionPage} from '../pages/object-web/EditObjectDefinitionPage';
import {ActionBuilderPage} from '../pages/object-web/object-action/ActionBuilderPage';
import {SidePanelObjectActionPage} from '../pages/object-web/object-action/SidePanelObjectActionPage';
import {ViewObjectActionsPage} from '../pages/object-web/object-action/ViewObjectActionsPage';
import {ObjectLayoutsPage} from '../pages/object-web/object-layout/ObjectLayoutsPage';

const editObjectDefinitionPagesTest = test.extend<{
	actionBuilderPage: ActionBuilderPage;
	editObjectDefinitionPage: EditObjectDefinitionPage;
	objectLayoutsPage: ObjectLayoutsPage;
	sidePanelObjectActionPage: SidePanelObjectActionPage;
	viewObjectActionsPage: ViewObjectActionsPage;
}>({
	actionBuilderPage: async ({page}, use) => {
		await use(new ActionBuilderPage(page));
	},
	editObjectDefinitionPage: async ({page}, use) => {
		await use(new EditObjectDefinitionPage(page));
	},
	objectLayoutsPage: async ({page}, use) => {
		await use(new ObjectLayoutsPage(page));
	},
	sidePanelObjectActionPage: async ({page}, use) => {
		await use(new SidePanelObjectActionPage(page));
	},
	viewObjectActionsPage: async ({page}, use) => {
		await use(new ViewObjectActionsPage(page));
	},
});

export {editObjectDefinitionPagesTest};
