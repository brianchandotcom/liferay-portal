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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = UADApplicationSummaryHelper.class)
public class UADApplicationSummaryHelper {

	public SearchContainer<UADApplicationSummaryDisplay> createSearchContainer(
		RenderRequest renderRequest, RenderResponse renderResponse,
		long userId) {

		PortletRequest portletRequest =
			(PortletRequest)renderRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(
				(PortletResponse)renderRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE));

		PortletURL currentURL = PortletURLUtil.getCurrent(
			_portal.getLiferayPortletRequest(portletRequest),
			liferayPortletResponse);

		SearchContainer<UADApplicationSummaryDisplay> searchContainer =
			new SearchContainer<>(portletRequest, currentURL, null, null);

		searchContainer.setOrderByCol(
			ParamUtil.getString(
				renderRequest, searchContainer.getOrderByColParam(), "name"));
		searchContainer.setOrderByType(
			ParamUtil.getString(
				renderRequest, searchContainer.getOrderByTypeParam(), "asc"));

		Stream<UADApplicationSummaryDisplay>
			uadApplicationSummaryDisplayStream =
				getUADApplicationSummaryDisplayStream(portletRequest, userId);

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			uadApplicationSummaryDisplayStream.filter(
				getPredicate(
					ParamUtil.getString(renderRequest, "navigation", "all"))
			).sorted(
				getComparator(
					searchContainer.getOrderByCol(),
					searchContainer.getOrderByType())
			).collect(
				Collectors.toList()
			);

		searchContainer.setResults(uadApplicationSummaryDisplays);

		searchContainer.setTotal(uadApplicationSummaryDisplays.size());

		return searchContainer;
	}

	public List<UADAnonymizer> getApplicationUADAnonymizers(
		String applicationName) {

		Stream<UADEntityDisplay> uadEntityDisplayStream =
			getApplicationUADEntityDisplayStream(applicationName);

		return uadEntityDisplayStream.map(
			UADEntityDisplay::getKey
		).map(
			key -> _uadRegistry.getUADAnonymizer(key)
		).collect(
			Collectors.toList()
		);
	}

	public Stream<UADEntityDisplay> getApplicationUADEntityDisplayStream(
		String applicationName) {

		Stream<UADEntityDisplay> uadEntityDisplayStream =
			_uadRegistry.getUADEntityDisplayStream();

		return uadEntityDisplayStream.filter(
			uadEntityDisplay -> applicationName.equals(
				uadEntityDisplay.getApplicationName()));
	}

	public Comparator<UADApplicationSummaryDisplay> getComparator(
		String orderByColumn, String orderByType) {

		Comparator<UADApplicationSummaryDisplay> comparator =
			Comparator.comparing(UADApplicationSummaryDisplay::getName);

		if (orderByColumn.equals("items") || orderByColumn.equals("status")) {
			comparator = Comparator.comparingInt(
				UADApplicationSummaryDisplay::getCount);
		}

		if (orderByType.equals("desc")) {
			comparator = comparator.reversed();
		}

		return comparator;
	}

	public String getDefaultUADRegistryKey(String applicationName) {
		Stream<UADEntityDisplay> uadEntityDisplayStream =
			getApplicationUADEntityDisplayStream(applicationName);

		return uadEntityDisplayStream.map(
			UADEntityDisplay::getKey
		).findFirst(
		).get();
	}

	public Predicate<UADApplicationSummaryDisplay> getPredicate(
		String navigation) {

		if (navigation.equals("in-progress")) {
			return display -> display.getCount() > 0;
		}
		else if (navigation.equals("done")) {
			return display -> display.getCount() <= 0;
		}

		return display -> true;
	}

	public int getReviewableUADEntitiesCount(
		Stream<UADEntityDisplay> uadEntityDisplayStream, long userId) {

		return uadEntityDisplayStream.map(
			uadEntityDisplay -> uadEntityDisplay.getKey()
		).map(
			key -> _uadRegistry.getUADAggregator(key)
		).mapToInt(
			uadAggregator -> (int)uadAggregator.count(userId)
		).sum();
	}

	public int getTotalReviewableUADEntitiesCount(long userId) {
		return getReviewableUADEntitiesCount(
			_uadRegistry.getUADEntityDisplayStream(), userId);
	}

	public UADApplicationSummaryDisplay getUADApplicationSummaryDisplay(
		PortletRequest portletRequest, String applicationName, long userId) {

		UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
			new UADApplicationSummaryDisplay();

		int count = getReviewableUADEntitiesCount(
			getApplicationUADEntityDisplayStream(applicationName), userId);

		uadApplicationSummaryDisplay.setCount(count);

		uadApplicationSummaryDisplay.setName(applicationName);

		if (count > 0) {
			uadApplicationSummaryDisplay.setViewURL(
				getViewURL(portletRequest, applicationName, userId));
		}

		return uadApplicationSummaryDisplay;
	}

	public Stream<UADApplicationSummaryDisplay>
		getUADApplicationSummaryDisplayStream(
			PortletRequest portletRequest, long userId) {

		Stream<UADEntityDisplay> uadEntityDisplayStream =
			_uadRegistry.getUADEntityDisplayStream();

		return uadEntityDisplayStream.map(
			UADEntityDisplay::getApplicationName
		).distinct(
		).sorted(
		).map(
			applicationName -> getUADApplicationSummaryDisplay(
				portletRequest, applicationName, userId)
		);
	}

	public String getViewURL(
		PortletRequest portletRequest, String applicationName, long userId) {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			portletRequest, UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName", "/view_uad_entities");
		liferayPortletURL.setParameter("p_u_i_d", String.valueOf(userId));
		liferayPortletURL.setParameter("applicationName", applicationName);
		liferayPortletURL.setParameter(
			"uadRegistryKey", getDefaultUADRegistryKey(applicationName));

		return liferayPortletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UADApplicationSummaryHelper.class);

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}