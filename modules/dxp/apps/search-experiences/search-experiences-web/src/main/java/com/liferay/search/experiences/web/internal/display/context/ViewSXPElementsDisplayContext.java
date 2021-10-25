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
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.security.permission.resource.SXPElementEntryPermission;
import com.liferay.search.experiences.web.internal.util.SXPBlueprintUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletException;

/**
 * @author Petteri Karttunen
 */
public class ViewSXPElementsDisplayContext
	extends BaseDisplayContext<SXPElement> {

	public ViewSXPElementsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Queries queries,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		SXPElementService sxpElementService) {

		super(liferayPortletRequest, liferayPortletResponse);

		_queries = queries;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
		_sorts = sorts;
		_sxpElementService = sxpElementService;
	}

	public List<String> getAvailableActions(SXPElement sxpElement)
		throws PortalException {

		if (SXPElementEntryPermission.contains(
				themeDisplay.getPermissionChecker(), sxpElement,
				ActionKeys.DELETE)) {

			return Collections.singletonList("deleteEntries");
		}

		return Collections.emptyList();
	}

	public SearchContainer<SXPElement> getSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<SXPElement> searchContainer =
			super.getSearchContainer();

		SXPBlueprintUtil.populateSXPElementSearchContainer(
			themeDisplay.getCompanyGroupId(), getOrderByCol(), getOrderByType(),
			liferayPortletRequest, _queries, _searcher, searchContainer,
			_searchRequestBuilderFactory, _sorts,
			WorkflowConstants.STATUS_APPROVED, _sxpElementService);

		return searchContainer;
	}

	private final Queries _queries;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private final Sorts _sorts;
	private final SXPElementService _sxpElementService;

}