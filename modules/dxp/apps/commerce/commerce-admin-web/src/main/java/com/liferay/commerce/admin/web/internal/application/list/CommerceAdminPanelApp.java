/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.admin.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.admin.web.internal.util.CommerceAdminModuleRegistry;
import com.liferay.commerce.application.list.constants.CommercePanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=1000",
		"panel.category.key=" + CommercePanelCategoryKeys.SITE_ADMINISTRATION_COMMERCE
	},
	service = PanelApp.class
)
public class CommerceAdminPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return CommerceAdminPortletKeys.COMMERCE_ADMIN;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		boolean show = super.isShow(permissionChecker, group);

		if (show) {
			Map<String, CommerceAdminModule> commerceAdminModules =
				_commerceAdminModuleRegistry.getCommerceAdminModules();

			if (commerceAdminModules.isEmpty()) {
				show = false;
			}
		}

		return show;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

	@Reference
	private CommerceAdminModuleRegistry _commerceAdminModuleRegistry;

}