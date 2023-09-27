/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author André de Oliveira
 */
public class IndexableActionableDynamicQueryTest {

	@Before
	public void setUp() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			PortalExecutorManager.class,
			Mockito.mock(PortalExecutorManager.class), null);

		indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setIndexWriterHelper(indexWriterHelper);

		IndexerRegistry indexerRegistry = Mockito.mock(IndexerRegistry.class);

		Mockito.when(
			indexerRegistry.getIndexer((String)null)
		).thenReturn(
			Mockito.mock(Indexer.class)
		);

		ReflectionTestUtil.setFieldValue(
			IndexerRegistryUtil.class, "_indexerRegistry", indexerRegistry);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testAddDocuments() throws Exception {
		indexableActionableDynamicQuery.setInterval(1);

		indexableActionableDynamicQuery.addDocuments(document1, document2);

		verifyDocumentsUpdated(document1, document2);
	}

	@Test
	public void testAddDocumentsWithinInterval() throws Exception {
		indexableActionableDynamicQuery.setInterval(3);

		indexableActionableDynamicQuery.addDocuments(document1, document2);

		verifyNoDocumentsUpdated();

		indexableActionableDynamicQuery.addDocuments(document3);

		verifyDocumentsUpdated(document1, document2, document3);
	}

	protected void verifyDocumentsUpdated(Document... documents)
		throws Exception {

		Mockito.verify(
			indexWriterHelper
		).updateDocuments(
			0, Arrays.asList(documents), false
		);
	}

	protected void verifyNoDocumentsUpdated() {
		Mockito.verifyNoInteractions(indexWriterHelper);
	}

	protected Document document1 = Mockito.mock(Document.class);
	protected Document document2 = Mockito.mock(Document.class);
	protected Document document3 = Mockito.mock(Document.class);
	protected IndexableActionableDynamicQuery indexableActionableDynamicQuery;
	protected IndexWriterHelper indexWriterHelper = Mockito.mock(
		IndexWriterHelper.class);

	private ServiceRegistration<?> _serviceRegistration;

}