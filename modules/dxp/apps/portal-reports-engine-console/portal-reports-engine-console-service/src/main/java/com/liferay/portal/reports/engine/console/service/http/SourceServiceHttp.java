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

package com.liferay.portal.reports.engine.console.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.reports.engine.console.service.SourceServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link SourceServiceUtil} service utility. The
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
 * @author Brian Wing Shun Chan
 * @see SourceServiceSoap
 * @see HttpPrincipal
 * @see SourceServiceUtil
 * @generated
 */
@ProviderType
public class SourceServiceHttp {
	public static com.liferay.portal.reports.engine.console.model.Source addSource(
		HttpPrincipal httpPrincipal, long groupId,
		java.util.Map<java.util.Locale, String> nameMap,
		String driverClassName, String driverUrl, String driverUserName,
		String driverPassword,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SourceServiceUtil.class,
					"addSource", _addSourceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					nameMap, driverClassName, driverUrl, driverUserName,
					driverPassword, serviceContext);

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

			return (com.liferay.portal.reports.engine.console.model.Source)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Source deleteSource(
		HttpPrincipal httpPrincipal, long sourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SourceServiceUtil.class,
					"deleteSource", _deleteSourceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, sourceId);

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

			return (com.liferay.portal.reports.engine.console.model.Source)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Source getSource(
		HttpPrincipal httpPrincipal, long sourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SourceServiceUtil.class,
					"getSource", _getSourceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, sourceId);

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

			return (com.liferay.portal.reports.engine.console.model.Source)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.reports.engine.console.model.Source> getSources(
		HttpPrincipal httpPrincipal, long groupId, String name,
		String driverUrl, boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SourceServiceUtil.class,
					"getSources", _getSourcesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, driverUrl, andSearch, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.portal.reports.engine.console.model.Source>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getSourcesCount(HttpPrincipal httpPrincipal,
		long groupId, String name, String driverUrl, boolean andSearch) {
		try {
			MethodKey methodKey = new MethodKey(SourceServiceUtil.class,
					"getSourcesCount", _getSourcesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, driverUrl, andSearch);

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

	public static com.liferay.portal.reports.engine.console.model.Source updateSource(
		HttpPrincipal httpPrincipal, long sourceId,
		java.util.Map<java.util.Locale, String> nameMap,
		String driverClassName, String driverUrl, String driverUserName,
		String driverPassword,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SourceServiceUtil.class,
					"updateSource", _updateSourceParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					sourceId, nameMap, driverClassName, driverUrl,
					driverUserName, driverPassword, serviceContext);

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

			return (com.liferay.portal.reports.engine.console.model.Source)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SourceServiceHttp.class);
	private static final Class<?>[] _addSourceParameterTypes0 = new Class[] {
			long.class, java.util.Map.class, String.class, String.class,
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSourceParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSourceParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSourcesParameterTypes3 = new Class[] {
			long.class, String.class, String.class, boolean.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getSourcesCountParameterTypes4 = new Class[] {
			long.class, String.class, String.class, boolean.class
		};
	private static final Class<?>[] _updateSourceParameterTypes5 = new Class[] {
			long.class, java.util.Map.class, String.class, String.class,
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}