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

package com.liferay.portal.resiliency.spi.monitor.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;
import com.liferay.portal.resiliency.spi.util.SPIAdminConstants;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public class SPIRestartMessageListener extends BaseSPIStatusMessageListener {

	public SPIRestartMessageListener() {
		setInterestedStatus(SPIAdminConstants.STATUS_STOPPED);
	}

	@Override
	protected void processSPIStatus(
			PortletPreferences portletPreferences, SPIDefinition spiDefinition,
			int status)
		throws Exception {

		if ((spiDefinition.getStatus() == SPIAdminConstants.STATUS_STOPPED) &&
			(status == SPIAdminConstants.STATUS_STOPPED)) {

			return;
		}

		if ((spiDefinition.getStatus() == SPIAdminConstants.STATUS_STARTING) ||
			(spiDefinition.getStatus() == SPIAdminConstants.STATUS_STOPPING)) {

			return;
		}

		int maxRestartAttempts = spiDefinition.getMaxRestartAttempts();

		if (maxRestartAttempts < 0) {
			maxRestartAttempts = GetterUtil.getInteger(
				portletPreferences.getValue("maxRestartAttempts", null));
		}

		int restartAttempts = spiDefinition.getRestartAttempts();

		if (maxRestartAttempts <= restartAttempts++) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Restart attempt " + restartAttempts +
						" is ignored because it exceeds the limit of " +
							maxRestartAttempts + " restart attempts");
			}

			SPIDefinitionLocalServiceUtil.updateSPIDefinition(
				spiDefinition.getSpiDefinitionId(),
				SPIAdminConstants.STATUS_STOPPED,
				"unable-to-automatically-restart-the-spi-the-total-number-of-" +
					"automatic-restart-attempts-have-exceeded-the-defined-" +
						"maximum");

			return;
		}

		spiDefinition.setRestartAttempts(restartAttempts);

		SPIDefinitionLocalServiceUtil.updateTypeSettings(
			spiDefinition.getUserId(), spiDefinition.getSpiDefinitionId(),
			spiDefinition.getTypeSettings(), new ServiceContext());

		long userId = UserLocalServiceUtil.getDefaultUserId(
			spiDefinition.getCompanyId());

		SPIDefinitionLocalServiceUtil.startSPIinBackground(
			userId, spiDefinition.getSpiDefinitionId(), true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SPIRestartMessageListener.class);

}