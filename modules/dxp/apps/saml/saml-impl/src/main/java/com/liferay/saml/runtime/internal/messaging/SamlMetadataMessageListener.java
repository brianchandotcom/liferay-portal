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

package com.liferay.saml.runtime.internal.messaging;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
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
	protected void doReceive(Message message) throws Exception {
		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			if (!company.isActive()) {
				continue;
			}

			Long companyId = CompanyThreadLocal.getCompanyId();

			CompanyThreadLocal.setCompanyId(company.getCompanyId());

			try {
				if (!_samlProviderConfigurationHelper.isEnabled()) {
					continue;
				}

				try {
					if (_samlProviderConfigurationHelper.isRoleIdp()) {
						updateSpMetadata(company.getCompanyId());
					}
					else if (_samlProviderConfigurationHelper.isRoleSp()) {
						updateIdpMetadata(company.getCompanyId());
					}
				}
				catch (SystemException se) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to refresh metadata for company " +
								company.getCompanyId(),
							se);
					}
				}
			}
			finally {
				CompanyThreadLocal.setCompanyId(companyId);
			}
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	protected void updateIdpMetadata(long companyId) {
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
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to refresh IdP metadata for " +
							samlSpIdpConnection.getSamlIdpEntityId(),
						e);
				}
			}
		}
	}

	protected void updateSpMetadata(long companyId) {
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
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to refresh SP metadata for " +
							samlIdpSpConnection.getSamlSpEntityId(),
						e);
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