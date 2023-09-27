/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.model;

import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;

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