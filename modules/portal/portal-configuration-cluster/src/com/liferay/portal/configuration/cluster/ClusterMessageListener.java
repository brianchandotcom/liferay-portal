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

package com.liferay.portal.configuration.cluster;

import com.liferay.portal.configuration.persistence.ReloadablePersitenceManager;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

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
	property = {
		Details.DESTINATION_NAME + "=" + Details.CONFIGURATION_DESTINATION
	},
	service = MessageListener.class
)
public class ClusterMessageListener extends BaseMessageListener {

	@Reference
	public void setReloadablePersitenceManager(
		ReloadablePersitenceManager reloadablePersitenceManager) {

		_reloadablePersitenceManager = reloadablePersitenceManager;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		StringBuilder sb = new StringBuilder();

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

		_reloadablePersitenceManager.reload(pid);

		try {
			Details.localUpdateOnly.set(Boolean.TRUE);

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(sb.toString());

			if (configurations == null) {
				return;
			}

			int type = message.getInteger("cm.type");

			for (Configuration configuration : configurations) {
				if (type == ConfigurationEvent.CM_DELETED) {
					configuration.delete();
				}
				else {
					configuration.update();
				}
			}
		}
		finally {
			Details.localUpdateOnly.set(Boolean.FALSE);
		}
	}

	@Reference
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	private ConfigurationAdmin _configurationAdmin;
	private ReloadablePersitenceManager _reloadablePersitenceManager;

}