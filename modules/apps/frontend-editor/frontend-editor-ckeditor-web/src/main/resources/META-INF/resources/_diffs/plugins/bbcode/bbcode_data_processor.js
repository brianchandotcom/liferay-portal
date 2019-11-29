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
	var toHex = function(val) {
		val = parseInt(val, 10).toString(16);

		if (val.length === 1) {
			val = '0' + val;
		}

		return val;
	};

	var MAP_HANDLERS = {
		a: '_handleLink',
		b: '_handleStrong',
		blockquote: '_handleQuote',
		br: '_handleBreak',
		caption: '_handleTableCaption',
		cite: '_handleCite',
		code: '_handlePre',
		em: '_handleEm',
		font: '_handleFont',
		i: '_handleEm',
		img: '_handleImage',
		li: '_handleListItem',
		ol: '_handleOrderedList',
		pre: '_handlePre',
		s: '_handleLineThrough',
		strike: '_handleLineThrough',
		strong: '_handleStrong',
		table: '_handleTable',
		td: '_handleTableCell',
		th: '_handleTableHeader',
		tr: '_handleTableRow',
		u: '_handleUnderline',
		ul: '_handleUnorderedList'
	};

	var MAP_IMAGE_ATTRIBUTES = [
		'alt',
		'class',
		'data-image-id',
		'dir',
		'height',
		'id',
		'lang',
		'longdesc',
		'style',
		'title',
		'width'
	];

	var MAP_LINK_HANDLERS = {
		0: 'email'
	};

	var NEW_LINE = '\n';

	var REGEX_COLOR_RGB = /^rgb\s*\(\s*([01]?\d\d?|2[0-4]\d|25[0-5]),\s*([01]?\d\d?|2[0-4]\d|25[0-5]),\s*([01]?\d\d?|2[0-4]\d|25[0-5])\s*\)$/;

	var REGEX_EM = /em$/i;

	var REGEX_LASTCHAR_NEWLINE_WHITESPACE = /(\r?\n\s*)$/;

	var REGEX_LIST_CIRCLE = /circle/i;

	var REGEX_LIST_LOWER_ALPHA = /lower-alpha/i;

	var REGEX_LIST_LOWER_ROMAN = /lower-roman/i;

	var REGEX_LIST_SQUARE = /square/i;

	var REGEX_LIST_UPPER_ALPHA = /upper-alpha/i;

	var REGEX_LIST_UPPER_ROMAN = /upper-roman/i;

	var REGEX_NEWLINE = /\r?\n/g;

	var REGEX_PERCENT = /%$/i;

	var REGEX_PRE = /<pre>/gi;

	var REGEX_PX = /px$/i;

	var REGEX_SINGLE_QUOTE = /'/g;

	var STR_EMPTY = '';

	var STR_MAILTO = 'mailto:';

	var TAG_BLOCKQUOTE = 'blockquote';

	var TAG_BR = 'br';

	var TAG_CITE = 'cite';

	var TAG_CODE = 'code';

	var TAG_DIV = 'div';

	var TAG_LI = 'li';

	var TAG_LINK = 'a';

	var TAG_PARAGRAPH = 'p';

	var TAG_PRE = 'pre';

	var TAG_TABLE = 'table';

	var TAG_TD = 'td';

	var BBCodeDataProcessor = function(editor) {
		this._editor = editor;
	};

	BBCodeDataProcessor.prototype = {
		_allowNewLine(element) {
			var instance = this;

			var allowNewLine = true;

			if (!instance._inPRE) {
				var parentNode = element.parentNode;

				if (parentNode) {
					var parentTagName = parentNode.tagName;

					if (parentTagName) {
						parentTagName = parentTagName.toLowerCase();

						if (
							(parentTagName === TAG_PARAGRAPH &&
								parentNode.style.cssText) ||
							(CKEDITOR.env.gecko &&
								element.tagName &&
								element.tagName.toLowerCase() === TAG_BR &&
								parentTagName === TAG_TD &&
								!element.nextSibling)
						) {
							allowNewLine = false;
						}
					}
				}
			}

			return allowNewLine;
		},

		_checkParentElement(element, tagName) {
			var parentNode = element.parentNode;

			return (
				parentNode &&
				parentNode.tagName &&
				parentNode.tagName.toLowerCase() === tagName
			);
		},

		_convert(data) {
			var instance = this;

			var node = document.createElement(TAG_DIV);

			node.innerHTML = data;

			instance._handle(node);

			var endResult = instance._endResult.join(STR_EMPTY);

			instance._endResult = null;

			return endResult;
		},

		_convertRGBToHex(color) {
			color = color.replace(
				REGEX_COLOR_RGB,
				(_match, red, green, blue) => {
					var b = toHex(blue);
					var g = toHex(green);
					var r = toHex(red);

					color = '#' + r + g + b;

					return color;
				}
			);

			return color;
		},

		_endResult: null,

		_getBodySize() {
			var body = document.body;

			var style;

			if (document.defaultView.getComputedStyle) {
				style = document.defaultView.getComputedStyle(body, null);
			}
			else if (body.currentStyle) {
				style = body.currentStyle;
			}

			return parseFloat(style.fontSize, 10);
		},

		_getEmoticonSymbol(element) {
			var instance = this;

			var emoticonSymbol = null;

			var imagePath = element.getAttribute('src');

			if (imagePath) {
				var editorConfig = this._editor.config;

				var image = imagePath.substring(imagePath.lastIndexOf('/') + 1);

				var imageIndex = instance._getImageIndex(
					editorConfig.smiley_images,
					image
				);

				if (imageIndex >= 0) {
					emoticonSymbol = editorConfig.smiley_symbols[imageIndex];
				}
			}

			return emoticonSymbol;
		},

		_getFontSize(fontSize) {
			var instance = this;

			var bodySize;

			if (REGEX_PX.test(fontSize)) {
				fontSize = instance._getFontSizePX(fontSize);
			}
			else if (REGEX_EM.test(fontSize)) {
				bodySize = instance._getBodySize();

				fontSize = parseFloat(fontSize, 10);

				fontSize = Math.round(fontSize * bodySize) + 'px';

				fontSize = instance._getFontSize(fontSize);
			}
			else if (REGEX_PERCENT.test(fontSize)) {
				bodySize = instance._getBodySize();

				fontSize = parseFloat(fontSize, 10);
				fontSize = Math.round((fontSize * bodySize) / 100) + 'px';

				fontSize = instance._getFontSize(fontSize);
			}

			return fontSize;
		},

		_getFontSizePX(fontSize) {
			var sizeValue = parseInt(fontSize, 10);

			if (sizeValue <= 10) {
				sizeValue = '1';
			}
			else if (sizeValue <= 12) {
				sizeValue = '2';
			}
			else if (sizeValue <= 14) {
				sizeValue = '3';
			}
			else if (sizeValue <= 16) {
				sizeValue = '4';
			}
			else if (sizeValue <= 18) {
				sizeValue = '5';
			}
			else if (sizeValue <= 24) {
				sizeValue = '6';
			}
			else if (sizeValue <= 32) {
				sizeValue = '7';
			}
			else {
				sizeValue = '8';
			}

			return sizeValue;
		},

		_getImageIndex(array, image) {
			var index = -1;

			if (array.lastIndexOf) {
				index = array.lastIndexOf(image);
			}
			else {
				for (var i = array.length - 1; i >= 0; i--) {
					var item = array[i];

					if (image === item) {
						index = i;

						break;
					}
				}
			}

			return index;
		},

		_handle(node) {
			var instance = this;

			if (!instance._endResult) {
				instance._endResult = [];
			}

			var children = node.childNodes;

			var pushTagList = instance._pushTagList;

			var length = children.length;

			for (var i = 0; i < length; i++) {
				var listTagsIn = [];
				var listTagsOut = [];

				var stylesTagsIn = [];
				var stylesTagsOut = [];

				var child = children[i];

				instance._handleElementStart(child, listTagsIn, listTagsOut);
				instance._handleStyles(child, stylesTagsIn, stylesTagsOut);

				pushTagList.call(instance, listTagsIn);
				pushTagList.call(instance, stylesTagsIn);

				instance._handle(child);

				instance._handleElementEnd(child, listTagsIn, listTagsOut);

				pushTagList.call(instance, stylesTagsOut.reverse());
				pushTagList.call(instance, listTagsOut);
			}

			instance._handleData(node.data, node);
		},

		_handleBreak(element, listTagsIn) {
			var instance = this;

			if (instance._inPRE) {
				listTagsIn.push(NEW_LINE);
			}
			else if (instance._allowNewLine(element)) {
				listTagsIn.push(NEW_LINE);
			}
		},

		_handleCite(element, _listTagsIn, listTagsOut) {
			var instance = this;

			var parentNode = element.parentNode;

			if (
				parentNode &&
				parentNode.tagName &&
				parentNode.tagName.toLowerCase() === TAG_BLOCKQUOTE &&
				!parentNode.getAttribute(TAG_CITE)
			) {
				var endResult = instance._endResult;

				for (var i = endResult.length - 1; i >= 0; i--) {
					if (endResult[i] === '[quote]') {
						endResult[i] = '[quote=';

						listTagsOut.push(']');

						break;
					}
				}
			}
		},

		_handleData(data, element) {
			var instance = this;

			if (data) {
				if (!instance._allowNewLine(element)) {
					data = data.replace(REGEX_NEWLINE, STR_EMPTY);
				}
				else if (
					instance._checkParentElement(element, TAG_LINK) &&
					data.indexOf(STR_MAILTO) === 0
				) {
					data = data.substring(STR_MAILTO.length);
				}
				else if (instance._checkParentElement(element, TAG_CITE)) {
					data = Liferay.BBCodeUtil.escape(data);
				}

				instance._endResult.push(data);
			}
		},

		_handleElementEnd(element) {
			var instance = this;

			var tagName = element.tagName;

			if (tagName) {
				tagName = tagName.toLowerCase();

				if (tagName === TAG_LI) {
					if (!instance._isLastItemNewLine()) {
						instance._endResult.push(NEW_LINE);
					}
				}
				else if (tagName === TAG_PRE || tagName === TAG_CODE) {
					instance._inPRE = false;
				}
			}
		},

		_handleElementStart(element, listTagsIn, listTagsOut) {
			var instance = this;

			var tagName = element.tagName;

			if (tagName) {
				tagName = tagName.toLowerCase();

				var handlerName = MAP_HANDLERS[tagName];

				if (handlerName) {
					instance[handlerName](element, listTagsIn, listTagsOut);
				}
			}
		},

		_handleEm(_element, listTagsIn, listTagsOut) {
			listTagsIn.push('[i]');

			listTagsOut.push('[/i]');
		},

		_handleFont(element, listTagsIn) {
			var instance = this;

			var size = element.size;

			if (size) {
				size = parseInt(size, 10);

				if (size >= 1 && size <= 7) {
					listTagsIn.push('[size=', size, ']');

					listTagsIn.push('[/size]');
				}
			}

			var color = element.color;

			if (color) {
				color = instance._convertRGBToHex(color);

				listTagsIn.push('[color=', color, ']');

				listTagsIn.push('[/color]');
			}
		},

		_handleImage(element, listTagsIn, listTagsOut) {
			var instance = this;

			var emoticonSymbol = instance._getEmoticonSymbol(element);

			if (emoticonSymbol) {
				instance._endResult.push(emoticonSymbol);
			}
			else {
				var attrSrc = element.getAttribute('src');

				var openTag =
					'[img' + instance._handleImageAttributes(element) + ']';

				listTagsIn.push(openTag);
				listTagsIn.push(attrSrc);

				listTagsOut.push('[/img]');
			}
		},

		_handleImageAttributes(element) {
			var attrs = '';

			var length = MAP_IMAGE_ATTRIBUTES.length;

			for (var i = 0; i < length; i++) {
				var attrName = MAP_IMAGE_ATTRIBUTES[i];

				var attrValue = element.getAttribute(attrName);

				if (attrValue) {
					attrs += ' ' + attrName + '="' + attrValue + '"';
				}
			}

			return attrs;
		},

		_handleLineThrough(_element, listTagsIn, listTagsOut) {
			listTagsIn.push('[s]');

			listTagsOut.push('[/s]');
		},

		_handleLink(element, listTagsIn, listTagsOut) {
			var hrefAttribute = element.getAttribute('href');

			if (hrefAttribute) {
				var editorConfig = this._editor.config;

				if (hrefAttribute.indexOf(editorConfig.newThreadURL) >= 0) {
					hrefAttribute = editorConfig.newThreadURL;
				}

				var linkHandler =
					MAP_LINK_HANDLERS[hrefAttribute.indexOf(STR_MAILTO)] ||
					'url';

				listTagsIn.push('[' + linkHandler + '=', hrefAttribute, ']');

				listTagsOut.push('[/' + linkHandler + ']');
			}
		},

		_handleListItem(_element, listTagsIn) {
			var instance = this;

			if (!instance._isLastItemNewLine()) {
				listTagsIn.push(NEW_LINE);
			}

			listTagsIn.push('[*]');
		},

		_handleOrderedList(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[list');

			var listStyleType = element.style.listStyleType;

			if (REGEX_LIST_LOWER_ALPHA.test(listStyleType)) {
				listTagsIn.push(' type="a"');
			}
			else if (REGEX_LIST_LOWER_ROMAN.test(listStyleType)) {
				listTagsIn.push(' type="i"');
			}
			else if (REGEX_LIST_UPPER_ALPHA.test(listStyleType)) {
				listTagsIn.push(' type="A"');
			}
			else if (REGEX_LIST_UPPER_ROMAN.test(listStyleType)) {
				listTagsIn.push(' type="I"');
			}
			else {
				listTagsIn.push(' type="1"');
			}

			var start = element.start;

			if (start >= 0) {
				listTagsIn.push(' start="' + start + '"');
			}

			listTagsIn.push(']');

			listTagsOut.push('[/list]');
		},

		_handlePre(_element, listTagsIn, listTagsOut) {
			var instance = this;

			instance._inPRE = true;

			listTagsIn.push('[code]');

			listTagsOut.push('[/code]');
		},

		_handleQuote(element, listTagsIn, listTagsOut) {
			var cite = element.getAttribute(TAG_CITE);

			var openTag = '[quote]';

			if (cite) {
				openTag = '[quote=' + cite + ']';
			}

			listTagsIn.push(openTag);

			listTagsOut.push('[/quote]');
		},

		_handleStrong(_element, listTagsIn, listTagsOut) {
			listTagsIn.push('[b]');

			listTagsOut.push('[/b]');
		},

		_handleStyleAlignCenter(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var alignment = style.textAlign.toLowerCase();

			if (alignment === 'center') {
				stylesTagsIn.push('[center]');

				stylesTagsOut.push('[/center]');
			}
		},

		_handleStyleAlignJustify(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var alignment = style.textAlign.toLowerCase();

			if (alignment === 'justify') {
				stylesTagsIn.push('[justify]');

				stylesTagsOut.push('[/justify]');
			}
		},

		_handleStyleAlignLeft(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var alignment = style.textAlign.toLowerCase();

			if (alignment === 'left') {
				stylesTagsIn.push('[left]');

				stylesTagsOut.push('[/left]');
			}
		},

		_handleStyleAlignRight(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var alignment = style.textAlign.toLowerCase();

			if (alignment === 'right') {
				stylesTagsIn.push('[right]');

				stylesTagsOut.push('[/right]');
			}
		},

		_handleStyleBold(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var fontWeight = style.fontWeight;

			if (fontWeight.toLowerCase() === 'bold') {
				stylesTagsIn.push('[b]');

				stylesTagsOut.push('[/b]');
			}
		},

		_handleStyleColor(element, stylesTagsIn, stylesTagsOut) {
			var instance = this;

			var style = element.style;

			var color = style.color;

			if (color) {
				color = instance._convertRGBToHex(color);

				stylesTagsIn.push('[color=', color, ']');

				stylesTagsOut.push('[/color]');
			}
		},

		_handleStyleFontFamily(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var fontFamily = style.fontFamily;

			if (fontFamily) {
				stylesTagsIn.push(
					'[font=',
					fontFamily.replace(REGEX_SINGLE_QUOTE, STR_EMPTY),
					']'
				);

				stylesTagsOut.push('[/font]');
			}
		},

		_handleStyleFontSize(element, stylesTagsIn, stylesTagsOut) {
			var instance = this;

			var style = element.style;

			var fontSize = style.fontSize;

			if (fontSize) {
				fontSize = instance._getFontSize(fontSize);

				stylesTagsIn.push('[size=', fontSize, ']');

				stylesTagsOut.push('[/size]');
			}
		},

		_handleStyleItalic(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var fontStyle = style.fontStyle;

			if (fontStyle.toLowerCase() === 'italic') {
				stylesTagsIn.push('[i]');

				stylesTagsOut.push('[/i]');
			}
		},

		_handleStyleTextDecoration(element, stylesTagsIn, stylesTagsOut) {
			var style = element.style;

			var textDecoration = style.textDecoration.toLowerCase();

			if (textDecoration === 'line-through') {
				stylesTagsIn.push('[s]');

				stylesTagsOut.push('[/s]');
			}
			else if (textDecoration === 'underline') {
				stylesTagsIn.push('[u]');

				stylesTagsOut.push('[/u]');
			}
		},

		_handleStyles(element, stylesTagsIn, stylesTagsOut) {
			var instance = this;

			var tagName = element.tagName;

			if (
				(!tagName || tagName.toLowerCase() !== TAG_LINK) &&
				element.style
			) {
				instance._handleStyleAlignCenter(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
				instance._handleStyleAlignJustify(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
				instance._handleStyleAlignLeft(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
				instance._handleStyleAlignRight(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
				instance._handleStyleBold(element, stylesTagsIn, stylesTagsOut);
				instance._handleStyleColor(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
				instance._handleStyleFontFamily(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
				instance._handleStyleFontSize(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
				instance._handleStyleItalic(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
				instance._handleStyleTextDecoration(
					element,
					stylesTagsIn,
					stylesTagsOut
				);
			}
		},

		_handleTable(_element, listTagsIn, listTagsOut) {
			listTagsIn.push('[table]', NEW_LINE);

			listTagsOut.push('[/table]');
		},

		_handleTableCaption(element, listTagsIn, listTagsOut) {
			var instance = this;

			if (instance._checkParentElement(element, TAG_TABLE)) {
				listTagsIn.push('[tr]', NEW_LINE, '[th]');

				listTagsOut.push('[/th]', NEW_LINE, '[/tr]', NEW_LINE);
			}
		},

		_handleTableCell(_element, listTagsIn, listTagsOut) {
			listTagsIn.push('[td]');

			listTagsOut.push('[/td]', NEW_LINE);
		},

		_handleTableHeader(_element, listTagsIn, listTagsOut) {
			listTagsIn.push('[th]');

			listTagsOut.push('[/th]', NEW_LINE);
		},

		_handleTableRow(_element, listTagsIn, listTagsOut) {
			listTagsIn.push('[tr]', NEW_LINE);

			listTagsOut.push('[/tr]', NEW_LINE);
		},

		_handleUnderline(_element, listTagsIn, listTagsOut) {
			listTagsIn.push('[u]');

			listTagsOut.push('[/u]');
		},

		_handleUnorderedList(element, listTagsIn, listTagsOut) {
			listTagsIn.push('[list');

			var listStyleType = element.style.listStyleType;

			if (REGEX_LIST_CIRCLE.test(listStyleType)) {
				listTagsIn.push(' type="circle"]');
			}
			else if (REGEX_LIST_SQUARE.test(listStyleType)) {
				listTagsIn.push(' type="square"]');
			}
			else {
				listTagsIn.push(' type="disc"]');
			}

			listTagsOut.push('[/list]');
		},

		_inPRE: false,

		_isLastItemNewLine() {
			var instance = this;

			var endResult = instance._endResult;

			return (
				endResult &&
				REGEX_LASTCHAR_NEWLINE_WHITESPACE.test(endResult.slice(-1))
			);
		},

		_pushTagList(tagsList) {
			var instance = this;

			var endResult = instance._endResult;

			var length = tagsList.length;

			for (var i = 0; i < length; i++) {
				var tag = tagsList[i];

				endResult.push(tag);
			}
		},

		constructor: BBCodeDataProcessor,

		toDataFormat(html) {
			var instance = this;

			html = html.replace(REGEX_PRE, '$&\n');

			var data = instance._convert(html);

			return data;
		},

		toHtml(data, config) {
			var instance = this;

			if (!instance._bbcodeConverter) {
				var editorConfig = this._editor.config;

				var converterConfig = {
					emoticonImages: editorConfig.smiley_images,
					emoticonPath: editorConfig.smiley_path,
					emoticonSymbols: editorConfig.smiley_symbols
				};

				instance._bbcodeConverter = new CKEDITOR.BBCode2HTML(
					converterConfig
				);
			}

			if (config) {
				var fragment = CKEDITOR.htmlParser.fragment.fromHtml(data);

				var writer = new CKEDITOR.htmlParser.basicWriter();

				config.filter.applyTo(fragment);

				fragment.writeHtml(writer);

				data = writer.getHtml();
			}
			else {
				data = instance._bbcodeConverter.convert(data);
			}

			return data;
		}
	};

	CKEDITOR.plugins.add('bbcode_data_processor', {
		init(editor) {
			editor.dataProcessor = new BBCodeDataProcessor(editor);

			editor.fire('customDataProcessorLoaded');
		},

		requires: ['htmlwriter']
	});
})();
