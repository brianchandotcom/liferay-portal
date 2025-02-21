/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.configuration.CMSSiteInitializerConfiguration;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam Ziemer
 */
public class ContentsSectionDisplayContext extends BaseSectionDisplayContext {

	public ContentsSectionDisplayContext(
		CMSSiteInitializerConfiguration cmsSiteInitializerConfiguration,
		HttpServletRequest httpServletRequest, Language language) {

		super(cmsSiteInitializerConfiguration, httpServletRequest);

		_language = language;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("forms");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "basic-content"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("blogs");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "blog"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setIcon("wiki");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "knowledge-base"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "createFolder");
				dropdownItem.setIcon("folder");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "folder"));
			}
		).build();
	}

	@Override
	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			_language.get(
				httpServletRequest,
				"click-new-to-create-your-first-piece-of-content")
		).put(
			"image", "/states/cms_empty_state_content.svg"
		).put(
			"title", _language.get(httpServletRequest, "no-content-yet")
		).build();
	}

	@Override
	public String[] getEntryClassNames() {
		return cmsSiteInitializerConfiguration.contentsClassNames();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						httpServletRequest,
						"com_liferay_portlet_configuration_web_portlet_" +
							"PortletConfigurationPortlet",
						ActionRequest.RENDER_PHASE)
				).setMVCPath(
					"/edit_permissions.jsp"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"modelResource", "{entryClassName}"
				).setParameter(
					"modelResourceDescription", "{embedded.name}"
				).setParameter(
					"resourcePrimKey", "{embedded.id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"password-policies", "permissions",
				_language.get(httpServletRequest, "permissions"), "get", null,
				"modal-permissions"));
	}

	private final Language _language;
	private final ThemeDisplay _themeDisplay;

}