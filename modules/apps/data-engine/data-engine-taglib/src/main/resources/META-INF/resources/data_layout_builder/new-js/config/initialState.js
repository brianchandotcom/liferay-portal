/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {INITIAL_STATE as CORE_INITIAL_STATE} from 'data-engine-js-components-web/js/core/config/index.es';

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
