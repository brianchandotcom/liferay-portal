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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.PortalServiceUtil;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * Provides the HTTP utility for the
 * {@link PortalServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see PortalServiceSoap
 * @see HttpPrincipal
 * @see PortalServiceUtil
 * @generated
 */
@ProviderType
public class PortalServiceHttp {
	public static java.lang.String getAutoDeployDirectory(
		HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"getAutoDeployDirectory",
					_getAutoDeployDirectoryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getBuildNumber(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"getBuildNumber", _getBuildNumberParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void testAddClassName_Rollback(HttpPrincipal httpPrincipal,
		java.lang.String classNameValue) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testAddClassName_Rollback",
					_testAddClassName_RollbackParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					classNameValue);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void testAddClassName_Success(HttpPrincipal httpPrincipal,
		java.lang.String classNameValue) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testAddClassName_Success",
					_testAddClassName_SuccessParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					classNameValue);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void testAddClassNameAndTestTransactionPortletBar_PortalRollback(
		HttpPrincipal httpPrincipal, java.lang.String transactionPortletBarText) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testAddClassNameAndTestTransactionPortletBar_PortalRollback",
					_testAddClassNameAndTestTransactionPortletBar_PortalRollbackParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					transactionPortletBarText);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void testAddClassNameAndTestTransactionPortletBar_PortletRollback(
		HttpPrincipal httpPrincipal, java.lang.String transactionPortletBarText) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testAddClassNameAndTestTransactionPortletBar_PortletRollback",
					_testAddClassNameAndTestTransactionPortletBar_PortletRollbackParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					transactionPortletBarText);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void testAddClassNameAndTestTransactionPortletBar_Success(
		HttpPrincipal httpPrincipal, java.lang.String transactionPortletBarText) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testAddClassNameAndTestTransactionPortletBar_Success",
					_testAddClassNameAndTestTransactionPortletBar_SuccessParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					transactionPortletBarText);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void testAutoSyncHibernateSessionStateOnTxCreation(
		HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testAutoSyncHibernateSessionStateOnTxCreation",
					_testAutoSyncHibernateSessionStateOnTxCreationParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void testDeleteClassName(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testDeleteClassName", _testDeleteClassNameParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int testGetBuildNumber(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testGetBuildNumber", _testGetBuildNumberParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void testGetUserId(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testGetUserId", _testGetUserIdParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean testHasClassName(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(PortalServiceUtil.class,
					"testHasClassName", _testHasClassNameParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortalServiceHttp.class);
	private static final Class<?>[] _getAutoDeployDirectoryParameterTypes0 = new Class[] {
			
		};
	private static final Class<?>[] _getBuildNumberParameterTypes1 = new Class[] {
			
		};
	private static final Class<?>[] _testAddClassName_RollbackParameterTypes2 = new Class[] {
			java.lang.String.class
		};
	private static final Class<?>[] _testAddClassName_SuccessParameterTypes3 = new Class[] {
			java.lang.String.class
		};
	private static final Class<?>[] _testAddClassNameAndTestTransactionPortletBar_PortalRollbackParameterTypes4 =
		new Class[] { java.lang.String.class };
	private static final Class<?>[] _testAddClassNameAndTestTransactionPortletBar_PortletRollbackParameterTypes5 =
		new Class[] { java.lang.String.class };
	private static final Class<?>[] _testAddClassNameAndTestTransactionPortletBar_SuccessParameterTypes6 =
		new Class[] { java.lang.String.class };
	private static final Class<?>[] _testAutoSyncHibernateSessionStateOnTxCreationParameterTypes7 =
		new Class[] {  };
	private static final Class<?>[] _testDeleteClassNameParameterTypes8 = new Class[] {
			
		};
	private static final Class<?>[] _testGetBuildNumberParameterTypes9 = new Class[] {
			
		};
	private static final Class<?>[] _testGetUserIdParameterTypes10 = new Class[] {
			
		};
	private static final Class<?>[] _testHasClassNameParameterTypes11 = new Class[] {
			
		};
}