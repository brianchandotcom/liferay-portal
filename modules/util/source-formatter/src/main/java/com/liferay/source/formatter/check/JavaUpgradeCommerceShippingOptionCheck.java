/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class JavaUpgradeCommerceShippingOptionCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		List<String> importNames = javaTerm.getImportNames();

		String javaTermContent = javaTerm.getContent();

		if (!importNames.contains(
				"com.liferay.commerce.model.CommerceShippingOption")) {

			return javaTermContent;
		}

		Matcher matcher = _commerceShippingOptionPattern.matcher(
			javaTermContent);

		while (matcher.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				matcher.group(1));

			if (parameterList.size() == 3) {
				String newInstance = StringUtil.replace(
					matcher.group(), matcher.group(2),
					_getNewParameters(parameterList));

				javaTermContent = StringUtil.replace(
					javaTermContent, matcher.group(), newInstance);
			}
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _getNewParameters(List<String> parameters) {
		StringBundler sb = new StringBundler(6);

		sb.append(parameters.get(2));
		sb.append(", \"\", ");
		sb.append(parameters.get(0));
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(parameters.get(0));
		sb.append(", 0");

		return sb.toString();
	}

	private static final Pattern _commerceShippingOptionPattern =
		Pattern.compile("new (CommerceShippingOption\\((.+)\\))");

}