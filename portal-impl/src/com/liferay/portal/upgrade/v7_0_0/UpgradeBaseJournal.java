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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public abstract class UpgradeBaseJournal extends UpgradeDynamicDataMapping {

	protected void addResourcePermission(
			long companyId, String className, long primKey, long roleId,
			long actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			long resourcePermissionId = increment(
				ResourcePermission.class.getName());

			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(3);

			sb.append("insert into ResourcePermission (resourcePermissionId, ");
			sb.append("companyId, name, scope, primKey, roleId, ownerId, ");
			sb.append("actionIds) values (?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, resourcePermissionId);
			ps.setLong(2, companyId);
			ps.setString(3, className);
			ps.setInt(4, ResourceConstants.SCOPE_INDIVIDUAL);
			ps.setLong(5, primKey);
			ps.setLong(6, roleId);
			ps.setLong(7, 0);
			ps.setLong(8, actionIds);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource permission " + className, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected long getBitwiseValue(
		Map<String, Long> bitwiseValues, List<String> actionIds) {

		long bitwiseValue = 0;

		for (String actionId : actionIds) {
			Long actionIdBitwiseValue = bitwiseValues.get(actionId);

			if (actionIdBitwiseValue == null) {
				continue;
			}

			bitwiseValue |= actionIdBitwiseValue;
		}

		return bitwiseValue;
	}

	protected Map<String, Long> getBitwiseValues(String name) throws Exception {
		Map<String, Long> bitwiseValues = _bitwiseValues.get(name);

		if (bitwiseValues != null) {
			return bitwiseValues;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String currentShardName = null;

		try {
			currentShardName = ShardUtil.setTargetSource(
				PropsUtil.get(PropsKeys.SHARD_DEFAULT_NAME));

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select actionId, bitwiseValue from ResourceAction " +
					"where name = ?");

			ps.setString(1, name);

			rs = ps.executeQuery();

			bitwiseValues = new HashMap<String, Long>();

			while (rs.next()) {
				String actionId = rs.getString("actionId");
				long bitwiseValue = rs.getLong("bitwiseValue");

				bitwiseValues.put(actionId, bitwiseValue);
			}

			_bitwiseValues.put(name, bitwiseValues);

			return bitwiseValues;
		}
		finally {
			if (Validator.isNotNull(currentShardName)) {
				ShardUtil.setTargetSource(currentShardName);
			}

			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getCompanyGroupId(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from Group_ where classNameId = ? and " +
					"classPK = ?");

			ps.setLong(1, PortalUtil.getClassNameId(Company.class.getName()));
			ps.setLong(2, companyId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getDefaultUserId(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
					"select userId from User_ where companyId = ? and " +
							"defaultUser = ?"
			);

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("userId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getRoleId(long companyId, String name) throws Exception {
		String roleIdsKey = companyId + StringPool.POUND + name;

		Long roleId = _roleIds.get(roleIdsKey);

		if (roleId != null) {
			return roleId;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select roleId from Role_ where companyId = ? and name = ?");

			ps.setLong(1, companyId);
			ps.setString(2, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				roleId = rs.getLong("roleId");
			}

			_roleIds.put(roleIdsKey, roleId);

			return roleId;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String localize(
			long groupId, String key, String defaultLanguageId)
		throws Exception {

		Map<Locale, String> localizationMap = new HashMap<Locale, String>();

		Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

		for (Locale locale : locales) {
			localizationMap.put(locale, LanguageUtil.get(locale, key));
		}

		return LocalizationUtil.updateLocalization(
			localizationMap, StringPool.BLANK, key, defaultLanguageId);
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeBaseJournal.class);

	private Map<String, Map<String, Long>> _bitwiseValues =
		new HashMap<String, Map<String, Long>>();
	private Map<String, Long> _roleIds = new HashMap<String, Long>();

}