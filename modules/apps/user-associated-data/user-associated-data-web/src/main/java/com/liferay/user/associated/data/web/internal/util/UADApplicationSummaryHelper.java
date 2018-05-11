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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = UADApplicationSummaryHelper.class)
public class UADApplicationSummaryHelper {

	public DropdownItemList createManagementBarFilterItems(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		HttpServletRequest request = _portal.getHttpServletRequest(
			renderRequest);

		return new DropdownItemList() {
			{
				DropdownItemList filterByNavigationDropdownItemList =
					getFilterByNavigationDropdownItemList(
						renderRequest, renderResponse);

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							filterByNavigationDropdownItemList);
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "filter-by-navigation"));
					});

				DropdownItemList orderByNavigationDropdownItemList =
					getOrderByDropdownItemList(renderRequest, renderResponse);

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							orderByNavigationDropdownItemList);
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "order-by"));
					});
			}
		};
	}

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

		searchContainer.setOrderByCol(getOrderByCol(renderRequest));
		searchContainer.setOrderByType(getOrderByType(renderRequest));

		Predicate<UADApplicationSummaryDisplay> predicate = getPredicate(
			getNavigation(renderRequest));

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			getUADApplicationSummaryDisplays(portletRequest, userId);

		Supplier<Stream<UADApplicationSummaryDisplay>> streamSupplier = () -> {
			Stream<UADApplicationSummaryDisplay> stream =
				uadApplicationSummaryDisplays.stream();

			return stream.filter(predicate);
		};

		Stream<UADApplicationSummaryDisplay> summaryDisplayStream =
			streamSupplier.get();

		List<UADApplicationSummaryDisplay> results =
			summaryDisplayStream.sorted(
				getComparator(
					searchContainer.getOrderByCol(),
					searchContainer.getOrderByType())
			).skip(
				searchContainer.getStart()
			).limit(
				searchContainer.getDelta()
			).collect(
				Collectors.toList()
			);

		searchContainer.setResults(results);

		summaryDisplayStream = streamSupplier.get();

		searchContainer.setTotal((int)summaryDisplayStream.count());

		return searchContainer;
	}

	public List<UADAnonymizer> getApplicationUADAnonymizers(
		String applicationKey) {

		Stream<UADDisplay> uadDisplayStream =
			_uadRegistry.getApplicationUADDisplayStream(applicationKey);

		return uadDisplayStream.map(
			UADDisplay::getTypeClass
		).map(
			Class::getName
		).map(
			key -> _uadRegistry.getUADAnonymizer(key)
		).collect(
			Collectors.toList()
		);
	}

	public PortletURL getBaseURL(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		PortletURL baseURL = renderResponse.createRenderURL();

		baseURL.setParameter(
			"mvcRenderCommandName", "/view_uad_applications_summary");
		baseURL.setParameter("navigation", getNavigation(renderRequest));
		baseURL.setParameter("orderByCol", getOrderByCol(renderRequest));
		baseURL.setParameter("orderByType", getOrderByType(renderRequest));

		User user = _selectedUserHelper.getSelectedUser(renderRequest);

		long userId = user.getUserId();

		baseURL.setParameter("p_u_i_d", String.valueOf(userId));

		return baseURL;
	}

	public Comparator<UADApplicationSummaryDisplay> getComparator(
		String orderByColumn, String orderByType) {

		Comparator<UADApplicationSummaryDisplay> comparator =
			Comparator.comparing(
				UADApplicationSummaryDisplay::getApplicationKey);

		if (orderByColumn.equals("items") || orderByColumn.equals("status")) {
			comparator = Comparator.comparingInt(
				UADApplicationSummaryDisplay::getCount);
		}

		if (orderByType.equals("desc")) {
			comparator = comparator.reversed();
		}

		return comparator;
	}

	public String getDefaultUADRegistryKey(String applicationKey) {
		List<UADDisplay> uadDisplays = _uadRegistry.getApplicationUADDisplays(
			applicationKey);

		UADDisplay uadDisplay = uadDisplays.get(0);

		if (uadDisplay == null) {
			return null;
		}

		Class<?> typeClass = uadDisplay.getTypeClass();

		return typeClass.getName();
	}

	public DropdownItemList getFilterByNavigationDropdownItemList(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		HttpServletRequest request = _portal.getHttpServletRequest(
			renderRequest);

		PortletURL baseURL = getBaseURL(renderRequest, renderResponse);

		return new DropdownItemList() {
			{
				for (String navigation :
						new String[] {"all", "pending", "done"}) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								navigation.equals(
									getNavigation(renderRequest)));
							dropdownItem.setHref(
								baseURL, "navigation", navigation);
							dropdownItem.setLabel(
								LanguageUtil.get(request, navigation));
						});
				}
			}
		};
	}

	public String getNavigation(RenderRequest renderRequest) {
		return ParamUtil.getString(renderRequest, "navigation", "all");
	}

	public String getOrderByCol(RenderRequest renderRequest) {
		return ParamUtil.getString(renderRequest, "orderByCol", "name");
	}

	public DropdownItemList getOrderByDropdownItemList(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		HttpServletRequest request = _portal.getHttpServletRequest(
			renderRequest);

		PortletURL baseURL = getBaseURL(renderRequest, renderResponse);

		return new DropdownItemList() {
			{
				for (String orderByCol :
						new String[] {"name", "items", "status"}) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(
									getOrderByCol(renderRequest)));
							dropdownItem.setHref(
								baseURL, "orderByCol", orderByCol);
							dropdownItem.setLabel(
								LanguageUtil.get(request, orderByCol));
						});
				}
			}
		};
	}

	public String getOrderByType(RenderRequest renderRequest) {
		return ParamUtil.getString(renderRequest, "orderByType", "asc");
	}

	public Predicate<UADApplicationSummaryDisplay> getPredicate(
		String navigation) {

		if (navigation.equals("pending")) {
			return display -> display.getCount() > 0;
		}
		else if (navigation.equals("done")) {
			return display -> display.getCount() <= 0;
		}

		return display -> true;
	}

	public int getReviewableUADEntitiesCount(
		Stream<UADDisplay> uadDisplayStream, long userId) {

		return uadDisplayStream.mapToInt(
			uadDisplay -> (int)uadDisplay.count(userId)
		).sum();
	}

	public String getSortingURL(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		PortletURL sortingURL = getBaseURL(renderRequest, renderResponse);

		String orderByType;

		if (Objects.equals(getOrderByType(renderRequest), "asc")) {
			orderByType = "desc";
		}
		else {
			orderByType = "asc";
		}

		sortingURL.setParameter("orderByType", orderByType);

		return sortingURL.toString();
	}

	public int getTotalReviewableUADEntitiesCount(long userId) {
		return getReviewableUADEntitiesCount(
			_uadRegistry.getUADDisplayStream(), userId);
	}

	public UADApplicationSummaryDisplay getUADApplicationSummaryDisplay(
		PortletRequest portletRequest, String applicationKey, long userId) {

		UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
			new UADApplicationSummaryDisplay();

		Collection<UADDisplay> applicationUADDisplays =
			_uadRegistry.getApplicationUADDisplays(applicationKey);

		int count = getReviewableUADEntitiesCount(
			applicationUADDisplays.stream(), userId);

		uadApplicationSummaryDisplay.setCount(count);

		uadApplicationSummaryDisplay.setApplicationKey(applicationKey);

		if (count > 0) {
			uadApplicationSummaryDisplay.setViewURL(
				getViewURL(portletRequest, applicationKey, userId));
		}

		return uadApplicationSummaryDisplay;
	}

	public List<UADApplicationSummaryDisplay> getUADApplicationSummaryDisplays(
		PortletRequest portletRequest, long userId) {

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			new ArrayList<>();

		Set<String> applicationUADDisplayKeySet =
			_uadRegistry.getApplicationUADDisplaysKeySet();

		Iterator<String> iterator = applicationUADDisplayKeySet.iterator();

		while (iterator.hasNext()) {
			String applicationKey = iterator.next();

			uadApplicationSummaryDisplays.add(
				getUADApplicationSummaryDisplay(
					portletRequest, applicationKey, userId));
		}

		uadApplicationSummaryDisplays.sort(
			(uadApplicationSummaryDisplay, uadApplicationSummaryDisplay2) -> {
				String applicationKey1 =
					uadApplicationSummaryDisplay.getApplicationKey();

				return applicationKey1.compareTo(
					uadApplicationSummaryDisplay2.getApplicationKey());
			});

		return uadApplicationSummaryDisplays;
	}

	public String getViewURL(
		PortletRequest portletRequest, String applicationKey, long userId) {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			portletRequest, UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName", "/view_uad_entities");
		liferayPortletURL.setParameter("p_u_i_d", String.valueOf(userId));
		liferayPortletURL.setParameter("applicationKey", applicationKey);
		liferayPortletURL.setParameter(
			"uadRegistryKey", getDefaultUADRegistryKey(applicationKey));

		return liferayPortletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UADApplicationSummaryHelper.class);

	@Reference
	private Portal _portal;

	@Reference
	private SelectedUserHelper _selectedUserHelper;

	@Reference
	private UADRegistry _uadRegistry;

}