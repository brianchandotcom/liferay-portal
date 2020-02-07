/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository.model;

import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.petra.string.StringPool;
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