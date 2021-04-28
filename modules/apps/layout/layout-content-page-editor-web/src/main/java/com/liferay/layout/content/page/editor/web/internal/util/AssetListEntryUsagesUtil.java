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
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntryUsageLocalServiceUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class AssetListEntryUsagesUtil {

	public static JSONArray getPageContentsJSONArray(
			HttpServletRequest httpServletRequest, long plid)
		throws PortalException {

		JSONArray mappedContentsJSONArray = JSONFactoryUtil.createJSONArray();

		List<AssetListEntryUsage> assetListEntryUsages =
			AssetListEntryUsageLocalServiceUtil.getAssetEntryListUsagesByPlid(
				plid);

		Set<String> uniqueAssetListEntryUsagesKeys = new HashSet<>();

		LayoutStructure layoutStructure = _getLayoutStructure(
			httpServletRequest, plid);

		for (AssetListEntryUsage assetListEntryUsage : assetListEntryUsages) {
			if (uniqueAssetListEntryUsagesKeys.contains(
					_generateUniqueLayoutClassedModelUsageKey(
						assetListEntryUsage)) ||
				_isFragmentEntryLinkDeleted(
					assetListEntryUsage, layoutStructure)) {

				continue;
			}

			mappedContentsJSONArray.put(
				_getPageContentJSONObject(
					assetListEntryUsage, httpServletRequest));

			uniqueAssetListEntryUsagesKeys.add(
				_generateUniqueLayoutClassedModelUsageKey(assetListEntryUsage));
		}

		return mappedContentsJSONArray;
	}

	private static String _generateUniqueLayoutClassedModelUsageKey(
		AssetListEntryUsage assetListEntryUsage) {

		return assetListEntryUsage.getClassNameId() + StringPool.DASH +
			assetListEntryUsage.getKey();
	}

	private static String _getAssetEntryListSubtypeLabel(
		AssetListEntry assetListEntry, Locale locale) {

		String typeLabel = ResourceActionsUtil.getModelResource(
			locale, assetListEntry.getAssetEntryType());

		String subtypeLabel = _getSubtypeLabel(assetListEntry, locale);

		if (Validator.isNull(subtypeLabel)) {
			return typeLabel;
		}

		return typeLabel + " - " + subtypeLabel;
	}

	private static LayoutStructure _getLayoutStructure(
			HttpServletRequest httpServletRequest, long plid)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return LayoutStructureUtil.getLayoutStructure(
			themeDisplay.getScopeGroupId(), plid,
			ParamUtil.getLong(httpServletRequest, "segmentsExperienceId"));
	}

	private static JSONObject _getPageContentJSONObject(
		AssetListEntryUsage assetListEntryUsage,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONObject mappedContentJSONObject = JSONUtil.put(
			"className", assetListEntryUsage.getClassName()
		).put(
			"classNameId", assetListEntryUsage.getClassNameId()
		).put(
			"classPK", assetListEntryUsage.getKey()
		).put(
			"icon", "list-ul"
		).put(
			"type", LanguageUtil.get(httpServletRequest, "collection")
		);

		if (Objects.equals(
				assetListEntryUsage.getClassName(),
				AssetListEntry.class.getName())) {

			AssetListEntry assetListEntry =
				AssetListEntryLocalServiceUtil.fetchAssetListEntry(
					GetterUtil.getLong(assetListEntryUsage.getKey()));

			if (assetListEntry != null) {
				mappedContentJSONObject.put(
					"subtype",
					_getAssetEntryListSubtypeLabel(
						assetListEntry, themeDisplay.getLocale())
				).put(
					"title", assetListEntry.getTitle()
				);
			}
		}

		if (Objects.equals(
				assetListEntryUsage.getClassName(),
				InfoListProvider.class.getName())) {

			InfoListProvider<?> infoListProvider =
				InfoListProviderTrackerUtil.getInfoListProvider(
					assetListEntryUsage.getKey());

			if (infoListProvider != null) {
				mappedContentJSONObject.put(
					"subtype",
					ResourceActionsUtil.getModelResource(
						themeDisplay.getLocale(),
						GenericUtil.getGenericClassName(infoListProvider))
				).put(
					"title", infoListProvider.getLabel(themeDisplay.getLocale())
				);
			}
		}

		return mappedContentJSONObject;
	}

	private static String _getSubtypeLabel(
		AssetListEntry assetListEntry, Locale locale) {

		long classTypeId = GetterUtil.getLong(
			assetListEntry.getAssetEntrySubtype(), -1);

		if (classTypeId < 0) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetListEntry.getAssetEntryType());

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

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

	private static boolean _isFragmentEntryLinkDeleted(
		AssetListEntryUsage assetListEntryUsage,
		LayoutStructure layoutStructure) {

		if (assetListEntryUsage.getContainerType() != PortalUtil.getClassNameId(
				FragmentEntryLink.class)) {

			return false;
		}

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				GetterUtil.getLong(assetListEntryUsage.getContainerKey()));

		if (fragmentEntryLink == null) {
			AssetListEntryUsageLocalServiceUtil.deleteAssetListEntryUsage(
				assetListEntryUsage);

			return true;
		}

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());

		if (ListUtil.exists(
				layoutStructure.getDeletedLayoutStructureItems(),
				deletedLayoutStructureItem ->
					deletedLayoutStructureItem.containsItemId(
						layoutStructureItem.getItemId()))) {

			return true;
		}

		return false;
	}

}