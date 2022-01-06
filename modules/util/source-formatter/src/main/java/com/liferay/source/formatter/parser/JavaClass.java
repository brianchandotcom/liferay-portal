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

package com.liferay.source.formatter.parser;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaClass extends BaseJavaTerm {

	public JavaClass(
		String name, String packageName, List<String> importNames,
		String content, String accessModifier, int lineNumber,
		boolean isAbstract, boolean isFinal, boolean isStatic,
		boolean isInterface, boolean anonymous) {

		super(
			name, content, accessModifier, lineNumber, isAbstract, isFinal,
			isStatic);

		_packageName = packageName;
		_importNames = importNames;
		_isInterface = isInterface;
		_anonymous = anonymous;
	}

	public void addChildJavaTerm(JavaTerm javaTerm) {
		javaTerm.setParentJavaClass(this);

		_childJavaTerms.add(javaTerm);
	}

	public void addExtendedClassNames(String... extendedClassNames) {
		for (String extendedClassName : extendedClassNames) {
			_extendedClassTypes.add(
				new JavaClassType(
					StringUtil.trim(extendedClassName), _packageName,
					_importNames));
		}
	}

	public void addImplementedClassNames(String... implementedClassNames) {
		for (String implementedClassName : implementedClassNames) {
			_implementedClassTypes.add(
				new JavaClassType(
					StringUtil.trim(implementedClassName), _packageName,
					_importNames));
		}
	}

	public List<JavaTerm> getChildJavaTerms() {
		return _childJavaTerms;
	}

	public List<String> getExtendedClassNames() {
		return getExtendedClassNames(false);
	}

	public List<String> getExtendedClassNames(boolean fullyQualifiedName) {
		List<String> extendedClassNames = new ArrayList<>();

		for (JavaClassType extendedClassType : _extendedClassTypes) {
			extendedClassNames.add(
				extendedClassType.toString(fullyQualifiedName));
		}

		return extendedClassNames;
	}

	public List<String> getImplementedClassNames() {
		return getImplementedClassNames(false);
	}

	public List<String> getImplementedClassNames(boolean fullyQualifiedName) {
		List<String> implementedClassNames = new ArrayList<>();

		for (JavaClassType implementedClassType : _implementedClassTypes) {
			implementedClassNames.add(
				implementedClassType.toString(fullyQualifiedName));
		}

		return implementedClassNames;
	}

	public List<JavaClassType> getImplementedClassTypes() {
		return _implementedClassTypes;
	}

	@Override
	public List<String> getImportNames() {
		return _importNames;
	}

	public String getName(boolean fullyQualifiedClassName) {
		if (!fullyQualifiedClassName) {
			return getName();
		}

		return _packageName + "." + getName();
	}

	@Override
	public String getPackageName() {
		return _packageName;
	}

	public boolean isAnonymous() {
		return _anonymous;
	}

	public boolean isInterface() {
		return _isInterface;
	}

	private final boolean _anonymous;
	private final List<JavaTerm> _childJavaTerms = new ArrayList<>();
	private final List<JavaClassType> _extendedClassTypes = new ArrayList<>();
	private final List<JavaClassType> _implementedClassTypes =
		new ArrayList<>();
	private final List<String> _importNames;
	private final boolean _isInterface;
	private final String _packageName;

}