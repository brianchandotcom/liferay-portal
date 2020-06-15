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

package com.liferay.commerce.data.integration.web.internal.portlet;

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationPortletKeys;
import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationWebKeys;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.process.type.ProcessTypeRegistry;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessService;
import com.liferay.commerce.data.integration.trigger.CommerceDataIntegrationProcessTriggerHelper;
import com.liferay.commerce.data.integration.web.internal.display.context.CommerceDataIntegrationProcessDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-data-integration-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Data Integration Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CommerceDataIntegrationPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess = null;

		try {
			long commerceDataIntegrationProcessId = ParamUtil.getLong(
				renderRequest, "commerceDataIntegrationProcessId");

			if (commerceDataIntegrationProcessId > 0) {
				commerceDataIntegrationProcess =
					_commerceDataIntegrationProcessService.
						fetchCommerceDataIntegrationProcess(
							commerceDataIntegrationProcessId);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		if (commerceDataIntegrationProcess != null) {
			renderRequest.setAttribute(
				CommerceDataIntegrationWebKeys.
					COMMERCE_DATA_INTEGRATION_PROCESS,
				commerceDataIntegrationProcess);
		}

		CommerceDataIntegrationProcessDisplayContext
			commerceDataIntegrationProcessDisplayContext =
				new CommerceDataIntegrationProcessDisplayContext(
					_commerceDataIntegrationProcessScheduledTaskHelper,
					_commerceDataIntegrationProcessService,
					_processTypeRegistry, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceDataIntegrationProcessDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDataIntegrationPortlet.class);

	@Reference
	private CommerceDataIntegrationProcessTriggerHelper
		_commerceDataIntegrationProcessScheduledTaskHelper;

	@Reference
	private CommerceDataIntegrationProcessService
		_commerceDataIntegrationProcessService;

	@Reference
	private Portal _portal;

	@Reference
	private ProcessTypeRegistry _processTypeRegistry;

}