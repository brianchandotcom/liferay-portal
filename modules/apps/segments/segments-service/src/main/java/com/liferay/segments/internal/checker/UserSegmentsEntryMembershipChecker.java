/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.checker;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.lang.reflect.Array;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;

/**
 * @author Marcos Martins
 */
public class UserSegmentsEntryMembershipChecker {

	public static boolean isMember(
			String filterString, Map<String, Object> userAttributes)
		throws Exception {

		GroovyShell groovyShell = new GroovyShell();

		Script script = groovyShell.parse(_parse(filterString, userAttributes));

		return (boolean)script.invokeMethod("evaluate", null);
	}

	private static String _getDateValueString(String input) throws Exception {
		Matcher matcher = _dateTimePattern.matcher(input);

		if (matcher.find()) {
			String group = matcher.group();

			return _dateTimeFormat.format(
				DateUtils.parseDate(group, _ALLOWED_DATE_PATTERNS));
		}

		return null;
	}

	private static String _getFieldName(String key) {
		String fieldName = _fieldNames.get(StringUtil.trim(key));

		if (fieldName != null) {
			return fieldName;
		}

		return key;
	}

	private static Object _getFieldValue(
		String fieldName, Map<String, Object> userAttributes) {

		return userAttributes.get(_getFieldName(StringUtil.trim(fieldName)));
	}

	private static String _getGroup(String input, Pattern pattern) {
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return matcher.group();
		}

