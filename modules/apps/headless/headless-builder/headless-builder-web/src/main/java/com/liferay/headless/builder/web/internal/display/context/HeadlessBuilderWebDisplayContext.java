/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.web.internal.display.context;

import com.liferay.headless.builder.web.internal.display.context.helper.HeadlessBuilderWebRequestHelper;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;

import javax.portlet.PortletException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Montenegro
 */
public class HeadlessBuilderWebDisplayContext {

	public HeadlessBuilderWebDisplayContext(
		EditorConfigurationFactory editorConfigurationFactory,
		HttpServletRequest httpServletRequest) {

		_editorConfigurationFactory = editorConfigurationFactory;

		_headlessBuilderWebRequestHelper = new HeadlessBuilderWebRequestHelper(
			httpServletRequest);
	}

	public HashMap<String, String> getAPIURLPaths() {
		return HashMapBuilder.put(
			"applications", "/o/headless-builder/applications/"
		).put(
			"endpoints", "/o/headless-builder/endpoints/"
		).put(
			"filters", "/o/headless-builder/filters/"
		).put(
			"properties", "/o/headless-builder/properties/"
		).put(
			"schemas", "/o/headless-builder/schemas/"
		).put(
			"sorts", "/o/headless-builder/sorts/"
		).build();
	}

	public String getEditorURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(
				PortletURLUtil.getCurrent(
					_headlessBuilderWebRequestHelper.getLiferayPortletRequest(),
					_headlessBuilderWebRequestHelper.
						getLiferayPortletResponse()),
				_headlessBuilderWebRequestHelper.getLiferayPortletResponse())
		).setMVCRenderCommandName(
			"/headless_builder/edit_api_application"
		).setParameter(
			"apiApplicationId", ""
		).setParameter(
			"editAPIApplicationNav", "details"
		).buildString();
	}

	public String getPortletId() {
		return _headlessBuilderWebRequestHelper.getPortletId();
	}

	private final EditorConfigurationFactory _editorConfigurationFactory;
	private final HeadlessBuilderWebRequestHelper
		_headlessBuilderWebRequestHelper;

}