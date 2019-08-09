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

package com.liferay.layout.content.page.editor.web.internal.configuration.util;

import com.liferay.layout.content.page.editor.web.internal.configuration.ContentPageEditorConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.layout.content.page.editor.web.internal.configuration.ContentPageEditorConfiguration",
	immediate = true, service = ContentPageEditorConfigurationUtil.class
)
public class ContentPageEditorConfigurationUtil {

	public static boolean commentsEnabled() {
		if (_contentPageEditorConfiguration != null) {
			return _contentPageEditorConfiguration.commentsEnabled();
		}

		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_contentPageEditorConfiguration = ConfigurableUtil.createConfigurable(
			ContentPageEditorConfiguration.class, properties);
	}

	private static ContentPageEditorConfiguration
		_contentPageEditorConfiguration;

}