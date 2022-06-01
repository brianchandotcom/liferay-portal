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

package com.liferay.client.extension.internal.configuration;

import com.liferay.client.extension.configuration.ClientExtensionConfiguration;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.client.extension.configuration.ClientExtensionConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class ClientExtensionConfigurationFactory {

	@Activate
	public ClientExtensionConfigurationFactory(
			@Reference CompanyLocalService companyLocalService,
			@Reference ClientExtensionEntryLocalService
				clientExtensionEntryLocalService,
			@Reference UserLocalService userLocalService,
			Map<String, Object> properties)
		throws PortalException {

		_clientExtensionEntryLocalService = clientExtensionEntryLocalService;

		ClientExtensionConfiguration clientExtensionFactoryConfiguration =
			ConfigurableUtil.createConfigurable(
				ClientExtensionConfiguration.class, properties);

		String externalReferenceCode = _getExternalReferenceCode(properties);

		Company company;

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId == 0) {
			company = companyLocalService.getCompanyByWebId(
				PropsValues.COMPANY_DEFAULT_WEB_ID);
		}
		else {
			company = companyLocalService.getCompanyById(companyId);
		}

		User defaultAdminUser = userLocalService.getUserByScreenName(
			company.getCompanyId(), PropsValues.DEFAULT_ADMIN_SCREEN_NAME);

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getMostRelevantLocale(),
			clientExtensionFactoryConfiguration.name());

		ClientExtensionEntry clientExtensionEntry =
			_clientExtensionEntryLocalService.addOrUpdateClientExtensionEntry(
				externalReferenceCode, defaultAdminUser.getUserId(),
				clientExtensionFactoryConfiguration.baseURL(),
				clientExtensionFactoryConfiguration.description(), nameMap,
				StringPool.BLANK,
				clientExtensionFactoryConfiguration.sourceCodeURL(),
				clientExtensionFactoryConfiguration.type(),
				StringUtil.merge(
					clientExtensionFactoryConfiguration.typeSettings(),
					StringPool.NEW_LINE));

		if (clientExtensionEntry.isNew()) {
			_clientExtensionEntryLocalService.updateStatus(
				defaultAdminUser.getUserId(),
				clientExtensionEntry.getClientExtensionEntryId(),
				WorkflowConstants.STATUS_APPROVED);
		}

		_clientExtensionEntry = clientExtensionEntry;
	}

	@Deactivate
	protected void deactivate(Integer reason) throws PortalException {
		if (reason ==
				ComponentConstants.DEACTIVATION_REASON_CONFIGURATION_DELETED) {

			_clientExtensionEntryLocalService.deleteClientExtensionEntry(
				_clientExtensionEntry);
		}
	}

	private String _getExternalReferenceCode(Map<String, Object> properties) {
		String externalReferenceCode = GetterUtil.getString(
			properties.get(Constants.SERVICE_PID));

		int pos = externalReferenceCode.indexOf('~');

		if (pos > 0) {
			externalReferenceCode = externalReferenceCode.substring(pos + 1);
		}

		return _EXTERNAL_REFERENCE_CODE_NAMESPACE + externalReferenceCode;
	}

	private static final String _EXTERNAL_REFERENCE_CODE_NAMESPACE = "LXC:";

	private final ClientExtensionEntry _clientExtensionEntry;
	private final ClientExtensionEntryLocalService
		_clientExtensionEntryLocalService;

}