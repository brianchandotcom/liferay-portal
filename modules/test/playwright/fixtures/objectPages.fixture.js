/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ModelBuilderPage} from '../pages/object/modelBuilder.page';
import {ObjectDefinitionsPage} from '../pages/object/objectDefinitions.page';

exports.test = test.extend({
	_modelBuilderPage: async ({page}, use) => {
		await use(new ModelBuilderPage(page));
	},
	_objectDefinitionsPage: async ({page}, use) => {
		await use(new ObjectDefinitionsPage(page));
	},
});
