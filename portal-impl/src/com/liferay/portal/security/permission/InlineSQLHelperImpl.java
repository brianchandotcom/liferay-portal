/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.dao.orm.CustomSQLUtil;

/**
 * @author Raymond Augé
 */
public class InlineSQLHelperImpl implements InlineSQLHelper {

	public static final String JOIN_RESOURCE_PERMISSION =
		InlineSQLHelper.class.getName() + ".joinResourcePermission";

	public boolean isEnabled() {
		return isEnabled(0);
	}

	public boolean isEnabled(long groupId) {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return false;
		}

		if (!PropsValues.PERMISSIONS_INLINE_SQL_CHECK_ENABLED) {
			return false;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return false;
		}

		if (groupId > 0) {
			if (permissionChecker.isGroupAdmin(groupId) ||
				permissionChecker.isGroupOwner(groupId)) {

				return false;
			}
		}
		else {
			if (permissionChecker.isCompanyAdmin()) {
				return false;
			}
		}

		return true;
	}

	public boolean isEnabled(long[] groupIds) {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return false;
		}

		if (!PropsValues.PERMISSIONS_INLINE_SQL_CHECK_ENABLED) {
			return false;
		}

		for (long groupId : groupIds) {
			if (!isEnabled(groupId)) {
				return false;
			}
		}

		return true;
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField) {

		return replacePermissionCheck(
			sql, className, classPKField, null, new long[] {0}, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, long groupId) {

		return replacePermissionCheck(
			sql, className, classPKField, null, new long[] {groupId}, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, long groupId,
		String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, null, new long[] {groupId},
			bridgeJoin);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, long[] groupIds) {

		return replacePermissionCheck(
			sql, className, classPKField, null, groupIds, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, long[] groupIds,
		String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, null, groupIds, bridgeJoin);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {0}, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {groupId},
			null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId, String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {groupId},
			bridgeJoin);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, groupIds, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds, String bridgeJoin) {

		if (!isEnabled(groupIds)) {
			return sql;
		}

		if (Validator.isNull(className)) {
			throw new IllegalArgumentException("className is null");
		}

		if (Validator.isNull(classPKField)) {
			throw new IllegalArgumentException("classPKField is null");
		}

		if (Validator.isNull(sql)) {
			return sql;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		long checkGroupId = 0;

		if (groupIds.length == 1) {
			checkGroupId = groupIds[0];
		}

		if (permissionChecker.hasPermission(
				checkGroupId, className, 0, ActionKeys.VIEW)) {

			return sql;
		}

		String permissionJoin = StringPool.BLANK;

		if (Validator.isNotNull(bridgeJoin)) {
			permissionJoin = bridgeJoin;
		}

		permissionJoin += CustomSQLUtil.get(JOIN_RESOURCE_PERMISSION);

		StringBundler ownerSQL = new StringBundler(5);

		long userId = getUserId();

		if (permissionChecker.isSignedIn()) {
			ownerSQL.append(" OR ");

			if (Validator.isNotNull(userIdField)) {
				ownerSQL.append("(");
				ownerSQL.append(userIdField);
				ownerSQL.append(" = ");
				ownerSQL.append(userId);
				ownerSQL.append(")");
			}
			else {
				ownerSQL.append("(ResourcePermission.ownerId = ");
				ownerSQL.append(userId);
				ownerSQL.append(")");
			}
		}

		long[] roleIds = getRoleIds(groupIds);

		StringBundler roleIdsSQL = new StringBundler();

		if (roleIds.length == 0) {
			roleIds = _NO_ROLE_IDS;
		}

		for (int i = 0; i < roleIds.length; i++) {
			if (i == 0) {
				roleIdsSQL.append("(");
			}
			else if (i > 0) {
				roleIdsSQL.append(" OR ");
			}

			roleIdsSQL.append("ResourcePermission.roleId = ");
			roleIdsSQL.append(roleIds[i]);

			if (i == (roleIds.length - 1)) {
				roleIdsSQL.append(")");
			}
		}

		StringBundler primKeysSQL = new StringBundler();

		primKeysSQL.append("(RP.primKey = CAST_TEXT(");
		primKeysSQL.append(classPKField);
		primKeysSQL.append(")");

		if (groupIds.length > 1) {
			for (long groupId : groupIds) {
				primKeysSQL.append(" OR RP.primKey = '");
				primKeysSQL.append(groupId);
				primKeysSQL.append("'");
			}
		}

		primKeysSQL.append(")");

		permissionJoin = StringUtil.replace(
			permissionJoin,
			new String[] {
				"[$CLASS_NAME$]",
				"[$COMPANY_ID$]",
				"OR [$OWNER_CHECK$]",
				"[$PRIM_KEYS$]",
				"[$ROLE_IDS$]"
			},
			new String[] {
				className,
				String.valueOf(permissionChecker.getCompanyId()),
				ownerSQL.toString(),
				primKeysSQL.toString(),
				roleIdsSQL.toString()
			});

		int pos = sql.indexOf(_WHERE_CLAUSE);

		if (pos != -1) {
			return sql.substring(0, pos + 1).concat(permissionJoin).concat(
				sql.substring(pos + 1));
		}

		pos = sql.indexOf(_GROUP_BY_CLAUSE);

		if (pos != -1) {
			return sql.substring(0, pos + 1).concat(permissionJoin).concat(
				sql.substring(pos + 1));
		}

		pos = sql.indexOf(_ORDER_BY_CLAUSE);

		if (pos != -1) {
			return sql.substring(0, pos + 1).concat(permissionJoin).concat(
				sql.substring(pos + 1));
		}

		return sql.concat(StringPool.SPACE).concat(permissionJoin);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, 0, bridgeJoin);
	}

	protected long[] getRoleIds(long groupId) {
		long[] roleIds = PermissionChecker.DEFAULT_ROLE_IDS;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			roleIds = permissionChecker.getRoleIds(
				permissionChecker.getUserId(), groupId);
		}

		return roleIds;
	}

	protected long[] getRoleIds(long[] groupIds) {
		long[] roleIds = PermissionChecker.DEFAULT_ROLE_IDS;

		for (long groupId : groupIds) {
			for (long roleId : getRoleIds(groupId)) {
				if (!ArrayUtil.contains(roleIds, roleId)) {
					roleIds = ArrayUtil.append(roleIds, roleId);
				}
			}
		}

		return roleIds;
	}

	protected long getUserId() {
		long userId = 0;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			userId = permissionChecker.getUserId();
		}

		return userId;
	}

	private static final long[] _NO_ROLE_IDS = {0};

	private static final String _GROUP_BY_CLAUSE = " GROUP BY ";

	private static final String _ORDER_BY_CLAUSE = " ORDER BY ";

	private static final String _WHERE_CLAUSE = " WHERE ";

}