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

package com.liferay.portal.search.tuning.blueprints.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;
import com.liferay.portal.search.tuning.blueprints.web.internal.constants.BlueprintsWebPortletKeys;
import com.liferay.portal.search.tuning.blueprints.web.internal.display.context.BlueprintsDisplayBuilder;
import com.liferay.portal.search.tuning.blueprints.web.internal.util.BlueprintsWebPortletHelper;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlueprintsWebPortletKeys.BLUEPRINTS_WEB,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		BlueprintsDisplayBuilder blueprintsDisplayBuilder =
			new BlueprintsDisplayBuilder(
				_portal.getHttpServletRequest(renderRequest), renderRequest,
				renderResponse, _blueprintHelper, _blueprintsWebPortletHelper);

		renderRequest.setAttribute(
			BlueprintsWebPortletKeys.BLUEPRINTS_DISPLAY_CONTEXT,
			blueprintsDisplayBuilder.build());

		return "/view.jsp";
	}

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private BlueprintsWebPortletHelper _blueprintsWebPortletHelper;

	@Reference
	private Portal _portal;

}