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

package com.liferay.iframe.web.portlet;

import com.liferay.iframe.web.action.ViewAction;
import com.liferay.iframe.web.upgrade.IFrameUpgrade;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Peter Fellwock
*/

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-iframe",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/default.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.struts-path=iframe",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=IFrame", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.preferences=classpath:/META-INF/portlet-preferences/default-portlet-preferences.xml",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class IFramePortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest,
		RenderResponse renderResponse) throws IOException, PortletException {

		String src = transformIframe(renderRequest, renderResponse);

		renderRequest.setAttribute(WebKeys.IFRAME_SRC, src);

		if (Validator.isNull(src) || src.equals(Http.HTTP_WITH_SLASH) ||
			src.equals(Http.HTTPS_WITH_SLASH)) {

			include("/html/portal/portlet_not_setup.jsp", renderRequest, renderResponse);
		}
		else {
			super.doView(renderRequest, renderResponse);
		}
	}

	/**
	* Force upgrades to register before the portlet is registered to prevent
	* race conditions.
	*/
	@Reference(unbind = "-")
	protected void setIFrameUpgrade(IFrameUpgrade iFrameUpgrade) {
	}

	private String transformIframe(RenderRequest renderRequest,
		RenderResponse renderResponse) {

		try {
			ViewAction viewAction = new ViewAction();

			return viewAction.transformSrc(renderRequest, renderResponse);

		} catch (PortalException e) {
		}

		return null;
	}
}