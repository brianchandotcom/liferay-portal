/**
 * XML Beautifier is based on vkBeautify
 *
 * Copyright (c) 2012 Vadim Kiryukhin
 * vkiryukhin @ gmail.com
 * http://www.eslinstructor.net/vkbeautify/
 *
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 */

AUI.add(
	'liferay-xml-beautifier',
	function(A) {
		var Lang = A.Lang;

		var AArray = A.Array;

		var STR_BLANK = '';

		var XMLBeautifier = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'xmlbeautifier',

				ATTRS: {
					lineIndent: {
						validator: Lang.isString,
						value: '\r\n'
					},

					tagIndent: {
						validator: Lang.isString,
						value: '\t'
					}
				},

				prototype: {
					beautify: function(content) {
						var instance = this;

						var tagIndent = instance.get('tagIndent');

						var lineIndent = instance.get('lineIndent');

						var items = content.replace(/>\s{0,}</g, '><')
							.replace(/</g, '~::~<')
							.replace(/\s*xmlns\:/g, '~::~xmlns:')
							.replace(/\s*xmlns\=/g, '~::~xmlns=')
							.split('~::~');

						var inComment = false;

						var level = 0;

						var result = STR_BLANK;

						AArray.each(
							items,
							function(item, index, collection) {
								if (/<!/.test(item)) {
									result += instance._indent(lineIndent, tagIndent, level) + item;

									inComment = true;

									if (/-->/.test(item) || /\]>/.test(item) || /!DOCTYPE/.test(item)) {
										inComment = false;
									}
								}
								else if(/-->/.test(item) || /\]>/.test(item)) {
									result += item;

									inComment = false;
								}
								else if(/^<\w/.exec(items[index - 1]) && /^<\/\w/.exec(item) &&
									/^<[\w:\-\.\,]+/.exec(items[index - 1]) == /^<\/[\w:\-\.\,]+/.exec(item)[0].replace('/', STR_BLANK)) {
									result += item;

									if (!inComment) {
										--level;
									}
								}
								else if(/<\w/.test(item) && !/<\//.test(item) && !/\/>/.test(item) ) {
									if (inComment) {
										result += item;
									}
									else {
										result += instance._indent(lineIndent, tagIndent, level++) + item;
									}
								}
								else if(/<\w/.test(item) && /<\//.test(item)) {
									if (inComment) {
										result += item;
									}
									else {
										result += instance._indent(lineIndent, tagIndent, level) + item;
									}
								}
								else if(/<\//.test(item)) {
									if (inComment) {
										result += item;
									}
									else {
										result += instance._indent(lineIndent, tagIndent, --level) + item;
									}
								}
								else if(/\/>/.test(item) ) {
									if (inComment) {
										result += item;
									}
									else {
										result += instance._indent(lineIndent, tagIndent, level) + item;
									}
								}
								else if(/<\?/.test(item)) {
									result += instance._indent(lineIndent, tagIndent, level) + item;
								}
								else if(/xmlns\:/.test(item) || /xmlns\=/.test(item)) {
									result += instance._indent(lineIndent, tagIndent, level) + item;
								}
								else {
									result += item;
								}
							}
						);

						if (new RegExp('^' + lineIndent).test(result)) {
							result = result.slice(lineIndent.length);
						}

						return result;
					},

					uglify: function(content) {
						return content.replace(/>\s{0,}</g, '><');
					},

					_indent: function(lineIndent, separator, times) {
						var instance = this;

						return lineIndent + new Array(times + 1).join(separator);
					}
				}
			}
		);

		Liferay.XMLBeautifier = XMLBeautifier;
	},
	'',
	{
		requires: ['aui-base']
	}
);