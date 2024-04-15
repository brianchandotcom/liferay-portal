/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ModalAddObjectDefinitionPage} from '../pages/object-web/ModalAddObjectDefinitionPage';
import {ModalEditObjectFolderPage} from '../pages/object-web/ModalEditObjectFolderPage';
import {ModelBuilderPage} from '../pages/object-web/ModelBuilderPage';
import {ViewObjectDefinitionsPage} from '../pages/object-web/ViewObjectDefinitionsPage';

const objectPagesTest = test.extend<{
	modalAddObjectDefinitionPage: ModalAddObjectDefinitionPage;
	modalEditObjectFolderPage: ModalEditObjectFolderPage;
	modelBuilderPage: ModelBuilderPage;
	viewObjectDefinitionsPage: ViewObjectDefinitionsPage;
}>({
	modalAddObjectDefinitionPage: async ({page}, use) => {
		await use(new ModalAddObjectDefinitionPage(page));
	},
	modalEditObjectFolderPage: async ({page}, use) => {
		await use(new ModalEditObjectFolderPage(page));
	},
	modelBuilderPage: async ({page}, use) => {
		await use(new ModelBuilderPage(page));
	},
	viewObjectDefinitionsPage: async ({page}, use) => {
		await use(new ViewObjectDefinitionsPage(page));
	},
});

export {objectPagesTest};
