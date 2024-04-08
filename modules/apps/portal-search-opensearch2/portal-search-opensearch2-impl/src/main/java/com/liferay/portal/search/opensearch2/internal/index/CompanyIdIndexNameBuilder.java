/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationObserver;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = IndexNameBuilder.class)
public class CompanyIdIndexNameBuilder
	implements IndexNameBuilder, OpenSearchConfigurationObserver {

	@Override
	public int compareTo(
		OpenSearchConfigurationObserver openSearchConfigurationObserver) {

		return openSearchConfigurationWrapper.compare(
			this, openSearchConfigurationObserver);
	}

	@Override
	public String getIndexName(long companyId) {
		return _indexNamePrefix + companyId;
	}

	@Override
	public String getIndexNamePrefix() {
		return _indexNamePrefix;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void onOpenSearchConfigurationUpdate() {
		setIndexNamePrefix(openSearchConfigurationWrapper.indexNamePrefix());
	}

	@Activate
	protected void activate() {
		openSearchConfigurationWrapper.register(this);

		setIndexNamePrefix(openSearchConfigurationWrapper.indexNamePrefix());
	}

	@Deactivate
	protected void deactivate() {
		openSearchConfigurationWrapper.unregister(this);
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
	protected OpenSearchConfigurationWrapper openSearchConfigurationWrapper;

	private String _indexNamePrefix;

}