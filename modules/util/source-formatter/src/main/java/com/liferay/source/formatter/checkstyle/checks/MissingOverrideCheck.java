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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.ExcludeSyntaxPattern;
import com.liferay.source.formatter.SourceFormatter;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;
import com.liferay.source.formatter.util.ThreadSafeSortedClassLibraryBuilder;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.JavaType;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import com.thoughtworks.qdox.model.impl.DefaultJavaParameterizedType;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class MissingOverrideCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PACKAGE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), '\\', '/');

		JavaProjectBuilder javaProjectBuilder = null;

		try {
			javaProjectBuilder = _getJavaProjectBuilder(fileName);
		}
		catch (Exception e) {
			return;
		}

		if (javaProjectBuilder == null) {
			return;
		}

		JavaClass javaClass = javaProjectBuilder.getClassByName(
			_getPackageName(detailAST) + "." + _getClassName(fileName));

		List<Tuple> ancestorJavaClassTuples = _addAncestorJavaClassTuples(
			javaClass, javaProjectBuilder, new ArrayList<Tuple>());

		for (JavaMethod javaMethod : javaClass.getMethods()) {
			if (javaMethod.getLineNumber() == 0) {
				continue;
			}

			if (!_hasAnnotation(javaMethod, "Override") &&
				_isOverrideMethod(
					javaClass, javaMethod, javaProjectBuilder,
					ancestorJavaClassTuples)) {

				log(javaMethod.getLineNumber(), _MSG_MISSING_OVERRIDE);
			}
		}
	}

	private List<Tuple> _addAncestorJavaClassTuples(
		JavaClass javaClass, JavaProjectBuilder javaProjectBuilder,
		List<Tuple> ancestorJavaClassTuples) {

		JavaClass superJavaClass = javaClass.getSuperJavaClass();

		if (superJavaClass != null) {
			ancestorJavaClassTuples.add(new Tuple(superJavaClass));

			ancestorJavaClassTuples = _addAncestorJavaClassTuples(
				superJavaClass, javaProjectBuilder, ancestorJavaClassTuples);
		}

		for (JavaClass interfaceClass : javaClass.getInterfaces()) {
			if (!(interfaceClass instanceof DefaultJavaParameterizedType)) {
				continue;
			}

			DefaultJavaParameterizedType defaultJavaParameterizedType =
				(DefaultJavaParameterizedType)interfaceClass;

			List<JavaType> actualTypeArguments =
				defaultJavaParameterizedType.getActualTypeArguments();

			if (actualTypeArguments == null) {
				ancestorJavaClassTuples.add(new Tuple(interfaceClass));
			}
			else {
				ancestorJavaClassTuples.add(
					new Tuple(interfaceClass, actualTypeArguments));
			}

			ancestorJavaClassTuples = _addAncestorJavaClassTuples(
				interfaceClass, javaProjectBuilder, ancestorJavaClassTuples);
		}

		return ancestorJavaClassTuples;
	}

	private String _getClassName(String fileName) {
		int pos = fileName.lastIndexOf('/');

		return fileName.substring(pos + 1, fileName.length() - 5);
	}

	private JavaProjectBuilder _getJavaProjectBuilder(String fileName)
		throws IOException {

		if (_javaProjectBuilder != null) {
			return _javaProjectBuilder;
		}

		JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder(
			new ThreadSafeSortedClassLibraryBuilder());

		String absolutePath = SourceUtil.getAbsolutePath(fileName);

		while (true) {
			int x = absolutePath.lastIndexOf("/");

			if (x == -1) {
				return null;
			}

			absolutePath = absolutePath.substring(0, x);

			File file = new File(absolutePath + "/portal-impl");

			if (file.exists()) {
				break;
			}
		}

		Set<ExcludeSyntaxPattern> defaultExcludeSyntaxPatterns =
			SetUtil.fromArray(SourceFormatter.DEFAULT_EXCLUDE_SYNTAX_PATTERNS);

		List<String> fileNames = SourceFormatterUtil.scanForFiles(
			absolutePath + "/", new String[0], new String[] {"**/*.java"},
			new SourceFormatterExcludes(defaultExcludeSyntaxPatterns), true);

		for (String curFileName : fileNames) {
			curFileName = StringUtil.replace(
				curFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			try {
				javaProjectBuilder.addSource(
					new File(SourceUtil.getAbsolutePath(curFileName)));
			}
			catch (Exception e) {
			}
		}

		_javaProjectBuilder = javaProjectBuilder;

		return _javaProjectBuilder;
	}

	private String _getPackageName(DetailAST packageDefAST) {
		DetailAST dotAST = packageDefAST.findFirstToken(TokenTypes.DOT);

		FullIdent fullIdent = FullIdent.createFullIdent(dotAST);

		return fullIdent.getText();
	}

	private boolean _hasAnnotation(
		JavaMethod javaMethod, String annotationName) {

		List<JavaAnnotation> annotations = javaMethod.getAnnotations();

		if (annotations == null) {
			return false;
		}

		for (JavaAnnotation javaAnnotation : annotations) {
			JavaClass javaClass = javaAnnotation.getType();

			if (annotationName.equals(javaClass.getName())) {
				return true;
			}
		}

		return false;
	}

	private boolean _isOverrideMethod(
		JavaClass javaClass, JavaMethod javaMethod,
		JavaProjectBuilder javaProjectBuilder,
		Collection<Tuple> ancestorJavaClassTuples) {

		if (javaMethod.isPrivate() || javaMethod.isStatic() ||
			_overridesHigherJavaAPIVersion(javaMethod)) {

			return false;
		}

		String methodName = javaMethod.getName();

		List<JavaType> parameterTypes = javaMethod.getParameterTypes();

		// Check for matching method in each ancestor

		for (Tuple ancestorJavaClassTuple : ancestorJavaClassTuples) {
			JavaClass ancestorJavaClass =
				(JavaClass)ancestorJavaClassTuple.getObject(0);

			JavaMethod ancestorJavaMethod = null;

			String ancestorJavaClassName =
				ancestorJavaClass.getFullyQualifiedName();

			if ((ancestorJavaClassTuple.getSize() == 1) ||
				(ancestorJavaClassName.equals("java.util.Map") &&
				 methodName.equals("get"))) {

				ancestorJavaMethod = ancestorJavaClass.getMethodBySignature(
					methodName, parameterTypes);
			}
			else {

				// LPS-35613

				List<JavaType> ancestorActualTypeArguments =
					(List<JavaType>)ancestorJavaClassTuple.getObject(1);

				List<JavaType> genericTypes = new ArrayList<>();

				for (JavaType parameterType : parameterTypes) {
					String typeValue = parameterType.getValue();

					boolean useGenericType = false;

					for (JavaType ancestorActualTypeArgument :
							ancestorActualTypeArguments) {

						if (typeValue.equals(
								ancestorActualTypeArgument.getValue())) {

							useGenericType = true;

							break;
						}
					}

					if (useGenericType) {
						genericTypes.add(
							javaProjectBuilder.getClassByName(
								"java.lang.Object"));
					}
					else {
						genericTypes.add(parameterType);
					}
				}

				ancestorJavaMethod = ancestorJavaClass.getMethodBySignature(
					methodName, genericTypes);
			}

			if (ancestorJavaMethod == null) {
				continue;
			}

			boolean samePackage = false;

			JavaPackage ancestorJavaPackage = ancestorJavaClass.getPackage();

			if (ancestorJavaPackage != null) {
				samePackage = ancestorJavaPackage.equals(
					javaClass.getPackage());
			}

			// Check if the method is in scope

			if (samePackage) {
				return !ancestorJavaMethod.isPrivate();
			}

			if (ancestorJavaMethod.isProtected() ||
				ancestorJavaMethod.isPublic()) {

				return true;
			}

			return false;
		}

		return false;
	}

	private boolean _overridesHigherJavaAPIVersion(JavaMethod javaMethod) {
		List<JavaAnnotation> annotations = javaMethod.getAnnotations();

		if (annotations == null) {
			return false;
		}

		for (JavaAnnotation annotation : annotations) {
			JavaClass javaClass = annotation.getType();

			String javaClassName = javaClass.getFullyQualifiedName();

			if (javaClassName.equals(SinceJava.class.getName())) {
				AnnotationValue annotationValue = annotation.getProperty(
					"value");

				double sinceJava = GetterUtil.getDouble(
					annotationValue.getParameterValue());

				if (sinceJava > _LOWEST_SUPPORTED_JAVA_VERSION) {
					return true;
				}
			}
		}

		return false;
	}

	private static final double _LOWEST_SUPPORTED_JAVA_VERSION = 1.7;

	private static final String _MSG_MISSING_OVERRIDE = "override.missing";

	private JavaProjectBuilder _javaProjectBuilder;

}