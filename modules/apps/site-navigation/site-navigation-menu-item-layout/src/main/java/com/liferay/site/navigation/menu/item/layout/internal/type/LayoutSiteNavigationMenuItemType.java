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

package com.liferay.site.navigation.menu.item.layout.internal.type;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.staging.LayoutStaging;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletToken;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.navigation.constants.SiteNavigationWebKeys;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.menu.item.layout.internal.constants.SiteNavigationMenuItemTypeLayoutWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeContext;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"service.ranking:Integer=400",
		"site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.LAYOUT
	},
	service = SiteNavigationMenuItemType.class
)
public class LayoutSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public boolean exportData(
			PortletDataContext portletDataContext,
			Element siteNavigationMenuItemElement,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws PortalException {

		Layout layout = _getLayout(portletDataContext, siteNavigationMenuItem);

		if (layout == null) {
			return false;
		}

		boolean privateLayout = layout.isPrivateLayout();

		if (privateLayout != portletDataContext.isPrivateLayout()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Site navigation menu item ",
						siteNavigationMenuItem.getSiteNavigationMenuItemId(),
						" will not be exported because it points to a ",
						privateLayout ? "private" : "public",
						" layout. It will be exported when a ",
						privateLayout ? "private" : "public",
						" layout is exported."));
			}

			return false;
		}

		if (!ArrayUtil.contains(
				portletDataContext.getLayoutIds(), layout.getLayoutId())) {

			return false;
		}

		LayoutRevision layoutRevision = _layoutStaging.getLayoutRevision(
			layout);

		if ((layoutRevision != null) &&
			((layoutRevision.getStatus() == WorkflowConstants.STATUS_DRAFT) ||
			 layoutRevision.isIncomplete())) {

			return false;
		}

		siteNavigationMenuItemElement.addAttribute(
			"layout-friendly-url", layout.getFriendlyURL());

		portletDataContext.addReferenceElement(
			siteNavigationMenuItem, siteNavigationMenuItemElement, layout,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

		return true;
	}

	@Override
	public String getAddTitle(Locale locale) {
		return _language.format(locale, "select-x", "pages");
	}

	@Override
	public PortletURL getAddURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return PortletURLBuilder.createActionURL(
			renderResponse
		).setActionName(
			"/navigation_menu/add_layout_site_navigation_menu_item"
		).setParameter(
			"siteNavigationMenuItemType", getType()
		).buildPortletURL();
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getItemSelectorURL(HttpServletRequest httpServletRequest) {
		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new UUIDItemSelectorReturnType());
		layoutItemSelectorCriterion.setShowBreadcrumb(false);
		layoutItemSelectorCriterion.setShowHiddenPages(true);
		layoutItemSelectorCriterion.setMultiSelection(true);

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
				renderResponse.getNamespace() + "selectItem",
				layoutItemSelectorCriterion)
		).setParameter(
			"multipleSelection", isMultiSelection()
		).buildString();
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "page");
	}

	@Override
	public Layout getLayout(SiteNavigationMenuItem siteNavigationMenuItem) {
		return _fetchLayout(siteNavigationMenuItem);
	}

	@Override
	public String getRegularURL(
			HttpServletRequest httpServletRequest,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		return layout.getRegularURL(httpServletRequest);
	}

	@Override
	public String getResetLayoutURL(
			HttpServletRequest httpServletRequest,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		return layout.getResetLayoutURL(httpServletRequest);
	}

	@Override
	public String getResetMaxStateURL(
			HttpServletRequest httpServletRequest,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		return layout.getResetMaxStateURL(httpServletRequest);
	}

	@Override
	public String getSubtitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		Group group = layout.getGroup();

		if (!group.isPrivateLayoutsEnabled()) {
			return _language.get(locale, "page");
		}

		if (layout.isPublicLayout()) {
			return _language.get(locale, "public-page");
		}

		return _language.get(locale, "private-page");
	}

	@Override
	public String getTarget(SiteNavigationMenuItem siteNavigationMenuItem) {
		Layout layout = _fetchLayout(siteNavigationMenuItem);

		return layout.getTarget();
	}

	@Override
	public String getTitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		if (!_isUseCustomName(siteNavigationMenuItem)) {
			return layout.getName(locale);
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		String defaultLanguageId = typeSettingsUnicodeProperties.getProperty(
			Field.DEFAULT_LANGUAGE_ID,
			LocaleUtil.toLanguageId(LocaleUtil.getMostRelevantLocale()));

		String defaultTitle = typeSettingsUnicodeProperties.getProperty(
			"name_" + defaultLanguageId);

		if (layout != null) {
			defaultTitle = layout.getName(locale);
		}

		return typeSettingsUnicodeProperties.getProperty(
			"name_" + LocaleUtil.toLanguageId(locale), defaultTitle);
	}

	@Override
	public String getType() {
		return SiteNavigationMenuItemTypeConstants.LAYOUT;
	}

	@Override
	public String getTypeSettingsFromLayout(Layout layout) {
		return UnicodePropertiesBuilder.put(
			"groupId", String.valueOf(layout.getGroupId())
		).put(
			"layoutUuid", layout.getUuid()
		).put(
			"privateLayout", String.valueOf(layout.isPrivateLayout())
		).buildString();
	}

	@Override
	public String getUnescapedName(
		SiteNavigationMenuItem siteNavigationMenuItem, String languageId) {

		String title = getTitle(
			siteNavigationMenuItem, LocaleUtil.fromLanguageId(languageId));

		if (Validator.isNotNull(title)) {
			return title;
		}

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		return layout.getName(languageId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws PortalException {

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		return LayoutPermissionUtil.contains(
			permissionChecker, layout.getPlid(), ActionKeys.VIEW);
	}

	@Override
	public String iconURL(
		SiteNavigationMenuItem siteNavigationMenuItem, String pathImage) {

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		if ((layout == null) || !layout.isIconImage()) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			pathImage, "/layout_icon?img_id=", layout.getIconImageId(), "&t=",
			_webServerServletToken.getToken(layout.getIconImageId()));
	}

	@Override
	public boolean importData(
			PortletDataContext portletDataContext,
			SiteNavigationMenuItem siteNavigationMenuItem,
			SiteNavigationMenuItem importedSiteNavigationMenuItem)
		throws PortalException {

		Layout layout = _getLayout(
			portletDataContext, importedSiteNavigationMenuItem);

		if (layout == null) {
			if (ExportImportThreadLocal.isPortletImportInProcess()) {
				throw new NoSuchLayoutException();
			}

			return false;
		}

		LayoutRevision layoutRevision = _layoutStaging.getLayoutRevision(
			layout);

		if ((layoutRevision != null) &&
			(layoutRevision.getStatus() == WorkflowConstants.STATUS_DRAFT)) {

			return false;
		}

		importedSiteNavigationMenuItem.setTypeSettings(
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).put(
				"groupId", String.valueOf(layout.getGroupId())
			).put(
				"layoutUuid", layout.getUuid()
			).put(
				"privateLayout", String.valueOf(layout.isPrivateLayout())
			).buildString());

		return true;
	}

	@Override
	public boolean isAvailable(
		SiteNavigationMenuItemTypeContext siteNavigationMenuItemTypeContext) {

		Optional<Group> groupOptional =
			siteNavigationMenuItemTypeContext.getGroupOptional();

		if (!groupOptional.isPresent()) {
			return false;
		}

		Group group = groupOptional.get();

		if (group.isCompany()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isBrowsable(SiteNavigationMenuItem siteNavigationMenuItem) {
		Layout layout = _fetchLayout(siteNavigationMenuItem);

		LayoutType layoutType = layout.getLayoutType();

		return layoutType.isBrowsable();
	}

	@Override
	public boolean isChildSelected(
			boolean selectable, SiteNavigationMenuItem siteNavigationMenuItem,
			Layout curLayout)
		throws PortalException {

		if (!selectable) {
			return false;
		}

		List<Long> parentSiteNavigationMenuItemIds =
			_siteNavigationMenuItemLocalService.
				getParentSiteNavigationMenuItemIds(
					siteNavigationMenuItem.getSiteNavigationMenuId(),
					StringBundler.concat(
						"%layoutUuid=", curLayout.getUuid(),
						StringPool.PERCENT));

		for (Long parentSiteNavigationMenuItemId :
				parentSiteNavigationMenuItemIds) {

			if (_isAncestor(
					siteNavigationMenuItem.getSiteNavigationMenuItemId(),
					parentSiteNavigationMenuItemId)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isItemSelector() {
		return true;
	}

	@Override
	public boolean isMultiSelection() {
		return true;
	}

	@Override
	public boolean isSelected(
			boolean selectable, SiteNavigationMenuItem siteNavigationMenuItem,
			Layout curLayout)
		throws Exception {

		if (!selectable) {
			return false;
		}

		Layout layout = _fetchLayout(siteNavigationMenuItem);

		if (layout.getPlid() == curLayout.getPlid()) {
			return true;
		}

		return false;
	}

	@Override
	public void renderEditPage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR,
			_itemSelector);

		httpServletRequest.setAttribute(
			SiteNavigationMenuItemTypeLayoutWebKeys.USE_CUSTOM_NAME,
			_isUseCustomName(siteNavigationMenuItem));
		httpServletRequest.setAttribute(
			SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM,
			siteNavigationMenuItem);
		httpServletRequest.setAttribute(
			WebKeys.SEL_LAYOUT, _fetchLayout(siteNavigationMenuItem));
		httpServletRequest.setAttribute(
			WebKeys.TITLE,
			getTitle(siteNavigationMenuItem, themeDisplay.getLocale()));

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_layout.jsp");
	}

	private Layout _fetchLayout(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		String layoutUuid = typeSettingsUnicodeProperties.get("layoutUuid");

		boolean privateLayout = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.get("privateLayout"));

		return _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, siteNavigationMenuItem.getGroupId(), privateLayout);
	}

	private Layout _getLayout(
		PortletDataContext portletDataContext,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		String layoutUuid = typeSettingsUnicodeProperties.get("layoutUuid");

		boolean privateLayout = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.get("privateLayout"));

		if (privateLayout != portletDataContext.isPrivateLayout()) {
			ServiceContextThreadLocal.pushServiceContext(new ServiceContext());
		}

		try {
			Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				layoutUuid, siteNavigationMenuItem.getGroupId(), privateLayout);

			if ((layout == null) &&
				ExportImportThreadLocal.isImportInProcess()) {

				layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
					layoutUuid, siteNavigationMenuItem.getGroupId(),
					!privateLayout);
			}

			if (layout == null) {
				Element layoutElement = portletDataContext.getImportDataElement(
					siteNavigationMenuItem);

				String friendlyURL = layoutElement.attributeValue(
					"layout-friendly-url");

				LayoutFriendlyURL layoutFriendlyURL =
					_layoutFriendlyURLLocalService.fetchFirstLayoutFriendlyURL(
						siteNavigationMenuItem.getGroupId(), privateLayout,
						friendlyURL);

				if (layoutFriendlyURL != null) {
					layout = _layoutLocalService.fetchLayout(
						layoutFriendlyURL.getPlid());
				}
			}

			return layout;
		}
		finally {
			if (privateLayout != portletDataContext.isPrivateLayout()) {
				ServiceContextThreadLocal.popServiceContext();
			}
		}
	}

	private boolean _isAncestor(
		long siteNavigationMenuItemId, long parentSiteNavigationMenuItemId) {

		if (parentSiteNavigationMenuItemId == 0) {
			return false;
		}

		if (parentSiteNavigationMenuItemId == siteNavigationMenuItemId) {
			return true;
		}

		SiteNavigationMenuItem parentSiteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.fetchSiteNavigationMenuItem(
				parentSiteNavigationMenuItemId);

		return _isAncestor(
			siteNavigationMenuItemId,
			parentSiteNavigationMenuItem.getParentSiteNavigationMenuItemId());
	}

	private boolean _isUseCustomName(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		return GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.get("useCustomName"),
			GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.get("setCustomName")));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSiteNavigationMenuItemType.class);

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutStaging _layoutStaging;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.layout)",
		unbind = "-"
	)
	private ServletContext _servletContext;

	@Reference
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Reference
	private WebServerServletToken _webServerServletToken;

}