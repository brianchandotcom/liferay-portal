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

package com.liferay.portal.dao.orm.hibernate.event;

import com.liferay.portal.kernel.model.BaseModel;

import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.event.LoadEvent;
import org.hibernate.event.def.DefaultLoadEventListener;

/**
 * @author Tina Tian
 */
public class ResetOriginalValuesLoadEventListener
	extends DefaultLoadEventListener {

	public static final ResetOriginalValuesLoadEventListener INSTANCE =
		new ResetOriginalValuesLoadEventListener();

	@Override
	public void onLoad(LoadEvent loadEvent, LoadType loadType)
		throws HibernateException {

		String entityClassName = loadEvent.getEntityClassName();

		if (entityClassName.endsWith("BlobModel") &&
			Objects.equals(loadType.getName(), "INTERNAL_LOAD_NULLABLE")) {

			return;
		}

		super.onLoad(loadEvent, loadType);

		Object result = loadEvent.getResult();

		if (result instanceof BaseModel) {
			BaseModel<?> baseModel = (BaseModel<?>)result;

			baseModel.resetOriginalValues();
		}
	}

}