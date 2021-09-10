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

package com.liferay.object.rest.internal.jaxrs.context.provider;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.internal.deployer.ObjectDefinitionDeployerImpl;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Javier Gamarra
 */
@Provider
public class ObjectDefinitionContextProvider
	implements ContextProvider<ObjectDefinition> {

	public ObjectDefinitionContextProvider(
		ObjectDefinitionDeployerImpl objectDefinitionDeployerImpl,
		Portal portal) {

		_objectDefinitionDeployerImpl = objectDefinitionDeployerImpl;
		_portal = portal;
	}

	@Override
	public ObjectDefinition createContext(Message message) {
		long companyId = _portal.getCompanyId(
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST"));

		String restContextPath = (String)message.getContextualProperty(
			"org.apache.cxf.message.Message.BASE_PATH");

		restContextPath = StringUtil.removeFirst(restContextPath, "/o");
		restContextPath = StringUtil.replaceLast(restContextPath, '/', "");

		return _objectDefinitionDeployerImpl.getObjectDefinition(
			companyId, restContextPath);
	}

	private final ObjectDefinitionDeployerImpl _objectDefinitionDeployerImpl;
	private final Portal _portal;

}