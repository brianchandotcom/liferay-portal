/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INITIAL_STATE as CORE_INITIAL_STATE} from 'data-engine-js-components-web';

export const INITIAL_STATE = {
	...CORE_INITIAL_STATE,
	availableLanguageIds: [themeDisplay.getDefaultLanguageId()],
	dataDefinition: {
		dataDefinitionFields: [],
	},
	dataDefinitionId: 0,
	dataLayout: {
		dataLayoutFields: {},
	},
	dataLayoutId: 0,
	editingDataDefinitionId: 0,
	editingLanguageId: themeDisplay.getDefaultLanguageId(),
	fieldSets: [],
	name: {},
	paginationMode: 'single-page',
};

export default INITIAL_STATE;
