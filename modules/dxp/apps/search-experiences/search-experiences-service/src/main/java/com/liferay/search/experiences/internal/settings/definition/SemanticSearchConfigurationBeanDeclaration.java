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

package com.liferay.search.experiences.internal.settings.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = ConfigurationBeanDeclaration.class)
public class SemanticSearchConfigurationBeanDeclaration
	implements ConfigurationBeanDeclaration {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return SemanticSearchConfiguration.class;
	}

}