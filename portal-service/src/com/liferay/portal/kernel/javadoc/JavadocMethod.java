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

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class JavadocMethod extends BaseJavadoc {

	public JavadocMethod(Method method) {
		this(method, null);
	}

	public JavadocMethod(Method method, String comment) {
		this._method = method;
		setComment(comment);
	}

	public Method getMethod() {
		return _method;
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

	public String getReturnComment() {
		return _returnComment;
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

	public void setMethod(Method method) {
		this._method = method;
	}

	public void setParametersComments(String[] parametersComments) {
		this._parametersComments = parametersComments;
	}

	public void setReturnComment(String returnComment) {
		this._returnComment = returnComment;
	}

	public void setThrowsComments(String[] throwsComments) {
		this._throwsComments = throwsComments;
	}

	private Method _method;
	private String[] _parametersComments;
	private String _returnComment;
	private String[] _throwsComments;

}