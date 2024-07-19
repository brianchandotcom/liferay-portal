/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter;

import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndexRequest;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetFilterWriterUtilTest extends BaseSynonymsWebTestCase {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUpdateSynonymSets() {
		SynonymSetFilterWriterUtil.updateSynonymSets(
			searchEngineAdapter, "companyIndexName",
			new String[] {"filterName"}, new String[] {"car,automobile"}, true);

		Mockito.verify(
			searchEngineAdapter, Mockito.times(3)
		).execute(
			Mockito.nullable(IndexRequest.class)
		);
	}

	@Test
	public void testUpdateSynonymSetsWithEmptySynonymSetFalseDeletion() {
		SynonymSetFilterWriterUtil.updateSynonymSets(
			searchEngineAdapter, "companyIndexName",
			new String[] {"filterName"}, new String[0], false);

		Mockito.verify(
			searchEngineAdapter, Mockito.never()
		).execute(
			Mockito.any(CloseIndexRequest.class)
		);
	}

}