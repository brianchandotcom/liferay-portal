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

package com.liferay.roles.item.selector.web.internal.organization.role;

import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.roles.item.selector.organization.role.OrganizationRoleItemSelectorCriterion;
import com.liferay.roles.item.selector.web.internal.BaseRoleItemSelectorView;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class OrganizationRoleItemSelectorView
	extends BaseRoleItemSelectorView<OrganizationRoleItemSelectorCriterion> {

	@Override
	public Class<OrganizationRoleItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return OrganizationRoleItemSelectorCriterion.class;
	}

	@Override
	public String getTitle(Locale locale) {
		return language.get(
			portal.getResourceBundle(locale), "organization-roles");
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_ORGANIZATION;
	}

	@Override
	protected long[] getCheckedRoleIds(
		OrganizationRoleItemSelectorCriterion
			organizationRoleItemSelectorCriterion) {

		return organizationRoleItemSelectorCriterion.getCheckedRoleIds();
	}

	@Override
	protected String[] getExcludedRoleNames(
		OrganizationRoleItemSelectorCriterion
			organizationRoleItemSelectorCriterion) {

		return organizationRoleItemSelectorCriterion.getExcludedRoleNames();
	}

}