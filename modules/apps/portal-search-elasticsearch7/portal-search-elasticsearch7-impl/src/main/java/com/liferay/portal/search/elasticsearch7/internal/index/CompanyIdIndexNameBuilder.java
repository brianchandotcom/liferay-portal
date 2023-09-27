/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = IndexNameBuilder.class)
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