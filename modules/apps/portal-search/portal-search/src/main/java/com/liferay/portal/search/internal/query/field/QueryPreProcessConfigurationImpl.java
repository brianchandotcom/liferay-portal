/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query.field;

import com.liferay.portal.search.query.field.QueryPreProcessConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = QueryPreProcessConfiguration.class)
public class QueryPreProcessConfigurationImpl
	implements QueryPreProcessConfiguration {

	@Override
	public boolean isSubstringSearchAlways(String fieldName) {
		return queryPreProcessConfiguration.isSubstringSearchAlways(fieldName);
	}

	@Reference
	protected
		com.liferay.portal.kernel.search.query.QueryPreProcessConfiguration
			queryPreProcessConfiguration;

}