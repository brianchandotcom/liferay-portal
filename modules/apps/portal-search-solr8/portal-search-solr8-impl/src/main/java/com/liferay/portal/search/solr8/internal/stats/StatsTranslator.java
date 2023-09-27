/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.stats;

import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsResponse;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FieldStatsInfo;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public interface StatsTranslator {

	public void populateRequest(SolrQuery solrQuery, StatsRequest statsRequest);

	public StatsResponse translateResponse(FieldStatsInfo fieldStatsInfo);

}