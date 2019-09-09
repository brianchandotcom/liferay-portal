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

package com.liferay.layout.content.page.editor.web.internal.sidebar.panel;

import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.configuration.ContentsContentPageEditorSidebarPanelConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.layout.content.page.editor.web.internal.configuration.ContentsContentPageEditorSidebarPanelConfiguration",
	immediate = true, property = "service.ranking:Integer=400",
	service = ContentPageEditorSidebarPanel.class
)
public class ContentsContentPageEditorSidebarPanel
	implements ContentPageEditorSidebarPanel {

	@Override
	public String getIcon() {
		return "list-ul";
	}

	@Override
	public String getId() {
		return "contents";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "contents");
	}

	@Override
	public boolean includeSeparator() {
		return true;
	}

	@Override
	public boolean isVisible(boolean pageIsDisplayPage) {
		if (!_contentsContentPageEditorSidebarPanelConfiguration.enabled()) {
			return false;
		}

		return !pageIsDisplayPage;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_contentsContentPageEditorSidebarPanelConfiguration =
			ConfigurableUtil.createConfigurable(
				ContentsContentPageEditorSidebarPanelConfiguration.class,
				properties);
	}

	private volatile ContentsContentPageEditorSidebarPanelConfiguration
		_contentsContentPageEditorSidebarPanelConfiguration;

}