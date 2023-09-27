/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ORDER_RULES_PATH = '/order-rules';

const VERSION = 'v1.0';

function resolvePath(basePath = '', orderRuleId = '') {
	return `${basePath}${VERSION}${ORDER_RULES_PATH}/${orderRuleId}`;
}

export default function OrderRule(basePath) {
	return {
		addOrderRule: (json) => AJAX.POST(resolvePath(basePath), json),
	};
}
