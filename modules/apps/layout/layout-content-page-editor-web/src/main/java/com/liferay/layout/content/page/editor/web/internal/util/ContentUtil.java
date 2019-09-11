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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalServiceUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class ContentUtil {

	public static Set<AssetEntry> getFragmentEntryLinkMappedAssetEntries(
		FragmentEntryLink fragmentEntryLink) {

		return _getFragmentEntryLinkMappedAssetEntries(
			fragmentEntryLink, new HashSet<>());
	}

	public static Set<AssetEntry> getLayoutMappedAssetEntries(String layoutData)
		throws PortalException {

		return _getLayoutMappedAssetEntries(layoutData, new HashSet<>());
	}

	public static Set<AssetEntry> getMappedAssetEntries(long groupId, long plid)
		throws PortalException {

		Set<Long> mappedClassPKs = new HashSet<>();

		Set<AssetEntry> assetEntries = _getFragmentEntryLinksMappedAssetEntries(
			groupId, plid, mappedClassPKs);

		assetEntries.addAll(
			_getLayoutMappedAssetEntries(groupId, plid, mappedClassPKs));

		return assetEntries;
	}

	public static JSONArray getPageContentsJSONArray(
		long plid, String backURL, HttpServletRequest httpServletRequest) {

		JSONArray mappedContentsJSONArray = JSONFactoryUtil.createJSONArray();

		List<AssetEntryUsage> assetEntryUsages =
			AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesByPlid(plid);

		try {
			Set<Long> assetEntryIds = new HashSet<>();

			for (AssetEntryUsage assetEntryUsage : assetEntryUsages) {
				if (assetEntryIds.contains(assetEntryUsage.getAssetEntryId())) {
					continue;
				}

				AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
					assetEntryUsage.getAssetEntryId());

				mappedContentsJSONArray.put(
					_getPageContentJSONObject(
						assetEntry, backURL, httpServletRequest));

				assetEntryIds.add(assetEntryUsage.getAssetEntryId());
			}
		}
		catch (Exception e) {
			_log.error("An error ocurred while getting mapped contents", e);
		}

		return mappedContentsJSONArray;
	}

	private static JSONObject _getActionsJSONObject(
			AssetEntry assetEntry, ThemeDisplay themeDisplay,
			HttpServletRequest httpServletRequest, String backURL)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		if (AssetEntryPermission.contains(
				themeDisplay.getPermissionChecker(), assetEntry,
				ActionKeys.UPDATE)) {

			PortletURL portletURL = assetRenderer.getURLEdit(
				httpServletRequest, LiferayWindowState.NORMAL, backURL);

			if (portletURL != null) {
				jsonObject.put("editURL", portletURL.toString());
			}
		}

		if (AssetEntryPermission.contains(
				themeDisplay.getPermissionChecker(), assetEntry,
				ActionKeys.PERMISSIONS)) {

			String permissionsURL = PermissionsURLTag.doTag(
				StringPool.BLANK, assetEntry.getClassName(),
				HtmlUtil.escape(assetEntry.getTitle(themeDisplay.getLocale())),
				null, String.valueOf(assetEntry.getClassPK()),
				LiferayWindowState.POP_UP.toString(), null, httpServletRequest);

			if (Validator.isNotNull(permissionsURL)) {
				jsonObject.put("permissionsURL", permissionsURL);
			}
		}

		if (AssetEntryPermission.contains(
				themeDisplay.getPermissionChecker(), assetEntry,
				ActionKeys.VIEW)) {

			String viewUsagesURL = assetRenderer.getURLViewUsages(
				httpServletRequest);

			viewUsagesURL = HttpUtil.setParameter(
				viewUsagesURL, "p_p_state",
				LiferayWindowState.POP_UP.toString());

			if (viewUsagesURL != null) {
				jsonObject.put("viewUsagesURL", viewUsagesURL);
			}
		}

		return jsonObject;
	}

	private static AssetEntry _getAssetEntry(
		JSONObject jsonObject, Set<Long> mappedClassPKs) {

		if (!jsonObject.has("classNameId") || !jsonObject.has("classPK")) {
			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		if (classPK <= 0) {
			return null;
		}

		if (mappedClassPKs.contains(classPK)) {
			return null;
		}

		mappedClassPKs.add(classPK);

		long classNameId = jsonObject.getLong("classNameId");

		if (classNameId <= 0) {
			return null;
		}

		try {
			return AssetEntryServiceUtil.getEntry(
				PortalUtil.getClassName(classNameId), classPK);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get asset entry for class name ID ",
						classNameId, " with primary key ", classPK),
					pe);
			}
		}

		return null;
	}

	private static Set<AssetEntry> _getFragmentEntryLinkMappedAssetEntries(
		FragmentEntryLink fragmentEntryLink, Set<Long> mappedClassPKs) {

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to create JSON object from " +
						fragmentEntryLink.getEditableValues(),
					jsone);
			}

			return Collections.emptySet();
		}

		Set<AssetEntry> assetEntries = new HashSet<>();

		Iterator<String> keysIterator = editableValuesJSONObject.keys();

		while (keysIterator.hasNext()) {
			String key = keysIterator.next();

			JSONObject editableProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(key);

			if (editableProcessorJSONObject == null) {
				continue;
			}

			Iterator<String> editableKeysIterator =
				editableProcessorJSONObject.keys();

			while (editableKeysIterator.hasNext()) {
				String editableKey = editableKeysIterator.next();

				JSONObject editableJSONObject =
					editableProcessorJSONObject.getJSONObject(editableKey);

				if (editableJSONObject == null) {
					continue;
				}

				JSONObject configJSONObject = editableJSONObject.getJSONObject(
					"config");

				if ((configJSONObject != null) &&
					(configJSONObject.length() > 0)) {

					AssetEntry assetEntry = _getAssetEntry(
						configJSONObject, mappedClassPKs);

					if (assetEntry != null) {
						assetEntries.add(assetEntry);
					}
				}

				AssetEntry assetEntry = _getAssetEntry(
					editableJSONObject, mappedClassPKs);

				if (assetEntry == null) {
					continue;
				}

				assetEntries.add(assetEntry);
			}
		}

		return assetEntries;
	}

	private static Set<AssetEntry> _getFragmentEntryLinksMappedAssetEntries(
		long groupId, long plid, Set<Long> mappedClassPKs) {

		Set<AssetEntry> assetEntries = new HashSet<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				groupId, PortalUtil.getClassNameId(Layout.class.getName()),
				plid);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			assetEntries.addAll(
				_getFragmentEntryLinkMappedAssetEntries(
					fragmentEntryLink, mappedClassPKs));
		}

		return assetEntries;
	}

	private static Set<AssetEntry> _getLayoutMappedAssetEntries(
			long groupId, long plid, Set<Long> mappedClassPKs)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					groupId, PortalUtil.getClassNameId(Layout.class.getName()),
					plid, false);

		return _getLayoutMappedAssetEntries(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT),
			mappedClassPKs);
	}

	private static Set<AssetEntry> _getLayoutMappedAssetEntries(
			String layoutData, Set<Long> mappedClassPKs)
		throws PortalException {

		JSONObject layoutDataJSONObject = JSONFactoryUtil.createJSONObject(
			layoutData);

		JSONArray structureJSONArray = layoutDataJSONObject.getJSONArray(
			"structure");

		if (structureJSONArray == null) {
			return Collections.emptySet();
		}

		Set<AssetEntry> assetEntries = new HashSet<>();

		Iterator<JSONObject> iteratorStructure = structureJSONArray.iterator();

		iteratorStructure.forEachRemaining(
			structureJSONObject -> {
				JSONObject configJSONObject = structureJSONObject.getJSONObject(
					"config");

				if (configJSONObject != null) {
					JSONObject backgroundImageJSONObject =
						configJSONObject.getJSONObject("backgroundImage");

					if (backgroundImageJSONObject != null) {
						AssetEntry assetEntry = _getAssetEntry(
							backgroundImageJSONObject, mappedClassPKs);

						if (assetEntry != null) {
							assetEntries.add(assetEntry);
						}
					}
				}
			});

		return assetEntries;
	}

	private static JSONObject _getPageContentJSONObject(
			AssetEntry assetEntry, String backURL,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONObject mappedContentJSONObject = JSONUtil.put(
			"actions",
			_getActionsJSONObject(
				assetEntry, themeDisplay, httpServletRequest, backURL)
		).put(
			"className", assetEntry.getClassName()
		).put(
			"classNameId", assetEntry.getClassNameId()
		).put(
			"classPK", assetEntry.getClassPK()
		);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		String name = ResourceActionsUtil.getModelResource(
			themeDisplay.getLocale(), assetEntry.getClassName());

		if (assetEntry.getClassTypeId() > 0) {
			name = assetRendererFactory.getTypeName(
				themeDisplay.getLocale(), assetEntry.getClassTypeId());
		}

		return mappedContentJSONObject.put(
			"name", name
		).put(
			"status", _getStatusJSONObject(assetEntry)
		).put(
			"title", assetEntry.getTitle(themeDisplay.getLocale())
		).put(
			"usagesCount",
			AssetEntryUsageLocalServiceUtil.getUniqueAssetEntryUsagesCount(
				assetEntry.getEntryId())
		);
	}

	private static JSONObject _getStatusJSONObject(AssetEntry assetEntry)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			assetEntry.getAssetRendererFactory();

		AssetRenderer latestAssetRenderer =
			assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK(), AssetRendererFactory.TYPE_LATEST);

		boolean hasApprovedVersion = false;

		if (latestAssetRenderer.getStatus() !=
				WorkflowConstants.STATUS_APPROVED) {

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK(),
				AssetRendererFactory.TYPE_LATEST_APPROVED);

			if (assetRenderer.getStatus() ==
					WorkflowConstants.STATUS_APPROVED) {

				hasApprovedVersion = true;
			}
		}

		return JSONUtil.put(
			"hasApprovedVersion", hasApprovedVersion
		).put(
			"label",
			WorkflowConstants.getStatusLabel(latestAssetRenderer.getStatus())
		).put(
			"style",
			LabelItem.getStyleFromWorkflowStatus(
				latestAssetRenderer.getStatus())
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(ContentUtil.class);

}