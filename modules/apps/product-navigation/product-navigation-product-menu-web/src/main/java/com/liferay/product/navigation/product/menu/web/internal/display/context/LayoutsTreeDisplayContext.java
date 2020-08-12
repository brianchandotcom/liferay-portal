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

package com.liferay.product.navigation.product.menu.web.internal.display.context;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;
import com.liferay.product.navigation.product.menu.web.internal.constants.ProductNavigationProductMenuWebKeys;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Pavel Savinov
 */
public class LayoutsTreeDisplayContext {

	public LayoutsTreeDisplayContext(
		LiferayPortletRequest liferayPortletRequest) {

		_liferayPortletRequest = liferayPortletRequest;

		_groupProvider = (GroupProvider)liferayPortletRequest.getAttribute(
			ApplicationListWebKeys.GROUP_PROVIDER);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAddChildCollectionURLTemplate() throws Exception {
		return StringBundler.concat(
			getAddCollectionLayoutURL(), StringPool.AMPERSAND,
			PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES),
			"selPlid={plid}");
	}

	public String getAddChildURLTemplate() throws Exception {
		return StringBundler.concat(
			getAddLayoutURL(), StringPool.AMPERSAND,
			PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES),
			"selPlid={plid}");
	}

	public String getAddCollectionLayoutURL() throws Exception {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return StringPool.BLANK;
		}

		PortletURL addLayoutURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		addLayoutURL.setParameter("mvcPath", "/select_layout_collections.jsp");

		Layout layout = _themeDisplay.getLayout();

		addLayoutURL.setParameter(
			"redirect", PortalUtil.getLayoutFullURL(layout, _themeDisplay));
		addLayoutURL.setParameter(
			"backURL", PortalUtil.getLayoutFullURL(layout, _themeDisplay));

		addLayoutURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getSiteGroupId()));
		addLayoutURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));

		return addLayoutURL.toString();
	}

	public String getAddLayoutURL() throws Exception {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return StringPool.BLANK;
		}

		PortletURL addLayoutURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		addLayoutURL.setParameter(
			"mvcPath", "/select_layout_page_template_entry.jsp");

		Layout layout = _themeDisplay.getLayout();

		addLayoutURL.setParameter(
			"redirect", PortalUtil.getLayoutFullURL(layout, _themeDisplay));
		addLayoutURL.setParameter(
			"backURL", PortalUtil.getLayoutFullURL(layout, _themeDisplay));

		addLayoutURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getSiteGroupId()));
		addLayoutURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));

		return addLayoutURL.toString();
	}

	public String getAdministrationPortletURL() {
		PortletURL administrationPortletURL =
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE);

		administrationPortletURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());

		return administrationPortletURL.toString();
	}

	public String getConfigureLayoutSetURL() throws PortalException {
		PortletURL configureLayoutSetURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		configureLayoutSetURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout_set");

		Layout layout = _themeDisplay.getLayout();

		configureLayoutSetURL.setParameter(
			"redirect", PortalUtil.getLayoutFullURL(layout, _themeDisplay));
		configureLayoutSetURL.setParameter(
			"backURL", PortalUtil.getLayoutFullURL(layout, _themeDisplay));

		configureLayoutSetURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		configureLayoutSetURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));

		return configureLayoutSetURL.toString();
	}

	public String getConfigureLayoutURL() throws PortalException {
		PortletURL configureLayoutURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		configureLayoutURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout");

		Layout layout = _themeDisplay.getLayout();

		if (layout.isTypeAssetDisplay() || layout.isTypeControlPanel()) {
			String redirect = ParamUtil.getString(
				_liferayPortletRequest, "redirect",
				_themeDisplay.getURLCurrent());

			configureLayoutURL.setParameter("redirect", redirect);
			configureLayoutURL.setParameter("backURL", redirect);
		}
		else {
			configureLayoutURL.setParameter(
				"redirect", PortalUtil.getLayoutFullURL(layout, _themeDisplay));
			configureLayoutURL.setParameter(
				"backURL", PortalUtil.getLayoutFullURL(layout, _themeDisplay));
		}

		configureLayoutURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		configureLayoutURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));

		return configureLayoutURL.toString();
	}

	public String getConfigureLayoutURLTemplate() throws Exception {
		return StringBundler.concat(
			getConfigureLayoutURL(), StringPool.AMPERSAND,
			PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES),
			"selPlid={plid}");
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		Group group = _groupProvider.getGroup(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		if (group != null) {
			_groupId = group.getGroupId();
		}
		else {
			_groupId = _themeDisplay.getSiteGroupId();
		}

		return _groupId;
	}

	public Map<String, Object> getLayoutFinderData() {
		LiferayPortletURL findLayoutsURL = PortletURLFactoryUtil.create(
			_liferayPortletRequest,
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU,
			PortletRequest.RESOURCE_PHASE);

		findLayoutsURL.setResourceID("/product_menu/find_layouts");

		return HashMapBuilder.<String, Object>put(
			"administrationPortletNamespace",
			PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES)
		).put(
			"administrationPortletURL", getAdministrationPortletURL()
		).put(
			"findLayoutsURL", findLayoutsURL.toString()
		).put(
			"namespace", getNamespace()
		).put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).build();
	}

	public String getNamespace() {
		return PortalUtil.getPortletNamespace(
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU);
	}

	public Map<String, Object> getPageTypeSelectorData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"addCollectionLayoutURL", getAddCollectionLayoutURL()
		).put(
			"addLayoutURL", getAddLayoutURL()
		).put(
			"configureLayoutSetURL", getConfigureLayoutSetURL()
		).put(
			"namespace", getNamespace()
		).put(
			"privateLayout", isPrivateLayout()
		).build();
	}

	public String getViewCollectionItemsURL()
		throws PortalException, WindowStateException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_liferayPortletRequest, AssetListEntry.class.getName(),
			PortletProvider.Action.BROWSE);

		if (portletURL == null) {
			return StringPool.BLANK;
		}

		Layout layout = _themeDisplay.getLayout();

		String redirect = PortalUtil.getLayoutRelativeURL(
			_themeDisplay.getLayout(), _themeDisplay);

		if (layout.isTypeAssetDisplay() || layout.isTypeControlPanel()) {
			redirect = ParamUtil.getString(
				_liferayPortletRequest, "redirect", redirect);
		}

		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("showActions", String.valueOf(Boolean.TRUE));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return StringBundler.concat(
			portletURL, StringPool.AMPERSAND,
			PortalUtil.getPortletNamespace(AssetListPortletKeys.ASSET_LIST),
			"collectionPK={collectionPK}", StringPool.AMPERSAND,
			PortalUtil.getPortletNamespace(AssetListPortletKeys.ASSET_LIST),
			"collectionType={collectionType}");
	}

	public boolean isPrivateLayout() {
		Layout layout = _themeDisplay.getLayout();

		return GetterUtil.getBoolean(
			SessionClicks.get(
				PortalUtil.getHttpServletRequest(_liferayPortletRequest),
				getNamespace() +
					ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT,
				"false"),
			layout.isPrivateLayout());
	}

	private Long _groupId;
	private final GroupProvider _groupProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final ThemeDisplay _themeDisplay;

}