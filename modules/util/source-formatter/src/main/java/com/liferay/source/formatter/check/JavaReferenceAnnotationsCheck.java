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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin Lee
 */
public class JavaReferenceAnnotationsCheck extends JavaAnnotationsCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String formatAnnotation(
			String fileName, String absolutePath, JavaClass javaClass,
			String fileContent, String annotation, String indent)
		throws Exception {

		String trimmedAnnotation = StringUtil.trim(annotation);

		if (!(trimmedAnnotation.equals("@Reference") ||
			  trimmedAnnotation.startsWith("@Reference("))) {

			return annotation;
		}

		List<String> importNames = javaClass.getImportNames();

		if (!importNames.contains(
				"org.osgi.service.component.annotations.Reference")) {

			return annotation;
		}

		List<String> ignoreHaveReferenceMethods = getAttributeValues(
				_IGNORE_HAVE_REFERENCE_METHODS_KEY, absolutePath);

		if (isAttributeValue(_CHECK_REFERENCE_METHOD_KEY, absolutePath)) {

			boolean ignoreFile = false;

			for (String ignoreHaveReferenceMethod : ignoreHaveReferenceMethods) {
				if (absolutePath.endsWith(ignoreHaveReferenceMethod)) {
					ignoreFile = true;

					break;
				}
			}

			if (!ignoreFile) {
				_checkReferenceMethods(fileName, javaClass);
			}
		}

		_checkTargetAttribute(fileName, absolutePath, javaClass, annotation);

		return annotation;
	}

	private void _checkReferenceMethods(String fileName, JavaClass javaClass) {
		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (javaTerm.isJavaMethod() &&
					javaTerm.hasAnnotation("Reference")) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Do not use @Reference on method ", javaTerm.getName(),
						", use @Reference on field or ServiceTracker",
						"/ServiceTrackerList/ServiceTrackerMap instead"));

				break;
			}
		}
	}

	private void _checkTargetAttribute(
			String fileName, String absolutePath, JavaClass javaClass,
			String annotation)
		throws Exception {

		String targetAttributeValue = getAnnotationAttributeValue(
			annotation, "target");

		List<String> ignoreTargetAttributeValues = getAttributeValues(
			_IGNORE_TARGET_ATTRIBUTE_VALUES_KEY, absolutePath);

		if ((targetAttributeValue == null) ||
			ignoreTargetAttributeValues.contains(targetAttributeValue)) {

			return;
		}

		String componentName = _getComponentName(
			absolutePath, javaClass, targetAttributeValue);

		if (componentName == null) {
			return;
		}

		if (componentName.contains("+")) {
			componentName = componentName.replaceAll("[^\\w\\.]", "");
		}

		if (componentName.contains("*")) {
			addMessage(
				fileName,
				"Do not use globs for the 'component.name'. Use the fully " +
					"qualified name of the component class.");

			return;
		}

		if (!componentName.startsWith("com.liferay")) {
			return;
		}

		JavaClass componentJavaClass = _getJavaClass(
			absolutePath, componentName);

		if (componentJavaClass != null) {
			List<String> importNames = componentJavaClass.getImportNames();

			if (componentJavaClass.hasAnnotation("Component") &&
				importNames.contains(
					"org.osgi.service.component.annotations.Component")) {

				return;
			}
		}

		addMessage(
			fileName,
			"The value '" + componentName + "' is not a valid OSGi component");
	}

	private Map<String, String> _getBundleSymbolicNamesMap(
		String absolutePath) {

		Map<String, String> bundleSymbolicNamesMap = _bundleSymbolicNamesMap;

		if (bundleSymbolicNamesMap == null) {
			bundleSymbolicNamesMap = BNDSourceUtil.getBundleSymbolicNamesMap(
				_getRootDirName(absolutePath));

			_bundleSymbolicNamesMap = bundleSymbolicNamesMap;
		}

		return bundleSymbolicNamesMap;
	}

	private String _getComponentName(
			String absolutePath, JavaClass javaClass,
			String targetAttributeValue)
		throws Exception {

		Matcher classConstantMatcher = _classConstantPattern.matcher(
			targetAttributeValue);

		if (!classConstantMatcher.find()) {
			Matcher componentNameMatcher = _componentNamePattern.matcher(
				targetAttributeValue);

			if (componentNameMatcher.find()) {
				return componentNameMatcher.group(1);
			}

			return null;
		}

		String classConstantName = classConstantMatcher.group(1);
		JavaClass classConstantJavaClass = javaClass;

		if (classConstantMatcher.groupCount() == 2) {
			String className = classConstantMatcher.group(1);

			String fullyQualifiedName =
				javaClass.getPackageName() + "." + className;

			for (String importName : javaClass.getImportNames()) {
				if (importName.endsWith(className)) {
					fullyQualifiedName = importName;

					break;
				}
			}

			classConstantName = classConstantMatcher.group(2);
			classConstantJavaClass = _getJavaClass(
				absolutePath, fullyQualifiedName);
		}

		if (classConstantJavaClass != null) {
			for (JavaTerm javaTerm :
					classConstantJavaClass.getChildJavaTerms()) {

				if (!javaTerm.isJavaVariable()) {
					continue;
				}

				JavaVariable javaVariable = (JavaVariable)javaTerm;

				if (classConstantName.equals(javaVariable.getName())) {
					Matcher componentNameMatcher =
						_componentNamePattern.matcher(
							javaVariable.getContent());

					if (componentNameMatcher.find()) {
						return componentNameMatcher.group(1);
					}

					return null;
				}
			}
		}

		return null;
	}

	private JavaClass _getJavaClass(
			String absolutePath, String fullyQualifiedName)
		throws Exception {

		JavaClass javaClass = _javaClassMap.get(fullyQualifiedName);

		if (javaClass == null) {
			File javaFile = JavaSourceUtil.getJavaFile(
				fullyQualifiedName, _getRootDirName(absolutePath),
				_getBundleSymbolicNamesMap(absolutePath));

			if (javaFile != null) {
				javaClass = JavaClassParser.parseJavaClass(
					javaFile.getName(), FileUtil.read(javaFile));

				_javaClassMap.put(fullyQualifiedName, javaClass);
			}
		}

		return javaClass;
	}

	private String _getRootDirName(String absolutePath) {
		String rootDirName = _rootDirName;

		if (rootDirName == null) {
			rootDirName = JavaSourceUtil.getRootDirName(absolutePath);

			_rootDirName = rootDirName;
		}

		return rootDirName;
	}

	private static final String _IGNORE_TARGET_ATTRIBUTE_VALUES_KEY =
		"ignoreTargetAttributeValues";

	private static final String _IGNORE_HAVE_REFERENCE_METHODS_KEY =
			"ignoreHaveReferenceMethods";

	private static final String _CHECK_REFERENCE_METHOD_KEY =
			"checkReferenceMethod";

	private static final Pattern _classConstantPattern = Pattern.compile(
		"^([A-Z]\\w+)\\.?([A-Z]\\w+)$");
	private static final Pattern _componentNamePattern = Pattern.compile(
		"\\(component\\.name=([^)]+)\\)");

	private volatile Map<String, String> _bundleSymbolicNamesMap;
	private final Map<String, JavaClass> _javaClassMap =
		new ConcurrentHashMap<>();
	private volatile String _rootDirName;

}