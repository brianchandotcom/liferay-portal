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

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class SimpleSiteItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<Group> {

	public SimpleSiteItemSelectorViewDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		GroupService groupService, HttpServletRequest httpServletRequest,
		PortletURL portletURL, String itemSelectedEventName, boolean search) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commerceChannelLocalService = commerceChannelLocalService;
		_groupService = groupService;
		_search = search;
	}

	public String getChannelUsingSite(long siteGroupId) throws PortalException {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				siteGroupId);

		if (commerceChannel == null) {
			return StringPool.BLANK;
		}

		return commerceChannel.getName();
	}

	public long getGroupId() {
		return ParamUtil.getLong(
			cpRequestHelper.getRenderRequest(), "siteGroupId", -1);
	}

	@Override
	public PortletURL getPortletURL() {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setParameter(
			"siteGroupId", getGroupId()
		).buildPortletURL();
	}

	@Override
	public SearchContainer<Group> getSearchContainer() throws PortalException {
		if (searchContainer != null) {
			return searchContainer;
		}

		String emptyResultsMessage = "no-sites-were-found";

		long groupId = getGroupId();

		if (groupId > 0) {
			emptyResultsMessage = "no-sites-were-found-in-x";

			Group group = _groupService.getGroup(groupId);

			Locale locale = cpRequestHelper.getLocale();

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());

			emptyResultsMessage = LanguageUtil.format(
				resourceBundle, emptyResultsMessage, group.getName(locale),
				false);
		}

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null,
			emptyResultsMessage);

		String orderByCol = getOrderByCol();

		String orderByType = getOrderByType();

		OrderByComparator<Group> orderByComparator = new GroupNameComparator(
			orderByType.equals("asc"));

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setSearch(_search);

		searchContainer.setResultsAndTotal(
			() -> _groupService.search(
				cpRequestHelper.getCompanyId(),
				new long[] {
					ClassNameLocalServiceUtil.getClassNameId(Group.class),
					ClassNameLocalServiceUtil.getClassNameId(Organization.class)
				},
				null,
				LinkedHashMapBuilder.<String, Object>put(
					"active", true
				).put(
					"site", true
				).build(),
				searchContainer.getStart(), searchContainer.getEnd(), null),
			_groupService.searchCount(
				cpRequestHelper.getCompanyId(), null, null, new String[0]));

		return searchContainer;
	}

	public boolean isSiteAvailable(long siteGroupId) {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				siteGroupId);

		if (commerceChannel == null) {
			return true;
		}

		return false;
	}

	protected ManagementBarFilterItem getManagementBarFilterItem(
			long siteGroupId, String label)
		throws PortletException {

		boolean active = false;

		if (getGroupId() == siteGroupId) {
			active = true;
		}

		return new ManagementBarFilterItem(
			active, String.valueOf(siteGroupId), label,
			PortletURLBuilder.create(
				PortletURLUtil.clone(
					getPortletURL(), cpRequestHelper.getRenderResponse())
			).setParameter(
				"siteGroupId", siteGroupId
			).buildString());
	}

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final GroupService _groupService;
	private final boolean _search;

}