/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.web.internal.configuration.definition;

import com.liferay.comment.configuration.CommentGroupServiceConfiguration;
import com.liferay.comment.constants.CommentConstants;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 * @author István András Dézsi
 */
@Component(service = ConfigurationPidMapping.class)
public class CommentGroupServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommentGroupServiceConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return CommentConstants.SERVICE_NAME;
	}

}