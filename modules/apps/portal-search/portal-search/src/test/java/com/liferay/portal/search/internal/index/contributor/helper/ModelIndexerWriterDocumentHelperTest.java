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

package com.liferay.portal.search.internal.index.contributor.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
public class ModelIndexerWriterDocumentHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testException() throws PortalException {
		_throwIndexNameBuilderException(new SystemException());

		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper =
			new ModelIndexerWriterDocumentHelperImpl(
				RandomTestUtil.randomString(), indexDocumentBuilder);

		modelIndexerWriterDocumentHelper.getDocument(baseModel);
	}

	@Mock
	protected BaseModel<?> baseModel;

	@Mock
	protected IndexerDocumentBuilder indexDocumentBuilder;

	private void _throwIndexNameBuilderException(Exception exception) {
		Mockito.when(
			indexDocumentBuilder.getDocument(Matchers.any())
		).thenThrow(
			exception
		);
	}

}