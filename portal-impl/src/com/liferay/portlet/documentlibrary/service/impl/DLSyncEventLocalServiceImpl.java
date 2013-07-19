/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.service.base.DLSyncEventLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Dennis Ju
 */
public class DLSyncEventLocalServiceImpl
	extends DLSyncEventLocalServiceBaseImpl {

	@Override
	public DLSyncEvent addOrUpdateDLSyncEvent(
			long typeId, String type, String event, long modifiedDate)
		throws PortalException, SystemException {

		DLSyncEvent dlSyncEvent = dlSyncEventPersistence.fetchByTypeId(typeId);

		if (dlSyncEvent == null) {
			long dlSyncEventId = counterLocalService.increment();

			dlSyncEvent = dlSyncEventPersistence.create(dlSyncEventId);

			dlSyncEvent.setTypeId(typeId);
			dlSyncEvent.setType(type);
		}

		dlSyncEvent.setModifiedDate(modifiedDate);
		dlSyncEvent.setEvent(event);

		return dlSyncEventPersistence.update(dlSyncEvent);
	}

	@Override
	public List<DLSyncEvent> getDLSyncEvents(long modifiedDate)
		throws PortalException, SystemException {

		return dlSyncEventPersistence.findByModifiedDate(modifiedDate);
	}

	@Override
	public void removeAll() throws SystemException {
		dlSyncEventPersistence.removeAll();
	}

}