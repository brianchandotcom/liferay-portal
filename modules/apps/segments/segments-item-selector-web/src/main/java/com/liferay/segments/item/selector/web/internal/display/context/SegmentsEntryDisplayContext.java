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

package com.liferay.segments.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stefan Tanasie
 */
public class SegmentsEntryDisplayContext {

	public SegmentsEntryDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		RenderRequest renderRequest,
		SegmentsEntryLocalService segmentsEntryLocalService) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_renderRequest = renderRequest;
		_segmentsEntryLocalService = segmentsEntryLocalService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<SegmentsEntry> getSegmentEntrySearchContainer()
		throws PortalException {

		if (_segmentEntrySearchContainer != null) {
			return _segmentEntrySearchContainer;
		}

		SearchContainer<SegmentsEntry> segmentEntrySearchContainer =
			new SearchContainer<>(
				_renderRequest, _portletURL, null, "there-are-no-segments");

		segmentEntrySearchContainer.setId("selectSegmentsEntry");
		segmentEntrySearchContainer.setOrderByCol(_getOrderByCol());
		segmentEntrySearchContainer.setOrderByType(_getOrderByType());

		segmentEntrySearchContainer.setResultsAndTotal(
			_segmentsEntryLocalService.searchSegmentsEntries(
				_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
				_getKeywords(), true,
				LinkedHashMapBuilder.<String, Object>put(
					"excludedSegmentsEntryIds", _getExcludedSegmentsEntryIds()
				).put(
					"excludedSources", _getExcludedSources()
				).build(),
				segmentEntrySearchContainer.getStart(),
				segmentEntrySearchContainer.getEnd(), _getSort()));

		_segmentEntrySearchContainer = segmentEntrySearchContainer;

		return _segmentEntrySearchContainer;
	}

	private long[] _getExcludedSegmentsEntryIds() {
		if (_excludedSegmentsEntryIds != null) {
			return _excludedSegmentsEntryIds;
		}

		_excludedSegmentsEntryIds = ParamUtil.getLongValues(
			_httpServletRequest, "excludedSegmentsEntryIds");

		return _excludedSegmentsEntryIds;
	}

	private String[] _getExcludedSources() {
		if (_excludedSources != null) {
			return _excludedSources;
		}

		_excludedSources = ParamUtil.getStringValues(
			_httpServletRequest, "excludedSources");

		return _excludedSources;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, SegmentsPortletKeys.SEGMENTS,
			"entry-order-by-col", "modified-date");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, SegmentsPortletKeys.SEGMENTS,
			"entry-order-by-type", "asc");

		return _orderByType;
	}

	private Sort _getSort() {
		boolean orderByAsc = false;

		if (Objects.equals(_getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (Objects.equals(_getOrderByCol(), "name")) {
			sort = new Sort(
				Field.getSortableFieldName(
					"localized_name_".concat(_themeDisplay.getLanguageId())),
				Sort.STRING_TYPE, !orderByAsc);
		}
		else {
			sort = new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
		}

		return sort;
	}

	private long[] _excludedSegmentsEntryIds;
	private String[] _excludedSources;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private SearchContainer<SegmentsEntry> _segmentEntrySearchContainer;
	private final SegmentsEntryLocalService _segmentsEntryLocalService;
	private final ThemeDisplay _themeDisplay;

}