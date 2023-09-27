/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.item.selector.web.internal.site.role;

import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.roles.item.selector.site.role.SiteRoleItemSelectorCriterion;
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
public class SiteRoleItemSelectorView
	extends BaseRoleItemSelectorView<SiteRoleItemSelectorCriterion> {

	@Override
	public Class<SiteRoleItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return SiteRoleItemSelectorCriterion.class;
	}

	@Override
	public String getTitle(Locale locale) {
		return language.get(portal.getResourceBundle(locale), "site-roles");
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_SITE;
	}

	@Override
	protected long[] getCheckedRoleIds(
		SiteRoleItemSelectorCriterion siteRoleItemSelectorCriterion) {

		return siteRoleItemSelectorCriterion.getCheckedRoleIds();
	}

	@Override
	protected String[] getExcludedRoleNames(
		SiteRoleItemSelectorCriterion siteRoleItemSelectorCriterion) {

		return siteRoleItemSelectorCriterion.getExcludedRoleNames();
	}

}