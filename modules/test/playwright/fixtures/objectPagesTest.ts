/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EditObjectDefinitionPage} from '../pages/object-web/EditObjectDefinitionPage';
import {ModalAddObjectDefinitionPage} from '../pages/object-web/ModalAddObjectDefinitionPage';
import {ModalEditObjectFolderPage} from '../pages/object-web/ModalEditObjectFolderPage';
import {ModelBuilderPage} from '../pages/object-web/ModelBuilderPage';
import {ViewObjectDefinitionsPage} from '../pages/object-web/ViewObjectDefinitionsPage';
import {ViewObjectEntriesPage} from '../pages/object-web/object-entries/ViewObjectEntriesPage';
import {EditObjectValidationPage} from '../pages/object-web/object-validation/EditObjectValidationPage';
import {ModalAddObjectValidationPage} from '../pages/object-web/object-validation/ModalAddObjectValidationPage';
import {ObjectValidationsFDSPage} from '../pages/object-web/object-validation/ObjectValidationsFDSPage';

const objectPagesTest = test.extend<{
	editObjectDefinitionPage: EditObjectDefinitionPage;
	editObjectValidationPage: EditObjectValidationPage;
	modalAddObjectDefinitionPage: ModalAddObjectDefinitionPage;
	modalAddObjectValidationPage: ModalAddObjectValidationPage;
	modalEditObjectFolderPage: ModalEditObjectFolderPage;
	modelBuilderPage: ModelBuilderPage;
	objectValidationsFDSPage: ObjectValidationsFDSPage;
	viewObjectDefinitionsPage: ViewObjectDefinitionsPage;
	viewObjectEntriesPage: ViewObjectEntriesPage;
}>({
	editObjectDefinitionPage: async ({page}, use) => {
		await use(new EditObjectDefinitionPage(page));
	},
	editObjectValidationPage: async ({page}, use) => {
		await use(new EditObjectValidationPage(page));
	},
	modalAddObjectDefinitionPage: async ({page}, use) => {
		await use(new ModalAddObjectDefinitionPage(page));
	},
	modalAddObjectValidationPage: async ({page}, use) => {
		await use(new ModalAddObjectValidationPage(page));
	},
	modalEditObjectFolderPage: async ({page}, use) => {
		await use(new ModalEditObjectFolderPage(page));
	},
	modelBuilderPage: async ({page}, use) => {
		await use(new ModelBuilderPage(page));
	},
	objectValidationsFDSPage: async ({page}, use) => {
		await use(new ObjectValidationsFDSPage(page));
	},
	viewObjectDefinitionsPage: async ({page}, use) => {
		await use(new ViewObjectDefinitionsPage(page));
	},
	viewObjectEntriesPage: async ({page}, use) => {
		await use(new ViewObjectEntriesPage(page));
	},
});

export {objectPagesTest};
