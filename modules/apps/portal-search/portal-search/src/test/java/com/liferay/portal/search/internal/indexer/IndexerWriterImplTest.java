/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.batch.BatchIndexingHelper;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.search.index.UpdateDocumentIndexWriter;
import com.liferay.portal.search.indexer.BaseModelRetriever;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.permission.SearchPermissionIndexWriter;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Selena Aungst
 */
public class IndexerWriterImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testReindexCompanyThrowsWhenReindexFails() throws Exception {
		ModelIndexerWriterContributor<?> modelIndexerWriterContributor =
			Mockito.mock(ModelIndexerWriterContributor.class);

		Mockito.when(
			modelIndexerWriterContributor.shouldRun(Mockito.anyLong())
		).thenReturn(
			true
		);

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			Mockito.mock(IndexableActionableDynamicQuery.class);

		Mockito.when(
			modelIndexerWriterContributor.getIndexableActionableDynamicQuery()
		).thenReturn(
			indexableActionableDynamicQuery
		);

		RuntimeException runtimeException1 = new RuntimeException(
			RandomTestUtil.randomString());

		Mockito.doThrow(
			runtimeException1
		).when(
			indexableActionableDynamicQuery
		).performActions();

		CTCollectionLocalService ctCollectionLocalService = Mockito.mock(
			CTCollectionLocalService.class);

		Mockito.when(
			ctCollectionLocalService.getCTCollections(
				Mockito.anyLong(), Mockito.any(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.any())
		).thenReturn(
			Collections.emptyList()
		);

		IndexerWriterImpl<?> indexerWriterImpl = new IndexerWriterImpl(
			Mockito.mock(ModelSearchSettings.class),
			Mockito.mock(BaseModelRetriever.class),
			Mockito.mock(BatchIndexingHelper.class), ctCollectionLocalService,
			modelIndexerWriterContributor,
			Mockito.mock(IndexerDocumentBuilder.class),
			Mockito.mock(SearchPermissionIndexWriter.class),
			Mockito.mock(UpdateDocumentIndexWriter.class),
			Mockito.mock(IndexStatusManager.class),
			Mockito.mock(IndexWriterHelper.class),
			Mockito.mock(UIDFactory.class)) {

			@Override
			public boolean isEnabled() {
				return true;
			}

		};

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				IndexerWriterImpl.class.getName(), LoggerTestUtil.ERROR)) {

			try {
				indexerWriterImpl.reindexCompany(RandomTestUtil.randomLong());

				Assert.fail();
			}
			catch (RuntimeException runtimeException2) {
				Assert.assertSame(runtimeException1, runtimeException2);
			}

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());
		}
	}

}