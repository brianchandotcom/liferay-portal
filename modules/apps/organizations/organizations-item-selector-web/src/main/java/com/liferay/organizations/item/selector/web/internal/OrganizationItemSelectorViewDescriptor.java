/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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