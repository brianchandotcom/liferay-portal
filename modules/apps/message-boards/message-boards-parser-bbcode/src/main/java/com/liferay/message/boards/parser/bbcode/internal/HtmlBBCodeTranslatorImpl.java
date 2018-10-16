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

package com.liferay.message.boards.parser.bbcode.internal;

import com.liferay.message.boards.util.MBUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ThemeConstants;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iliyan Peychev
 */
@Component(service = BBCodeTranslator.class)
public class HtmlBBCodeTranslatorImpl implements BBCodeTranslator {

	public HtmlBBCodeTranslatorImpl() {
		_bbCodeCharacters = new HashMap<>();

		_bbCodeCharacters.put("&", "&amp;");
		_bbCodeCharacters.put("'", "&#039;");
		_bbCodeCharacters.put("(", "&#40;");
		_bbCodeCharacters.put(")", "&#41;");
		_bbCodeCharacters.put("/", "&#047;");
		_bbCodeCharacters.put("<", "&lt;");
		_bbCodeCharacters.put(">", "&gt;");
		_bbCodeCharacters.put("[", "&#91;");
		_bbCodeCharacters.put("\"", "&#034;");
		_bbCodeCharacters.put("]", "&#93;");
		_bbCodeCharacters.put("`", "&#096;");

		for (int i = 0; i < _EMOTICONS.length; i++) {
			String[] emoticon = _EMOTICONS[i];

			_emoticonDescriptions[i] = emoticon[2];
			_emoticonFiles[i] = emoticon[0];
			_emoticonSymbols[i] = emoticon[1];

			String image = emoticon[0];

			StringBuilder sb = new StringBuilder(6);

			sb.append("<img alt=\"emoticon\" src=\"");
			sb.append(ThemeConstants.TOKEN_THEME_IMAGES_PATH);
			sb.append(MBUtil.EMOTICONS);
			sb.append("/");
			sb.append(image);
			sb.append("\" >");

			emoticon[0] = sb.toString();
		}

		_excludeNewLineTypes = new HashMap<>();

		_excludeNewLineTypes.put("*", BBCodeParser.TYPE_TAG_START_END);
		_excludeNewLineTypes.put("li", BBCodeParser.TYPE_TAG_START_END);
		_excludeNewLineTypes.put("table", BBCodeParser.TYPE_TAG_END);
		_excludeNewLineTypes.put("td", BBCodeParser.TYPE_TAG_START_END);
		_excludeNewLineTypes.put("th", BBCodeParser.TYPE_TAG_START_END);
		_excludeNewLineTypes.put("tr", BBCodeParser.TYPE_TAG_START_END);

		_imageAttributes = new HashSet<>(
			Arrays.asList(
				"alt", "class", "dir", "height", "id", "lang", "longdesc",
				"style", "title", "width"));

		_orderedListStyles = new HashMap<>();

		_orderedListStyles.put("1", "list-style: decimal outside;");
		_orderedListStyles.put("a", "list-style: lower-alpha outside;");
		_orderedListStyles.put("A", "list-style: upper-alpha outside;");
		_orderedListStyles.put("i", "list-style: lower-roman outside;");
		_orderedListStyles.put("I", "list-style: upper-roman outside;");

		_unorderedListStyles = new HashMap<>();

		_unorderedListStyles.put("circle", "list-style: circle outside;");
		_unorderedListStyles.put("disc", "list-style: disc outside;");
		_unorderedListStyles.put("square", "list-style: square outside;");
	}

	@Override
	public String[] getEmoticonDescriptions() {
		return _emoticonDescriptions;
	}

	@Override
	public String[] getEmoticonFiles() {
		return _emoticonFiles;
	}

	@Override
	public String[][] getEmoticons() {
		return _EMOTICONS;
	}

	@Override
	public String[] getEmoticonSymbols() {
		return _emoticonSymbols;
	}

	@Override
	public String getHTML(String bbcode) {
		try {
			bbcode = parse(bbcode);
		}
		catch (Exception e) {
			_log.error("Unable to parse: " + bbcode, e);

			bbcode = HtmlUtil.escape(bbcode);
		}

		return bbcode;
	}

