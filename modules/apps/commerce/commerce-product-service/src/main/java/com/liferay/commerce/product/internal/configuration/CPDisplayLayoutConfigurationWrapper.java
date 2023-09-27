/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.configuration;

import com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alec Sloan
 */
@Component(
	configurationPid = "com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = CPDisplayLayoutConfigurationWrapper.class
)
public class CPDisplayLayoutConfigurationWrapper {

	public String getAssetCategoryLayoutUuid() {
		return _cpDisplayLayoutConfiguration.assetCategoryLayoutUuid();
	}

	public String getProuctLayoutUuid() {
		return _cpDisplayLayoutConfiguration.productLayoutUuid();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_cpDisplayLayoutConfiguration = ConfigurableUtil.createConfigurable(
			CPDisplayLayoutConfiguration.class, properties);
	}

	private volatile CPDisplayLayoutConfiguration _cpDisplayLayoutConfiguration;

}