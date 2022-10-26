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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.PortalServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>PortalServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortalServiceHttp {

	public static String getAutoDeployDirectory(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "getAutoDeployDirectory",
				_getAutoDeployDirectoryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getBuildNumber(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "getBuildNumber",
				_getBuildNumberParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static String getVersion(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "getVersion",
				_getVersionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void testAddClassName_Rollback(
		HttpPrincipal httpPrincipal, String classNameValue) {

		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "testAddClassName_Rollback",
				_testAddClassName_RollbackParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameValue);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void testAddClassName_Success(
		HttpPrincipal httpPrincipal, String classNameValue) {

		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "testAddClassName_Success",
				_testAddClassName_SuccessParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameValue);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void testAutoSyncHibernateSessionStateOnTxCreation(
		HttpPrincipal httpPrincipal) {

		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class,
				"testAutoSyncHibernateSessionStateOnTxCreation",
				_testAutoSyncHibernateSessionStateOnTxCreationParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void testDeleteClassName(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "testDeleteClassName",
				_testDeleteClassNameParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int testGetBuildNumber(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "testGetBuildNumber",
				_testGetBuildNumberParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void testGetUserId(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "testGetUserId",
				_testGetUserIdParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean testHasClassName(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(
				PortalServiceUtil.class, "testHasClassName",
				_testHasClassNameParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortalServiceHttp.class);

	private static final Class<?>[] _getAutoDeployDirectoryParameterTypes0 =
		new Class[] {};
	private static final Class<?>[] _getBuildNumberParameterTypes1 =
		new Class[] {};
	private static final Class<?>[] _getVersionParameterTypes2 = new Class[] {};
	private static final Class<?>[] _testAddClassName_RollbackParameterTypes3 =
		new Class[] {String.class};
	private static final Class<?>[] _testAddClassName_SuccessParameterTypes4 =
		new Class[] {String.class};
	private static final Class<?>[]
		_testAutoSyncHibernateSessionStateOnTxCreationParameterTypes5 =
			new Class[] {};
	private static final Class<?>[] _testDeleteClassNameParameterTypes6 =
		new Class[] {};
	private static final Class<?>[] _testGetBuildNumberParameterTypes7 =
		new Class[] {};
	private static final Class<?>[] _testGetUserIdParameterTypes8 =
		new Class[] {};
	private static final Class<?>[] _testHasClassNameParameterTypes9 =
		new Class[] {};

}