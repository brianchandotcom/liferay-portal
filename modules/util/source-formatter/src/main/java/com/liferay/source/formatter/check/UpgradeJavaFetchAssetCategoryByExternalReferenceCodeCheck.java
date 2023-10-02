/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaFetchAssetCategoryByExternalReferenceCodeCheck
	extends BaseUpgradeCheck {

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

			Matcher matcher =
				_fetchAssetCategoryByExternalReferenceCodePattern.matcher(
					javaMethodContent);

			while (matcher.find()) {
				String methodCall = JavaSourceUtil.getMethodCall(
					javaMethodContent, matcher.start());

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCall);

				if (parameterList.size() != 2) {
					continue;
				}

				String variableName = getVariableName(methodCall);

				if ((variableName != null) &&
					!variableName.contains("AssetCategoryLocalServiceUtil") &&
					!hasClassOrVariableName(
						"AssetCategoryLocalService", content, content,
						methodCall)) {

					continue;
				}

				String[] parameterTypes = {"long", "String"};

				String message = StringBundler.concat(
					"The fetchAssetCategoryByExternalReferenceCode method ",
					"from AssetCategoryLocalService and AssetCategoryLocal",
					"ServiceUtil no longer uses companyId as a parameter and ",
					"has changed the order of its parameters. Fill the new ",
					"parameters manually, see LPS-194134.");

				if (!hasValidParameters(
						parameterTypes.length, fileName, javaMethodContent,
						message, parameterList, parameterTypes)) {

					continue;
				}

				addMessage(fileName, message);
			}
		}

		return content;
	}

	private static final Pattern
		_fetchAssetCategoryByExternalReferenceCodePattern = Pattern.compile(
			"\\w+\\.fetchAssetCategoryByExternalReferenceCode\\(");

}