/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.parsers.bbcode;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.messageboards.util.BBCodeTranslator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * @author Iliyan Peychev
 */
public class BBCodeToHtmlTranslator implements BBCodeTranslator {

	static final String[][] _emoticons = {
		{"happy.gif", ":)", "happy"},
		{"smile.gif", ":D", "smile"},
		{"cool.gif", "B)", "cool"},
		{"sad.gif", ":(", "sad"},
		{"tongue.gif", ":P", "tongue"},
		{"laugh.gif", ":lol:", "laugh"},
		{"kiss.gif", ":#", "kiss"},
		{"blush.gif", ":*)", "blush"},
		{"bashful.gif", ":bashful:", "bashful"},
		{"smug.gif", ":smug:", "smug"},
		{"blink.gif", ":blink:", "blink"},
		{"huh.gif", ":huh:", "huh"},
		{"mellow.gif", ":mellow:", "mellow"},
		{"unsure.gif", ":unsure:", "unsure"},
		{"mad.gif", ":mad:", "mad"},
		{"oh_my.gif", ":O", "oh-my-goodness"},
		{"roll_eyes.gif", ":rolleyes:", "roll-eyes"},
		{"angry.gif", ":angry:", "angry"},
		{"suspicious.gif", "8o", "suspicious"},
		{"big_grin.gif", ":grin:", "grin"},
		{"in_love.gif", ":love:", "in-love"},
		{"bored.gif", ":bored:", "bored"},
		{"closed_eyes.gif", "-_-", "closed-eyes"},
		{"cold.gif", ":cold:", "cold"},
		{"sleep.gif", ":sleep:", "sleep"},
		{"glare.gif", ":glare:", "glare"},
		{"darth_vader.gif", ":vader:", "darth-vader"},
		{"dry.gif", ":dry:", "dry"},
		{"exclamation.gif", ":what:", "what"},
		{"girl.gif", ":girl:", "girl"},
		{"karate_kid.gif", ":kid:", "karate-kid"},
		{"ninja.gif", ":ph34r:", "ninja"},
		{"pac_man.gif", ":V", "pac-man"},
		{"wacko.gif", ":wacko:", "wacko"},
		{"wink.gif", ":wink:", "wink"},
		{"wub.gif", ":wub:", "wub"}
	};

	static final String[] _emoticonDescriptions =
		new String[_emoticons.length];

	static final String[] _emoticonFiles = new String[_emoticons.length];

	static final String[] _emoticonSymbols = new String[_emoticons.length];

	static Map<String, String> _mapFontSize;

	static Map<String, String> _mapListStyles;

	static Map<String, Integer> _mapTokensExcludeNewLine;

	static final int TOKEN_DATA = com.liferay.portal.parsers.bbcode.BBCodeParser.TOKEN_DATA;

	static final int TOKEN_TAG_END = com.liferay.portal.parsers.bbcode.BBCodeParser.TOKEN_TAG_END;

	static final int TOKEN_TAG_START = com.liferay.portal.parsers.bbcode.BBCodeParser.TOKEN_TAG_START;

	static final int TOKEN_TAG_START_END =
			TOKEN_TAG_START | TOKEN_TAG_END;

