/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const TERM_PATH = '/terms';

const TERM_ORDER_TYPES_PATH = '/term-order-types';

const VERSION = 'v1.0';

function resolvePath(basePath = '', termId = '', termOrderTypeId = '') {
	return `${basePath}${VERSION}${TERM_PATH}/${termId}${TERM_ORDER_TYPES_PATH}/${termOrderTypeId}`;
}

export default function TermOrderType(basePath) {
	return {
		addTermOrderType: (termId, json) =>
			AJAX.POST(resolvePath(basePath, termId), json),
	};
}
