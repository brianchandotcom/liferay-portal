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

package com.liferay.portal.resiliency.spi.monitor;

import com.liferay.portal.resiliency.spi.model.SPIDefinition;

/**
 * @author Michael C. Han
 */
public class SPIDefinitionMonitorUtil {

	public static SPIDefinitionMonitor getSPIDefinitionMonitor() {
		return _spiDefinitionMonitor;
	}

	public static void register(SPIDefinition spiDefinition) {
		getSPIDefinitionMonitor().register(spiDefinition);
	}

	public static void unregister() {
		getSPIDefinitionMonitor().unregister();
	}

	public static void unregister(long spiDefinitionId) {
		getSPIDefinitionMonitor().unregister(spiDefinitionId);
	}

	public void setSPIDefinitionMonitor(
		SPIDefinitionMonitor spiDefinitionMonitor) {

		_spiDefinitionMonitor = spiDefinitionMonitor;
	}

	private static SPIDefinitionMonitor _spiDefinitionMonitor;

}