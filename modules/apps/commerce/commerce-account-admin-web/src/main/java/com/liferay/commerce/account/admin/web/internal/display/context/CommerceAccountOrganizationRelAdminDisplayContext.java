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
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.organizations.item.selector.OrganizationItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountOrganizationRelAdminDisplayContext
	extends BaseCommerceAccountAdminDisplayContext
		<CommerceAccountOrganizationRel> {

	public CommerceAccountOrganizationRelAdminDisplayContext(
		CommerceAccountOrganizationRelService
			commerceAccountOrganizationRelService,
		CommerceAccountService commerceAccountService,
		ItemSelector itemSelector,
		ModelResourcePermission<CommerceAccount> modelResourcePermission,
		RenderRequest renderRequest) {

		super(commerceAccountService, modelResourcePermission, renderRequest);

		_commerceAccountOrganizationRelService =
			commerceAccountOrganizationRelService;
		_itemSelector = itemSelector;
	}

	public String getEditOrganizationURL(long organizationId)
		throws PortalException {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				commerceAccountAdminRequestHelper.getRequest(),
				User.class.getName(), PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/users_admin/edit_organization"
		).setRedirect(
			commerceAccountAdminRequestHelper.getCurrentURL()
		).setParameter(
			"organizationId", organizationId
		).buildString();
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				commerceAccountAdminRequestHelper.getRequest());

		OrganizationItemSelectorCriterion organizationItemSelectorCriterion =
			new OrganizationItemSelectorCriterion();

		organizationItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, "organizationsSelectItem",
				organizationItemSelectorCriterion)
		).setParameter(
			"checkedOrganizationIds",
			StringUtil.merge(getCheckedOrganizationIds())
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
	public SearchContainer<CommerceAccountOrganizationRel> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceAccountAdminRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-organizations");

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				commerceAccountAdminRequestHelper.getLiferayPortletResponse()));

		int total =
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRelsCount(getCommerceAccountId());

		List<CommerceAccountOrganizationRel> results =
			_getCommerceAccountOrganizationRels(
				_searchContainer.getStart(), _searchContainer.getEnd());

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected long[] getCheckedOrganizationIds() throws PortalException {
		return ListUtil.toLongArray(
			_getOrganizations(), Organization.ORGANIZATION_ID_ACCESSOR);
	}

	private List<CommerceAccountOrganizationRel>
			_getCommerceAccountOrganizationRels(int start, int end)
		throws PortalException {

		return _commerceAccountOrganizationRelService.
			getCommerceAccountOrganizationRels(
				getCommerceAccountId(), start, end);
	}

	private List<Organization> _getOrganizations() throws PortalException {
		List<Organization> organizations = new ArrayList<>();

		List<CommerceAccountOrganizationRel> commerceAccountOrganizationRels =
			_getCommerceAccountOrganizationRels(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceAccountOrganizationRel commerceAccountOrganizationRel :
				commerceAccountOrganizationRels) {

			organizations.add(commerceAccountOrganizationRel.getOrganization());
		}

		return organizations;
	}

	private final CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;
	private final ItemSelector _itemSelector;
	private SearchContainer<CommerceAccountOrganizationRel> _searchContainer;

}