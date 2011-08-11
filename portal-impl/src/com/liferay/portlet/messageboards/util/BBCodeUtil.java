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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class BBCodeUtil {

	static String[][] emoticons = {
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

	static String[] emoticonDescriptions = new String[emoticons.length];

	static String[] emoticonFiles = new String[emoticons.length];

	static String[] emoticonSymbols = new String[emoticons.length];

	static Map<Integer, String> fontSizes = new HashMap<Integer, String>();

	static Map<String, String> listStyles = new HashMap<String, String>();

	static {
		for (int i = 0; i < emoticons.length; i++) {
			String[] emoticon = emoticons[i];

			emoticonDescriptions[i] = emoticon[2];
			emoticonFiles[i] = emoticon[0];
			emoticonSymbols[i] = emoticon[1];
		}

		fontSizes.put(new Integer(1), "<span style='font-size: 0.7em;'>");
		fontSizes.put(new Integer(2), "<span style='font-size: 0.8em;'>");
		fontSizes.put(new Integer(3), "<span style='font-size: 0.9em;'>");
		fontSizes.put(new Integer(4), "<span style='font-size: 1.0em;'>");
		fontSizes.put(new Integer(5), "<span style='font-size: 1.1em;'>");
		fontSizes.put(new Integer(6), "<span style='font-size: 1.3em;'>");
		fontSizes.put(new Integer(7), "<span style='font-size: 1.5em;'>");

		listStyles.put("1", "<ol style='list-style: decimal inside;'>");
		listStyles.put("i", "<ol style='list-style: lower-roman inside;'>");
		listStyles.put("I", "<ol style='list-style: upper-roman inside;'>");
		listStyles.put("a", "<ol style='list-style: lower-alpha inside;'>");
		listStyles.put("A", "<ol style='list-style: upper-alpha inside;'>");

		for (int i = 0; i < emoticons.length; i++) {
			String[] emoticon = emoticons[i];

			String image = emoticon[0];
			String code = emoticon[1];

			emoticon[0] =
				"<img alt='emoticon' src='@theme_images_path@/emoticons/" +
					image + "' />";
			emoticon[1] = HtmlUtil.escape(code);
		}
	}

	public static final String[][] EMOTICONS = emoticons;

	public static final String[] EMOTICONS_DESCRIPTIONS =
		emoticonDescriptions;

	public static final String[] EMOTICONS_FILES = emoticonFiles;

	public static final String[] EMOTICONS_SYMBOLS = emoticonSymbols;

	public static final String NEW_THREAD_URL = "${newThreadURL}";

	public static String getHTML(MBMessage message) {
		String body = message.getBody();

		try {
			body = getHTML(body);
		}
		catch (Exception e) {
			_log.error(
				"Could not parse message " + message.getMessageId() + " " +
					e.getMessage());
		}

		return body;
	}

	public static String getHTML(String bbcode) {
		String html = HtmlUtil.escape(bbcode);

		html = StringUtil.replace(html, _BBCODE_TAGS, _HTML_TAGS);

		for (int i = 0; i < emoticons.length; i++) {
			String[] emoticon = emoticons[i];

			html = StringUtil.replace(html, emoticon[1], emoticon[0]);
		}

		BBCodeTag tag = null;

		StringBundler sb = null;

		while ((tag = getFirstTag(html, "code")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			String code = tag.getElement().replaceAll(
				"\t", StringPool.FOUR_SPACES);
			String[] lines = code.split("\\n");
			int digits = String.valueOf(lines.length + 1).length();

			sb = new StringBundler(preTag);

			sb.append("<div class='code'>");

			for (int i = 0; i < lines.length; i++) {
				String index = String.valueOf(i + 1);
				int ld = index.length();

				sb.append("<span class='code-lines'>");

				for (int j = 0; j < digits - ld; j++) {
					sb.append("&nbsp;");
				}

				lines[i] = StringUtil.replace(lines[i], "   ",
					StringPool.NBSP + StringPool.SPACE + StringPool.NBSP);
				lines[i] = StringUtil.replace(lines[i], "  ",
					StringPool.NBSP + StringPool.SPACE);

				sb.append(index + "</span>");
				sb.append(lines[i]);

				if (index.length() < lines.length) {
					sb.append("<br />");
				}
			}

			sb.append("</div>");
			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "color")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			if (sb == null) {
				sb = new StringBundler(preTag);
			}
			else {
				sb.setIndex(0);

				sb.append(preTag);
			}

			if (tag.hasParameter()) {
				sb.append("<span style='color: ");
				sb.append(tag.getParameter() + ";'>");
				sb.append(tag.getElement() + "</span>");
			}
			else {
				sb.append(tag.getElement());
			}

			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "email")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			String mailto = GetterUtil.getString(
				tag.getParameter(), tag.getElement().trim());

			if (sb == null) {
				sb = new StringBundler(preTag);
			}
			else {
				sb.setIndex(0);

				sb.append(preTag);
			}

			sb.append("<a href='mailto: ");
			sb.append(mailto);
			sb.append("'>");
			sb.append(tag.getElement() + "</a>");
			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "font")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			if (sb == null) {
				sb = new StringBundler(preTag);
			}
			else {
				sb.setIndex(0);

				sb.append(preTag);
			}

			if (tag.hasParameter()) {
				sb.append("<span style='font-family: ");
				sb.append(tag.getParameter() + ";'>");
				sb.append(tag.getElement() + "</span>");
			}
			else {
				sb.append(tag.getElement());
			}

			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "img")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			if (sb == null) {
				sb = new StringBundler(preTag);
			}
			else {
				sb.setIndex(0);

				sb.append(preTag);
			}

			sb.append("<img alt='' src='");
			sb.append(tag.getElement().trim());
			sb.append("' />");
			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "list")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			String[] items = _getListItems(tag.getElement());

			if (sb == null) {
				sb = new StringBundler(preTag);
			}
			else {
				sb.setIndex(0);

				sb.append(preTag);
			}

			String tagParameterValue = null;

			if (tag.hasParameter() &&
				(tagParameterValue =
					listStyles.get(tag.getParameter())) != null) {
				sb.append(tagParameterValue);

				for (int i = 0; i < items.length; i++) {
					if (items[i].trim().length() > 0) {
						sb.append("<li>" + items[i].trim() + "</li>");
					}
				}

				sb.append("</ol>");
			}
			else {
				sb.append("<ul style='list-style: disc inside;'>");

				for (int i = 0; i < items.length; i++) {
					if (items[i].trim().length() > 0) {
						sb.append("<li>" + items[i].trim() + "</li>");
					}
				}

				sb.append("</ul>");
			}

			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "quote")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			if (sb == null) {
				sb = new StringBundler(preTag);
			}
			else {
				sb.setIndex(0);

				sb.append(preTag);
			}

			if (tag.hasParameter()) {
				sb.append("<div class='quote-title'>");
				sb.append(tag.getParameter() + ":</div>");
			}

			sb.append("<div class='quote'>");
			sb.append("<div class='quote-content'>");
			sb.append(tag.getElement());
			sb.append("</div></div>");
			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "size")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			if (sb == null) {
				sb = new StringBundler(preTag);
			}
			else {
				sb.setIndex(0);

				sb.append(preTag);
			}

			if (tag.hasParameter()) {
				Integer size = new Integer(
					GetterUtil.getInteger(tag.getParameter()));

				if (size.intValue() > 7) {
					size = new Integer(7);
				}

				String fontSize = fontSizes.get(size);
				if (fontSize != null) {
					sb.append(fontSize);
					sb.append(tag.getElement() + "</span>");
				}
				else {
					sb.append(tag.getElement());
				}
			}
			else {
				sb.append(tag.getElement());
			}

			sb.append(postTag);

			html = sb.toString();
		}

		while ((tag = getFirstTag(html, "url")) != null) {
			String preTag = html.substring(0, tag.getStartPos());
			String postTag = html.substring(tag.getEndPos());

			String url = GetterUtil.getString(
				tag.getParameter(), tag.getElement().trim());

			if (sb == null) {
				sb = new StringBundler(preTag);
			}
			else {
				sb.setIndex(0);

				sb.append(preTag);
			}

			sb.append("<a href='");
			sb.append(HtmlUtil.escapeHREF(url));
			sb.append("'>");
			sb.append(tag.getElement());
			sb.append("</a>");
			sb.append(postTag);

			html = sb.toString();
		}

		html = StringUtil.replace(html, "\n", "<br />");

		return html;
	}

	public static BBCodeTag getFirstTag(String bbcode, String name) {
		BBCodeTag tag = new BBCodeTag();

		String begTag = "[" + name;
		String endTag = "[/" + name + "]";

		String preTag = StringUtil.extractFirst(bbcode, begTag);

		if (preTag == null) {
			return null;
		}

		if (preTag.length() != bbcode.length()) {
			tag.setStartPos(preTag.length());

			String remainder = bbcode.substring(
				preTag.length() + begTag.length());

			int cb = remainder.indexOf("]");
			int end = _getEndTagPos(remainder, begTag, endTag);

			if (end > 0) {
				if (cb > 0 && remainder.startsWith("=")) {
					tag.setParameter(remainder.substring(1, cb));
					tag.setElement(remainder.substring(cb + 1, end));
				}
				else if (cb == 0) {
					try {
						tag.setElement(remainder.substring(1, end));
					}
					catch (StringIndexOutOfBoundsException sioobe) {
						_log.error(bbcode);

						throw sioobe;
					}
				}
			}
		}

		if (tag.hasElement()) {
			int length =
				begTag.length() + 1 + tag.getElement().length() +
					endTag.length();

			if (tag.hasParameter()) {
				length += 1 + tag.getParameter().length();
			}

			tag.setEndPos(tag.getStartPos() + length);

			return tag;
		}

		return null;
	}

	private static int _getEndTagPos(
		String remainder, String begTag, String endTag) {

		int nextBegTagPos = remainder.indexOf(begTag);
		int nextEndTagPos = remainder.indexOf(endTag);

		while ((nextBegTagPos < nextEndTagPos) && (nextBegTagPos >= 0)) {
			nextBegTagPos = remainder.indexOf(
				begTag, nextBegTagPos + begTag.length());
			nextEndTagPos = remainder.indexOf(
				endTag, nextEndTagPos + endTag.length());
		}

		return nextEndTagPos;
	}

	private static String[] _getListItems(String tagElement) {
		List<String> items = new ArrayList<String>();

		StringBundler sb = new StringBundler();

		int nestLevel = 0;

		for (String item : StringUtil.split(tagElement, "[*]")) {
			item = item.trim();

			if (item.length() == 0) {
				continue;
			}

			int begTagCount = StringUtil.count(item, "[list");

			if (begTagCount > 0) {
				nestLevel += begTagCount;
			}

			int endTagCount = StringUtil.count(item, "[/list]");

			if (endTagCount > 0) {
				nestLevel -= endTagCount;
			}

			if (nestLevel == 0) {
				if ((begTagCount == 0) && (endTagCount == 0)) {
					items.add(item);
				}
				else if (endTagCount > 0) {
					if (sb.length() > 0) {
						sb.append("[*]");
					}

					sb.append(item);

					items.add(sb.toString());

					sb.setIndex(0);
				}
			}
			else {
				if (sb.length() > 0) {
					sb.append("[*]");
				}

				sb.append(item);
			}
		}

		return items.toArray(new String[items.size()]);
	}

	private static final String[] _BBCODE_TAGS = {
		"[b]", "[/b]", "[i]", "[/i]", "[u]", "[/u]", "[s]", "[/s]",
		"[img]", "[/img]",
		"[left]", "[center]",
		"[right]",
		"[justify]",
		"[indent]",
		"[/left]", "[/center]", "[/right]", "[/justify]", "[/indent]",
		"[tt]", "[/tt]",
		"[table]", "[tr]", "[th]", "[td]",
		"[/table]", "[/tr]", "[/th]", "[/td]"
	};

	private static final String[] _HTML_TAGS = {
		"<b>", "</b>", "<i>", "</i>", "<u>", "</u>", "<strike>", "</strike>",
		"<img alt='' src='", "' />",
		"<div style='text-align: left;'>", "<div style='text-align: center;'>",
		"<div style='text-align: right;'>",
		"<div style='text-align: justify;'>",
		"<div style='margin-left: 15px;'>",
		"</div>", "</div>", "</div>", "</div>", "</div>",
		"<tt>", "</tt>",
		"<table>", "<tr>", "<th>", "<td>",
		"</table>", "</tr>", "</th>", "</td>"
	};

	private static Log _log = LogFactoryUtil.getLog(BBCodeUtil.class);

}