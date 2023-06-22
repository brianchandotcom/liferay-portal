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

package com.liferay.segments.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.item.selector.criterion.SegmentsExperienceItemSelectorCriterion;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SegmentsExperienceItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<SegmentsExperience> {

	public SegmentsExperienceItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		SegmentsExperienceItemSelectorCriterion
			segmentsExperienceItemSelectorCriterion) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_segmentsExperienceItemSelectorCriterion =
			segmentsExperienceItemSelectorCriterion;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public String[] getDisplayViews() {
		return new String[0];
	}

	@Override
	public ItemDescriptor getItemDescriptor(
		SegmentsExperience segmentsExperience) {

		return new SegmentsExperienceItemDescriptor(
			_httpServletRequest, segmentsExperience);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	@Override
	public SearchContainer<SegmentsExperience> getSearchContainer()
		throws PortalException {

		SearchContainer<SegmentsExperience> searchContainer =
			new SearchContainer<>(
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST),
				_portletURL, null, "there-are-no-items-to-display");

		searchContainer.setResultsAndTotal(
			SegmentsExperienceLocalServiceUtil.getSegmentsExperiences(
				_themeDisplay.getScopeGroupId(),
				_segmentsExperienceItemSelectorCriterion.getPlid(), true));

		return searchContainer;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;
	private final SegmentsExperienceItemSelectorCriterion
		_segmentsExperienceItemSelectorCriterion;
	private final ThemeDisplay _themeDisplay;

}