	@Override
	public String parse(String text) {
		StringBundler sb = new StringBundler();

		List<BBCodeItem> bbCodeItems = _bbCodeParser.parse(text);
		Stack<String> tags = new Stack<>();
		IntegerWrapper marker = new IntegerWrapper();

		for (; marker.getValue() < bbCodeItems.size(); marker.increment()) {
			BBCodeItem bbCodeItem = bbCodeItems.get(marker.getValue());

			int type = bbCodeItem.getType();

			if (type == BBCodeParser.TYPE_DATA) {
				handleData(sb, bbCodeItems, tags, marker, bbCodeItem);
			}
			else if (type == BBCodeParser.TYPE_TAG_END) {
				handleTagEnd(sb, tags);
			}
			else if (type == BBCodeParser.TYPE_TAG_START) {
				handleTagStart(sb, bbCodeItems, tags, marker, bbCodeItem);
			}
		}

		return sb.toString();
	}

	protected String escapeQuote(String quote) {
		StringBuilder sb = new StringBuilder();

		int index = 0;

		Matcher matcher = _bbCodePattern.matcher(quote);

		Collection<String> values = _bbCodeCharacters.values();

		while (matcher.find()) {
			String match = matcher.group();

			int matchStartIndex = matcher.start();

			int nextSemicolonIndex = quote.indexOf(
				StringPool.SEMICOLON, matchStartIndex);

			sb.append(quote.substring(index, matchStartIndex));

			boolean entityFound = false;

			if (nextSemicolonIndex >= 0) {
				String value = quote.substring(
					matchStartIndex, nextSemicolonIndex + 1);

				if (values.contains(value)) {
					sb.append(value);

					index = matchStartIndex + value.length();

					entityFound = true;
				}
			}

			if (!entityFound) {
				String escapedValue = _bbCodeCharacters.get(match);

				sb.append(escapedValue);

				index = matchStartIndex + match.length();
			}
		}

		if (index < quote.length()) {
			sb.append(quote.substring(index));
		}

		return sb.toString();
	}

	protected String extractData(
		List<BBCodeItem> bbCodeItems, IntegerWrapper marker, String tag,
		int type, boolean consume) {

		StringBundler sb = new StringBundler();

		int index = marker.getValue() + 1;

		BBCodeItem bbCodeItem = null;

		do {
			bbCodeItem = bbCodeItems.get(index++);

			if ((bbCodeItem.getType() & type) > 0) {
				sb.append(bbCodeItem.getValue());
			}
		}
		while ((bbCodeItem.getType() != BBCodeParser.TYPE_TAG_END) &&
			   !tag.equals(bbCodeItem.getValue()));

		if (consume) {
			marker.setValue(index - 1);
		}

		return sb.toString();
	}

