/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaConstructor;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaSchedulerEntryImplConstructorCheck
	extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (!extendedClassNames.contains("SchedulerEntryImpl")) {
			return content;
		}

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaConstructor()) {
				continue;
			}

			JavaConstructor javaConstructor = (JavaConstructor)childJavaTerm;

			content = StringUtil.replace(
				content, javaConstructor.getContent(),
				_replaceConstructor(javaConstructor));
		}

		return content;
	}

	@Override
	protected String[] getNewImports() {
		return _newImports;
	}

	private String _getNewSuperWithSchedulerEntryImpl(String parameterName) {
		StringBundler sb = new StringBundler(7);

		sb.append("super(");
		sb.append(parameterName);
		sb.append(".getEventListenerClass(), ");
		sb.append(parameterName);
		sb.append(".getTriggerConfiguration(), ");
		sb.append(parameterName);
		sb.append(".getDescription());");

		return sb.toString();
	}

	private String _replaceConstructor(JavaConstructor javaConstructor) {
		String constructorContent = javaConstructor.getContent();

		Matcher matcher = _superPattern.matcher(constructorContent);

		if (!matcher.find()) {
			return StringUtil.replace(
				constructorContent, "{\n",
				"{\n\t\t" + _replaceSuper(javaConstructor));
		}

		List<String> parameterList = JavaSourceUtil.getParameterList(
			matcher.group());

		if (parameterList.isEmpty()) {
			return StringUtil.replace(
				constructorContent, matcher.group(),
				_replaceSuper(javaConstructor));
		}

		JavaSignature javaSignature = javaConstructor.getSignature();

		for (JavaParameter javaParameter : javaSignature.getParameters()) {
			String parameterType = javaParameter.getParameterType();

			if (parameterType.equals("Trigger")) {
				_newImports = new String[] {
					"com.liferay.portal.kernel.scheduler.TriggerConfiguration"
				};

				return StringUtil.replace(
					constructorContent, "Trigger", "TriggerConfiguration");
			}
		}

		return constructorContent;
	}

	private String _replaceSuper(JavaConstructor javaConstructor) {
		String newSuperConstructor = "super(null, null);";

		JavaSignature javaSignature = javaConstructor.getSignature();

		for (JavaParameter javaParameter : javaSignature.getParameters()) {
			if (Objects.equals(
					javaParameter.getParameterType(), "SchedulerEntryImpl")) {

				newSuperConstructor = _getNewSuperWithSchedulerEntryImpl(
					javaParameter.getParameterName());

				break;
			}
		}

		return newSuperConstructor;
	}

	private static final Pattern _superPattern = Pattern.compile(
		"super\\((.+)*\\);");

	private String[] _newImports;

}