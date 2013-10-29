/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kwang Lee
 */
public class PoshiSourceProcessor extends BaseSourceProcessor {

	public static String formatPoshi(String content) {
		String newContent = StringUtil.replace(content, "\"/>\n", "\" />\n");
		while (true) {
			Matcher matcher = _commentPattern1.matcher(newContent);

			if (matcher.find()) {
				newContent = StringUtil.replaceFirst(
					newContent, ">\n", ">\n\n", matcher.start());

				continue;
			}

			matcher = _commentPattern2.matcher(newContent);

			if (!matcher.find()) {
				break;
			}

			newContent = StringUtil.replaceFirst(
				newContent, "-->\n", "-->\n\n", matcher.start());
		}

		return newContent;
	}

	@Override
	protected void format() throws Exception {
		String[] excludes = new String[] {
			"**\\TestCase*.testcase", "**\\Macro*.macro", "**\\Action*.action",
			"**\\Function*.function"};
		String[] includes = new String[] {
			"**\\*.testcase", "**\\*.macro", "**\\*.action", "**\\*.function"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String content = fileUtil.read(file);

			String newContent = content;

			if (fileName.endsWith(".action")) {
				newContent = trimContent(newContent, false);
			}

			if (fileName.endsWith(".action")) {
				newContent = validatePoshiDocument(fileName, newContent);
			}

			if (fileName.endsWith(".function")) {
				newContent = trimContent(newContent, false);
			}

			if (fileName.endsWith(".function")) {
				newContent = validatePoshiDocument(fileName, newContent);
			}

			if (fileName.endsWith(".macro")) {
				newContent = trimContent(newContent, false);
			}

			if (fileName.endsWith(".macro")) {
				newContent = validatePoshiDocument(fileName, newContent);

				try {
					Document document = saxReaderUtil.read(newContent);

					Element rootElement = document.getRootElement();

					List<Element> elements = rootElement.elements();

					for (Element element : elements) {
						String elementName = element.getName();

						if (!Validator.equals(elementName, "command")) {
							String attributeValue = element.attributeValue(
								"value");

							if (attributeValue == null) {
								newContent = StringUtil.replace(
									newContent, elementName, "command");
							}
						}

						if (!Validator.equals(elementName, "var")) {
							String attributeValue = element.attributeValue(
								"value");

							if (attributeValue != null) {
								newContent = StringUtil.replace(
									newContent, elementName, "var");
							}
						}

						if (!Validator.equals(elementName, "if")) {
							String attributeValue = element.attributeValue(
								"name");

							if (attributeValue == null) {
								newContent = StringUtil.replace(
									newContent, elementName, "if");
							}
						}
					}

					newContent = newContent;
				}
				catch (DocumentException de) {
				}

				newContent = newContent;
			}

			if (fileName.endsWith(".testcase")) {
				newContent = trimContent(newContent, false);
			}

			if (fileName.endsWith(".testcase")) {
				newContent = validatePoshiDocument(fileName, newContent);
			}

			newContent = formatPoshi(newContent);

			if (isAutoFix() && (newContent != null) &&
				!content.equals(newContent)) {

				fileUtil.write(file, newContent);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	protected String validatePoshiDocument(String fileName, String content)
		throws DocumentException, IOException {

		String newContent = content;

		try {
			Document document = saxReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			List<Element> elements = rootElement.elements();

			if (!Validator.equals(rootElement.getName(), "definition")) {
				newContent = StringUtil.replace(
					content, rootElement.getName(), "definition");
			}

			Matcher _endTagLineMatcher1 = _endTagLinePattern1.matcher(
				newContent);

			while (_endTagLineMatcher1.find()) {
				Matcher _endTagLineMatcher2 = _endTagLinePattern2.matcher(
					_endTagLineMatcher1.group());

				if (_endTagLineMatcher2.find()) {
					if (!_endTagLineMatcher2.group().contains("var")) {
						newContent = StringUtil.replace(
							newContent, _endTagLineMatcher2.group(),
							_endTagLineMatcher2.group() + "\n");
					}
				}
			}

			Matcher _endTagLineMatcher5 = _endTagLinePattern5.matcher(
				newContent);

			while (_endTagLineMatcher5.find()) {
				Matcher _endTagLineMatcher6 = _endTagLinePattern6.matcher(
					_endTagLineMatcher5.group());

				if (_endTagLineMatcher5.group().contains("var")) {
					if (_endTagLineMatcher6.find()) {
						if (!_endTagLineMatcher6.group().contains("var")) {
							newContent = StringUtil.replace(
								newContent, _endTagLineMatcher6.group(),
								"\n" + _endTagLineMatcher6.group());
						}
					}
				}
			}

			Matcher _tagLineMatcher1 = _tagLinePattern1.matcher(newContent);

			while (_tagLineMatcher1.find()) {
				Matcher _tagLineMatcher2 = _tagLinePattern2.matcher(
					_tagLineMatcher1.group());

				if (_tagLineMatcher2.find()) {
					newContent = StringUtil.replace(
						newContent, _tagLineMatcher1.group(),
						_tagLineMatcher2.group());
				}
			}

			Matcher _lineEndTagMatcher1 = _lineEndTagPattern1.matcher(
				newContent);

			while (_lineEndTagMatcher1.find()) {
				Matcher _lineEndTagMatcher2 = _lineEndTagPattern2.matcher(
					_lineEndTagMatcher1.group());

				if (_lineEndTagMatcher2.find()) {
					newContent = StringUtil.replace(
						newContent, _lineEndTagMatcher1.group(),
						_lineEndTagMatcher2.group());
				}
			}

			Matcher _endTagLineMatcher3 = _endTagLinePattern3.matcher(
				newContent);

			while (_endTagLineMatcher3.find()) {
				Matcher _endTagLineMatcher4 = _endTagLinePattern4.matcher(
					_endTagLineMatcher3.group());

				if (_endTagLineMatcher4.find()) {
					newContent = StringUtil.replace(
						newContent, _endTagLineMatcher3.group(),
						_endTagLineMatcher4.group());
				}
			}

			Matcher _emptyLineMatcher1 = _emptyLinePattern1.matcher(newContent);

			while (_emptyLineMatcher1.find()) {
				newContent = StringUtil.replace(
					newContent, _emptyLineMatcher1.group(), "\n\n");
			}

			Matcher _emptySpaceLineMatcher1 = _emptySpaceLinePattern1.matcher(
				newContent);

			while (_emptySpaceLineMatcher1.find()) {
				Matcher _emptySpaceLineMatcher2 =
					_emptySpaceLinePattern2.matcher(
						_emptySpaceLineMatcher1.group());

				if (_emptySpaceLineMatcher2.find()) {
					newContent = StringUtil.replace(
						newContent, _emptySpaceLineMatcher1.group(),
						_emptySpaceLineMatcher2.group());
				}
			}

			Matcher _emptySpaceLineMatcher3 = _emptySpaceLinePattern3.matcher(
				newContent);

			while (_emptySpaceLineMatcher3.find()) {
				Matcher _emptySpaceLineMatcher4 =
					_emptySpaceLinePattern4.matcher(
						_emptySpaceLineMatcher3.group());

				if (_emptySpaceLineMatcher4.find()) {
					newContent = StringUtil.replace(
						newContent, _emptySpaceLineMatcher3.group(),
						_emptySpaceLineMatcher4.group());
				}
			}

			Matcher _endTagNoChildrenMatcher = _endTagNoChildrenPattern.matcher(
				newContent);

			while (_endTagNoChildrenMatcher.find()) {
				newContent = StringUtil.replace(
					newContent, _endTagNoChildrenMatcher.group(), "\" />");
			}

			return newContent;
		}
		catch (DocumentException de) {
		}

		return newContent;
	}

	private static Pattern _commentPattern1 = Pattern.compile(
		">\n\t+<!--[\n ]");
	private static Pattern _commentPattern2 = Pattern.compile(
		"[\t ]-->\n[\t<]");
	private static Pattern _emptyLinePattern1 = Pattern.compile("\\n\\n\\n+");
	private static Pattern _emptySpaceLinePattern1 = Pattern.compile(
		"(\\s*\\n+)\\t\\</[a-z\\-]+>");
	private static Pattern _emptySpaceLinePattern2 = Pattern.compile(
		"\\n\\t\\</[a-z\\-]+>");
	private static Pattern _emptySpaceLinePattern3 = Pattern.compile(
		"[\\s\\t]*\\n+\\</[a-z\\-]+>");
	private static Pattern _emptySpaceLinePattern4 = Pattern.compile(
		"\\n\\</[a-z\\-]+>");
	private static Pattern _endTagLinePattern1 = Pattern.compile(
		"\\</[a-z\\-]+>\\n+\\t*\\<[a-z]+");
	private static Pattern _endTagLinePattern2 = Pattern.compile(
		"\\</[a-z\\-]+>");
	private static Pattern _endTagLinePattern3 = Pattern.compile(
		"\\</[a-z\\-]+>\\n\\n\\n+");
	private static Pattern _endTagLinePattern4 = Pattern.compile(
		"\\</[a-z\\-]+>\\n\\n");
	private static Pattern _endTagLinePattern5 = Pattern.compile(
		"\\<[a-z]+[ a-z]+=\\\"[a-zA-Z0-9_]*\\\"[ a-z]+=\\\"[a-zA-Z ]*" +
			"[a-zA-Z0-9_ ]*[a-zA-Z ]*\\\" />\\n\\t+<[^/var]+");
	private static Pattern _endTagLinePattern6 = Pattern.compile(
		"\\n\\t+<[^/var]+");
	private static Pattern _endTagNoChildrenPattern = Pattern.compile(
		"\\\" \\>\\n\\t+\\</[a-z\\-]+>");
	private static Pattern _lineEndTagPattern1 = Pattern.compile(
		"\\n+\\t+\\</[a-z\\-]+>");
	private static Pattern _lineEndTagPattern2 = Pattern.compile(
		"\\n\\t+\\</[a-z\\-]+>");
	private static Pattern _tagLinePattern1 = Pattern.compile(
		"\\<[a-z]+\\-*[a-z]+>\\n\\n+");
	private static Pattern _tagLinePattern2 = Pattern.compile(
		"\\<[a-z]+\\-*[a-z]+>\\n");

}