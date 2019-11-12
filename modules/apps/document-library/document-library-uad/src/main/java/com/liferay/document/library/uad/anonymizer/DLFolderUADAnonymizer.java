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

package com.liferay.document.library.uad.anonymizer;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = UADAnonymizer.class)
public class DLFolderUADAnonymizer extends BaseDLFolderUADAnonymizer {

	@Override
	public void autoAnonymize(
			DLFolder dlFolder, long userId, User anonymousUser)
		throws PortalException {

		dlFolder = dlFolderLocalService.getDLFolder(dlFolder.getFolderId());

		super.autoAnonymize(dlFolder, userId, anonymousUser);
	}

	@Override
	public void delete(DLFolder dlFolder) throws PortalException {
		if (dlFolderLocalService.fetchDLFolder(dlFolder.getFolderId()) !=
				null) {

			super.delete(dlFolder);
		}
	}

}