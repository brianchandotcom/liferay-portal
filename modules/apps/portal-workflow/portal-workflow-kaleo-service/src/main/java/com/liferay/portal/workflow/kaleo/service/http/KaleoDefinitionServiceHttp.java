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

package com.liferay.portal.workflow.kaleo.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>KaleoDefinitionServiceUtil</code> service
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
 * @see KaleoDefinitionServiceSoap
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
public class KaleoDefinitionServiceHttp {

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
			getKaleoDefinitions(
				HttpPrincipal httpPrincipal, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				KaleoDefinitionServiceUtil.class, "getKaleoDefinitions",
				_getKaleoDefinitionsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
			getKaleoDefinitions(
				HttpPrincipal httpPrincipal, long companyId, int start,
				int end) {

		try {
			MethodKey methodKey = new MethodKey(
				KaleoDefinitionServiceUtil.class, "getKaleoDefinitions",
				_getKaleoDefinitionsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionServiceHttp.class);

	private static final Class<?>[] _getKaleoDefinitionsParameterTypes0 =
		new Class[] {int.class, int.class};
	private static final Class<?>[] _getKaleoDefinitionsParameterTypes1 =
		new Class[] {long.class, int.class, int.class};

}