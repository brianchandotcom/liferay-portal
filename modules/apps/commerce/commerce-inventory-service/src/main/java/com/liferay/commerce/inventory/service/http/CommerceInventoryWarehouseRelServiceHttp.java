/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.http;

import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceInventoryWarehouseRelServiceUtil</code> service
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
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryWarehouseRelServiceHttp {

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel
				addCommerceInventoryWarehouseRel(
					HttpPrincipal httpPrincipal, String className, long classPK,
					long commerceInventoryWarehouseId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"addCommerceInventoryWarehouseRel",
				_addCommerceInventoryWarehouseRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commerceInventoryWarehouseId);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryWarehouseRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceInventoryWarehouseRel(
			HttpPrincipal httpPrincipal, long commerceInventoryWarehouseRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"deleteCommerceInventoryWarehouseRel",
				_deleteCommerceInventoryWarehouseRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseRelId);

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

	public static void deleteCommerceInventoryWarehouseRels(
			HttpPrincipal httpPrincipal, String className,
			long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"deleteCommerceInventoryWarehouseRels",
				_deleteCommerceInventoryWarehouseRelsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, commerceInventoryWarehouseId);

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

	public static void
			deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseId(
				HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseId",
				_deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseIdParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId);

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

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel
				fetchCommerceInventoryWarehouseRel(
					HttpPrincipal httpPrincipal, String className, long classPK,
					long commerceInventoryWarehouseId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"fetchCommerceInventoryWarehouseRel",
				_fetchCommerceInventoryWarehouseRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commerceInventoryWarehouseId);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryWarehouseRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getAccountEntryCommerceInventoryWarehouseRels(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, String keywords,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getAccountEntryCommerceInventoryWarehouseRels",
				_getAccountEntryCommerceInventoryWarehouseRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, keywords, start, end);

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

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getAccountEntryCommerceInventoryWarehouseRelsCount(
			HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getAccountEntryCommerceInventoryWarehouseRelsCount",
				_getAccountEntryCommerceInventoryWarehouseRelsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, keywords);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getAccountGroupCommerceInventoryWarehouseRels(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, String keywords,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getAccountGroupCommerceInventoryWarehouseRels",
				_getAccountGroupCommerceInventoryWarehouseRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, keywords, start, end);

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

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getAccountGroupCommerceInventoryWarehouseRelsCount(
			HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getAccountGroupCommerceInventoryWarehouseRelsCount",
				_getAccountGroupCommerceInventoryWarehouseRelsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, keywords);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel
				getCommerceInventoryWarehouseRel(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceInventoryWarehouseRel",
				_getCommerceInventoryWarehouseRelParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseRelId);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryWarehouseRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getCommerceInventoryWarehouseRels(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceInventoryWarehouseRels",
				_getCommerceInventoryWarehouseRelsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId);

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

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getCommerceInventoryWarehouseRels(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.inventory.model.
							CommerceInventoryWarehouseRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceInventoryWarehouseRels",
				_getCommerceInventoryWarehouseRelsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, start, end,
				orderByComparator);

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

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getCommerceInventoryWarehouseRels(
					HttpPrincipal httpPrincipal, String className,
					long commerceInventoryWarehouseId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceInventoryWarehouseRels",
				_getCommerceInventoryWarehouseRelsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, commerceInventoryWarehouseId);

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

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getCommerceInventoryWarehouseRels(
					HttpPrincipal httpPrincipal, String className,
					long commerceInventoryWarehouseId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.inventory.model.
							CommerceInventoryWarehouseRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceInventoryWarehouseRels",
				_getCommerceInventoryWarehouseRelsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, commerceInventoryWarehouseId, start, end,
				orderByComparator);

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

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceInventoryWarehouseRelsCount(
			HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceInventoryWarehouseRelsCount",
				_getCommerceInventoryWarehouseRelsCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceInventoryWarehouseRelsCount(
			HttpPrincipal httpPrincipal, String className,
			long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceInventoryWarehouseRelsCount",
				_getCommerceInventoryWarehouseRelsCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, commerceInventoryWarehouseId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getCommerceOrderTypeCommerceInventoryWarehouseRels(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, String keywords,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceOrderTypeCommerceInventoryWarehouseRels",
				_getCommerceOrderTypeCommerceInventoryWarehouseRelsParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, keywords, start, end);

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

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceOrderTypeCommerceInventoryWarehouseRelsCount(
			HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseRelServiceUtil.class,
				"getCommerceOrderTypeCommerceInventoryWarehouseRelsCount",
				_getCommerceOrderTypeCommerceInventoryWarehouseRelsCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, keywords);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceInventoryWarehouseRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceInventoryWarehouseRelParameterTypes0 = new Class[] {
			String.class, long.class, long.class
		};
	private static final Class<?>[]
		_deleteCommerceInventoryWarehouseRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceInventoryWarehouseRelsParameterTypes2 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[]
		_deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseIdParameterTypes3 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommerceInventoryWarehouseRelParameterTypes4 = new Class[] {
			String.class, long.class, long.class
		};
	private static final Class<?>[]
		_getAccountEntryCommerceInventoryWarehouseRelsParameterTypes5 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getAccountEntryCommerceInventoryWarehouseRelsCountParameterTypes6 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getAccountGroupCommerceInventoryWarehouseRelsParameterTypes7 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getAccountGroupCommerceInventoryWarehouseRelsCountParameterTypes8 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseRelParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseRelsParameterTypes10 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseRelsParameterTypes11 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseRelsParameterTypes12 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseRelsParameterTypes13 = new Class[] {
			String.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseRelsCountParameterTypes14 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseRelsCountParameterTypes15 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[]
		_getCommerceOrderTypeCommerceInventoryWarehouseRelsParameterTypes16 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceOrderTypeCommerceInventoryWarehouseRelsCountParameterTypes17 =
			new Class[] {long.class, String.class};

}