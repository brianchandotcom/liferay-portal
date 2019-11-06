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

package com.liferay.portal.kernel.internal.service.persistence;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.PortalCacheManagerProvider;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.ParamSetter;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class CachelessTableMapperImpl
	<L extends BaseModel<L>, R extends BaseModel<R>>
		extends TableMapperImpl<L, R> {

	public CachelessTableMapperImpl(
		String tableName, String companyColumnName, String leftColumnName,
		String rightColumnName, Class<L> leftModelClass,
		Class<R> rightModelClass, BasePersistence<L> leftBasePersistence,
		BasePersistence<R> rightBasePersistence) {

		super(
			tableName, companyColumnName, leftColumnName, rightColumnName,
			leftModelClass, rightModelClass, leftBasePersistence,
			rightBasePersistence);

		getTableMappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(
			leftBasePersistence.getDataSource(),
			StringBundler.concat(
				"SELECT * FROM ", tableName, " WHERE ", leftColumnName,
				" = ? AND ", rightColumnName, " = ?"),
			RowMapper.COUNT, ParamSetter.BIGINT, ParamSetter.BIGINT);

		destroy();

		leftToRightPortalCache = new DummyPortalCache<>(
			PortalCacheManagerNames.MULTI_VM,
			leftToRightPortalCache.getPortalCacheName());
		rightToLeftPortalCache = new DummyPortalCache<>(
			PortalCacheManagerNames.MULTI_VM,
			rightToLeftPortalCache.getPortalCacheName());
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

	protected static class DummyPortalCache<K extends Serializable, V>
		implements PortalCache<K, V> {

		@Override
		public V get(K key) {
			return null;
		}

		@Override
		public List<K> getKeys() {
			return Collections.emptyList();
		}

		@Override
		public PortalCacheManager<K, V> getPortalCacheManager() {
			return (PortalCacheManager<K, V>)
				PortalCacheManagerProvider.getPortalCacheManager(
					_portalCacheManagerName);
		}

		@Override
		public String getPortalCacheName() {
			return _portalCacheName;
		}

		@Override
		public boolean isBlocking() {
			return false;
		}

		@Override
		public boolean isMVCC() {
			return false;
		}

		@Override
		public void put(K key, V value) {
		}

		@Override
		public void put(K key, V value, int timeToLive) {
		}

		@Override
		public void registerPortalCacheListener(
			PortalCacheListener<K, V> portalCacheListener) {
		}

		@Override
		public void registerPortalCacheListener(
			PortalCacheListener<K, V> portalCacheListener,
			PortalCacheListenerScope portalCacheListenerScope) {
		}

		@Override
		public void remove(K key) {
		}

		@Override
		public void removeAll() {
		}

		@Override
		public void unregisterPortalCacheListener(
			PortalCacheListener<K, V> portalCacheListener) {
		}

		@Override
		public void unregisterPortalCacheListeners() {
		}

		protected DummyPortalCache(
			String portalCacheManagerName, String portalCacheName) {

			_portalCacheManagerName = portalCacheManagerName;
			_portalCacheName = portalCacheName;
		}

		private final String _portalCacheManagerName;
		private final String _portalCacheName;

	}

}