/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterRegistry;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public abstract class BaseFilesSectionDisplayContext
	extends BaseSectionDisplayContext {

	public BaseFilesSectionDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		DLConfiguration dlConfiguration, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		ModelResourcePermission<ObjectEntryFolder>
			objectEntryFolderModelResourcePermission,
		Portal portal,
		TranslationInfoItemFieldValuesExporterRegistry
			translationInfoItemFieldValuesExporterRegistry) {

		super(
			depotEntryLocalService, dlConfiguration, groupLocalService,
			httpServletRequest, language, objectDefinitionService,
			objectDefinitionSettingLocalService,
			objectEntryFolderModelResourcePermission, portal,
			translationInfoItemFieldValuesExporterRegistry);
	}

	@Override
	public List<DropdownItem> getBulkActionDropdownItems() {
		return SectionDisplayContextUtil.getFilesBulkActionDropdownItems(
			httpServletRequest);
	}

	@Override
	public List<DropdownItem> getCreationMenuDropdownItems() {
		return ActionUtil.getFilesSectionCreationMenuDropdownItems(
			httpServletRequest, null);
	}

	@Override
	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(httpServletRequest, getEmptyStateDescriptionKey())
		).put(
			"image", "/states/cms_empty_state_files.svg"
		).put(
			"title", LanguageUtil.get(httpServletRequest, "no-files-yet")
		).build();
	}

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return SectionDisplayContextUtil.getFilesFDSActionDropdownItems(
			httpServletRequest);
	}

	protected abstract String getEmptyStateDescriptionKey();

	@Override
	protected String[] getObjectFolderExternalReferenceCodes() {
		return new String[] {
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
		};
	}

	@Override
	protected String getRootObjectEntryFolderExternalReferenceCode() {
		return ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES;
	}

}