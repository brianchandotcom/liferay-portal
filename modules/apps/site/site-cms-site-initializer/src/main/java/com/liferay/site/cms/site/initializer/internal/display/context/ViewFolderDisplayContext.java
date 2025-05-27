/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public class ViewFolderDisplayContext extends BaseSectionDisplayContext {

	public ViewFolderDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		ObjectEntryFolderLocalService objectEntryFolderLocalService,
		Portal portal) {

		super(
			depotEntryLocalService, groupLocalService, httpServletRequest,
			language, objectDefinitionService,
			objectDefinitionSettingLocalService, portal);

		_objectEntryFolderLocalService = objectEntryFolderLocalService;
	}

	@Override
	public Map<String, Object> getEmptyState() {
		String rootObjectEntryFolderExternalReferenceCode =
			getRootObjectEntryFolderExternalReferenceCode();

		String description = "click-new-to-create-your-first-asset";
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

			description = "click-new-to-create-your-first-file";
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
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			super.getFDSActionDropdownItems();

		fdsActionDropdownItems.add(
			1,
			new FDSActionDropdownItem(
				"{embedded.file.link.href}", "download", "download",
				LanguageUtil.get(httpServletRequest, "download"), "get", null,
				"link"));

		return fdsActionDropdownItems;
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
			return null;
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

	private final ObjectEntryFolderLocalService _objectEntryFolderLocalService;
	private String _objectFolderExternalReferenceCode;
	private String _rootObjectEntryFolderExternalReferenceCode;

}