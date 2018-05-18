/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
public class UserCommerceUserSegmentCriterionTypeDisplayContext
	extends CommerceUserSegmentDisplayContext {

	public UserCommerceUserSegmentCriterionTypeDisplayContext(
		CommerceUserSegmentCriterionService commerceUserSegmentCriterionService,
		CommerceUserSegmentCriterionTypeJSPContributorRegistry
			commerceUserSegmentCriterionTypeJSPContributorRegistry,
		CommerceUserSegmentCriterionTypeRegistry
			commerceUserSegmentCriterionTypeRegistry,
		CommerceUserSegmentEntryService commerceUserSegmentEntryService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		UserLocalService userLocalService) {

		super(
			commerceUserSegmentCriterionService,
			commerceUserSegmentCriterionTypeJSPContributorRegistry,
			commerceUserSegmentCriterionTypeRegistry,
			commerceUserSegmentEntryService, httpServletRequest);

		_itemSelector = itemSelector;
		_userLocalService = userLocalService;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				commerceUserSegmentRequestHelper.getRequest());

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

	public List<User> getUsers() throws PortalException {
		List<User> users = new ArrayList<>();

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			getCommerceUserSegmentCriterion();

		if (commerceUserSegmentCriterion == null) {
			return users;
		}

		String[] userIds = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings());

		for (String userId : userIds) {
			User user = _userLocalService.fetchUser(GetterUtil.getLong(userId));

			if (user != null) {
				users.add(user);
			}
		}

		return users;
	}

	protected long[] getCheckedUserIds() throws PortalException {
		return ListUtil.toLongArray(getUsers(), User.USER_ID_ACCESSOR);
	}

	private final ItemSelector _itemSelector;
	private final UserLocalService _userLocalService;

}