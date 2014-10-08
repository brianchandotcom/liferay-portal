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

package com.liferay.portlet.wiki.translators;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.wiki.importers.mediawiki.MediaWikiImporter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jorge Ferrer
 * @author Daniel Kocsis
 */
public class MediaWikiToCreoleTranslator extends BaseTranslator {

	public static final String TABLE_OF_CONTENTS = "<<TableOfContents>>\n\n";

	public MediaWikiToCreoleTranslator() {
		initRegexps();
		initNowikiRegexps();
	}

	public boolean isStrictImportMode() {
		return _strictImportMode;
	}

	public void setStrictImportMode(boolean strictImportMode) {
		_strictImportMode = strictImportMode;
	}

	protected void initNowikiRegexps() {

		// Preformat protected

		nowikiRegexps.add("(<nowiki>)(.*?)(</nowiki>)");
		nowikiRegexps.add("(<pre>)(.*?)(</pre>)");

		// Escape protected

		nowikiRegexps.add(
			"~(\\*\\*|~|//|-|#|\\{\\{|}}|\\\\|~\\[~~[|]]|----|=|\\|)");
	}

	protected void initRegexps() {

		// Clean unnecessary header emphasis

		regexps.put("= '''([^=]+)''' =", "= $1 =");
		regexps.put("== '''([^=]+)''' ==", "== $1 ==");
		regexps.put("== '''([^=]+)''' ===", "=== $1 ===");

		// Unscape angle brackets

		regexps.put("&lt;", "<");
		regexps.put("&gt;", ">");

		// Remove categories

		regexps.put("\\[\\[[Cc]ategory:([^\\]]*)\\]\\][\\n]*", "");

		// Remove disambiguations

		regexps.put("\\{{2}OtherTopics\\|([^\\}]*)\\}{2}", StringPool.BLANK);

		// Remove work in progress

		regexps.put("\\{{2}Work in progress\\}{2}", StringPool.BLANK);

		// Remove references

		regexps.put("\\[{2}Wikipedia:([^\\]]*)\\]{2}", StringPool.BLANK);

		// Bold and italics

		regexps.put(
			"''''((?s:.)*?)(''''|(\n\n|\r\r|\r\n\r\n))", "**//$1//**$3");

		// Bold

		regexps.put("'''((?s:.)*?)('''|(\n\n|\r\r|\r\n\r\n))", "**$1**$3");

		// Italics

		regexps.put("''((?s:.)*?)(''|(\n\n|\r\r|\r\n\r\n))", "//$1//$3");

		// Normalize URLs

		regexps.put("\\[{2}((http|ftp)[^ ]*) ([^\\]]*)\\]{2}", "[$1 $3]");

		// URL

		regexps.put("\\[((http|ftp)[^ ]*)\\]", "[[$1]]");

		// URL with label

		regexps.put("\\[((http|ftp)[^ ]*) ([^\\]]*)\\]", "[[$1|$3]]");

		// Term and definition

		regexps.put("^\\t([\\w]+):\\t(.*)", "**$1**:\n$2");

		// Indented paragraph

		regexps.put("^\\t:\\t(.*)", "$1");

		// Monospace

		regexps.put("(^ (.+))(\\n (.+))*", "{{{\n$0\n}}}");

		// No wiki

		regexps.put("<nowiki>([^<]*)</nowiki>", "{{{$1}}}");

		// HTML PRE

		regexps.put("<pre>([^<]*)</pre>", "{{{$1}}}");

		// User reference

		regexps.put("[-]*\\[{2}User:([^\\]]*)\\]{2}", "$1");
	}

