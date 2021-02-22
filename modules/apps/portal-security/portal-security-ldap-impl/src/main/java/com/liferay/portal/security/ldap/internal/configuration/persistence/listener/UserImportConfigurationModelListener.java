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

package com.liferay.portal.security.ldap.internal.configuration.persistence.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.security.ldap.internal.constants.LDAPDestinationNames;

import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shuyang Zhou
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration",
	service = {
		ConfigurationModelListener.class,
		UserImportConfigurationModelListener.class
	}
)
public class UserImportConfigurationModelListener
	extends BaseMessageListener implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		LDAPImportConfiguration ldapImportConfiguration =
			ConfigurableUtil.createConfigurable(
				LDAPImportConfiguration.class, properties);

		if (ldapImportConfiguration.companyId() == 0) {
			_updateDefaultImportInterval(
				ldapImportConfiguration.importInterval());
		}
	}

	@Activate
	@Modified
	protected void activate() {
		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(0L);

		_updateDefaultImportInterval(ldapImportConfiguration.importInterval());
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long time = _getLastImportTime();

		_companyLocalService.forEachCompanyId(
			companyId -> _importUsers(companyId, time));
	}

	@Override
	protected void doReceive(Message message, long companyId) throws Exception {
		_importUsers(companyId, _getLastImportTime());
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(
		target = "(destination.name=" + LDAPDestinationNames.SCHEDULED_USER_LDAP_IMPORT + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration)",
		unbind = "-"
	)
	protected void setLDAPImportConfigurationProvider(
		ConfigurationProvider<LDAPImportConfiguration>
			ldapImportConfigurationProvider) {

		_ldapImportConfigurationProvider = ldapImportConfigurationProvider;
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	private Date _getFutureDate(int interval) {
		Calendar calendar = Calendar.getInstance();

		calendar.setLenient(true);
		calendar.setTime(new Date());

		calendar.add(Calendar.MINUTE, interval);

		return calendar.getTime();
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

	private void _updateDefaultImportInterval(int interval) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"LDAP user imports will be attempted every " + interval +
					" minutes");
		}

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, _getFutureDate(interval), null, interval,
			TimeUnit.MINUTE);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry,
			LDAPDestinationNames.SCHEDULED_USER_LDAP_IMPORT);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserImportConfigurationModelListener.class);

	private CompanyLocalService _companyLocalService;
	private ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile LDAPUserImporter _ldapUserImporter;

	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}