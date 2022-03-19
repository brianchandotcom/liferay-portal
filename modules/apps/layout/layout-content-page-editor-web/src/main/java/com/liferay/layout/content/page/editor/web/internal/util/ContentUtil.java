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
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.info.display.url.provider.InfoEditURLProviderUtil;
import com.liferay.layout.content.page.editor.web.internal.layout.display.page.LayoutDisplayPageProviderTrackerUtil;
import com.liferay.layout.content.page.editor.web.internal.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalServiceUtil;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Víctor Galán
 */
public class ContentUtil {

	public static Set<LayoutDisplayPageObjectProvider<?>>
		getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
			FragmentEntryLink fragmentEntryLink) {

		return _getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
			fragmentEntryLink, new HashSet<>());
	}

	public static Set<LayoutDisplayPageObjectProvider<?>>
		getLayoutMappedLayoutDisplayPageObjectProviders(String layoutData) {

		return _getLayoutMappedLayoutDisplayPageObjectProviders(
			LayoutStructure.of(layoutData), new HashSet<>());
	}

	public static Set<LayoutDisplayPageObjectProvider<?>>
			getMappedLayoutDisplayPageObjectProviders(long groupId, long plid)
		throws PortalException {

		Set<Long> mappedClassPKs = new HashSet<>();

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders =
				_getFragmentEntryLinksMappedLayoutDisplayPageObjectProviders(
					groupId, plid, mappedClassPKs);

		layoutDisplayPageObjectProviders.addAll(
			_getLayoutMappedLayoutDisplayPageObjectProviders(
				groupId, plid, mappedClassPKs));

		return layoutDisplayPageObjectProviders;
	}

	public static JSONArray getPageContentsJSONArray(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long plid,
			long segmentsExperienceId)
		throws PortalException {

		return JSONUtil.concat(
			_getLayoutClassedModelPageContentsJSONArray(
				httpServletRequest, plid, segmentsExperienceId),
			AssetListEntryUsagesUtil.getPageContentsJSONArray(
				httpServletRequest, httpServletResponse, plid));
	}

	private static String _generateUniqueLayoutClassedModelUsageKey(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		return layoutClassedModelUsage.getClassNameId() + StringPool.DASH +
			layoutClassedModelUsage.getClassPK();
	}

	private static JSONObject _getActionsJSONObject(
			LayoutClassedModelUsage layoutClassedModelUsage,
			ThemeDisplay themeDisplay, HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String className = layoutClassedModelUsage.getClassName();

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			LayoutDisplayPageProviderTrackerUtil.getLayoutDisplayPageProvider(
				layoutClassedModelUsage.getClassName());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					className, layoutClassedModelUsage.getClassPK()));

		if (ModelResourcePermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				layoutClassedModelUsage.getClassName(),
				layoutClassedModelUsage.getClassPK(), ActionKeys.UPDATE)) {

			String editURL = InfoEditURLProviderUtil.getURLEdit(
				layoutClassedModelUsage.getClassName(),
				layoutDisplayPageObjectProvider.getDisplayObject(),
				httpServletRequest);

			if (editURL != null) {
				jsonObject.put("editURL", editURL);
			}

			if (Objects.equals(
					layoutClassedModelUsage.getClassName(),
					FileEntry.class.getName())) {

				FileEntry fileEntry =
					(FileEntry)
						layoutDisplayPageObjectProvider.getDisplayObject();

				PortletResponse portletResponse =
					(PortletResponse)httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE);

				LiferayPortletResponse liferayPortletResponse =
					PortalUtil.getLiferayPortletResponse(portletResponse);

				LiferayPortletURL portletURL =
					liferayPortletResponse.createActionURL(
						DLPortletKeys.DOCUMENT_LIBRARY_ADMIN);

				portletURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/document_library/edit_file_entry_image_editor");

				jsonObject.put(
					"editImage",
					JSONUtil.put(
						"editImageURL", portletURL.toString()
					).put(
						"fileEntryId", fileEntry.getFileEntryId()
					).put(
						"previewURL",
						DLURLHelperUtil.getPreviewURL(
							fileEntry, fileEntry.getFileVersion(), themeDisplay,
							StringPool.BLANK)
					));
			}
		}

		if (ModelResourcePermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				layoutClassedModelUsage.getClassName(),
				layoutClassedModelUsage.getClassPK(), ActionKeys.PERMISSIONS)) {

			String permissionsURL = PermissionsURLTag.doTag(
				StringPool.BLANK, layoutClassedModelUsage.getClassName(),
				HtmlUtil.escape(
					layoutDisplayPageObjectProvider.getTitle(
						themeDisplay.getLocale())),
				null, String.valueOf(layoutClassedModelUsage.getClassPK()),
				LiferayWindowState.POP_UP.toString(), null, httpServletRequest);

			if (Validator.isNotNull(permissionsURL)) {
				jsonObject.put("permissionsURL", permissionsURL);
			}
		}

		return jsonObject.put(
			"viewUsagesURL",
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest,
					ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
					PortletRequest.RENDER_PHASE)
			).setMVCPath(
				"/view_layout_classed_model_usages.jsp"
			).setParameter(
				"className", layoutClassedModelUsage.getClassName()
			).setParameter(
				"classPK", layoutClassedModelUsage.getClassPK()
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString());
	}

	private static AssetRendererFactory<?> _getAssetRendererFactory(
		String className) {

		// LPS-111037

		if (Objects.equals(className, FileEntry.class.getName())) {
			className = DLFileEntry.class.getName();
		}

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(className);
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
		_getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
			FragmentEntryLink fragmentEntryLink, Set<Long> mappedClassPKs) {

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to create JSON object from " +
						fragmentEntryLink.getEditableValues(),
					jsonException);
			}

			return Collections.emptySet();
		}

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders = new HashSet<>();

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

				layoutDisplayPageObjectProviders.addAll(
					_getLocalizedLayoutDisplayPageObjectProviders(
						editableJSONObject, mappedClassPKs));

				JSONObject configJSONObject = editableJSONObject.getJSONObject(
					"config");

				if ((configJSONObject != null) &&
					(configJSONObject.length() > 0)) {

					LayoutDisplayPageObjectProvider<?>
						layoutDisplayPageObjectProvider =
							_getLayoutDisplayPageObjectProvider(
								configJSONObject, mappedClassPKs);

					if (layoutDisplayPageObjectProvider != null) {
						layoutDisplayPageObjectProviders.add(
							layoutDisplayPageObjectProvider);
					}

					layoutDisplayPageObjectProviders.addAll(
						_getLocalizedLayoutDisplayPageObjectProviders(
							configJSONObject, mappedClassPKs));
				}

				JSONObject itemSelectorJSONObject =
					editableJSONObject.getJSONObject("itemSelector");

				if ((itemSelectorJSONObject != null) &&
					(itemSelectorJSONObject.length() > 0)) {

					LayoutDisplayPageObjectProvider<?>
						layoutDisplayPageObjectProvider =
							_getLayoutDisplayPageObjectProvider(
								itemSelectorJSONObject, mappedClassPKs);

					if (layoutDisplayPageObjectProvider != null) {
						layoutDisplayPageObjectProviders.add(
							layoutDisplayPageObjectProvider);
					}
				}

				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						_getLayoutDisplayPageObjectProvider(
							editableJSONObject, mappedClassPKs);

				if (layoutDisplayPageObjectProvider == null) {
					continue;
				}

				layoutDisplayPageObjectProviders.add(
					layoutDisplayPageObjectProvider);
			}
		}

		return layoutDisplayPageObjectProviders;
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
		_getFragmentEntryLinksMappedLayoutDisplayPageObjectProviders(
			long groupId, long plid, Set<Long> mappedClassPKs) {

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders = new HashSet<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksByPlid(
				groupId, plid);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			layoutDisplayPageObjectProviders.addAll(
				_getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
					fragmentEntryLink, mappedClassPKs));
		}

		return layoutDisplayPageObjectProviders;
	}

	private static String _getIcon(String className, long classPK)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory = _getAssetRendererFactory(
			className);

		if (assetRendererFactory == null) {
			return "web-content";
		}

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			classPK);

		if (assetRenderer == null) {
			return "web-content";
		}

		return assetRenderer.getIconCssClass();
	}

	private static JSONArray _getLayoutClassedModelPageContentsJSONArray(
			HttpServletRequest httpServletRequest, long plid,
			long segmentsExperienceId)
		throws PortalException {

		JSONArray mappedContentsJSONArray = JSONFactoryUtil.createJSONArray();

		long fragmentEntryLinkClassNameId = PortalUtil.getClassNameId(
			FragmentEntryLink.class);
		LayoutStructure layoutStructure = null;
		Set<String> uniqueLayoutClassedModelUsageKeys = new HashSet<>();

		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			LayoutClassedModelUsageLocalServiceUtil.
				getLayoutClassedModelUsagesByPlid(plid);

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			if (uniqueLayoutClassedModelUsageKeys.contains(
					_generateUniqueLayoutClassedModelUsageKey(
						layoutClassedModelUsage))) {

				continue;
			}

			if (layoutClassedModelUsage.getContainerType() ==
					fragmentEntryLinkClassNameId) {

				FragmentEntryLink fragmentEntryLink =
					FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
						GetterUtil.getLong(
							layoutClassedModelUsage.getContainerKey()));

				if (fragmentEntryLink == null) {
					LayoutClassedModelUsageLocalServiceUtil.
						deleteLayoutClassedModelUsage(layoutClassedModelUsage);

					continue;
				}

				if (!Objects.equals(
						fragmentEntryLink.getSegmentsExperienceId(),
						segmentsExperienceId)) {

					continue;
				}

				if (layoutStructure == null) {
					layoutStructure = LayoutStructureUtil.getLayoutStructure(
						fragmentEntryLink.getGroupId(),
						fragmentEntryLink.getPlid(),
						fragmentEntryLink.getSegmentsExperienceId());
				}

				LayoutStructureItem layoutStructureItem =
					layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
						fragmentEntryLink.getFragmentEntryLinkId());

				if (ListUtil.exists(
						layoutStructure.getDeletedLayoutStructureItems(),
						deletedLayoutStructureItem -> Objects.equals(
							deletedLayoutStructureItem.getItemId(),
							layoutStructureItem.getItemId()))) {

					continue;
				}
			}

			try {
				mappedContentsJSONArray.put(
					_getPageContentJSONObject(
						layoutClassedModelUsage, httpServletRequest));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"An error occurred while getting mapped content ",
							"with class PK ",
							layoutClassedModelUsage.getClassPK(),
							" and class name ID ",
							layoutClassedModelUsage.getClassNameId()),
						exception);
				}
			}

			uniqueLayoutClassedModelUsageKeys.add(
				_generateUniqueLayoutClassedModelUsageKey(
					layoutClassedModelUsage));
		}

		return mappedContentsJSONArray;
	}

	private static LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(
			JSONObject jsonObject, Set<Long> mappedClassPKs) {

		if (!jsonObject.has("classNameId") || !jsonObject.has("classPK")) {
			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		if ((classPK <= 0) || mappedClassPKs.contains(classPK)) {
			return null;
		}

		long classNameId = jsonObject.getLong("classNameId");

		if (classNameId <= 0) {
			return null;
		}

		String className = PortalUtil.getClassName(classNameId);

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			LayoutDisplayPageProviderTrackerUtil.getLayoutDisplayPageProvider(
				className);

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		mappedClassPKs.add(classPK);

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			new InfoItemReference(className, classPK));
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
		_getLayoutMappedLayoutDisplayPageObjectProviders(
			LayoutStructure layoutStructure, Set<Long> mappedClassPKs) {

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders = new HashSet<>();

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof
					ContainerStyledLayoutStructureItem) ||
				ListUtil.exists(
					layoutStructure.getDeletedLayoutStructureItems(),
					deletedLayoutStructureItem ->
						deletedLayoutStructureItem.containsItemId(
							layoutStructureItem.getItemId()))) {

				continue;
			}

			ContainerStyledLayoutStructureItem
				containerStyledLayoutStructureItem =
					(ContainerStyledLayoutStructureItem)layoutStructureItem;

			JSONObject backgroundImageJSONObject =
				containerStyledLayoutStructureItem.
					getBackgroundImageJSONObject();

			if (backgroundImageJSONObject != null) {
				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						_getLayoutDisplayPageObjectProvider(
							backgroundImageJSONObject, mappedClassPKs);

				if (layoutDisplayPageObjectProvider != null) {
					layoutDisplayPageObjectProviders.add(
						layoutDisplayPageObjectProvider);
				}
			}

			JSONObject linkJSONObject =
				containerStyledLayoutStructureItem.getLinkJSONObject();

			if (linkJSONObject != null) {
				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						_getLayoutDisplayPageObjectProvider(
							linkJSONObject, mappedClassPKs);

				if (layoutDisplayPageObjectProvider != null) {
					layoutDisplayPageObjectProviders.add(
						layoutDisplayPageObjectProvider);
				}

				layoutDisplayPageObjectProviders.addAll(
					_getLocalizedLayoutDisplayPageObjectProviders(
						linkJSONObject, mappedClassPKs));
			}
		}

		return layoutDisplayPageObjectProviders;
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
			_getLayoutMappedLayoutDisplayPageObjectProviders(
				long groupId, long plid, Set<Long> mappedClassPKs)
		throws PortalException {

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				groupId, plid, SegmentsExperienceConstants.ID_DEFAULT);

		return _getLayoutMappedLayoutDisplayPageObjectProviders(
			layoutStructure, mappedClassPKs);
	}

	private static Set<LayoutDisplayPageObjectProvider<?>>
		_getLocalizedLayoutDisplayPageObjectProviders(
			JSONObject jsonObject, Set<Long> mappedClassPKs) {

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders = new HashSet<>();

		Set<Locale> locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			JSONObject localizableJSONObject = jsonObject.getJSONObject(
				LocaleUtil.toLanguageId(locale));

			if ((localizableJSONObject == null) ||
				(localizableJSONObject.length() == 0)) {

				continue;
			}

			LayoutDisplayPageObjectProvider<?>
				localizedLayoutDisplayPageObjectProvider =
					_getLayoutDisplayPageObjectProvider(
						localizableJSONObject, mappedClassPKs);

			if (localizedLayoutDisplayPageObjectProvider != null) {
				layoutDisplayPageObjectProviders.add(
					localizedLayoutDisplayPageObjectProvider);
			}
		}

		return layoutDisplayPageObjectProviders;
	}

	private static JSONObject _getPageContentJSONObject(
			LayoutClassedModelUsage layoutClassedModelUsage,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONObject mappedContentJSONObject = JSONUtil.put(
			"actions",
			_getActionsJSONObject(
				layoutClassedModelUsage, themeDisplay, httpServletRequest)
		).put(
			"className", layoutClassedModelUsage.getClassName()
		).put(
			"classNameId", layoutClassedModelUsage.getClassNameId()
		).put(
			"classPK", layoutClassedModelUsage.getClassPK()
		).put(
			"icon",
			_getIcon(
				layoutClassedModelUsage.getClassName(),
				layoutClassedModelUsage.getClassPK())
		);

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			LayoutDisplayPageProviderTrackerUtil.getLayoutDisplayPageProvider(
				layoutClassedModelUsage.getClassName());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					layoutClassedModelUsage.getClassName(),
					layoutClassedModelUsage.getClassPK()));

		return mappedContentJSONObject.put(
			"classTypeId", layoutDisplayPageObjectProvider.getClassTypeId()
		).put(
			"status", _getStatusJSONObject(layoutClassedModelUsage)
		).put(
			"subtype",
			_getSubtype(
				layoutClassedModelUsage.getClassName(),
				layoutDisplayPageObjectProvider.getClassTypeId(),
				themeDisplay.getLocale())
		).put(
			"title",
			layoutDisplayPageObjectProvider.getTitle(themeDisplay.getLocale())
		).put(
			"type",
			ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(),
				layoutClassedModelUsage.getClassName())
		).put(
			"usagesCount",
			LayoutClassedModelUsageLocalServiceUtil.
				getUniqueLayoutClassedModelUsagesCount(
					layoutClassedModelUsage.getClassNameId(),
					layoutClassedModelUsage.getClassPK())
		);
	}

	private static JSONObject _getStatusJSONObject(
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					layoutClassedModelUsage.getClassNameId());

		if (assetRendererFactory == null) {
			return JSONUtil.put(
				"hasApprovedVersion", false
			).put(
				"label",
				WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_APPROVED)
			).put(
				"style",
				WorkflowConstants.getStatusStyle(
					WorkflowConstants.STATUS_APPROVED)
			);
		}

		AssetRenderer<?> latestAssetRenderer =
			assetRendererFactory.getAssetRenderer(
				layoutClassedModelUsage.getClassPK(),
				AssetRendererFactory.TYPE_LATEST);

		boolean hasApprovedVersion = false;

		if (latestAssetRenderer.getStatus() !=
				WorkflowConstants.STATUS_APPROVED) {

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					layoutClassedModelUsage.getClassPK(),
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
			WorkflowConstants.getStatusStyle(latestAssetRenderer.getStatus())
		);
	}

	private static String _getSubtype(
		String className, long classTypeId, Locale locale) {

		AssetRendererFactory<?> assetRendererFactory = _getAssetRendererFactory(
			className);

		if (assetRendererFactory == null) {
			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		try {
			ClassType classType = classTypeReader.getClassType(
				classTypeId, locale);

			return classType.getName();
		}
		catch (Exception exception) {
			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ContentUtil.class);

}