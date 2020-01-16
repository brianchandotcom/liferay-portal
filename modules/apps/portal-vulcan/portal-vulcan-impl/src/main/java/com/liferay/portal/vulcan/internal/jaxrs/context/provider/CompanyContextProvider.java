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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.Portal;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Alejandro Hernández
 * @author Cristina González
 */
@Provider
public class CompanyContextProvider implements ContextProvider<Company> {

	public CompanyContextProvider(Portal portal) {
		_portal = portal;
	}

	@Override
	public Company createContext(Message message) {
		try {
			return _portal.getCompany(
				ContextProviderUtil.getHttpServletRequest(message));
		}
		catch (PortalException portalException) {
			throw new ServerErrorException(500, portalException);
		}
	}

	private final Portal _portal;

}