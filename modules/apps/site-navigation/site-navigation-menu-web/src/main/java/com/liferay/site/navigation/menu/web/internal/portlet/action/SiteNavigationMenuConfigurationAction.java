/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.portlet.action;

import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.constants.SiteNavigationMenuPortletKeys;
import com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.io.IOException;

import java.util.Objects;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 * @author Raymond Augé
 */
@Component(
	property = "javax.portlet.name=" + SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
	service = ConfigurationAction.class
)
public class SiteNavigationMenuConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			SiteNavigationMenuWebKeys.ITEM_SELECTOR, _itemSelector);
		httpServletRequest.setAttribute(
			SiteNavigationMenuWebKeys.SITE_NAVIGATION_MENU_ITEM_TYPE_REGISTRY,
			_siteNavigationMenuItemTypeRegistry);

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	public void postProcess(
			long companyId, PortletRequest portletRequest, Settings settings)
		throws PortalException {

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.reset("included-layouts");

		_updateDisplayStyleGroupPreferences(modifiableSettings, portletRequest);
		_updateRootMenuItemPreferences(modifiableSettings);
		_updateSiteNavigationMenuPreferences(modifiableSettings);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_TEMPLATE, _portletDisplayTemplate);

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected SiteNavigationMenuItemLocalService
		siteNavigationMenuItemLocalService;

	@Reference
	protected SiteNavigationMenuService siteNavigationMenuService;

	private void _updateDisplayStyleGroupPreferences(
		ModifiableSettings modifiableSettings, PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String displayStyleGroupKey = modifiableSettings.getValue(
			"displayStyleGroupKey", null);

		Group group = groupLocalService.fetchGroup(
			themeDisplay.getCompanyId(), displayStyleGroupKey);

		if ((group != null) &&
			(group.getGroupId() != themeDisplay.getScopeGroupId())) {

			modifiableSettings.setValue(
				"displayStyleGroupExternalReferenceCode",
				group.getExternalReferenceCode());
		}
		else {
			modifiableSettings.reset("displayStyleGroupExternalReferenceCode");
		}
	}

	private void _updateRootMenuItemPreferences(
			ModifiableSettings modifiableSettings)
		throws PortalException {

		long rootMenuItemId = GetterUtil.getLong(
			modifiableSettings.getValue("rootMenuItemId", null));
		String rootMenuItemType = modifiableSettings.getValue(
			"rootMenuItemType", StringPool.BLANK);

		if ((rootMenuItemId == 0) ||
			!Objects.equals(rootMenuItemType, "select")) {

			modifiableSettings.reset("rootMenuItemExternalReferenceCode");
			modifiableSettings.reset("rootMenuItemId");
		}

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemLocalService.fetchSiteNavigationMenuItem(
				rootMenuItemId);

		if (siteNavigationMenuItem != null) {
			modifiableSettings.setValue(
				"rootMenuItemExternalReferenceCode",
				siteNavigationMenuItem.getExternalReferenceCode());

			return;
		}

		modifiableSettings.reset("rootMenuItemExternalReferenceCode");
	}

	private void _updateSiteNavigationMenuPreferences(
			ModifiableSettings modifiableSettings)
		throws PortalException {

		long siteNavigationMenuId = GetterUtil.getLong(
			modifiableSettings.getValue("siteNavigationMenuId", null));

		if (siteNavigationMenuId == 0) {
			modifiableSettings.reset("siteNavigationMenuExternalReferenceCode");

			return;
		}

		SiteNavigationMenu siteNavigationMenu =
			siteNavigationMenuService.fetchSiteNavigationMenu(
				siteNavigationMenuId);

		if (siteNavigationMenu != null) {
			modifiableSettings.setValue(
				"siteNavigationMenuExternalReferenceCode",
				siteNavigationMenu.getExternalReferenceCode());

			return;
		}

		modifiableSettings.reset("siteNavigationMenuExternalReferenceCode");
	}

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private PortletDisplayTemplate _portletDisplayTemplate;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

}