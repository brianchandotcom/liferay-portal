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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;

/**
 * @author Brian Wing Shun Chan
 */
public class ExceptionTranslator {

	public static ORMException translate(Exception e) {
		if (e instanceof org.hibernate.ObjectNotFoundException) {
			return new ObjectNotFoundException(e);
		}

		return new ORMException(e);
	}

	public static ORMException translate(
		Exception e, Session session, Object object) {

		if (e instanceof StaleObjectStateException) {
			BaseModel<?> baseModel = (BaseModel<?>)object;

			Object currentObject = session.get(
				object.getClass(), baseModel.getPrimaryKeyObj());

			try {
				User admin = _getAdmin();

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(admin);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);

				JSONSerializer jsonSerializer =
					JSONFactoryUtil.createJSONSerializer();

				String objStr = jsonSerializer.serialize(object);
				String currObjStr = jsonSerializer.serialize(currentObject);

				return new ORMException(
					objStr + " is stale in comparison to " + currObjStr, e);
			}
			catch (Exception e1) {
				return new ORMException(e1);
			}
		}

		return new ORMException(e);
	}

	private static User _getAdmin() throws Exception {
		final long companyId = PortalUtil.getDefaultCompanyId();
		Role role = null;

		role = RoleLocalServiceUtil.getRole(companyId,
			RoleConstants.ADMINISTRATOR);
		long roleId = role.getRoleId();

		for (User admin : UserLocalServiceUtil.getRoleUsers(roleId)) {
			return admin;
		}

		return null;
	}

}