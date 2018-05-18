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

package com.liferay.commerce.product.content.web.internal.portlet;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.CPTypeRenderer;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-product-content",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Product Detail",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/product_detail/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_CONTENT_WEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {CPContentPortlet.class, Portlet.class}
)
public class CPContentPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CPDefinition cpDefinition = (CPDefinition)renderRequest.getAttribute(
			CPWebKeys.CP_DEFINITION);

		renderCPDefinition(cpDefinition, renderRequest, renderResponse);

		super.render(renderRequest, renderResponse);
	}

	protected void renderCPDefinition(
		CPDefinition cpDefinition, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		if (cpDefinition == null) {
			return;
		}

		CPTypeRenderer cpTypeRenderer =
			_cpTypeServicesTracker.getCPTypeRenderer(
				cpDefinition.getProductTypeName());

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		try {
			cpTypeRenderer.render(
				cpDefinition, httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentPortlet.class);

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@Reference
	private Portal _portal;

}