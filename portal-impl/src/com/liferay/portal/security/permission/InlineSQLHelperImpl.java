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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceTypePermissionLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Connor McKay
 */
@DoPrivileged
public class InlineSQLHelperImpl implements InlineSQLHelper {

	public static final String FILTER_BY_RESOURCE_BLOCK_ID =
		InlineSQLHelper.class.getName() + ".filterByResourceBlockId";

	public static final String FILTER_BY_RESOURCE_BLOCK_ID_OWNER =
		InlineSQLHelper.class.getName() + ".filterByResourceBlockIdOwner";

	public static final String FIND_BY_RESOURCE_BLOCK_ID =
		InlineSQLHelper.class.getName() + ".findByResourceBlockId";

	public static final String FIND_BY_RESOURCE_PERMISSION =
		InlineSQLHelper.class.getName() + ".findByResourcePermission";

	@Override
	public boolean isEnabled() {
		return isEnabled(0, 0);
	}

	@Override
	public boolean isEnabled(long groupId) {
		return isEnabled(0, groupId);
	}

	@Override
	public boolean isEnabled(long companyId, long groupId) {
		if (!PropsValues.PERMISSIONS_INLINE_SQL_CHECK_ENABLED) {
			return false;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new IllegalStateException("Permission checker is null");
		}

		if (groupId > 0) {
			if (permissionChecker.isGroupAdmin(groupId) ||
				permissionChecker.isGroupOwner(groupId)) {

				return false;
			}
		}
		else if (companyId > 0) {
			if (permissionChecker.isCompanyAdmin(companyId)) {
				return false;
			}
		}
		else {
			if (permissionChecker.isOmniadmin()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isEnabled(long[] groupIds) {
		if (!PropsValues.PERMISSIONS_INLINE_SQL_CHECK_ENABLED) {
			return false;
		}

		for (long groupId : groupIds) {
			if (isEnabled(groupId)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField) {

		return replacePermissionCheck(
			sql, className, classPKField, null, new long[] {0}, null);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, long groupId) {

		return replacePermissionCheck(
			sql, className, classPKField, null, new long[] {groupId}, null);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, long groupId,
		String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, null, new long[] {groupId},
			bridgeJoin);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, long[] groupIds) {

		return replacePermissionCheck(
			sql, className, classPKField, null, groupIds, null);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, long[] groupIds,
		String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, null, groupIds, bridgeJoin);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {0}, null);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {groupId},
			null);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId, String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {groupId},
			bridgeJoin);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, groupIds, null);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds, String bridgeJoin) {

		String groupIdField = classPKField.substring(
			0, classPKField.lastIndexOf(CharPool.PERIOD));

		return replacePermissionCheck(
			sql, className, classPKField, userIdField,
			groupIdField.concat(".groupId"), groupIds, bridgeJoin);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, 0, bridgeJoin);
	}

	@Override
	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String groupIdField, long[] groupIds, String bridgeJoin) {

		if (!isEnabled(groupIds)) {
			return sql;
		}

		if (Validator.isNull(className)) {
			throw new IllegalArgumentException("className is null");
		}

		if (Validator.isNull(sql)) {
			return sql;
		}

		if (ResourceBlockLocalServiceUtil.isSupported(className)) {
			return replacePermissionCheckBlocks(
				sql, className, classPKField, userIdField, groupIds,
				bridgeJoin);
		}
		else {
			return replacePermissionCheckJoin(
				sql, className, classPKField, userIdField, groupIdField,
				groupIds, bridgeJoin);
		}
	}

	protected Set<Long> getOwnerResourceBlockIds(
		long companyId, long[] groupIds, String className) {

		Set<Long> resourceBlockIds = new HashSet<>();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		for (long groupId : groupIds) {
			resourceBlockIds.addAll(
				permissionChecker.getOwnerResourceBlockIds(
					companyId, groupId, className, ActionKeys.VIEW));
		}

		return resourceBlockIds;
	}

	protected String getOwnerResourceBlockIdsSQL(
		PermissionChecker permissionChecker, long checkGroupId,
		String className, Set<Long> ownerResourceBlockIds) {

		if (ownerResourceBlockIds.size() <
				PropsValues.
					PERMISSIONS_INLINE_SQL_RESOURCE_BLOCK_QUERY_THRESHOLD) {

			return StringUtil.merge(ownerResourceBlockIds);
		}

		return StringUtil.replace(
			CustomSQLUtil.get(FIND_BY_RESOURCE_BLOCK_ID),
			new String[] {
				"[$COMPANY_ID$]", "[$GROUP_ID$]", "[$RESOURCE_BLOCK_NAME$]",
				"[$ROLE_ID$]"
			},
			new String[] {
				String.valueOf(permissionChecker.getCompanyId()),
				String.valueOf(checkGroupId), className,
				StringUtil.valueOf(permissionChecker.getOwnerRoleId())
			});
	}

	protected Set<Long> getResourceBlockIds(
		long companyId, long[] groupIds, String className) {

		Set<Long> resourceBlockIds = new HashSet<>();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		for (long groupId : groupIds) {
			resourceBlockIds.addAll(
				permissionChecker.getResourceBlockIds(
					companyId, groupId, permissionChecker.getUserId(),
					className, ActionKeys.VIEW));
		}

		return resourceBlockIds;
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
		if (groupIds.length == 1) {
			return getRoleIds(groupIds[0]);
		}

		Set<Long> roleIds = new HashSet<>();

		for (long groupId : groupIds) {
			for (long roleId : getRoleIds(groupId)) {
				roleIds.add(roleId);
			}
		}

		return ArrayUtil.toLongArray(roleIds);
	}

	protected String getRoleIdsOrOwnerIdSQL(
		PermissionChecker permissionChecker, long[] groupIds,
		String userIdField) {

		StringBundler sb = new StringBundler(9);

		long[] roleIds = getRoleIds(groupIds);

		if (roleIds.length > 0) {
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("ResourcePermission.roleId IN (");
			sb.append(StringUtil.merge(roleIds));
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		if (permissionChecker.isSignedIn()) {
			if (sb.index() > 0) {
				sb.append(" OR ");
			}
			else {
				sb.append(StringPool.OPEN_PARENTHESIS);
			}

			long userId = permissionChecker.getUserId();

			if (Validator.isNotNull(userIdField)) {
				sb.append(userIdField);
				sb.append(" = ");
				sb.append(userId);
			}
			else {
				sb.append("ResourcePermission.ownerId = ");
				sb.append(userId);
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);
		}
		else if (sb.index() > 0) {
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
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

	protected String getUserResourceBlockIdsSQL(
		PermissionChecker permissionChecker, long checkGroupId, long[] roleIds,
		String className, Set<Long> userResourceBlockIds) {

		if (userResourceBlockIds.size() <
				PropsValues.
					PERMISSIONS_INLINE_SQL_RESOURCE_BLOCK_QUERY_THRESHOLD) {

			return StringUtil.merge(userResourceBlockIds);
		}

		return StringUtil.replace(
			CustomSQLUtil.get(FIND_BY_RESOURCE_BLOCK_ID),
			new String[] {
				"[$COMPANY_ID$]", "[$GROUP_ID$]", "[$RESOURCE_BLOCK_NAME$]",
				"[$ROLE_ID$]"
			},
			new String[] {
				String.valueOf(permissionChecker.getCompanyId()),
				String.valueOf(checkGroupId), className,
				StringUtil.merge(roleIds)
			});
	}

	protected String replacePermissionCheckBlocks(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds, String bridgeJoin) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		long checkGroupId = 0;

		if (groupIds.length == 1) {
			checkGroupId = groupIds[0];
		}

		long[] roleIds = permissionChecker.getRoleIds(
			getUserId(), checkGroupId);

		try {
			for (long roleId : roleIds) {
				if (ResourceTypePermissionLocalServiceUtil.
						hasCompanyScopePermission(
							permissionChecker.getCompanyId(), className, roleId,
							ActionKeys.VIEW)) {

					return sql;
				}
			}
		}
		catch (Exception e) {
		}

		Set<Long> userResourceBlockIds = getResourceBlockIds(
			permissionChecker.getCompanyId(), groupIds, className);

		String permissionWhere = StringPool.BLANK;

		if (Validator.isNotNull(bridgeJoin)) {
			permissionWhere = bridgeJoin;
		}

		Set<Long> ownerResourceBlockIds = getOwnerResourceBlockIds(
			permissionChecker.getCompanyId(), groupIds, className);

		// If a user has regular access to a resource block, it isn't necessary
		// to check owner permissions on it as well.

		ownerResourceBlockIds.removeAll(userResourceBlockIds);

		// A SQL syntax error occurs if there is not at least one resource block
		// ID.

		if (ownerResourceBlockIds.isEmpty()) {
			ownerResourceBlockIds.add(_NO_RESOURCE_BLOCKS_ID);
		}

		if (userResourceBlockIds.isEmpty()) {
			userResourceBlockIds.add(_NO_RESOURCE_BLOCKS_ID);
		}

		if (Validator.isNotNull(userIdField)) {
			permissionWhere = permissionWhere.concat(
				CustomSQLUtil.get(FILTER_BY_RESOURCE_BLOCK_ID_OWNER));

			permissionWhere = StringUtil.replace(
				permissionWhere,
				new String[] {
					"[$OWNER_RESOURCE_BLOCK_ID$]", "[$USER_ID$]",
					"[$USER_ID_FIELD$]", "[$USER_RESOURCE_BLOCK_ID$]"
				},
				new String[] {
					getOwnerResourceBlockIdsSQL(
						permissionChecker, checkGroupId, className,
						ownerResourceBlockIds),
					String.valueOf(permissionChecker.getUserId()), userIdField,
					getUserResourceBlockIdsSQL(
						permissionChecker, checkGroupId, roleIds, className,
						userResourceBlockIds)
				});
		}
		else {
			permissionWhere = permissionWhere.concat(
				CustomSQLUtil.get(FILTER_BY_RESOURCE_BLOCK_ID));

			permissionWhere = StringUtil.replace(
				permissionWhere, "[$USER_RESOURCE_BLOCK_ID$]",
				getUserResourceBlockIdsSQL(
					permissionChecker, checkGroupId, roleIds, className,
					userResourceBlockIds));
		}

		int pos = sql.indexOf(_WHERE_CLAUSE);

		if (pos != -1) {
			StringBundler sb = new StringBundler(4);

			sb.append(sql.substring(0, pos));
			sb.append(permissionWhere);
			sb.append(" AND ");
			sb.append(sql.substring(pos + 7));

			return sb.toString();
		}

		pos = sql.indexOf(_GROUP_BY_CLAUSE);

		if (pos != -1) {
			return sql.substring(0, pos + 1).concat(permissionWhere).concat(
				sql.substring(pos + 1));
		}

		pos = sql.indexOf(_ORDER_BY_CLAUSE);

		if (pos != -1) {
			return sql.substring(0, pos + 1).concat(permissionWhere).concat(
				sql.substring(pos + 1));
		}

		return sql.concat(StringPool.SPACE).concat(permissionWhere);
	}

	protected String replacePermissionCheckJoin(
		String sql, String className, String classPKField, String userIdField,
		String groupIdField, long[] groupIds, String bridgeJoin) {

		if (Validator.isNull(classPKField)) {
			throw new IllegalArgumentException("classPKField is null");
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		long companyId = 0;

		if (groupIds.length == 1) {
			long groupId = groupIds[0];

			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			if (group != null) {
				companyId = group.getCompanyId();

				long[] roleIds = getRoleIds(groupId);

				try {
					if (ResourcePermissionLocalServiceUtil.
							hasResourcePermission(
								companyId, className,
								ResourceConstants.SCOPE_GROUP,
								String.valueOf(groupId), roleIds,
								ActionKeys.VIEW) ||
						ResourcePermissionLocalServiceUtil.
							hasResourcePermission(
								companyId, className,
								ResourceConstants.SCOPE_GROUP_TEMPLATE,
								String.valueOf(
									GroupConstants.DEFAULT_PARENT_GROUP_ID),
								roleIds, ActionKeys.VIEW)) {

						return sql;
					}
				}
				catch (PortalException pe) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get resource permissions for " +
								className + " with group " + groupId,
							pe);
					}
				}
			}
		}
		else {
			for (long groupId : groupIds) {
				Group group = GroupLocalServiceUtil.fetchGroup(groupId);

				if (group == null) {
					continue;
				}

				if (companyId == 0) {
					companyId = group.getCompanyId();

					continue;
				}

				if (group.getCompanyId() != companyId) {
					throw new IllegalArgumentException(
						"Permission queries across multiple portal instances " +
							"are not supported");
				}
			}
		}

		if (companyId == 0) {
			companyId = permissionChecker.getCompanyId();
		}

		try {
			if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
					companyId, className, ResourceConstants.SCOPE_COMPANY,
					String.valueOf(companyId), getRoleIds(0),
					ActionKeys.VIEW)) {

				return sql;
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get resource permissions for " + className +
						" with company " + companyId,
					pe);
			}
		}

		String permissionQuery = CustomSQLUtil.get(FIND_BY_RESOURCE_PERMISSION);

		if (Validator.isNotNull(bridgeJoin)) {
			permissionQuery = bridgeJoin.concat(permissionQuery);
		}

		String roleIdsOrOwnerIdSQL = getRoleIdsOrOwnerIdSQL(
			permissionChecker, groupIds, userIdField);

		StringBundler groupAdminResourcePermissionSB = null;

		for (long groupId : groupIds) {
			if (!isEnabled(groupId)) {
				groupAdminResourcePermissionSB = new StringBundler(5);

				if (!roleIdsOrOwnerIdSQL.isEmpty()) {
					groupAdminResourcePermissionSB.append(" OR ");
				}

				groupAdminResourcePermissionSB.append(
					"((ResourcePermission.primKeyId = 0) AND ");

				groupAdminResourcePermissionSB.append(
					"(ResourcePermission.roleId = ");

				groupAdminResourcePermissionSB.append(
					permissionChecker.getOwnerRoleId());

				groupAdminResourcePermissionSB.append("))");

				break;
			}
		}

		int scope = ResourceConstants.SCOPE_INDIVIDUAL;

		String groupAdminSQL = StringPool.BLANK;

		if (groupAdminResourcePermissionSB != null) {
			groupAdminSQL = groupAdminResourcePermissionSB.toString();
		}

		permissionQuery = StringUtil.replace(
			permissionQuery,
			new String[] {
				"[$CLASS_NAME$]", "[$COMPANY_ID$]",
				"[$GROUP_ADMIN_RESOURCE_PERMISSION$]",
				"[$RESOURCE_SCOPE_INDIVIDUAL$]", "[$ROLE_IDS_OR_OWNER_ID$]"
			},
			new String[] {
				className, String.valueOf(companyId), groupAdminSQL,
				String.valueOf(scope), roleIdsOrOwnerIdSQL
			});

		StringBundler sb = new StringBundler(8);

		int pos = sql.indexOf(_WHERE_CLAUSE);

		if (pos == -1) {
			pos = sql.indexOf(_GROUP_BY_CLAUSE);

			if (pos == -1) {
				pos = sql.indexOf(_ORDER_BY_CLAUSE);
			}

			if (pos == -1) {
				sb.append(sql);
			}
			else {
				sb.append(sql.substring(0, pos));
			}

			sb.append(_WHERE_CLAUSE);

			_appendPermissionQuery(sb, classPKField, permissionQuery);

			if (pos != -1) {
				sb.append(sql.substring(pos));
			}
		}
		else {
			pos += _WHERE_CLAUSE.length();

			sb.append(sql.substring(0, pos));

			_appendPermissionQuery(sb, classPKField, permissionQuery);

			sb.append("AND ");

			sb.append(sql.substring(pos));
		}

		return sb.toString();
	}

	private void _appendPermissionQuery(
		StringBundler sb, String classPKField, String permissionQuery) {

		sb.append("(");
		sb.append(classPKField);
		sb.append(" IN (");
		sb.append(permissionQuery);
		sb.append(")) ");
	}

	private static final String _GROUP_BY_CLAUSE = " GROUP BY ";

	private static final long _NO_RESOURCE_BLOCKS_ID = -1;

	private static final String _ORDER_BY_CLAUSE = " ORDER BY ";

	private static final String _WHERE_CLAUSE = " WHERE ";

	private static final Log _log = LogFactoryUtil.getLog(
		InlineSQLHelperImpl.class);

}