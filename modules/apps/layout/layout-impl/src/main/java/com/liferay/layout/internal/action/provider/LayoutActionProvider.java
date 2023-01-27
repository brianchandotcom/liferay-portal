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

package com.liferay.layout.internal.action.provider;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.url.builder.ActionURLBuilder;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class LayoutActionProvider {

	public LayoutActionProvider(
		HttpServletRequest httpServletRequest, Language language,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;

		_groupProvider = (GroupProvider)httpServletRequest.getAttribute(
			ApplicationListWebKeys.GROUP_PROVIDER);
		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST));
		_namespace = PortalUtil.getPortletNamespace(
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JSONArray getActionsJSONArray(
			Layout layout, Layout afterDeleteSelectedLayout)
		throws Exception {

		JSONArray itemsJSONArray = JSONFactoryUtil.createJSONArray();

		if (_isShowPreviewDraftAction(layout)) {
			itemsJSONArray.put(
				JSONUtil.put(
					"href",
					PortalUtil.getLayoutFriendlyURL(
						layout.fetchDraftLayout(), _themeDisplay)
				).put(
					"id", "preview-draft"
				).put(
					"label",
					_language.get(_themeDisplay.getLocale(), "preview-draft")
				).put(
					"symbolRight", "shortcut"
				).put(
					"target", "_blank"
				).put(
					"type", "item"
				));
		}

		Map<String, String> valuesMap = HashMapBuilder.put(
			"collectionPK", layout.getTypeSettingsProperty("collectionPK")
		).put(
			"collectionType", layout.getTypeSettingsProperty("collectionType")
		).put(
			"plid", String.valueOf(layout.getPlid())
		).build();

		if (!layout.isTypeCollection() &&
			Validator.isNotNull(_getAddChildURLTemplate())) {

			itemsJSONArray.put(
				JSONUtil.put(
					"href",
					StringUtil.replace(
						_getAddChildURLTemplate(), StringPool.OPEN_CURLY_BRACE,
						StringPool.CLOSE_CURLY_BRACE, valuesMap)
				).put(
					"id", "add-child-page"
				).put(
					"label",
					_language.get(_themeDisplay.getLocale(), "add-child-page")
				).put(
					"type", "item"
				));
		}

		if (!layout.isTypeCollection() &&
			Validator.isNotNull(_getAddChildCollectionURLTemplate())) {

			itemsJSONArray.put(
				JSONUtil.put(
					"href",
					StringUtil.replace(
						_getAddChildCollectionURLTemplate(),
						StringPool.OPEN_CURLY_BRACE,
						StringPool.CLOSE_CURLY_BRACE, valuesMap)
				).put(
					"id", "add-child-collection-page"
				).put(
					"label",
					_language.get(
						_themeDisplay.getLocale(), "add-child-collection-page")
				).put(
					"type", "item"
				));
		}

		itemsJSONArray.put(
			JSONUtil.put("type", "divider")
		).put(
			JSONUtil.put(
				"data",
				JSONUtil.put(
					"id", "copy-page"
				).put(
					"modalTitle",
					_language.get(_themeDisplay.getLocale(), "copy-page")
				).put(
					"url", _getCopyLayoutRenderURL(layout)
				)
			).put(
				"href", StringPool.POUND
			).put(
				"id", "copy-page"
			).put(
				"label", _language.get(_themeDisplay.getLocale(), "copy-page")
			).put(
				"symbolLeft", "copy"
			).put(
				"type", "item"
			)
		).put(
			JSONUtil.put("type", "divider")
		).put(
			JSONUtil.put(
				"href",
				StringUtil.replace(
					_getConfigureLayoutURLTemplate(),
					StringPool.OPEN_CURLY_BRACE, StringPool.CLOSE_CURLY_BRACE,
					valuesMap)
			).put(
				"id", "configure"
			).put(
				"label", _language.get(_themeDisplay.getLocale(), "configure")
			).put(
				"symbolLeft", "cog"
			).put(
				"type", "item"
			)
		);

		if (layout.isTypeCollection() &&
			Validator.isNotNull(_getViewCollectionItemsURL())) {

			itemsJSONArray.put(
				JSONUtil.put(
					"data",
					JSONUtil.put(
						"id", "view-collection-items"
					).put(
						"modalTitle",
						_language.get(_themeDisplay.getLocale(), "view-items")
					).put(
						"url",
						StringUtil.replace(
							_getViewCollectionItemsURL(),
							StringPool.OPEN_CURLY_BRACE,
							StringPool.CLOSE_CURLY_BRACE, valuesMap)
					)
				).put(
					"href", StringPool.POUND
				).put(
					"id", "view-collection-items"
				).put(
					"label",
					_language.get(
						_themeDisplay.getLocale(), "view-collection-items")
				).put(
					"target", "_blank"
				).put(
					"type", "item"
				));
		}

		itemsJSONArray.put(
			JSONUtil.put(
				"data",
				JSONUtil.put(
					"id", "permissions"
				).put(
					"modalTitle",
					_language.get(_themeDisplay.getLocale(), "permissions")
				).put(
					"url", _getPermissionsURL(layout)
				)
			).put(
				"href", StringPool.POUND
			).put(
				"id", "permissions"
			).put(
				"label", _language.get(_themeDisplay.getLocale(), "permissions")
			).put(
				"symbolLeft", "password-policies"
			).put(
				"type", "item"
			)
		).put(
			JSONUtil.put("type", "divider")
		).put(
			JSONUtil.put(
				"data",
				HashMapBuilder.put(
					"message",
					() -> {
						String messageKey =
							"are-you-sure-you-want-to-delete-the-page-x.-it-" +
								"will-be-removed-immediately";

						if (layout.hasChildren() && _hasScopeGroup(layout)) {
							messageKey = StringBundler.concat(
								"are-you-sure-you-want-to-delete-the-page-x.-",
								"this-page-serves-as-a-scope-for-content-and-",
								"also-contains-child-pages");
						}
						else if (layout.hasChildren()) {
							messageKey = StringBundler.concat(
								"are-you-sure-you-want-to-delete-the-page-x.-",
								"this-page-contains-child-pages-that-will-",
								"also-be-removed");
						}
						else if (_hasScopeGroup(layout)) {
							messageKey =
								"are-you-sure-you-want-to-delete-the-page-x.-" +
									"this-page-serves-as-a-scope-for-content";
						}

						return _language.format(
							_httpServletRequest, messageKey,
							HtmlUtil.escape(
								layout.getName(_themeDisplay.getLocale())));
					}
				).put(
					"modalTitle",
					_language.get(_themeDisplay.getLocale(), "delete-page")
				).put(
					"url",
					_getDeleteLayoutURL(layout, afterDeleteSelectedLayout)
				).build()
			).put(
				"id", "delete"
			).put(
				"label", _language.get(_themeDisplay.getLocale(), "delete")
			).put(
				"symbolLeft", "trash"
			).put(
				"type", "item"
			)
		);

		return JSONUtil.putAll(
			JSONUtil.put(
				"items", itemsJSONArray
			).put(
				"type", "group"
			));
	}

	private String _getAddChildCollectionURLTemplate() throws Exception {
		PortletURL addChildCollectionURL = _getAddCollectionLayoutURL();

		if (addChildCollectionURL == null) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			addChildCollectionURL, StringPool.AMPERSAND,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
	}

	private String _getAddChildURLTemplate() throws Exception {
		PortletURL addLayoutURL = _getAddLayoutURL();

		if (addLayoutURL == null) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			addLayoutURL, StringPool.AMPERSAND,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
	}

	private PortletURL _getAddCollectionLayoutURL() throws Exception {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_layout_collections.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", _themeDisplay.getSiteGroupId()
		).setParameter(
			"privateLayout", _isPrivateLayout()
		).buildPortletURL();
	}

	private PortletURL _getAddLayoutURL() throws Exception {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/select_layout_page_template_entry.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			_getBackURL()
		).setParameter(
			"groupId", _themeDisplay.getSiteGroupId()
		).setParameter(
			"privateLayout", _isPrivateLayout()
		).buildPortletURL();
	}

	private String _getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		String backURL = ParamUtil.getString(_httpServletRequest, "backURL");

		if (Validator.isNull(backURL)) {
			backURL = ParamUtil.getString(
				PortalUtil.getOriginalServletRequest(_httpServletRequest),
				"backURL", _themeDisplay.getURLCurrent());
		}

		_backURL = backURL;

		return backURL;
	}

	private String _getConfigureLayoutURL() throws Exception {
		PortletURL configureLayoutURL = PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).buildPortletURL();

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
			"privateLayout", String.valueOf(_isPrivateLayout()));

		return configureLayoutURL.toString();
	}

	private String _getConfigureLayoutURLTemplate() throws Exception {
		return StringBundler.concat(
			_getConfigureLayoutURL(), StringPool.AMPERSAND,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE, "selPlid={plid}");
	}

	private String _getCopyLayoutRenderURL(Layout layout) {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/add_layout"
		).setRedirect(
			ParamUtil.getString(
				_liferayPortletRequest, "redirect",
				_themeDisplay.getURLCurrent())
		).setParameter(
			"privateLayout", layout.isPrivateLayout()
		).setParameter(
			"sourcePlid", layout.getPlid()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private String _getDeleteLayoutURL(
			Layout layout, Layout afterDeleteSelectedLayout)
		throws Exception {

		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isStaged() && !scopeGroup.isStagingGroup()) {
			return null;
		}

		String redirect = ParamUtil.getString(
			_liferayPortletRequest, "redirect", _themeDisplay.getURLCurrent());

		Layout curLayout = _themeDisplay.getLayout();

		Layout draftLayout = layout.fetchDraftLayout();

		if (Objects.equals(curLayout.getPlid(), layout.getPlid()) ||
			((draftLayout != null) &&
			 Objects.equals(curLayout.getPlid(), draftLayout.getPlid()))) {

			if (afterDeleteSelectedLayout != null) {
				redirect = PortalUtil.getLayoutRelativeURL(
					afterDeleteSelectedLayout, _themeDisplay);
			}
			else {
				redirect = String.valueOf(
					PortalUtil.getControlPanelPortletURL(
						_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
						PortletRequest.RENDER_PHASE));
			}
		}

		return ActionURLBuilder.createActionURL(
			(ActionURL)PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/layout_admin/delete_layout"
		).setRedirect(
			redirect
		).setParameter(
			"selPlid", String.valueOf(layout.getPlid())
		).buildString();
	}

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		Group group = _groupProvider.getGroup(_httpServletRequest);

		if (group != null) {
			_groupId = group.getGroupId();
		}
		else {
			_groupId = _themeDisplay.getSiteGroupId();
		}

		return _groupId;
	}

	private String _getPageTypeSelectedOption() {
		if (_pageTypeSelectedOption != null) {
			return _pageTypeSelectedOption;
		}

		String pageTypeSelectedOption =
			ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT;

		String pageTypeSelectedOptionSessionValue = SessionClicks.get(
			_httpServletRequest,
			_namespace +
				ProductNavigationProductMenuWebKeys.PAGE_TYPE_SELECTED_OPTION,
			ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT);

		if (_isValidPageTypeSelectedOption(
				pageTypeSelectedOptionSessionValue)) {

			pageTypeSelectedOption = pageTypeSelectedOptionSessionValue;
		}

		_pageTypeSelectedOption = pageTypeSelectedOption;

		return _pageTypeSelectedOption;
	}

	private String _getPermissionsURL(Layout layout) throws Exception {
		return PermissionsURLTag.doTag(
			StringPool.BLANK, Layout.class.getName(),
			HtmlUtil.escape(layout.getName(_themeDisplay.getLocale())), null,
			String.valueOf(layout.getPlid()),
			LiferayWindowState.POP_UP.toString(), null,
			_themeDisplay.getRequest());
	}

	private String _getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = PortalUtil.escapeRedirect(_getBackURL());
		}

		_redirect = redirect;

		return _redirect;
	}

	private String _getViewCollectionItemsURL() throws Exception {
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

	private boolean _hasScopeGroup(Layout layout) throws Exception {
		if (layout.hasScopeGroup()) {
			return true;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout == null) {
			return false;
		}

		return draftLayout.hasScopeGroup();
	}

	private boolean _isPageHierarchyOption(String pageTypeOption) {
		if (Objects.equals(
				pageTypeOption,
				ProductNavigationProductMenuWebKeys.PUBLIC_LAYOUT) ||
			Objects.equals(
				pageTypeOption,
				ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT)) {

			return true;
		}

		return false;
	}

	private boolean _isPrivateLayout() {
		return Objects.equals(
			ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT,
			_getPageTypeSelectedOption());
	}

	private boolean _isShowPreviewDraftAction(Layout layout) throws Exception {
		if (!layout.isTypeContent() ||
			!LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), layout,
				ActionKeys.UPDATE)) {

			return false;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout == null) {
			return false;
		}

		if (draftLayout.isDraft() || !layout.isPublished()) {
			return true;
		}

		return false;
	}

	private boolean _isValidPageTypeSelectedOption(
		String pageTypeSelectedOption) {

		if (_isPageHierarchyOption(pageTypeSelectedOption)) {
			return true;
		}

		long siteNavigationMenuId = GetterUtil.getLong(pageTypeSelectedOption);

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
				siteNavigationMenuId);

		if ((siteNavigationMenu != null) &&
			(siteNavigationMenu.getGroupId() == _getGroupId())) {

			return true;
		}

		return false;
	}

	private String _backURL;
	private Long _groupId;
	private final GroupProvider _groupProvider;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final String _namespace;
	private String _pageTypeSelectedOption;
	private String _redirect;
	private final SiteNavigationMenuLocalService
		_siteNavigationMenuLocalService;
	private final ThemeDisplay _themeDisplay;

}