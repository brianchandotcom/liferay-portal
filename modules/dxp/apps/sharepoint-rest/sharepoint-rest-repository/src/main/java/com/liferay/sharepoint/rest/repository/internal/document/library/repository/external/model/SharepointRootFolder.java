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

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external.model;

import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SharepointRootFolder implements ExtRepositoryFolder {

	public SharepointRootFolder(String extRepositoryModelKey) {
		_extRepositoryModelKey = extRepositoryModelKey;
	}

	@Override
	public boolean containsPermission(
		ExtRepositoryPermission extRepositoryPermission) {

		return true;
	}

	@Override
	public Date getCreateDate() {
		return null;
	}

	@Override
	public String getDescription() {
		return StringPool.BLANK;
	}

	@Override
	public String getExtension() {
		return StringPool.BLANK;
	}

	@Override
	public String getExtRepositoryModelKey() {
		return _extRepositoryModelKey;
	}

	@Override
	public Date getModifiedDate() {
		return null;
	}

	@Override
	public String getName() {
		return StringPool.BLANK;
	}

	@Override
	public String getOwner() {
		return null;
	}

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public boolean isRoot() {
		return true;
	}

	private final String _extRepositoryModelKey;

}