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

package com.liferay.product.navigation.control.menu.web.internal.display.context;

import com.liferay.asset.constants.AssetWebKeys;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetPublisherAddItemHolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.PortletItemLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletCategoryComparator;
import com.liferay.portal.kernel.util.comparator.PortletTitleComparator;
import com.liferay.portal.util.PortletCategoryUtil;
import com.liferay.portal.util.WebAppPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Eudaldo Alonso
 */
public class AddContentPanelDisplayContext {

	public AddContentPanelDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_assetHelper = (AssetHelper)_httpServletRequest.getAttribute(
			AssetWebKeys.ASSET_HELPER);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getAddContentPanelData() {
		return HashMapBuilder.<String, Object>put(
			"addContentsURLs",
			() -> {
				if (hasAddContentPermission()) {
					return _getAddContentsURLs();
				}

				return Collections.emptyList();
			}
		).put(
			"contents",
			() -> {
				if (hasAddContentPermission()) {
					return getContents();
				}

				return Collections.emptyList();
			}
		).put(
			"getContentsURL",
			() -> {
				ResourceURL resourceURL =
					_liferayPortletResponse.createResourceURL();

				resourceURL.setResourceID(
					"/product_navigation_control_menu/get_contents");

				return resourceURL.toString();
			}
		).put(
			"languageDirection", _getLanguageDirection()
		).put(
			"languageId", _themeDisplay.getLanguageId()
		).put(
			"namespace", _liferayPortletResponse.getNamespace()
		).put(
			"plid", _themeDisplay.getPlid()
		).put(
			"widgets",
			() -> {
				if (hasAddApplicationsPermission()) {
					return _getWidgets();
				}

				return Collections.emptyList();
			}
		).build();
	}

	public List<Map<String, Object>> getContents() throws Exception {
		List<Map<String, Object>> contents = new ArrayList<>();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setAttribute("showNonindexable", Boolean.TRUE);
		assetEntryQuery.setClassNameIds(_getAvailableClassNameIds());
		assetEntryQuery.setEnd(_getDelta());
		assetEntryQuery.setGroupIds(
			new long[] {_themeDisplay.getScopeGroupId()});
		assetEntryQuery.setKeywords(_getKeywords());
		assetEntryQuery.setOrderByCol1("modifiedDate");
		assetEntryQuery.setOrderByCol2("title");
		assetEntryQuery.setOrderByType1("DESC");
		assetEntryQuery.setOrderByType2("ASC");
		assetEntryQuery.setStart(0);

		BaseModelSearchResult<AssetEntry> baseModelSearchResult =
			_assetHelper.searchAssetEntries(
				_httpServletRequest, assetEntryQuery, 0, _getDelta());

		for (AssetEntry assetEntry : baseModelSearchResult.getBaseModels()) {
			AssetRendererFactory<?> assetRendererFactory =
				assetEntry.getAssetRendererFactory();

			if (assetRendererFactory == null) {
				continue;
			}

			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

			if ((assetRenderer == null) || !assetRenderer.isDisplayable()) {
				continue;
			}

			String portletId = PortletProviderUtil.getPortletId(
				assetEntry.getClassName(), PortletProvider.Action.ADD);

			contents.add(
				HashMapBuilder.<String, Object>put(
					"className", assetEntry.getClassName()
				).put(
					"classPK", assetEntry.getClassPK()
				).put(
					"draggable",
					PortletPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getLayout(), portletId,
						ActionKeys.ADD_TO_PAGE)
				).put(
					"icon", assetRenderer.getIconCssClass()
				).put(
					"portletId", portletId
				).put(
					"title",
					HtmlUtil.escape(
						StringUtil.shorten(
							assetRenderer.getTitle(_themeDisplay.getLocale()),
							60))
				).put(
					"type",
					_getAssetEntryTypeLabel(
						assetEntry.getClassName(), assetEntry.getClassTypeId())
				).build());
		}

