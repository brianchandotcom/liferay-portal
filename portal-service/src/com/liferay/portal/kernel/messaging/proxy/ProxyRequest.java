/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Micha Kiener
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class ProxyRequest implements Serializable {

	public ProxyRequest(Method method, Object[] arguments) throws Exception {
		_methodHandler = new MethodHandler(method, arguments);

		_hasReturnValue = false;

		if (method.getReturnType() != Void.TYPE) {
			_hasReturnValue = true;
		}

		MessagingProxy messagingProxy = method.getAnnotation(
			MessagingProxy.class);

		if (messagingProxy == null) {
			messagingProxy = method.getDeclaringClass().getAnnotation(
				MessagingProxy.class);
		}

		if ((messagingProxy != null) &&
			messagingProxy.mode().equals(ProxyMode.SYNC)) {

			_synchronous = true;
		}
	}

	public Object execute(Object object) throws Exception {
		try {
			return _methodHandler.invoke(object);
		}
		catch (InvocationTargetException ite) {
			Throwable t = ite.getCause();

			if (t instanceof Exception) {
				throw (Exception)t;
			}
			else {
				throw new Exception(t);
			}
		}
	}

	public Object[] getArguments() {
		return _methodHandler.getArguments();
	}

	public boolean hasReturnValue() {
		return _hasReturnValue;
	}

	public boolean isSynchronous() {
		return _synchronous;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{hasReturnValue=");
		sb.append(_hasReturnValue);
		sb.append(", methodHandler=");
		sb.append(_methodHandler);
		sb.append(", synchronous");
		sb.append(_synchronous);
		sb.append("}");

		return sb.toString();
	}

	private boolean _hasReturnValue;
	private MethodHandler _methodHandler;
	private boolean _synchronous;

}