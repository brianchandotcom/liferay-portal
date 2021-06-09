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

import org.hibernate.event.internal.DefaultPostLoadEventListener;
import org.hibernate.event.spi.PostLoadEvent;

/**
 * @author Tina Tian
 */
public class ResetOriginalValuesPostLoadEventListener
	extends DefaultPostLoadEventListener {

	public static final ResetOriginalValuesPostLoadEventListener INSTANCE =
		new ResetOriginalValuesPostLoadEventListener();

	@Override
	public void onPostLoad(PostLoadEvent postLoadEvent) {
		super.onPostLoad(postLoadEvent);

		Object entity = postLoadEvent.getEntity();

		if (entity instanceof BaseModel) {
			BaseModel<?> baseModel = (BaseModel<?>)entity;

			baseModel.resetOriginalValues();
		}
	}

}