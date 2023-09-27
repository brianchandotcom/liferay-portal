/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.hits;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.highlight.HighlightField;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
@ProviderType
public interface SearchHit {

	public Document getDocument();

	public String getExplanation();

	public Map<String, HighlightField> getHighlightFieldsMap();

	public String getId();

	public String[] getMatchedQueries();

	public float getScore();

	public Object[] getSortValues();

	public Map<String, Object> getSourcesMap();

	public long getVersion();

}