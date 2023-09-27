/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
