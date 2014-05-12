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

package com.liferay.portal.parsers.bbcode;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslator;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ThemeConstants;
import com.liferay.portlet.messageboards.util.MBUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Iliyan Peychev
 */
@DoPrivileged
public class HtmlBBCodeTranslatorImpl implements BBCodeTranslator {

	public HtmlBBCodeTranslatorImpl() {
		_listStyles = new HashMap<String, String>();

		_listStyles.put("a", "list-style: lower-alpha outside;");
		_listStyles.put("A", "list-style: upper-alpha outside;");
		_listStyles.put("1", "list-style: decimal outside;");
		_listStyles.put("i", "list-style: lower-roman outside;");
		_listStyles.put("I", "list-style: upper-roman outside;");

		_excludeNewLineTypes = new HashMap<String, Integer>();

		_excludeNewLineTypes.put("*", BBCodeParser.TYPE_TAG_START_END);
		_excludeNewLineTypes.put("li", BBCodeParser.TYPE_TAG_START_END);
		_excludeNewLineTypes.put("table", BBCodeParser.TYPE_TAG_END);
		_excludeNewLineTypes.put("td", BBCodeParser.TYPE_TAG_START_END);
		_excludeNewLineTypes.put("th", BBCodeParser.TYPE_TAG_START_END);
		_excludeNewLineTypes.put("tr", BBCodeParser.TYPE_TAG_START_END);

		_bbCodeCharacters = new HashMap<String, String>();

		_bbCodeCharacters.put("&", "&amp;");
		_bbCodeCharacters.put("<", "&lt;");
		_bbCodeCharacters.put(">", "&gt;");
		_bbCodeCharacters.put("\"", "&#034;");
		_bbCodeCharacters.put("'", "&#039;");
		_bbCodeCharacters.put("/", "&#047;");
		_bbCodeCharacters.put("`", "&#096;");
		_bbCodeCharacters.put("[", "&#91;");
		_bbCodeCharacters.put("]", "&#93;");
		_bbCodeCharacters.put("(", "&#40;");
		_bbCodeCharacters.put(")", "&#41;");

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
		Stack<String> tags = new Stack<String>();
		IntegerWrapper marker = new IntegerWrapper();

		for (; marker.getValue() < bbCodeItems.size(); marker.increment()) {
			BBCodeItem bbCodeItem = bbCodeItems.get(marker.getValue());

			int type = bbCodeItem.getType();

			if (type == BBCodeParser.TYPE_DATA) {
				handleData(sb, bbCodeItems, tags, marker, bbCodeItem);
			}
			else if (type == BBCodeParser.TYPE_TAG_END) {
				handleTagEnd(sb, tags, bbCodeItem);
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
			sb.append(quote.substring(index, quote.length()));
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
		code = code.replaceAll(StringPool.TAB, StringPool.FOUR_SPACES);

		String[] lines = code.split("\r?\n");

		for (int i = 0; i < lines.length; i++) {
			sb.append("<tr>");
			sb.append("<td class=\"line-numbers\">");
			sb.append("<span class=\"number\">");

			String index = String.valueOf(i + 1);

			sb.append(index);
			sb.append("</span>");
			sb.append("</td>");
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

		for (int i = 0; i < _EMOTICONS.length; i++) {
			String[] emoticon = _EMOTICONS[i];

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

		if ((size >= 1) && (size <= _fontSizes.length)) {
			sb.append(_fontSizes[size - 1]);
		}
		else {
			sb.append(_fontSizes[1]);
		}

		sb.append("px\">");

		tags.push("</span>");
	}

	protected void handleImage(
		StringBundler sb, List<BBCodeItem> bbCodeItems, IntegerWrapper marker) {

		sb.append("<img src=\"");

		String src = extractData(
			bbCodeItems, marker, "img", BBCodeParser.TYPE_DATA, true);

		Matcher matcher = _imagePattern.matcher(src);

		if (matcher.matches()) {
			sb.append(HtmlUtil.escapeAttribute(src));
		}

		sb.append("\" />");
	}

	protected void handleItalic(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "em");
	}

	protected void handleList(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		String listStyle = null;

		String tag = null;

		String listAttribute = bbCodeItem.getAttribute();

		if (listAttribute != null) {
			listStyle = _listStyles.get(listAttribute);

			tag = "ol";
		}
		else {
			tag = "ul style=\"list-style: disc outside;\"";
		}

		if (listStyle == null) {
			sb.append("<");
			sb.append(tag);
			sb.append(">");
		}
		else {
			sb.append("<");
			sb.append(tag);
			sb.append(" style=\"");
			sb.append(listStyle);
			sb.append("\">");
		}

		tags.push("</" + tag + ">");
	}

	protected void handleListItem(StringBundler sb, Stack<String> tags) {
		handleSimpleTag(sb, tags, "li");
	}

	protected String handleNewLine(
		List<BBCodeItem> bbCodeItems, Stack<String> tags, IntegerWrapper marker,
		String data) {

		BBCodeItem bbCodeItem = null;

		if ((marker.getValue() + 1) < bbCodeItems.size()) {
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
			data = data.replaceAll("\r?\n", "<br />");
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

	protected void handleTagEnd(
		StringBundler sb, Stack<String> tags, BBCodeItem bbCodeItem) {

		String tag = bbCodeItem.getValue();

		if (isValidTag(tag)) {
			sb.append(tags.pop());
		}
	}

	protected void handleTagStart(
		StringBundler sb, List<BBCodeItem> bbCodeItems, Stack<String> tags,
		IntegerWrapper marker, BBCodeItem bbCodeItem) {

		String tag = bbCodeItem.getValue();

		if (!isValidTag(tag)) {
			return;
		}

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
			handleImage(sb, bbCodeItems, marker);
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

	protected boolean isValidTag(String tag) {
		if ((tag != null) && (tag.length() > 0)) {
			Matcher matcher = _tagPattern.matcher(tag);

			return matcher.matches();
		}

		return false;
	}

	private static final String[][] _EMOTICONS = {
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

	private static Log _log = LogFactoryUtil.getLog(
		HtmlBBCodeTranslatorImpl.class);

	private Map<String, String> _bbCodeCharacters;
	private BBCodeParser _bbCodeParser = new BBCodeParser();
	private Pattern _bbCodePattern = Pattern.compile("[]&<>'\"`\\[()]");
	private Pattern _colorPattern = Pattern.compile(
		"^(:?aqua|black|blue|fuchsia|gray|green|lime|maroon|navy|olive|purple" +
			"|red|silver|teal|white|yellow|#(?:[0-9a-f]{3})?[0-9a-f]{3})$",
		Pattern.CASE_INSENSITIVE);
	private String[] _emoticonDescriptions = new String[_EMOTICONS.length];
	private String[] _emoticonFiles = new String[_EMOTICONS.length];
	private String[] _emoticonSymbols = new String[_EMOTICONS.length];
	private Map<String, Integer> _excludeNewLineTypes;
	private int[] _fontSizes = {10, 12, 16, 18, 24, 32, 48};
	private Pattern _imagePattern = Pattern.compile(
		"^(?:https?://|/)[-;/?:@&=+$,_.!~*'()%0-9a-z]{1,512}$",
		Pattern.CASE_INSENSITIVE);
	private Map<String, String> _listStyles;
	private Pattern _tagPattern = Pattern.compile(
		"^/?(?:b|center|code|colou?r|email|i|img|justify|left|pre|q|quote|" +
			"right|\\*|s|size|table|tr|th|td|li|list|font|u|url)$",
		Pattern.CASE_INSENSITIVE);
	private Pattern _urlPattern = Pattern.compile(
		"^[-;/?:@&=+$,_.!~*'()%0-9a-z#]{1,512}$", Pattern.CASE_INSENSITIVE);

}