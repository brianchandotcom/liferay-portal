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

package com.liferay.portal.security.service.access.policy.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicy;
import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicyManager;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.security.service.access.policy.configuration.SAPConfiguration;
import com.liferay.portal.security.service.access.policy.constants.SAPConstants;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = ServiceAccessPolicyManager.class)
public class ServiceAccessPolicyManagerImpl
	implements ServiceAccessPolicyManager {

	@Override
	public String getDefaultApplicationServiceAccessPolicyName(long companyId) {
		SAPConfiguration sapConfiguration = getSAPConfiguration(companyId);

		if (sapConfiguration != null) {
			return sapConfiguration.systemDefaultSAPEntryName();
		}

		return null;
	}

	@Override
	public String getDefaultUserServiceAccessPolicyName(long companyId) {
		SAPConfiguration sapConfiguration = getSAPConfiguration(companyId);

		if (sapConfiguration != null) {
			return sapConfiguration.systemUserPasswordSAPEntryName();
		}

		return null;
	}

	@Override
	public List<ServiceAccessPolicy> getServiceAccessPolicies(
		long companyId, int start, int end) {

		return toServiceAccessPolicies(
			_sapEntryService.getCompanySAPEntries(companyId, start, end));
	}

	@Override
	public int getServiceAccessPoliciesCount(long companyId) {
		return _sapEntryService.getCompanySAPEntriesCount(companyId);
	}

	@Override
	public ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, String name) {

		try {
			return toServiceAccessPolicy(
				_sapEntryService.getSAPEntry(companyId, name));
		}
		catch (PortalException pe) {
			return null;
		}
	}

	protected SAPConfiguration getSAPConfiguration(long companyId) {
		try {
			return _configurationProvider.getConfiguration(
				SAPConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, SAPConstants.SERVICE_NAME));
		}
		catch (ConfigurationException ce) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get SAP configuration", ce);
			}

			return null;
		}
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setSAPEntryService(SAPEntryService sapEntryService) {
		_sapEntryService = sapEntryService;
	}

	protected List<ServiceAccessPolicy> toServiceAccessPolicies(
		List<SAPEntry> sapEntries) {

		if (sapEntries == null) {
			return null;
		}

		List<ServiceAccessPolicy> serviceAccessPolicies = new ArrayList<>(
			sapEntries.size());

		for (SAPEntry sapEntry : sapEntries) {
			ServiceAccessPolicy serviceAccessPolicy = toServiceAccessPolicy(
				sapEntry);

			serviceAccessPolicies.add(serviceAccessPolicy);
		}

		return serviceAccessPolicies;
	}

	protected ServiceAccessPolicy toServiceAccessPolicy(SAPEntry sapEntry) {
		if (sapEntry != null) {
			return new ServiceAccessPolicyImpl(sapEntry);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceAccessPolicyManagerImpl.class);

	private ConfigurationProvider _configurationProvider;
	private SAPEntryService _sapEntryService;

}