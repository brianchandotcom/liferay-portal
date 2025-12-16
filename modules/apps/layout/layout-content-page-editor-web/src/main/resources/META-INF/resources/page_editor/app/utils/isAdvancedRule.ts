/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

import {AdvancedRule, Rule} from '../../types/Rule';

export function isAdvancedRule(rule: Rule): rule is AdvancedRule {
	return !isNullOrUndefined(rule?.script);
}
