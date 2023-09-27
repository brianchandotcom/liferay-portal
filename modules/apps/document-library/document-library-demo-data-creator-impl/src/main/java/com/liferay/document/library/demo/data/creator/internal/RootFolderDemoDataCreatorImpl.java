/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.demo.data.creator.internal;

import com.liferay.document.library.demo.data.creator.RootFolderDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(service = RootFolderDemoDataCreator.class)
public class RootFolderDemoDataCreatorImpl
	extends BaseFolderDemoDataCreatorImpl implements RootFolderDemoDataCreator {

	@Override
	public Folder create(long userId, long groupId) throws PortalException {
		return create(userId, groupId, "Demo");
	}

	@Override
	public Folder create(long userId, long groupId, String name)
		throws PortalException {

		return createFolder(userId, groupId, 0, name);
	}

}