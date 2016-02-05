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

package com.liferay.portal.search.elasticsearch.internal.index;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.index.IndexNameBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, service = IndexNameBuilder.class
)
public class CompanyIdIndexNameBuilder implements IndexNameBuilder {

	@Override
	public String getIndexName(long companyId) {
		return _elasticsearchConfiguration.indexNamePrefix() + companyId;
	}

	@Override
	public String getIndexName(SearchContext searchContext) {
		return getIndexName(searchContext.getCompanyId());
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	private ElasticsearchConfiguration _elasticsearchConfiguration;

}