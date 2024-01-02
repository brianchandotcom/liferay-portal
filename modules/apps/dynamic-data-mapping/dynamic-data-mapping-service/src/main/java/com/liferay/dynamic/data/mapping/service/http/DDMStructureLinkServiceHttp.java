/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.http;

import com.liferay.dynamic.data.mapping.service.DDMStructureLinkServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMStructureLinkServiceUtil</code> service
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
 * @generated
 */
public class DDMStructureLinkServiceHttp {

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructureLink>
			getStructureLinks(
				HttpPrincipal httpPrincipal, long classNameId, long classPK,
				long[] groupIds, String keywords, String resourceClassName,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructureLink>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureLinkServiceUtil.class, "getStructureLinks",
				_getStructureLinksParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, classPK, groupIds, keywords,
				resourceClassName, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMStructureLink>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getStructureLinksCount(
		HttpPrincipal httpPrincipal, long classNameId, long classPK,
		long[] groupIds, String keywords, String resourceClassName) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureLinkServiceUtil.class, "getStructureLinksCount",
				_getStructureLinksCountParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, classPK, groupIds, keywords,
				resourceClassName);

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

	private static Log _log = LogFactoryUtil.getLog(
		DDMStructureLinkServiceHttp.class);

	private static final Class<?>[] _getStructureLinksParameterTypes0 =
		new Class[] {
			long.class, long.class, long[].class, String.class, String.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getStructureLinksCountParameterTypes1 =
		new Class[] {
			long.class, long.class, long[].class, String.class, String.class
		};

}