/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @generated
 */
public class AddressServiceHttp {

	public static com.liferay.portal.kernel.model.Address addAddress(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, long listTypeId,
			boolean mailing, boolean primary, String phoneNumber,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "addAddress",
				_addAddressParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, className, classPK, name,
				description, street1, street2, street3, city, zip, regionId,
				countryId, listTypeId, mailing, primary, phoneNumber,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Address>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Address>
			getListTypeAddresses(
				HttpPrincipal httpPrincipal, String className, long classPK,
				long[] listTypeIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "getListTypeAddresses",
				_getListTypeAddressesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, listTypeIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.Address>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Address updateAddress(
			HttpPrincipal httpPrincipal, long addressId, String name,
			String description, String street1, String street2, String street3,
			String city, String zip, long regionId, long countryId,
			long listTypeId, boolean mailing, boolean primary,
			String phoneNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "updateAddress",
				_updateAddressParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, addressId, name, description, street1, street2,
				street3, city, zip, regionId, countryId, listTypeId, mailing,
				primary, phoneNumber);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Address
			updateExternalReferenceCode(
				HttpPrincipal httpPrincipal,
				com.liferay.portal.kernel.model.Address address,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "updateExternalReferenceCode",
				_updateExternalReferenceCodeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, address, externalReferenceCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Address
			updateExternalReferenceCode(
				HttpPrincipal httpPrincipal, long addressId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AddressServiceUtil.class, "updateExternalReferenceCode",
				_updateExternalReferenceCodeParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, addressId, externalReferenceCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AddressServiceHttp.class);

	private static final Class<?>[] _addAddressParameterTypes0 = new Class[] {
		String.class, String.class, long.class, String.class, String.class,
		String.class, String.class, String.class, String.class, String.class,
		long.class, long.class, long.class, boolean.class, boolean.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteAddressParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getAddressParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getAddressesParameterTypes3 = new Class[] {
		String.class, long.class
	};
	private static final Class<?>[] _getListTypeAddressesParameterTypes4 =
		new Class[] {String.class, long.class, long[].class};
	private static final Class<?>[] _updateAddressParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, long.class, long.class,
			long.class, boolean.class, boolean.class, String.class
		};
	private static final Class<?>[]
		_updateExternalReferenceCodeParameterTypes6 = new Class[] {
			com.liferay.portal.kernel.model.Address.class, String.class
		};
	private static final Class<?>[]
		_updateExternalReferenceCodeParameterTypes7 = new Class[] {
			long.class, String.class
		};

}