	static {
		_mapFontSize = new HashMap();

		_mapFontSize.put("1", "10");
		_mapFontSize.put("2", "12");
		_mapFontSize.put("3", "16");
		_mapFontSize.put("4", "18");
		_mapFontSize.put("5", "24");
		_mapFontSize.put("6", "32");
		_mapFontSize.put("7", "48");

		_mapListStyles = new HashMap();

		_mapListStyles.put("1", "list-style: decimal inside;");
		_mapListStyles.put("i", "list-style: lower-roman inside;");
		_mapListStyles.put("I", "list-style: upper-roman inside;");
		_mapListStyles.put("a", "list-style: lower-alpha inside;");
		_mapListStyles.put("A", "list-style: upper-alpha inside;");

		_mapTokensExcludeNewLine = new HashMap();

		_mapTokensExcludeNewLine.put("*", TOKEN_TAG_START_END);
		_mapTokensExcludeNewLine.put("li", TOKEN_TAG_START_END);
		_mapTokensExcludeNewLine.put("tr", TOKEN_TAG_START_END);
		_mapTokensExcludeNewLine.put("td", TOKEN_TAG_START_END);
		_mapTokensExcludeNewLine.put("th", TOKEN_TAG_START_END);
		_mapTokensExcludeNewLine.put("table", TOKEN_TAG_END);

		for (int i = 0; i < _emoticons.length; i++) {

			String[] emoticon = _emoticons[i];

			_emoticonDescriptions[i] = emoticon[2];
			_emoticonFiles[i] = emoticon[0];
			_emoticonSymbols[i] = emoticon[1];

			String image = emoticon[0];

			emoticon[0] =
					"<img alt=\"emoticon\" src=\"@theme_images_path@/" +
							"emoticons/" + image + "\" >";
		}
	}

	public BBCodeToHtmlTranslator() {
		_bbCodeParser = new com.liferay.portal.parsers.bbcode.BBCodeParser();

		_sb = new StringBundler();
	}

	public String[][] getEmoticons() {
		return _emoticons;
	}

	public String[] getEmoticonDescriptions() {
		return _emoticonDescriptions;
	}

	public String[] getEmoticonFiles() {
		return _emoticonFiles;
	}

	public String[] getEmoticonSymbols() {
		return _emoticonSymbols;
	}

	public String parse(String text) {
		_parsedData = _bbCodeParser.parse(text);

		for (_itemPointer = 0; _itemPointer < _parsedData.size();
			 _itemPointer++) {

			BBCodeItem item = _parsedData.get(_itemPointer);

			int type = item.getType();

			if (type == TOKEN_TAG_START) {
				_handleTagStart(item);
			}
			else if (type == TOKEN_TAG_END) {
				_handleTagEnd(item);
			}
			else if (type == TOKEN_DATA) {
				_handleData(item);
			}
		}

		String result = _sb.toString();

		_reset();

		return result;
	}

	private String _extractData(
			String toTagName, int tagFilter, boolean consume) {

		StringBundler sb = new StringBundler();

		int index = _itemPointer + 1;

		BBCodeItem item = null;

		do {
			item = _parsedData.get(index++);

			if ((item.getType() & tagFilter) > 0) {
				sb.append(item.getValue());
			}

		} while ((item.getType() != TOKEN_TAG_END) &&
			!(item.getValue().equals(toTagName)));

		if (consume) {
			_itemPointer = index - 1;
		}

		return sb.toString();
	}

	private String _getFontSize(String fontSize) {
		fontSize = _mapFontSize.get(fontSize);

		if (fontSize == null) {
			fontSize = STR_DEFAULT_FONT_SIZE;
		}

		return fontSize;
	}

	private void _handleCode() {
		String code = _extractData(STR_CODE, TOKEN_DATA, true);

		code = HtmlUtil.escape(code);

		code = code.replaceAll(StringPool.TAB, StringPool.FOUR_SPACES);

		String[] lines = _regexNewLine.split(code);

		int digits = String.valueOf(lines.length + 1).length();

		_sb.append(STR_TAG_CODE_OPEN);

		for (int i = 0; i < lines.length; i++) {
			String index = String.valueOf(i + 1);
			int ld = index.length();

		   _sb.append(STR_TAG_CODE_LINES);

			for (int j = 0; j < digits - ld; j++) {
				_sb.append(StringPool.NBSP);
			}

			lines[i] = StringUtil.replace(lines[i], STR_THREE_SPACES,
				StringPool.NBSP + StringPool.SPACE + StringPool.NBSP);

			lines[i] = StringUtil.replace(lines[i], StringPool.DOUBLE_SPACE,
				StringPool.NBSP + StringPool.SPACE);

			_sb.append(index + STR_TAG_SPAN_CLOSE);
			_sb.append(lines[i]);

			if (index.length() < lines.length) {
				_sb.append(STR_TAG_BR);
			}
		}

		_sb.append(STR_TAG_DIV_CLOSE);
	}

