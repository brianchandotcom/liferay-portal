/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.site.cms.site.initializer.internal.configuration.CMSSiteInitializerConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam Ziemer
 */
public class FilesSectionDisplayContext {

	public FilesSectionDisplayContext(
		CMSSiteInitializerConfiguration cmsSiteInitializerConfiguration,
		HttpServletRequest httpServletRequest) {

		_cmsSiteInitializerConfiguration = cmsSiteInitializerConfiguration;
		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(3);

		sb.append("/o/search/v1.0/search?emptySearch=true&entryClassNames=");
		sb.append(
			ArrayUtil.toString(
				_cmsSiteInitializerConfiguration.filesClassNames(),
				StringPool.BLANK));
		sb.append("&nestedFields=embedded");

		return sb.toString();
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
				dropdownItem.setIcon("folder");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "folder"));
			}
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest, "click-new-to-create-your-first-file")
		).put(
			"image", "/states/cms_empty_state_files.svg"
		).put(
			"title", LanguageUtil.get(_httpServletRequest, "no-files-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return new ArrayList<>();
	}

	private final CMSSiteInitializerConfiguration
		_cmsSiteInitializerConfiguration;
	private final HttpServletRequest _httpServletRequest;

}