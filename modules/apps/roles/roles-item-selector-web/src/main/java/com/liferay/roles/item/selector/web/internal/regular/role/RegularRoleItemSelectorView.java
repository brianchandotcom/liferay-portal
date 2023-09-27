/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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