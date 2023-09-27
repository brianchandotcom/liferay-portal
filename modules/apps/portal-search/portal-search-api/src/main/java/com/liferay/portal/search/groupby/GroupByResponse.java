/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.groupby;

import com.liferay.portal.kernel.search.Hits;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 * @author Michael C. Han
 */
@ProviderType
public interface GroupByResponse {

	public String getField();

	public Hits getHits(String term);

	public Map<String, Hits> getHitsMap();

	public void putHits(String term, Hits hits);

	public void setField(String field);

}