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

package com.liferay.search.experiences.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.constants.SXPBlueprintWebKeys;
import com.liferay.search.experiences.web.internal.display.context.ViewSXPElementsDisplayContext;

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
			SXPBlueprintWebKeys.VIEW_SXP_ELEMENTS_DISPLAY_CONTEXT,
			viewSXPElementsDisplayContext);

		return "/sxp_blueprint_admin/view.jsp";
	}

	@Reference(unbind = "-")
	protected void setQueries(Queries queries) {
		_queries = queries;
	}

	@Reference(unbind = "-")
	protected void setSearcher(Searcher searcher) {
		_searcher = searcher;
	}

	@Reference(unbind = "-")
	protected void setSearchRequestBuilderFactory(
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setSorts(Sorts sorts) {
		_sorts = sorts;
	}

	@Reference(unbind = "-")
	protected void setSXPElementService(SXPElementService sxpElementService) {
		_sxpElementService = sxpElementService;
	}

	private ViewSXPElementsDisplayContext _getViewElementsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return new ViewSXPElementsDisplayContext(
			_portal.getLiferayPortletRequest(renderRequest),
			_portal.getLiferayPortletResponse(renderResponse), _queries,
			_searcher, _searchRequestBuilderFactory, _sorts,
			_sxpElementService);
	}

	@Reference
	private Portal _portal;

	private Queries _queries;
	private Searcher _searcher;
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private Sorts _sorts;
	private SXPElementService _sxpElementService;

}