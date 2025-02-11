/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service.http;

import com.liferay.commerce.tax.service.CommerceTaxCategoryMappingServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceTaxCategoryMappingServiceUtil</code> service
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
 * @author Marco Leo
 * @generated
 */
public class CommerceTaxCategoryMappingServiceHttp {

	public static com.liferay.commerce.tax.model.CommerceTaxCategoryMapping
			addCommerceTaxCategoryMapping(
				HttpPrincipal httpPrincipal, long groupId,
				long commerceTaxMethodId, long cpTaxCategoryId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxCategoryMappingServiceUtil.class,
				"addCommerceTaxCategoryMapping",
				_addCommerceTaxCategoryMappingParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceTaxMethodId, cpTaxCategoryId,
				externalReferenceCode);

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

			return (com.liferay.commerce.tax.model.CommerceTaxCategoryMapping)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceTaxCategoryMapping(
			HttpPrincipal httpPrincipal, long commerceTaxCategoryMappingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxCategoryMappingServiceUtil.class,
				"deleteCommerceTaxCategoryMapping",
				_deleteCommerceTaxCategoryMappingParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTaxCategoryMappingId);

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

	public static com.liferay.commerce.tax.model.CommerceTaxCategoryMapping
			fetchCommerceTaxCategoryMapping(
				HttpPrincipal httpPrincipal, long commerceTaxCategoryMappingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxCategoryMappingServiceUtil.class,
				"fetchCommerceTaxCategoryMapping",
				_fetchCommerceTaxCategoryMappingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTaxCategoryMappingId);

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

			return (com.liferay.commerce.tax.model.CommerceTaxCategoryMapping)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceTaxCategoryMappingCount(
			HttpPrincipal httpPrincipal, long groupId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxCategoryMappingServiceUtil.class,
				"getCommerceTaxCategoryMappingCount",
				_getCommerceTaxCategoryMappingCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceTaxMethodId);

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
		<com.liferay.commerce.tax.model.CommerceTaxCategoryMapping>
				getCommerceTaxCategoryMappings(
					HttpPrincipal httpPrincipal, long groupId,
					long commerceTaxMethodId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.tax.model.
							CommerceTaxCategoryMapping> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxCategoryMappingServiceUtil.class,
				"getCommerceTaxCategoryMappings",
				_getCommerceTaxCategoryMappingsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceTaxMethodId, start, end,
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
				<com.liferay.commerce.tax.model.CommerceTaxCategoryMapping>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.tax.model.CommerceTaxCategoryMapping
			updateCommerceTaxCategoryMapping(
				HttpPrincipal httpPrincipal, long commerceTaxCategoryMappingId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxCategoryMappingServiceUtil.class,
				"updateCommerceTaxCategoryMapping",
				_updateCommerceTaxCategoryMappingParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTaxCategoryMappingId, externalReferenceCode);

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

			return (com.liferay.commerce.tax.model.CommerceTaxCategoryMapping)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceTaxCategoryMappingServiceHttp.class);

	private static final Class<?>[]
		_addCommerceTaxCategoryMappingParameterTypes0 = new Class[] {
			long.class, long.class, long.class, String.class
		};
	private static final Class<?>[]
		_deleteCommerceTaxCategoryMappingParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommerceTaxCategoryMappingParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceTaxCategoryMappingCountParameterTypes3 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_getCommerceTaxCategoryMappingsParameterTypes4 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_updateCommerceTaxCategoryMappingParameterTypes5 = new Class[] {
			long.class, String.class
		};

}