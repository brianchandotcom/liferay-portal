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

package com.liferay.commerce.price.list.qualification.type.web.internal.display.context;

import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelService;
import com.liferay.commerce.price.list.qualification.type.web.internal.price.UserCommercePriceListQualificationTypeImpl;
import com.liferay.commerce.price.list.qualification.type.web.internal.util.CommercePriceListQualificationTypeUtil;
import com.liferay.commerce.price.list.web.display.context.BaseCommercePriceListDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.users.admin.item.selector.UserItemSelectorCriterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class UserCommercePriceListQualificationTypeDisplayContext
	extends BaseCommercePriceListDisplayContext<User> {

	public UserCommercePriceListQualificationTypeDisplayContext(
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommercePriceListUserRelService commercePriceListUserRelService,
		ItemSelector itemSelector, HttpServletRequest httpServletRequest,
		UserLocalService userLocalService) {

		super(commercePriceListActionHelper, httpServletRequest);

		_commercePriceListUserRelService = commercePriceListUserRelService;
		_itemSelector = itemSelector;
		_userLocalService = userLocalService;

		setDefaultOrderByCol("name");
		setDefaultOrderByType("asc");
	}

	public CommercePriceListQualificationTypeRel
			getCommercePriceListQualificationTypeRel()
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel = null;

		CommercePriceList commercePriceList = getCommercePriceList();

		if (commercePriceList != null) {
			commercePriceListQualificationTypeRel =
				commercePriceList.fetchCommercePriceListQualificationTypeRel(
					UserCommercePriceListQualificationTypeImpl.KEY);
		}

		return commercePriceListQualificationTypeRel;
	}

	public long getCommercePriceListQualificationTypeRelId()
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				getCommercePriceListQualificationTypeRel();

		if (commercePriceListQualificationTypeRel != null) {
			return commercePriceListQualificationTypeRel.
				getCommercePriceListQualificationTypeRelId();
		}

		return 0;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		UserItemSelectorCriterion userItemSelectorCriterion =
			new UserItemSelectorCriterion();

		userItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "usersSelectItem",
			userItemSelectorCriterion);

		String checkedUserIds = StringUtil.merge(getCheckedUserIds());

		itemSelectorURL.setParameter("checkedUserIds", checkedUserIds);

		return itemSelectorURL.toString();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommercePriceList");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return "users";
	}

	@Override
	public SearchContainer<User> getSearchContainer() throws PortalException {
		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, "there-are-no-users");

		OrderByComparator<User> orderByComparator =
			CommercePriceListQualificationTypeUtil.getUserOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commercePriceListUserRelService.getCommercePriceListUserRelsCount(
				getCommercePriceListQualificationTypeRelId(),
				User.class.getName());

		searchContainer.setTotal(total);

		List<User> results = getUsers(
			searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long[] getCheckedUserIds() throws PortalException {
		List<Long> userIds = new ArrayList<>();

		List<User> users = getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (User user : users) {
			userIds.add(user.getUserId());
		}

		if (!userIds.isEmpty()) {
			return ArrayUtil.toLongArray(userIds);
		}

		return new long[0];
	}

	protected List<User> getUsers(int start, int end) throws PortalException {
		List<User> users = new ArrayList<>();

		List<CommercePriceListUserRel> commercePriceListUserRels =
			_commercePriceListUserRelService.getCommercePriceListUserRels(
				getCommercePriceListQualificationTypeRelId(),
				User.class.getName(), start, end);

		for (CommercePriceListUserRel commercePriceListUserRel :
				commercePriceListUserRels) {

			User user = _userLocalService.fetchUser(
				commercePriceListUserRel.getClassPK());

			if (user != null) {
				users.add(user);
			}
		}

		return users;
	}

	private final CommercePriceListUserRelService
		_commercePriceListUserRelService;
	private final ItemSelector _itemSelector;
	private final UserLocalService _userLocalService;

}