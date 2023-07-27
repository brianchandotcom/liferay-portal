/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.service.data.sample.processor;

import com.liferay.portal.kernel.monitoring.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.monitoring.internal.statistics.service.ServerStatistics;
import com.liferay.portal.monitoring.internal.statistics.service.ServiceRequestDataSample;
import com.liferay.portal.monitoring.internal.statistics.service.ServiceStatistics;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renan Vasconcelos
 */
@Component(
	enabled = false, property = "namespace=com.liferay.monitoring.Service",
	service = DataSampleProcessor.class
)
public class ServiceRequestDataSampleProcessor
	implements DataSampleProcessor<ServiceRequestDataSample> {

	@Override
	public void processDataSample(
		ServiceRequestDataSample serviceRequestDataSample) {

		MethodSignature methodSignature =
			serviceRequestDataSample.getMethodSignature();

		String className = methodSignature.getClassName();

		ServiceStatistics serviceStatistics =
			_serverStatistics.getServiceStatistics(className);

		if (serviceStatistics == null) {
			serviceStatistics = new ServiceStatistics(className);

			_serverStatistics.setServiceStatistics(
				className, serviceStatistics);
		}

		serviceStatistics.processDataSample(serviceRequestDataSample);
	}

	@Reference
	private ServerStatistics _serverStatistics;

}