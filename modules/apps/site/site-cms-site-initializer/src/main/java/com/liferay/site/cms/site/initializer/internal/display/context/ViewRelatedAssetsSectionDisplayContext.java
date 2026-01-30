/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterRegistry;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Carolina Barbosa
 */
public class ViewRelatedAssetsSectionDisplayContext
	extends BaseRelatedAssetsSectionDisplayContext {

	public ViewRelatedAssetsSectionDisplayContext(
		AssetTagLocalService assetTagLocalService,
		DepotEntryLocalService depotEntryLocalService,
		DLConfiguration dlConfiguration, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinition objectDefinition,
		ObjectDefinitionService objectDefinitionService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		ObjectEntry objectEntry,
		ModelResourcePermission<ObjectEntryFolder>
			objectEntryFolderModelResourcePermission,
		Portal portal,
		TranslationInfoItemFieldValuesExporterRegistry
			translationInfoItemFieldValuesExporterRegistry) {

		super(
			assetTagLocalService, depotEntryLocalService, dlConfiguration,
			groupLocalService, httpServletRequest, language, objectDefinition,
			objectDefinitionService, objectDefinitionSettingLocalService,
			objectEntry, objectEntryFolderModelResourcePermission, portal,
			translationInfoItemFieldValuesExporterRegistry);

		Set<String> tagNames = getTagNames(objectDefinition, objectEntry);

		_keywords = tagNames.toArray(new String[0]);

		_sectionDisplayContextHelper = new SectionDisplayContextHelper(
			depotEntryLocalService, groupLocalService, language,
			objectDefinitionSettingLocalService,
			objectEntryFolderModelResourcePermission, portal);
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"keywords", StringUtil.merge(_keywords)
		).putAll(
			super.getAdditionalProps()
		).build();
	}

	@Override
	public List<DropdownItem> getCreationMenuDropdownItems() {
		return ListUtil.fromArray(
			DropdownItemBuilder.putData(
				"action", "uploadMultipleFiles"
			).putData(
				"baseAssetLibraryViewURL",
				ActionUtil.getBaseSpaceURL(themeDisplay)
			).putData(
				"keywords", StringUtil.merge(_keywords)
			).putData(
				"parentObjectEntryFolderExternalReferenceCode", StringPool.BLANK
			).setIcon(
				"upload-multiple"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "upload")
			).build(),
			DropdownItemBuilder.putData(
				"action", "selectAssets"
			).putData(
				"keywords", StringUtil.merge(_keywords)
			).putData(
				"searchAPIURL",
				() -> {
					String additionalAPIURLParameters =
						_sectionDisplayContextHelper.
							getAdditionalAPIURLParameters(
								appendStatus(
									StringBundler.concat(
										"(cmsSection eq 'contents' or ",
										"cmsSection eq 'files') and not ",
										"(keywords/any(k:k in (",
										getKeywordsFilterString(),
										"))) and objectDefinitionId gt 0")),
								httpServletRequest, null);

					return "/o/search/v1.0/search?" +
						additionalAPIURLParameters;
				}
			).setIcon(
				"sheets"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "cms-assets")
			).build());
	}

	@Override
	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(httpServletRequest, "drag-and-drop-your-files-or")
		).putAll(
			super.getEmptyState()
		).build();
	}

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			super.getFDSActionDropdownItems();

		fdsActionDropdownItems.add(
			new FDSActionDropdownItem(
				null, "chain-broken", "unlink-asset",
				LanguageUtil.format(
					httpServletRequest, "remove-from-x",
					objectDefinition.getLabel(themeDisplay.getLocale())),
				null, "update", null));

		return fdsActionDropdownItems;
	}

	@Override
	protected String[] getKeywords() {
		return _keywords;
	}

	private final String[] _keywords;
	private final SectionDisplayContextHelper _sectionDisplayContextHelper;

}