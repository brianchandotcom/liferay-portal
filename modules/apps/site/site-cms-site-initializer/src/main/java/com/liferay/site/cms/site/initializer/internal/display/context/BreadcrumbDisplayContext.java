/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSpaceConstants;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;

import jakarta.portlet.ActionRequest;

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
					"href",
					PortletURLBuilder.create(
						PortalUtil.getControlPanelPortletURL(
							_httpServletRequest,
							"com_liferay_portlet_configuration_web_portlet_" +
								"PortletConfigurationPortlet",
							ActionRequest.RENDER_PHASE)
					).setMVCPath(
						"/edit_permissions.jsp"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"modelResource", DepotEntry.class.getName()
					).setParameter(
						"modelResourceDescription", group.getDescriptiveName()
					).setParameter(
						"resourcePrimKey", group.getClassPK()
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString()
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "permissions")
				).put(
					"symbolLeft", "password-policies"
				).put(
					"target", "modal"
				),
				JSONUtil.put(
					"confirmationMessage",
					LanguageUtil.get(
						_httpServletRequest,
						"are-you-sure-you-want-to-delete-this-entry")
				).put(
					"href",
					"/o/headless-asset-library/v1.0/asset-libraries/" +
						group.getClassPK()
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "delete")
				).put(
					"redirect",
					StringBundler.concat(
						_themeDisplay.getPathFriendlyURLPublic(),
						GroupConstants.CMS_FRIENDLY_URL, "/all-spaces")
				).put(
					"symbolLeft", "trash"
				).put(
					"target", "asyncDelete"
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