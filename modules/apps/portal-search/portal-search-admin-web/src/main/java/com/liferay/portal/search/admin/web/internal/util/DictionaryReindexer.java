/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.util;

import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;

/**
 * @author Adam Brandizzi
 */
public class DictionaryReindexer {

	public DictionaryReindexer(
		IndexWriterHelper indexWriterHelper,
		PortalInstancesLocalService portalInstancesLocalService) {

		_indexWriterHelper = indexWriterHelper;
		_portalInstancesLocalService = portalInstancesLocalService;
	}

	public void reindexDictionaries() throws SearchException {
		reindexDictionaries(CompanyConstants.SYSTEM);

		long[] companyIds = _portalInstancesLocalService.getCompanyIds();

		for (long companyId : companyIds) {
			reindexDictionaries(companyId);
		}
	}

	public void reindexDictionaries(long[] companyIds) throws SearchException {
		for (long companyId : companyIds) {
			reindexDictionaries(companyId);
		}
	}

	protected void reindexDictionaries(long companyId) throws SearchException {
		_indexWriterHelper.indexQuerySuggestionDictionaries(companyId);
		_indexWriterHelper.indexSpellCheckerDictionaries(companyId);
	}

	private final IndexWriterHelper _indexWriterHelper;
	private final PortalInstancesLocalService _portalInstancesLocalService;

}