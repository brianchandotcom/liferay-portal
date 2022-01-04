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

package com.liferay.saml.internal.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
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
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = SamlMetadataMessageListener.class
)
public class SamlMetadataMessageListener extends SamlMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		SamlConfiguration samlConfiguration =
			ConfigurableUtil.createConfigurable(
				SamlConfiguration.class, properties);

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			samlConfiguration.getMetadataRefreshInterval(), TimeUnit.SECOND);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) {
		_companyLocalService.forEachCompany(
			company -> {
				if (!company.isActive()) {
					return;
				}

				_updateMetadata(company.getCompanyId());
			});
	}

	@Override
	protected void doReceive(Message message, long companyId) {
		_updateMetadata(companyId);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
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
		SamlMetadataMessageListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}