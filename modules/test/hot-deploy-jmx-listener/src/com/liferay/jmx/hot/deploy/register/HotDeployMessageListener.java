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

package com.liferay.jmx.hot.deploy.register;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true, service = MessageListener.class
)
public class HotDeployMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		MessageBusUtil.getMessageBus().registerMessageListener(
			DestinationNames.HOT_DEPLOY, this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String command = message.getString("command");
		String servletContextName = message.getString("servletContextName");

		if (command.equals("deploy")) {
			if (_log.isInfoEnabled()) {
				_log.info(servletContextName + " was deployed");
			}

			_pluginStatistics.addDeployedLegacyPlugins(servletContextName);
		}
		else if (command.equals("undeploy")) {
			if (_log.isInfoEnabled()) {
				_log.info(servletContextName + " was undeployed");
			}

			_pluginStatistics.removeDeployedLegacyPlugins(servletContextName);
		}
	}

	@Reference
	protected void setPluginStatistics(PluginStatistics pluginStatistics) {
		_pluginStatistics = pluginStatistics;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HotDeployMessageListener.class);

	private PluginStatistics _pluginStatistics;

}