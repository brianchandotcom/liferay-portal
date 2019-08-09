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
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ContentPageEditorConfigurationUtil.class)
public class ContentPageEditorConfigurationUtil {

	public static boolean isCommentsEnabled(long companyId)
		throws ConfigurationException {

		if (_configurationProvider != null) {
			ContentPageEditorConfiguration companyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					ContentPageEditorConfiguration.class, companyId);

			return companyConfiguration.commentsEnabled();
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private static ConfigurationProvider _configurationProvider;

}