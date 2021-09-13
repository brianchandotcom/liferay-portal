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