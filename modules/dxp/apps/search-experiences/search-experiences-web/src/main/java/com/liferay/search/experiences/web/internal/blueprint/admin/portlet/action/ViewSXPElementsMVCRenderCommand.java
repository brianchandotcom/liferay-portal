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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.blueprint.admin.display.context.ViewSXPElementsDisplayContext;
import com.liferay.search.experiences.web.internal.blueprint.admin.display.context.ViewSXPElementsManagementToolbarDisplayContext;
import com.liferay.search.experiences.web.internal.constants.SXPWebKeys;

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

		ViewSXPElementsDisplayContext viewSXPElementsDisplayContext =
			_getViewElementsDisplayContext(renderRequest, renderResponse);

		renderRequest.setAttribute(
			SXPWebKeys.VIEW_SXP_ELEMENTS_DISPLAY_CONTEXT,
			viewSXPElementsDisplayContext);

		_setSXPElementsManagementToolbar(
			renderRequest, renderResponse, viewSXPElementsDisplayContext);

		return "/sxp_blueprint_admin/view.jsp";
	}

	private ViewSXPElementsDisplayContext _getViewElementsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return new ViewSXPElementsDisplayContext(
			_portal.getLiferayPortletRequest(renderRequest),
			_portal.getLiferayPortletResponse(renderResponse), _queries,
			_searcher, _searchRequestBuilderFactory, _sorts,
			_sxpElementService);
	}

	private void _setSXPElementsManagementToolbar(
		RenderRequest renderRequest, RenderResponse renderResponse,
		ViewSXPElementsDisplayContext viewSXPElementsDisplayContext) {

		try {
			ViewSXPElementsManagementToolbarDisplayContext
				viewSXPElementEntriesManagementToolbarDisplayContext =
					new ViewSXPElementsManagementToolbarDisplayContext(
						_portal.getLiferayPortletRequest(renderRequest),
						_portal.getLiferayPortletResponse(renderResponse),
						viewSXPElementsDisplayContext.getSearchContainer(),
						viewSXPElementsDisplayContext.getDisplayStyle());

			renderRequest.setAttribute(
				SXPWebKeys.VIEW_SXP_ELEMENTS_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
				viewSXPElementEntriesManagementToolbarDisplayContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(renderRequest, portalException.getClass());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewSXPElementsMVCRenderCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

	@Reference
	private SXPElementService _sxpElementService;

}