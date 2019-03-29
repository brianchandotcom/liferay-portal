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

package com.liferay.portal.async.advice.internal;

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceInvokerUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.util.MethodHandler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class AsyncProcessCallable
	implements Externalizable, ProcessCallable<Serializable> {

	public AsyncProcessCallable() {
		this(null, null);
	}

	public AsyncProcessCallable(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {

		_aopMethodInvocation = aopMethodInvocation;
		_arguments = arguments;
	}

	@Override
	public Serializable call() {
		try {
			if (_aopMethodInvocation != null) {
				_aopMethodInvocation.proceed(_arguments);
			}
			else {
				AsyncInvokeThreadLocal.setEnabled(true);

				try {
					_methodHandler.invoke(null);
				}
				finally {
					AsyncInvokeThreadLocal.setEnabled(false);
				}
			}
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}

		return null;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_methodHandler = (MethodHandler)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		MethodHandler methodHandler = _methodHandler;

		if (methodHandler == null) {
			methodHandler =
				IdentifiableOSGiServiceInvokerUtil.createMethodHandler(
					_aopMethodInvocation.getThis(),
					_aopMethodInvocation.getMethod(), _arguments);
		}

		objectOutput.writeObject(methodHandler);
	}

	private final AopMethodInvocation _aopMethodInvocation;
	private final Object[] _arguments;
	private MethodHandler _methodHandler;

}