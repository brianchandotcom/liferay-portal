/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const DISCOUNTS_PATH = '/discounts';

const DISCOUNT_RULES_PATH = '/discount-rules';

const VERSION = 'v2.0';

function resolvePath(basePath = '', discountId = '') {
	return `${basePath}${VERSION}${DISCOUNTS_PATH}/${discountId}${DISCOUNT_RULES_PATH}`;
}

function resolveRulePath(basePath = '', discountRuleId = '') {
	return `${basePath}${VERSION}${DISCOUNT_RULES_PATH}/${discountRuleId}`;
}

export default function DiscountRule(basePath) {
	return {
		addDiscountRule: (discountId, json) =>
			AJAX.POST(resolvePath(basePath, discountId), json),

		updateDiscountRule: (discountRuleId, json) =>
			AJAX.PATCH(resolveRulePath(basePath, discountRuleId), json),
	};
}
