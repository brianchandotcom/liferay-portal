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
import com.liferay.source.formatter.exception.UpgradeCatchAllException;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeCatchAllCheck extends BaseFileCheck {

	public static String[] getExpectedMessages() throws Exception {
		List<String> expectedMessages = new ArrayList<>();

		JSONArray jsonArray = _getReplacementsJSONArray("replacements.json");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String from = jsonObject.getString("from");

			if (from.contains(StringPool.OPEN_PARENTHESIS)) {
				expectedMessages.add(_getMessage(jsonObject));
			}
		}

		return ArrayUtil.toStringArray(expectedMessages);
	}

	public static void setTestMode(boolean testMode) {
		_testMode = testMode;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		JSONArray jsonArray = _getReplacementsJSONArray("replacements.json");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (!_hasValidExtension(fileName, jsonObject)) {
				continue;
			}

			String oldContent = content;

			_newMessage = false;

			if (fileName.endsWith(".java")) {
				content = _formatJava(content, fileName, jsonObject);
			}
			else {
				content = _formatGeneral(content, fileName, jsonObject);
			}

			if (_testMode && oldContent.equals(content)) {
				String to = jsonObject.getString("to");

				if (to.isEmpty() && _newMessage) {
					continue;
				}

				throw new UpgradeCatchAllException(
					"Unable to process pattern " +
						jsonObject.getString("from") +
							" or there is no test associated with it");
			}
		}

		_testMode = false;

		return content;
	}

	private static String _getMessage(JSONObject jsonObject) {
		StringBundler sb = new StringBundler(6);

		sb.append("See ");
		sb.append(jsonObject.getString("issueKey"));
		sb.append(StringPool.COMMA_AND_SPACE);

		String[] classNames = JSONUtil.toStringArray(
			jsonObject.getJSONArray("classNames"));

		if (classNames.length > 0) {
			sb.append(StringUtil.merge(classNames, StringPool.SLASH));
		}

		String from = jsonObject.getString("from");

		int index = from.indexOf(CharPool.PERIOD);

		if (index != -1) {
			from = StringUtil.replace(from, CharPool.PERIOD, CharPool.POUND);
		}
		else {
			sb.append(StringPool.POUND);
		}

		sb.append(from);

		return sb.toString();
	}

	private static Pattern _getPattern(JSONObject jsonObject) {
		String from = jsonObject.getString("from");

		if (from.contains(StringPool.OPEN_PARENTHESIS)) {
			from = from.substring(0, from.indexOf(CharPool.OPEN_PARENTHESIS));
		}

		String regex = "\\w+\\.[\\w\\(\\)\\s\\.]*\\b" + from;

		if (from.contains(StringPool.PERIOD)) {
			regex = StringUtil.replace(from, CharPool.PERIOD, "\\.\\s*");
		}
		else if (from.contains("::")) {
			regex = from;
		}

		return Pattern.compile(regex + "[\\(;]");
	}

	private static JSONArray _getReplacementsJSONArray(String fileName)
		throws Exception {

		ClassLoader classLoader = UpgradeCatchAllCheck.class.getClassLoader();

		return new JSONArrayImpl(
			StringUtil.read(
				classLoader.getResourceAsStream("dependencies/" + fileName)));
	}

	private String _addNewReference(String content, String newReference) {
		if (!newReference.equals(StringPool.BLANK)) {
			content = JavaSourceUtil.addImports(
				content, "org.osgi.service.component.annotations.Reference");

			return StringUtil.replaceLast(
				content, CharPool.CLOSE_CURLY_BRACE,
				"\t@Reference\n\tprivate " + newReference + ";\n\n}");
		}

		return content;
	}

	private String _addOrReplaceParameters(
		String newMethodCall, List<String> parameterNames,
		List<String> newParameterNames) {

		StringBundler sb = new StringBundler(2 + newParameterNames.size());

		sb.append(newMethodCall);

		List<String> interpolatedNewParameterNames = new ArrayList<>();

		for (String newParameterName : newParameterNames) {
			String prefix = "param#";

			if (newParameterName.contains(prefix)) {
				int index = GetterUtil.getInteger(
					newParameterName.substring(
						newParameterName.indexOf(CharPool.POUND) + 1,
						newParameterName.lastIndexOf(CharPool.POUND)));

				interpolatedNewParameterNames.add(
					StringUtil.replace(
						newParameterName, prefix + index + CharPool.POUND,
						parameterNames.get(index)));
			}
			else {
				interpolatedNewParameterNames.add(newParameterName);
			}
		}

		sb.append(
			StringUtil.merge(
				interpolatedNewParameterNames, StringPool.COMMA_AND_SPACE));

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
			newContent = BaseUpgradeCheck.addNewImportsJSPHeader(
				newContent, newImports);
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
					fileName, from, newContent, jsonObject, matcher, newContent,
					to);
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
						fileName, from, javaMethodContent, jsonObject, matcher,
						newContent, to);
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
		String fileName, String from, String javaMethodContent,
		JSONObject jsonObject, Matcher matcher, String newContent, String to) {

		String methodCall = JavaSourceUtil.getMethodCall(
			javaMethodContent, matcher.start());

		List<String> parameterNames = JavaSourceUtil.getParameterList(
			methodCall);

		List<String> parameterTypes = JavaSourceUtil.getParameterList(from);

		if (parameterNames.size() != parameterTypes.size()) {
			return newContent;
		}

		if ((fileName.endsWith(".java") &&
			 !hasParameterTypes(
				 javaMethodContent, javaMethodContent,
				 ArrayUtil.toStringArray(parameterNames),
				 ArrayUtil.toStringArray(parameterTypes))) ||
			to.isEmpty()) {

			addMessage(fileName, _getMessage(jsonObject));

			_newMessage = true;

			return newContent;
		}

		String newMethodCall = to.substring(
			0, to.indexOf(CharPool.OPEN_PARENTHESIS) + 1);

		if (!newMethodCall.contains(StringPool.PERIOD)) {
			newMethodCall = StringBundler.concat(
				getVariableName(methodCall), CharPool.PERIOD, newMethodCall);
		}

		newMethodCall = _addOrReplaceParameters(
			newMethodCall, parameterNames, JavaSourceUtil.getParameterList(to));

		return StringUtil.replaceFirst(newContent, methodCall, newMethodCall);
	}

	private boolean _hasValidClassName(
		String[] classNames, String content, String methodCall) {

		String variableName = getVariableName(methodCall);

		for (String className : classNames) {
			if (Character.isUpperCase(variableName.charAt(0)) &&
				StringUtil.equals(variableName, className)) {

				return true;
			}
			else if (!Character.isUpperCase(variableName.charAt(0)) &&
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

	private static boolean _testMode;

	private boolean _newMessage;

}