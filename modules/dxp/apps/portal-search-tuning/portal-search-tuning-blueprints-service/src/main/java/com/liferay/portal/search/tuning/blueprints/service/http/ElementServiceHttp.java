/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.search.tuning.blueprints.service.ElementServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>ElementServiceUtil</code> service
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
 * @see ElementServiceSoap
 * @generated
 */
public class ElementServiceHttp {

	public static com.liferay.portal.search.tuning.blueprints.model.Element
			addCompanyElement(
				HttpPrincipal httpPrincipal,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "addCompanyElement",
				_addCompanyElementParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, titleMap, descriptionMap, configuration, type,
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

			return (com.liferay.portal.search.tuning.blueprints.model.Element)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Element
			addGroupElement(
				HttpPrincipal httpPrincipal,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "addGroupElement",
				_addGroupElementParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, titleMap, descriptionMap, configuration, type,
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

			return (com.liferay.portal.search.tuning.blueprints.model.Element)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Element
			deleteElement(HttpPrincipal httpPrincipal, long elementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "deleteElement",
				_deleteElementParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, elementId);

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

			return (com.liferay.portal.search.tuning.blueprints.model.Element)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Element
			getElement(HttpPrincipal httpPrincipal, long elementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "getElement",
				_getElementParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, elementId);

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

			return (com.liferay.portal.search.tuning.blueprints.model.Element)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				HttpPrincipal httpPrincipal, long companyId, int type,
				int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "getGroupElements",
				_getGroupElementsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Element>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				HttpPrincipal httpPrincipal, long companyId, int status,
				int type, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "getGroupElements",
				_getGroupElementsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, status, type, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Element>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				HttpPrincipal httpPrincipal, long companyId, int status,
				int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.Element>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "getGroupElements",
				_getGroupElementsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, status, type, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Element>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				HttpPrincipal httpPrincipal, long companyId, int type,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.Element>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "getGroupElements",
				_getGroupElementsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Element>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupElementsCount(
		HttpPrincipal httpPrincipal, long companyId, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "getGroupElementsCount",
				_getGroupElementsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	public static int getGroupElementsCount(
		HttpPrincipal httpPrincipal, long companyId, int status, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "getGroupElementsCount",
				_getGroupElementsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, status, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	public static com.liferay.portal.search.tuning.blueprints.model.Element
			updateElement(
				HttpPrincipal httpPrincipal, long elementId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ElementServiceUtil.class, "updateElement",
				_updateElementParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, elementId, titleMap, descriptionMap, configuration,
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

			return (com.liferay.portal.search.tuning.blueprints.model.Element)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ElementServiceHttp.class);

	private static final Class<?>[] _addCompanyElementParameterTypes0 =
		new Class[] {
			java.util.Map.class, java.util.Map.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addGroupElementParameterTypes1 =
		new Class[] {
			java.util.Map.class, java.util.Map.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteElementParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getElementParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getGroupElementsParameterTypes4 =
		new Class[] {long.class, int.class, int.class, int.class};
	private static final Class<?>[] _getGroupElementsParameterTypes5 =
		new Class[] {long.class, int.class, int.class, int.class, int.class};
	private static final Class<?>[] _getGroupElementsParameterTypes6 =
		new Class[] {
			long.class, int.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupElementsParameterTypes7 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupElementsCountParameterTypes8 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getGroupElementsCountParameterTypes9 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _updateElementParameterTypes10 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}