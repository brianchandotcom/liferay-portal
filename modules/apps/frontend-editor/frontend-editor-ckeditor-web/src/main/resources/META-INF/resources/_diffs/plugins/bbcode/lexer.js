/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	// eslint-disable-next-line no-control-regex
	const REGEX_BBCODE = /(?:\[((?:[a-z]|\*){1,16})(?:[=\s]([^\x00-\x1F'<>[\]]{1,2083}))?\])|(?:\[\/([a-z]{1,16})\])/gi;

	const Lexer = function (data) {
		const instance = this;

		instance._data = data;
	};

	Lexer.prototype = {
		constructor: Lexer,

		getLastIndex() {
			return REGEX_BBCODE.lastIndex;
		},

		getNextToken() {
			const instance = this;

			return REGEX_BBCODE.exec(instance._data);
		},
	};

	Liferay.BBCodeLexer = Lexer;
})();
