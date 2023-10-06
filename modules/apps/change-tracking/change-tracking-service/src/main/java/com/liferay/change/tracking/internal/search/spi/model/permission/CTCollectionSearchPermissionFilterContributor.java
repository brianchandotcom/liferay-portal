/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.permission;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRoleTable;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.permission.contributor.SearchPermissionFilterContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = SearchPermissionFilterContributor.class)
public class CTCollectionSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.equals(CTCollection.class.getName())) {
			return;
		}

		try {
			_addCTCollectionIdsFilter(
				booleanFilter, companyId, userId, permissionChecker);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private void _addCTCollectionIdsFilter(
			BooleanFilter booleanFilter, long companyId, long userId,
			PermissionChecker permissionChecker)
		throws PortalException {

		List<Long> ctCollectionIds = _ctCollectionLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				CTCollectionTable.INSTANCE.ctCollectionId
			).from(
				CTCollectionTable.INSTANCE
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.classNameId.eq(
					_classNameLocalService.getClassNameId(CTCollection.class)
				).and(
					GroupTable.INSTANCE.classPK.eq(
						CTCollectionTable.INSTANCE.ctCollectionId)
				)
			).innerJoinON(
				UserGroupRoleTable.INSTANCE,
				UserGroupRoleTable.INSTANCE.groupId.eq(
					GroupTable.INSTANCE.groupId
				).and(
					UserGroupRoleTable.INSTANCE.roleId.in(
						TransformUtil.transformToArray(
							_roleLocalService.getRoles(
								companyId,
								new int[] {RoleConstants.TYPE_PUBLICATIONS}),
							Role::getRoleId, Long.class))
				).and(
					UserGroupRoleTable.INSTANCE.userId.eq(userId)
				)
			));

		ctCollectionIds = ListUtil.filter(
			ctCollectionIds,
			ctCollectionId -> {
				try {
					if (_ctCollectionModelResourcePermission.contains(
							permissionChecker, ctCollectionId,
							ActionKeys.VIEW)) {

						return true;
					}

					return false;
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}

					return false;
				}
			});

		TermsFilter classPksFilter = new TermsFilter(Field.ENTRY_CLASS_PK);

		if (!ctCollectionIds.isEmpty()) {
			classPksFilter.addValues(ArrayUtil.toStringArray(ctCollectionIds));
		}

		if (!classPksFilter.isEmpty()) {
			booleanFilter.add(classPksFilter, BooleanClauseOccur.SHOULD);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTCollectionSearchPermissionFilterContributor.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Reference
	private RoleLocalService _roleLocalService;

}