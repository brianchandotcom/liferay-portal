/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.model.PermissionPropagation;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.service.base.PermissionPropagationLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author To Trinh
 * @author Quan Huynh
 */
public class PermissionPropagationLocalServiceImpl
	extends PermissionPropagationLocalServiceBaseImpl {

	public PermissionPropagation addPermissionPropagation(
		long companyId, long groupId, String className, long classPK,
		boolean propagate) {

		long permissionPropagationId = counterLocalService.increment();

		PermissionPropagation permissionPropagation =
			permissionPropagationPersistence.create(permissionPropagationId);

		permissionPropagation.setGroupId(groupId);
		permissionPropagation.setCompanyId(companyId);
		permissionPropagation.setClassNameId(
			_classNameLocalService.getClassNameId(className));
		permissionPropagation.setClassPK(classPK);
		permissionPropagation.setPropagate(propagate);

		return permissionPropagationPersistence.update(permissionPropagation);
	}

	public PermissionPropagation fetchPermissionPropagation(
		long companyId, long groupId, String className, long classPK) {

		return permissionPropagationPersistence.fetchByG_C_C_C(
			groupId, companyId,
			_classNameLocalService.getClassNameId(className), classPK);
	}

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

}