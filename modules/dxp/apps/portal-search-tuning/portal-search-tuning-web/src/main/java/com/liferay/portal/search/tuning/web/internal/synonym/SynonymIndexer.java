/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.web.internal.synonym;

/**
 * @author Adam Brandizzi
 */
public interface SynonymIndexer {

	public String[] getSynonymSets(long companyId, String filterName)
		throws SynonymException;

	public String[] getSynonymSets(String indexName, String filterName)
		throws SynonymException;

	public void updateSynonymSets(
			long companyId, String filterName, String[] synonymSets)
		throws SynonymException;

	public void updateSynonymSets(
			String indexName, String filterName, String[] synonymSets)
		throws SynonymException;

}