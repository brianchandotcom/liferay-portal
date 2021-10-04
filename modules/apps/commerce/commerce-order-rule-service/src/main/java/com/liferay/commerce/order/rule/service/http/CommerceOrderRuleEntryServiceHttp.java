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

package com.liferay.commerce.order.rule.service.http;

import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceOrderRuleEntryServiceUtil</code> service
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
 * @see CommerceOrderRuleEntryServiceSoap
 * @generated
 */
public class CommerceOrderRuleEntryServiceHttp {

	public static com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
			addCommerceOrderRuleEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				boolean active, String description, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String name, int priority, String type,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryServiceUtil.class,
				"addCommerceOrderRuleEntry",
				_addCommerceOrderRuleEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, active, description,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, name, priority, type,
				typeSettings, serviceContext);

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

			return (com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
			deleteCommerceOrderRuleEntry(
				HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryServiceUtil.class,
				"deleteCommerceOrderRuleEntry",
				_deleteCommerceOrderRuleEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId);

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

			return (com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>
				getCommerceOrderRuleEntries(
					HttpPrincipal httpPrincipal, long companyId, boolean active,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryServiceUtil.class,
				"getCommerceOrderRuleEntries",
				_getCommerceOrderRuleEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active, start, end);

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
				<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>
				getCommerceOrderRuleEntries(
					HttpPrincipal httpPrincipal, long companyId, boolean active,
					String type, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryServiceUtil.class,
				"getCommerceOrderRuleEntries",
				_getCommerceOrderRuleEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active, type, start, end);

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
				<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>
				getCommerceOrderRuleEntries(
					HttpPrincipal httpPrincipal, long companyId, String type,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryServiceUtil.class,
				"getCommerceOrderRuleEntries",
				_getCommerceOrderRuleEntriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type, start, end);

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
				<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
			updateCommerceOrderRuleEntry(
				HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
				boolean active, String description, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String name, int priority,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryServiceUtil.class,
				"updateCommerceOrderRuleEntry",
				_updateCommerceOrderRuleEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, active, description,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, name, priority, typeSettings,
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

			return (com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceOrderRuleEntryServiceHttp.class);

	private static final Class<?>[] _addCommerceOrderRuleEntryParameterTypes0 =
		new Class[] {
			String.class, boolean.class, String.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class, String.class, int.class,
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommerceOrderRuleEntryParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceOrderRuleEntriesParameterTypes2 = new Class[] {
			long.class, boolean.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceOrderRuleEntriesParameterTypes3 = new Class[] {
			long.class, boolean.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceOrderRuleEntriesParameterTypes4 = new Class[] {
			long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_updateCommerceOrderRuleEntryParameterTypes5 = new Class[] {
			long.class, boolean.class, String.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class, String.class, int.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};

}