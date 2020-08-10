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

package com.liferay.commerce.bom.admin.web.internal.util;

import com.liferay.commerce.bom.constants.CommerceBOMPortletKeys;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.product.util.CPNavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.navigation.item.key=" + CommerceBOMPortletKeys.COMMERCE_BOM_ADMIN,
		"commerce.product.navigation.item.order:Integer=40"
	},
	service = CPNavigationItem.class
)
public class CommerceBOMAdminNavigationItem implements CPNavigationItem {

	public NavigationItem getNavigationItem(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_portletPermission.contains(
				themeDisplay.getPermissionChecker(),
				CommerceBOMPortletKeys.COMMERCE_BOM_ADMIN, ActionKeys.VIEW)) {

			return null;
		}

		NavigationItem navigationItem = new NavigationItem();

		String portletId = _portal.getPortletId(portletRequest);

		navigationItem.setActive(
			portletId.equals(CommerceBOMPortletKeys.COMMERCE_BOM_ADMIN));

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			portletRequest, themeDisplay.getScopeGroup(),
			CommerceBOMFolder.class.getName(), PortletProvider.Action.MANAGE);

		navigationItem.setHref(portletURL.toString());

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		navigationItem.setLabel(LanguageUtil.get(resourceBundle, "bom"));

		return navigationItem;
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

}