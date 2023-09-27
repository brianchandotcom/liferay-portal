/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.synonym;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author     Adam Brandizzi
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
@ProviderType
public interface SynonymIndexer {

	public String[] getSynonymSets(long companyId, String filterName);

	public String[] getSynonymSets(String indexName, String filterName);

	public void updateSynonymSets(
			long companyId, String filterName, String[] synonymSets)
		throws SynonymException;

	public void updateSynonymSets(
			String indexName, String filterName, String[] synonymSets)
		throws SynonymException;

}