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

package com.liferay.portal.internal.resiliency.service;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.MethodHandler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class ServiceMethodProcessCallable
	implements Externalizable, ProcessCallable<Serializable> {

	/**
	 * The empty constructor is required by {@link Externalizable}. Do not use
	 * this for any other purpose.
	 */
	public ServiceMethodProcessCallable() {
	}

	public ServiceMethodProcessCallable(MethodHandler methodHandler) {
		_methodHandler = methodHandler;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			_userId = permissionChecker.getUserId();
		}
	}

	@Override
	public Serializable call() throws ProcessException {
		String oldName = PrincipalThreadLocal.getName();
		PermissionChecker oldPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (_userId != 0) {
				PrincipalThreadLocal.setName(_userId);

				User user = UserLocalServiceUtil.fetchUser(_userId);

				if (user != null) {
					PermissionChecker permissionChecker =
						PermissionCheckerFactoryUtil.create(user);

					PermissionThreadLocal.setPermissionChecker(
						permissionChecker);
				}
			}

			return (Serializable)_methodHandler.invoke();
		}
		catch (Exception e) {
			throw new ProcessException(e);
		}
		finally {
			PrincipalThreadLocal.setName(oldName);
			PermissionThreadLocal.setPermissionChecker(oldPermissionChecker);
		}
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_methodHandler = (MethodHandler)objectInput.readObject();
		_userId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeObject(_methodHandler);
		objectOutput.writeLong(_userId);
	}

	private MethodHandler _methodHandler;
	private long _userId;

}