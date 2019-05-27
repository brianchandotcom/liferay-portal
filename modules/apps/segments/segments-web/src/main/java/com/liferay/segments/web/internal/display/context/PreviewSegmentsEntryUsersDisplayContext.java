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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryService;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsEntryProviderRegistry segmentsEntryProviderRegistry,
		SegmentsEntryService segmentsEntryService,
		ODataRetriever<User> userODataRetriever,
		UserLocalService userLocalService) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsEntryProviderRegistry = segmentsEntryProviderRegistry;
		_segmentsEntryService = segmentsEntryService;
		_userODataRetriever = userODataRetriever;
		_userLocalService = userLocalService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
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

		int total = 0;
		List<User> users = null;

		try {
			Criteria criteria = getCriteriaFromSession();

			SegmentsEntry segmentsEntry = getSegmentsEntry();

			if ((criteria != null) &&
				Validator.isNotNull(
					criteria.getFilterString(Criteria.Type.MODEL))) {

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
			else if (segmentsEntry != null) {
				total =
					_segmentsEntryProviderRegistry.
						getSegmentsEntryClassPKsCount(
							segmentsEntry.getSegmentsEntryId());

				long[] segmentsEntryClassPKs =
					_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
						segmentsEntry.getSegmentsEntryId(),
						userSearchContainer.getStart(),
						userSearchContainer.getEnd());

				LongStream segmentsEntryClassPKsStream = Arrays.stream(
					segmentsEntryClassPKs);

				users = segmentsEntryClassPKsStream.boxed(
				).map(
					userId -> _userLocalService.fetchUser(userId)
				).collect(
					Collectors.toList()
				);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to obtain a preview of the segment users", pe);
			}
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

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry != null) {
			portletURL.setParameter(
				"segmentsEntryId",
				String.valueOf(segmentsEntry.getSegmentsEntryId()));
		}

		return portletURL;
	}

	protected SegmentsEntry getSegmentsEntry() {
		if (_segmentsEntry != null) {
			return _segmentsEntry;
		}

		long segmentsEntryId = ParamUtil.getLong(
			_httpServletRequest, "segmentsEntryId");

		if (segmentsEntryId > 0) {
			try {
				_segmentsEntry = _segmentsEntryService.getSegmentsEntry(
					segmentsEntryId);
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to get segment entry " + segmentsEntryId, pe);

				return null;
			}
		}

		return _segmentsEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreviewSegmentsEntryUsersDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SegmentsEntry _segmentsEntry;
	private final SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;
	private final SegmentsEntryService _segmentsEntryService;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;
	private final ODataRetriever<User> _userODataRetriever;
	private SearchContainer _userSearchContainer;

}