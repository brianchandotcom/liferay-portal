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

package com.liferay.oauth2.provider.rest.internal.endpoint.liferay;

import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRESTEndpointConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.security.Principal;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;
import org.apache.cxf.rs.security.oauth2.provider.SubjectCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = SubjectCreator.class)
public class LiferaySubjectCreator implements SubjectCreator {

	@Override
	public UserSubject createUserSubject(
			MessageContext messageContext,
			MultivaluedMap<String, String> params)
		throws OAuthServiceException {

		SecurityContext securityContext = messageContext.getSecurityContext();

		Principal userPrincipal = securityContext.getUserPrincipal();

		try {
			User user = _userLocalService.getUser(
				GetterUtil.getLong(userPrincipal.getName()));

			UserSubject userSubject = new UserSubject(
				user.getLogin(), String.valueOf(user.getUserId()));

			Map<String, String> properties = userSubject.getProperties();

			properties.put(
				OAuth2ProviderRESTEndpointConstants.PROPERTY_KEY_COMPANY_ID,
				String.valueOf(user.getCompanyId()));

			return userSubject;
		}
		catch (PortalException pe) {
			throw new OAuthServiceException(pe);
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}