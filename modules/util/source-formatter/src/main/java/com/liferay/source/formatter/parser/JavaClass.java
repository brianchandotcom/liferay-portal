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
		String name, String content, String accessModifier, int lineNumber,
		boolean isAbstract, boolean isStatic, boolean isInterface,
		boolean anonymous) {

		super(name, content, accessModifier, lineNumber, isAbstract, isStatic);

		_isInterface = isInterface;
		_anonymous = anonymous;
	}

	public void addChildJavaTerm(JavaTerm javaTerm) {
		javaTerm.setParentJavaClass(this);

		_childJavaTerms.add(javaTerm);
	}

	public void addExtendedClassNames(String... extendedClassNames) {
		for (String extendedClassName : extendedClassNames) {
			_extendedClassNames.add(StringUtil.trim(extendedClassName));
		}
	}

	public void addImplementedClassNames(String... implementedClassNames) {
		for (String implementedClassName : implementedClassNames) {
			_implementedClassNames.add(StringUtil.trim(implementedClassName));
		}
	}

	public void addImport(String importName) {
		_imports.add(importName);
	}

	public List<JavaTerm> getChildJavaTerms() {
		return _childJavaTerms;
	}

	public List<String> getExtendedClassNames() {
		return getExtendedClassNames(false);
	}

	public List<String> getExtendedClassNames(boolean fullyQualifiedClassName) {
		if (!fullyQualifiedClassName || _extendedClassNames.isEmpty()) {
			return _extendedClassNames;
		}

		return _getFullyQualifiedClassNames(_extendedClassNames);
	}

	public List<String> getImplementedClassNames() {
		return getImplementedClassNames(false);
	}

	public List<String> getImplementedClassNames(
		boolean fullyQualifiedClassName) {

		if (!fullyQualifiedClassName || _implementedClassNames.isEmpty()) {
			return _implementedClassNames;
		}

		return _getFullyQualifiedClassNames(_implementedClassNames);
	}

	public List<String> getImports() {
		return _imports;
	}

	public String getName(boolean fullyQualifiedClassName) {
		if (!fullyQualifiedClassName) {
			return getName();
		}

		return _packageName + "." + getName();
	}

	public String getPackageName() {
		return _packageName;
	}

	public boolean isAnonymous() {
		return _anonymous;
	}

	public boolean isInterface() {
		return _isInterface;
	}

	public void setPackageName(String packageName) {
		_packageName = packageName;
	}

	private List<String> _getFullyQualifiedClassNames(List<String> classNames) {
		List<String> fullyQualifiedClassNames = new ArrayList<>();

		outerLoop:
		for (String className : classNames) {
			if (className.matches("([a-z]\\w*\\.){2,}[A-Z]\\w*")) {
				fullyQualifiedClassNames.add(className);

				continue;
			}

			for (String importName : _imports) {
				if (importName.endsWith("." + className)) {
					fullyQualifiedClassNames.add(importName);

					continue outerLoop;
				}
			}

			fullyQualifiedClassNames.add(_packageName + "." + className);
		}

		return fullyQualifiedClassNames;
	}

	private final boolean _anonymous;
	private final List<JavaTerm> _childJavaTerms = new ArrayList<>();
	private List<String> _extendedClassNames = new ArrayList<>();
	private List<String> _implementedClassNames = new ArrayList<>();
	private List<String> _imports = new ArrayList<>();
	private final boolean _isInterface;
	private String _packageName;

}