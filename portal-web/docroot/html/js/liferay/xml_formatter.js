AUI.add(
	'liferay-xml-formatter',
	function(A) {
		var Lang = A.Lang;
		var AArray = A.Array;

		var REGEX_DECLARATIVE_CLOSE = /-->|\]>/;

		var REGEX_DECLARATIVE_OPEN = /<!/;

		var REGEX_DIRECTIVE = /<\?/;

		var REGEX_DOCTYPE = /!DOCTYPE/;

		var REGEX_ELEMENT = /^<\w/;

		var REGEX_ELEMENT_CLOSE = /^<\/\w/;

		var REGEX_ELEMENT_NAMESPACED = /^<[\w:\-\.\,]+/;

		var REGEX_ELEMENT_NAMESPACED_CLOSE = /^<\/[\w:\-\.\,]+/;

		var REGEX_ELEMENT_OPEN = /<\w/;

		var REGEX_NAMESPACE_XML = /xmlns(?:\:|\=)/g;

		var REGEX_NAMESPACE_XML_ATTR = /\s*(xmlns)(\:|\=)/g;

		var REGEX_TAG_CLOSE = /<\//;

		var REGEX_TAG_OPEN = /</g;

		var REGEX_TAG_SINGLE_CLOSE = /\/>/;

		var REGEX_WHITESPACE_BETWEEN_TAGS = />\s+</g;

		var STR_BLANK = '';

		var STR_TOKEN = '~::~';

		var XMLFormatter = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'liferayxmlformatter',

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
					format: function(content) {
						var instance = this;

						var tagIndent = instance.get('tagIndent');

						var lineIndent = instance.get('lineIndent');

						content = instance.minify(content);

						content = content.replace(REGEX_TAG_OPEN, STR_TOKEN + '<');
						content = content.replace(REGEX_NAMESPACE_XML_ATTR, STR_TOKEN + '$1$2');

						var items = content.split(STR_TOKEN);

						var inComment = false;

						var level = 0;

						var result = STR_BLANK;

						AArray.each(
							items,
							function(item, index, collection) {
								if (REGEX_DECLARATIVE_OPEN.test(item)) {
									result += instance._indent(lineIndent, tagIndent, level) + item;

									inComment = true;

									if (REGEX_DECLARATIVE_CLOSE.test(item) || REGEX_DOCTYPE.test(item)) {
										inComment = false;
									}
								}
								else if (REGEX_DECLARATIVE_CLOSE.test(item)) {
									result += item;

									inComment = false;
								}
								else if (REGEX_ELEMENT.exec(items[index - 1]) && REGEX_ELEMENT_CLOSE.exec(item) &&
									REGEX_ELEMENT_NAMESPACED.exec(items[index - 1]) == REGEX_ELEMENT_NAMESPACED_CLOSE.exec(item)[0].replace('/', STR_BLANK)) {
									result += item;

									if (!inComment) {
										--level;
									}
								}
								else if (REGEX_ELEMENT_OPEN.test(item) && !REGEX_TAG_CLOSE.test(item) && !REGEX_TAG_SINGLE_CLOSE.test(item) ) {
									if (inComment) {
										result += item;
									}
									else {
										result += instance._indent(lineIndent, tagIndent, level++) + item;
									}
								}
								else if (REGEX_ELEMENT_OPEN.test(item) && REGEX_TAG_CLOSE.test(item)) {
									if (inComment) {
										result += item;
									}
									else {
										result += instance._indent(lineIndent, tagIndent, level) + item;
									}
								}
								else if (REGEX_TAG_CLOSE.test(item)) {
									if (inComment) {
										result += item;
									}
									else {
										result += instance._indent(lineIndent, tagIndent, --level) + item;
									}
								}
								else if (REGEX_TAG_SINGLE_CLOSE.test(item) ) {
									if (inComment) {
										result += item;
									}
									else {
										result += instance._indent(lineIndent, tagIndent, level) + item;
									}
								}
								else if (REGEX_DIRECTIVE.test(item)) {
									result += instance._indent(lineIndent, tagIndent, level) + item;
								}
								else if (REGEX_NAMESPACE_XML) {
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

					minify: function(content) {
						return content.replace(REGEX_WHITESPACE_BETWEEN_TAGS, '><');
					},

					_indent: function(lineIndent, separator, times) {
						var instance = this;

						return lineIndent + new Array(times + 1).join(separator);
					}
				}
			}
		);

		Liferay.XMLFormatter = XMLFormatter;
	},
	'',
	{
		requires: ['aui-base']
	}
);