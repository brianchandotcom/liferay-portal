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

package com.liferay.portal.kernel.javadoc;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class JavadocMethodImpl extends JavadocMethod {

	public JavadocMethodImpl(Method method) {
		this(method, null);
	}

	public JavadocMethodImpl(Method method, String comment) {
		_method = method;

		setComment(comment);
	}

	@Override
	public Method getMethod() {
		return _method;
	}

	@Override
	public String getParameterComment(int index) {
		if (_parameterComments == null) {
			return null;
		}

		return _parameterComments[index];
	}

	@Override
	public String[] getParameterComments() {
		return _parameterComments;
	}

	@Override
	public String getReturnComment() {
		return _returnComment;
	}

	@Override
	public String getThrowsComment(int index) {
		if (_throwsComments == null) {
			return null;
		}

		return _throwsComments[index];
	}

	@Override
	public String[] getThrowsComments() {
		return _throwsComments;
	}

	public void setMethod(Method method) {
		_method = method;
	}

	public void setParameterComments(String[] parameterComments) {
		_parameterComments = parameterComments;
	}

	public void setReturnComment(String returnComment) {
		_returnComment = returnComment;
	}

	public void setThrowsComments(String[] throwsComments) {
		_throwsComments = throwsComments;
	}

	private Method _method;
	private String[] _parameterComments;
	private String _returnComment;
	private String[] _throwsComments;

}