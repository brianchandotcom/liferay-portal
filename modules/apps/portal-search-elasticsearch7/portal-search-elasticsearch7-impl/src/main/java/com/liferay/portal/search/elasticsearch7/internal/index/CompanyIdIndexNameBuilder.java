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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserver;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.index.IndexNameBuilder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = IndexNameBuilder.class)
public class CompanyIdIndexNameBuilder
	implements ElasticsearchConfigurationObserver, IndexNameBuilder {

	@Override
	public int compareTo(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		return elasticsearchConfigurationWrapper.compare(
			this, elasticsearchConfigurationObserver);
	}

	@Override
	public String getIndexName(long companyId) {
		return _indexNamePrefix + companyId;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void onElasticsearchConfigurationUpdate() {
		setIndexNamePrefix(elasticsearchConfigurationWrapper.indexNamePrefix());
	}

	@Activate
	protected void activate() {
		elasticsearchConfigurationWrapper.register(this);

		setIndexNamePrefix(elasticsearchConfigurationWrapper.indexNamePrefix());
	}

	@Deactivate
	protected void deactivate() {
		elasticsearchConfigurationWrapper.unregister(this);
	}

	protected void setIndexNamePrefix(String indexNamePrefix) {
		if (indexNamePrefix == null) {
			_indexNamePrefix = StringPool.BLANK;
		}
		else {
			_indexNamePrefix = StringUtil.toLowerCase(
				StringUtil.trim(indexNamePrefix));
		}
	}

	@Reference
	protected volatile ElasticsearchConfigurationWrapper
		elasticsearchConfigurationWrapper;

	private volatile String _indexNamePrefix;

}