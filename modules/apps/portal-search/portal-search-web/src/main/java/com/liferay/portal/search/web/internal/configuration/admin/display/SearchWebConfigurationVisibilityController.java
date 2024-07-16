/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Olivia Yu
 */
@Component(service = ConfigurationVisibilityController.class)
public class SearchWebConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public String getKey() {
		return "search-web";
	}

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (FeatureFlagManagerUtil.isEnabled("LPD-13778")) {
			return true;
		}

		return false;
	}

}