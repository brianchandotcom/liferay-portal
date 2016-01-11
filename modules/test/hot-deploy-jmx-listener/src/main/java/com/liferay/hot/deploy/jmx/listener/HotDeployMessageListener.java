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

package com.liferay.hot.deploy.jmx.listener;

import com.liferay.hot.deploy.jmx.listener.statistics.PluginStatisticsManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = MessageListener.class)
public class HotDeployMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		_messageBus.registerMessageListener(DestinationNames.HOT_DEPLOY, this);
	}

	@Deactivate
	protected void deactivate() {
		_messageBus.unregisterMessageListener(
			DestinationNames.HOT_DEPLOY, this);
	}

	@Override
	protected void doReceive(Message message) {
		String command = GetterUtil.getString(message.getString("command"));
		String servletContextName = message.getString("servletContextName");

		if (command.equals("deploy")) {
			if (_pluginStatisticsManager.registerLegacyPlugin(
					servletContextName)) {

				if (_log.isInfoEnabled()) {
					_log.info("Deployed " + servletContextName);
				}
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Not deploying " + servletContextName +
						" because it is already deployed");
			}
		}
		else if (command.equals("undeploy")) {
			if (_pluginStatisticsManager.unregisterLegacyPlugin(
					servletContextName)) {

				if (_log.isInfoEnabled()) {
					_log.info("Undeployed " + servletContextName);
				}
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Not undeploying " + servletContextName +
						" because it was not deployed");
			}
		}
		else if (_log.isWarnEnabled()) {
			_log.warn("Unknown command " + command);
		}
	}

	@Reference(unbind = "-")
	protected void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	@Reference(unbind = "-")
	protected void setPluginStatisticsManager(
		PluginStatisticsManager pluginStatisticsManager) {

		_pluginStatisticsManager = pluginStatisticsManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HotDeployMessageListener.class);

	private MessageBus _messageBus;
	private PluginStatisticsManager _pluginStatisticsManager;

}