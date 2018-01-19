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

package com.liferay.sharepoint.repository.model;

import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sharepoint.connector.SharepointObject;

/**
 * @author Iván Zaera
 */
public class SharepointWSFolder
	extends SharepointWSObject implements ExtRepositoryFolder {

	public SharepointWSFolder(SharepointObject sharepointObject) {
		super(sharepointObject);
	}

	@Override
	public String getName() {
		return sharepointObject.getName();
	}

	@Override
	public boolean isRoot() {
		String path = sharepointObject.getPath();

		return path.equals(StringPool.SLASH);
	}

}