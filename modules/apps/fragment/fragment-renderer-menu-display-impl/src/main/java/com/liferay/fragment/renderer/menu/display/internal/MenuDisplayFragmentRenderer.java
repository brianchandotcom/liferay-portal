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

package com.liferay.fragment.renderer.menu.display.internal;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentConfiguration.ContextualMenu;
import com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentConfiguration.DisplayStyle;
import com.liferay.fragment.renderer.menu.display.internal.MenuDisplayFragmentConfiguration.SiteNavigationMenuSource;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuMode;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuTag;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = FragmentRenderer.class)
public class MenuDisplayFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "menu-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					getClass(),
					"/com/liferay/fragment/renderer/menu/display/internal" +
						"/dependencies/configuration.json"));

			return _fragmentEntryConfigurationParser.translateConfiguration(
				jsonObject, resourceBundle);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public String getIcon() {
		return "sites";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return LanguageUtil.get(resourceBundle, "menu-display");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			String fragmentElementId =
				fragmentRendererContext.getFragmentElementId();

			printWriter.write("<div id=\"" + fragmentElementId + "\">");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			MenuDisplayFragmentConfiguration menuDisplayFragmentConfiguration =
				_menuDisplayFragmentConfigurationParser.parse(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					themeDisplay.getScopeGroupId());

			_writeCss(
				fragmentElementId, menuDisplayFragmentConfiguration,
				printWriter);

			NavigationMenuTag navigationMenuTag = _getNavigationMenuTag(
				themeDisplay.getCompanyId(), menuDisplayFragmentConfiguration);

			navigationMenuTag.doTag(httpServletRequest, httpServletResponse);

			printWriter.write("</div>");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.renderer.menu.display.impl)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private void _configureMenu(
			MenuDisplayFragmentConfiguration menuDisplayFragmentConfiguration,
			NavigationMenuTag navigationMenuTag)
		throws PortalException {

		MenuDisplayFragmentConfiguration.Source source =
			menuDisplayFragmentConfiguration.getSource();

		if (source instanceof ContextualMenu) {
			ContextualMenu contextualMenu = (ContextualMenu)source;

			navigationMenuTag.setRootItemType("relative");

			if (contextualMenu == ContextualMenu.CHILDREN) {
				navigationMenuTag.setRootItemLevel(0);
			}
			else if (contextualMenu == ContextualMenu.PARENT_AND_ITS_SIBLINGS) {
				navigationMenuTag.setRootItemLevel(2);
			}
			else if (contextualMenu == ContextualMenu.SELF_AND_SIBLINGS) {
				navigationMenuTag.setRootItemLevel(1);
			}
		}
		else if (source instanceof SiteNavigationMenuSource) {
			SiteNavigationMenuSource siteNavigationMenuSource =
				(SiteNavigationMenuSource)source;

			navigationMenuTag.setNavigationMenuMode(
				NavigationMenuMode.PUBLIC_PAGES);

			if (siteNavigationMenuSource.isPrivateLayout()) {
				navigationMenuTag.setNavigationMenuMode(
					NavigationMenuMode.PRIVATE_PAGES);
			}

			navigationMenuTag.setRootItemType("select");

			long siteNavigationMenuId =
				siteNavigationMenuSource.getSiteNavigationMenuId();

			navigationMenuTag.setSiteNavigationMenuId(siteNavigationMenuId);

			long parentSiteNavigationMenuItemId =
				siteNavigationMenuSource.getParentSiteNavigationMenuItemId();

			if (parentSiteNavigationMenuItemId > 0) {
				if (_isLayoutHierarchy(siteNavigationMenuId)) {
					Layout layout = _layoutLocalService.fetchLayout(
						parentSiteNavigationMenuItemId);

					navigationMenuTag.setRootItemId(layout.getUuid());
				}
				else {
					navigationMenuTag.setRootItemId(
						String.valueOf(parentSiteNavigationMenuItemId));
				}
			}
		}

		navigationMenuTag.setDisplayDepth(
			menuDisplayFragmentConfiguration.sublevels() + 1);
	}

	private NavigationMenuTag _getNavigationMenuTag(
			long companyId,
			MenuDisplayFragmentConfiguration menuDisplayFragmentConfiguration)
		throws PortalException {

		NavigationMenuTag navigationMenuTag = new NavigationMenuTag();

		DDMTemplate ddmTemplate = _getTagDDMTemplate(
			companyId, menuDisplayFragmentConfiguration.getDisplayStyle());

		if (ddmTemplate != null) {
			navigationMenuTag.setDdmTemplateGroupId(ddmTemplate.getGroupId());
			navigationMenuTag.setDdmTemplateKey(ddmTemplate.getTemplateKey());
		}

		_configureMenu(menuDisplayFragmentConfiguration, navigationMenuTag);

		return navigationMenuTag;
	}

	private DDMTemplate _getTagDDMTemplate(
			long companyId, DisplayStyle displayStyle)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		String ddmTemplateKey = "NAVBAR-BLANK-FTL";

		if (displayStyle == DisplayStyle.STACKED) {
			ddmTemplateKey = "LIST-MENU-FTL";
		}

		return _ddmTemplateLocalService.fetchTemplate(
			companyGroup.getGroupId(), _portal.getClassNameId(NavItem.class),
			ddmTemplateKey);
	}

	private boolean _isLayoutHierarchy(long siteNavigationMenuId) {
		if (siteNavigationMenuId == 0) {
			return true;
		}

		return false;
	}

	private void _writeCss(
			String fragmentElementId,
			MenuDisplayFragmentConfiguration menuDisplayFragmentConfiguration,
			PrintWriter printWriter)
		throws IOException {

		String styles = StringUtil.replace(
			StringUtil.read(
				getClass(),
				"/com/liferay/fragment/renderer/menu/display/internal" +
					"/dependencies/styles.tmpl"),
			"${", "}",
			HashMapBuilder.put(
				"fragmentElementId", fragmentElementId
			).put(
				"hoveredItemColor",
				() -> {
					Optional<String> hoveredItemColorOptional =
						menuDisplayFragmentConfiguration.
							getHoveredItemColorOptional();

					return hoveredItemColorOptional.orElse("inherit");
				}
			).put(
				"selectedItemColor",
				() -> {
					Optional<String> selectedItemColorOptional =
						menuDisplayFragmentConfiguration.
							getSelectedItemColorOptional();

					return selectedItemColorOptional.orElse("inherit");
				}
			).build());

		printWriter.write(styles);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MenuDisplayFragmentRenderer.class);

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private MenuDisplayFragmentConfigurationParser
		_menuDisplayFragmentConfigurationParser;

	@Reference
	private Portal _portal;

	private ServletContext _servletContext;

}