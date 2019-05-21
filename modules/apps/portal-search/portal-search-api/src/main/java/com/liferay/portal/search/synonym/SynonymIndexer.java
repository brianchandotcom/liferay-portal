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

import aQute.bnd.annotation.ProviderType;

/**
 * @author Adam Brandizzi
 */
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