	private void _handleColor(BBCodeItem item) {
		String colorName = item.getAttribute();

		if (colorName == null || !_regexColor.matcher(colorName).matches()) {
			colorName = STR_DEFAULT_COLOR;
		}

		_sb.append(STR_TAG_SPAN_STYLE_OPEN);
		_sb.append(STR_STYLE_COLOR);
		_sb.append(colorName);
		_sb.append(STR_TAG_ATTR_CLOSE);

		_stack.push(STR_TAG_SPAN_CLOSE);
	}

	private void _handleData(BBCodeItem item) {
		String value = HtmlUtil.escape(item.getValue());

		value = _handleNewLine(value);

		 for (int i = 0; i < _emoticons.length; i++) {
			String[] emoticon = _emoticons[i];

			value = StringUtil.replace(value, emoticon[1], emoticon[0]);
		 }

		_sb.append(value);
	}

	private void _handleEmail(BBCodeItem item) {
		String emailHref = item.getAttribute();

		if (emailHref == null) {
			emailHref = _extractData(STR_EMAIL, TOKEN_DATA, false);
		}

		if (emailHref.indexOf(STR_MAILTO) != 0) {
			emailHref = STR_MAILTO + emailHref;
		}

		emailHref = HtmlUtil.escapeHREF(emailHref);

		_sb.append(STR_TAG_ATTR_HREF_OPEN);
		_sb.append(emailHref);
		_sb.append(STR_TAG_ATTR_CLOSE);

		_stack.push(STR_TAG_A_CLOSE);
	}

	private void _handleEm() {
		_handleSimpleTag(STR_EM);
	}

	private void _handleFont(BBCodeItem item) {
		String fontName = item.getAttribute();

		fontName = HtmlUtil.escapeAttribute(fontName);

		_sb.append(STR_TAG_SPAN_STYLE_OPEN);
		_sb.append(STR_STYLE_FONT_FAMILY);
		_sb.append(fontName);
		_sb.append(STR_TAG_ATTR_CLOSE);

		_stack.push(STR_TAG_SPAN_CLOSE);
	}

	private void _handleImage() {
		String imageSrc = StringPool.BLANK;

		String imageSrcInput = _extractData(STR_IMG, TOKEN_DATA, true);

		if (_regexImageSrc.matcher(imageSrcInput).matches()) {
			imageSrc = HtmlUtil.escapeAttribute(imageSrcInput);
		}

		_sb.append(STR_TAG_IMG_ATTR_SRC);
		_sb.append(imageSrc);
		_sb.append(STR_TAG_ATTR_CLOSE);
	}

	private void _handleList(BBCodeItem item) {
		String tag = STR_DEFAULT_UL;

		String styleAttr = null;

		String listAttribute = item.getAttribute();

		if (listAttribute != null) {
			tag = STR_LIST_ORDERED;

			styleAttr = _mapListStyles.get(listAttribute);
		}

		String result = STR_TAG_OPEN + tag + STR_TAG_END_CLOSE;

		if (styleAttr != null) {
			result =
					STR_TAG_OPEN + tag + StringPool.SPACE + STR_STYLE +
						StringPool.EQUAL + StringPool.QUOTE + styleAttr +
							STR_TAG_ATTR_CLOSE;
		}

		_sb.append(result);

		_stack.push(STR_TAG_END_OPEN + tag + STR_TAG_END_CLOSE);
	}

	private void _handleListItem() {
		_handleSimpleTag(STR_LIST_ITEM);
	}

