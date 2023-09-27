/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.configuration.definition;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.internal.configuration.NotificationQueueConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Murilo Stodolni
 */
@Component(service = ConfigurationPidMapping.class)
public class NotificationQueueConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return NotificationQueueConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return NotificationConstants.RESOURCE_NAME_NOTIFICATION_QUEUE;
	}

}