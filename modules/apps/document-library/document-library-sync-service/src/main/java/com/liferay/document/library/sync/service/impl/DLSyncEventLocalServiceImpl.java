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

package com.liferay.document.library.sync.service.impl;

import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.document.library.sync.service.base.DLSyncEventLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Dennis Ju
 */
@Component(
	property = "model.class.name=com.liferay.document.library.sync.model.DLSyncEvent",
	service = AopService.class
)
public class DLSyncEventLocalServiceImpl
	extends DLSyncEventLocalServiceBaseImpl {

	@Override
	public DLSyncEvent addDLSyncEvent(String event, String type, long typePK) {
		DLSyncEvent dlSyncEvent = dlSyncEventPersistence.fetchByTypePK(typePK);

		if (dlSyncEvent == null) {
			long dlSyncEventId = counterLocalService.increment();

			dlSyncEvent = dlSyncEventPersistence.create(dlSyncEventId);

			dlSyncEvent.setType(type);
			dlSyncEvent.setTypePK(typePK);
		}

		dlSyncEvent.setModifiedTime(System.currentTimeMillis());
		dlSyncEvent.setEvent(event);

		return dlSyncEventPersistence.update(dlSyncEvent);
	}

	@Override
	public void deleteDLSyncEvents() {
		dlSyncEventPersistence.removeAll();
	}

	@Override
	public List<DLSyncEvent> getDLSyncEvents(long modifiedTime) {
		return dlSyncEventPersistence.findByModifiedTime(modifiedTime);
	}

	@Override
	public List<DLSyncEvent> getLatestDLSyncEvents() {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLSyncEvent.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName("modifiedTime");

		DynamicQuery modifiedTimeDynamicQuery =
			DynamicQueryFactoryUtil.forClass(
				DLSyncEvent.class, getClassLoader());

		Projection projection = ProjectionFactoryUtil.max("modifiedTime");

		modifiedTimeDynamicQuery.setProjection(projection);

		dynamicQuery.add(property.eq(modifiedTimeDynamicQuery));

		return dlSyncEventPersistence.findWithDynamicQuery(dynamicQuery);
	}

}