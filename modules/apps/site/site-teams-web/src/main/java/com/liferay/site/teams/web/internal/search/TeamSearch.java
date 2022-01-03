/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.teams.web.internal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.TeamNameComparator;
import com.liferay.site.teams.web.internal.constants.SiteTeamsPortletKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class TeamSearch extends SearchContainer<Team> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-teams-were-found";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("name");
			add("description");
		}
	};

	public TeamSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new TeamDisplayTerms(portletRequest),
			new TeamDisplayTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		TeamDisplayTerms displayTerms = (TeamDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			TeamDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(TeamDisplayTerms.NAME, displayTerms.getName());

		String orderByCol = SearchOrderByUtil.getOrderByCol(
			portletRequest, SiteTeamsPortletKeys.SITE_TEAMS, "name");

		setOrderByCol(orderByCol);

		String orderByType = SearchOrderByUtil.getOrderByType(
			portletRequest, SiteTeamsPortletKeys.SITE_TEAMS, "asc");

		setOrderByComparator(_getOrderByComparator(orderByCol, orderByType));
		setOrderByType(orderByType);
	}

	private static OrderByComparator<Team> _getOrderByComparator(
		String orderByCol, String orderByType) {

		OrderByComparator<Team> orderByComparator = null;

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("name")) {
			orderByComparator = new TeamNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}