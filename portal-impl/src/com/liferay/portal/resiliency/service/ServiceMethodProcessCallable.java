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

package com.liferay.portal.resiliency.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

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
	 * The empty constructor is required by {@link java.io.Externalizable}. Do
	 * not use this for any other purpose.
	 */
	public ServiceMethodProcessCallable() {
	}

	public ServiceMethodProcessCallable(
		String servletContextName, String beanIdentifier,
		MethodHandler methodHandler) {

		_servletContextName = servletContextName;
		_beanIdentifier = beanIdentifier;
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

			Object bean = PortletBeanLocatorUtil.locate(
				_servletContextName, _beanIdentifier);

			return (Serializable)_methodHandler.invoke(bean);
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

		_userId = objectInput.readLong();
		_servletContextName = objectInput.readUTF();
		_beanIdentifier = objectInput.readUTF();
		_methodHandler = (MethodHandler)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(_userId);
		objectOutput.writeUTF(_servletContextName);
		objectOutput.writeUTF(_beanIdentifier);
		objectOutput.writeObject(_methodHandler);
	}

	private String _beanIdentifier;
	private MethodHandler _methodHandler;
	private String _servletContextName;
	private long _userId;

}