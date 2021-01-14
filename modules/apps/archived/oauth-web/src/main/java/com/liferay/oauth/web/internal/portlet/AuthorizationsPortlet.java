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

package com.liferay.oauth.web.internal.portlet;

import com.liferay.oauth.constants.OAuthPortletKeys;
import com.liferay.oauth.service.OAuthUserService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=oauth-portlet oauth-portlet-authorizations",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=OAuth Authorizations",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/authorizations/",
		"javax.portlet.init-param.view-template=/authorizations/view.jsp",
		"javax.portlet.name=" + OAuthPortletKeys.OAUTH_AUTHORIZATIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class AuthorizationsPortlet extends MVCPortlet {

	public void deleteOAuthUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long oAuthApplicationId = ParamUtil.getLong(
			actionRequest, "oAuthApplicationId");

		_oAuthUserService.deleteOAuthUser(oAuthApplicationId);
	}

	@Reference(unbind = "-")
	protected void setOAuthUserService(OAuthUserService oAuthUserService) {
		_oAuthUserService = oAuthUserService;
	}

	private OAuthUserService _oAuthUserService;

}