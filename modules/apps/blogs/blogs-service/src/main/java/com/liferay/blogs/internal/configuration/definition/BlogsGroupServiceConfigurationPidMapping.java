/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.configuration.definition;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.settings.BlogsGroupServiceSettings;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "service.ranking:Integer=-1",
	service = ConfigurationPidMapping.class
)
public class BlogsGroupServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return BlogsGroupServiceSettings.class;
	}

	@Override
	public String getConfigurationPid() {
		return BlogsConstants.SERVICE_NAME;
	}

}