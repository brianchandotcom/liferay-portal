/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Sam Ziemer
 */
public class ContentsSectionDisplayContext extends BaseSectionDisplayContext {

	public ContentsSectionDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		Portal portal) {

		super(
			depotEntryLocalService, groupLocalService, httpServletRequest,
			language, objectDefinitionService,
			objectDefinitionSettingLocalService, portal);
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"parentObjectEntryFolderExternalReferenceCode",
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS
		).build();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.putData("action", "createFolder");
						dropdownItem.putData(
							"assetLibraries",
							getDepotEntriesJSONArray(
								depotEntryLocalService.getDepotEntries(
									QueryUtil.ALL_POS, QueryUtil.ALL_POS)));
						dropdownItem.putData(
							"baseAssetLibraryViewURL",
							StringBundler.concat(
								themeDisplay.getPathFriendlyURLPublic(),
								GroupConstants.CMS_FRIENDLY_URL, "/e/space/",
								portal.getClassNameId(DepotEntry.class),
								StringPool.SLASH));
						dropdownItem.putData(
							"baseFolderViewURL",
							StringBundler.concat(
								themeDisplay.getPathFriendlyURLPublic(),
								GroupConstants.CMS_FRIENDLY_URL,
								"/e/view-folder/",
								portal.getClassNameId(ObjectEntryFolder.class),
								StringPool.SLASH));
						dropdownItem.setIcon("folder");
						dropdownItem.setLabel(
							language.get(httpServletRequest, "folder"));
					});

				addStructureContentDropdownItems(this);
			}
		};
	}

	@Override
	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			language.get(
				httpServletRequest,
				"click-new-to-create-your-first-piece-of-content")
		).put(
			"image", "/states/cms_empty_state_content.svg"
		).put(
			"title", language.get(httpServletRequest, "no-content-yet")
		).build();
	}

	@Override
	protected String getCMSSectionFilterString() {
		return "cmsSection eq 'contents' and cmsRoot eq true";
	}

	@Override
	protected String[] getObjectFolderExternalReferenceCodes() {
		return new String[] {
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES
		};
	}

	@Override
	protected String getRootObjectEntryFolderExternalReferenceCode() {
		return ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS;
	}

}