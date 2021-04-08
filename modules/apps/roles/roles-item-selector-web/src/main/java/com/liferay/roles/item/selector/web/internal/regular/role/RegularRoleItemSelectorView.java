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

package com.liferay.roles.item.selector.web.internal.regular.role;

import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.roles.item.selector.regular.role.RegularRoleItemSelectorCriterion;
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
public class RegularRoleItemSelectorView
	extends BaseRoleItemSelectorView<RegularRoleItemSelectorCriterion> {

	@Override
	public Class<RegularRoleItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return RegularRoleItemSelectorCriterion.class;
	}

	@Override
	public String getTitle(Locale locale) {
		return language.get(portal.getResourceBundle(locale), "regular-roles");
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_REGULAR;
	}

	@Override
	protected long[] getCheckedRoleIds(
		RegularRoleItemSelectorCriterion regularRoleItemSelectorCriterion) {

		return regularRoleItemSelectorCriterion.getCheckedRoleIds();
	}

	@Override
	protected String[] getExcludedRoleNames(
		RegularRoleItemSelectorCriterion regularRoleItemSelectorCriterion) {

		return regularRoleItemSelectorCriterion.getExcludedRoleNames();
	}

}