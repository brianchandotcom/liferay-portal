/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.translator;

/**
 * @author Jorge Ferrer
 */
public class ClassicToCreoleTranslator extends BaseTranslator {

	public ClassicToCreoleTranslator() {
		initRegexps();
	}

	protected void initRegexps() {

		// Bold and italics

		regexps.put(
			"'''''((?s:.)*?)('''''|(\n\n|\r\r|\r\n\r\n))", "**//$1//**$3");

		// Bold

		regexps.put("'''((?s:.)*?)('''|(\n\n|\r\r|\r\n\r\n))", "**$1**$3");

		// Italics

		regexps.put("''((?s:.)*?)(''|(\n\n|\r\r|\r\n\r\n))", "//$1//$3");

		// Link

		regexps.put("\\[([^ ]*)\\]", "[[$1]]");

		// Link with label

		regexps.put("\\[([^ ]+) (.*)\\]", "[[$1|$2]]");

		// Monospace

		regexps.put("(^ (.+))(\\n (.+))*", "{{{\n$0\n}}}");

		// List item

		regexps.put("^\\t[\\*] (.*)", "* $1");

		// List subitem

		regexps.put("^\\t\\t[\\*] (.*)", "** $1");

		// List subsubitem

		regexps.put("^\\t\\t\\t[\\*] (.*)", "*** $1");

		// List subsubsubitem

		regexps.put("^\\t\\t\\t\\t[\\*] (.*)", "**** $1");

		// Ordered list item

		regexps.put("^\\t1 (.*)", "# $1");

		// Ordered list subitem

		regexps.put("^\\t\\t1 (.*)", "## $1");

		// Ordered list subsubitem

		regexps.put("^\\t\\t\\t1 (.*)", "### $1");

		// Ordered list subsubsubitem

		regexps.put("^\\t\\t\\t\\t1 (.*)", "#### $1");

		// Term and definition

		regexps.put("^\\t([\\w]+):\\t(.*)", "**$1**:\n$2");

		// Indented paragraph

		regexps.put("^\\t:\\t(.*)", "$1");

		// CamelCase

		regexps.put(
			"(^|\\p{Punct}|\\p{Space})((\\p{Lu}\\p{Ll}+){2,})" +
				"(\\z|\\n|\\p{Punct}|\\p{Space})",
			" [[$2]] ");
	}

}