		return null;
	}

	private static String _getValue(String input) throws Exception {
		String value = _getGroup(input, _valuePattern);

		if (value == null) {
			return null;
		}

		String dateValueString = _getDateValueString(value);

		if (dateValueString != null) {
			return StringUtil.quote(dateValueString, StringPool.QUOTE);
		}

		return value;
	}

	private static String _parse(
			String filterString, Map<String, Object> userAttributes)
		throws Exception {

		String parsedFilterString = _processContainsOperations(
			filterString, userAttributes);

		parsedFilterString = _processLogicalOperations(parsedFilterString);
		parsedFilterString = _processNotOperations(parsedFilterString);
		parsedFilterString = _processOperations(
			parsedFilterString, userAttributes);

		return StringBundler.concat(
			"def evaluate() {return ", parsedFilterString, "}");
	}

	private static String _processContainsOperations(
		String filterString, Map<String, Object> userAttributes) {

		StringBuffer sb = new StringBuffer();

		Matcher matcher = _containsOperationPattern.matcher(filterString);

		while (matcher.find()) {
			String group = matcher.group();

			Object fieldValue = _getFieldValue(
				_getGroup(group, _fieldNameContainsPattern), userAttributes);

			group = _getGroup(group, _valuePattern);

			if ((fieldValue == null) || Validator.isBlank(group)) {
				continue;
			}

			matcher.appendReplacement(
				sb,
				StringBundler.concat(
					_NOT_FOUND_INDEX, " < ",
					StringUtil.quote(_toString(fieldValue), StringPool.QUOTE),
					".indexOf(", group, ")"));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private static String _processLogicalOperations(String filterString) {
		StringBuffer sb = new StringBuffer();

		Matcher matcher = _logicalOperationPattern.matcher(filterString);

		while (matcher.find()) {
			String group = matcher.group();

			matcher.appendReplacement(
				sb,
				StringUtil.quote(
					_operators.get(StringUtil.trim(group)), StringPool.SPACE));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private static String _processNotOperations(String filterString) {
		StringBuffer sb = new StringBuffer();

		Matcher matcher = _notOperationPattern.matcher(filterString);

		while (matcher.find()) {
			matcher.appendReplacement(sb, "!");
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private static String _processOperations(
			String filterString, Map<String, Object> userAttributes)
		throws Exception {

		StringBuffer sb = new StringBuffer();

		Matcher matcher = _operationPattern.matcher(filterString);

		while (matcher.find()) {
			String group = matcher.group();

			Object object = _getFieldValue(
				_getGroup(group, _fieldNamePattern), userAttributes);

			String operatorGroup = StringUtil.trim(
				_getGroup(group, _operatorPattern));

			String operator = _operators.getOrDefault(
				operatorGroup, operatorGroup);

			String value = _getValue(group);

			if ((object == null) || Validator.isBlank(operator) ||
				Validator.isBlank(value)) {

				continue;
			}

			Class<?> clazz = object.getClass();

			if (clazz.isArray()) {
				matcher.appendReplacement(
					sb,
					StringBundler.concat(
						"(", value, " in [",
						StringUtil.merge(
							TransformUtil.unsafeTransform(
								_toArray(object),
								item -> StringUtil.quote(String.valueOf(item)),
								String.class)),
						"])"));
			}
			else {
				String objectString = _toString(object);

				if (Validator.isNull(objectString)) {
					matcher.appendReplacement(sb, "false");
				}
				else {
					matcher.appendReplacement(
						sb,
						StringBundler.concat(
							StringUtil.quote(objectString, StringPool.QUOTE),
							StringPool.SPACE, operator, StringPool.SPACE,
							value));
				}
			}
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private static Object[] _toArray(Object object) {
		Class<?> clazz = object.getClass(
		).getComponentType();

		if (clazz.isPrimitive()) {
			List<Object> list = new ArrayList<>();

			for (int i = 0; i < Array.getLength(object); i++) {
				list.add(Array.get(object, i));
			}

			return list.toArray();
		}

		return (Object[])object;
	}

	private static String _toString(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof Date) {
			return _dateTimeFormat.format((Date)object);
		}

		return String.valueOf(object);
	}

	private static final String[] _ALLOWED_DATE_PATTERNS = {
		"yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
	};

	private static final int _NOT_FOUND_INDEX = -1;

	private static final Pattern _containsOperationPattern = Pattern.compile(
		"contains\\(\\w*, '\\w*'\\)");
	private static final DateFormat _dateTimeFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	private static final Pattern _dateTimePattern = Pattern.compile(
		"\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2}.\\d{3}){0,1}((Z)|" +
			"((\\+|\\-)(\\d*))){0,1}");
	private static final Pattern _fieldNameContainsPattern = Pattern.compile(
		"(?<=contains\\()\\w*");
	private static final Pattern _fieldNamePattern = Pattern.compile(
		"\\w*\\s+(?=eq|ge|gt|in|le|lt)");
	private static final Map<String, String> _fieldNames = HashMapBuilder.put(
		"dateModified", "modifiedDate"
	).build();
	private static final Pattern _logicalOperationPattern = Pattern.compile(
		"\\s+(and|or)\\s+");
	private static final Pattern _notOperationPattern = Pattern.compile(
		"not(?=\\s*\\()");
	private static final Pattern _operationPattern = Pattern.compile(
		"\\w*\\s+(eq|ge|gt|in|le|lt)\\s+('\\w*'|\\('\\w*'\\)|" +
			"\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2}.\\d{3}){0,1}" +
				"((Z)|((\\+|\\-)(\\d*))){0,1})");
	private static final Pattern _operatorPattern = Pattern.compile(
		"\\s+(eq|ge|gt|in|le|lt)(?=\\w*\\s+)");
	private static final Map<String, String> _operators = HashMapBuilder.put(
		"and", "&&"
	).put(
		"eq", "=="
	).put(
		"ge", ">="
	).put(
		"gt", ">"
	).put(
		"le", "<="
	).put(
		"lt", "<"
	).put(
		"not", "!"
	).put(
		"or", "||"
	).build();
	private static final Pattern _valuePattern = Pattern.compile(
		"'\\w*'|'{0,1}\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2}.\\d{3}){0,1}" +
			"((Z)|((\\+|\\-)(\\d*))){0,1}'{0,1}");

}