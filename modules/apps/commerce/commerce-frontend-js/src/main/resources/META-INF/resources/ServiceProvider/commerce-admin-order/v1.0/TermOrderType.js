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
