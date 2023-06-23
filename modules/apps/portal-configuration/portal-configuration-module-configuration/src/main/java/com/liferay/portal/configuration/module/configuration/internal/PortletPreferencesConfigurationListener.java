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

package com.liferay.portal.configuration.module.configuration.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;
import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(service = ConfigurationListener.class)
public class PortletPreferencesConfigurationListener
	implements ConfigurationListener {

	@Override
	public void configurationEvent(ConfigurationEvent configurationEvent) {
		String key = configurationEvent.getPid();

		String factoryPid = configurationEvent.getFactoryPid();

		if (factoryPid != null) {
			key = StringUtil.replaceLast(
				factoryPid, ".scoped", StringPool.BLANK);
		}

		ConfigurationOverrideInstance.clearConfigurationOverrideInstance(key);
	}

}