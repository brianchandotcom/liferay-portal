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

package com.liferay.portal.search.admin.web.internal.util;

import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author Adam Brandizzi
 */
public class DictionaryReindexerTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_portalInstancesLocalService.getCompanyIds()
		).thenReturn(
			_COMPANY_IDS
		);
	}

	@Test
	public void testReindexAllCompaniesDictionaries() throws SearchException {
		DictionaryReindexer dictionaryReindexer = new DictionaryReindexer(
			_indexWriterHelper, _portalInstancesLocalService);

		dictionaryReindexer.reindexDictionaries();

		for (long companyId : _COMPANY_IDS) {
			assertIndexWriterHelperReindexDictionariesWithCompanyId(companyId);
		}
	}

	@Test
	public void testReindexSystemCompanyDictionaries() throws SearchException {
		DictionaryReindexer dictionaryReindexer = new DictionaryReindexer(
			_indexWriterHelper, _portalInstancesLocalService);

		dictionaryReindexer.reindexDictionaries();

		assertIndexWriterHelperReindexDictionariesWithCompanyId(
			CompanyConstants.SYSTEM);
	}

	protected void assertIndexWriterHelperReindexDictionariesWithCompanyId(
			long companyId)
		throws SearchException {

		Mockito.verify(
			_indexWriterHelper
		).indexSpellCheckerDictionaries(
			companyId
		);

		Mockito.verify(
			_indexWriterHelper
		).indexQuerySuggestionDictionaries(
			companyId
		);
	}

	private static final long[] _COMPANY_IDS = {1001L, 2002L};

	@Spy
	private final IndexWriterHelper _indexWriterHelper = Mockito.mock(
		IndexWriterHelper.class);

	@Mock
	private PortalInstancesLocalService _portalInstancesLocalService;

}