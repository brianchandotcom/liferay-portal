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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLCustomSQLFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.contains("/custom-sql/")) {
			_checkCustomSQLXML(fileName, content);
		}

		return content;
	}

	private void _checkCustomSQLXML(String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		checkElementOrder(
			fileName, document.getRootElement(), "sql", null,
			new CustomSQLElementComparator("id"));

		Matcher matcher = _whereNotInSQLPattern.matcher(content);

		if (matcher.find()) {
			int x = content.lastIndexOf("<sql id=", matcher.start());

			int y = content.indexOf(CharPool.QUOTE, x);

			int z = content.indexOf(CharPool.QUOTE, y + 1);

			addMessage(
				fileName,
				"Avoid using WHERE ... NOT IN: " + content.substring(y + 1, z) +
					", see LPS-51315");
		}
	}

	private final Pattern _whereNotInSQLPattern = Pattern.compile(
		"WHERE[ \t\n]+\\(*[a-zA-z0-9.]+ NOT IN");

	private class CustomSQLElementComparator extends ElementComparator {

		public CustomSQLElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public int compare(Element sqlElement1, Element sqlElement2) {
			String sqlElementName1 = getElementName(sqlElement1);
			String sqlElementName2 = getElementName(sqlElement2);

			String finderObjectName1 = _getFinderObjectName(sqlElementName1);
			String finderObjectName2 = _getFinderObjectName(sqlElementName2);

			if ((finderObjectName1 == null) || (finderObjectName2 == null)) {
				return 0;
			}

			int value = finderObjectName1.compareToIgnoreCase(
				finderObjectName2);

			if (value != 0) {
				return value;
			}

			String finderKeyName1 = _getFinderKeyName(sqlElementName1);
			String finderKeyName2 = _getFinderKeyName(sqlElementName2);

			int startsWithWeight = StringUtil.startsWithWeight(
				finderKeyName1, finderKeyName2);

			if (startsWithWeight == 0) {
				return finderKeyName1.compareTo(finderKeyName2);
			}

			String startFinder = finderKeyName1.substring(0, startsWithWeight);

			if (!startFinder.contains("By")) {
				NaturalOrderStringComparator comparator =
					new NaturalOrderStringComparator();

				return comparator.compare(finderKeyName1, finderKeyName2);
			}

			int columnCount1 = StringUtil.count(
				sqlElementName1, CharPool.UNDERLINE);
			int columnCount2 = StringUtil.count(
				sqlElementName2, CharPool.UNDERLINE);

			return columnCount1 - columnCount2;
		}

		private String _getFinderKeyName(String elementName) {
			if (Validator.isNull(elementName)) {
				return null;
			}

			int pos = elementName.lastIndexOf(StringPool.PERIOD);

			if (pos == -1) {
				return null;
			}

			return elementName.substring(pos + 1);
		}

		private String _getFinderObjectName(String elementName) {
			if (Validator.isNull(elementName)) {
				return null;
			}

			int pos = elementName.lastIndexOf(StringPool.PERIOD);

			if (pos == -1) {
				return null;
			}

			return elementName.substring(0, pos);
		}

	}

}