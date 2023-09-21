/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeCatchAllCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		JSONArray jsonArray = _getReplacementsJSONArray("replacements.json");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (!_hasValidExtension(fileName, jsonObject)) {
				continue;
			}

			if (fileName.endsWith(".java")) {
				newContent = _formatJava(newContent, fileName, jsonObject);
			}
			else {
				newContent = _formatGeneral(newContent, fileName, jsonObject);
			}
		}

		return newContent;
	}

	private static Pattern _getPattern(JSONObject jsonObject) {
		String from = jsonObject.getString("from");

		if (from.contains(StringPool.OPEN_PARENTHESIS)) {
			from = from.substring(0, from.indexOf(CharPool.OPEN_PARENTHESIS));
		}

		String regex = "\\w+\\.[\\w\\(\\)\\s\\.]*" + from;

		if (from.contains(StringPool.PERIOD)) {
			regex = StringUtil.replace(from, CharPool.PERIOD, "\\.\\s*");
		}

		return Pattern.compile(regex + "\\(");
	}

	private String _addNewReference(String content, String newReference) {
		if (!newReference.equals(StringPool.BLANK)) {
			content = JavaSourceUtil.addImports(
				content, "org.osgi.service.component.annotations.Reference");

			return StringUtil.replaceLast(
				content, CharPool.CLOSE_CURLY_BRACE,
				"\n\t@Reference\n\tprivate " + newReference + ";\n\n}");
		}

		return content;
	}

	private String _addOrReplaceParameters(
		String newMethodCall, List<String> parameterList,
		List<String> parametersOrder) {

		String prefix = "param#";

		List<String> newParameterList = new ArrayList<>();

		StringBundler sb = new StringBundler(2 + parametersOrder.size());

		sb.append(newMethodCall);

		for (String parameterOrder : parametersOrder) {
			if (parameterOrder.contains(prefix)) {
				String parameterIndex = parameterOrder.substring(
					parameterOrder.indexOf(CharPool.POUND) + 1,
					parameterOrder.lastIndexOf(CharPool.POUND));

				newParameterList.add(
					StringUtil.replace(
						parameterOrder,
						prefix + parameterIndex + CharPool.POUND,
						parameterList.get(
							GetterUtil.getInteger(parameterIndex))));
			}
			else {
				newParameterList.add(parameterOrder);
			}
		}

		sb.append(
			StringUtil.merge(newParameterList, StringPool.COMMA_AND_SPACE));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private String _addReplacementDependencies(
		String fileName, JSONObject jsonObject, String newContent) {

		String[] newImports = JSONUtil.toStringArray(
			jsonObject.getJSONArray("newImports"));

		if (fileName.endsWith(".java")) {
			newContent = JavaSourceUtil.addImports(newContent, newImports);

			return _addNewReference(
				newContent, jsonObject.getString("newReference"));
		}
		else if (fileName.endsWith(".jsp")) {
			for (String newImport : newImports) {
				if (!newContent.contains(newImport)) {
					newContent = StringBundler.concat(
						"<%@ page import=\"", newImport, "\" %>\n\n",
						newContent);
				}
			}
		}

		return newContent;
	}

	private String _formatGeneral(
		String content, String fileName, JSONObject jsonObject) {

		String newContent = content;

		Pattern pattern = _getPattern(jsonObject);

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String methodCall = matcher.group();

			String from = jsonObject.getString("from");
			String to = jsonObject.getString("to");

			if (from.contains(StringPool.OPEN_PARENTHESIS)) {
				newContent = _formatParameters(
					new String[0], fileName, from, newContent, jsonObject,
					matcher, newContent, to);
			}
			else {
				newContent = StringUtil.replace(
					newContent, methodCall,
					StringUtil.replace(methodCall, from, to));
			}
		}

		if (!content.equals(newContent)) {
			newContent = _addReplacementDependencies(
				fileName, jsonObject, newContent);
		}

		return newContent;
	}

	private String _formatJava(
			String content, String fileName, JSONObject jsonObject)
		throws Exception {

		String newContent = content;

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			Pattern pattern = _getPattern(jsonObject);

			Matcher matcher = pattern.matcher(javaMethodContent);

			while (matcher.find()) {
				String methodCall = matcher.group();

				String[] classNames = JSONUtil.toStringArray(
					jsonObject.getJSONArray("classNames"));

				if ((classNames.length > 0) &&
					!_hasValidClassName(classNames, content, methodCall)) {

					continue;
				}

				String from = jsonObject.getString("from");
				String to = jsonObject.getString("to");

				if (from.contains(StringPool.OPEN_PARENTHESIS)) {
					newContent = _formatParameters(
						classNames, fileName, from, javaMethodContent,
						jsonObject, matcher, newContent, to);
				}
				else {
					newContent = StringUtil.replaceFirst(
						newContent, methodCall,
						StringUtil.replace(methodCall, from, to));
				}
			}
		}

		if (!content.equals(newContent)) {
			newContent = _addReplacementDependencies(
				fileName, jsonObject, newContent);
		}

		return newContent;
	}

	private String _formatParameters(
		String[] classNames, String fileName, String from,
		String javaMethodContent, JSONObject jsonObject, Matcher matcher,
		String newContent, String to) {

		String methodCall = JavaSourceUtil.getMethodCall(
			javaMethodContent, matcher.start());

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		List<String> parametersTypes = JavaSourceUtil.getParameterList(from);

		if (parameterList.size() != parametersTypes.size()) {
			return newContent;
		}

		if (fileName.endsWith(".java") &&
			!hasParameterTypes(
				javaMethodContent, javaMethodContent,
				ArrayUtil.toStringArray(parameterList),
				ArrayUtil.toStringArray(parametersTypes))) {

			StringBundler sb = new StringBundler(6);

			sb.append("See ");
			sb.append(jsonObject.getString("issueKey"));
			sb.append(StringPool.COMMA_AND_SPACE);

			if (classNames.length > 0) {
				sb.append(StringUtil.merge(classNames, StringPool.SLASH));
			}
			else {
				sb.append(getVariableName(methodCall));
			}

			sb.append(StringPool.POUND);

			int index = from.indexOf(CharPool.OPEN_PARENTHESIS);

			if (from.contains(StringPool.PERIOD)) {
				sb.append(from.substring(from.indexOf(CharPool.PERIOD), index));
			}
			else {
				sb.append(from.substring(0, index));
			}

			addMessage(fileName, sb.toString());

			return newContent;
		}

		List<String> parametersOrder = JavaSourceUtil.getParameterList(to);

		String newMethodCall = to.substring(
			0, to.indexOf(CharPool.OPEN_PARENTHESIS) + 1);

		if (!newMethodCall.contains(StringPool.PERIOD)) {
			newMethodCall = StringBundler.concat(
				getVariableName(methodCall), CharPool.PERIOD, newMethodCall);
		}

		newMethodCall = _addOrReplaceParameters(
			newMethodCall, parameterList, parametersOrder);

		return StringUtil.replaceFirst(newContent, methodCall, newMethodCall);
	}

	private JSONArray _getReplacementsJSONArray(String fileName)
		throws Exception {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"dependencies/" + fileName);

		if (inputStream == null) {
			return new JSONArrayImpl();
		}

		return new JSONArrayImpl(StringUtil.read(inputStream));
	}

	private boolean _hasValidClassName(
		String[] classNames, String content, String methodCall) {

		for (String className : classNames) {
			if (className.endsWith("Util") &&
				StringUtil.equals(getVariableName(methodCall), className)) {

				return true;
			}
			else if (!className.endsWith("Util") &&
					 hasClassOrVariableName(
						 className, content, content, methodCall)) {

				return true;
			}
		}

		return false;
	}

	private boolean _hasValidExtension(String fileName, JSONObject jsonObject) {
		String[] validExtensions = JSONUtil.toStringArray(
			jsonObject.getJSONArray("validExtensions"));

		if (validExtensions.length == 0) {
			validExtensions = new String[] {"java"};
		}

		for (String validExtension : validExtensions) {
			if (fileName.endsWith(CharPool.PERIOD + validExtension)) {
				return true;
			}
		}

		return false;
	}

}