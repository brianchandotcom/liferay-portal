/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.javadoc;

/**
 * @author Igor Spasic
 */
public class JavadocType extends BaseJavadoc {

	public JavadocType(Class type) {
		this(type, null);
	}

	public JavadocType(Class type, String comment) {
		this._type = type;
		setComment(comment);
	}

	public String[] getAuthors() {
		return _authors;
	}

	public Class<?> getType() {
		return _type;
	}

	public void setAuthors(String[] authors) {
		this._authors = authors;
	}

	public void setType(Class<?> type) {
		this._type = type;
	}

	private String[] _authors;
	private Class<?> _type;

}