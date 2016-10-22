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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.reflect.Whitebox;

/**
 * @author André de Oliveira
 */
public class IndexableActionableDynamicQueryTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		PropsUtil.setProps(props);

		RegistryUtil.setRegistry(createRegistry());

		Whitebox.setInternalState(
			IndexWriterHelperUtil.class, IndexWriterHelper.class,
			indexWriterHelper);
	}

	@Test
	public void testAddDocuments() throws Exception {
		setUpInterval(1);

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.addDocuments(document1, document2);

		Mockito.verify(
			indexWriterHelper
		).updateDocuments(null, 0, Arrays.asList(document1, document2), false);
	}

	@Test
	public void testAddDocumentsWithinInterval() throws Exception {
		setUpInterval(3);

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.addDocuments(document1, document2);

		Mockito.verifyZeroInteractions(indexWriterHelper);

		indexableActionableDynamicQuery.addDocuments(document3);

		Mockito.verify(
			indexWriterHelper
		).updateDocuments(
			null, 0, Arrays.asList(document1, document2, document3), false);
	}

	protected Registry createRegistry() {
		Registry registry = new BasicRegistryImpl();

		registry.registerService(
			PortalExecutorManager.class,
			Mockito.mock(PortalExecutorManager.class));

		return registry;
	}

	protected void setUpInterval(int interval) {
		Mockito.doReturn(
			String.valueOf(interval)
		).when(
			props
		).get(PropsKeys.DAO_ORM_ACTIONABLE_DYNAMIC_QUERY_INTERVAL_DEFAULT);
	}

	@Mock
	protected Document document1;

	@Mock
	protected Document document2;

	@Mock
	protected Document document3;

	@Mock
	protected IndexWriterHelper indexWriterHelper;

	@Mock
	protected Props props;

}