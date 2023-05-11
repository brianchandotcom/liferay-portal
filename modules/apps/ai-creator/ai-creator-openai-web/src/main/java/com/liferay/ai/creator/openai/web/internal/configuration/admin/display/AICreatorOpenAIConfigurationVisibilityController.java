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

package com.liferay.ai.creator.openai.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"configuration.pid=com.liferay.ai.creator.openai.configuration.AICreatorOpenAICompanyConfiguration",
		"configuration.pid=com.liferay.ai.creator.openai.configuration.AICreatorOpenAIGroupConfiguration"
	},
	service = ConfigurationVisibilityController.class
)
public class AICreatorOpenAIConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (FeatureFlagManagerUtil.isEnabled("LPS-179483")) {
			return true;
		}

		return false;
	}

}