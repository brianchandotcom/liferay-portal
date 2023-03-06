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

package com.liferay.object.rest.internal.jaxrs.context.provider.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.internal.deployer.ObjectDefinitionDeployerImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectsContextProviderUtil {

	public static HttpServletRequest getHttpServletRequest(Message message) {
		return (HttpServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");
	}

	public static ObjectDefinition getObjectDefinition(
		Message message,
		ObjectDefinitionDeployerImpl objectDefinitionDeployerImpl,
		Portal portal) {

		long companyId = portal.getCompanyId(getHttpServletRequest(message));

		String restContextPath = (String)message.getContextualProperty(
			"org.apache.cxf.message.Message.BASE_PATH");

		restContextPath = restContextPath.substring(
			restContextPath.indexOf("/o/"));

		restContextPath = StringUtil.removeFirst(restContextPath, "/o");
		restContextPath = StringUtil.replaceLast(restContextPath, '/', "");

		try {
			return objectDefinitionDeployerImpl.getObjectDefinition(
				companyId, restContextPath);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			throw new RuntimeException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectsContextProviderUtil.class);

}