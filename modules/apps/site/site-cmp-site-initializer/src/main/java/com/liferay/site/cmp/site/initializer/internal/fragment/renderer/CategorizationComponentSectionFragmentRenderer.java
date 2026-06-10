/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.util.ObjectEntryUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Larissa Ribeiro
 */
@Component(service = FragmentRenderer.class)
public class CategorizationComponentSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	protected String getComponentName(HttpServletRequest httpServletRequest) {
		return "Categorization";
	}

	@Override
	protected String getLabelKey() {
		return "categorization";
	}

	@Override
	protected String getModuleName() {
		return "site-cmp-site-initializer";
	}

	@Override
	protected Map<String, Object> getProps(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		ObjectEntry objectEntry = ObjectEntryUtil.getObjectEntry(
			httpServletRequest);

		if (objectEntry == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<AssetCategory> categories =
			_assetCategoryLocalService.getCategories(
				objectEntry.getModelClassName(),
				objectEntry.getObjectEntryId());

		return HashMapBuilder.<String, Object>put(
			"cmsGroupId", themeDisplay.getScopeGroupId()
		).put(
			"funnelStagesVocabularyERC", "L_FUNNEL_STAGE"
		).put(
			"hasUpdatePermission",
			() -> {
				ModelResourcePermission<ObjectEntry> modelResourcePermission =
					_objectEntryService.getModelResourcePermission(
						objectEntry.getObjectDefinitionId());

				return modelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), objectEntry,
					ActionKeys.UPDATE);
			}
		).put(
			"objectEntryKeywords",
			ListUtil.toArray(
				_assetTagLocalService.getTags(
					objectEntry.getModelClassName(),
					objectEntry.getObjectEntryId()),
				AssetTag.NAME_ACCESSOR)
		).put(
			"personasVocabularyERC", "L_PERSONAS"
		).put(
			"selectedFunnelStageCategories",
			() -> _getSelectedCategoriesJSONArray(
				categories, themeDisplay, "L_FUNNEL_STAGE")
		).put(
			"selectedPersonaCategories",
			() -> _getSelectedCategoriesJSONArray(
				categories, themeDisplay, "L_PERSONAS")
		).build();
	}

	private JSONArray _getSelectedCategoriesJSONArray(
		List<AssetCategory> categories, ThemeDisplay themeDisplay,
		String vocabularyERC) {

		AssetVocabulary vocabulary =
			_assetVocabularyLocalService.
				fetchAssetVocabularyByExternalReferenceCode(
					vocabularyERC, themeDisplay.getSiteGroupId());

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		if (vocabulary == null) {
			return jsonArray;
		}

		for (AssetCategory category : categories) {
			if (category.getVocabularyId() != vocabulary.getVocabularyId()) {
				continue;
			}

			jsonArray.put(
				JSONUtil.put(
					"id", category.getCategoryId()
				).put(
					"name", category.getTitle(themeDisplay.getLocale())
				));
		}

		return jsonArray;
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectEntryService _objectEntryService;

}