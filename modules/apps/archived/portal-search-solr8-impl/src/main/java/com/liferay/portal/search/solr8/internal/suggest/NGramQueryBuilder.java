/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.suggest;

import com.liferay.portal.kernel.search.SearchException;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author Michael C. Han
 */
public interface NGramQueryBuilder {

	public SolrQuery getNGramQuery(String input) throws SearchException;

}