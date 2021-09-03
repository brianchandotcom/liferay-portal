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

package com.liferay.portal.security.sso.ntlm.internal.configuration.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(service = ConfigurationPidMapping.class)
public class NtlmCompanyServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return NtlmConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return NtlmConstants.SERVICE_NAME;
	}

}