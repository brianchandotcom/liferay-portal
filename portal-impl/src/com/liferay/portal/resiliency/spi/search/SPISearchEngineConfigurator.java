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

package com.liferay.portal.resiliency.spi.search;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.nio.intraband.messaging.IntrabandBridgeMessageListener;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;

import java.rmi.RemoteException;

import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class SPISearchEngineConfigurator {

	public void afterPropertiesSet() throws RemoteException {
		if (!SPIUtil.isSPI()) {
			return;
		}

		Set<String> searchEngineIds =
			SearchEngineHelperUtil.getSearchEngineIds();

		for (String searchEngineId : searchEngineIds) {
			String destinationName =
				SearchEngineHelperUtil.getSearchWriterDestinationName(
					searchEngineId);

			Destination destination = MessageBusUtil.getDestination(
				destinationName);

			destination.unregisterMessageListeners();

			SPI spi = SPIUtil.getSPI();

			destination.register(
				new IntrabandBridgeMessageListener(
					spi.getRegistrationReference()));
		}
	}

}