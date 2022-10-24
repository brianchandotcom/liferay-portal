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

package com.liferay.layout.internal.portlet.category;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.portlet.category.PortletCategoryManager;
import com.liferay.layout.util.PortalPreferencesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletItemLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletCategoryComparator;
import com.liferay.portal.kernel.util.comparator.PortletTitleComparator;
import com.liferay.portal.util.PortletCategoryUtil;
import com.liferay.portal.util.WebAppPool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = PortletCategoryManager.class)
public class PortletCategoryManagerImpl implements PortletCategoryManager {

	public JSONArray getPortletsJSONArray(
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws Exception {

		PortletCategory rootPortletCategory = (PortletCategory)WebAppPool.get(
			themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		PortletCategory highlightedPortletCategory =
			rootPortletCategory.getCategory("category.highlighted");

		PortletCategory portletCategory =
			PortletCategoryUtil.getRelevantPortletCategory(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getCompanyId(), themeDisplay.getLayout(),
				rootPortletCategory, themeDisplay.getLayoutTypePortlet());

		Map<String, JSONObject> portletCategoryJSONObjectsMap =
			_getPortletCategoryJSONObjectsMap(
				_getHighlightedPortletIds(
					httpServletRequest, highlightedPortletCategory),
				httpServletRequest, portletCategory, themeDisplay);

		List<String> sortedPortletCategoryKeys = _getSortedPortletCategoryKeys(
			_portletPreferencesFactory.getPortalPreferences(
				httpServletRequest));

		if (sortedPortletCategoryKeys.isEmpty()) {
			return JSONUtil.toJSONArray(
				new ArrayList<>(portletCategoryJSONObjectsMap.values()),
				portletCategoryJSONObject -> portletCategoryJSONObject);
		}

		List<JSONObject> sortedPortletCategoryJSONObjectsList =
			new LinkedList<>();

		for (String portletCategoryKey : sortedPortletCategoryKeys) {
			JSONObject portletCategoryJSONObject =
				portletCategoryJSONObjectsMap.remove(portletCategoryKey);

			if (portletCategoryJSONObject == null) {
				continue;
			}

			sortedPortletCategoryJSONObjectsList.add(portletCategoryJSONObject);
		}

		sortedPortletCategoryJSONObjectsList.addAll(
			0, portletCategoryJSONObjectsMap.values());

		return JSONUtil.toJSONArray(
			sortedPortletCategoryJSONObjectsList,
			portletCategoryJSONObject -> portletCategoryJSONObject);
	}

	@Override
	public void updateSortedPortletCategoryKeys(
		PortalPreferences portalPreferences,
		String[] sortedPortletCategoryKeys) {

		PortalPreferencesUtil.updateSortedPortalPreferencesValues(
			portalPreferences,
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
			"sortedPortletCategoryKeys", sortedPortletCategoryKeys);
	}

	private Set<String> _getHighlightedPortletIds(
		HttpServletRequest httpServletRequest,
		PortletCategory highlightedPortletCategory) {

		Set<String> highlightedPortletIds = new TreeSet<>(
			highlightedPortletCategory.getPortletIds());

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(httpServletRequest);

		highlightedPortletIds.addAll(
			SetUtil.fromArray(
				portalPreferences.getValues(
					ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
					"highlightedPortletIds", new String[0])));

		highlightedPortletIds.removeAll(
			SetUtil.fromArray(
				portalPreferences.getValues(
					ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
					"nonhighlightedPortletIds", new String[0])));

		return highlightedPortletIds;
	}

	private Map<String, JSONObject> _getPortletCategoryJSONObjectsMap(
			Set<String> highlightedPortletIds,
			HttpServletRequest httpServletRequest,
			PortletCategory portletCategory, ThemeDisplay themeDisplay)
		throws Exception {

		Map<String, JSONObject> portletCategoryJSONObjectsMap =
			new LinkedHashMap<>();

		List<PortletCategory> portletCategories = ListUtil.fromCollection(
			portletCategory.getCategories());

		portletCategories = ListUtil.sort(
			portletCategories,
			new PortletCategoryComparator(themeDisplay.getLocale()));

		for (PortletCategory currentPortletCategory : portletCategories) {
			if (currentPortletCategory.isHidden()) {
				continue;
			}

			String portletCategoryKey = StringUtil.replace(
				currentPortletCategory.getPath(), new String[] {"/", "."},
				new String[] {"-", "-"});

			Map<String, JSONObject> childPortletCategoryJSONObjectsMap =
				_getPortletCategoryJSONObjectsMap(
					highlightedPortletIds, httpServletRequest,
					currentPortletCategory, themeDisplay);

			JSONArray childPortletCategoriesJSONArray = JSONUtil.toJSONArray(
				childPortletCategoryJSONObjectsMap.values(),
				portletCategoryJSONObject -> portletCategoryJSONObject);

			JSONArray portletsJSONArray = _getPortletsJSONArray(
				highlightedPortletIds, httpServletRequest,
				currentPortletCategory, themeDisplay);

			if ((childPortletCategoriesJSONArray.length() > 0) ||
				(portletsJSONArray.length() > 0)) {

				portletCategoryJSONObjectsMap.put(
					portletCategoryKey,
					JSONUtil.put(
						"categories", childPortletCategoriesJSONArray
					).put(
						"path", portletCategoryKey
					).put(
						"portlets", portletsJSONArray
					).put(
						"title",
						_getPortletCategoryTitle(
							httpServletRequest, currentPortletCategory,
							themeDisplay)
					));
			}
		}

		return portletCategoryJSONObjectsMap;
	}

	private String _getPortletCategoryTitle(
		HttpServletRequest httpServletRequest, PortletCategory portletCategory,
		ThemeDisplay themeDisplay) {

		for (String portletId :
				PortletCategoryUtil.getFirstChildPortletIds(portletCategory)) {

			Portlet portlet = _portletLocalService.getPortletById(
				themeDisplay.getCompanyId(), portletId);

			if (portlet == null) {
				continue;
			}

			PortletApp portletApp = portlet.getPortletApp();

			if (!portletApp.isWARFile()) {
				continue;
			}

			PortletConfig portletConfig = PortletConfigFactoryUtil.create(
				portlet, httpServletRequest.getServletContext());

			ResourceBundle portletResourceBundle =
				portletConfig.getResourceBundle(themeDisplay.getLocale());

			String title = ResourceBundleUtil.getString(
				portletResourceBundle, portletCategory.getName());

			if (Validator.isNotNull(title)) {
				return title;
			}
		}

		return _language.get(httpServletRequest, portletCategory.getName());
	}

	private JSONArray _getPortletItemsJSONArray(
		Portlet portlet, ThemeDisplay themeDisplay) {

		List<PortletItem> portletItems =
			_portletItemLocalService.getPortletItems(
				themeDisplay.getScopeGroupId(), portlet.getPortletId(),
				PortletPreferences.class.getName());

		if (ListUtil.isEmpty(portletItems)) {
			return _jsonFactory.createJSONArray();
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (PortletItem portletItem : portletItems) {
			jsonArray.put(
				JSONUtil.put(
					"instanceable", portlet.isInstanceable()
				).put(
					"portletId", portlet.getPortletId()
				).put(
					"portletItemId", portletItem.getPortletItemId()
				).put(
					"title", HtmlUtil.escape(portletItem.getName())
				));
		}

		return jsonArray;
	}

	private List<Portlet> _getPortlets(
		Set<String> highlightedPortletIds, PortletCategory portletCategory,
		ThemeDisplay themeDisplay) {

		List<Portlet> portlets = new ArrayList<>();

		Set<String> portletIds = portletCategory.getPortletIds();

		Layout layout = themeDisplay.getLayout();

		if (Objects.equals(portletCategory.getName(), "category.highlighted")) {
			portletIds = highlightedPortletIds;
		}

		for (String portletId : portletIds) {
			Portlet portlet = _portletLocalService.getPortletById(
				themeDisplay.getCompanyId(), portletId);

			if ((portlet == null) ||
				((layout.isTypeAssetDisplay() || layout.isTypeContent()) &&
				 ArrayUtil.contains(
					 _UNSUPPORTED_PORTLETS_NAMES, portlet.getPortletName()))) {

				continue;
			}

			try {
				if (PortletPermissionUtil.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getLayout(), portlet,
						ActionKeys.ADD_TO_PAGE)) {

					portlets.add(portlet);
				}
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to check portlet permissions for " +
						portlet.getPortletId(),
					portalException);
			}
		}

		return portlets;
	}

	private JSONArray _getPortletsJSONArray(
			Set<String> highlightedPortletIds,
			HttpServletRequest httpServletRequest,
			PortletCategory portletCategory, ThemeDisplay themeDisplay)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		HttpSession httpSession = httpServletRequest.getSession();

		ServletContext servletContext = httpSession.getServletContext();

		List<Portlet> portlets = _getPortlets(
			highlightedPortletIds, portletCategory, themeDisplay);

		portlets = ListUtil.sort(
			portlets,
			new PortletTitleComparator(
				servletContext, themeDisplay.getLocale()));

		for (Portlet portlet : portlets) {
			jsonArray.put(
				JSONUtil.put(
					"highlighted",
					highlightedPortletIds.contains(portlet.getPortletId())
				).put(
					"instanceable", portlet.isInstanceable()
				).put(
					"portletId", portlet.getPortletId()
				).put(
					"portletItems",
					_getPortletItemsJSONArray(portlet, themeDisplay)
				).put(
					"title",
					_portal.getPortletTitle(
						portlet, servletContext, themeDisplay.getLocale())
				));
		}

		return jsonArray;
	}

	private List<String> _getSortedPortletCategoryKeys(
		PortalPreferences portalPreferences) {

		return PortalPreferencesUtil.getSortedPortalPreferencesValues(
			portalPreferences,
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
			"sortedPortletCategoryKeys");
	}

	private static final String[] _UNSUPPORTED_PORTLETS_NAMES = {
		PortletKeys.NESTED_PORTLETS
	};

	private static final Log _log = LogFactoryUtil.getLog(
		PortletCategoryManagerImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletItemLocalService _portletItemLocalService;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

}