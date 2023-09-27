/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate.event;

import com.liferay.portal.kernel.model.BaseModel;

import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultLoadEventListener;
import org.hibernate.event.spi.LoadEvent;

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