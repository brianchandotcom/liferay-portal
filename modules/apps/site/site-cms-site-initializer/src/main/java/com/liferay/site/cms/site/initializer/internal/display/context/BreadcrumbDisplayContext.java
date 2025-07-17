/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSpaceConstants;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Marco Galluzzi
 */
public class BreadcrumbDisplayContext {

	public BreadcrumbDisplayContext(
		long groupId, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, String size) {

		_groupId = groupId;
		_groupLocalService = groupLocalService;
		_httpServletRequest = httpServletRequest;
		_size = GetterUtil.get(size, CMSSpaceConstants.SPACE_STICKER_LG);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getProps() throws Exception {
		Group group = _groupLocalService.getGroup(_groupId);

		return HashMapBuilder.<String, Object>put(
			"actionItems",
			JSONUtil.putAll(
				JSONUtil.put(
					"href",
					ActionUtil.getSpaceSettingsURL(
						group.getClassPK(), _themeDisplay.getURLCurrent(),
						_themeDisplay)
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "space-settings")
				).put(
					"symbolLeft", "cog"
				),
				JSONUtil.put(
					"label",
					LanguageUtil.get(_httpServletRequest, "permissions")
				).put(
					"symbolLeft", "password-policies"
				),
				JSONUtil.put(
					"label", LanguageUtil.get(_httpServletRequest, "delete")
				).put(
					"symbolLeft", "trash"
				))
		).put(
			"breadcrumbItems",
			JSONUtil.put(
				JSONUtil.put(
					"active", false
				).put(
					"href", StringPool.BLANK
				).put(
					"label", group.getDescriptiveName(_themeDisplay.getLocale())
				))
		).put(
			"displayType",
			() -> {
				UnicodeProperties unicodeProperties =
					group.getTypeSettingsProperties();

				return GetterUtil.get(
					unicodeProperties.get("logoColor"), "outline-0");
			}
		).put(
			"size", _size
		).build();
	}

	private final long _groupId;
	private final GroupLocalService _groupLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final String _size;
	private final ThemeDisplay _themeDisplay;

}