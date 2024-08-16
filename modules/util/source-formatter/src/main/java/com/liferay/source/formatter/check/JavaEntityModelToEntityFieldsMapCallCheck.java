/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.comparator.ParameterNameComparator;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaEntityModelToEntityFieldsMapCallCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int x = -1;

		while (true) {
			x = content.indexOf("EntityModel.toEntityFieldsMap(", x + 1);

			if (x == -1) {
				return content;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(x));

			if (parameterList.size() < 2) {
				continue;
			}

			for (int i = 1; i < parameterList.size(); i++) {
				NewEntityFieldComparator newEntityFieldComparator =
					new NewEntityFieldComparator();

				String parameter = parameterList.get(i);
				String previousParameter = parameterList.get(i - 1);

				int compare = newEntityFieldComparator.compare(
					previousParameter, parameter);

				if (compare > 0) {
					content = StringUtil.replaceFirst(
						content, parameter, previousParameter, x);

					return StringUtil.replaceFirst(
						content, previousParameter, parameter, x);
				}
			}
		}
	}

	private class NewEntityFieldComparator implements Comparator<String> {

		@Override
		public int compare(String newEntityField1, String newEntityField2) {
			String entityFieldClassName1 = null;
			String entityFieldClassName2 = null;

			Matcher matcher = _newEntityFieldPattern.matcher(newEntityField1);

			if (matcher.find()) {
				entityFieldClassName1 = matcher.group(1);
			}

			matcher = _newEntityFieldPattern.matcher(newEntityField2);

			if (matcher.find()) {
				entityFieldClassName2 = matcher.group(1);
			}

			if ((entityFieldClassName1 == null) ||
				(entityFieldClassName2 == null)) {

				return newEntityField1.compareTo(newEntityField2);
			}

			if (!entityFieldClassName1.equals(entityFieldClassName2)) {
				return entityFieldClassName1.compareTo(entityFieldClassName2);
			}

			List<String> parameterList1 = JavaSourceUtil.getParameterList(
				newEntityField1);
			List<String> parameterList2 = JavaSourceUtil.getParameterList(
				newEntityField2);

			if (parameterList1.isEmpty() || parameterList2.isEmpty()) {
				return 0;
			}

			if (entityFieldClassName1.equals("CollectionEntityField")) {
				NewEntityFieldComparator newEntityFieldComparator =
					new NewEntityFieldComparator();

				return newEntityFieldComparator.compare(
					parameterList1.get(0), parameterList2.get(0));
			}

			ParameterNameComparator parameterNameComparator =
				new ParameterNameComparator();

			return parameterNameComparator.compare(
				parameterList1.get(0), parameterList2.get(0));
		}

		private final Pattern _newEntityFieldPattern = Pattern.compile(
			"new (\\w+EntityField)\\(");

	}

}