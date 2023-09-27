/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaUpdateCommerceAddressCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			Matcher matcher = _pattern.matcher(javaMethodContent);

			while (matcher.find() &&
				   _isCommerceAddress(javaMethodContent, content, matcher)) {

				String line = matcher.group();

				String newLine = StringUtil.replace(
					line, matcher.group(1),
					_getNewUpdateCommerceAddressImplementation(
						SourceUtil.getIndent(line), matcher.group(2)));

				javaMethodContent = StringUtil.replace(
					javaMethodContent, line, newLine);
			}

			content = StringUtil.replace(
				content, javaMethod.getContent(), javaMethodContent);
		}

		return content;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.kernel.service.ServiceContextThreadLocal"
		};
	}

	private String _getNewUpdateCommerceAddressImplementation(
		String indent, String variable) {

		StringBundler sb = new StringBundler(7);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (String method : _METHODS) {
			sb.append(StringPool.NEW_LINE);
			sb.append(indent);
			sb.append(StringPool.TAB);
			sb.append(variable);
			sb.append(StringPool.PERIOD);
			sb.append(method);
			sb.append("(),");
		}

		sb.append(StringPool.NEW_LINE);
		sb.append(indent);
		sb.append(StringPool.TAB);
		sb.append("ServiceContextThreadLocal.getServiceContext()");
		sb.append(StringPool.NEW_LINE);
		sb.append(indent);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private boolean _isCommerceAddress(
		String content, String fileContent, Matcher matcher) {

		if (Objects.equals(
				getVariableTypeName(content, fileContent, matcher.group(2)),
				"CommerceAddress") &&
			(hasClassOrVariableName(
				"CommerceAddressService", content, fileContent,
				matcher.group()) ||
			 hasClassOrVariableName(
				 "CommerceAddressLocalService", content, fileContent,
				 matcher.group()))) {

			return true;
		}

		return false;
	}

	private static final String[] _METHODS = {
		"getCommerceAddressId", "getName", "getDescription", "getStreet1",
		"getStreet2", "getStreet3", "getCity", "getZip", "getRegionId",
		"getCountryId", "getPhoneNumber", "getType"
	};

	private static final Pattern _pattern = Pattern.compile(
		"\\t+\\w+\\.\\s*updateCommerceAddress(\\(\\s*(\\w+)\\s*\\));");

}