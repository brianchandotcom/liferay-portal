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

package com.liferay.segments.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;

import java.util.List;

import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class PreviewSegmentsEntryUsersDisplayContext {

	public PreviewSegmentsEntryUsersDisplayContext(
		HttpServletRequest request, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsCriteriaContributorRegistry segmentsCriteriaContributorRegistry,
		ODataRetriever<User> userODataRetriever) {

		_request = request;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsCriteriaContributorRegistry =
			segmentsCriteriaContributorRegistry;
		_userODataRetriever = userODataRetriever;

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer getSearchContainer() {
		if (_userSearchContainer != null) {
			return _userSearchContainer;
		}

		SearchContainer userSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null,
			"no-users-have-been-assigned-to-this-segment");

		userSearchContainer.setId("segmentsEntryUsers");

		if (_userODataRetriever == null) {
			return userSearchContainer;
		}

		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			_segmentsCriteriaContributorRegistry.
				getSegmentsCriteriaContributors(User.class.getName());

		Criteria criteria = getCriteriaFromSession();

		if ((criteria == null) ||
			Validator.isNull(criteria.getFilterString(Criteria.Type.MODEL))) {

			return userSearchContainer;
		}

		int total = 0;
		List<User> users = null;

		try {
			total = _userODataRetriever.getResultsCount(
				_themeDisplay.getCompanyId(),
				criteria.getFilterString(Criteria.Type.MODEL),
				_themeDisplay.getLocale());

			users = _userODataRetriever.getResults(
				_themeDisplay.getCompanyId(),
				criteria.getFilterString(Criteria.Type.MODEL),
				_themeDisplay.getLocale(), userSearchContainer.getStart(),
				userSearchContainer.getEnd());
		}
		catch (PortalException pe) {
			_log.error("Unable to retrieve users for criteria " + criteria, pe);
		}

		userSearchContainer.setResults(users);
		userSearchContainer.setTotal(total);

		_userSearchContainer = userSearchContainer;

		return _userSearchContainer;
	}

	protected Criteria getCriteriaFromSession() {
		PortletSession portletSession = _renderRequest.getPortletSession();

		return (Criteria)portletSession.getAttribute(
			SegmentsWebKeys.PREVIEW_SEGMENTS_ENTRY_CRITERIA);
	}

	protected PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "previewSegmentsEntryUsers");

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreviewSegmentsEntryUsersDisplayContext.class);

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;
	private final ThemeDisplay _themeDisplay;
	private final ODataRetriever<User> _userODataRetriever;
	private SearchContainer _userSearchContainer;

}