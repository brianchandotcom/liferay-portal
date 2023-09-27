/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.configuration;

import com.liferay.portal.search.elasticsearch7.configuration.OperationMode;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = OperationModeResolver.class)
public class OperationModeResolver {

	public boolean isDevelopmentModeEnabled() {
		return !isProductionModeEnabled();
	}

	public boolean isProductionModeEnabled() {
		if (elasticsearchConfigurationWrapper.productionModeEnabled()) {
			return true;
		}

		OperationMode operationMode =
			elasticsearchConfigurationWrapper.operationMode();

		if (operationMode == OperationMode.REMOTE) {
			return true;
		}

		return false;
	}

	@Reference
	protected volatile ElasticsearchConfigurationWrapper
		elasticsearchConfigurationWrapper;

}