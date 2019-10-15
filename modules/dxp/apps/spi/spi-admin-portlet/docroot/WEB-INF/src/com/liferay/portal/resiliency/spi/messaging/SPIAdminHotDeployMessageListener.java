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

package com.liferay.portal.resiliency.spi.messaging;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.resiliency.spi.backgroundtask.StartSPIBackgroundTaskExecutor;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.monitor.SPIDefinitionMonitorUtil;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;
import com.liferay.portal.resiliency.spi.util.PortletPropsKeys;
import com.liferay.portal.resiliency.spi.util.SPIAdminConstants;
import com.liferay.util.portlet.PortletProps;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class SPIAdminHotDeployMessageListener extends HotDeployMessageListener {

	public SPIAdminHotDeployMessageListener(String... servletContextNames) {
		super(servletContextNames);
	}

	@Override
	protected void onDeploy(Message message) throws Exception {
		List<SPIDefinition> spiDefinitions =
			SPIDefinitionLocalServiceUtil.getSPIDefinitions();

		for (SPIDefinition spiDefinition : spiDefinitions) {
			SPIDefinitionLocalServiceUtil.updateSPIDefinition(
				spiDefinition.getSpiDefinitionId(),
				SPIAdminConstants.STATUS_STOPPED, null);
		}

		if (!GetterUtil.getBoolean(
				PortletProps.get(
					PortletPropsKeys.SPI_START_ON_PORTAL_STARTUP))) {

			return;
		}

		List<BackgroundTask> backgroundTasks =
			BackgroundTaskManagerUtil.getBackgroundTasks(
				StartSPIBackgroundTaskExecutor.class.getName(),
				BackgroundTaskConstants.STATUS_QUEUED);

		for (BackgroundTask backgroundTask : backgroundTasks) {
			BackgroundTaskManagerUtil.deleteBackgroundTask(
				backgroundTask.getBackgroundTaskId());
		}

		for (SPIDefinition spiDefinition : spiDefinitions) {
			long userId = UserLocalServiceUtil.getDefaultUserId(
				spiDefinition.getCompanyId());

			SPIDefinitionLocalServiceUtil.startSPIinBackground(
				userId, spiDefinition.getSpiDefinitionId());
		}
	}

	@Override
	protected void onUndeploy(Message message) throws Exception {
		SPIDefinitionMonitorUtil.unregister();

		List<SPIDefinition> spiDefinitions =
			SPIDefinitionLocalServiceUtil.getSPIDefinitions();

		for (SPIDefinition spiDefinition : spiDefinitions) {
			try {
				if (spiDefinition.isAlive()) {
					SPIDefinitionLocalServiceUtil.stopSPI(
						spiDefinition.getSpiDefinitionId());
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to stop SPI " + spiDefinition.getName());
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SPIAdminHotDeployMessageListener.class);

}