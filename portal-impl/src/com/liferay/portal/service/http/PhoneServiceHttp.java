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
import com.liferay.portal.service.PhoneServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link PhoneServiceUtil} service utility. The
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
 * @see PhoneServiceSoap
 * @see HttpPrincipal
 * @see PhoneServiceUtil
 * @generated
 */
@ProviderType
public class PhoneServiceHttp {
	public static com.liferay.portal.model.Phone addPhone(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK,
		java.lang.String number, java.lang.String extension, long typeId,
		boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PhoneServiceUtil.class,
					"addPhone", _addPhoneParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, number, extension, typeId, primary,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Phone)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deletePhone(HttpPrincipal httpPrincipal, long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PhoneServiceUtil.class,
					"deletePhone", _deletePhoneParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, phoneId);

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

	public static com.liferay.portal.model.Phone getPhone(
		HttpPrincipal httpPrincipal, long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PhoneServiceUtil.class,
					"getPhone", _getPhoneParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, phoneId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Phone)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PhoneServiceUtil.class,
					"getPhones", _getPhonesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.model.Phone>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Phone updatePhone(
		HttpPrincipal httpPrincipal, long phoneId, java.lang.String number,
		java.lang.String extension, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PhoneServiceUtil.class,
					"updatePhone", _updatePhoneParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, phoneId,
					number, extension, typeId, primary);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Phone)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PhoneServiceHttp.class);
	private static final Class<?>[] _addPhoneParameterTypes0 = new Class[] {
			java.lang.String.class, long.class, java.lang.String.class,
			java.lang.String.class, long.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deletePhoneParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getPhoneParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getPhonesParameterTypes3 = new Class[] {
			java.lang.String.class, long.class
		};
	private static final Class<?>[] _updatePhoneParameterTypes4 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			long.class, boolean.class
		};
}