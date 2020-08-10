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

package com.liferay.commerce.data.integration.web.internal.portlet.action;

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationPortletKeys;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogService;
import com.liferay.commerce.data.integration.web.internal.display.context.CommerceDataIntegrationProcessLogDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
		"mvc.command.name=viewCommerceDataIntegrationProcessLog"
	},
	service = MVCRenderCommand.class
)
public class ViewCommerceDataIntegrationProcessLogRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		CommerceDataIntegrationProcessLogDisplayContext
			commerceDataIntegrationProcessLogDisplayContext =
				new CommerceDataIntegrationProcessLogDisplayContext(
					_commerceDataIntegrationProcessLogService, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceDataIntegrationProcessLogDisplayContext);

		return "/process_log_details.jsp";
	}

	@Reference
	private CommerceDataIntegrationProcessLogService
		_commerceDataIntegrationProcessLogService;

}