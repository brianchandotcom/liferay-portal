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
import com.liferay.commerce.price.list.qualification.type.web.internal.price.RoleCommercePriceListQualificationTypeImpl;
import com.liferay.commerce.price.list.qualification.type.web.internal.util.CommercePriceListQualificationTypeUtil;
import com.liferay.commerce.price.list.web.display.context.BaseCommercePriceListDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
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
public class RoleCommercePriceListQualificationTypeDisplayContext
	extends BaseCommercePriceListDisplayContext<Role> {

	public RoleCommercePriceListQualificationTypeDisplayContext(
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommercePriceListUserRelService commercePriceListUserRelService,
		ItemSelector itemSelector, HttpServletRequest httpServletRequest,
		RoleLocalService roleLocalService) {

		super(commercePriceListActionHelper, httpServletRequest);

		_commercePriceListUserRelService = commercePriceListUserRelService;
		_itemSelector = itemSelector;
		_roleLocalService = roleLocalService;

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
					RoleCommercePriceListQualificationTypeImpl.KEY);
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

		RoleItemSelectorCriterion roleItemSelectorCriterion =
			new RoleItemSelectorCriterion();

		roleItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "rolesSelectItem",
			roleItemSelectorCriterion);

		String checkedRoleIds = StringUtil.merge(getCheckedRoleIds());

		itemSelectorURL.setParameter("checkedRoleIds", checkedRoleIds);

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
		return "roles";
	}

	@Override
	public SearchContainer<Role> getSearchContainer() throws PortalException {
		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, "there-are-no-roles");

		OrderByComparator<Role> orderByComparator =
			CommercePriceListQualificationTypeUtil.getRoleOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commercePriceListUserRelService.getCommercePriceListUserRelsCount(
				getCommercePriceListQualificationTypeRelId(),
				Role.class.getName());

		searchContainer.setTotal(total);

		List<Role> results = getRoles(
			searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long[] getCheckedRoleIds() throws PortalException {
		List<Long> roleIds = new ArrayList<>();

		List<Role> roles = getRoles(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Role role : roles) {
			roleIds.add(role.getRoleId());
		}

		if (!roleIds.isEmpty()) {
			return ArrayUtil.toLongArray(roleIds);
		}

		return new long[0];
	}

	protected List<Role> getRoles(int start, int end) throws PortalException {
		List<Role> roles = new ArrayList<>();

		List<CommercePriceListUserRel> commercePriceListUserRels =
			_commercePriceListUserRelService.getCommercePriceListUserRels(
				getCommercePriceListQualificationTypeRelId(),
				Role.class.getName(), start, end);

		for (CommercePriceListUserRel commercePriceListUserRel :
				commercePriceListUserRels) {

			Role role = _roleLocalService.fetchRole(
				commercePriceListUserRel.getClassPK());

			if (role != null) {
				roles.add(role);
			}
		}

		return roles;
	}

	private final CommercePriceListUserRelService
		_commercePriceListUserRelService;
	private final ItemSelector _itemSelector;
	private final RoleLocalService _roleLocalService;

}