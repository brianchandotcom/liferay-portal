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

package com.liferay.commerce.user.segment.web.internal.display.context;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributorRegistry;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
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
public class UserGroupCommerceUserSegmentCriterionTypeDisplayContext
	extends CommerceUserSegmentDisplayContext {

	public UserGroupCommerceUserSegmentCriterionTypeDisplayContext(
		CommerceUserSegmentCriterionService commerceUserSegmentCriterionService,
		CommerceUserSegmentCriterionTypeJSPContributorRegistry
			commerceUserSegmentCriterionTypeJSPContributorRegistry,
		CommerceUserSegmentCriterionTypeRegistry
			commerceUserSegmentCriterionTypeRegistry,
		CommerceUserSegmentEntryService commerceUserSegmentEntryService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		UserGroupLocalService userGroupLocalService) {

		super(
			commerceUserSegmentCriterionService,
			commerceUserSegmentCriterionTypeJSPContributorRegistry,
			commerceUserSegmentCriterionTypeRegistry,
			commerceUserSegmentEntryService, httpServletRequest);

		_itemSelector = itemSelector;
		_userGroupLocalService = userGroupLocalService;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				commerceUserSegmentRequestHelper.getRequest());

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

	public List<UserGroup> getUserGroups() throws PortalException {
		List<UserGroup> userGroups = new ArrayList<>();

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			getCommerceUserSegmentCriterion();

		if (commerceUserSegmentCriterion == null) {
			return userGroups;
		}

		String[] userGroupIds = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings());

		for (String userGroupId : userGroupIds) {
			UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
				Long.parseLong(userGroupId));

			if (userGroup != null) {
				userGroups.add(userGroup);
			}
		}

		return userGroups;
	}

	protected long[] getCheckedUserGroupIds() throws PortalException {
		return ListUtil.toLongArray(
			getUserGroups(), UserGroup.USER_GROUP_ID_ACCESSOR);
	}

	private final ItemSelector _itemSelector;
	private final UserGroupLocalService _userGroupLocalService;

}