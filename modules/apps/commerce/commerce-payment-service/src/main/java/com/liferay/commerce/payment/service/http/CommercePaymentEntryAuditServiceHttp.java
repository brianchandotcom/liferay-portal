/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service.http;

import com.liferay.commerce.payment.service.CommercePaymentEntryAuditServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePaymentEntryAuditServiceUtil</code> service
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
public class CommercePaymentEntryAuditServiceHttp {

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntryAudit>
				getCommercePaymentEntries(
					HttpPrincipal httpPrincipal, long companyId,
					long commercePaymentEntryId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentEntryAudit> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryAuditServiceUtil.class,
				"getCommercePaymentEntries",
				_getCommercePaymentEntriesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, commercePaymentEntryId, start, end,
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
				<com.liferay.commerce.payment.model.CommercePaymentEntryAudit>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntryAudit
			getCommercePaymentEntryAudit(
				HttpPrincipal httpPrincipal, long commercePaymentEntryAuditId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryAuditServiceUtil.class,
				"getCommercePaymentEntryAudit",
				_getCommercePaymentEntryAuditParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentEntryAuditId);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentEntryAudit)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntryAudit> search(
				HttpPrincipal httpPrincipal, long companyId,
				long[] commercePaymentEntryIds, String[] logTypes,
				String keywords, int start, int end, String orderByField,
				boolean reverse)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryAuditServiceUtil.class, "search",
				_searchParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, commercePaymentEntryIds, logTypes,
				keywords, start, end, orderByField, reverse);

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
				<com.liferay.commerce.payment.model.CommercePaymentEntryAudit>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePaymentEntryAuditServiceHttp.class);

	private static final Class<?>[] _getCommercePaymentEntriesParameterTypes0 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommercePaymentEntryAuditParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[] _searchParameterTypes2 = new Class[] {
		long.class, long[].class, String[].class, String.class, int.class,
		int.class, String.class, boolean.class
	};

}