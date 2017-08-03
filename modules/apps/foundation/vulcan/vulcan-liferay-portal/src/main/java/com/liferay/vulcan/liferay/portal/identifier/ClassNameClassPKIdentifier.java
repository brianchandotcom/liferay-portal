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

package com.liferay.vulcan.liferay.portal.identifier;

/**
 * Allows developers to use a combination of className-classPK values as
 * identifier for a resource.
 *
 * <p>
 * To use this class, add it as the first parameter in
 * <code>collectionItem</code> {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder} methods.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class ClassNameClassPKIdentifier {

	public ClassNameClassPKIdentifier(String className, Long classPK) {
		_className = className;
		_classPK = classPK;
	}

	/**
	 * Returns the className.
	 *
	 * @return className.
	 */
	public String getClassName() {
		return _className;
	}

	/**
	 * Returns the classPK.
	 *
	 * @return classPK.
	 */
	public Long getClassPK() {
		return _classPK;
	}

	private final String _className;
	private final Long _classPK;

}