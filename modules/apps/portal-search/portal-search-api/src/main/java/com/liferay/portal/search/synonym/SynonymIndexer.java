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

package com.liferay.portal.search.synonym;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adam Brandizzi
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