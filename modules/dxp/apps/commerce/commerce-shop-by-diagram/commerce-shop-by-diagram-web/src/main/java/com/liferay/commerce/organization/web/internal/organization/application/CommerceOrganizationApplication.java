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

package com.liferay.commerce.shop.by.diagram.web.internal.organization.application;

import com.liferay.commerce.shop.by.diagram.web.internal.organization.application.context.provider.PaginationContextProvider;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.application.context.provider.SortContextProvider;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.application.context.provider.ThemeDisplayContextProvider;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.resource.CommerceOrganizationResource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/commerce-organization",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=CommerceOrganization.Application",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true", "liferay.oauth2=false"
	},
	service = Application.class
)
public class CommerceOrganizationApplication extends Application {

	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(_commerceOrganizationResource);
		singletons.add(_paginationContextProvider);
		singletons.add(_sortContextProvider);
		singletons.add(_themeDisplayContextProvider);

		return singletons;
	}

	@Reference
	private CommerceOrganizationResource _commerceOrganizationResource;

	@Reference
	private PaginationContextProvider _paginationContextProvider;

	@Reference
	private SortContextProvider _sortContextProvider;

	@Reference
	private ThemeDisplayContextProvider _themeDisplayContextProvider;

}