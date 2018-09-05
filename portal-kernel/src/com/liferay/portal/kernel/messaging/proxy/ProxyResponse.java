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

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Micha Kiener
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class ProxyResponse implements Serializable {

	public Exception getException() {
		return _exception;
	}

	public Object getResult() {
		return _result;
	}

	public boolean hasError() {
		if (_exception != null) {
			return true;
		}

		return false;
	}

	public void setException(Exception exception) {
		_exception = exception;
	}

	public void setResult(Object result) {
		_result = result;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{exception=");
		sb.append(_exception);
		sb.append(", result=");
		sb.append(_result);
		sb.append("}");

		return sb.toString();
	}

	private Exception _exception;
	private Object _result;

}