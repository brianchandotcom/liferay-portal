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

package com.liferay.search.experiences.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintService;
import com.liferay.search.experiences.web.internal.security.permission.resource.SXPBlueprintEntryPermission;
import com.liferay.search.experiences.web.internal.util.SXPBlueprintUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class ViewSXPBlueprintsDisplayContext
	extends BaseDisplayContext<SXPBlueprint> {

	public ViewSXPBlueprintsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public List<String> getAvailableActions(SXPBlueprint sxpBlueprint)
		throws PortalException {

		if (SXPBlueprintEntryPermission.contains(
				themeDisplay.getPermissionChecker(), sxpBlueprint,
				ActionKeys.DELETE)) {

			return Collections.singletonList("deleteEntries");
		}

		return Collections.emptyList();
	}

	public SearchContainer<SXPBlueprint> getSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<SXPBlueprint> searchContainer =
			super.getSearchContainer();

		SXPBlueprintUtil.populateSXPBlueprintSearchContainer(
			themeDisplay.getCompanyGroupId(), getOrderByCol(), getOrderByType(),
			liferayPortletRequest, _queries, _searcher, searchContainer,
			_searchRequestBuilderFactory, _sorts,
			WorkflowConstants.STATUS_APPROVED, _sxpBlueprintService);

		return searchContainer;
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
	protected void setSXPBlueprintService(
		SXPBlueprintService sxpBlueprintService) {

		_sxpBlueprintService = sxpBlueprintService;
	}

	private Queries _queries;
	private Searcher _searcher;
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private Sorts _sorts;
	private SXPBlueprintService _sxpBlueprintService;

}