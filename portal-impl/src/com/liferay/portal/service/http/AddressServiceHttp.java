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
import com.liferay.portal.kernel.service.AddressServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AddressServiceUtil</code> service
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
 * @see AddressServiceSoap
 * @generated
 */
public class AddressServiceHttp {

	public static com.liferay.portal.kernel.model.Address addAddress(
			HttpPrincipal httpPrincipal, String className, long classPK,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, long typeId,
			boolean mailing, boolean primary,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "addAddress",
				_addAddressParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, street1, street2, street3, city,
				zip, regionId, countryId, typeId, mailing, primary,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteAddress(
			HttpPrincipal httpPrincipal, long addressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "deleteAddress",
				_deleteAddressParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, addressId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Address getAddress(
			HttpPrincipal httpPrincipal, long addressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "getAddress",
				_getAddressParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, addressId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Address>
			getAddresses(
				HttpPrincipal httpPrincipal, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "getAddresses",
				_getAddressesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Address>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Address updateAddress(
			HttpPrincipal httpPrincipal, long addressId, String street1,
			String street2, String street3, String city, String zip,
			long regionId, long countryId, long typeId, boolean mailing,
			boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "updateAddress",
				_updateAddressParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, addressId, street1, street2, street3, city, zip,
				regionId, countryId, typeId, mailing, primary);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AddressServiceHttp.class);

	private static final Class<?>[] _addAddressParameterTypes0 = new Class[] {
		String.class, long.class, String.class, String.class, String.class,
		String.class, String.class, long.class, long.class, long.class,
		boolean.class, boolean.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteAddressParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getAddressParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getAddressesParameterTypes3 = new Class[] {
		String.class, long.class
	};
	private static final Class<?>[] _updateAddressParameterTypes4 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, long.class, long.class, long.class, boolean.class,
			boolean.class
		};

}