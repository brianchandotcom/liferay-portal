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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;

/**
 * @author Brian Wing Shun Chan
 */
public class ExceptionTranslator {

	public static ORMException translate(Exception exception) {
		if (exception instanceof org.hibernate.ObjectNotFoundException) {
			return new ObjectNotFoundException(exception);
		}

		return new ORMException(exception);
	}

	public static ORMException translate(
		Exception exception, Session session, Object object) {

		if (exception instanceof StaleObjectStateException) {
			BaseModel<?> baseModel = (BaseModel<?>)object;

			Object currentObject = session.get(
				object.getClass(), baseModel.getPrimaryKeyObj());

			BaseModel<?> currentObjectBaseModel = (BaseModel<?>)currentObject;

			currentObject = currentObjectBaseModel.clone();

			object = baseModel.clone();

			JSONSerializer jsonSerializer =
				JSONFactoryUtil.createJSONSerializer();

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			try {
				PermissionThreadLocal.setPermissionChecker(null);

				return new ORMException(
					StringBundler.concat(
						jsonSerializer.serialize(object),
						" is stale in comparison to ",
						jsonSerializer.serialize(currentObject)),
					exception);
			}
			finally {
				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}
		}

		return new ORMException(exception);
	}

}