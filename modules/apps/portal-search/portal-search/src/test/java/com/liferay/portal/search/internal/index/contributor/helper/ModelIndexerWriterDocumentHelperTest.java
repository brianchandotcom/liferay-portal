/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.index.contributor.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class ModelIndexerWriterDocumentHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testException() throws PortalException {
		_throwIndexNameBuilderException(new SystemException());

		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper =
			new ModelIndexerWriterDocumentHelperImpl(
				RandomTestUtil.randomString(), indexDocumentBuilder);

		modelIndexerWriterDocumentHelper.getDocument(baseModel);
	}

	protected BaseModel<?> baseModel = Mockito.mock(BaseModel.class);
	protected IndexerDocumentBuilder indexDocumentBuilder = Mockito.mock(
		IndexerDocumentBuilder.class);

	private void _throwIndexNameBuilderException(Exception exception) {
		Mockito.when(
			indexDocumentBuilder.getDocument(Mockito.any())
		).thenThrow(
			exception
		);
	}

}