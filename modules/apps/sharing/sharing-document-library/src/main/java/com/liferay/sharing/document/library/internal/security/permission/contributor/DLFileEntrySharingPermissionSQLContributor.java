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

package com.liferay.sharing.document.library.internal.security.permission.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.security.permission.contributor.PermissionSQLContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Extends inline permission sql query to also consider sharing entries when
 * returning results for
 * {@link com.liferay.document.library.kernel.model.DLFileEntry}.
 *
 * @author Sergio González
 * @review
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = PermissionSQLContributor.class
)
public class DLFileEntrySharingPermissionSQLContributor
	implements PermissionSQLContributor {

	@Override
	public String getPermissionSQL(
		String className, String classPKField, String userIdField,
		String groupIdField, long[] groupIds) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		StringBundler sb = new StringBundler(7);

		sb.append(classPKField);
		sb.append(" IN (SELECT SharingEntry.classPK FROM SharingEntry WHERE ");
		sb.append("SharingEntry.toUserId = ");
		sb.append(permissionChecker.getUserId());
		sb.append(" AND SharingEntry.classNameId = ");
		sb.append(_classNameLocalService.getClassNameId(className));
		sb.append(")");

		return sb.toString();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

}