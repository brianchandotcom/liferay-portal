/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.internal.configuration.definition;

import com.liferay.commerce.notification.internal.configuration.CommerceNotificationQueueEntryConfiguration;
import com.liferay.commerce.notification.internal.constants.CommerceNotificationQueueEntryConstants;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ethan Bustad
 */
@Component(service = ConfigurationPidMapping.class)
public class CommerceNotificationQueueEntryServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommerceNotificationQueueEntryConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return CommerceNotificationQueueEntryConstants.SERVICE_NAME;
	}

}