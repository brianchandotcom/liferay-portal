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

package com.liferay.commerce.account.admin.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.users.admin.item.selector.UserItemSelectorCriterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountUserRelAdminDisplayContext
	extends BaseCommerceAccountAdminDisplayContext<CommerceAccountUserRel> {

	public CommerceAccountUserRelAdminDisplayContext(
		CommerceAccountService commerceAccountService,
		CommerceAccountUserRelService commerceAccountUserRelService,
		ModelResourcePermission<CommerceAccount> modelResourcePermission,
		ItemSelector itemSelector, RenderRequest renderRequest) {

		super(commerceAccountService, modelResourcePermission, renderRequest);

		_commerceAccountUserRelService = commerceAccountUserRelService;
		_itemSelector = itemSelector;
	}

	public String getEditUserURL(long userId) throws PortalException {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				commerceAccountAdminRequestHelper.getRequest(),
				User.class.getName(), PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/users_admin/edit_user"
		).setRedirect(
			commerceAccountAdminRequestHelper.getCurrentURL()
		).setParameter(
			"p_u_i_d", userId
		).buildString();
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				commerceAccountAdminRequestHelper.getRequest());

		UserItemSelectorCriterion userItemSelectorCriterion =
			new UserItemSelectorCriterion();

		userItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, "usersSelectItem",
				userItemSelectorCriterion)
		).setParameter(
			"checkedUserIds", StringUtil.merge(getCheckedUserIds())
		).buildString();
	}

	@Override
	public PortletURL getPortletURL() {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setMVCRenderCommandName(
			"/commerce_account_admin/edit_commerce_account"
		).build();
	}

	@Override
	public SearchContainer<CommerceAccountUserRel> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceAccountAdminRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-users");

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				commerceAccountAdminRequestHelper.getLiferayPortletResponse()));

		int total =
			_commerceAccountUserRelService.getCommerceAccountUserRelsCount(
				getCommerceAccountId());

		List<CommerceAccountUserRel> results = _getCommerceAccountUserRels(
			_searchContainer.getStart(), _searchContainer.getEnd());

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public String getUserRoles(CommerceAccountUserRel commerceAccountUserRel)
		throws PortalException {

		List<Role> roles = new ArrayList<>();

		List<UserGroupRole> userGroupRoles =
			commerceAccountUserRel.getUserGroupRoles();

		for (UserGroupRole userGroupRole : userGroupRoles) {
			roles.add(userGroupRole.getRole());
		}

		return ListUtil.toString(roles, "name", StringPool.COMMA_AND_SPACE);
	}

	protected long[] getCheckedUserIds() throws PortalException {
		return ListUtil.toLongArray(_getUsers(), User.USER_ID_ACCESSOR);
	}

	private List<CommerceAccountUserRel> _getCommerceAccountUserRels(
			int start, int end)
		throws PortalException {

		return _commerceAccountUserRelService.getCommerceAccountUserRels(
			getCommerceAccountId(), start, end);
	}

	private List<User> _getUsers() throws PortalException {
		List<User> users = new ArrayList<>();

		List<CommerceAccountUserRel> commerceAccountUserRels =
			_getCommerceAccountUserRels(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceAccountUserRel commerceAccountUserRel :
				commerceAccountUserRels) {

			users.add(commerceAccountUserRel.getUser());
		}

		return users;
	}

	private final CommerceAccountUserRelService _commerceAccountUserRelService;
	private final ItemSelector _itemSelector;
	private SearchContainer<CommerceAccountUserRel> _searchContainer;

}