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
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
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