/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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