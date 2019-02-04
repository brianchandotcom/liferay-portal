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

package com.liferay.portal.vulcan.internal.context.provider;

import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.internal.context.AcceptLanguageImpl;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Cristina González
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.extension.select=\\(osgi.jaxrs.name=vulcan.AcceptLanguageContextProvider\\))",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=vulcan.AcceptLanguageContextProvider",
	},
	scope = ServiceScope.PROTOTYPE,
	service = ContextProvider.class
)
@Provider
public class AcceptLanguageContextProvider
	implements ContextProvider<AcceptLanguage> {

	@Override
	public AcceptLanguage createContext(Message message) {
		return new AcceptLanguageImpl(
			ContextProviderUtil.getHttpServletRequest(message), _portal);
	}

	@Reference
	private Portal _portal;

}