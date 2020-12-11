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

package com.liferay.multi.factor.authentication.poc.internal.settings.definition;

import com.liferay.multi.factor.authentication.poc.internal.configuration.MFAPocConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marta Medio
 */
@Component(
	property = "mfa.visibility.configuration.pid=com.liferay.multi.factor.authentication.poc.internal.configuration.MFAPocConfiguration",
	service = ConfigurationBeanDeclaration.class
)
public class MFAPocCompanyConfigurationBeanDeclaration
	implements ConfigurationBeanDeclaration {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return MFAPocConfiguration.class;
	}

}