/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.async.advice.internal;

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceInvokerUtil;
import com.liferay.portal.kernel.util.MethodHandler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class AsyncCallable implements Callable<Serializable>, Externalizable {

	public AsyncCallable() {
		this(null, null);
	}

	public AsyncCallable(
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
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
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