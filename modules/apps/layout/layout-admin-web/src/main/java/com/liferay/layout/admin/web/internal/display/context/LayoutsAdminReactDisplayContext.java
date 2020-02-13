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
import com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.staging.StagingGroupHelper;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

/**
 * @author Carlos Lancha
 */
public class LayoutsAdminReactDisplayContext
	extends LayoutsAdminDisplayContext {

	public LayoutsAdminReactDisplayContext(
		LayoutConverterConfiguration layoutConverterConfiguration,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		StagingGroupHelper stagingGroupHelper) {

		super(
			layoutConverterConfiguration, liferayPortletRequest,
			liferayPortletResponse, stagingGroupHelper);
	}

	@Override
	public JSONArray getLayoutColumnsJSONArray() throws Exception {
		JSONArray layoutColumnsJSONArray = JSONUtil.put(
			_getFirstLayoutColumnJSONArray());

		if (isFirstColumn()) {
			return layoutColumnsJSONArray;
		}

		JSONArray layoutSetBranchesJSONArray = getLayoutSetBranchesJSONArray();

		if (layoutSetBranchesJSONArray.length() > 0) {
			layoutColumnsJSONArray.put(layoutSetBranchesJSONArray);
		}

		Layout selLayout = getSelLayout();

		if (selLayout == null) {
			layoutColumnsJSONArray.put(
				getLayoutsJSONArray(0, isPrivateLayout()));

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

	@Override
	public JSONArray getLayoutsJSONArray(
			long parentLayoutId, boolean privateLayout)
		throws Exception {

		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			getSelGroupId(), privateLayout, parentLayoutId, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (Layout layout : layouts) {
			if (getActiveLayoutSetBranchId() > 0) {
				LayoutRevision layoutRevision =
					LayoutStagingUtil.getLayoutRevision(layout);

				if ((layoutRevision != null) && layoutRevision.isIncomplete()) {
					continue;
				}
			}

			JSONObject layoutJSONObject = JSONUtil.put(
				"actions", _getLayoutActionsJSONArray(layout)
			).put(
				"active", isActive(layout.getPlid())
			).put(
				"bulkActions", StringUtil.merge(getAvailableActions(layout))
			);

			LayoutTypeController layoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(
					layout.getType());

			ResourceBundle layoutTypeResourceBundle =
				ResourceBundleUtil.getBundle(
					"content.Language", themeDisplay.getLocale(),
					layoutTypeController.getClass());

			layoutJSONObject.put(
				"description",
				LanguageUtil.get(
					httpServletRequest, layoutTypeResourceBundle,
					"layout.types." + layout.getType())
			).put(
				"draggable", true
			);

			int childLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
				getSelGroup(), layout.isPrivateLayout(), layout.getLayoutId());

			layoutJSONObject.put(
				"hasChild", childLayoutsCount > 0
			).put(
				"id", layout.getPlid()
			);

			LayoutType layoutType = layout.getLayoutType();

			layoutJSONObject.put(
				"parentable", layoutType.isParentable()
			).put(
				"selectable", true
			).put(
				"states", _getLayoutStatesJSONArray(layout)
			).put(
				"title", layout.getName(themeDisplay.getLocale())
			);

			PortletURL portletURL = getPortletURL();

			portletURL.setParameter(
				"selPlid", String.valueOf(layout.getPlid()));
			portletURL.setParameter(
				"layoutSetBranchId",
				String.valueOf(getActiveLayoutSetBranchId()));
			portletURL.setParameter(
				"privateLayout", String.valueOf(layout.isPrivateLayout()));

			layoutJSONObject.put("url", portletURL.toString());

			layoutsJSONArray.put(layoutJSONObject);
		}

		return layoutsJSONArray;
	}

	private JSONObject _getFirstLayoutColumn(
			boolean privatePages, boolean active)
		throws PortalException {

		JSONObject pagesJSONObject = JSONUtil.put(
			"actions", _getFirstLayoutColumnActionsJSONArray(privatePages)
		).put(
			"active", active
		).put(
			"hasChild", true
		).put(
			"id", LayoutConstants.DEFAULT_PLID
		).put(
			"title", getTitle(privatePages)
		);

		PortletURL pagesURL = getPortletURL();

		pagesURL.setParameter(
			"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));
		pagesURL.setParameter("privateLayout", String.valueOf(privatePages));

		pagesJSONObject.put("url", pagesURL.toString());

		return pagesJSONObject;
	}

	private JSONArray _getFirstLayoutColumnActionsJSONArray(
			boolean privatePages)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (isShowAddRootLayoutButton()) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "plus"
				).put(
					"id", "add"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "add")
				).put(
					"quickAction", true
				).put(
					"url", getSelectLayoutPageTemplateEntryURL(privatePages)
				));
		}

		if (isShowFirstColumnConfigureAction()) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "cog"
				).put(
					"id", "configure"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "configure")
				).put(
					"quickAction", true
				).put(
					"url", getFirstColumnConfigureLayoutURL(privatePages)
				));
		}

		return jsonArray;
	}

	private JSONArray _getFirstLayoutColumnJSONArray() throws Exception {
		JSONArray firstColumnJSONArray = JSONFactoryUtil.createJSONArray();

		Layout selLayout = getSelLayout();

		if (LayoutLocalServiceUtil.hasLayouts(getSelGroup(), false) &&
			isShowPublicPages()) {

			boolean active = !isPrivateLayout();

			if (selLayout != null) {
				active = selLayout.isPublicLayout();
			}

			if (isFirstColumn()) {
				active = false;
			}

			firstColumnJSONArray.put(_getFirstLayoutColumn(false, active));
		}

		if (LayoutLocalServiceUtil.hasLayouts(getSelGroup(), true)) {
			boolean active = isPrivateLayout();

			if (selLayout != null) {
				active = selLayout.isPrivateLayout();
			}

			if (isFirstColumn()) {
				active = false;
			}

			firstColumnJSONArray.put(_getFirstLayoutColumn(true, active));
		}

		return firstColumnJSONArray;
	}

	private JSONArray _getLayoutActionsJSONArray(Layout layout)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (isShowAddChildPageAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "plus"
				).put(
					"id", "add"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "add")
				).put(
					"quickAction", true
				).put(
					"url",
					getSelectLayoutPageTemplateEntryURL(
						getFirstLayoutPageTemplateCollectionId(),
						layout.getPlid(), layout.isPrivateLayout())
				));
		}

		if (isShowConfigureAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "cog"
				).put(
					"id", "configure"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "configure")
				).put(
					"url", getConfigureLayoutURL(layout)
				));
		}

		Layout draftLayout = LayoutLocalServiceUtil.fetchLayout(
			PortalUtil.getClassNameId(Layout.class), layout.getPlid());

		if (isShowConvertLayoutAction(layout)) {
			if (draftLayout == null) {
				jsonArray.put(
					JSONUtil.put(
						"id", "layoutConversionPreview"
					).put(
						"label",
						LanguageUtil.get(
							httpServletRequest,
							"convert-to-content-page-and-preview")
					).put(
						"url", getLayoutConversionPreviewURL(layout)
					));
			}
			else {
				jsonArray.put(
					JSONUtil.put(
						"id", "deleteLayoutConversionPreview"
					).put(
						"label",
						LanguageUtil.get(
							httpServletRequest, "discard-conversion-draft")
					).put(
						"url", getDeleteLayoutURL(layout)
					));
			}
		}

		if (isShowCopyLayoutAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "copyLayout"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "copy-page")
				).put(
					"url", getCopyLayoutRenderURL(layout)
				));
		}

		if (isShowDeleteAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "delete"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "delete")
				).put(
					"url", getDeleteLayoutURL(layout)
				));
		}

		if (isConversionDraft(layout) && isShowConfigureAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "editConversionLayout"
				).put(
					"label",
					LanguageUtil.get(
						httpServletRequest, "edit-conversion-draft")
				).put(
					"url", getEditLayoutURL(layout)
				));
		}
		else if (isShowConfigureAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "editLayout"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "edit")
				).put(
					"url", getEditLayoutURL(layout)
				));
		}

		if (isShowOrphanPortletsAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "orphanPortlets"
				).put(
					"label",
					LanguageUtil.get(httpServletRequest, "orphan-widgets")
				).put(
					"url", getOrphanPortletsURL(layout)
				));
		}

		if (isShowPermissionsAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "permissions"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "permissions")
				).put(
					"url", getPermissionsURL(layout)
				));
		}

		if (layout.isPending()) {
			jsonArray.put(
				JSONUtil.put(
					"id", "previewLayout"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "preview")
				).put(
					"url", getViewLayoutURL(layout)
				));
		}
		else {
			jsonArray.put(
				JSONUtil.put(
					"id", "viewLayout"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "view")
				).put(
					"url", getViewLayoutURL(layout)
				));
		}

		return jsonArray;
	}

	private JSONArray _getLayoutStatesJSONArray(Layout layout)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Layout draftLayout = LayoutLocalServiceUtil.fetchLayout(
			PortalUtil.getClassNameId(Layout.class), layout.getPlid());

		if (layout.isTypeContent()) {
			boolean published = GetterUtil.getBoolean(
				draftLayout.getTypeSettingsProperty("published"));

			if ((draftLayout.getStatus() == WorkflowConstants.STATUS_DRAFT) ||
				!published) {

				jsonArray.put(
					JSONUtil.put(
						"id", "draft"
					).put(
						"label", LanguageUtil.get(httpServletRequest, "draft")
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
							httpServletRequest, "conversionPreview")
					));
			}
		}

		if (layout.getStatus() == WorkflowConstants.STATUS_PENDING) {
			jsonArray.put(
				JSONUtil.put(
					"id", "pending"
				).put(
					"label", LanguageUtil.get(httpServletRequest, "pending")
				));
		}

		return jsonArray;
	}

}