	protected void handleBold(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "strong");
	}

	protected void handleCode(
		StringBundler sb, List<BBCodeItem> bbCodeItems, IntegerWrapper marker) {

		sb.append("<div class=\"lfr-code\">");
		sb.append("<table>");
		sb.append("<tbody>");

		String code = extractData(
			bbCodeItems, marker, "code", BBCodeParser.TYPE_DATA, true);

		code = HtmlUtil.escape(code);
		code = StringUtil.replace(code, CharPool.TAB, StringPool.FOUR_SPACES);

		String[] lines = code.split("\r?\n");

		for (int i = 0; i < lines.length; i++) {
			sb.append("<tr>");
			sb.append("<td class=\"line-numbers\" data-line-number=\"");

			String index = String.valueOf(i + 1);

			sb.append(index);

			sb.append("\"></td>");
			sb.append("<td class=\"lines\">");

			String line = lines[i];

			line = StringUtil.replace(
				line, StringPool.THREE_SPACES, "&nbsp; &nbsp;");
			line = StringUtil.replace(line, StringPool.DOUBLE_SPACE, "&nbsp; ");

			if (Validator.isNull(line)) {
				line = "<br />";
			}

			sb.append("<div class=\"line\">");
			sb.append(line);
			sb.append("</div>");
			sb.append("</td>");
			sb.append("</tr>");
		}

		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</div>");
	}

	protected void handleColor(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		sb.append("<span style=\"color: ");

		String color = bbCodeItem.getAttribute();

		if (color == null) {
			color = "inherit";
		}
		else {
			Matcher matcher = _colorPattern.matcher(color);

			if (!matcher.matches()) {
				color = "inherit";
			}
		}

		sb.append(color);

		sb.append("\">");

		tags.push("</span>");
	}

	protected void handleData(
		StringBundler sb, List<BBCodeItem> bbCodeItems, Stack<String> tags,
		IntegerWrapper marker, BBCodeItem bbCodeItem) {

		String value = HtmlUtil.escape(bbCodeItem.getValue());

		value = handleNewLine(bbCodeItems, tags, marker, value);

		for (String[] emoticon : _EMOTICONS) {
			value = StringUtil.replace(value, emoticon[1], emoticon[0]);
		}

		sb.append(value);
	}

	protected void handleEmail(
		StringBundler sb, List<BBCodeItem> bbCodeItems, Stack<String> tags,
		IntegerWrapper marker, BBCodeItem bbCodeItem) {

		sb.append("<a href=\"");

		String href = bbCodeItem.getAttribute();

		if (href == null) {
			href = extractData(
				bbCodeItems, marker, "email", BBCodeParser.TYPE_DATA, false);
		}

		if (!href.startsWith("mailto:")) {
			href = "mailto:" + href;
		}

		sb.append(HtmlUtil.escapeHREF(href));

		sb.append("\">");

		tags.push("</a>");
	}

	protected void handleFontFamily(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		sb.append("<span style=\"font-family: ");
		sb.append(HtmlUtil.escapeAttribute(bbCodeItem.getAttribute()));
		sb.append("\">");

		tags.push("</span>");
	}

	protected void handleFontSize(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		sb.append("<span style=\"font-size: ");

		int size = GetterUtil.getInteger(bbCodeItem.getAttribute());

		if ((size >= 1) && (size <= _FONT_SIZES.length)) {
			sb.append(_FONT_SIZES[size - 1]);
		}
		else {
			sb.append(_FONT_SIZES[1]);
		}

		sb.append("px;\">");

		tags.push("</span>");
	}

	protected void handleImage(
		StringBundler sb, Stack<String> tags, List<BBCodeItem> bbCodeItems,
		IntegerWrapper marker) {

		sb.append("<img src=\"");

		int pos = marker.getValue();

		String src = extractData(
			bbCodeItems, marker, "img", BBCodeParser.TYPE_DATA, true);

		Matcher matcher = _imagePattern.matcher(src);

		if (matcher.matches()) {
			sb.append(HtmlUtil.escapeAttribute(src));
		}

		sb.append("\"");

		BBCodeItem bbCodeItem = bbCodeItems.get(pos);

		String attributes = bbCodeItem.getAttribute();

		if (Validator.isNotNull(attributes)) {
			sb.append(StringPool.SPACE);

			handleImageAttributes(sb, attributes);
		}

		sb.append(" />");

		tags.push(StringPool.BLANK);
	}

	protected void handleImageAttributes(StringBundler sb, String attributes) {
		Matcher matcher = _attributesPattern.matcher(attributes);

		while (matcher.find()) {
			String attributeName = matcher.group(1);

			if (Validator.isNotNull(attributeName) &&
				_imageAttributes.contains(
					StringUtil.toLowerCase(attributeName))) {

				String attributeValue = matcher.group(2);

				sb.append(StringPool.SPACE);
				sb.append(attributeName);
				sb.append(StringPool.EQUAL);
				sb.append(StringPool.QUOTE);
				sb.append(HtmlUtil.escapeAttribute(attributeValue));
				sb.append(StringPool.QUOTE);
			}
		}
	}

	protected void handleItalic(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "em");
	}

	protected void handleList(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		String tag = "ul";
		StringBundler attributesSB = new StringBundler();

		if (Validator.isNotNull(bbCodeItem.getAttribute())) {
			Matcher matcher = _attributesPattern.matcher(
				bbCodeItem.getAttribute());

			while (matcher.find()) {
				String attributeName = matcher.group(1);
				String attributeValue = matcher.group(2);

				if (Objects.equals(attributeName, "type")) {
					String listStyle = null;

					if (_orderedListStyles.get(attributeValue) != null) {
						listStyle = _orderedListStyles.get(attributeValue);

						tag = "ol";
					}
					else {
						listStyle = _unorderedListStyles.get(attributeValue);
					}

					if (Validator.isNotNull(listStyle)) {
						attributesSB.append(" style=\"");
						attributesSB.append(listStyle);
						attributesSB.append("\"");
					}
				}
				else if (Objects.equals(attributeName, "start") &&
						 Validator.isNumber(attributeValue)) {

					attributesSB.append(" start=\"");
					attributesSB.append(attributeValue);
					attributesSB.append("\"");
				}
			}
		}

		sb.append("<");
		sb.append(tag);
		sb.append(attributesSB);
		sb.append(">");

		tags.push("</" + tag + ">");
	}

	protected void handleListItem(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "li");
	}

	protected String handleNewLine(
		List<BBCodeItem> bbCodeItems, Stack<String> tags, IntegerWrapper marker,
		String data) {

		if ((marker.getValue() + 1) < bbCodeItems.size()) {
			BBCodeItem bbCodeItem = null;

			if (data.matches("\\A\r?\n\\z")) {
				bbCodeItem = bbCodeItems.get(marker.getValue() + 1);

				if (bbCodeItem != null) {
					String value = bbCodeItem.getValue();

					if (_excludeNewLineTypes.containsKey(value)) {
						int type = bbCodeItem.getType();

						int excludeNewLineType = _excludeNewLineTypes.get(
							value);

						if ((type & excludeNewLineType) > 0) {
							data = StringPool.BLANK;
						}
					}
				}
			}
			else if (data.matches("(?s).*\r?\n\\z")) {
				bbCodeItem = bbCodeItems.get(marker.getValue() + 1);

				if ((bbCodeItem != null) &&
					(bbCodeItem.getType() == BBCodeParser.TYPE_TAG_END)) {

					String value = bbCodeItem.getValue();

					if (value.equals("*")) {
						data = data.substring(0, data.length() - 1);
					}
				}
			}
		}

		if (data.length() > 0) {
			data = StringUtil.replace(
				data, StringPool.RETURN_NEW_LINE, "<br />");
			data = StringUtil.replace(data, CharPool.NEW_LINE, "<br />");
		}

		return data;
	}

	protected void handleQuote(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		String quote = bbCodeItem.getAttribute();

		if ((quote != null) && (quote.length() > 0)) {
			sb.append("<div class=\"quote-title\">");
			sb.append(escapeQuote(quote));
			sb.append(":</div>");
		}

		sb.append("<div class=\"quote\"><div class=\"quote-content\">");

		tags.push("</div></div>");
	}

	protected void handleSimpleTag(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		handleSimpleTag(sb, tags, bbCodeItem.getValue());
	}

	protected void handleSimpleTag(
		StringBundler sb, Stack<String> tags, String tag) {

		sb.append("<");
		sb.append(tag);
		sb.append(">");

		tags.push("</" + tag + ">");
	}

	protected void handleStrikeThrough(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "strike");
	}

	protected void handleTable(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "table");
	}

	protected void handleTableCell(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "td");
	}

	protected void handleTableHeader(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "th");
	}

	protected void handleTableRow(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "tr");
	}

	protected void handleTagEnd(StringBundler sb, Stack<String> tags) {
		sb.append(tags.pop());
	}

	protected void handleTagStart(
		StringBundler sb, List<BBCodeItem> bbCodeItems, Stack<String> tags,
		IntegerWrapper marker, BBCodeItem bbCodeItem) {

		String tag = bbCodeItem.getValue();

		if (tag.equals("b")) {
			handleBold(sb, tags);
		}
		else if (tag.equals("center") || tag.equals("justify") ||
				 tag.equals("left") || tag.equals("right")) {

			handleTextAlign(sb, tags, bbCodeItem);
		}
		else if (tag.equals("code")) {
			handleCode(sb, bbCodeItems, marker);
		}
		else if (tag.equals("color") || tag.equals("colour")) {
			handleColor(sb, tags, bbCodeItem);
		}
		else if (tag.equals("email")) {
			handleEmail(sb, bbCodeItems, tags, marker, bbCodeItem);
		}
		else if (tag.equals("font")) {
			handleFontFamily(sb, tags, bbCodeItem);
		}
		else if (tag.equals("i")) {
			handleItalic(sb, tags);
		}
		else if (tag.equals("img")) {
			handleImage(sb, tags, bbCodeItems, marker);
		}
		else if (tag.equals("li") || tag.equals("*")) {
			handleListItem(sb, tags);
		}
		else if (tag.equals("list")) {
			handleList(sb, tags, bbCodeItem);
		}
		else if (tag.equals("q") || tag.equals("quote")) {
			handleQuote(sb, tags, bbCodeItem);
		}
		else if (tag.equals("s")) {
			handleStrikeThrough(sb, tags);
		}
		else if (tag.equals("size")) {
			handleFontSize(sb, tags, bbCodeItem);
		}
		else if (tag.equals("table")) {
			handleTable(sb, tags);
		}
		else if (tag.equals("td")) {
			handleTableCell(sb, tags);
		}
		else if (tag.equals("th")) {
			handleTableHeader(sb, tags);
		}
		else if (tag.equals("tr")) {
			handleTableRow(sb, tags);
		}
		else if (tag.equals("url")) {
			handleURL(sb, bbCodeItems, tags, marker, bbCodeItem);
		}
		else {
			handleSimpleTag(sb, tags, bbCodeItem);
		}
	}

	protected void handleTextAlign(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		sb.append("<p style=\"text-align: ");
		sb.append(bbCodeItem.getValue());
		sb.append("\">");

		tags.push("</p>");
	}

	protected void handleURL(
		StringBundler sb, List<BBCodeItem> bbCodeItems, Stack<String> tags,
		IntegerWrapper marker, BBCodeItem bbCodeItem) {

		sb.append("<a href=\"");

		String href = bbCodeItem.getAttribute();

		if (href == null) {
			href = extractData(
				bbCodeItems, marker, "url", BBCodeParser.TYPE_DATA, false);
		}

		Matcher matcher = _urlPattern.matcher(href);

		if (matcher.matches()) {
			sb.append(HtmlUtil.escapeHREF(href));
		}

		sb.append("\">");

		tags.push("</a>");
	}

	private static final String[][] _EMOTICONS = {
		{"happy.gif", ":)", "happy"}, {"smile.gif", ":D", "smile"},
		{"cool.gif", "B)", "cool"}, {"sad.gif", ":(", "sad"},
		{"tongue.gif", ":P", "tongue"}, {"laugh.gif", ":lol:", "laugh"},
		{"kiss.gif", ":#", "kiss"}, {"blush.gif", ":*)", "blush"},
		{"bashful.gif", ":bashful:", "bashful"}, {"smug.gif", ":smug:", "smug"},
		{"blink.gif", ":blink:", "blink"}, {"huh.gif", ":huh:", "huh"},
		{"mellow.gif", ":mellow:", "mellow"},
		{"unsure.gif", ":unsure:", "unsure"}, {"mad.gif", ":mad:", "mad"},
		{"oh_my.gif", ":O", "oh-my-goodness"},
		{"roll_eyes.gif", ":rolleyes:", "roll-eyes"},
		{"angry.gif", ":angry:", "angry"},
		{"suspicious.gif", "8o", "suspicious"},
		{"big_grin.gif", ":grin:", "grin"},
		{"in_love.gif", ":love:", "in-love"}, {"bored.gif", ":bored:", "bored"},
		{"closed_eyes.gif", "-_-", "closed-eyes"},
		{"cold.gif", ":cold:", "cold"}, {"sleep.gif", ":sleep:", "sleep"},
		{"glare.gif", ":glare:", "glare"},
		{"darth_vader.gif", ":vader:", "darth-vader"},
		{"dry.gif", ":dry:", "dry"}, {"exclamation.gif", ":what:", "what"},
		{"girl.gif", ":girl:", "girl"},
		{"karate_kid.gif", ":kid:", "karate-kid"},
		{"ninja.gif", ":ph34r:", "ninja"}, {"pac_man.gif", ":V", "pac-man"},
		{"wacko.gif", ":wacko:", "wacko"}, {"wink.gif", ":wink:", "wink"},
		{"wub.gif", ":wub:", "wub"}
	};

	private static final int[] _FONT_SIZES = {10, 12, 14, 16, 18, 24, 32, 48};

	private static final Log _log = LogFactoryUtil.getLog(
		HtmlBBCodeTranslatorImpl.class);

	private static final Pattern _attributesPattern = Pattern.compile(
		"\\s*([^=]+)\\s*=\\s*\"([^\"]+)\"\\s*");
	private static final Pattern _bbCodePattern = Pattern.compile(
		"[]&<>'\"`\\[()]");
	private static final Pattern _colorPattern = Pattern.compile(
		"^(:?aqua|black|blue|fuchsia|gray|green|lime|maroon|navy|olive|purple" +
			"|red|silver|teal|white|yellow|#(?:[0-9a-f]{3})?[0-9a-f]{3})$",
		Pattern.CASE_INSENSITIVE);
	private static final Pattern _imagePattern = Pattern.compile(
		"^(?:https?://|/)[-;/?:@&=+$,_.!~*'()%0-9a-z]{1,2048}$",
		Pattern.CASE_INSENSITIVE);
	private static final Pattern _urlPattern = Pattern.compile(
		"^[-;/?:@&=+$,_.!~*'()%\\p{Digit}\\p{L}#]{1,2048}$",
		Pattern.CASE_INSENSITIVE);

	private final Map<String, String> _bbCodeCharacters;
	private final BBCodeParser _bbCodeParser = new BBCodeParser();
	private final String[] _emoticonDescriptions =
		new String[_EMOTICONS.length];
	private final String[] _emoticonFiles = new String[_EMOTICONS.length];
	private final String[] _emoticonSymbols = new String[_EMOTICONS.length];
	private final Map<String, Integer> _excludeNewLineTypes;
	private final Set<String> _imageAttributes;
	private final Map<String, String> _orderedListStyles;
	private final Map<String, String> _unorderedListStyles;

}