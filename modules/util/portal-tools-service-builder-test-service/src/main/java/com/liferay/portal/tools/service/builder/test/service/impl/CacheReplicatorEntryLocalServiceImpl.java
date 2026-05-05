/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry;
import com.liferay.portal.tools.service.builder.test.service.base.CacheReplicatorEntryLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CacheReplicatorEntryLocalServiceImpl
	extends CacheReplicatorEntryLocalServiceBaseImpl {

	@Override
	public CacheReplicatorEntry addCacheReplicatorEntry(
		long companyId, String name) {

		CacheReplicatorEntry cacheReplicatorEntry =
			cacheReplicatorEntryPersistence.create(
				counterLocalService.increment());

		cacheReplicatorEntry.setCompanyId(companyId);
		cacheReplicatorEntry.setName(name);

		return cacheReplicatorEntryPersistence.update(cacheReplicatorEntry);
	}

	@Override
	public CacheReplicatorEntry fetchCacheReplicatorEntryByName(String name) {
		return cacheReplicatorEntryPersistence.fetchByName(name);
	}

	@Override
	public List<CacheReplicatorEntry> getCacheReplicatorEntriesByCompanyId(
		long companyId) {

		return cacheReplicatorEntryPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<CacheReplicatorEntry> getCacheReplicatorEntriesByCompanyId(
		long companyId, int start, int end) {

		return cacheReplicatorEntryPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getCacheReplicatorEntriesCountByCompanyId(long companyId) {
		return cacheReplicatorEntryPersistence.countByCompanyId(companyId);
	}

}

// LIFERAY-SERVICE-BUILDER-HASH:1653481128