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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class JavaFinderImplCustomSQLCheck extends BaseFileCheck {

	@Override
	public void init() throws Exception {
		_portalCustomSQLDocument = getPortalCustomSQLDocument();
	}

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("FinderImpl.java")) {
			_checkCustomSQL(fileName, absolutePath, content);
		}

		return content;
	}

	private void _checkCustomSQL(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document customSQLDocument = null;
		String finderName = null;

		Matcher matcher = _stringUtilReplacePattern.matcher(content);

		outerLoop:
		while (matcher.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				matcher.group());

			if (parameterList.size() != 3) {
				continue;
			}

			String replaceSQLValue = _getReplaceSQLValue(
				content, parameterList.get(1));

			if (replaceSQLValue == null) {
				continue;
			}

			if (finderName == null) {
				String className = JavaSourceUtil.getClassName(fileName);

				finderName = className.substring(0, className.length() - 4);

				customSQLDocument = getCustomSQLDocument(
					fileName, absolutePath, _portalCustomSQLDocument);
			}

			if (customSQLDocument == null) {
				return;
			}

			Element rootElement = customSQLDocument.getRootElement();

			for (Element sqlElement :
					(List<Element>)rootElement.elements("sql")) {

				String id = sqlElement.attributeValue("id");

				if (!id.contains(finderName)) {
					continue;
				}

				String sql = _transform(sqlElement.getText());

				if (sql.contains(replaceSQLValue)) {
					continue outerLoop;
				}
			}

			addMessage(
				fileName,
				StringBundler.concat(
					"SQL '", replaceSQLValue,
					"' does not exist in the custom-sql file"),
				getLineCount(content, matcher.start()));
		}
	}

	private String _getReplaceSQLValue(String content, String parameterValue) {
		if (parameterValue.matches("[A-Z_]+")) {
			Pattern pattern = Pattern.compile(
				"final String\\s+" + parameterValue + "\\s+=\\s+(.*?);\n",
				Pattern.DOTALL);

			Matcher matcher = pattern.matcher(content);

			if (matcher.find()) {
				parameterValue = matcher.group(1);
			}
		}

		if (!parameterValue.matches("(?s)\".* .*\"")) {
			return null;
		}

		parameterValue = parameterValue.substring(
			1, parameterValue.length() - 1);

		return parameterValue.replaceAll("\"\\s+\\+\\s+\"", StringPool.BLANK);
	}

	private String _transform(String sql) {
		StringBundler sb = new StringBundler();

		String[] lines = StringUtil.splitLines(sql);

		for (String line : lines) {
			line = line.trim();

			if (line.startsWith(StringPool.CLOSE_PARENTHESIS)) {
				sb.setIndex(sb.index() - 1);
			}

			sb.append(line);

			if (!line.endsWith(StringPool.OPEN_PARENTHESIS)) {
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private Document _portalCustomSQLDocument;
	private final Pattern _stringUtilReplacePattern = Pattern.compile(
		"sql = StringUtil.replace\\(.*?\\);\n", Pattern.DOTALL);

}