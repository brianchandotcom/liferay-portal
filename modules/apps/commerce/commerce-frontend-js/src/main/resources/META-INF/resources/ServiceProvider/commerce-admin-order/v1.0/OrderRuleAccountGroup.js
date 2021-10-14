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

export default (basePath) => ({
	addOrderRuleAccountGroup: (orderRuleId, json) =>
		AJAX.POST(resolvePath(basePath, orderRuleId), json),
});
