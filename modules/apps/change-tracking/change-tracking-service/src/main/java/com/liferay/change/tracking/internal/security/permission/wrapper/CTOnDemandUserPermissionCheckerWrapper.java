/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.security.permission.wrapper;

import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.wrapper.PermissionCheckerWrapper;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Pei-Jung Lan
 */
public class CTOnDemandUserPermissionCheckerWrapper
	extends PermissionCheckerWrapper {

	public CTOnDemandUserPermissionCheckerWrapper(
		PermissionChecker permissionChecker,
		ClassNameLocalService classNameLocalService,
		CTCollectionLocalService ctCollectionLocalService,
		CTEntryLocalService ctEntryLocalService) {

		super(permissionChecker);

		_permissionChecker = permissionChecker;
		_classNameLocalService = classNameLocalService;
		_ctCollectionLocalService = ctCollectionLocalService;
		_ctEntryLocalService = ctEntryLocalService;
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		return _hasPermission(
			name, primKey, actionId,
			() -> super.hasPermission(group, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return _hasPermission(
			name, GetterUtil.getLong(primKey), actionId,
			() -> super.hasPermission(group, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return _hasPermission(
			name, primKey, actionId,
			() -> super.hasPermission(groupId, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return _hasPermission(
			name, GetterUtil.getLong(primKey), actionId,
			() -> super.hasPermission(groupId, name, primKey, actionId));
	}

	private long _getCTCollectionId() {
		List<Long> ctCollectionIds = _ctCollectionLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				CTCollectionTable.INSTANCE.ctCollectionId
			).from(
				CTCollectionTable.INSTANCE
			).where(
				CTCollectionTable.INSTANCE.onDemandUserId.eq(
					_permissionChecker.getUserId())
			));

		if (ctCollectionIds.isEmpty()) {
			return -1;
		}

		return ctCollectionIds.get(0);
	}

	private boolean _hasPermission(
		String name, long primKey, String actionId,
		Supplier<Boolean> hasPermissionSupplier) {

		if (!StringUtil.equals(name, Layout.class.getName()) ||
			!StringUtil.equals(actionId, ActionKeys.VIEW)) {

			return hasPermissionSupplier.get();
		}

		long ctCollectionId = _getCTCollectionId();

		if ((ctCollectionId > 0) &&
			_ctEntryLocalService.hasCTEntry(
				ctCollectionId,
				_classNameLocalService.getClassNameId(Layout.class), primKey)) {

			return true;
		}

		return hasPermissionSupplier.get();
	}

	private final ClassNameLocalService _classNameLocalService;
	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTEntryLocalService _ctEntryLocalService;
	private final PermissionChecker _permissionChecker;

}