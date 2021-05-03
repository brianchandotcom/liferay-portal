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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context.ViewElementsDisplayContext;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context.ViewElementsManagementToolbarDisplayContext;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;

import javax.portlet.PortletException;
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
		"javax.portlet.name=" + BlueprintsPortletKeys.BLUEPRINTS_ADMIN,
		"mvc.command.name=" + BlueprintsAdminMVCCommandNames.VIEW_ELEMENTS
	},
	service = MVCRenderCommand.class
)
public class ViewElementsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ViewElementsDisplayContext viewElementsDisplayContext =
			_getViewElementsDisplayContext(renderRequest, renderResponse);

		renderRequest.setAttribute(
			BlueprintsAdminWebKeys.VIEW_ELEMENTS_DISPLAY_CONTEXT,
			viewElementsDisplayContext);

		_setElementsManagementToolbar(
			renderRequest, renderResponse, viewElementsDisplayContext);

		return "/view.jsp";
	}

	private ViewElementsDisplayContext _getViewElementsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return new ViewElementsDisplayContext(
			_portal.getLiferayPortletRequest(renderRequest),
			_portal.getLiferayPortletResponse(renderResponse));
	}

	private void _setElementsManagementToolbar(
		RenderRequest renderRequest, RenderResponse renderResponse,
		ViewElementsDisplayContext viewElementsDisplayContext) {

		try {
			ViewElementsManagementToolbarDisplayContext
				viewElementEntriesManagementToolbarDisplayContext =
					new ViewElementsManagementToolbarDisplayContext(
						_portal.getLiferayPortletRequest(renderRequest),
						_portal.getLiferayPortletResponse(renderResponse),
						viewElementsDisplayContext.getSearchContainer(),
						viewElementsDisplayContext.getDisplayStyle());

			renderRequest.setAttribute(
				BlueprintsAdminWebKeys.
					VIEW_ELEMENTS_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
				viewElementEntriesManagementToolbarDisplayContext);
		}
		catch (PortalException | PortletException exception) {
			_log.error(exception.getMessage(), exception);

			SessionErrors.add(
				renderRequest, BlueprintsAdminWebKeys.ERROR,
				exception.getMessage());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewElementsMVCRenderCommand.class);

	@Reference
	private Portal _portal;

}