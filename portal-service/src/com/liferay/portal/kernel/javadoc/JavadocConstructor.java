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

import java.lang.reflect.Constructor;

/**
 * @author Igor Spasic
 */
public class JavadocConstructor extends BaseJavadoc {

	public JavadocConstructor(Constructor constructor) {
		this(constructor, null);
	}

	public JavadocConstructor(Constructor constructor, String comment) {
		this._constructor = constructor;
		setComment(comment);
	}

	public Constructor getConstructor() {
		return _constructor;
	}

	public String getParametersComment(int index) {
		if (_parametersComments == null) {
			return null;
		}
		return _parametersComments[index];
	}

	public String[] getParametersComments() {
		return _parametersComments;
	}

	public String getThrowsComment(int index) {
		if (_throwsComments == null) {
			return null;
		}
		return _throwsComments[index];
	}

	public String[] getThrowsComments() {
		return _throwsComments;
	}

	public void setConstructor(Constructor constructor) {
		this._constructor= constructor;
	}

	public void setParametersComments(String[] parametersComments) {
		this._parametersComments = parametersComments;
	}

	public void setThrowsComments(String[] throwsComments) {
		this._throwsComments = throwsComments;
	}

	private Constructor _constructor;
	private String[] _parametersComments;
	private String[] _throwsComments;

}