	@Override
	protected String postProcess(String content) {
		if (_strictImportMode) {
			content = runRegexp(
				content, "\\{{2}Special:(.*?)\\}{2}", StringPool.BLANK);
			content = runRegexp(content, "\\{{2}(.*?)\\}{2}", StringPool.BLANK);
			content = runRegexp(
				content, "(?s)\\{{2}(.*?)\\}{2}", StringPool.BLANK);
		}
		else {
			content = runRegexp(
				content, "\\{{2}Special:(.*?)\\}{2}", "{{{$1}}}\n");
			content = runRegexp(content, "\\{{2}(.*?)\\}{2}", "{{{$1}}}");
			content = runRegexp(
				content, "([^\\{])(\\{{2})([^\\{])", "$1\n{{{\n$3");
			content = runRegexp(
				content, "([^\\}])(\\}{2})([^\\}])", "$1\n}}}\n$3");
		}

		// LEP-6118

		Matcher matcher = _titlePattern.matcher(content);

		if (matcher.find()) {
			content = runRegexp(content, "^===([^=]+)===", "====$1====");
			content = runRegexp(content, "^==([^=]+)==", "===$1===");
			content = runRegexp(content, "^=([^=]+)=", "==$1==");
		}

		// Remove HTML tags

		for (Pattern pattern : _htmlTagPatterns) {
			matcher = pattern.matcher(content);

			content = matcher.replaceAll(StringPool.BLANK);
		}

		for (String htmlTag : _HTML_TAGS) {
			content = StringUtil.replace(content, htmlTag, StringPool.BLANK);
		}

		// Images

		matcher = _imagePattern.matcher(content);

		StringBuffer sb = new StringBuffer(content);

		int level = 0;
		int offset = 0;
		int originalLength = 0;
		int prefixLength = 0;

		while (matcher.find()) {
			level = 0;
			prefixLength = matcher.end(2) - matcher.start(2);

			for (int i = matcher.start(0) + offset; i < sb.length() - 1; i++) {
				if ((sb.charAt(i) == '[') && (sb.charAt(i + 1) == '[')) {
					level++;
				}
				else if ((sb.charAt(i) == ']') && (sb.charAt(i + 1) == ']')) {
					level--;

					if (level == 0) {
						originalLength = (i + 2) - (matcher.start(0) + offset);

						break;
					}
				}
			}

			int imageStartPos = matcher.end(3) + offset;
			int imageEndPos = matcher.start(2) + offset + originalLength - 4;

			String image =
				"{{" + MediaWikiImporter.SHARED_IMAGES_TITLE + "/" +
					StringUtil.toLowerCase(
						sb.substring(imageStartPos, imageEndPos)) + "}}";

			int imageLength = image.length();

			image = StringUtil.replace(image, "[[", StringPool.BLANK);
			image = StringUtil.replace(image, "]]", StringPool.BLANK);

			sb.replace(
				matcher.start(0) + offset,
				matcher.start(0) + originalLength + offset, image);

			offset +=
				MediaWikiImporter.SHARED_IMAGES_TITLE.length() - prefixLength -
					(imageLength - image.length());
		}

		content = sb.toString();

		// Tables

		matcher = _tablePattern.matcher(content);

		sb = new StringBuffer(content);

		String mediaWikiTable = null;

		offset = 0;
		originalLength = 0;

		while (matcher.find()) {
			mediaWikiTable = sb.substring(
				matcher.start(1) + offset, matcher.end(1) + offset);

			originalLength = mediaWikiTable.length() + 4;

			Matcher matcher1 = _mediaWikiTablePattern1.matcher(mediaWikiTable);

			mediaWikiTable = matcher1.replaceAll(StringPool.BLANK);

			Matcher matcher2 = _mediaWikiTablePattern2.matcher(mediaWikiTable);

			mediaWikiTable = matcher2.replaceAll("$1");

			Matcher matcher3 = _mediaWikiTablePattern3.matcher(mediaWikiTable);

			mediaWikiTable = matcher3.replaceAll("===$1===");

			Matcher matcher4 = _mediaWikiTablePattern4.matcher(mediaWikiTable);

			mediaWikiTable = matcher4.replaceAll("|=$1|");

			mediaWikiTable = StringUtil.replace(
				mediaWikiTable, CharPool.NEW_LINE, StringPool.BLANK);
			mediaWikiTable = StringUtil.replace(
				mediaWikiTable, CharPool.RETURN, StringPool.BLANK);
			mediaWikiTable = StringUtil.replace(mediaWikiTable, "|-", "\n\r");
			mediaWikiTable = StringUtil.replace(mediaWikiTable, "||", "|");
			mediaWikiTable = StringUtil.replace(
				mediaWikiTable, "////", StringPool.BLANK);

			sb.replace(
				matcher.start(0) + offset,
				matcher.start(0) + originalLength + offset, mediaWikiTable);

			offset += mediaWikiTable.length() - originalLength;
		}

		content = sb.toString();

		content = runRegexp(content, "/{2}(\\{{3})", "$1");
		content = runRegexp(content, "(\\}{3})/{2}", "$1");

		// Remove underscores from links

		matcher = _linkPattern.matcher(content);

		sb = new StringBuffer(content);

		while (matcher.find()) {
			String link = matcher.group(1).replace(
				StringPool.UNDERLINE, StringPool.SPACE);

			sb.replace(matcher.start(1), matcher.end(1), link);
		}

		return TABLE_OF_CONTENTS + super.postProcess(sb.toString());
	}

	private static final String[] _HTML_TAGS = {
		"<blockquote>", "</blockquote>", "<br>", "<br/>", "<br />", "<center>",
		"</center>", "<cite>", "</cite>","<code>", "</code>", "</div>",
		"</font>", "<hr>", "<hr/>", "<hr />", "<p>", "</p>", "<tt>", "</tt>",
		"<var>", "</var>"
	};

	private Pattern[] _htmlTagPatterns = {
		Pattern.compile("<div[^>]*>"), Pattern.compile("<font[^>]*>")};
	private Pattern _imagePattern = Pattern.compile(
		"(\\[{2})(Image|File)(:)", Pattern.DOTALL);
	private Pattern _linkPattern = Pattern.compile(
		"\\[{2}([^\\]]*)\\]{2}", Pattern.DOTALL);
	private Pattern _mediaWikiTablePattern1 = Pattern.compile(
		"class=(.*?)[|\n\r]");
	private Pattern _mediaWikiTablePattern2 = Pattern.compile("(\\|\\-)(.*)");
	private Pattern _mediaWikiTablePattern3 = Pattern.compile("\\|\\+(.*)");
	private Pattern _mediaWikiTablePattern4 = Pattern.compile("(?m)^!(.+)");
	private boolean _strictImportMode;
	private Pattern _tablePattern = Pattern.compile(
		"\\{\\|(.*?)\\|\\}", Pattern.DOTALL);
	private Pattern _titlePattern = Pattern.compile(
		"^=([^=]+)=", Pattern.MULTILINE);

}