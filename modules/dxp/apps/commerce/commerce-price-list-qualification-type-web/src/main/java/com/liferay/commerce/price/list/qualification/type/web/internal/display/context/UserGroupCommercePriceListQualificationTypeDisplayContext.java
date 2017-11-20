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
import com.liferay.commerce.price.list.qualification.type.web.internal.price.UserGroupCommercePriceListQualificationTypeImpl;
import com.liferay.commerce.price.list.qualification.type.web.internal.util.CommercePriceListQualificationTypeUtil;
import com.liferay.commerce.price.list.web.display.context.BaseCommercePriceListDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.groups.admin.item.selector.UserGroupItemSelectorCriterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class UserGroupCommercePriceListQualificationTypeDisplayContext
	extends BaseCommercePriceListDisplayContext<UserGroup> {

	public UserGroupCommercePriceListQualificationTypeDisplayContext(
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommercePriceListUserRelService commercePriceListUserRelService,
		ItemSelector itemSelector, HttpServletRequest httpServletRequest,
		UserGroupLocalService userGroupLocalService) {

		super(commercePriceListActionHelper, httpServletRequest);

		_commercePriceListUserRelService = commercePriceListUserRelService;
		_itemSelector = itemSelector;
		_userGroupLocalService = userGroupLocalService;

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
					UserGroupCommercePriceListQualificationTypeImpl.KEY);
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

		UserGroupItemSelectorCriterion userGroupItemSelectorCriterion =
			new UserGroupItemSelectorCriterion();

		userGroupItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "userGroupsSelectItem",
			userGroupItemSelectorCriterion);

		String checkedUserGroupIds = StringUtil.merge(getCheckedUserGroupIds());

		itemSelectorURL.setParameter(
			"checkedUserGroupIds", checkedUserGroupIds);

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
	public String getScreenNavigationCategoryKey() throws PortalException {
		return "user-groups";
	}

	@Override
	public SearchContainer<UserGroup> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"there-are-no-user-groups");

		OrderByComparator<UserGroup> orderByComparator =
			CommercePriceListQualificationTypeUtil.
				getUserGroupOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commercePriceListUserRelService.getCommercePriceListUserRelsCount(
				getCommercePriceListQualificationTypeRelId(),
				UserGroup.class.getName());

		searchContainer.setTotal(total);

		List<UserGroup> results = getUserGroups(
			searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long[] getCheckedUserGroupIds() throws PortalException {
		List<Long> userGroupIds = new ArrayList<>();

		List<UserGroup> userGroups = getUserGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (UserGroup userGroup : userGroups) {
			userGroupIds.add(userGroup.getUserGroupId());
		}

		if (!userGroupIds.isEmpty()) {
			return ArrayUtil.toLongArray(userGroupIds);
		}

		return new long[0];
	}

	protected List<UserGroup> getUserGroups(int start, int end)
		throws PortalException {

		List<UserGroup> userGroups = new ArrayList<>();

		List<CommercePriceListUserRel> commercePriceListUserRels =
			_commercePriceListUserRelService.getCommercePriceListUserRels(
				getCommercePriceListQualificationTypeRelId(),
				UserGroup.class.getName(), start, end);

		for (CommercePriceListUserRel commercePriceListUserRel :
				commercePriceListUserRels) {

			UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
				commercePriceListUserRel.getClassPK());

			if (userGroup != null) {
				userGroups.add(userGroup);
			}
		}

		return userGroups;
	}

	private final CommercePriceListUserRelService
		_commercePriceListUserRelService;
	private final ItemSelector _itemSelector;
	private final UserGroupLocalService _userGroupLocalService;

}