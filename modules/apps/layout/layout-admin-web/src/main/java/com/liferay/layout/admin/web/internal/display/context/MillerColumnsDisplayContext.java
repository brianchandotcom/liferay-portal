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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.layout.admin.web.internal.servlet.taglib.util.LayoutActionDropdownItemsProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class MillerColumnsDisplayContext {

	public MillerColumnsDisplayContext(
		LayoutActionDropdownItemsProvider layoutActionDropdownItemsProvider,
		LayoutsAdminDisplayContext layoutsAdminDisplayContext,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TranslationInfoItemFieldValuesExporterTracker
			translationInfoItemFieldValuesExporterTracker) {

		_layoutActionDropdownItemsProvider = layoutActionDropdownItemsProvider;
		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;
		_liferayPortletResponse = liferayPortletResponse;
		_translationInfoItemFieldValuesExporterTracker =
			translationInfoItemFieldValuesExporterTracker;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getLayoutChildrenURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/layout_admin/get_layout_children"
		).buildString();
	}

	public JSONArray getLayoutColumnsJSONArray() throws Exception {
		JSONArray layoutColumnsJSONArray = JSONFactoryUtil.createJSONArray();

		if (_layoutsAdminDisplayContext.isPrivateLayoutsEnabled()) {
			layoutColumnsJSONArray = JSONUtil.put(
				_getFirstLayoutColumnJSONArray());

			if (_layoutsAdminDisplayContext.isFirstColumn()) {
				return layoutColumnsJSONArray;
			}
		}

		JSONArray layoutSetBranchesJSONArray = _getLayoutSetBranchesJSONArray();

		if (layoutSetBranchesJSONArray.length() > 0) {
			layoutColumnsJSONArray.put(layoutSetBranchesJSONArray);
		}

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (selLayout == null) {
			layoutColumnsJSONArray.put(
				getLayoutsJSONArray(
					0, _layoutsAdminDisplayContext.isPrivateLayout()));

			return layoutColumnsJSONArray;
		}

		List<Layout> layouts = ListUtil.copy(selLayout.getAncestors());

		Collections.reverse(layouts);

		layouts.add(selLayout);

		for (Layout layout : layouts) {
			layoutColumnsJSONArray.put(
				getLayoutsJSONArray(
					layout.getParentLayoutId(), selLayout.isPrivateLayout()));
		}

		layoutColumnsJSONArray.put(
			getLayoutsJSONArray(
				selLayout.getLayoutId(), selLayout.isPrivateLayout()));

		return layoutColumnsJSONArray;
	}

	public Map<String, Object> getLayoutData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"context",
			Collections.singletonMap(
				"namespace", _liferayPortletResponse.getNamespace())
		).put(
			"props",
			HashMapBuilder.<String, Object>put(
				"breadcrumbEntries", _getBreadcrumbEntriesJSONArray()
			).put(
				"getItemChildrenURL", getLayoutChildrenURL()
			).put(
				"isPrivateLayoutsEnabled",
				() -> {
					Group group = _themeDisplay.getScopeGroup();

					return group.isPrivateLayoutsEnabled();
				}
			).put(
				"languageId", _themeDisplay.getLanguageId()
			).put(
				"layoutColumns", getLayoutColumnsJSONArray()
			).put(
				"moveItemURL",
				_layoutsAdminDisplayContext.getMoveLayoutColumnItemURL()
			).put(
				"searchContainerId", "pages"
			).build()
		).build();
	}

	public JSONArray getLayoutsJSONArray(
			long parentLayoutId, boolean privateLayout)
		throws Exception {

		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutServiceUtil.getLayouts(
			_layoutsAdminDisplayContext.getSelGroupId(), privateLayout,
			parentLayoutId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Layout layout : layouts) {
			if (_layoutsAdminDisplayContext.getActiveLayoutSetBranchId() > 0) {
				LayoutRevision layoutRevision =
					LayoutStagingUtil.getLayoutRevision(layout);

				if ((layoutRevision != null) && layoutRevision.isIncomplete()) {
					continue;
				}
			}

			LayoutTypeController layoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(
					layout.getType());

			JSONObject layoutJSONObject = JSONUtil.put(
				"actions",
				_layoutActionDropdownItemsProvider.getActionDropdownItems(
					layout, false)
			).put(
				"active", _layoutsAdminDisplayContext.isActive(layout.getPlid())
			).put(
				"bulkActions",
				StringUtil.merge(
					_layoutsAdminDisplayContext.getAvailableActions(layout))
			).put(
				"description",
				LanguageUtil.get(
					_httpServletRequest,
					ResourceBundleUtil.getBundle(
						"content.Language", _themeDisplay.getLocale(),
						layoutTypeController.getClass()),
					"layout.types." + layout.getType())
			).put(
				"draggable", true
			);

			int childLayoutsCount = LayoutServiceUtil.getLayoutsCount(
				_layoutsAdminDisplayContext.getSelGroupId(),
				layout.isPrivateLayout(), layout.getLayoutId());

			layoutJSONObject.put(
				"hasChild", childLayoutsCount > 0
			).put(
				"hasScopeGroup", _hasScopeGroup(layout)
			).put(
				"id", layout.getPlid()
			).put(
				"key", String.valueOf(layout.getPlid())
			);

			LayoutType layoutType = layout.getLayoutType();

			layoutJSONObject.put(
				"parentable", layoutType.isParentable()
			).put(
				"quickActions", _getQuickActionsJSONArray(layout)
			).put(
				"selectable", true
			).put(
				"states", _getLayoutStatesJSONArray(layout)
			).put(
				"title", layout.getName(_themeDisplay.getLocale())
			).put(
				"url",
				PortletURLBuilder.create(
					_layoutsAdminDisplayContext.getPortletURL()
				).setParameter(
					"layoutSetBranchId",
					_layoutsAdminDisplayContext.getActiveLayoutSetBranchId()
				).setParameter(
					"privateLayout", layout.isPrivateLayout()
				).setParameter(
					"selPlid", layout.getPlid()
				).buildString()
			);

			if (_layoutsAdminDisplayContext.isShowViewLayoutAction(layout)) {
				layoutJSONObject.put(
					"viewUrl",
					_layoutsAdminDisplayContext.getViewLayoutURL(layout));
			}

			layoutsJSONArray.put(layoutJSONObject);
		}

		return layoutsJSONArray;
	}

	private JSONObject _getAddChildPageActionJSONObject(
		Layout layout, String actionType) {

		return JSONUtil.put(
			actionType, true
		).put(
			"icon", "plus"
		).put(
			"id", "add"
		).put(
			"label", LanguageUtil.get(_httpServletRequest, "add-page")
		).put(
			"url",
			_layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(
				_layoutsAdminDisplayContext.
					getFirstLayoutPageTemplateCollectionId(),
				layout.getPlid(), layout.isPrivateLayout())
		);
	}

	private JSONObject _getAddLayoutCollectionActionJSONObject(
		long plid, boolean privateLayout) {

		return JSONUtil.put(
			"id", "addCollectionPage"
		).put(
			"label",
			LanguageUtil.get(_httpServletRequest, "add-collection-page")
		).put(
			"layoutAction", true
		).put(
			"url",
			_layoutsAdminDisplayContext.getSelectLayoutCollectionURL(
				plid, null, privateLayout)
		);
	}

	private JSONObject _getAddRootLayoutActionJSONObject(
			boolean privatePages, String actionType)
		throws Exception {

		return JSONUtil.put(
			actionType, true
		).put(
			"icon", "plus"
		).put(
			"id", "add"
		).put(
			"label", LanguageUtil.get(_httpServletRequest, "add-page")
		).put(
			"url",
			_layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(
				privatePages)
		);
	}

	private JSONArray _getBreadcrumbEntriesJSONArray() throws Exception {
		JSONArray breadcrumbEntriesJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (BreadcrumbEntry breadcrumbEntry :
				_layoutsAdminDisplayContext.getPortletBreadcrumbEntries()) {

			breadcrumbEntriesJSONArray.put(
				JSONUtil.put(
					"title", breadcrumbEntry.getTitle()
				).put(
					"url", breadcrumbEntry.getURL()
				));
		}

		return breadcrumbEntriesJSONArray;
	}

	private JSONArray _getFirstLayoutColumnJSONArray() throws Exception {
		JSONArray firstColumnJSONArray = JSONFactoryUtil.createJSONArray();

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (LayoutLocalServiceUtil.hasLayouts(
				_layoutsAdminDisplayContext.getSelGroup(), false) &&
			_layoutsAdminDisplayContext.isShowPublicLayouts()) {

			boolean active = !_layoutsAdminDisplayContext.isPrivateLayout();

			if (selLayout != null) {
				active = selLayout.isPublicLayout();
			}

			if (_layoutsAdminDisplayContext.isFirstColumn()) {
				active = false;
			}

			firstColumnJSONArray.put(
				_getFirstLayoutColumnJSONObject(false, active));
		}

		if (LayoutLocalServiceUtil.hasLayouts(
				_layoutsAdminDisplayContext.getSelGroup(), true)) {

			boolean active = _layoutsAdminDisplayContext.isPrivateLayout();

			if (selLayout != null) {
				active = selLayout.isPrivateLayout();
			}

			if (_layoutsAdminDisplayContext.isFirstColumn()) {
				active = false;
			}

			firstColumnJSONArray.put(
				_getFirstLayoutColumnJSONObject(true, active));
		}

		return firstColumnJSONArray;
	}

	private JSONObject _getFirstLayoutColumnJSONObject(
			boolean privatePages, boolean active)
		throws Exception {

		String key = "public-pages";

		if (privatePages) {
			key = "private-pages";
		}

		return JSONUtil.put(
			"active", active
		).put(
			"hasChild", true
		).put(
			"hasScopeGroup", true
		).put(
			"id", LayoutConstants.DEFAULT_PLID
		).put(
			"key", key
		).put(
			"quickActions",
			_getFirstLayoutColumnQuickActionsJSONArray(privatePages)
		).put(
			"title", _layoutsAdminDisplayContext.getTitle(privatePages)
		).put(
			"url",
			PortletURLBuilder.create(
				_layoutsAdminDisplayContext.getPortletURL()
			).setParameter(
				"privateLayout", privatePages
			).setParameter(
				"selPlid", LayoutConstants.DEFAULT_PLID
			).buildString()
		);
	}

	private JSONArray _getFirstLayoutColumnQuickActionsJSONArray(
			boolean privatePages)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (_layoutsAdminDisplayContext.isShowAddRootLayoutButton()) {
			jsonArray.put(
				_getAddRootLayoutActionJSONObject(privatePages, "layoutAction")
			).put(
				_getAddLayoutCollectionActionJSONObject(
					LayoutConstants.DEFAULT_PLID, privatePages)
			);
		}

		if (_layoutsAdminDisplayContext.isShowFirstColumnConfigureAction()) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "cog"
				).put(
					"id", "configure"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "configure")
				).put(
					"quickAction", true
				).put(
					"url",
					_layoutsAdminDisplayContext.
						getFirstColumnConfigureLayoutURL(privatePages)
				));
		}

		return jsonArray;
	}

	private JSONArray _getLayoutSetBranchesJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<LayoutSetBranch> layoutSetBranches =
			LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(
				_themeDisplay.getScopeGroupId(),
				_layoutsAdminDisplayContext.isPrivateLayout());

		for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
			jsonArray.put(
				JSONUtil.put(
					"active",
					layoutSetBranch.getLayoutSetBranchId() ==
						_layoutsAdminDisplayContext.getActiveLayoutSetBranchId()
				).put(
					"hasChild", true
				).put(
					"hasScopeGroup", true
				).put(
					"id", LayoutConstants.DEFAULT_PLID
				).put(
					"key",
					String.valueOf(layoutSetBranch.getLayoutSetBranchId())
				).put(
					"plid", LayoutConstants.DEFAULT_PLID
				).put(
					"title",
					LanguageUtil.get(
						_httpServletRequest, layoutSetBranch.getName())
				).put(
					"url",
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/layout_admin/select_layout_set_branch"
					).setRedirect(
						PortletURLBuilder.create(
							_layoutsAdminDisplayContext.getPortletURL()
						).setParameter(
							"layoutSetBranchId",
							layoutSetBranch.getLayoutSetBranchId()
						).setParameter(
							"privateLayout", layoutSetBranch.isPrivateLayout()
						).buildString()
					).setParameter(
						"groupId", layoutSetBranch.getGroupId()
					).setParameter(
						"layoutSetBranchId",
						layoutSetBranch.getLayoutSetBranchId()
					).setParameter(
						"privateLayout", layoutSetBranch.isPrivateLayout()
					).buildString()
				));
		}

		return jsonArray;
	}

	private JSONArray _getLayoutStatesJSONArray(Layout layout) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Layout draftLayout = layout.fetchDraftLayout();

		if (layout.isTypeContent()) {
			boolean published = GetterUtil.getBoolean(
				draftLayout.getTypeSettingsProperty("published"));

			if ((draftLayout.getStatus() == WorkflowConstants.STATUS_DRAFT) ||
				!published) {

				jsonArray.put(
					JSONUtil.put(
						"id", "draft"
					).put(
						"label", LanguageUtil.get(_httpServletRequest, "draft")
					));
			}
		}
		else {
			if (draftLayout != null) {
				jsonArray.put(
					JSONUtil.put(
						"id", "conversionPreview"
					).put(
						"label",
						LanguageUtil.get(
							_httpServletRequest, "conversion-draft")
					));
			}
		}

		if (layout.isDenied() || layout.isPending()) {
			jsonArray.put(
				JSONUtil.put(
					"id", "pending"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "pending")
				));
		}

		return jsonArray;
	}

	private JSONArray _getQuickActionsJSONArray(Layout layout)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (_layoutsAdminDisplayContext.isShowAddChildPageAction(layout)) {
			jsonArray.put(
				_getAddChildPageActionJSONObject(layout, "layoutAction")
			).put(
				_getAddLayoutCollectionActionJSONObject(
					layout.getPlid(), layout.isPrivateLayout())
			);
		}

		return jsonArray;
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

	private final HttpServletRequest _httpServletRequest;
	private final LayoutActionDropdownItemsProvider
		_layoutActionDropdownItemsProvider;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;
	private final TranslationInfoItemFieldValuesExporterTracker
		_translationInfoItemFieldValuesExporterTracker;

}