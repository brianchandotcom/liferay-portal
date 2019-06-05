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
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaComponentAnnotationsCheck extends JavaAnnotationsCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		return formatAnnotations(fileName, absolutePath, (JavaClass)javaTerm);
	}

	@Override
	protected String formatAnnotation(
		String fileName, String absolutePath, JavaClass javaClass,
		String annotation, String indent) {

		String trimmedAnnotation = StringUtil.trim(annotation);

		if (!trimmedAnnotation.equals("@Component") &&
			!trimmedAnnotation.startsWith("@Component(")) {

			return annotation;
		}

		List<String> importNames = javaClass.getImports();

		if (!importNames.contains(
				"org.osgi.service.component.annotations.Component")) {

			return annotation;
		}

		annotation = _formatAnnotationParameterProperties(annotation);
		annotation = _formatConfigurationAttributes(
			fileName, absolutePath, javaClass, annotation);
		annotation = _formatServiceAttribute(
			fileName, absolutePath, javaClass.getName(), annotation,
			javaClass.getImplementedClassNames());

		return annotation;
	}

	private String _addAttribute(
		String annotation, String attributeName, String attributeValue) {

		if (!annotation.contains("(")) {
			return StringBundler.concat(
				annotation.substring(0, annotation.length() - 1), "(",
				attributeName, " = ", attributeValue, ")\n");
		}

		Matcher matcher = _attributePattern.matcher(annotation);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(annotation, matcher.end(1)) &&
				(getLevel(annotation.substring(0, matcher.end()), "{", "}") ==
					0)) {

				String curAttributeName = matcher.group(1);

				if (curAttributeName.compareTo(attributeName) > 0) {
					return StringUtil.insert(
						annotation,
						StringBundler.concat(
							attributeName, " = ", attributeValue, ", "),
						matcher.start(1));
				}
			}
		}

		String indent = SourceUtil.getIndent(annotation);

		if (annotation.endsWith("\n" + indent + ")\n")) {
			int pos = annotation.lastIndexOf("\n", annotation.length() - 2);

			return StringUtil.insert(
				annotation,
				StringBundler.concat(
					",\n\t", indent, attributeName, " = ", attributeValue),
				pos);
		}

		return StringUtil.replaceLast(
			annotation, ')',
			StringBundler.concat(
				", ", attributeName, " = ", attributeValue, ")"));
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

	private String _formatConfigurationAttributes(
		String fileName, String absolutePath, JavaClass javaClass,
		String annotation) {

		if (_getAttributeValue(annotation, "configurationPid") != null) {
			return annotation;
		}

		for (JavaMethod javaMethod :
				_getJavaMethods(javaClass, "Activate", "Modified")) {

			String javaMethodContent = javaMethod.getContent();

			if (javaMethodContent.contains(
					"ConfigurableUtil.createConfigurable")) {

				addMessage(
					fileName,
					"Missing @Component 'configurationPid' attribute, see " +
						"LPS-88783");

				break;
			}
		}

		if (!isAttributeValue(
				_CHECK_CONFIGURATION_POLICY_ATTRIBUTE_KEY, absolutePath)) {

			return annotation;
		}

		List<String> imports = javaClass.getImports();

		if (imports.contains(
				"org.osgi.service.component.annotations.Modified") ||
			(_getAttributeValue(annotation, "configurationPolicy ") != null)) {

			return annotation;
		}

		for (JavaMethod javaMethod : _getJavaMethods(javaClass, "Activate")) {
			JavaSignature signature = javaMethod.getSignature();

			for (JavaParameter parameter : signature.getParameters()) {
				String parameterType = parameter.getParameterType();

				if (parameterType.equals("ComponentContext") ||
					parameterType.startsWith("Map<")) {

					return annotation;
				}
			}
		}

		return _addAttribute(
			annotation, "configurationPolicy", "ConfigurationPolicy.IGNORE");
	}

	private String _formatServiceAttribute(
		String fileName, String absolutePath, String className,
		String annotation, List<String> implementedClassNames) {

		String expectedServiceAttributeValue =
			_getExpectedServiceAttributeValue(implementedClassNames);

		String serviceAttributeValue = _getAttributeValue(
			annotation, "service");

		if (serviceAttributeValue == null) {
			return _addAttribute(
				annotation, "service", expectedServiceAttributeValue);
		}

		boolean checkMismatchedServiceAttribute = isAttributeValue(
			_CHECK_MISMATCHED_SERVICE_ATTRIBUTE_KEY, absolutePath);
		boolean checkSelfRegistration = isAttributeValue(
			_CHECK_SELF_REGISTRATION_KEY, absolutePath);

		if (!checkMismatchedServiceAttribute && !checkSelfRegistration) {
			return annotation;
		}

		if (checkMismatchedServiceAttribute &&
			!serviceAttributeValue.equals(expectedServiceAttributeValue)) {

			addMessage(fileName, "Mismatched @Component 'service' attribute");
		}

		if (checkSelfRegistration &&
			serviceAttributeValue.matches(".*\\b" + className + "\\.class.*")) {

			addMessage(
				fileName,
				"No need to register '" + className +
					"' in @Component 'service' attribute");
		}

		return annotation;
	}

	private String _getAttributeValue(String annotation, String attributeName) {
		Pattern pattern = Pattern.compile("\\W" + attributeName + "\\s*=");

		Matcher matcher = pattern.matcher(annotation);

		if (!matcher.find()) {
			return null;
		}

		int start = matcher.end() + 1;

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

		String attributeValue = StringUtil.trim(
			annotation.substring(start, end));

		if (!attributeValue.contains("\n")) {
			return attributeValue;
		}

		return StringUtil.replace(
			attributeValue, new String[] {"\t", ",\n", "\n"},
			new String[] {"", ", ", ""});
	}

	private String _getExpectedServiceAttributeValue(
		List<String> implementedClassNames) {

		if (implementedClassNames.isEmpty()) {
			return "{}";
		}

		if (implementedClassNames.size() == 1) {
			return implementedClassNames.get(0) + ".class";
		}

		StringBundler sb = new StringBundler(
			implementedClassNames.size() * 3 + 1);

		sb.append("{");

		for (String implementedClassName : implementedClassNames) {
			sb.append(implementedClassName);
			sb.append(".class");
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}");

		return sb.toString();
	}

	private List<JavaMethod> _getJavaMethods(
		JavaClass javaClass, String... annotations) {

		List<JavaMethod> javaMethods = new ArrayList<>();

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!(javaTerm instanceof JavaMethod)) {
				continue;
			}

			for (String annotation : annotations) {
				if (javaTerm.hasAnnotation(annotation)) {
					javaMethods.add((JavaMethod)javaTerm);

					break;
				}
			}
		}

		return javaMethods;
	}

	private static final String _CHECK_CONFIGURATION_POLICY_ATTRIBUTE_KEY =
		"checkConfigurationPolicyAttribute";

	private static final String _CHECK_MISMATCHED_SERVICE_ATTRIBUTE_KEY =
		"checkMismatchedServiceAttribute";

	private static final String _CHECK_SELF_REGISTRATION_KEY =
		"checkSelfRegistration";

	private static final Pattern _annotationParameterPropertyPattern =
		Pattern.compile("\\s(\\w+) = \\{");
	private static final Pattern _attributePattern = Pattern.compile(
		"\\W(\\w+)\\s*=");

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