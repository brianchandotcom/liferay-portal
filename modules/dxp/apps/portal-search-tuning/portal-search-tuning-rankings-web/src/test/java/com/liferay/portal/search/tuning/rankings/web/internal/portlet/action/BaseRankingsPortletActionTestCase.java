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

package com.liferay.portal.search.tuning.rankings.web.internal.portlet.action;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.DuplicateQueryStringsDetector;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.storage.RankingStorageAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public abstract class BaseRankingsPortletActionTestCase
	extends BaseRankingsWebTestCase {

	@SuppressWarnings("unchecked")
	protected void setUpDuplicateQueryStringsDetector() {
		DuplicateQueryStringsDetector.Criteria.Builder builder = Mockito.mock(
			DuplicateQueryStringsDetector.Criteria.Builder.class);

		Mockito.doReturn(
			builder
		).when(
			builder
		).index(
			Mockito.anyString()
		);

		Mockito.doReturn(
			builder
		).when(
			builder
		).queryStrings(
			Mockito.anyCollection()
		);

		Mockito.doReturn(
			builder
		).when(
			builder
		).rankingIndexName(
			Mockito.any()
		);

		Mockito.doReturn(
			builder
		).when(
			builder
		).unlessRankingDocumentId(
			Mockito.anyString()
		);

		Mockito.doReturn(
			Mockito.mock(DuplicateQueryStringsDetector.Criteria.class)
		).when(
			builder
		).build();

		Mockito.doReturn(
			builder
		).when(
			duplicateQueryStringsDetector
		).builder();

		Mockito.doReturn(
			Collections.emptyList()
		).when(
			duplicateQueryStringsDetector
		).detect(
			Mockito.any()
		);
	}

	protected void setUpIndexNameBuilder() {
		Mockito.doReturn(
			"indexName"
		).when(
			indexNameBuilder
		).getIndexName(
			Mockito.anyLong()
		);
	}

	@Override
	protected void setUpPortletRequestParamValue(
		PortletRequest portletRequest, String returnValue, String paramName) {

		Mockito.doReturn(
			returnValue
		).when(
			portletRequest
		).getParameter(
			Mockito.eq(paramName)
		);
	}

	protected void setUpRankingIndexReader() {
		Ranking ranking = Mockito.mock(Ranking.class);

		ReflectionTestUtil.setFieldValue(
			ranking, "_aliases", Arrays.asList("aliases"));
		ReflectionTestUtil.setFieldValue(
			ranking, "_hiddenDocumentIds",
			new LinkedHashSet<String>(Arrays.asList("hiddenDocumentIds")));
		ReflectionTestUtil.setFieldValue(
			ranking, "_pins",
			new ArrayList<Ranking.Pin>(
				Arrays.asList(new Ranking.Pin(0, "id"))));

		Mockito.doReturn(
			Arrays.asList("hiddenDocumentIds")
		).when(
			ranking
		).getHiddenDocumentIds();

		Mockito.doReturn(
			Arrays.asList("aliases")
		).when(
			ranking
		).getAliases();

		Mockito.doReturn(
			Optional.of(ranking)
		).when(
			rankingIndexReader
		).fetchOptional(
			Mockito.any(), Mockito.anyString()
		);
	}

	protected void setUpResourceResponse() {
		Mockito.doReturn(
			true
		).when(
			resourceResponse
		).isCommitted();

		Mockito.doReturn(
			Mockito.mock(PortletURL.class)
		).when(
			resourceResponse
		).createRenderURL();
	}

	protected DuplicateQueryStringsDetector duplicateQueryStringsDetector =
		Mockito.mock(DuplicateQueryStringsDetector.class);
	protected IndexNameBuilder indexNameBuilder = Mockito.mock(
		IndexNameBuilder.class);
	protected RankingIndexReader rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	protected RankingStorageAdapter rankingStorageAdapter = Mockito.mock(
		RankingStorageAdapter.class);

}