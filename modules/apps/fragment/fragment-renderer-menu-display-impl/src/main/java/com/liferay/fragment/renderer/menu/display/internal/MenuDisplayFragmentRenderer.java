/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.renderer.menu.display.internal;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuTag;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = FragmentRenderer.class)
public class MenuDisplayFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "menu-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "source"
						).put(
							"name", "source"
						).put(
							"type", "navigationMenuSelector"
						),
						JSONUtil.put(
							"defaultValue", "horizontal"
						).put(
							"label", "display-style"
						).put(
							"name", "displayStyle"
						).put(
							"type", "select"
						).put(
							"typeOptions",
							JSONUtil.put(
								"validValues",
								JSONUtil.putAll(
									JSONUtil.put(
										"label",
										LanguageUtil.get(
											resourceBundle, "horizontal")
									).put(
										"value", "horizontal"
									),
									JSONUtil.put(
										"label",
										LanguageUtil.get(
											resourceBundle, "stacked")
									).put(
										"value", "stacked"
									)))
						))))
		).toString();
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return LanguageUtil.get(resourceBundle, "menu-display");
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		return true;
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			NavigationMenuTag navigationMenuTag = new NavigationMenuTag();

			if (Objects.equals(
					_getDisplayStyle(fragmentRendererContext), "stacked")) {

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				Group companyGroup = _groupLocalService.getCompanyGroup(
					themeDisplay.getCompanyId());

				DDMTemplate ddmTemplate = _ddmTemplateService.fetchTemplate(
					companyGroup.getGroupId(),
					_portal.getClassNameId(NavItem.class), "LIST-MENU-FTL");

				navigationMenuTag.setDdmTemplateGroupId(
					ddmTemplate.getGroupId());
				navigationMenuTag.setDdmTemplateKey(
					ddmTemplate.getTemplateKey());
			}

			navigationMenuTag.doTag(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.renderer.menu.display.impl)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private String _getDisplayStyle(
		FragmentRendererContext fragmentRendererContext) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		return (String)_fragmentEntryConfigurationParser.getFieldValue(
			getConfiguration(fragmentRendererContext),
			fragmentEntryLink.getEditableValues(), "displayStyle");
	}

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	private ServletContext _servletContext;

}