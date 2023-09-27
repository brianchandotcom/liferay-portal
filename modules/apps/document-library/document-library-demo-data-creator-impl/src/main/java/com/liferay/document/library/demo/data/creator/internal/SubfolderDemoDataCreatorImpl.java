/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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