/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.scheduler;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	service = SchedulerJobConfiguration.class
)
public class SamlMetadataSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeConsumer<Long, Exception>
		getCompanyJobExecutorUnsafeConsumer() {

		return companyId -> _updateMetadata(companyId);
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _companyLocalService.forEachCompany(
			company -> {
				if (!company.isActive()) {
					return;
				}

				_updateMetadata(company.getCompanyId());
			});
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_samlConfiguration.getMetadataRefreshInterval(), TimeUnit.SECOND);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_samlConfiguration = ConfigurableUtil.createConfigurable(
			SamlConfiguration.class, properties);
	}

	private void _updateIdpMetadata(long companyId) {
		List<SamlSpIdpConnection> samlSpIdpConnections =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnections(companyId);

		for (SamlSpIdpConnection samlSpIdpConnection : samlSpIdpConnections) {
			if (!samlSpIdpConnection.isEnabled() ||
				Validator.isNull(samlSpIdpConnection.getMetadataUrl())) {

				continue;
			}

			try {
				_samlSpIdpConnectionLocalService.updateMetadata(
					samlSpIdpConnection.getSamlSpIdpConnectionId());
			}
			catch (Exception exception) {
				String message = StringBundler.concat(
					"Unable to refresh IdP metadata for ",
					samlSpIdpConnection.getSamlIdpEntityId(), ": ",
					exception.getMessage());

				if (_log.isDebugEnabled()) {
					_log.debug(message, exception);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(message);
				}
			}
		}
	}

	private void _updateMetadata(long companyId) {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				SamlMetadataSchedulerJobConfiguration.class.getClassLoader());

			if (!_samlProviderConfigurationHelper.isEnabled()) {
				return;
			}

			try {
				if (_samlProviderConfigurationHelper.isRoleIdp()) {
					_updateSpMetadata(companyId);
				}
				else if (_samlProviderConfigurationHelper.isRoleSp()) {
					_updateIdpMetadata(companyId);
				}
			}
			catch (Exception exception) {
				String msg = StringBundler.concat(
					"Unable to refresh metadata for company ", companyId, ": ",
					exception.getMessage());

				if (_log.isDebugEnabled()) {
					_log.debug(msg, exception);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(msg);
				}
			}
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

	private void _updateSpMetadata(long companyId) {
		List<SamlIdpSpConnection> samlIdpSpConnections =
			_samlIdpSpConnectionLocalService.getSamlIdpSpConnections(companyId);

		for (SamlIdpSpConnection samlIdpSpConnection : samlIdpSpConnections) {
			if (!samlIdpSpConnection.isEnabled() ||
				Validator.isNull(samlIdpSpConnection.getMetadataUrl())) {

				continue;
			}

			try {
				_samlIdpSpConnectionLocalService.updateMetadata(
					samlIdpSpConnection.getSamlIdpSpConnectionId());
			}
			catch (Exception exception) {
				String message = StringBundler.concat(
					"Unable to refresh SP metadata for ",
					samlIdpSpConnection.getSamlSpEntityId(), ": ",
					exception.getMessage());

				if (_log.isDebugEnabled()) {
					_log.debug(message, exception);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(message);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlMetadataSchedulerJobConfiguration.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private SamlConfiguration _samlConfiguration;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}