		return contents;
	}

	public boolean hasAddApplicationsPermission() throws Exception {
		if (_hasAddApplicationsPermission != null) {
			return _hasAddApplicationsPermission;
		}

		_hasAddApplicationsPermission = false;

		boolean stateMaximized = ParamUtil.getBoolean(
			_httpServletRequest, "stateMaximized");

		LayoutTypePortlet layoutTypePortlet =
			_themeDisplay.getLayoutTypePortlet();

		LayoutTypeController layoutTypeController =
			layoutTypePortlet.getLayoutTypeController();

		Layout layout = _themeDisplay.getLayout();

		if (!stateMaximized && layout.isTypePortlet() &&
			!layout.isLayoutPrototypeLinkActive() &&
			!layoutTypeController.isFullPageDisplayable() &&
			(hasLayoutUpdatePermission() ||
			 (layoutTypePortlet.isCustomizable() &&
			  layoutTypePortlet.isCustomizedView() &&
			  hasLayoutCustomizePermission()))) {

			_hasAddApplicationsPermission = true;
		}

		return _hasAddApplicationsPermission;
	}

	public boolean hasAddContentPermission() throws Exception {
		if (_hasAddContentPermission != null) {
			return _hasAddContentPermission;
		}

		_hasAddContentPermission = false;

		Group group = _themeDisplay.getScopeGroup();

		if (hasAddApplicationsPermission() && !group.isLayoutPrototype()) {
			_hasAddContentPermission = true;
		}

		return _hasAddContentPermission;
	}

	public boolean hasLayoutCustomizePermission() throws Exception {
		if (_hasLayoutCustomizePermission != null) {
			return _hasLayoutCustomizePermission;
		}

		_hasLayoutCustomizePermission = false;

		if (LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _themeDisplay.getLayout(),
				ActionKeys.CUSTOMIZE)) {

			_hasLayoutCustomizePermission = true;
		}

		return _hasLayoutCustomizePermission;
	}

	public boolean hasLayoutUpdatePermission() throws Exception {
		if (_hasLayoutUpdatePermission != null) {
			return _hasLayoutUpdatePermission;
		}

		_hasLayoutUpdatePermission = false;

		if (LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _themeDisplay.getLayout(),
				ActionKeys.UPDATE)) {

			_hasLayoutUpdatePermission = true;
		}

		return _hasLayoutUpdatePermission;
	}

	public boolean showAddPanel() throws Exception {
		Group group = _themeDisplay.getScopeGroup();

		LayoutTypePortlet layoutTypePortlet =
			_themeDisplay.getLayoutTypePortlet();

		if (!group.isControlPanel() &&
			(hasLayoutUpdatePermission() ||
			 (layoutTypePortlet.isCustomizable() &&
			  layoutTypePortlet.isCustomizedView() &&
			  hasLayoutCustomizePermission()))) {

			return true;
		}

		return false;
	}

	private List<Map<String, Object>> _getAddContentsURLs() throws Exception {
		List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders =
			_assetHelper.getAssetPublisherAddItemHolders(
				_liferayPortletRequest, _liferayPortletResponse,
				_themeDisplay.getScopeGroupId(), _getClassNameIds(),
				new long[0], null, null, _getRedirectURL());

		Stream<AssetPublisherAddItemHolder> stream =
			assetPublisherAddItemHolders.stream();

		return stream.map(
			assetPublisherAddItemHolder -> HashMapBuilder.<String, Object>put(
				"label", assetPublisherAddItemHolder.getModelResource()
			).put(
				"url",
				() -> {
					long curGroupId = _themeDisplay.getScopeGroupId();

					Group group = _themeDisplay.getScopeGroup();

					if (!group.isStagedPortlet(
							assetPublisherAddItemHolder.getPortletId()) &&
						!group.isStagedRemotely()) {

						curGroupId = group.getLiveGroupId();
					}

					return _assetHelper.getAddURLPopUp(
						curGroupId, _themeDisplay.getPlid(),
						assetPublisherAddItemHolder.getPortletURL(), false,
						_themeDisplay.getLayout());
				}
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private String _getAssetEntryTypeLabel(String className, long classTypeId) {
		if (classTypeId <= 0) {
			return ResourceActionsUtil.getModelResource(
				_themeDisplay.getLocale(), className);
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return ResourceActionsUtil.getModelResource(
				_themeDisplay.getLocale(), className);
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			ClassType classType = classTypeReader.getClassType(
				classTypeId, themeDisplay.getLocale());

			return classType.getName();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return ResourceActionsUtil.getModelResource(
			_themeDisplay.getLocale(), className);
	}

	private long[] _getAvailableClassNameIds() {
		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				_themeDisplay.getCompanyId());

		for (long classNameId : availableClassNameIds) {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameId);

			if (!assetRendererFactory.isSelectable()) {
				availableClassNameIds = ArrayUtil.remove(
					availableClassNameIds, classNameId);
			}
		}

		return availableClassNameIds;
	}

	private long[] _getClassNameIds() {
		return AssetRendererFactoryRegistryUtil.getClassNameIds(
			_themeDisplay.getCompanyId());
	}

	private int _getDelta() {
		if (_delta != null) {
			return _delta;
		}

		int deltaDefault = GetterUtil.getInteger(
			SessionClicks.get(
				_httpServletRequest,
				"com.liferay.product.navigation.control.menu." +
					"web_addPanelNumItems",
				"10"));

		_delta = ParamUtil.getInteger(
			_httpServletRequest, "delta", deltaDefault);

		return _delta;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private Map<String, String> _getLanguageDirection() {
		Set<Locale> locales = LanguageUtil.getAvailableLocales(
			_themeDisplay.getScopeGroupId());

		Stream<Locale> stream = locales.stream();

		return stream.collect(
			Collectors.toMap(
				LocaleUtil::toLanguageId,
				locale -> LanguageUtil.get(locale, "lang.dir")));
	}

	private Set<String> _getLayoutDecodedPortletNames() {
		if (_layoutDecodedPortletNames != null) {
			return _layoutDecodedPortletNames;
		}

		Set<String> layoutDecodedPortletNames = new HashSet<>();

		LayoutTypePortlet layoutTypePortlet =
			_themeDisplay.getLayoutTypePortlet();

		for (Portlet layoutPortlet : layoutTypePortlet.getPortlets()) {
			String decodedPortletName = PortletIdCodec.decodePortletName(
				layoutPortlet.getPortletId());

			layoutDecodedPortletNames.add(decodedPortletName);
		}

		_layoutDecodedPortletNames = layoutDecodedPortletNames;

		return _layoutDecodedPortletNames;
	}

	private String _getPortletCategoryTitle(PortletCategory portletCategory) {
		for (String portletId :
				PortletCategoryUtil.getFirstChildPortletIds(portletCategory)) {

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(), portletId);

			if (portlet == null) {
				continue;
			}

			PortletApp portletApp = portlet.getPortletApp();

			if (!portletApp.isWARFile()) {
				continue;
			}

			PortletConfig portletConfig = PortletConfigFactoryUtil.create(
				portlet, _httpServletRequest.getServletContext());

			ResourceBundle portletResourceBundle =
				portletConfig.getResourceBundle(_themeDisplay.getLocale());

			String title = ResourceBundleUtil.getString(
				portletResourceBundle, portletCategory.getName());

			if (Validator.isNotNull(title)) {
				return title;
			}
		}

		return LanguageUtil.get(_httpServletRequest, portletCategory.getName());
	}

	private List<Map<String, Object>> _getPortletItems(Portlet portlet) {
		List<PortletItem> portletItems =
			PortletItemLocalServiceUtil.getPortletItems(
				_themeDisplay.getScopeGroupId(), portlet.getPortletId(),
				PortletPreferences.class.getName());

		if (ListUtil.isEmpty(portletItems)) {
			return Collections.emptyList();
		}

		Stream<PortletItem> stream = portletItems.stream();

		return stream.map(
			portletItem -> HashMapBuilder.<String, Object>put(
				"instanceable", portlet.isInstanceable()
			).put(
				"portletId", portlet.getPortletId()
			).put(
				"portletItemId", portletItem.getPortletItemId()
			).put(
				"title", HtmlUtil.escape(portletItem.getName())
			).put(
				"used", _isUsed(portlet)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private List<Map<String, Object>> _getPortlets(
		PortletCategory portletCategory) {

		HttpSession httpSession = _httpServletRequest.getSession();

		ServletContext servletContext = httpSession.getServletContext();

		Set<String> portletIds = portletCategory.getPortletIds();

		Stream<String> stream = portletIds.stream();

		return stream.map(
			portletId -> PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(), portletId)
		).filter(
			portlet -> {
				if (portlet == null) {
					return false;
				}

				try {
					return PortletPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getLayout(), portlet,
						ActionKeys.ADD_TO_PAGE);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to check portlet permissions for " +
							portlet.getPortletId(),
						portalException);

					return false;
				}
			}
		).sorted(
			new PortletTitleComparator(
				servletContext, _themeDisplay.getLocale())
		).map(
			portlet -> HashMapBuilder.<String, Object>put(
				"instanceable", portlet.isInstanceable()
			).put(
				"portletId", portlet.getPortletId()
			).put(
				"portletItems", _getPortletItems(portlet)
			).put(
				"title",
				PortalUtil.getPortletTitle(
					portlet, servletContext, _themeDisplay.getLocale())
			).put(
				"used", _isUsed(portlet)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private String _getRedirectURL() throws Exception {
		return PortalUtil.getLayoutFullURL(
			_themeDisplay.getLayout(), _themeDisplay);
	}

	private List<Map<String, Object>> _getWidgetCategories(
		PortletCategory portletCategory) {

		Collection<PortletCategory> portletCategories =
			portletCategory.getCategories();

		Stream<PortletCategory> stream = portletCategories.stream();

		return stream.sorted(
			new PortletCategoryComparator(_themeDisplay.getLocale())
		).filter(
			currentPortletCategory -> !currentPortletCategory.isHidden()
		).map(
			currentPortletCategory -> HashMapBuilder.<String, Object>put(
				"categories", _getWidgetCategories(currentPortletCategory)
			).put(
				"path",
				StringUtil.replace(
					currentPortletCategory.getPath(), new String[] {"/", "."},
					new String[] {"-", "-"})
			).put(
				"portlets", _getPortlets(currentPortletCategory)
			).put(
				"title", _getPortletCategoryTitle(currentPortletCategory)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private List<Map<String, Object>> _getWidgets() throws Exception {
		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			_themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		portletCategory = PortletCategoryUtil.getRelevantPortletCategory(
			_themeDisplay.getPermissionChecker(), _themeDisplay.getCompanyId(),
			_themeDisplay.getLayout(), portletCategory,
			_themeDisplay.getLayoutTypePortlet());

		return _getWidgetCategories(portletCategory);
	}

	private boolean _isUsed(Portlet portlet) {
		if (portlet.isInstanceable()) {
			return false;
		}

		Set<String> layoutDecodedPortletNames = _getLayoutDecodedPortletNames();
		LayoutTypePortlet layoutTypePortlet =
			_themeDisplay.getLayoutTypePortlet();

		if (layoutDecodedPortletNames.contains(portlet.getPortletId()) ||
			layoutTypePortlet.hasPortletId(portlet.getPortletId())) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddContentPanelDisplayContext.class);

	private final AssetHelper _assetHelper;
	private Integer _delta;
	private Boolean _hasAddApplicationsPermission;
	private Boolean _hasAddContentPermission;
	private Boolean _hasLayoutCustomizePermission;
	private Boolean _hasLayoutUpdatePermission;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private Set<String> _layoutDecodedPortletNames;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}