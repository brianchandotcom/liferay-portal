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

package com.liferay.commerce.product.internal.configuration;

import com.liferay.commerce.product.configuration.CPDefinitionLinkTypeSettings;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	service = CPDefinitionLinkTypeSettings.class
)
public class CPDefinitionLinkTypeSettingsImpl
	implements CPDefinitionLinkTypeSettings {

	@Override
	public String[] getTypes() {
		return ArrayUtil.toStringArray(
			_cpDefinitionLinkTypeConfigurationWrappers.keySet());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addCPDefinitionLinkTypeConfigurationWrapper(
		CPDefinitionLinkTypeConfigurationWrapper
			cpDefinitionLinkTypeConfigurationWrapper) {

		_cpDefinitionLinkTypeConfigurationWrappers.put(
			cpDefinitionLinkTypeConfigurationWrapper.getType(),
			cpDefinitionLinkTypeConfigurationWrapper);
	}

	protected void removeCPDefinitionLinkTypeConfigurationWrapper(
		CPDefinitionLinkTypeConfigurationWrapper
			cpDefinitionLinkTypeConfigurationWrapper) {

		_cpDefinitionLinkTypeConfigurationWrappers.remove(
			cpDefinitionLinkTypeConfigurationWrapper.getType());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionLinkTypeSettingsImpl.class);

	private final Map<String, CPDefinitionLinkTypeConfigurationWrapper>
		_cpDefinitionLinkTypeConfigurationWrappers = new ConcurrentHashMap<>();

}