/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.index;

import com.liferay.portal.search.index.IndexNameBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = IndexNameBuilder.class)
public class SolrIndexNameBuilder implements IndexNameBuilder {

	@Override
	public String getIndexName(long companyId) {
		return "liferay";
	}

}