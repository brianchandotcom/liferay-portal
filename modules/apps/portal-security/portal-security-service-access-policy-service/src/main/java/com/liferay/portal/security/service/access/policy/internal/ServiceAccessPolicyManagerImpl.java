/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
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
@Component(service = ServiceAccessPolicyManager.class)
public class ServiceAccessPolicyManagerImpl
	implements ServiceAccessPolicyManager {

	@Override
	public String getDefaultApplicationServiceAccessPolicyName(long companyId) {
		SAPConfiguration sapConfiguration = _getSAPConfiguration(companyId);

		if (sapConfiguration != null) {
			return sapConfiguration.systemDefaultSAPEntryName();
		}

		return null;
	}

	@Override
	public String getDefaultUserServiceAccessPolicyName(long companyId) {
		SAPConfiguration sapConfiguration = _getSAPConfiguration(companyId);

		if (sapConfiguration != null) {
			return sapConfiguration.systemUserPasswordSAPEntryName();
		}

		return null;
	}

	@Override
	public List<ServiceAccessPolicy> getServiceAccessPolicies(
		long companyId, int start, int end) {

		return _toServiceAccessPolicies(
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
			return _toServiceAccessPolicy(
				_sapEntryService.getSAPEntry(companyId, name));
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private SAPConfiguration _getSAPConfiguration(long companyId) {
		try {
			return _configurationProvider.getConfiguration(
				SAPConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, SAPConstants.SERVICE_NAME));
		}
		catch (ConfigurationException configurationException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get SAP configuration", configurationException);
			}

			return null;
		}
	}

	private List<ServiceAccessPolicy> _toServiceAccessPolicies(
		List<SAPEntry> sapEntries) {

		if (sapEntries == null) {
			return null;
		}

		List<ServiceAccessPolicy> serviceAccessPolicies = new ArrayList<>(
			sapEntries.size());

		for (SAPEntry sapEntry : sapEntries) {
			ServiceAccessPolicy serviceAccessPolicy = _toServiceAccessPolicy(
				sapEntry);

			serviceAccessPolicies.add(serviceAccessPolicy);
		}

		return serviceAccessPolicies;
	}

	private ServiceAccessPolicy _toServiceAccessPolicy(SAPEntry sapEntry) {
		if (sapEntry != null) {
			return new ServiceAccessPolicyImpl(sapEntry);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceAccessPolicyManagerImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private SAPEntryService _sapEntryService;

}