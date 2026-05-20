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
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerFDSNames;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterRegistry;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Marco Galluzzi
 */
public class ViewFolderSectionDisplayContext extends BaseSectionDisplayContext {

	public ViewFolderSectionDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		DLConfiguration dlConfiguration, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService,
		ObjectEntryFolderLocalService objectEntryFolderLocalService,
		Portal portal,
		TranslationInfoItemFieldValuesExporterRegistry
			translationInfoItemFieldValuesExporterRegistry) {

		super(
			depotEntryLocalService, dlConfiguration, groupLocalService,
			httpServletRequest, language, objectDefinitionService, portal,
			translationInfoItemFieldValuesExporterRegistry);

		_objectEntryFolderLocalService = objectEntryFolderLocalService;
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		Boolean contentsFolder = Objects.equals(
			getRootObjectEntryFolderExternalReferenceCode(),
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS);

		boolean rootFolder = false;

		if (objectEntryFolder != null) {
			rootFolder = Objects.equals(
				objectEntryFolder.getExternalReferenceCode(),
				getRootObjectEntryFolderExternalReferenceCode());
		}

		return new HashMapBuilder<>().putAll(
			super.getAdditionalProps()
		).put(
			"breadcrumbProps", getBreadcrumbProps()
		).put(
			"galleryViewEnabled", !contentsFolder
		).put(
			"rootFolder", rootFolder
		).put(
			"rootObjectEntryFolderExternalReferenceCode",
			getRootObjectEntryFolderExternalReferenceCode()
		).build();
	}

	public Map<String, Object> getBreadcrumbProps() {
		if (objectEntryFolder == null) {
			return Collections.emptyMap();
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Group group = groupLocalService.fetchGroup(
			objectEntryFolder.getGroupId());

		addBreadcrumbItem(
			jsonArray, false,
			ActionUtil.getSpaceURL(group.getClassPK(), themeDisplay),
			group.getName(themeDisplay.getLocale()));

		String[] parts = StringUtil.split(
			objectEntryFolder.getTreePath(), CharPool.SLASH);

		if (parts.length > 2) {
			for (int i = 1; i < (parts.length - 1); i++) {
				ObjectEntryFolder objectEntryFolder =
					_objectEntryFolderLocalService.fetchObjectEntryFolder(
						GetterUtil.getLong(parts[i]));

				addBreadcrumbItem(
					jsonArray, false,
					ActionUtil.getViewFolderURL(
						objectEntryFolder.getObjectEntryFolderId(),
						themeDisplay),
					objectEntryFolder.getLabel(themeDisplay.getLocale()));
			}
		}

		addBreadcrumbItem(
			jsonArray, true, null,
			objectEntryFolder.getLabel(themeDisplay.getLocale()));

		return HashMapBuilder.<String, Object>put(
			"breadcrumbItems", jsonArray
		).put(
			"displayType",
			() -> {
				UnicodeProperties unicodeProperties =
					group.getTypeSettingsProperties();

				return GetterUtil.get(
					unicodeProperties.get("logoColor"), "outline-0");
			}
		).put(
			"size", "sm"
		).build();
	}

	@Override
	public List<DropdownItem> getBulkActionDropdownItems() {
		if (_isContentsFolder()) {
			return SectionDisplayContextUtil.getContentsBulkActionDropdownItems(
				httpServletRequest);
		}

		return SectionDisplayContextUtil.getFilesBulkActionDropdownItems(
			httpServletRequest);
	}

	public String getCMSSiteInitializerFDSName() {
		if (Objects.equals(
				getRootObjectEntryFolderExternalReferenceCode(),
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS)) {

			return CMSSiteInitializerFDSNames.VIEW_CONTENTS_FOLDER;
		}

		return CMSSiteInitializerFDSNames.VIEW_FILES_FOLDER;
	}

	@Override
	public List<DropdownItem> getCreationMenuDropdownItems() {
		if (Objects.equals(
				getRootObjectEntryFolderExternalReferenceCode(),
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS)) {

			return ActionUtil.getContentsSectionCreationMenuDropdownItems(
				httpServletRequest, objectEntryFolder);
		}

		return ActionUtil.getFilesSectionCreationMenuDropdownItems(
			httpServletRequest, objectEntryFolder);
	}

	@Override
	public Map<String, Object> getEmptyState() {
		String rootObjectEntryFolderExternalReferenceCode =
			getRootObjectEntryFolderExternalReferenceCode();

		String description = "click-new-or-drag-and-drop-your-files-here";
		String image = "/states/cms_empty_state.svg";
		String title = "no-assets-yet";

		if (Objects.equals(
				rootObjectEntryFolderExternalReferenceCode,
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS)) {

			description = "click-new-to-create-your-first-piece-of-content";
			image = "/states/cms_empty_state_content.svg";
			title = "no-content-yet";
		}
		else if (Objects.equals(
					rootObjectEntryFolderExternalReferenceCode,
					ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES)) {

			description = "click-new-or-drag-and-drop-your-files-here";
			image = "/states/cms_empty_state_files.svg";
			title = "no-files-yet";
		}

		return HashMapBuilder.<String, Object>put(
			"description", LanguageUtil.get(httpServletRequest, description)
		).put(
			"image", image
		).put(
			"title", LanguageUtil.get(httpServletRequest, title)
		).build();
	}

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		if (_isContentsFolder()) {
			return SectionDisplayContextUtil.getContentsFDSActionDropdownItems(
				httpServletRequest);
		}

		return SectionDisplayContextUtil.getFilesFDSActionDropdownItems(
			httpServletRequest);
	}

	@Override
	public String[] getObjectFolderExternalReferenceCodes() {
		if (_objectFolderExternalReferenceCode != null) {
			return new String[] {_objectFolderExternalReferenceCode};
		}

		String rootObjectEntryFolderExternalReferenceCode =
			getRootObjectEntryFolderExternalReferenceCode();

		if (rootObjectEntryFolderExternalReferenceCode == null) {
			return new String[0];
		}

		if (rootObjectEntryFolderExternalReferenceCode.equals(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS)) {

			_objectFolderExternalReferenceCode =
				ObjectFolderConstants.
					EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES;
		}

		if (rootObjectEntryFolderExternalReferenceCode.equals(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES)) {

			_objectFolderExternalReferenceCode =
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES;
		}

		return new String[] {_objectFolderExternalReferenceCode};
	}

	public String getPropsTransformerModule() {
		String rootObjectEntryFolderExternalReferenceCode =
			getRootObjectEntryFolderExternalReferenceCode();

		if (rootObjectEntryFolderExternalReferenceCode.equals(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES)) {

			return "{AssetsFilesDropFDSPropsTransformer} from " +
				"site-cms-site-initializer";
		}

		return "{AssetsFDSPropsTransformer} from site-cms-site-initializer";
	}

	@Override
	public String getRootObjectEntryFolderExternalReferenceCode() {
		if (_rootObjectEntryFolderExternalReferenceCode != null) {
			return _rootObjectEntryFolderExternalReferenceCode;
		}

		if (objectEntryFolder == null) {
			return null;
		}

		String[] parts = StringUtil.split(
			objectEntryFolder.getTreePath(), CharPool.SLASH);

		if (parts.length <= 2) {
			_rootObjectEntryFolderExternalReferenceCode =
				objectEntryFolder.getExternalReferenceCode();

			return _rootObjectEntryFolderExternalReferenceCode;
		}

		ObjectEntryFolder rootObjectEntryFolder =
			_objectEntryFolderLocalService.fetchObjectEntryFolder(
				GetterUtil.getLong(parts[1]));

		if (rootObjectEntryFolder == null) {
			return null;
		}

		_rootObjectEntryFolderExternalReferenceCode =
			rootObjectEntryFolder.getExternalReferenceCode();

		return _rootObjectEntryFolderExternalReferenceCode;
	}

	@Override
	protected String getCMSSectionFilterString() {
		return null;
	}

	@Override
	protected boolean isFolderSearchEnabled() {
		return true;
	}

	private boolean _isContentsFolder() {
		return Objects.equals(
			getRootObjectEntryFolderExternalReferenceCode(),
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS);
	}

	private final ObjectEntryFolderLocalService _objectEntryFolderLocalService;
	private String _objectFolderExternalReferenceCode;
	private String _rootObjectEntryFolderExternalReferenceCode;

}