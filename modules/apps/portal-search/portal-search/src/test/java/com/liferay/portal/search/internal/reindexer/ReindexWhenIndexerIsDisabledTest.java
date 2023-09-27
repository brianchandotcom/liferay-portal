/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.reindexer;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.spi.reindexer.BulkReindexer;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author André de Oliveira
 */
public class ReindexWhenIndexerIsDisabledTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_reindex = createReindex();
	}

	@After
	public void tearDown() {
		_bulkReindexersRegistryImpl.deactivate();

		_bulkReindexerServiceRegistration.unregister();
	}

	@Test
	public void testDisabled() throws Exception {
		Mockito.doReturn(
			false
		).when(
			indexer
		).isIndexerEnabled();

		_reindex.reindex(_CLASS_NAME, _CLASS_PK);

		Mockito.verify(
			indexer, Mockito.never()
		).reindex(
			_CLASS_NAME, _CLASS_PK
		);
	}

	@Test
	public void testDisabledBulk() throws Exception {
		Mockito.doReturn(
			false
		).when(
			indexer
		).isIndexerEnabled();

		long classPK1 = _CLASS_PK;
		long classPK2 = _CLASS_PK + 1;

		_reindex.reindex(_CLASS_NAME, classPK1, classPK2);

		Mockito.verify(
			indexer, Mockito.never()
		).reindex(
			Mockito.anyString(), Mockito.anyLong()
		);

		Mockito.verifyNoInteractions(bulkReindexer);
	}

	@Test
	public void testEnabled() throws Exception {
		Mockito.doReturn(
			true
		).when(
			indexer
		).isIndexerEnabled();

		_reindex.reindex(_CLASS_NAME, _CLASS_PK);

		Mockito.verify(
			indexer
		).reindex(
			_CLASS_NAME, _CLASS_PK
		);
	}

	@Test
	public void testEnabledBulk() throws Exception {
		Mockito.doReturn(
			true
		).when(
			indexer
		).isIndexerEnabled();

		long classPK1 = _CLASS_PK;
		long classPK2 = _CLASS_PK + 1;

		_reindex.reindex(_CLASS_NAME, classPK1, classPK2);

		Mockito.verify(
			indexer, Mockito.never()
		).reindex(
			Mockito.anyString(), Mockito.anyLong()
		);

		Mockito.verify(
			bulkReindexer
		).reindex(
			Mockito.anyLong(), Mockito.eq(Arrays.asList(classPK1, classPK2))
		);
	}

	protected Reindex createReindex() {
		IndexerRegistry indexerRegistry = Mockito.mock(IndexerRegistry.class);

		Mockito.doReturn(
			indexer
		).when(
			indexerRegistry
		).getIndexer(
			_CLASS_NAME
		);

		_bulkReindexerServiceRegistration = _bundleContext.registerService(
			BulkReindexer.class, bulkReindexer,
			MapUtil.singletonDictionary("indexer.class.name", _CLASS_NAME));

		_bulkReindexersRegistryImpl = new BulkReindexersRegistryImpl();

		_bulkReindexersRegistryImpl.activate(_bundleContext);

		Reindex reindex = new Reindex(
			indexerRegistry, _bulkReindexersRegistryImpl, null, null);

		reindex.setSynchronousExecution(true);

		return reindex;
	}

	protected BulkReindexer bulkReindexer = Mockito.mock(BulkReindexer.class);
	protected Indexer<?> indexer = Mockito.mock(Indexer.class);

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private static final long _CLASS_PK = RandomTestUtil.randomLong();

	private ServiceRegistration<BulkReindexer>
		_bulkReindexerServiceRegistration;
	private BulkReindexersRegistryImpl _bulkReindexersRegistryImpl;
	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private Reindex _reindex;

}