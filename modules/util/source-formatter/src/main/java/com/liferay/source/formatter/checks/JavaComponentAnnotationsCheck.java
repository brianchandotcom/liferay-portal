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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaComponentAnnotationsCheck extends JavaAnnotationsCheck {

	public void setCheckMismatchedServiceAttribute(
		String checkMismatchedServiceAttribute) {

		_checkMismatchedServiceAttribute = GetterUtil.getBoolean(
			checkMismatchedServiceAttribute);
	}

	public void setCheckSelfRegistration(String checkSelfRegistration) {
		_checkSelfRegistration = GetterUtil.getBoolean(checkSelfRegistration);
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		return formatAnnotations(fileName, (JavaClass)javaTerm, false);
	}

	@Override
	protected String formatAnnotation(
		String fileName, JavaClass javaClass, String annotation,
		String indent) {

		if (!annotation.contains("@Component")) {
			return annotation;
		}

		annotation = _formatAnnotationParameterProperties(annotation);
		annotation = _formatServiceAttribute(
			fileName, javaClass.getName(), annotation,
			javaClass.getImplementedClassNames());

		return annotation;
	}

	private String _addServiceAttribute(
		String annotation, String serviceAttribute) {

		if (!annotation.contains("(")) {
			return StringBundler.concat(
				annotation.substring(0, annotation.length() - 1), "(",
				serviceAttribute, ")\n");
		}

		Matcher matcher = _attributePattern.matcher(annotation);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(annotation, matcher.end()) &&
				(getLevel(annotation.substring(0, matcher.end()), "{", "}") ==
					0)) {

				String attributeName = matcher.group(1);

				if (attributeName.compareTo("service") > 0) {
					return StringUtil.insert(
						annotation, serviceAttribute + ", ", matcher.start(1));
				}
			}
		}

		String indent = SourceUtil.getIndent(annotation);

		if (annotation.endsWith("\n" + indent + ")\n")) {
			int pos = annotation.lastIndexOf("\n", annotation.length() - 2);

			return StringUtil.insert(
				annotation, ",\n\t" + indent + serviceAttribute, pos);
		}

		return StringUtil.replaceLast(
			annotation, ')', ", " + serviceAttribute + ")");
	}

	private String _formatAnnotationParameterProperties(String annotation) {
		Matcher matcher = _annotationParameterPropertyPattern.matcher(
			annotation);

		while (matcher.find()) {
			int x = matcher.end();

			while (true) {
				x = annotation.indexOf(CharPool.CLOSE_CURLY_BRACE, x);

				if (!ToolsUtil.isInsideQuotes(annotation, x)) {
					break;
				}
			}

			String parameterProperties = annotation.substring(matcher.end(), x);

			String newParameterProperties = StringUtil.replace(
				parameterProperties, new String[] {" =", "= "},
				new String[] {"=", "="});

			if (!parameterProperties.equals(newParameterProperties)) {
				return StringUtil.replaceFirst(
					annotation, parameterProperties, newParameterProperties);
			}

			parameterProperties = StringUtil.replace(
				parameterProperties,
				new String[] {
					StringPool.TAB, StringPool.FOUR_SPACES, StringPool.NEW_LINE
				},
				new String[] {
					StringPool.BLANK, StringPool.BLANK, StringPool.SPACE
				});

			parameterProperties = StringUtil.trim(parameterProperties);

			if (parameterProperties.startsWith(StringPool.AT)) {
				continue;
			}

			String[] parameterPropertiesArray = StringUtil.split(
				parameterProperties, StringPool.COMMA_AND_SPACE);

			AnnotationParameterPropertyComparator comparator =
				new AnnotationParameterPropertyComparator(matcher.group(1));

			for (int i = 1; i < parameterPropertiesArray.length; i++) {
				String parameterProperty = parameterPropertiesArray[i];
				String previousParameterProperty =
					parameterPropertiesArray[i - 1];

				if (comparator.compare(
						previousParameterProperty, parameterProperty) > 0) {

					annotation = StringUtil.replaceFirst(
						annotation, previousParameterProperty,
						parameterProperty);
					annotation = StringUtil.replaceLast(
						annotation, parameterProperty,
						previousParameterProperty);

					return annotation;
				}
			}
		}

		return annotation;
	}

	private String _formatServiceAttribute(
		String fileName, String className, String annotation,
		List<String> implementedClassNames) {

		String expectedServiceAttribute = _getExpectedServiceAttribute(
			implementedClassNames);

		Matcher matcher = _serviceAttributePattern.matcher(annotation);

		if (!matcher.find()) {
			return _addServiceAttribute(annotation, expectedServiceAttribute);
		}

		if (!_checkMismatchedServiceAttribute && !_checkSelfRegistration) {
			return annotation;
		}

		String serviceAttribute = _getServiceAttribute(
			annotation, matcher.start() + 1);

		if (_checkMismatchedServiceAttribute &&
			!serviceAttribute.equals(expectedServiceAttribute)) {

			addMessage(fileName, "Mismatched @Component 'service' attribute");
		}

		if (_checkSelfRegistration &&
			serviceAttribute.matches(".*\\W" + className + "\\.class.*")) {

			addMessage(
				fileName,
				"No need to register '" + className +
					"' in @Component 'service' attribute");
		}

		return annotation;
	}

	private String _getExpectedServiceAttribute(
		List<String> implementedClassNames) {

		if (implementedClassNames.isEmpty()) {
			return "service = {}";
		}

		if (implementedClassNames.size() == 1) {
			return StringBundler.concat(
				"service = ", implementedClassNames.get(0), ".class");
		}

		StringBundler sb = new StringBundler(
			implementedClassNames.size() * 3 + 1);

		sb.append("service = {");

		for (String implementedClassName : implementedClassNames) {
			sb.append(implementedClassName);
			sb.append(".class");
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}");

		return sb.toString();
	}

	private String _getServiceAttribute(String annotation, int start) {
		int end = start;

		while (true) {
			end = annotation.indexOf(CharPool.COMMA, end + 1);

			if (end == -1) {
				end = annotation.lastIndexOf(CharPool.CLOSE_PARENTHESIS);

				break;
			}

			if (!ToolsUtil.isInsideQuotes(annotation, end) &&
				(getLevel(annotation.substring(start, end), "{", "}") == 0)) {

				break;
			}
		}

		String serviceAttribute = StringUtil.trim(
			annotation.substring(start, end));

		if (!serviceAttribute.contains("\n")) {
			return serviceAttribute;
		}

		return StringUtil.replace(
			serviceAttribute, new String[] {"\t", "=\n", ",\n", "\n"},
			new String[] {"", "= ", ", ", ""});
	}

	private static final Pattern _annotationParameterPropertyPattern =
		Pattern.compile("\t(\\w+) = \\{");
	private static final Pattern _attributePattern = Pattern.compile(
		"\\W(\\w+)\\s*=");
	private static final Pattern _serviceAttributePattern = Pattern.compile(
		"\\Wservice\\s*=");

	private boolean _checkMismatchedServiceAttribute;
	private boolean _checkSelfRegistration;

	private class AnnotationParameterPropertyComparator
		extends NaturalOrderStringComparator {

		public AnnotationParameterPropertyComparator(String parameterName) {
			_parameterName = parameterName;
		}

		public int compare(String property1, String property2) {
			if (!_parameterName.equals("property")) {
				return super.compare(property1, property2);
			}

			String propertyName1 = _getPropertyName(property1);
			String propertyName2 = _getPropertyName(property2);

			if (propertyName1.equals(propertyName2)) {
				return super.compare(property1, property2);
			}

			int value = super.compare(propertyName1, propertyName2);

			if (propertyName1.startsWith(StringPool.QUOTE) ^
				propertyName2.startsWith(StringPool.QUOTE)) {

				return -value;
			}

			return value;
		}

		private String _getPropertyName(String property) {
			int x = property.indexOf(StringPool.EQUAL);

			if (x != -1) {
				return property.substring(0, x);
			}

			return property;
		}

		private final String _parameterName;

	}

}