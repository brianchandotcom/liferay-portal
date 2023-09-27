/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.cluster.internal;

import com.liferay.portal.configuration.cluster.internal.constants.ConfigurationClusterDestinationNames;
import com.liferay.portal.configuration.persistence.InMemoryOnlyConfigurationThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.SynchronousConfigurationListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(enabled = false, service = SynchronousConfigurationListener.class)
public class ConfigurationSynchronousConfigurationListener
	implements SynchronousConfigurationListener {

	@Override
	public void configurationEvent(ConfigurationEvent configurationEvent) {
		if (ConfigurationThreadLocal.isLocalUpdate() ||
			InMemoryOnlyConfigurationThreadLocal.isInMemoryOnly()) {

			return;
		}

		Message message = new Message();

		message.setDestinationName(
			ConfigurationClusterDestinationNames.CONFIGURATION);

		message.put(Constants.SERVICE_PID, configurationEvent.getPid());

		message.put("configuration.event.type", configurationEvent.getType());

		_clusterLink.sendMulticastMessage(message, Priority.LEVEL10);
	}

	@Reference
	private ClusterLink _clusterLink;

	@Reference(
		target = "(destination.name=" + ConfigurationClusterDestinationNames.CONFIGURATION + ")"
	)
	private Destination _destination;

}