/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AllSectionDisplayContext {

	public AllSectionDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		return "/o/search/v1.0/search?emptySearch=true&nestedFields=embedded";
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return new ArrayList<>();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("forms");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "basic-content"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("upload");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "single-file"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("upload-multiple");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "multiple-files"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("blogs");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "blog"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("wiki");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "knowledge-base"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("video");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "external-video-shortcut"));
			}
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest, "click-new-to-create-your-first-content")
		).put(
			"image", "/states/cms_empty_state.svg"
		).put(
			"title", LanguageUtil.get(_httpServletRequest, "no-assets-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return new ArrayList<>();
	}

	private final HttpServletRequest _httpServletRequest;

}