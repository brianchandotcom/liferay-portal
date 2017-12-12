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

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Micha Kiener
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ProxyRequest implements Externalizable {

	/**
	 * The empty constructor is required by {@link Externalizable}. Do not use
	 * this for any other purpose.
	 */
	public ProxyRequest() {
		_local = false;
	}

	public ProxyRequest(Method method, Object[] arguments) throws Exception {
		_method = method;
		_arguments = arguments;

		boolean[] localAndSynchronous = _localAndSynchronousMap.get(method);

		if (localAndSynchronous == null) {
			localAndSynchronous = new boolean[2];

			MessagingProxy messagingProxy = AnnotationLocator.locate(
				method, method.getDeclaringClass(), MessagingProxy.class);

			if (messagingProxy != null) {
				if (messagingProxy.local()) {
					localAndSynchronous[0] = true;
				}

				ProxyMode proxyMode = messagingProxy.mode();

				if (proxyMode.equals(ProxyMode.SYNC)) {
					localAndSynchronous[1] = true;
				}
			}

			_localAndSynchronousMap.put(method, localAndSynchronous);
		}

		_local = localAndSynchronous[0];
		_synchronous = localAndSynchronous[1];
	}

	public Object execute(Object object) throws Exception {
		try {
			return _method.invoke(object, _arguments);
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
		return _arguments;
	}

	public boolean hasReturnValue() {
		if (_method.getReturnType() == Void.TYPE) {
			return false;
		}

		return true;
	}

	public boolean isLocal() {
		return _local;
	}

	public boolean isSynchronous() {
		return _synchronous;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_arguments = (Object[])objectInput.readObject();

		_local = objectInput.readBoolean();

		MethodKey methodKey = (MethodKey)objectInput.readObject();

		try {
			_method = methodKey.getMethod();
		}
		catch (NoSuchMethodException nsme) {
			throw new IOException(nsme);
		}

		_synchronous = objectInput.readBoolean();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{arguments=");
		sb.append(Arrays.toString(_arguments));
		sb.append(", local");
		sb.append(_local);
		sb.append(", method=");
		sb.append(_method);
		sb.append(", synchronous");
		sb.append(_synchronous);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeObject(_arguments);
		objectOutput.writeBoolean(_local);
		objectOutput.writeObject(new MethodKey(_method));
		objectOutput.writeBoolean(_synchronous);
	}

	private static final Map<Method, boolean[]> _localAndSynchronousMap =
		new ConcurrentHashMap<>();

	private Object[] _arguments;
	private boolean _local;
	private Method _method;
	private boolean _synchronous;

}