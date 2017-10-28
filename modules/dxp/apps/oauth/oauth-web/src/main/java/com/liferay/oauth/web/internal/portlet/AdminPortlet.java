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
import com.liferay.oauth.service.OAuthApplicationService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.InputStream;

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
		"com.liferay.portlet.add-default-resource=false",
		"com.liferay.portlet.css-class-wrapper=oauth-portlet oauth-portlet-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.show-portlet-access-denied=false",
		"javax.portlet.display-name=OAuth Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.always-send-redirect=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/admin/",
		"javax.portlet.init-param.view-template=/admin/view.jsp",
		"javax.portlet.name=" + OAuthPortletKeys.OAUTH_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class AdminPortlet extends MVCPortlet {

	public void addOAuthApplication(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		int accessLevel = ParamUtil.getInteger(actionRequest, "accessType");
		boolean shareableAccessToken = ParamUtil.getBoolean(
			actionRequest, "shareableAccessToken");
		String callbackURI = ParamUtil.getString(actionRequest, "callbackURI");
		String websiteURL = ParamUtil.getString(actionRequest, "websiteURL");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_oAuthApplicationService.addOAuthApplication(
			name, description, accessLevel, shareableAccessToken, callbackURI,
			websiteURL, serviceContext);
	}

	public void deleteOAuthApplication(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long oAuthApplicationId = ParamUtil.getLong(
			actionRequest, "oAuthApplicationId");

		_oAuthApplicationService.deleteOAuthApplication(oAuthApplicationId);
	}

	public void updateLogo(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		long oAuthApplicationId = ParamUtil.getLong(
			actionRequest, "oAuthApplicationId");

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream("fileName")) {

			if (inputStream == null) {
				throw new UploadException();
			}

			_oAuthApplicationService.updateLogo(
				oAuthApplicationId, inputStream);
		}
	}

	public void updateOAuthApplication(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long oAuthApplicationId = ParamUtil.getLong(
			actionRequest, "oAuthApplicationId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean shareableAccessToken = ParamUtil.getBoolean(
			actionRequest, "shareableAccessToken");
		String callbackURI = ParamUtil.getString(actionRequest, "callbackURI");
		String websiteURL = ParamUtil.getString(actionRequest, "websiteURL");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_oAuthApplicationService.updateOAuthApplication(
			oAuthApplicationId, name, description, shareableAccessToken,
			callbackURI, websiteURL, serviceContext);

		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		if (deleteLogo) {
			_oAuthApplicationService.deleteLogo(oAuthApplicationId);
		}
	}

	@Reference(unbind = "-")
	protected void setOAuthApplicationService(
		OAuthApplicationService oAuthApplicationService) {

		_oAuthApplicationService = oAuthApplicationService;
	}

	private OAuthApplicationService _oAuthApplicationService;

	@Reference
	private Portal _portal;

}