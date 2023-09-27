/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.bucket;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.query.Query;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface FiltersAggregation extends Aggregation {

	public void addKeyedQuery(String key, Query query);

	public List<KeyedQuery> getKeyedQueries();

	public Boolean getOtherBucket();

	public String getOtherBucketKey();

	public void setOtherBucket(Boolean otherBucket);

	public void setOtherBucketKey(String otherBucketKey);

	public interface KeyedQuery {

		public String getKey();

		public Query getQuery();

	}

}