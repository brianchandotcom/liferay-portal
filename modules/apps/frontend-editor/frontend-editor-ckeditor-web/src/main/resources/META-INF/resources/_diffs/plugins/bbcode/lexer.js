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

(function() {
	// eslint-disable-next-line no-control-regex
	var REGEX_BBCODE = /(?:\[((?:[a-z]|\*){1,16})(?:[=\s]([^\x00-\x1F'<>[\]]{1,2083}))?\])|(?:\[\/([a-z]{1,16})\])/gi;

	var Lexer = function(data) {
		var instance = this;

		instance._data = data;
	};

	Lexer.prototype = {
		constructor: Lexer,

		getLastIndex() {
			return REGEX_BBCODE.lastIndex;
		},

		getNextToken() {
			var instance = this;

			return REGEX_BBCODE.exec(instance._data);
		},
	};

	Liferay.BBCodeLexer = Lexer;
})();