	private String _handleNewLine(String data) {
		BBCodeItem nextItem = null;

		if (_regexStringIsNewLine.matcher(data).matches()) {
			nextItem = _parsedData.get(_itemPointer + 1);

			if (nextItem != null) {
				String nextItemValue = nextItem.getValue();

				if (_mapTokensExcludeNewLine.containsKey(
						nextItemValue)) {

					int nextItemType = nextItem.getType();

					int mappedValue =
						_mapTokensExcludeNewLine.get(nextItemValue);

					if ((nextItemType & mappedValue) > 0) {
						data = StringPool.BLANK;
					}
				}
			}
		}
		else if (_regexLastCharNewLine.matcher(data).matches()) {
			nextItem = _parsedData.get(_itemPointer + 1);

			if (nextItem != null &&
				(nextItem.getType() == TOKEN_TAG_END) &&
				(nextItem.getValue().equals(STR_TAG_LIST_ITEM_SHORT))) {

				data = data.substring(0, data.length() - 1);
			}
		}

		if (!data.isEmpty()) {
			data = _regexNewLine.matcher(data).replaceAll(STR_TAG_BR);
		}

		return data;
	}

	private void _handleQuote(BBCodeItem item) {
		String cite = item.getAttribute();

		if (cite != null && !cite.isEmpty()) {
			_sb.append(STR_TAG_DIV_CLASS_QUOTE_TITLE);
			_sb.append(HtmlUtil.escape(cite));
			_sb.append(StringPool.COLON + STR_TAG_DIV_CLOSE);
		}

		_sb.append(STR_TAG_DIV_CLASS_QUOTE);
		_sb.append(STR_TAG_DIV_CLASS_QUOTE_CONTENT);

		_stack.push(STR_TAG_DIV_CLOSE + STR_TAG_DIV_CLOSE);
	}

	private void _handleSimpleTag(String tagName) {
		_sb.append(STR_TAG_OPEN);
		_sb.append(tagName);
		_sb.append(STR_TAG_END_CLOSE);

		_stack.push(STR_TAG_END_OPEN + tagName + STR_TAG_END_CLOSE);
	}

	private void _handleSimpleTags(BBCodeItem item) {
		_handleSimpleTag(item.getValue());
	}

	private void _handleSize(BBCodeItem item) {
		String size = item.getAttribute();

		if (size == null || size.isEmpty() ||
			!_regexNumber.matcher(size).matches()) {

			size = STR_ONE;
		}

		_sb.append(STR_TAG_SPAN_STYLE_OPEN);
		_sb.append(STR_STYLE_FONT_SIZE);
		_sb.append(_getFontSize(size));
		_sb.append(STR_PX);
		_sb.append(STR_TAG_ATTR_CLOSE);

		_stack.push(STR_TAG_SPAN_CLOSE);
	}

	private void _handleStrikeThrough() {
		_handleSimpleTag(STR_STRIKE);
	}

	private void _handleStrong() {
		_handleSimpleTag(STR_STRONG);
	}

	private void _handleTable() {
		_handleSimpleTag(STR_TABLE);
	}

	private void _handleTableCell() {
		_handleSimpleTag(STR_TD);
	}

	private void _handleTableHeader() {
		_handleSimpleTag(STR_TH);
	}

	private void _handleTableRow() {
		_handleSimpleTag(STR_TR);
	}

	private void _handleTagEnd(BBCodeItem item) {
		String tagName = item.getValue();

		if (_isValidTag(tagName)) {
			_sb.append(_stack.pop());
		}
	}

