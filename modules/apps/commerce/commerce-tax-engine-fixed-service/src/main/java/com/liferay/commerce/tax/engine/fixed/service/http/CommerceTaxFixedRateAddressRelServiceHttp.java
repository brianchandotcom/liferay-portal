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

package com.liferay.commerce.tax.engine.fixed.service.http;

import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceTaxFixedRateAddressRelServiceUtil</code> service
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
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelServiceSoap
 * @generated
 */
public class CommerceTaxFixedRateAddressRelServiceHttp {

	public static
		com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
					HttpPrincipal httpPrincipal, long groupId,
					long commerceTaxMethodId, long cpTaxCategoryId,
					long countryId, long regionId, String zip, double rate)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxFixedRateAddressRelServiceUtil.class,
				"addCommerceTaxFixedRateAddressRel",
				_addCommerceTaxFixedRateAddressRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceTaxMethodId, cpTaxCategoryId,
				countryId, regionId, zip, rate);

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

			return (com.liferay.commerce.tax.engine.fixed.model.
				CommerceTaxFixedRateAddressRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
					HttpPrincipal httpPrincipal, long commerceTaxMethodId,
					long cpTaxCategoryId, long countryId, long regionId,
					String zip, double rate,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxFixedRateAddressRelServiceUtil.class,
				"addCommerceTaxFixedRateAddressRel",
				_addCommerceTaxFixedRateAddressRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTaxMethodId, cpTaxCategoryId, countryId,
				regionId, zip, rate, serviceContext);

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

			return (com.liferay.commerce.tax.engine.fixed.model.
				CommerceTaxFixedRateAddressRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceTaxFixedRateAddressRel(
			HttpPrincipal httpPrincipal, long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxFixedRateAddressRelServiceUtil.class,
				"deleteCommerceTaxFixedRateAddressRel",
				_deleteCommerceTaxFixedRateAddressRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTaxFixedRateAddressRelId);

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
		com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
					HttpPrincipal httpPrincipal,
					long commerceTaxFixedRateAddressRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxFixedRateAddressRelServiceUtil.class,
				"fetchCommerceTaxFixedRateAddressRel",
				_fetchCommerceTaxFixedRateAddressRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTaxFixedRateAddressRelId);

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

			return (com.liferay.commerce.tax.engine.fixed.model.
				CommerceTaxFixedRateAddressRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel>
					getCommerceTaxMethodFixedRateAddressRels(
						HttpPrincipal httpPrincipal, long groupId,
						long commerceTaxMethodId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.tax.engine.fixed.model.
								CommerceTaxFixedRateAddressRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxFixedRateAddressRelServiceUtil.class,
				"getCommerceTaxMethodFixedRateAddressRels",
				_getCommerceTaxMethodFixedRateAddressRelsParameterTypes4);

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
				<com.liferay.commerce.tax.engine.fixed.model.
					CommerceTaxFixedRateAddressRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceTaxMethodFixedRateAddressRelsCount(
			HttpPrincipal httpPrincipal, long groupId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxFixedRateAddressRelServiceUtil.class,
				"getCommerceTaxMethodFixedRateAddressRelsCount",
				_getCommerceTaxMethodFixedRateAddressRelsCountParameterTypes5);

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

	public static
		com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
					HttpPrincipal httpPrincipal,
					long commerceTaxFixedRateAddressRelId, long countryId,
					long regionId, String zip, double rate)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTaxFixedRateAddressRelServiceUtil.class,
				"updateCommerceTaxFixedRateAddressRel",
				_updateCommerceTaxFixedRateAddressRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTaxFixedRateAddressRelId, countryId,
				regionId, zip, rate);

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

			return (com.liferay.commerce.tax.engine.fixed.model.
				CommerceTaxFixedRateAddressRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceTaxFixedRateAddressRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceTaxFixedRateAddressRelParameterTypes0 = new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			String.class, double.class
		};
	private static final Class<?>[]
		_addCommerceTaxFixedRateAddressRelParameterTypes1 = new Class[] {
			long.class, long.class, long.class, long.class, String.class,
			double.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommerceTaxFixedRateAddressRelParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommerceTaxFixedRateAddressRelParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceTaxMethodFixedRateAddressRelsParameterTypes4 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceTaxMethodFixedRateAddressRelsCountParameterTypes5 =
			new Class[] {long.class, long.class};
	private static final Class<?>[]
		_updateCommerceTaxFixedRateAddressRelParameterTypes6 = new Class[] {
			long.class, long.class, long.class, String.class, double.class
		};

}