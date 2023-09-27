/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.util;

import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class DictionaryReindexerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
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
			_assertIndexWriterHelperReindexDictionariesWithCompanyId(companyId);
		}
	}

	@Test
	public void testReindexSystemCompanyDictionaries() throws SearchException {
		DictionaryReindexer dictionaryReindexer = new DictionaryReindexer(
			_indexWriterHelper, _portalInstancesLocalService);

		dictionaryReindexer.reindexDictionaries();

		_assertIndexWriterHelperReindexDictionariesWithCompanyId(
			CompanyConstants.SYSTEM);
	}

	private void _assertIndexWriterHelperReindexDictionariesWithCompanyId(
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

	private final IndexWriterHelper _indexWriterHelper = Mockito.mock(
		IndexWriterHelper.class);
	private final PortalInstancesLocalService _portalInstancesLocalService =
		Mockito.mock(PortalInstancesLocalService.class);

}