	private void _handleTagStart(BBCodeItem item) {
		String tagName = item.getValue();

		if (_isValidTag(tagName)) {
			if (tagName.equals(STR_BOLD)) {
				_handleStrong();
			}
			else if (tagName.equals(STR_CODE)) {
				_handleCode();
			}
			else if (tagName.equals(STR_COLOR) ||
				 tagName.equals(STR_COLOUR)) {

				_handleColor(item);
			}
			else if (tagName.equals(STR_EMAIL)) {
				_handleEmail(item);
			}
			else if (tagName.equals(STR_FONT)) {
				_handleFont(item);
			}
			else if (tagName.equals(STR_IMG)) {
				_handleImage();
			}
			else if (tagName.equals(STR_ITALIC)) {
				_handleEm();
			}
			else if (tagName.equals(STR_LIST)) {
				_handleList(item);
			}
			else if (tagName.equals(STR_TABLE)) {
				_handleTable();
			}
			else if (tagName.equals(STR_TAG_URL)) {
				_handleURL(item);
			}
			else if (tagName.equals(STR_TD)) {
				_handleTableCell();
			}
			else if (tagName.equals(STR_TH)) {
				_handleTableHeader();
			}
			else if (tagName.equals(STR_TR)) {
				_handleTableRow();
			}
			else if (tagName.equals(STR_SIZE)) {
				_handleSize(item);
			}
			else if (tagName.equals(STR_STRIKE_THROUGH)) {
				_handleStrikeThrough();
			}
			else if (tagName.equals(StringPool.STAR) ||
				tagName.equals(STR_LIST_ITEM)) {

				_handleListItem();
			}
			else if (tagName.equals(STR_Q) || tagName.equals(STR_QUOTE)) {
				_handleQuote(item);
			}
			else if (tagName.equals(STR_CENTER) ||
				tagName.equals(STR_JUSTIFY) || tagName.equals(STR_LEFT) ||
				tagName.equals(STR_RIGHT)) {

				_handleTextAlign(item);
			}
			else {
				_handleSimpleTags(item);
			}
		}
	}

	private void _handleTextAlign(BBCodeItem item) {
		_sb.append(STR_TEXT_ALIGN);
		_sb.append(item.getValue());
		_sb.append(STR_TAG_ATTR_CLOSE);

		_stack.push(STR_TAG_P_CLOSE);
	}

	private void _handleURL(BBCodeItem item) {
		String href = StringPool.BLANK;

		String hrefInput = item.getAttribute();

		if (hrefInput == null) {
			hrefInput = _extractData(STR_TAG_URL, TOKEN_DATA, false);
		}

		if (_regexURI.matcher(hrefInput).matches()) {
			href = HtmlUtil.escapeHREF(hrefInput);
		}

		_sb.append(STR_TAG_ATTR_HREF_OPEN);
		_sb.append(href);
		_sb.append(STR_TAG_ATTR_CLOSE);

		_stack.push(STR_TAG_A_CLOSE);
	}

	private boolean _isValidTag(String tagName) {
		boolean valid = false;

		if (tagName != null && !tagName.isEmpty()) {
			valid = _regexTagName.matcher(tagName).matches();
		}

		return valid;
	}

	private void _reset() {
		_sb.setIndex(0);
		_stack.empty();

		_parsedData.clear();
	}

	private com.liferay.portal.parsers.bbcode.BBCodeParser _bbCodeParser = null;
	private List<BBCodeItem> _parsedData = null;

	private static final Pattern _regexColor =
		Pattern.compile("^(:?aqua|black|blue|fuchsia|gray|green|lime|maroon|navy|olive|purple|red|silver|teal|white|yellow|#(?:[0-9a-f]{3})?[0-9a-f]{3})$", Pattern.CASE_INSENSITIVE);

	private static final Pattern _regexImageSrc =
		Pattern.compile("^(?:https?://|/)[-;/?:@&=+$,_.!~*'()%0-9a-z]{1,512}$", Pattern.CASE_INSENSITIVE);

	private static final Pattern _regexLastCharNewLine =
		Pattern.compile(".*\r?\n\\z", Pattern.DOTALL);

	private static final Pattern _regexNewLine = Pattern.compile("\r?\n");

	private static final Pattern _regexNumber =
		Pattern.compile("^[\\\\.0-9]{1,8}$");

	private static final Pattern _regexStringIsNewLine =
		Pattern.compile("\\A\r?\n\\z");

