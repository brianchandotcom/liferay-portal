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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.util.ThreadSafeClassLibrary;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractBaseJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DefaultDocletTagFactory;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;
import com.thoughtworks.qdox.model.annotation.AnnotationValue;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.File;
import java.io.FilenameFilter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MissingOverrideCheck extends AbstractCheck {

	public static final String MSG_MISSING_OVERRIDE = "override.missing";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PACKAGE_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), '\\', '/');

		JavaDocBuilder javaDocBuilder = _getJavaDocBuilder(fileName);

		try {
			javaDocBuilder.addSource(new UnsyncStringReader(_getContent()));
		}
		catch (ParseException pe) {
			return;
		}

		JavaClass javaClass = javaDocBuilder.getClassByName(
			_getPackagePath(detailAST) + "." + _getClassName(fileName));

		List<Tuple> ancestorJavaClassTuples = _addAncestorJavaClassTuples(
			javaClass, fileName, javaDocBuilder, new ArrayList<Tuple>());

		for (JavaMethod javaMethod : javaClass.getMethods()) {
			if (!_hasAnnotation(javaMethod, "Override") &&
				_isOverrideMethod(
					javaClass, javaMethod, ancestorJavaClassTuples)) {

				log(javaMethod.getLineNumber(), MSG_MISSING_OVERRIDE);
			}
		}
	}

	private List<Tuple> _addAncestorJavaClassTuples(
		JavaClass javaClass, String fileName, JavaDocBuilder javaDocBuilder,
		List<Tuple> ancestorJavaClassTuples) {

		JavaClass superJavaClass = _fixJavaClass(
			javaClass.getSuperJavaClass(), javaClass.getPackageName(), fileName,
			javaDocBuilder);

		if (superJavaClass != null) {
			ancestorJavaClassTuples.add(new Tuple(superJavaClass));

			ancestorJavaClassTuples = _addAncestorJavaClassTuples(
				superJavaClass, null, javaDocBuilder, ancestorJavaClassTuples);
		}

		Type[] implementz = javaClass.getImplements();

		for (Type implement : implementz) {
			Type[] actualTypeArguments = implement.getActualTypeArguments();
			JavaClass implementedInterface = _fixJavaClass(
				implement.getJavaClass(), javaClass.getPackageName(), fileName,
				javaDocBuilder);

			if (actualTypeArguments == null) {
				ancestorJavaClassTuples.add(new Tuple(implementedInterface));
			}
			else {
				ancestorJavaClassTuples.add(
					new Tuple(implementedInterface, actualTypeArguments));
			}

			ancestorJavaClassTuples = _addAncestorJavaClassTuples(
				implementedInterface, null, javaDocBuilder,
				ancestorJavaClassTuples);
		}

		return ancestorJavaClassTuples;
	}

	private URL[] _addJarFiles(URL[] urls, String dirName) {
		File dirFile = new File(dirName);

		if (!dirFile.exists()) {
			return urls;
		}

		File[] files = dirFile.listFiles(
			new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".jar")) {
						return true;
					}

					return false;
				}

			});

		for (File file : files) {
			try {
				URI uri = file.toURI();

				urls = ArrayUtil.append(urls, uri.toURL());
			}
			catch (MalformedURLException murle) {
			}
		}

		return urls;
	}

	private JavaClass _fixJavaClass(
		JavaClass javaClass, String packageName, String fileName,
		JavaDocBuilder javaDocBuilder) {

		// The methods getImplements and getSuperJavaClass in
		// com.thoughtworks.qdox.model.JavaClass incorrectly return an empty
		// class when the implemented or superclass are in the same package.
		// This method corrects that.

		if ((javaClass == null) || (fileName == null)) {
			return javaClass;
		}

		String fullyQualifiedName = javaClass.getFullyQualifiedName();

		if (fullyQualifiedName.contains(StringPool.PERIOD)) {
			return javaClass;
		}

		int pos = fileName.lastIndexOf("/");

		File file = new File(
			fileName.substring(0, pos + 1) + fullyQualifiedName + ".java");

		try {
			javaDocBuilder.addSource(file);
		}
		catch (Exception e) {
			return javaClass;
		}

		return javaDocBuilder.getClassByName(
			packageName + "." + fullyQualifiedName);
	}

	private String _getClassName(String fileName) {
		int pos = fileName.lastIndexOf('/');

		return fileName.substring(pos + 1, fileName.length() - 5);
	}

	private String _getContent() {
		FileContents fileContents = getFileContents();

		FileText fileText = fileContents.getText();

		return (String)fileText.getFullText();
	}

	private JavaDocBuilder _getJavaDocBuilder(String fileName) {
		int pos = fileName.lastIndexOf("/modules/");

		if (pos != -1) {
			return _getModulesJavaDocBuilder(fileName.substring(0, pos));
		}

		if (_javaDocBuilder != null) {
			return _javaDocBuilder;
		}

		_javaDocBuilder = new JavaDocBuilder(
			new DefaultDocletTagFactory(), new ThreadSafeClassLibrary());

		return _javaDocBuilder;
	}

	private JavaDocBuilder _getModulesJavaDocBuilder(String rootDir) {
		if (_modulesJavaDocBuilder != null) {
			return _modulesJavaDocBuilder;
		}

		ThreadSafeClassLibrary threadSafeClassLibrary =
			new ThreadSafeClassLibrary();

		URL[] urls = _addJarFiles(new URL[0], rootDir + "/tools/sdk/dist");

		threadSafeClassLibrary.addClassLoader(new URLClassLoader(urls));

		_modulesJavaDocBuilder = new JavaDocBuilder(
			new DefaultDocletTagFactory(), threadSafeClassLibrary);

		return _modulesJavaDocBuilder;
	}

	private String _getPackagePath(DetailAST packageDefAST) {
		DetailAST dotAST = packageDefAST.findFirstToken(TokenTypes.DOT);

		FullIdent fullIdent = FullIdent.createFullIdent(dotAST);

		return fullIdent.getText();
	}

	private boolean _hasAnnotation(
		AbstractBaseJavaEntity abstractBaseJavaEntity, String annotationName) {

		Annotation[] annotations = abstractBaseJavaEntity.getAnnotations();

		if (annotations == null) {
			return false;
		}

		for (int i = 0; i < annotations.length; i++) {
			Type type = annotations[i].getType();

			JavaClass javaClass = type.getJavaClass();

			if (annotationName.equals(javaClass.getName())) {
				return true;
			}
		}

		return false;
	}

	private boolean _isOverrideMethod(
		JavaClass javaClass, JavaMethod javaMethod,
		Collection<Tuple> ancestorJavaClassTuples) {

		if (javaMethod.isConstructor() || javaMethod.isPrivate() ||
			javaMethod.isStatic() ||
			_overridesHigherJavaAPIVersion(javaMethod)) {

			return false;
		}

		String methodName = javaMethod.getName();

		JavaParameter[] javaParameters = javaMethod.getParameters();

		Type[] types = new Type[javaParameters.length];

		for (int i = 0; i < javaParameters.length; i++) {
			types[i] = javaParameters[i].getType();
		}

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
					methodName, types);
			}
			else {

				// LPS-35613

				Type[] ancestorActualTypeArguments =
					(Type[])ancestorJavaClassTuple.getObject(1);

				Type[] genericTypes = new Type[types.length];

				for (int i = 0; i < types.length; i++) {
					Type type = types[i];

					String typeValue = type.getValue();

					boolean useGenericType = false;

					for (int j = 0; j < ancestorActualTypeArguments.length;
						j++) {

						if (typeValue.equals(
								ancestorActualTypeArguments[j].getValue())) {

							useGenericType = true;

							break;
						}
					}

					if (useGenericType) {
						genericTypes[i] = new Type("java.lang.Object");
					}
					else {
						genericTypes[i] = type;
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
			else {
				return false;
			}
		}

		return false;
	}

	private boolean _overridesHigherJavaAPIVersion(JavaMethod javaMethod) {
		Annotation[] annotations = javaMethod.getAnnotations();

		if (annotations == null) {
			return false;
		}

		for (Annotation annotation : annotations) {
			Type type = annotation.getType();

			JavaClass javaClass = type.getJavaClass();

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

	private JavaDocBuilder _javaDocBuilder;
	private JavaDocBuilder _modulesJavaDocBuilder;

}