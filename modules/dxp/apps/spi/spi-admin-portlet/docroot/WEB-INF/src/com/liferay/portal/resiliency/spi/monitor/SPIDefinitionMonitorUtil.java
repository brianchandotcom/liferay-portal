/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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