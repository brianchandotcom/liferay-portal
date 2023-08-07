/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.portlet.data.sample.processor;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.monitoring.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.monitoring.internal.statistics.portlet.CompanyStatistics;
import com.liferay.portal.monitoring.internal.statistics.portlet.PortletRequestDataSample;
import com.liferay.portal.monitoring.internal.statistics.portlet.ServerStatisticsHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renan Vasconcelos
 */
@Component(
	enabled = false, property = "namespace=com.liferay.monitoring.Portlet",
	service = DataSampleProcessor.class
)
public class PortletRequestDataSampleProcessor
	implements DataSampleProcessor<PortletRequestDataSample> {

	@Override
	public void processDataSample(
			PortletRequestDataSample portletRequestDataSample)
		throws MonitoringException {

		long companyId = portletRequestDataSample.getCompanyId();

		CompanyStatistics companyStatistics =
			_serverStatisticsHelper.getCompanyStatisticsByCompanyId(companyId);

		if (companyStatistics == null) {
			try {
				Company company = _companyLocalService.getCompany(companyId);

				companyStatistics = _serverStatisticsHelper.register(
					company.getWebId());
			}
			catch (Exception exception) {
				throw new IllegalStateException(
					"Unable to get company with company ID " + companyId,
					exception);
			}
		}

		companyStatistics.processDataSample(portletRequestDataSample);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ServerStatisticsHelper _serverStatisticsHelper;

}