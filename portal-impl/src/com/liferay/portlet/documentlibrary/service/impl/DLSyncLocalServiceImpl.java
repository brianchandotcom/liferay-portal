/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portlet.documentlibrary.model.DLSync;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.base.DLSyncLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Michael Young
 */
public class DLSyncLocalServiceImpl extends DLSyncLocalServiceBaseImpl {

	public DLSync addSync(
			String fileId, long companyId, long repositoryId,
			String type)
		throws PortalException, SystemException {

		DLSync dlSync = dlSyncPersistence.create(fileId);

		dlSync.setCompanyId(companyId);
		dlSync.setModifiedDate(new Date());
		dlSync.setRepositoryId(repositoryId);
		dlSync.setEvent(DLSyncConstants.ADD);
		dlSync.setType(type);

		dlSyncPersistence.update(dlSync, false);

		return dlSync;
	}

	public DLSync updateSync(String fileId, String event)
		throws PortalException, SystemException {

		DLSync dlSync = dlSyncPersistence.findByPrimaryKey(fileId);

		dlSync.setModifiedDate(new Date());
		dlSync.setEvent(event);

		dlSyncPersistence.update(dlSync, false);

		return dlSync;
	}

}