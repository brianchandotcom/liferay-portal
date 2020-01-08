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

package com.liferay.change.tracking.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class CTTableMapperHelper {

	public CTTableMapperHelper(
		CTService<?> ctService, String tableName, String leftColumnName) {

		_ctService = ctService;
		_tableName = tableName;
		_leftColumnName = leftColumnName;
	}

	public void delete(long ctCollectionId) throws Exception {
		_ctService.updateWithUnsafeFunction(
			ctPersistence -> {
				Connection connection = CurrentConnectionUtil.getConnection(
					ctPersistence.getDataSource());

				try (PreparedStatement ps = connection.prepareStatement(
						StringBundler.concat(
							"delete from ", _tableName,
							" where ctCollectionId = ", ctCollectionId))) {

					ps.executeUpdate();
				}

				return null;
			});
	}

	public void publish(
			long ctCollectionId, PortalCacheManager<?, ?> portalCacheManager)
		throws Exception {

		if (_rightColumnName == null) {
			throw new NullPointerException(
				StringBundler.concat(
					"Missing column name for ", _tableName,
					" with column name ", _leftColumnName));
		}

		int count = _ctService.updateWithUnsafeFunction(
			ctPersistence -> _publish(ctPersistence, ctCollectionId));

		if (count != 0) {
			_clearCache(
				portalCacheManager, _tableName, _leftColumnName,
				_rightColumnName);

			_clearCache(
				portalCacheManager, _tableName, _rightColumnName,
				_leftColumnName);
		}
	}

	public void undo(long fromCTCollectionId, long toCTCollectionId)
		throws Exception {

		if (_rightColumnName == null) {
			throw new NullPointerException(
				StringBundler.concat(
					"Missing column name for ", _tableName,
					" with column name ", _leftColumnName));
		}

		_ctService.updateWithUnsafeFunction(
			ctPersistence -> {
				Connection connection = CurrentConnectionUtil.getConnection(
					ctPersistence.getDataSource());

				try (PreparedStatement ps = connection.prepareStatement(
						StringBundler.concat(
							"insert into ", _tableName, " (companyId, ",
							_leftColumnName, ", ", _rightColumnName,
							", ctCollectionId, changeType) select ",
							"t1.companyId, t1.", _leftColumnName, ", t1.",
							_rightColumnName, ", ", toCTCollectionId,
							" as ctCollectionId, ? as changeType from ",
							_tableName, " t1 where t1.ctCollectionId = ",
							fromCTCollectionId, " and t1.changeType = ?"))) {

					ps.setBoolean(1, true);
					ps.setBoolean(2, false);

					ps.executeUpdate();

					ps.setBoolean(1, false);
					ps.setBoolean(2, true);

					ps.executeUpdate();
				}

				return null;
			});
	}

	protected void setRightColumnName(String rightColumnName) {
		_rightColumnName = rightColumnName;
	}

	private static void _clearCache(
		PortalCacheManager<?, ?> portalCacheManager, String tableName,
		String leftColumnName, String rightColumnName) {

		String portalCacheName = StringBundler.concat(
			TableMapper.class.getName(), "-", tableName, "-", leftColumnName,
			"-To-", rightColumnName);

		PortalCache<?, ?> portalCache = portalCacheManager.fetchPortalCache(
			portalCacheName);

		if (portalCache != null) {
			portalCache.removeAll();
		}
	}

	private int _publish(CTPersistence<?> ctPersistence, long ctCollectionId)
		throws Exception {

		Connection connection = CurrentConnectionUtil.getConnection(
			ctPersistence.getDataSource());

		List<Map.Entry<Long, Long>> entries = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"select ", _leftColumnName, ", ", _rightColumnName,
					" from ", _tableName, " where ctCollectionId = ",
					ctCollectionId, " and changeType = ?"))) {

			ps.setBoolean(1, false);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					entries.add(
						new AbstractMap.SimpleImmutableEntry<>(
							rs.getLong(1), rs.getLong(2)));
				}
			}
		}

		int count = 0;

		if (!entries.isEmpty()) {
			StringBundler sb = new StringBundler(8 * entries.size() + 3);

			sb.append("delete from ");
			sb.append(_tableName);
			sb.append(" where ctCollectionId = 0 and ((");

			for (Map.Entry<Long, Long> entry : entries) {
				sb.append(_leftColumnName);
				sb.append(" = ");
				sb.append(entry.getKey());
				sb.append(" and ");
				sb.append(_rightColumnName);
				sb.append(" = ");
				sb.append(entry.getValue());
				sb.append(") or (");
			}

			sb.setStringAt("))", sb.index() - 1);

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString())) {

				count += ps.executeUpdate();
			}
		}

		try (PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"insert into ", _tableName, " (companyId, ",
					_leftColumnName, ", ", _rightColumnName,
					", ctCollectionId) select t1.companyId, t1.",
					_leftColumnName, ", t1.", _rightColumnName,
					", 0 as ctCollectionId from ", _tableName, " t1 left join ",
					_tableName, " t2 on t2.ctCollectionId = 0 and t2.",
					_leftColumnName, " = t1.", _leftColumnName, " and t2.",
					_rightColumnName, " = t1.", _rightColumnName,
					" where t1.ctCollectionId = ", ctCollectionId,
					" and t1.changeType = ?"))) {

			ps.setBoolean(1, true);

			count += ps.executeUpdate();
		}

		return count;
	}

	private final CTService<?> _ctService;
	private final String _leftColumnName;
	private String _rightColumnName;
	private final String _tableName;

}