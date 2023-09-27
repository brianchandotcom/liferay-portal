/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaStylingCheck extends BaseStylingCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (content.contains("$\n */")) {
			content = StringUtil.replace(content, "$\n */", "$\n *\n */");
		}

		content = _fixAuthorNames(content);
		content = _fixSingleLineComment(content);

		content = StringUtil.replace(
			content, " final static ", " static final ");

		content = StringUtil.replace(
			content, new String[] {";\n/**", ";;\n", "\n */\npackage "},
			new String[] {";\n\n/**", ";\n", "\n */\n\npackage "});

		Matcher matcher = _incorrectSynchronizedPattern.matcher(content);

		content = matcher.replaceAll("$1$3 $2");

		matcher = _incorrectJavadocPattern.matcher(content);

		content = matcher.replaceAll("$1*$3");

		return formatStyling(content);
	}

	private String _fixAuthorNames(String content) {
		content = content.replaceFirst(
			"(@author +)Adolfo P.rez", "$1Adolfo P\u00e9rez");
		content = content.replaceFirst(
			"(@author +)Alejandro Hern.ndez", "$1Alejandro Hern\u00e1ndez");
		content = content.replaceFirst(
			"(@author +)Alejandro Tard.n", "$1Alejandro Tard\u00edn");
		content = content.replaceFirst(
			"(@author +)Ambr.n Chaudhary", "$1Ambr\u00edn Chaudhary");
		content = content.replaceFirst(
			"(@author +)Andr. de Oliveira", "$1Andr\u00e9 de Oliveira");
		content = content.replaceFirst(
			"(@author +)Bal.zs S.fr.ny-Kovalik",
			"$1Bal\u00e1zs S\u00e1fr\u00e1ny-Kovalik");
		content = content.replaceFirst(
			"(@author +)Carlos Sierra Andr.s", "$1Carlos Sierra Andr\u00e9s");
		content = content.replaceFirst(
			"(@author +)Cristina Gonz.lez", "$1Cristina Gonz\u00e1lez");
		content = content.replaceFirst(
			"(@author +)Cristina Rodr.guez", "$1Cristina Rodr\u00edguez");
		content = content.replaceFirst(
			"(@author +)Eduardo Garc.a", "$1Eduardo Garc\u00eda");
		content = content.replaceFirst(
			"(@author +)Eduardo P.rez", "$1Eduardo P\u00e9rez");
		content = content.replaceFirst(
			"(@author +)Herv. M.nage", "$1Herv\u00e9 M\u00e9nage");
		content = content.replaceFirst(
			"(@author +)In.cio Nery", "$1In\u00e1cio Nery");
		content = content.replaceFirst(
			"(@author +)Istv.n Andr.s D.zsi",
			"$1Istv\u00e1n Andr\u00e1s D\u00e9zsi");
		content = content.replaceFirst(
			"(@author +)Iv.n Zaera", "$1Iv\u00e1n Zaera");
		content = content.replaceFirst(
			"(@author +)Jorge Gonz.lez", "$1Jorge Gonz\u00e1lez");
		content = content.replaceFirst(
			"(@author +)Jos. .ngel Jim.nez",
			"$1Jos\u00e9 \u00c1ngel Jim\u00e9nez");
		content = content.replaceFirst(
			"(@author +)Jos. Manuel Navarro", "$1Jos\u00e9 Manuel Navarro");
		content = content.replaceFirst(
			"(@author +)Jos. Mar.a Mu.oz", "$1Jos\u00e9 Mar\u00eda Mu\u00f1oz");
		content = content.replaceFirst(
			"(@author +)Juan Fern.ndez", "$1Juan Fern\u00e1ndez");
		content = content.replaceFirst(
			"(@author +)Juan Gonz.lez", "$1Juan Gonz\u00e1lez");
		content = content.replaceFirst(
			"(@author +)J.rgen Kappler", "$1J\u00fcrgen Kappler");
		content = content.replaceFirst(
			"(@author +)L.szl. Csontos", "$1L\u00e1szl\u00f3 Csontos");
		content = content.replaceFirst(
			"(@author +)Levente Hud.k", "$1Levente Hud\u00e1k");
		content = content.replaceFirst(
			"(@author +)Manuel de la Pe.a", "$1Manuel de la Pe\u00f1a");
		content = content.replaceFirst(
			"(@author +)Mariano .lvaro S.iz",
			"$1Mariano \u00c1lvaro S\u00e1iz");
		content = content.replaceFirst(
			"(@author +)M.t. Thurz.", "$1M\u00e1t\u00e9 Thurz\u00f3");
		content = content.replaceFirst(
			"(@author +)P.ter Alius", "$1P\u00e9ter Alius");
		content = content.replaceFirst(
			"(@author +)P.ter Borkuti", "$1P\u00e9ter Borkuti");
		content = content.replaceFirst(
			"(@author +)Raymond Aug.", "$1Raymond Aug\u00e9");
		content = content.replaceFirst(
			"(@author +)Roberto D.az", "$1Roberto D\u00edaz");
		content = content.replaceFirst(
			"(@author +)Rub.n Pulido", "$1Rub\u00e9n Pulido");
		content = content.replaceFirst(
			"(@author +)Sarai D.az", "$1Sarai D\u00edaz");
		content = content.replaceFirst(
			"(@author +)V.ctor Gal.n", "$1V\u00edctor Gal\u00e1n");
		content = content.replaceFirst(
			"(@author +)Zolt.n Tak.cs", "$1Zolt\u00e1n Tak\u00e1cs");
		content = content.replaceFirst(
			"(@author +)Zsolt Ol.h", "$1Zsolt Ol\u00e1h");
		content = content.replaceFirst(
			"(@author +)Zsolt Szab.", "$1Zsolt Szab\u00f3");

		return content;
	}

	private String _fixSingleLineComment(String content) {
		Matcher matcher = _singleLineCommentPattern.matcher(content);

		while (matcher.find()) {
			String commentContent = matcher.group(1);

			if (_isCommentedOutCode(commentContent)) {
				continue;
			}

			if (commentContent.startsWith(StringPool.SLASH) ||
				commentContent.matches("\t[A-Z].*")) {

				return StringUtil.replaceFirst(
					content, commentContent, commentContent.substring(1),
					matcher.start(1));
			}

			if (!commentContent.startsWith(StringPool.SPACE)) {
				return StringUtil.insert(
					content, StringPool.SPACE, matcher.start(1));
			}

			for (Map.Entry<String, String[]> entry :
					_incorrectWordsMap.entrySet()) {

				for (String incorrectWord : entry.getValue()) {
					Pattern pattern = Pattern.compile(
						".*(\\b" + incorrectWord + "\\b).*",
						Pattern.CASE_INSENSITIVE);

					String correctWord = entry.getKey();

					Matcher incorrectWordMatcher = pattern.matcher(
						commentContent);

					while (incorrectWordMatcher.find()) {
						String matchedWord = incorrectWordMatcher.group(1);

						if (matchedWord.equals(correctWord)) {
							continue;
						}

						if (Character.isUpperCase(matchedWord.charAt(0))) {
							correctWord = StringUtil.upperCaseFirstLetter(
								correctWord);
						}

						return StringUtil.replaceFirst(
							content, matchedWord, correctWord,
							matcher.start(1));
					}
				}
			}
		}

		return content;
	}

	private boolean _isCommentedOutCode(String commentContent) {
		if (commentContent.contains(StringPool.SEMICOLON) ||
			commentContent.endsWith(StringPool.COMMA) ||
			commentContent.endsWith("||") || commentContent.endsWith("&&")) {

			return true;
		}

		int level = getLevel(
			commentContent,
			new String[] {
				StringPool.OPEN_BRACKET, StringPool.OPEN_CURLY_BRACE,
				StringPool.OPEN_PARENTHESIS
			},
			new String[] {
				StringPool.CLOSE_BRACKET, StringPool.CLOSE_CURLY_BRACE,
				StringPool.CLOSE_PARENTHESIS
			});

		if ((level != 0) || commentContent.matches(".+\\..+") ||
			commentContent.matches(".+=.+")) {

			return true;
		}

		return false;
	}

	private static final Pattern _incorrectJavadocPattern = Pattern.compile(
		"(\n([\t ]*)/\\*)(\n\\2 \\*)");
	private static final Pattern _incorrectSynchronizedPattern =
		Pattern.compile("([\n\t])(synchronized) (private|public|protected)");
	private static final Map<String, String[]> _incorrectWordsMap =
		HashMapBuilder.put(
			"nonexistent",
			new String[] {"nonexisting", "non-existent", "non-existing"}
		).put(
			"TODO", new String[] {"todo", "to-do"}
		).build();
	private static final Pattern _singleLineCommentPattern = Pattern.compile(
		"\n\t*//(.+)");

}