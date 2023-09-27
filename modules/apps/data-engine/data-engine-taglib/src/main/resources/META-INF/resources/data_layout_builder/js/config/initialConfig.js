/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INITIAL_CONFIG_STATE} from 'data-engine-js-components-web';

const INITIAL_CONFIG = {
	...INITIAL_CONFIG_STATE,
	allowFieldSets: false,
	allowNestedFields: true,
	allowRules: false,
	disabledProperties: [],
	disabledTabs: [],
	multiPage: true,
	ruleSettings: {},
	unimplementedProperties: [],
	visibleProperties: [],
};

export default INITIAL_CONFIG;
