/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.application.list;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = {
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU,
		"panel.category.order:Integer=100"
	},
	service = PanelCategory.class
)
public class ControlPanelCategoryWrapper extends BasePanelCategory {

	@Override
	public String getKey() {
		return _rootControlPanelCategory.getKey();
	}

	@Override
	public String getLabel(Locale locale) {
		return _rootControlPanelCategory.getLabel(locale);
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (_rootControlPanelCategory.isShow(permissionChecker, group)) {
			return false;
		}

		User user = permissionChecker.getUser();

		if (_organizationPermission.contains(
				permissionChecker, user.getOrganizationIds(true),
				AccountActionKeys.MANAGE_ACCOUNTS)) {

			return true;
		}

		return false;
	}

	@Reference
	private OrganizationPermission _organizationPermission;

	@Reference(
		target = "(component.name=com.liferay.product.navigation.control.panel.internal.application.list.ControlPanelCategory)"
	)
	private PanelCategory _rootControlPanelCategory;

}