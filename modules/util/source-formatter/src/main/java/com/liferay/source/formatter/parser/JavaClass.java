/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		String accessModifier, boolean anonymous, String content,
		List<String> importNames, boolean isAbstract, boolean isFinal,
		boolean isInterface, boolean isStatic, boolean isStrictfp,
		int lineNumber, String name, boolean nonsealed, String packageName,
		boolean sealed) {

		super(
			accessModifier, content, isAbstract, isFinal, isStatic, lineNumber,
			name);

		_anonymous = anonymous;
		_importNames = importNames;
		_isInterface = isInterface;
		_isStrictfp = isStrictfp;
		_nonsealed = nonsealed;
		_packageName = packageName;
		_sealed = sealed;
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

	public void addPermittedClassNames(String... permittedClassNames) {
		for (String permittedClassName : permittedClassNames) {
			_permittedClassTypes.add(
				new JavaClassType(
					StringUtil.trim(permittedClassName), _packageName,
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

	public List<String> getPermittedClassNames() {
		return getPermittedClassNames(false);
	}

	public List<String> getPermittedClassNames(boolean fullyQualifiedName) {
		List<String> permittedClassNames = new ArrayList<>();

		for (JavaClassType permittedClassType : _permittedClassTypes) {
			permittedClassNames.add(
				permittedClassType.toString(fullyQualifiedName));
		}

		return permittedClassNames;
	}

	public boolean isAnonymous() {
		return _anonymous;
	}

	public boolean isInterface() {
		return _isInterface;
	}

	public boolean isNonsealed() {
		return _nonsealed;
	}

	public boolean isSealed() {
		return _sealed;
	}

	public boolean isStrictfp() {
		return _isStrictfp;
	}

	private final boolean _anonymous;
	private final List<JavaTerm> _childJavaTerms = new ArrayList<>();
	private final List<JavaClassType> _extendedClassTypes = new ArrayList<>();
	private final List<JavaClassType> _implementedClassTypes =
		new ArrayList<>();
	private final List<String> _importNames;
	private final boolean _isInterface;
	private final boolean _isStrictfp;
	private final boolean _nonsealed;
	private final String _packageName;
	private final List<JavaClassType> _permittedClassTypes = new ArrayList<>();
	private final boolean _sealed;

}