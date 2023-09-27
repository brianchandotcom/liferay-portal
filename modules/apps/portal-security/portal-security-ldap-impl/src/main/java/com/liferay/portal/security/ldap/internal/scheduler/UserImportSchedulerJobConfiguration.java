/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.scheduler;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.security.ldap.internal.constants.LDAPDestinationNames;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shuyang Zhou
 */
@Component(
	factory = "com.liferay.portal.security.ldap.internal.scheduler.UserImportSchedulerJobConfiguration",
	service = SchedulerJobConfiguration.class
)
public class UserImportSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeConsumer<Long, Exception>
		getCompanyJobExecutorUnsafeConsumer() {

		return companyId -> _importUsers(companyId, _getLastImportTime());
	}

	@Override
	public String getDestinationName() {
		return LDAPDestinationNames.SCHEDULED_USER_LDAP_IMPORT;
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			long time = _getLastImportTime();

			_companyLocalService.forEachCompanyId(
				companyId -> _importUsers(companyId, time));
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_ldapImportConfiguration.importInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_ldapImportConfiguration = (LDAPImportConfiguration)properties.get(
			"configuration");

		if (_log.isDebugEnabled()) {
			_log.debug(
				"LDAP user imports will be attempted every " +
					_ldapImportConfiguration.importInterval() + " minutes");
		}
	}

	private long _getLastImportTime() throws Exception {
		long time =
			System.currentTimeMillis() - _ldapUserImporter.getLastImportTime();

		return Math.round(time / 60000.0);
	}

	private void _importUsers(long companyId, long time) throws Exception {
		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		if (!ldapImportConfiguration.importEnabled()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping LDAP user import for company " + companyId +
						" because LDAP import is disabled");
			}

			return;
		}

		if (ldapImportConfiguration.importInterval() <= 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping LDAP user import for company " + companyId +
						" because LDAP import interval is less than 1");
			}

			return;
		}

		if (time < ldapImportConfiguration.importInterval()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Skipping LDAP user import for company ", companyId,
						" because LDAP import interval has not been ",
						"reached"));
			}

			return;
		}

		_ldapUserImporter.importUsers(companyId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserImportSchedulerJobConfiguration.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private LDAPImportConfiguration _ldapImportConfiguration;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration)"
	)
	private ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile LDAPUserImporter _ldapUserImporter;

}