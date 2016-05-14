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

package com.liferay.portal.configuration.cluster.internal.messaging;

import com.liferay.portal.configuration.cluster.constants.ConfigurationClusterDestinationNames;
import com.liferay.portal.configuration.cluster.internal.ConfigurationThreadLocal;
import com.liferay.portal.configuration.persistence.ReloadablePersistenceManager;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {"destination.name=" + ConfigurationClusterDestinationNames.CONFIGURATION},
	service = MessageListener.class
)
public class ConfigurationMessageListener extends BaseMessageListener {

	@Reference(unbind = "-")
	public void setReloadablePersistenceManager(
		ReloadablePersistenceManager reloadablePersistenceManager) {

		_reloadablePersistenceManager = reloadablePersistenceManager;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("(");

		String pid = null;

		if (message.contains(ConfigurationAdmin.SERVICE_FACTORYPID)) {
			pid = message.getString(ConfigurationAdmin.SERVICE_FACTORYPID);

			sb.append(ConfigurationAdmin.SERVICE_FACTORYPID);
			sb.append("=");
			sb.append(pid);
		}
		else {
			pid = message.getString(Constants.SERVICE_PID);

			sb.append(Constants.SERVICE_PID);
			sb.append("=");
			sb.append(pid);
		}

		sb.append(")");

		_reloadablePersistenceManager.reload(pid);

		Dictionary<String, ?> properties = _reloadablePersistenceManager.load(
			pid);

		try {
			ConfigurationThreadLocal.setLocalUpdate(true);

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(sb.toString());

			if (configurations == null) {
				return;
			}

			int type = message.getInteger("configuration.event.type");

			for (Configuration configuration : configurations) {
				if (type == ConfigurationEvent.CM_DELETED) {
					configuration.delete();
				}
				else {
					configuration.update(properties);
				}
			}
		}
		finally {
			ConfigurationThreadLocal.setLocalUpdate(false);
		}
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(
		target = "(destination.name=" + ConfigurationClusterDestinationNames.CONFIGURATION + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	private ConfigurationAdmin _configurationAdmin;
	private ReloadablePersistenceManager _reloadablePersistenceManager;

}