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

package com.liferay.organizations.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.organizations.item.selector.OrganizationItemSelectorCriterion;
import com.liferay.organizations.item.selector.web.internal.display.context.OrganizationItemSelectorViewDisplayContext;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;

/**
 * @author Eudaldo Alonso
 */
public class OrganizationItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<Organization> {

	public OrganizationItemSelectorViewDescriptor(
		OrganizationItemSelectorCriterion organizationItemSelectorCriterion,
		OrganizationItemSelectorViewDisplayContext
			organizationItemSelectorViewDisplayContext) {

		_organizationItemSelectorCriterion = organizationItemSelectorCriterion;
		_organizationItemSelectorViewDisplayContext =
			organizationItemSelectorViewDisplayContext;
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public ItemDescriptor getItemDescriptor(Organization organization) {
		return new OrganizationItemDescriptor(organization);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"name", "type"};
	}

	@Override
	public SearchContainer<Organization> getSearchContainer()
		throws PortalException {

		return _organizationItemSelectorViewDisplayContext.getSearchContainer();
	}

	@Override
	public TableItemView getTableItemView(Organization organization) {
		return new OrganizationTableItemView(organization);
	}

	@Override
	public boolean isMultipleSelection() {
		return _organizationItemSelectorCriterion.isMultiSelection();
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final OrganizationItemSelectorCriterion
		_organizationItemSelectorCriterion;
	private final OrganizationItemSelectorViewDisplayContext
		_organizationItemSelectorViewDisplayContext;

}