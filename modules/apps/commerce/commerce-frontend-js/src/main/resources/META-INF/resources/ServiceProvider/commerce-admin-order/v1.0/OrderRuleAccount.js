/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ORDER_RULE_PATH = '/order-rules';

const ORDER_RULE_ACCOUNTS_PATH = '/order-rule-accounts';

const VERSION = 'v1.0';

function resolvePath(basePath = '', orderRuleId = '', orderRuleAccountId = '') {
	return `${basePath}${VERSION}${ORDER_RULE_PATH}/${orderRuleId}${ORDER_RULE_ACCOUNTS_PATH}/${orderRuleAccountId}`;
}

export default function OrderRuleAccount(basePath) {
	return {
		addOrderRuleAccount: (orderRuleId, json) =>
			AJAX.POST(resolvePath(basePath, orderRuleId), json),
	};
}
