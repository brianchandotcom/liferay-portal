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
import com.liferay.portal.kernel.model.BaseModel;

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

			return new ORMException(
				object + " is stale in comparison to " + currentObject, e);
		}

		return new ORMException(e);
	}

}