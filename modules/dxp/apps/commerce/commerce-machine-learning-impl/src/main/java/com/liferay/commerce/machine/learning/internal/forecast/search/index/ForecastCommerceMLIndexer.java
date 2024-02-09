/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.forecast.search.index;

import com.liferay.commerce.machine.learning.internal.search.api.CommerceMLIndexer;
import com.liferay.commerce.machine.learning.internal.search.api.IndexNamePatterns;
import com.liferay.commerce.machine.learning.internal.search.index.BaseCommerceMLIndexer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(service = CommerceMLIndexer.class)
public class ForecastCommerceMLIndexer extends BaseCommerceMLIndexer {

	public ForecastCommerceMLIndexer() {
		super(_INDEX_MAPPING_FILE_NAME, IndexNamePatterns.FORECAST);
	}

	private static final String _INDEX_MAPPING_FILE_NAME =
		"commerce-ml-forecast-mappings.json";

}