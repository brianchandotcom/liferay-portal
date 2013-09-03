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

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.util.PropsUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class WebDAVMethodFactoryImpl {

	public static Method create(HttpServletRequest request)
		throws WebDAVException {

		return _instance._create(request);
	}

	private WebDAVMethodFactoryImpl() {
		_methods = new HashMap<String, Object>();

		_methods.put("COPY", InstancePool.get(_COPY_METHOD_IMPL));
		_methods.put("DELETE", InstancePool.get(_DELETE_METHOD_IMPL));
		_methods.put("GET", InstancePool.get(_GET_METHOD_IMPL));
		_methods.put("HEAD", InstancePool.get(_HEAD_METHOD_IMPL));
		_methods.put("LOCK", InstancePool.get(_LOCK_METHOD_IMPL));
		_methods.put("MKCOL", InstancePool.get(_MKCOL_METHOD_IMPL));
		_methods.put("MOVE", InstancePool.get(_MOVE_METHOD_IMPL));
		_methods.put("OPTIONS", InstancePool.get(_OPTIONS_METHOD_IMPL));
		_methods.put("PROPFIND", InstancePool.get(_PROPFIND_METHOD_IMPL));
		_methods.put("PROPPATCH", InstancePool.get(_PROPPATCH_METHOD_IMPL));
		_methods.put("PUT", InstancePool.get(_PUT_METHOD_IMPL));
		_methods.put("UNLOCK", InstancePool.get(_UNLOCK_METHOD_IMPL));
	}

	private Method _create(HttpServletRequest request) throws WebDAVException {
		String method = request.getMethod();

		Method methodImpl = (Method)_methods.get(method.toUpperCase());

		if (methodImpl == null) {
			throw new WebDAVException(
				"Method " + method + " is not implemented");
		}

		return methodImpl;
	}

	private static final String _COPY_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".COPY"),
		CopyMethodImpl.class.getName());

	private static final String _DELETE_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".DELETE"),
		DeleteMethodImpl.class.getName());

	private static final String _GET_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".GET"),
		GetMethodImpl.class.getName());

	private static final String _HEAD_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".HEAD"),
		HeadMethodImpl.class.getName());

	private static final String _LOCK_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".LOCK"),
		LockMethodImpl.class.getName());

	private static final String _MKCOL_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".MKCOL"),
		MkcolMethodImpl.class.getName());

	private static final String _MOVE_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".MOVE"),
		MoveMethodImpl.class.getName());

	private static final String _OPTIONS_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".OPTIONS"),
		OptionsMethodImpl.class.getName());

	private static final String _PROPFIND_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".PROPFIND"),
		PropfindMethodImpl.class.getName());

	private static final String _PROPPATCH_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".PROPPATCH"),
		ProppatchMethodImpl.class.getName());

	private static final String _PUT_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".PUT"),
		PutMethodImpl.class.getName());

	private static final String _UNLOCK_METHOD_IMPL = GetterUtil.getString(
		PropsUtil.get(WebDAVMethodFactoryImpl.class.getName() + ".UNLOCK"),
		UnlockMethodImpl.class.getName());

	private static WebDAVMethodFactoryImpl _instance = new WebDAVMethodFactoryImpl();

	private Map<String, Object> _methods;

}