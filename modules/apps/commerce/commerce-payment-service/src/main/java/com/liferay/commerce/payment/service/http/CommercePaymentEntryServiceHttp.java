/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service.http;

import com.liferay.commerce.payment.service.CommercePaymentEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePaymentEntryServiceUtil</code> service
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
public class CommercePaymentEntryServiceHttp {

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			addCommercePaymentEntry(
				HttpPrincipal httpPrincipal, long classNameId, long classPK,
				long commerceChannelId, java.math.BigDecimal amount,
				String callbackURL, String cancelURL, String currencyCode,
				String languageId, String note, String payload,
				String paymentIntegrationKey, int paymentIntegrationType,
				String reasonKey, String transactionCode, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"addCommercePaymentEntry",
				_addCommercePaymentEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, classPK, commerceChannelId, amount,
				callbackURL, cancelURL, currencyCode, languageId, note, payload,
				paymentIntegrationKey, paymentIntegrationType, reasonKey,
				transactionCode, type, serviceContext);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			addOrUpdateCommercePaymentEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long classNameId, long classPK, long commerceChannelId,
				java.math.BigDecimal amount, String callbackURL,
				String cancelURL, String currencyCode, String errorMessages,
				String languageId, String note, String payload,
				String paymentIntegrationKey, int paymentIntegrationType,
				int paymentStatus, String reasonKey, String redirectURL,
				String transactionCode, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"addOrUpdateCommercePaymentEntry",
				_addOrUpdateCommercePaymentEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, classNameId, classPK,
				commerceChannelId, amount, callbackURL, cancelURL, currencyCode,
				errorMessages, languageId, note, payload, paymentIntegrationKey,
				paymentIntegrationType, paymentStatus, reasonKey, redirectURL,
				transactionCode, type, serviceContext);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			deleteCommercePaymentEntry(
				HttpPrincipal httpPrincipal, long commercePaymentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"deleteCommercePaymentEntry",
				_deleteCommercePaymentEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentEntryId);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			fetchCommercePaymentEntry(
				HttpPrincipal httpPrincipal, long commercePaymentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"fetchCommercePaymentEntry",
				_fetchCommercePaymentEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentEntryId);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			fetchCommercePaymentEntryByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"fetchCommercePaymentEntryByExternalReferenceCode",
				_fetchCommercePaymentEntryByExternalReferenceCodeParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry>
				getCommercePaymentEntries(
					HttpPrincipal httpPrincipal, long companyId,
					long classNameId, long classPK, int type, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"getCommercePaymentEntries",
				_getCommercePaymentEntriesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, classNameId, classPK, type, start, end,
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
				<com.liferay.commerce.payment.model.CommercePaymentEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry>
				getCommercePaymentEntries(
					HttpPrincipal httpPrincipal, long companyId,
					long classNameId, long classPK, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"getCommercePaymentEntries",
				_getCommercePaymentEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, classNameId, classPK, start, end,
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
				<com.liferay.commerce.payment.model.CommercePaymentEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePaymentEntriesCount(
			HttpPrincipal httpPrincipal, long companyId, long classNameId,
			long classPK, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"getCommercePaymentEntriesCount",
				_getCommercePaymentEntriesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, classNameId, classPK, type);

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

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			getCommercePaymentEntry(
				HttpPrincipal httpPrincipal, long commercePaymentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"getCommercePaymentEntry",
				_getCommercePaymentEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentEntryId);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.math.BigDecimal getRefundedAmount(
			HttpPrincipal httpPrincipal, long companyId, long classNameId,
			long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class, "getRefundedAmount",
				_getRefundedAmountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, classNameId, classPK);

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

			return (java.math.BigDecimal)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry> search(
				HttpPrincipal httpPrincipal, long companyId,
				long[] classNameIds, long[] classPKs, String[] currencyCodes,
				String keywords, String[] paymentMethodNames,
				int[] paymentStatuses, boolean excludeStatuses, int start,
				int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class, "search",
				_searchParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, classNameIds, classPKs, currencyCodes,
				keywords, paymentMethodNames, paymentStatuses, excludeStatuses,
				start, end, sort);

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
				<com.liferay.commerce.payment.model.CommercePaymentEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			updateCommercePaymentEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long commercePaymentEntryId, long commerceChannelId,
				java.math.BigDecimal amount, String callbackURL,
				String cancelURL, String currencyCode, String errorMessages,
				String languageId, String note, String payload,
				String paymentIntegrationKey, int paymentIntegrationType,
				int paymentStatus, String reasonKey, String redirectURL,
				String transactionCode, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"updateCommercePaymentEntry",
				_updateCommercePaymentEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commercePaymentEntryId,
				commerceChannelId, amount, callbackURL, cancelURL, currencyCode,
				errorMessages, languageId, note, payload, paymentIntegrationKey,
				paymentIntegrationType, paymentStatus, reasonKey, redirectURL,
				transactionCode, type);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			updateExternalReferenceCode(
				HttpPrincipal httpPrincipal, long commercePaymentEntryId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class,
				"updateExternalReferenceCode",
				_updateExternalReferenceCodeParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentEntryId, externalReferenceCode);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			updateNote(
				HttpPrincipal httpPrincipal, long commercePaymentEntryId,
				String note)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class, "updateNote",
				_updateNoteParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentEntryId, note);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.payment.model.CommercePaymentEntry
			updateReasonKey(
				HttpPrincipal httpPrincipal, long commercePaymentEntryId,
				String reasonKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentEntryServiceUtil.class, "updateReasonKey",
				_updateReasonKeyParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentEntryId, reasonKey);

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

			return (com.liferay.commerce.payment.model.CommercePaymentEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePaymentEntryServiceHttp.class);

	private static final Class<?>[] _addCommercePaymentEntryParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, java.math.BigDecimal.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, int.class, String.class,
			String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_addOrUpdateCommercePaymentEntryParameterTypes1 = new Class[] {
			String.class, long.class, long.class, long.class,
			java.math.BigDecimal.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, int.class, int.class, String.class,
			String.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommercePaymentEntryParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCommercePaymentEntryParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommercePaymentEntryByExternalReferenceCodeParameterTypes4 =
			new Class[] {String.class, long.class};
	private static final Class<?>[] _getCommercePaymentEntriesParameterTypes5 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommercePaymentEntriesParameterTypes6 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommercePaymentEntriesCountParameterTypes7 = new Class[] {
			long.class, long.class, long.class, int.class
		};
	private static final Class<?>[] _getCommercePaymentEntryParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getRefundedAmountParameterTypes9 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _searchParameterTypes10 = new Class[] {
		long.class, long[].class, long[].class, String[].class, String.class,
		String[].class, int[].class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.search.Sort.class
	};
	private static final Class<?>[]
		_updateCommercePaymentEntryParameterTypes11 = new Class[] {
			String.class, long.class, long.class, java.math.BigDecimal.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, int.class,
			int.class, String.class, String.class, String.class, int.class
		};
	private static final Class<?>[]
		_updateExternalReferenceCodeParameterTypes12 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _updateNoteParameterTypes13 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _updateReasonKeyParameterTypes14 =
		new Class[] {long.class, String.class};

}