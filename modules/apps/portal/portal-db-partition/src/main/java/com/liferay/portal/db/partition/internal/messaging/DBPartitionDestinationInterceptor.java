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

package com.liferay.portal.db.partition.internal.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.db.partition.internal.configuration.DBPartitionConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationInterceptor;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Chaparro
 */
@Component(
	configurationPid = "com.liferay.portal.db.partition.internal.configuration.DBPartitionConfiguration",
	immediate = true, service = DestinationInterceptor.class
)
public class DBPartitionDestinationInterceptor
	implements DestinationInterceptor {

	@Override
	public void send(Destination destination, Message message) {
		if (!_databasePartitionEnabled) {
			destination.send(message);

			return;
		}

		String destinationName = destination.getName();
		String jobName = message.getString(SchedulerEngine.JOB_NAME);

		long companyId = message.getLong("companyId");

		long currentCompanyId = CompanyThreadLocal.getCompanyId();

		String taskName = destination.getName();

		if (Validator.isNotNull(jobName)) {
			taskName += StringPool.POUND + jobName;
		}

		if (companyId != CompanyConstants.SYSTEM) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Executing ", taskName, " for partition with company ",
						"id ", companyId));
			}

			try {
				CompanyThreadLocal.setCompanyId(companyId);

				destination.send(message);
			}
			finally {
				CompanyThreadLocal.setCompanyId(currentCompanyId);
			}

			return;
		}

		if (isExcluded(destinationName, jobName)) {
			destination.send(message);

			return;
		}

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			if (!company.isActive()) {
				continue;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Executing ", taskName, " for partition with ",
						"company id ", company.getCompanyId()));
			}

			message.put("companyId", company.getCompanyId());

			try {
				CompanyThreadLocal.setCompanyId(company.getCompanyId());

				destination.send(message);
			}
			finally {
				CompanyThreadLocal.setCompanyId(currentCompanyId);
			}
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		DBPartitionConfiguration dbPartitionConfiguration =
			ConfigurableUtil.createConfigurable(
				DBPartitionConfiguration.class, properties);

		_excludedMessageBusDestinationNames = SetUtil.fromArray(
			dbPartitionConfiguration.excludedMessageBusDestinationNames());

		_excludedSchedulerJobNames = SetUtil.fromArray(
			dbPartitionConfiguration.excludedSchedulerJobNames());

		_databasePartitionEnabled = GetterUtil.getBoolean(
			_props.get("database.partition.enabled"));
	}

	protected boolean isExcluded(
		String destinationName, String schedulerJobName) {

		if (_excludedMessageBusDestinationNames.contains(destinationName) ||
			_excludedSchedulerJobNames.contains(schedulerJobName)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DBPartitionDestinationInterceptor.class);

	private static boolean _databasePartitionEnabled;

	@Reference
	private CompanyLocalService _companyLocalService;

	private volatile Set<String> _excludedMessageBusDestinationNames;
	private volatile Set<String> _excludedSchedulerJobNames;

	@Reference
	private Props _props;

}