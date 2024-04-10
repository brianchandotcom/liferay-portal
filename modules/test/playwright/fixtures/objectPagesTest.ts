/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AddObjectDefinitionModalPage} from '../pages/object-web/AddObjectDefinitionModalPage';
import {ModelBuilderPage} from '../pages/object-web/ModelBuilderPage';
import {ObjectDefinitionsPage} from '../pages/object-web/ObjectDefinitionsPage';

const objectPagesTest = test.extend<{
	addObjectDefinitionModalPage: AddObjectDefinitionModalPage;
	modelBuilderPage: ModelBuilderPage;
	objectDefinitionsPage: ObjectDefinitionsPage;
}>({
	addObjectDefinitionModalPage: async ({page}, use) => {
		await use(new AddObjectDefinitionModalPage(page));
	},
	modelBuilderPage: async ({page}, use) => {
		await use(new ModelBuilderPage(page));
	},
	objectDefinitionsPage: async ({page}, use) => {
		await use(new ObjectDefinitionsPage(page));
	},
});

export {objectPagesTest};
