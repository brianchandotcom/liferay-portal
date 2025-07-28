/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Mikel Lorza
 */
public class ViewVersionHistoryDisplayContext {

	public ViewVersionHistoryDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinition objectDefinition, ObjectEntry objectEntry) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_objectDefinition = objectDefinition;
		_objectEntry = objectEntry;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() throws PortalException {
		return StringBundler.concat(
			"/o", _objectDefinition.getRESTContextPath(), StringPool.SLASH,
			_objectEntry.getObjectEntryId(), "/versions");
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				"{file.link.href}", "download", "download",
				_language.get(_httpServletRequest, "download"), "get", null,
				"link"),
			new FDSActionDropdownItem(
				"{actions.restore.href}", "restore", "restore",
				_language.get(_httpServletRequest, "restore"), "put", "restore",
				"headless"),
			new FDSActionDropdownItem(
				"{actions.expire.href}", "time", "expire",
				_language.get(_httpServletRequest, "expire"), "post", "expire",
				"headless"),
			new FDSActionDropdownItem(
				"{actions.copy.href}", "copy", "copy",
				_language.get(_httpServletRequest, "make-a-copy"), "post",
				"copy", "headless"),
			new FDSActionDropdownItem(
				_language.get(
					_httpServletRequest,
					"are-you-sure-you-want-to-delete-this-entry"),
				"{actions.delete.href}", "trash", "delete",
				_language.get(_httpServletRequest, "delete"), "delete",
				"delete", "headless"));
	}

	public Map<String, Object> getProps() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"backURL", ParamUtil.getString(_httpServletRequest, "backURL")
		).put(
			"title", _objectEntry.getTitleValue(_themeDisplay.getLanguageId())
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntry _objectEntry;
	private final ThemeDisplay _themeDisplay;

}