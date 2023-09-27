/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.qualifier.service.http;

import com.liferay.commerce.qualifier.service.CommerceQualifierEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceQualifierEntryServiceUtil</code> service
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
 * @author Riccardo Alberti
 * @generated
 */
public class CommerceQualifierEntryServiceHttp {

	public static com.liferay.commerce.qualifier.model.CommerceQualifierEntry
			addCommerceQualifierEntry(
				HttpPrincipal httpPrincipal, String sourceClassName,
				long sourceClassPK, String sourceCommerceQualifierMetadataKey,
				String targetClassName, long targetClassPK,
				String targetCommerceQualifierMetadataKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"addCommerceQualifierEntry",
				_addCommerceQualifierEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceClassName, sourceClassPK,
				sourceCommerceQualifierMetadataKey, targetClassName,
				targetClassPK, targetCommerceQualifierMetadataKey);

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

			return (com.liferay.commerce.qualifier.model.CommerceQualifierEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.qualifier.model.CommerceQualifierEntry
			deleteCommerceQualifierEntry(
				HttpPrincipal httpPrincipal, long commerceQualifierEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"deleteCommerceQualifierEntry",
				_deleteCommerceQualifierEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceQualifierEntryId);

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

			return (com.liferay.commerce.qualifier.model.CommerceQualifierEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteSourceCommerceQualifierEntries(
			HttpPrincipal httpPrincipal, String sourceClassName,
			long sourceClassPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"deleteSourceCommerceQualifierEntries",
				_deleteSourceCommerceQualifierEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceClassName, sourceClassPK);

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

	public static void deleteTargetCommerceQualifierEntries(
			HttpPrincipal httpPrincipal, String targetClassName,
			long targetClassPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"deleteTargetCommerceQualifierEntries",
				_deleteTargetCommerceQualifierEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, targetClassName, targetClassPK);

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

	public static com.liferay.commerce.qualifier.model.CommerceQualifierEntry
			fetchCommerceQualifierEntry(
				HttpPrincipal httpPrincipal, String sourceClassName,
				long sourceClassPK, String targetClassName, long targetClassPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"fetchCommerceQualifierEntry",
				_fetchCommerceQualifierEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceClassName, sourceClassPK, targetClassName,
				targetClassPK);

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

			return (com.liferay.commerce.qualifier.model.CommerceQualifierEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.qualifier.model.CommerceQualifierEntry
			getCommerceQualifierEntry(
				HttpPrincipal httpPrincipal, long commerceQualifierEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"getCommerceQualifierEntry",
				_getCommerceQualifierEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceQualifierEntryId);

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

			return (com.liferay.commerce.qualifier.model.CommerceQualifierEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.qualifier.model.CommerceQualifierEntry>
				getSourceCommerceQualifierEntries(
					HttpPrincipal httpPrincipal, long companyId,
					String sourceClassName, long sourceClassPK,
					String targetCommerceQualifierMetadataKey, String keywords,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"getSourceCommerceQualifierEntries",
				_getSourceCommerceQualifierEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sourceClassName, sourceClassPK,
				targetCommerceQualifierMetadataKey, keywords, start, end);

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
				<com.liferay.commerce.qualifier.model.CommerceQualifierEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getSourceCommerceQualifierEntriesCount(
			HttpPrincipal httpPrincipal, long companyId, String sourceClassName,
			long sourceClassPK, String targetCommerceQualifierMetadataKey,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"getSourceCommerceQualifierEntriesCount",
				_getSourceCommerceQualifierEntriesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sourceClassName, sourceClassPK,
				targetCommerceQualifierMetadataKey, keywords);

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
		<com.liferay.commerce.qualifier.model.CommerceQualifierEntry>
				getTargetCommerceQualifierEntries(
					HttpPrincipal httpPrincipal, long companyId,
					String sourceCommerceQualifierMetadataKey,
					String targetClassName, long targetClassPK, String keywords,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"getTargetCommerceQualifierEntries",
				_getTargetCommerceQualifierEntriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sourceCommerceQualifierMetadataKey,
				targetClassName, targetClassPK, keywords, start, end);

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
				<com.liferay.commerce.qualifier.model.CommerceQualifierEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getTargetCommerceQualifierEntriesCount(
			HttpPrincipal httpPrincipal, long companyId,
			String sourceCommerceQualifierMetadataKey, String targetClassName,
			long targetClassPK, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceQualifierEntryServiceUtil.class,
				"getTargetCommerceQualifierEntriesCount",
				_getTargetCommerceQualifierEntriesCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sourceCommerceQualifierMetadataKey,
				targetClassName, targetClassPK, keywords);

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
		CommerceQualifierEntryServiceHttp.class);

	private static final Class<?>[] _addCommerceQualifierEntryParameterTypes0 =
		new Class[] {
			String.class, long.class, String.class, String.class, long.class,
			String.class
		};
	private static final Class<?>[]
		_deleteCommerceQualifierEntryParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[]
		_deleteSourceCommerceQualifierEntriesParameterTypes2 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[]
		_deleteTargetCommerceQualifierEntriesParameterTypes3 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[]
		_fetchCommerceQualifierEntryParameterTypes4 = new Class[] {
			String.class, long.class, String.class, long.class
		};
	private static final Class<?>[] _getCommerceQualifierEntryParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getSourceCommerceQualifierEntriesParameterTypes6 = new Class[] {
			long.class, String.class, long.class, String.class, String.class,
			int.class, int.class
		};
	private static final Class<?>[]
		_getSourceCommerceQualifierEntriesCountParameterTypes7 = new Class[] {
			long.class, String.class, long.class, String.class, String.class
		};
	private static final Class<?>[]
		_getTargetCommerceQualifierEntriesParameterTypes8 = new Class[] {
			long.class, String.class, String.class, long.class, String.class,
			int.class, int.class
		};
	private static final Class<?>[]
		_getTargetCommerceQualifierEntriesCountParameterTypes9 = new Class[] {
			long.class, String.class, String.class, long.class, String.class
		};

}