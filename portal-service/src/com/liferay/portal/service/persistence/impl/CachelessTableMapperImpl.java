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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.BasePersistence;

import java.sql.Types;

import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class CachelessTableMapperImpl
		<L extends BaseModel<L>, R extends BaseModel<R>>
	extends TableMapperImpl<L, R> {

	public CachelessTableMapperImpl(
		String tableName, String leftColumnName, String rightColumnName,
		BasePersistence<L> leftBasePersistence,
		BasePersistence<R> rightBasePersistence,
		ServiceCompanyProvider serviceCompanyProvider) {

		super(
			tableName, leftColumnName, rightColumnName, leftBasePersistence,
			rightBasePersistence, serviceCompanyProvider);

		getTableMappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(
			leftBasePersistence.getDataSource(),
			"SELECT * FROM " + tableName + " WHERE " + leftColumnName +
				" = ? AND " + rightColumnName + " = ?",
			new int[] {Types.BIGINT, Types.BIGINT}, RowMapper.COUNT);

		leftToRightPortalCache = new DummyPortalCache(
			leftToRightPortalCache.getPortalCacheName(),
			leftToRightPortalCache.getPortalCacheManager());
		rightToLeftPortalCache = new DummyPortalCache(
			rightToLeftPortalCache.getPortalCacheName(),
			rightToLeftPortalCache.getPortalCacheManager());

		destroy();
	}

	@Override
	protected boolean containsTableMapping(
		long leftPrimaryKey, long rightPrimaryKey, boolean updateCache) {

		List<Integer> counts = null;

		try {
			counts = getTableMappingSqlQuery.execute(
				leftPrimaryKey, rightPrimaryKey);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		if (counts.isEmpty()) {
			return false;
		}

		int count = counts.get(0);

		if (count == 0) {
			return false;
		}

		return true;
	}

	protected final MappingSqlQuery<Integer> getTableMappingSqlQuery;

	protected static class DummyPortalCache
		implements PortalCache<Long, long[]> {

		@Override
		public long[] get(Long key) {
			return null;
		}

		@Override
		public List<Long> getKeys() {
			return Collections.emptyList();
		}

		/**
		 * @deprecated As of 7.0.0, replaced by {@link #getPortalCacheName()}
		 */
		@Deprecated
		@Override
		public String getName() {
			return getPortalCacheName();
		}

		@Override
		public PortalCacheManager<Long, long[]> getPortalCacheManager() {
			return portalCacheManager;
		}

		@Override
		public String getPortalCacheName() {
			return portalCacheName;
		}

		@Override
		public void put(Long key, long[] value) {
		}

		@Override
		public void put(Long key, long[] value, int timeToLive) {
		}

		@Override
		public void registerPortalCacheListener(
			PortalCacheListener<Long, long[]> portalCacheListener) {
		}

		@Override
		public void registerPortalCacheListener(
			PortalCacheListener<Long, long[]> portalCacheListener,
			PortalCacheListenerScope portalCacheListenerScope) {
		}

		@Override
		public void remove(Long key) {
		}

		@Override
		public void removeAll() {
		}

		@Override
		public void unregisterPortalCacheListener(
			PortalCacheListener<Long, long[]> portalCacheListener) {
		}

		@Override
		public void unregisterPortalCacheListeners() {
		}

		protected DummyPortalCache(
			String portalCacheName,
			PortalCacheManager<Long, long[]> portalCacheManager) {

			this.portalCacheName = portalCacheName;
			this.portalCacheManager = portalCacheManager;
		}

		protected final PortalCacheManager<Long, long[]> portalCacheManager;
		protected final String portalCacheName;

	}

}