/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ORDER_RULE_PATH = '/order-rules';

const ORDER_RULE_ACCOUNT_GROUPS_PATH = '/order-rule-account-groups';

const VERSION = 'v1.0';

function resolvePath(
	basePath = '',
	orderRuleId = '',
	orderRuleAccountGroupId = ''
) {
	return `${basePath}${VERSION}${ORDER_RULE_PATH}/${orderRuleId}${ORDER_RULE_ACCOUNT_GROUPS_PATH}/${orderRuleAccountGroupId}`;
}

export default function OrderRuleAccountGroup(basePath) {
	return {
		addOrderRuleAccountGroup: (orderRuleId, json) =>
			AJAX.POST(resolvePath(basePath, orderRuleId), json),
	};
}