	private static final Pattern _regexTagName =
		Pattern.compile("^/?(?:b|center|code|colou?r|email|i|img|justify|left|pre|q|quote|right|\\*|s|size|table|tr|th|td|li|list|font|u|url)$", Pattern.CASE_INSENSITIVE);

	private static final Pattern _regexURI =
		Pattern.compile("^[-;/?:@&=+$,_.!~*'()%0-9a-z#]{1,512}$", Pattern.CASE_INSENSITIVE);

	private static final String STR_BOLD = "b";
	private static final String STR_CENTER = "center";
	private static final String STR_CODE = "code";
	private static final String STR_COLOR = "color";
	private static final String STR_COLOUR = "colour";
	private static final String STR_DEFAULT_COLOR = "inherit";
	private static final String STR_DEFAULT_FONT_SIZE = "12";
	private static final String STR_DEFAULT_UL =
			"ul style=\"list-style: disc inside;\"";
	private static final String STR_EM = "em";
	private static final String STR_EMAIL = "email";
	private static final String STR_FONT = "font";
	private static final String STR_IMG = "img";
	private static final String STR_ITALIC = "i";
	private static final String STR_JUSTIFY = "justify";
	private static final String STR_LEFT = "left";
	private static final String STR_LIST = "list";
	private static final String STR_LIST_ITEM = "li";
	private static final String STR_LIST_ORDERED = "ol";
	private static final String STR_MAILTO = "mailto:";
	private static final String STR_ONE = "1";
	private static final String STR_PX = "px";
	private static final String STR_Q = "q";
	private static final String STR_QUOTE = "quote";
	private static final String STR_RIGHT = "right";
	private static final String STR_SIZE = "size";
	private static final String STR_STRIKE = "strike";
	private static final String STR_STRIKE_THROUGH = "s";
	private static final String STR_STRONG = "strong";
	private static final String STR_STYLE = "style";
	private static final String STR_STYLE_COLOR = "color: ";
	private static final String STR_STYLE_FONT_FAMILY = "font-family: ";
	private static final String STR_STYLE_FONT_SIZE = "font-size: ";
	private static final String STR_TABLE = "table";
	private static final String STR_TAG_A_CLOSE = "</a>";
	private static final String STR_TAG_ATTR_CLOSE = "\">";
	private static final String STR_TAG_ATTR_HREF_OPEN = "<a href=\"";
	private static final String STR_TAG_BR = "<br>";
	private static final String STR_TAG_CODE_LINES =
			"<span class=\"code-lines\">";
	private static final String STR_TAG_CODE_OPEN = "<div class=\"code\">";
	private static final String STR_TAG_DIV_CLASS_QUOTE =
			"<div class=\"quote\">";
	private static final String STR_TAG_DIV_CLASS_QUOTE_CONTENT =
			"<div class=\"quote-content\">";
	private static final String STR_TAG_DIV_CLASS_QUOTE_TITLE =
			"<div class=\"quote-title\">";
	private static final String STR_TAG_DIV_CLOSE = "</div>";
	private static final String STR_TAG_END_CLOSE = ">";
	private static final String STR_TAG_END_OPEN = "</";
	private static final String STR_TAG_IMG_ATTR_SRC = "<img src=\"";
	private static final String STR_TAG_LIST_ITEM_SHORT = "*";
	private static final String STR_TAG_OPEN = "<";
	private static final String STR_TAG_P_CLOSE = "</p>";
	private static final String STR_TAG_SPAN_CLOSE = "</span>";
	private static final String STR_TAG_SPAN_STYLE_OPEN = "<span style=\"";
	private static final String STR_TAG_URL = "url";
	private static final String STR_TD = "td";
	private static final String STR_TEXT_ALIGN = "<p style=\"text-align: ";
	private static final String STR_TH = "th";
	private static final String STR_THREE_SPACES = "   ";
	private static final String STR_TR = "tr";

	private int _itemPointer = 0;
	private StringBundler _sb = null;
	private Stack<String> _stack = new Stack();

}