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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.roles.item.selector.RoleItemSelectorCriterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class RoleCommerceUserSegmentCriterionTypeDisplayContext
	extends CommerceUserSegmentDisplayContext {

	public RoleCommerceUserSegmentCriterionTypeDisplayContext(
		CommerceUserSegmentCriterionService commerceUserSegmentCriterionService,
		CommerceUserSegmentCriterionTypeJSPContributorRegistry
			commerceUserSegmentCriterionTypeJSPContributorRegistry,
		CommerceUserSegmentCriterionTypeRegistry
			commerceUserSegmentCriterionTypeRegistry,
		CommerceUserSegmentEntryService commerceUserSegmentEntryService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		RoleLocalService roleLocalService) {

		super(
			commerceUserSegmentCriterionService,
			commerceUserSegmentCriterionTypeJSPContributorRegistry,
			commerceUserSegmentCriterionTypeRegistry,
			commerceUserSegmentEntryService, httpServletRequest);

		_itemSelector = itemSelector;
		_roleLocalService = roleLocalService;
	}

	public String getItemSelectorUrl(int type) throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				commerceUserSegmentRequestHelper.getRequest());

		RoleItemSelectorCriterion roleItemSelectorCriterion =
			new RoleItemSelectorCriterion();

		roleItemSelectorCriterion.setType(type);

		roleItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "rolesSelectItem",
			roleItemSelectorCriterion);

		String checkedRoleIds = StringUtil.merge(getCheckedRoleIds(type));

		itemSelectorURL.setParameter("checkedRoleIds", checkedRoleIds);

		return itemSelectorURL.toString();
	}

	public List<Role> getRoles(int type) throws PortalException {
		List<Role> roles = new ArrayList<>();

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			getCommerceUserSegmentCriterion();

		if (commerceUserSegmentCriterion == null) {
			return roles;
		}

		String[] roleIds = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings());

		for (String roleId : roleIds) {
			Role role = _roleLocalService.fetchRole(GetterUtil.getLong(roleId));

			if ((role != null) && (type == role.getType())) {
				roles.add(role);
			}
		}

		return roles;
	}

	protected long[] getCheckedRoleIds(int type) throws PortalException {
		return ListUtil.toLongArray(getRoles(type), Role.ROLE_ID_ACCESSOR);
	}

	private final ItemSelector _itemSelector;
	private final RoleLocalService _roleLocalService;

}