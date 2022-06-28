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

(function () {
	const A = AUI();

	const entities = {
		...Liferay.Util.MAP_HTML_CHARS_ESCAPED,
		'(': '&#40;',
		')': '&#41;',
		'[': '&#91;',
		']': '&#93;',
	};

	const BBCodeUtil = Liferay.namespace('BBCodeUtil');

	BBCodeUtil.escape = A.rbind('escapeHTML', Liferay.Util, true, entities);
	BBCodeUtil.unescape = A.rbind('unescapeHTML', Liferay.Util, entities);
})();
