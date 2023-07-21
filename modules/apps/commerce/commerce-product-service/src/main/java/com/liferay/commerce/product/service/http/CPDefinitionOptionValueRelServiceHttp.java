/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPDefinitionOptionValueRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionOptionValueRelServiceUtil</code> service
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
public class CPDefinitionOptionValueRelServiceHttp {

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel
			addCPDefinitionOptionValueRel(
				HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId,
				String key, java.util.Map<java.util.Locale, String> nameMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"addCPDefinitionOptionValueRel",
				_addCPDefinitionOptionValueRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionRelId, key, nameMap, priority,
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

			return (com.liferay.commerce.product.model.
				CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel
			deleteCPDefinitionOptionValueRel(
				HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"deleteCPDefinitionOptionValueRel",
				_deleteCPDefinitionOptionValueRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel
			fetchCPDefinitionOptionValueRel(
				HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"fetchCPDefinitionOptionValueRel",
				_fetchCPDefinitionOptionValueRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel
			fetchCPDefinitionOptionValueRel(
				HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId,
				String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"fetchCPDefinitionOptionValueRel",
				_fetchCPDefinitionOptionValueRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionRelId, key);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel
			getCPDefinitionOptionValueRel(
				HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"getCPDefinitionOptionValueRel",
				_getCPDefinitionOptionValueRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>
				getCPDefinitionOptionValueRels(
					HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"getCPDefinitionOptionValueRels",
				_getCPDefinitionOptionValueRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionRelId, start, end);

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
				<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>
				getCPDefinitionOptionValueRels(
					HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.
							CPDefinitionOptionValueRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"getCPDefinitionOptionValueRels",
				_getCPDefinitionOptionValueRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionRelId, start, end,
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
				<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>
				getCPDefinitionOptionValueRels(
					HttpPrincipal httpPrincipal, long groupId, String key,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"getCPDefinitionOptionValueRels",
				_getCPDefinitionOptionValueRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, key, start, end);

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
				<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionOptionValueRelsCount(
			HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"getCPDefinitionOptionValueRelsCount",
				_getCPDefinitionOptionValueRelsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionRelId);

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

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel
			resetCPInstanceCPDefinitionOptionValueRel(
				HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"resetCPInstanceCPDefinitionOptionValueRel",
				_resetCPInstanceCPDefinitionOptionValueRelParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>
				searchCPDefinitionOptionValueRels(
					HttpPrincipal httpPrincipal, long companyId, long groupId,
					long cpDefinitionOptionRelId, String keywords, int start,
					int end, com.liferay.portal.kernel.search.Sort[] sorts)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"searchCPDefinitionOptionValueRels",
				_searchCPDefinitionOptionValueRelsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, cpDefinitionOptionRelId,
				keywords, start, end, sorts);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult
				<com.liferay.commerce.product.model.CPDefinitionOptionValueRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCPDefinitionOptionValueRelsCount(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long cpDefinitionOptionRelId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"searchCPDefinitionOptionValueRelsCount",
				_searchCPDefinitionOptionValueRelsCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, cpDefinitionOptionRelId,
				keywords);

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

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel
			updateCPDefinitionOptionValueRel(
				HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId,
				long cpInstanceId, String key,
				java.util.Map<java.util.Locale, String> nameMap,
				boolean preselected, java.math.BigDecimal price,
				double priority, java.math.BigDecimal quantity,
				String unitOfMeasureKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"updateCPDefinitionOptionValueRel",
				_updateCPDefinitionOptionValueRelParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionValueRelId, cpInstanceId, key,
				nameMap, preselected, price, priority, quantity,
				unitOfMeasureKey, serviceContext);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel
			updateCPDefinitionOptionValueRelPreselected(
				HttpPrincipal httpPrincipal, long cpDefinitionOptionValueRelId,
				boolean preselected)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionOptionValueRelServiceUtil.class,
				"updateCPDefinitionOptionValueRelPreselected",
				_updateCPDefinitionOptionValueRelPreselectedParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionOptionValueRelId, preselected);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionOptionValueRelServiceHttp.class);

	private static final Class<?>[]
		_addCPDefinitionOptionValueRelParameterTypes0 = new Class[] {
			long.class, String.class, java.util.Map.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCPDefinitionOptionValueRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCPDefinitionOptionValueRelParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCPDefinitionOptionValueRelParameterTypes3 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCPDefinitionOptionValueRelParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCPDefinitionOptionValueRelsParameterTypes5 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCPDefinitionOptionValueRelsParameterTypes6 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCPDefinitionOptionValueRelsParameterTypes7 = new Class[] {
			long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCPDefinitionOptionValueRelsCountParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_resetCPInstanceCPDefinitionOptionValueRelParameterTypes9 =
			new Class[] {long.class};
	private static final Class<?>[]
		_searchCPDefinitionOptionValueRelsParameterTypes10 = new Class[] {
			long.class, long.class, long.class, String.class, int.class,
			int.class, com.liferay.portal.kernel.search.Sort[].class
		};
	private static final Class<?>[]
		_searchCPDefinitionOptionValueRelsCountParameterTypes11 = new Class[] {
			long.class, long.class, long.class, String.class
		};
	private static final Class<?>[]
		_updateCPDefinitionOptionValueRelParameterTypes12 = new Class[] {
			long.class, long.class, String.class, java.util.Map.class,
			boolean.class, java.math.BigDecimal.class, double.class,
			java.math.BigDecimal.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCPDefinitionOptionValueRelPreselectedParameterTypes13 =
			new Class[] {long.class, boolean.class};

}