/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.security.permission.contributor;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.security.permission.contributor.PermissionSQLContributor;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.model.SharingEntryTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Extends inline permission SQL queries to also consider sharing entries when
 * returning results.
 *
 * @author Alejandro Tardín
 * @review
 */
public class SharingPermissionSQLContributor
	implements PermissionSQLContributor {

	public SharingPermissionSQLContributor(
		ClassNameLocalService classNameLocalService,
		GroupLocalService groupLocalService,
		SharingConfigurationFactory sharingConfigurationFactory) {

		_classNameLocalService = classNameLocalService;
		_groupLocalService = groupLocalService;
		_sharingConfigurationFactory = sharingConfigurationFactory;
	}

	@Override
	public Predicate getPermissionPredicate(
		PermissionChecker permissionChecker, String className,
		Column<?, Long> classPKColumn, long[] groupIds) {

		SharingConfiguration sharingConfiguration =
			_sharingConfigurationFactory.getSystemSharingConfiguration();

		if (!sharingConfiguration.isEnabled()) {
			return null;
		}

		List<Long> disableGroupIds = new ArrayList<>();

		if (groupIds != null) {
			for (long groupId : groupIds) {
				if (groupId == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
					continue;
				}

				SharingConfiguration groupSharingConfiguration =
					_getSharingConfiguration(groupId);

				if (!groupSharingConfiguration.isEnabled()) {
					disableGroupIds.add(groupId);
				}
			}

			if (disableGroupIds.size() == groupIds.length) {
				return null;
			}
		}

		return classPKColumn.in(
			DSLQueryFactoryUtil.select(
				SharingEntryTable.INSTANCE.classPK
			).from(
				SharingEntryTable.INSTANCE
			).where(
				() -> {
					Predicate predicate =
						SharingEntryTable.INSTANCE.toUserId.eq(
							permissionChecker.getUserId()
						).and(
							SharingEntryTable.INSTANCE.classNameId.eq(
								_classNameLocalService.getClassNameId(
									className))
						);

					if (disableGroupIds.isEmpty()) {
						return predicate;
					}

					return predicate.and(
						SharingEntryTable.INSTANCE.groupId.notIn(
							disableGroupIds.toArray(new Long[0])));
				}
			));
	}

	@Override
	public String getPermissionSQL(
		String className, String classPKField, String userIdField,
		String groupIdField, long[] groupIds) {

		SharingConfiguration sharingConfiguration =
			_sharingConfigurationFactory.getSystemSharingConfiguration();

		if (!sharingConfiguration.isEnabled()) {
			return StringPool.BLANK;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		StringBundler sb = new StringBundler(7);

		sb.append(classPKField);
		sb.append(" IN (SELECT SharingEntry.classPK FROM SharingEntry WHERE ");

		_addDisabledGroupsSQL(sb, groupIds);

		sb.append("(SharingEntry.toUserId = ");
		sb.append(permissionChecker.getUserId());
		sb.append(") AND (SharingEntry.classNameId = ");
		sb.append(_classNameLocalService.getClassNameId(className));
		sb.append("))");

		return sb.toString();
	}

	private void _addDisabledGroupsSQL(StringBundler sb, long[] groupIds) {
		if ((groupIds == null) || (groupIds.length == 0)) {
			return;
		}

		int groupCount = 0;

		for (long groupId : groupIds) {
			if (groupId == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
				continue;
			}

			SharingConfiguration sharingConfiguration =
				_getSharingConfiguration(groupId);

			if (!sharingConfiguration.isEnabled()) {
				if (groupCount == 0) {
					sb.append("(SharingEntry.groupId NOT IN (");
				}
				else {
					sb.append(StringPool.COMMA_AND_SPACE);
				}

				sb.append(groupId);

				groupCount++;
			}
		}

		if (groupCount > 0) {
			sb.append(")) AND");
		}
	}

	private SharingConfiguration _getSharingConfiguration(long groupId) {
		try {
			return _sharingConfigurationFactory.getGroupSharingConfiguration(
				_groupLocalService.getGroup(groupId));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final GroupLocalService _groupLocalService;
	private final SharingConfigurationFactory _sharingConfigurationFactory;

}