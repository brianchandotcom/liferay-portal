/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INITIAL_CONFIG_STATE as INITIAL_FORM_RENDERER_CONFIG_STATE} from 'data-engine-js-components-web/js/core/config/index.es';
import React from 'react';

import ElementSetList from '../plugins/field-sidebar/components/ElementSetList.es';

const tabs = [
	{
		label: Liferay.Language.get('element-sets'),
		render: ({searchTerm}) => <ElementSetList searchTerm={searchTerm} />,
	},
];

export const INITIAL_CONFIG_STATE = {
	...INITIAL_FORM_RENDERER_CONFIG_STATE,
	allowFieldSets: false,
	allowMultiplePages: false,
	allowNestedFields: true,
	allowRules: true,
	allowSuccessPage: false,
	disabledProperties: [],
	disabledTabs: [],
	tabs,
	unimplementedProperties: [
		'fieldNamespace',
		'readOnly',
		'visibilityExpression',
	],
	visibleProperties: [],
};
