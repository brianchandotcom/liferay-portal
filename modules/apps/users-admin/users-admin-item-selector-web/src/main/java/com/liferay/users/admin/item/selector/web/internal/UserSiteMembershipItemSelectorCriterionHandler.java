/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.item.selector.web.internal;

import com.liferay.item.selector.BaseItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorCriterionHandler;
import com.liferay.users.admin.item.selector.UserSiteMembershipItemSelectorCriterion;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ItemSelectorCriterionHandler.class)
public class UserSiteMembershipItemSelectorCriterionHandler
	extends BaseItemSelectorCriterionHandler
		<UserSiteMembershipItemSelectorCriterion> {

	@Override
	public Class<UserSiteMembershipItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return UserSiteMembershipItemSelectorCriterion.class;
	}

	@Activate
	@Override
	protected void activate(BundleContext bundleContext) {
		super.activate(bundleContext);
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();
	}

}