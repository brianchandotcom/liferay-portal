/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam Ziemer
 */
public class FilesSectionDisplayContext extends BaseSectionDisplayContext {

	public FilesSectionDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService) {

		super(httpServletRequest, language, objectDefinitionService);
	}

	@Override
	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.putData("action", "createFolder");
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
			LanguageUtil.get(
				httpServletRequest, "click-new-to-create-your-first-file")
		).put(
			"image", "/states/cms_empty_state_files.svg"
		).put(
			"title", LanguageUtil.get(httpServletRequest, "no-files-yet")
		).build();
	}

	@Override
	public String[] getObjectDefinitionFolderExternalReferenceCodes() {
		return new String[] {
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
		};
	}

	@Override
	protected String getCMSSectionFilterString() {
		return "cmsSection eq 'files'";
	}

}