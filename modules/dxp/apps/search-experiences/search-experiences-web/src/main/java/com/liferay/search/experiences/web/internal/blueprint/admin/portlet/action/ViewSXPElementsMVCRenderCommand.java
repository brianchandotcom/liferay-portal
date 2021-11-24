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

package com.liferay.search.experiences.web.internal.blueprint.admin.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.web.internal.blueprint.admin.display.context.ViewSXPElementsDisplayContext;
import com.liferay.search.experiences.web.internal.constants.SXPWebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT_ADMIN,
		"mvc.command.name=/sxp_blueprint_admin/view_sxp_elements"
	},
	service = MVCRenderCommand.class
)
public class ViewSXPElementsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			SXPWebKeys.VIEW_SXP_ELEMENTS_DISPLAY_CONTEXT,
			new ViewSXPElementsDisplayContext(
				_portal.getHttpServletRequest(renderRequest),
				_sxpElementModelResourcePermission));

		return "/sxp_blueprint_admin/view.jsp";
	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(model.class.name=com.liferay.search.experiences.model.SXPElement)"
	)
	private ModelResourcePermission<SXPElement>
		_sxpElementModelResourcePermission;

}