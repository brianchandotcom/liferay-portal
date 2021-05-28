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
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
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
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
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
		PortletURL addChildCollectionURL = getAddCollectionLayoutURL();

		if (addChildCollectionURL == null) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			addChildCollectionURL, StringPool.AMPERSAND,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
	}

	public String getAddChildURLTemplate() throws Exception {
		PortletURL addLayoutURL = getAddLayoutURL();

		if (addLayoutURL == null) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			addLayoutURL, StringPool.AMPERSAND,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
	}

	public PortletURL getAddCollectionLayoutURL() throws Exception {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		Layout layout = _themeDisplay.getLayout();

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_layout_collections.jsp"
		).setRedirect(
			PortalUtil.getLayoutFullURL(layout, _themeDisplay)
		).setBackURL(
			PortalUtil.getLayoutFullURL(layout, _themeDisplay)
		).setParameter(
			"groupId", _themeDisplay.getSiteGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).build();
	}

	public PortletURL getAddLayoutURL() throws Exception {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		Layout layout = _themeDisplay.getLayout();

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_layout_page_template_entry.jsp"
		).setRedirect(
			PortalUtil.getLayoutFullURL(layout, _themeDisplay)
		).setBackURL(
			PortalUtil.getLayoutFullURL(layout, _themeDisplay)
		).setParameter(
			"groupId", _themeDisplay.getSiteGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).build();
	}

	public String getAdministrationPortletURL() {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).buildString();
	}

	public PortletURL getConfigureLayoutSetURL() throws PortalException {
		Layout layout = _themeDisplay.getLayout();

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout_set"
		).setRedirect(
			PortalUtil.getLayoutFullURL(layout, _themeDisplay)
		).setBackURL(
			PortalUtil.getLayoutFullURL(layout, _themeDisplay)
		).setParameter(
			"groupId", _themeDisplay.getScopeGroupId()
		).setParameter(
			"privateLayout", isPrivateLayout()
		).build();
	}

	public String getConfigureLayoutURL() throws PortalException {
		PortletURL configureLayoutURL = PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).build();

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
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
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

	public Map<String, Object> getLayoutFinderData()
		throws WindowStateException {

		return HashMapBuilder.<String, Object>put(
			"administrationPortletNamespace",
			PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES)
		).put(
			"administrationPortletURL", getAdministrationPortletURL()
		).put(
			"findLayoutsURL",
			() -> {
				LiferayPortletURL findLayoutsURL = PortletURLFactoryUtil.create(
					_liferayPortletRequest,
					ProductNavigationProductMenuPortletKeys.
						PRODUCT_NAVIGATION_PRODUCT_MENU,
					PortletRequest.RESOURCE_PHASE);

				findLayoutsURL.setResourceID(
					"/product_navigation_product_menu/find_layouts");

				return findLayoutsURL.toString();
			}
		).put(
			"namespace", getNamespace()
		).put(
			"productMenuPortletURL", getProductMenuPortletURL()
		).put(
			"spritemap", _themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).build();
	}

	public String getNamespace() {
		return PortalUtil.getPortletNamespace(
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU);
	}

	public Map<String, Object> getPageTypeSelectorData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"addCollectionLayoutURL", _setSelPlid(getAddCollectionLayoutURL())
		).put(
			"addLayoutURL", _setSelPlid(getAddLayoutURL())
		).put(
			"configureLayoutSetURL",
			() -> {
				if (!hasConfigureLayoutPermission()) {
					return StringPool.BLANK;
				}

				return _setSelPlid(getConfigureLayoutSetURL());
			}
		).put(
			"namespace", getNamespace()
		).put(
			"privateLayout", isPrivateLayout()
		).put(
			"showAddIcon",
			() -> {
				if (!hasAddLayoutPermission()) {
					return false;
				}

				Group scopeGroup = _themeDisplay.getScopeGroup();

				if (scopeGroup.isStaged() &&
					Objects.equals(
						scopeGroup, StagingUtil.getLiveGroup(scopeGroup))) {

					return false;
				}

				return true;
			}
		).build();
	}

	public String getProductMenuPortletURL() throws WindowStateException {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_liferayPortletRequest,
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/portlet/product_menu.jsp"
		).setWindowState(
			LiferayWindowState.EXCLUSIVE
		).buildString();
	}

	public long getSelPlid() {
		Layout layout = _themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return ParamUtil.get(
				_liferayPortletRequest, "selPlid",
				LayoutConstants.DEFAULT_PLID);
		}

		if (layout.isSystem() && layout.isTypeContent()) {
			return layout.getClassPK();
		}

		return layout.getPlid();
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
			"collectionPK={collectionPK}&",
			PortalUtil.getPortletNamespace(AssetListPortletKeys.ASSET_LIST),
			"collectionType={collectionType}");
	}

	public boolean hasAddLayoutPermission() throws PortalException {
		if (GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup(), ActionKeys.ADD_LAYOUT)) {

			return true;
		}

		return false;
	}

	public boolean hasAdministrationPortletPermission() throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			_themeDisplay.getCompanyId(), LayoutAdminPortletKeys.GROUP_PAGES);

		if (portlet == null) {
			return false;
		}

		ControlPanelEntry controlPanelEntry =
			portlet.getControlPanelEntryInstance();

		if (!controlPanelEntry.hasAccessPermission(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup(), portlet)) {

			return false;
		}

		return true;
	}

	public boolean hasConfigureLayoutPermission() throws PortalException {
		if (GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup(), ActionKeys.MANAGE_LAYOUTS)) {

			return true;
		}

		return false;
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

	private String _setSelPlid(PortletURL portletURL) {
		if (portletURL == null) {
			return StringPool.BLANK;
		}

		portletURL.setParameter(
			"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));

		return portletURL.toString();
	}

	private Long _groupId;
	private final GroupProvider _groupProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final ThemeDisplay _themeDisplay;

}