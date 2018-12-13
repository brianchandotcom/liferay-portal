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

package com.liferay.portal.security.permission.internal;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.contributor.PermissionSQLContributor;
import com.liferay.portal.security.permission.internal.configuration.InlinePermissionConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Connor McKay
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.portal.security.permission.internal.configuration.InlinePermissionConfiguration",
	immediate = true, service = InlineSQLHelper.class
)
public class InlineSQLHelperImpl implements InlineSQLHelper {

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
		if (!_inlinePermissionConfiguration.sqlCheckEnabled()) {
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
		if (!_inlinePermissionConfiguration.sqlCheckEnabled()) {
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

		if (Objects.equals(className, AssetTag.class.getName())) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Class ", className,
					" does  not support inline permissions. See LPS-82433."));
		}

		if (Validator.isNull(sql)) {
			return sql;
		}

		return replacePermissionCheckJoin(
			sql, className, classPKField, userIdField, groupIdField, groupIds,
			bridgeJoin);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		modified(properties);

		_permissionSQLContributors = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, PermissionSQLContributor.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_permissionSQLContributors.close();
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
			if (roleIds.length > 0) {
				sb.append(" OR ");
			}
			else {
				sb.append(StringPool.OPEN_PARENTHESIS);
			}

			long userId = permissionChecker.getUserId();

			if (Validator.isNull(userIdField)) {
				sb.append("ResourcePermission.ownerId = ");
				sb.append(userId);
			}
			else {
				sb.append(userIdField);
				sb.append(" = ");
				sb.append(userId);
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);
		}
		else if (roleIds.length > 0) {
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

	@Modified
	protected void modified(Map<String, Object> properties) {
		_inlinePermissionConfiguration = ConfigurableUtil.createConfigurable(
			InlinePermissionConfiguration.class, properties);
	}

	protected String replacePermissionCheckJoin(
		String sql, String className, String classPKField, String userIdField,
		String groupIdField, long[] groupIds, String bridgeJoin) {

		if (Validator.isNull(classPKField)) {
			throw new IllegalArgumentException("classPKField is null");
		}

		long companyId = 0;

		if (groupIds.length == 1) {
			long groupId = groupIds[0];

			Group group = _groupLocalService.fetchGroup(groupId);

			if (group != null) {
				companyId = group.getCompanyId();

				long[] roleIds = getRoleIds(groupId);

				try {
					if (_resourcePermissionLocalService.hasResourcePermission(
							companyId, className, ResourceConstants.SCOPE_GROUP,
							String.valueOf(groupId), roleIds,
							ActionKeys.VIEW) ||
						_resourcePermissionLocalService.hasResourcePermission(
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
							StringBundler.concat(
								"Unable to get resource permissions for ",
								className, " with group ", groupId),
							pe);
					}
				}
			}
		}
		else {
			for (long groupId : groupIds) {
				Group group = _groupLocalService.fetchGroup(groupId);

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
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			companyId = permissionChecker.getCompanyId();
		}

		try {
			if (_resourcePermissionLocalService.hasResourcePermission(
					companyId, className, ResourceConstants.SCOPE_COMPANY,
					String.valueOf(companyId), getRoleIds(0),
					ActionKeys.VIEW)) {

				return sql;
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get resource permissions for ", className,
						" with company ", companyId),
					pe);
			}
		}

		String resourcePermissionSQL = _getResourcePermissionSQL(
			companyId, className, userIdField, groupIds, bridgeJoin);

		return _insertResourcePermissionSQL(
			sql, className, classPKField, userIdField, groupIdField, groupIds,
			resourcePermissionSQL);
	}

	private void _appendPermissionSQL(
		StringBundler sb, String className, String classPKField,
		String userIdField, String groupIdField, long[] groupIds,
		String permissionSQL) {

		List<PermissionSQLContributor> permissionSQLContributors =
			_permissionSQLContributors.getService(className);

		StringBundler permissionSQLContributorsSQLSB = null;

		if ((permissionSQLContributors != null) &&
			!permissionSQLContributors.isEmpty()) {

			permissionSQLContributorsSQLSB = new StringBundler(
				permissionSQLContributors.size() * 3);

			for (PermissionSQLContributor permissionSQLContributor :
					permissionSQLContributors) {

				String contributorPermissionSQL =
					permissionSQLContributor.getPermissionSQL(
						className, classPKField, userIdField, groupIdField,
						groupIds);

				if (Validator.isNull(contributorPermissionSQL)) {
					continue;
				}

				permissionSQLContributorsSQLSB.append("OR (");
				permissionSQLContributorsSQLSB.append(contributorPermissionSQL);
				permissionSQLContributorsSQLSB.append(") ");
			}
		}

		StringBundler groupAdminResourcePermissionSB = null;

		for (long groupId : groupIds) {
			if (!isEnabled(groupId)) {
				if (groupAdminResourcePermissionSB == null) {
					groupAdminResourcePermissionSB = new StringBundler(
						groupIds.length * 2 - 1);
				}
				else {
					groupAdminResourcePermissionSB.append(", ");
				}

				groupAdminResourcePermissionSB.append(groupId);
			}
		}

		if ((permissionSQLContributorsSQLSB != null) ||
			(groupAdminResourcePermissionSB != null)) {

			sb.append("(");
		}

		sb.append("(");
		sb.append(classPKField);
		sb.append(" IN (");
		sb.append(permissionSQL);
		sb.append(")) ");

		if (permissionSQLContributorsSQLSB != null) {
			sb.append(permissionSQLContributorsSQLSB);
		}

		if (groupAdminResourcePermissionSB != null) {
			sb.append(" OR (");
			sb.append(groupIdField);
			sb.append(" IN (");
			sb.append(groupAdminResourcePermissionSB);
			sb.append(")) ");
		}

		if ((permissionSQLContributorsSQLSB != null) ||
			(groupAdminResourcePermissionSB != null)) {

			sb.append(") ");
		}
	}

	private String _getResourcePermissionSQL(
		long companyId, String className, String userIdField, long[] groupIds,
		String bridgeJoin) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String resourcePermissionSQL = _customSQL.get(
			getClass(), FIND_BY_RESOURCE_PERMISSION);

		if (Validator.isNotNull(bridgeJoin)) {
			resourcePermissionSQL = bridgeJoin.concat(resourcePermissionSQL);
		}

		String roleIdsOrOwnerIdSQL = getRoleIdsOrOwnerIdSQL(
			permissionChecker, groupIds, userIdField);

		int scope = ResourceConstants.SCOPE_INDIVIDUAL;

		resourcePermissionSQL = StringUtil.replace(
			resourcePermissionSQL,
			new String[] {
				"[$CLASS_NAME$]", "[$COMPANY_ID$]",
				"[$RESOURCE_SCOPE_INDIVIDUAL$]", "[$ROLE_IDS_OR_OWNER_ID$]"
			},
			new String[] {
				className, String.valueOf(companyId), String.valueOf(scope),
				roleIdsOrOwnerIdSQL
			});

		return resourcePermissionSQL;
	}

	private String _insertResourcePermissionSQL(
		String sql, String className, String classPKField, String userIdField,
		String groupIdField, long[] groupIds, String permissionSQL) {

		StringBundler sb = new StringBundler(11);

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

			_appendPermissionSQL(
				sb, className, classPKField, userIdField, groupIdField,
				groupIds, permissionSQL);

			if (pos != -1) {
				sb.append(sql.substring(pos));
			}
		}
		else {
			pos += _WHERE_CLAUSE.length();

			sb.append(sql.substring(0, pos));

			_appendPermissionSQL(
				sb, className, classPKField, userIdField, groupIdField,
				groupIds, permissionSQL);

			sb.append("AND ");

			sb.append(sql.substring(pos));
		}

		return sb.toString();
	}

	private static final String _GROUP_BY_CLAUSE = " GROUP BY ";

	private static final String _ORDER_BY_CLAUSE = " ORDER BY ";

	private static final String _WHERE_CLAUSE = " WHERE ";

	private static final Log _log = LogFactoryUtil.getLog(
		InlineSQLHelperImpl.class);

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private GroupLocalService _groupLocalService;

	private volatile InlinePermissionConfiguration
		_inlinePermissionConfiguration;
	private ServiceTrackerMap<String, List<PermissionSQLContributor>>
		_permissionSQLContributors;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}