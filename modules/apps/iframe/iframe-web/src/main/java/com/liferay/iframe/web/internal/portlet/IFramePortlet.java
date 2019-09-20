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

package com.liferay.iframe.web.internal.portlet;

import com.liferay.iframe.web.internal.configuration.IFramePortletInstanceConfiguration;
import com.liferay.iframe.web.internal.constants.IFramePortletKeys;
import com.liferay.iframe.web.internal.constants.IFrameWebKeys;
import com.liferay.iframe.web.internal.display.context.IFrameDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
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
		"com.liferay.fragment.entry.processor.portlet.alias=iframe",
		"com.liferay.portlet.css-class-wrapper=portlet-iframe",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=IFrame", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + IFramePortletKeys.IFRAME,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class IFramePortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String src = null;

		try {
			src = transformSrc(renderRequest, renderResponse);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		renderRequest.setAttribute(IFrameWebKeys.IFRAME_SRC, src);

		if (Validator.isNull(src) || src.equals(Http.HTTP_WITH_SLASH) ||
			src.equals(Http.HTTPS_WITH_SLASH)) {

			include("/portlet_not_setup.jsp", renderRequest, renderResponse);
		}
		else {
			super.doView(renderRequest, renderResponse);
		}
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.iframe.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	protected String transformSrc(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		IFrameDisplayContext iFrameDisplayContext = new IFrameDisplayContext(
			renderRequest);

		IFramePortletInstanceConfiguration iFramePortletInstanceConfiguration =
			iFrameDisplayContext.getIFramePortletInstanceConfiguration();

		String src = ParamUtil.getString(
			renderRequest, "src", iFramePortletInstanceConfiguration.src());

		if (!iFramePortletInstanceConfiguration.auth()) {
			return src;
		}

		String authType = iFrameDisplayContext.getAuthType();

		if (authType.equals("form")) {
			PortletURL proxyURL = renderResponse.createRenderURL();

			proxyURL.setParameter("mvcPath", "/proxy.jsp");

			src = proxyURL.toString();
		}

		return src;
	}

	private static final Log _log = LogFactoryUtil.getLog(IFramePortlet.class);

}