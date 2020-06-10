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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Eudaldo Alonso
 */
public class AddContentPanelDisplayContext {

	public AddContentPanelDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_assetHelper = (AssetHelper)_httpServletRequest.getAttribute(
			AssetWebKeys.ASSET_HELPER);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getAddContentPanelData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"contents", _getContents()
		).put(
			"widgets", _getWidgets()
		).build();
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
				_log.debug(portalException, portalException);
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

	private List<Map<String, Object>> _getContents() throws Exception {
		List<Map<String, Object>> contents = new ArrayList<>();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setClassNameIds(_getAvailableClassNameIds());
		assetEntryQuery.setEnd(_getDelta());
		assetEntryQuery.setGroupIds(
			new long[] {_themeDisplay.getScopeGroupId()});
		assetEntryQuery.setAllKeywords(new String[] {_getKeywords()});
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
						_themeDisplay.getLayout(),
						portletId, ActionKeys.ADD_TO_PAGE)
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

	private int _getDelta() {
		if (_delta != null) {
			return _delta;
		}

		int deltaDefault = GetterUtil.getInteger(
			SessionClicks.get(
				_httpServletRequest,
				"com.liferay.product.navigation.control.menu.web_" +
					"addPanelNumItems",
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

	private List<HashMap<String, Object>> _getPortlets(
		PortletCategory portletCategory) {

		Set<String> portletIds = portletCategory.getPortletIds();

		Stream<String> stream = portletIds.stream();

		HttpSession session = _httpServletRequest.getSession();

		ServletContext servletContext = session.getServletContext();

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
				"title",
				PortalUtil.getPortletTitle(
					portlet, servletContext, _themeDisplay.getLocale())
			).put(
				"used", _isUsed(portlet, _themeDisplay.getPlid())
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private List<Map<String, Object>> _getWidgetCategories(
		PortletCategory portletCategory) {

		Collection<PortletCategory> portletCategories =
			portletCategory.getCategories();

		Stream<PortletCategory> stream = portletCategories.stream();

		return stream.sorted(
			new PortletCategoryComparator(_themeDisplay.getLocale())
		).filter(
			category -> !category.isHidden()
		).map(
			category -> HashMapBuilder.<String, Object>put(
				"categories", _getWidgetCategories(category)
			).put(
				"path",
				StringUtil.replace(
					category.getPath(), new String[] {"/", "."},
					new String[] {"-", "-"})
			).put(
				"portlets", _getPortlets(category)
			).put(
				"title", _getPortletCategoryTitle(category)
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

	private boolean _isUsed(Portlet portlet, long plid) {
		if (portlet.isInstanceable()) {
			return false;
		}

		long count =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
				portlet.getPortletId());

		if (count > 0) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddContentPanelDisplayContext.class);

	private final AssetHelper _assetHelper;
	private Integer _delta;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final ThemeDisplay _themeDisplay;

}