/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging.config;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface MessagingConfigurator {

	public void destroy();

	public void setDestinationConfigurations(
		Set<DestinationConfiguration> destinationConfigurations);

	public void setDestinations(List<Destination> destinations);

	public void setMessageListeners(
		Map<String, List<MessageListener>> messageListeners);

}