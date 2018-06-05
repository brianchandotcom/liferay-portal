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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceAvailabilityEstimateServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceAvailabilityEstimateServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see CommerceAvailabilityEstimateServiceSoap
 * @see HttpPrincipal
 * @see CommerceAvailabilityEstimateServiceUtil
 * @generated
 */
@ProviderType
public class CommerceAvailabilityEstimateServiceHttp {
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate addCommerceAvailabilityEstimate(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityEstimateServiceUtil.class,
					"addCommerceAvailabilityEstimate",
					_addCommerceAvailabilityEstimateParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					titleMap, priority, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceAvailabilityEstimate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceAvailabilityEstimate(
		HttpPrincipal httpPrincipal, long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityEstimateServiceUtil.class,
					"deleteCommerceAvailabilityEstimate",
					_deleteCommerceAvailabilityEstimateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAvailabilityEstimateId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceAvailabilityEstimate getCommerceAvailabilityEstimate(
		HttpPrincipal httpPrincipal, long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityEstimateServiceUtil.class,
					"getCommerceAvailabilityEstimate",
					_getCommerceAvailabilityEstimateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAvailabilityEstimateId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceAvailabilityEstimate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityEstimate> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityEstimateServiceUtil.class,
					"getCommerceAvailabilityEstimates",
					_getCommerceAvailabilityEstimatesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceAvailabilityEstimate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceAvailabilityEstimatesCount(
		HttpPrincipal httpPrincipal, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityEstimateServiceUtil.class,
					"getCommerceAvailabilityEstimatesCount",
					_getCommerceAvailabilityEstimatesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceAvailabilityEstimate updateCommerceAvailabilityEstimate(
		HttpPrincipal httpPrincipal, long commerceAvailabilityEstimateId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityEstimateServiceUtil.class,
					"updateCommerceAvailabilityEstimate",
					_updateCommerceAvailabilityEstimateParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAvailabilityEstimateId, titleMap, priority,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceAvailabilityEstimate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceAvailabilityEstimateServiceHttp.class);
	private static final Class<?>[] _addCommerceAvailabilityEstimateParameterTypes0 =
		new Class[] {
			java.util.Map.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceAvailabilityEstimateParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceAvailabilityEstimateParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceAvailabilityEstimatesParameterTypes3 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceAvailabilityEstimatesCountParameterTypes4 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceAvailabilityEstimateParameterTypes5 =
		new Class[] {
			long.class, java.util.Map.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}