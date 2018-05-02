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

package com.liferay.document.library.demo.data.creator.internal;

import com.liferay.document.library.demo.data.creator.SubfolderDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(service = SubfolderDemoDataCreator.class)
public class SubfolderDemoDataCreatorImpl
	extends BaseFolderDemoDataCreatorImpl implements SubfolderDemoDataCreator {

	@Override
	public Folder create(long userId, long folderId) throws PortalException {
		return create(userId, folderId, "Demo");
	}

	@Override
	public Folder create(long userId, long folderId, String name)
		throws PortalException {

		Folder folder = dlAppLocalService.getFolder(folderId);

		return createFolder(userId, folder.getGroupId(), folderId, name);
	}

}