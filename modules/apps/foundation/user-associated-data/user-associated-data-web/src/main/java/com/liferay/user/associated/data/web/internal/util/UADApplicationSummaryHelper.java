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
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			getUADApplicationSummaryDisplays(userId);

		searchContainer.setResults(uadApplicationSummaryDisplays);

		searchContainer.setTotal(uadApplicationSummaryDisplays.size());

		return searchContainer;
	}

	public List<String> getApplicationNames() {
		List<String> applicationNames = new ArrayList<>();

		for (UADEntityAggregator uadEntityAggregator :
				_uadRegistry.getUADEntityAggregators()) {

			String applicationName = uadEntityAggregator.getUADEntitySetName();

			if (!applicationNames.contains(applicationName)) {
				applicationNames.add(applicationName);
			}
		}

		Collections.sort(applicationNames);

		return applicationNames;
	}

	public List<UADEntityAggregator> getApplicationUADEntityAggregators(
		String applicationName) {

		List<UADEntityAggregator> uadEntityAggregators = new ArrayList<>();

		for (UADEntityAggregator uadEntityAggregator :
				_uadRegistry.getUADEntityAggregators()) {

			if (applicationName.equals(
					uadEntityAggregator.getUADEntitySetName())) {

				uadEntityAggregators.add(uadEntityAggregator);
			}
		}

		return uadEntityAggregators;
	}

	public List<UADEntityAnonymizer> getApplicationUADEntityAnonymizers(
		String applicationName) {

		List<UADEntityAnonymizer> uadEntityAnonymizers = new ArrayList<>();

		for (String uadRegistryKey : getUADRegistryKeys(applicationName)) {
			uadEntityAnonymizers.add(
				_uadRegistry.getUADEntityAnonymizer(uadRegistryKey));
		}

		return uadEntityAnonymizers;
	}

	public String getDefaultUADRegistryKey(String applicationName) {
		for (String uadRegistryKey :
				_uadRegistry.getUADEntityAggregatorKeySet()) {

			UADEntityAggregator uadEntityAggregator =
				_uadRegistry.getUADEntityAggregator(uadRegistryKey);

			if (applicationName.equals(
					uadEntityAggregator.getUADEntitySetName())) {

				return uadRegistryKey;
			}
		}

		return null;
	}

	public UADApplicationSummaryDisplay getUADApplicationSummaryDisplay(
		String applicationName, long userId) {

		List<UADEntityAggregator> uadEntityAggregators =
			getApplicationUADEntityAggregators(applicationName);

		Stream<UADEntityAggregator> uadEntityAggregatorsStream =
			uadEntityAggregators.stream();

		int count = uadEntityAggregatorsStream.mapToInt(
			uadEntityAggregator -> uadEntityAggregator.count(userId)
		).sum();

		return new UADApplicationSummaryDisplay(
			count, applicationName, getDefaultUADRegistryKey(applicationName));
	}

	public List<UADApplicationSummaryDisplay> getUADApplicationSummaryDisplays(
		long userId) {

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			new ArrayList<>();

		for (String applicationName : getApplicationNames()) {
			uadApplicationSummaryDisplays.add(
				getUADApplicationSummaryDisplay(applicationName, userId));
		}

		return uadApplicationSummaryDisplays;
	}

	public List<String> getUADRegistryKeys(String applicationName) {
		List<String> uadRegistryKeys = new ArrayList<>();

		for (String uadRegistryKey :
				_uadRegistry.getUADEntityAggregatorKeySet()) {

			UADEntityAggregator uadEntityAggregator =
				_uadRegistry.getUADEntityAggregator(uadRegistryKey);

			if (applicationName.equals(
					uadEntityAggregator.getUADEntitySetName())) {

				uadRegistryKeys.add(uadRegistryKey);
			}
		}

		return uadRegistryKeys;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UADApplicationSummaryHelper.class);

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}