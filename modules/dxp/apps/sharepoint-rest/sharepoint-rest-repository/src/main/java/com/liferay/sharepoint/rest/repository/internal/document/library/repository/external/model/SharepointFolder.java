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
public class SharepointFolder implements ExtRepositoryFolder {

	public SharepointFolder(
		String extRepositoryModelKey, String name, Date createDate,
		Date modifiedDate, long effectiveBasePermissionsBits) {

		_extRepositoryModelKey = extRepositoryModelKey;
		_name = name;
		_createDate = createDate;
		_modifiedDate = modifiedDate;
		_effectiveBasePermissionsBits = effectiveBasePermissionsBits;
	}

	@Override
	public boolean containsPermission(
		ExtRepositoryPermission extRepositoryPermission) {

		if ((extRepositoryPermission ==
				ExtRepositoryPermission.ADD_DISCUSSION) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.DELETE_DISCUSSION) ||
			(extRepositoryPermission == ExtRepositoryPermission.PERMISSIONS) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.UPDATE_DISCUSSION)) {

			return false;
		}

		int bitIndex = 0;

		if ((extRepositoryPermission == ExtRepositoryPermission.ACCESS) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.ADD_DOCUMENT) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.ADD_FOLDER) ||
			(extRepositoryPermission ==
				ExtRepositoryPermission.ADD_SUBFOLDER) ||
			(extRepositoryPermission == ExtRepositoryPermission.VIEW)) {

			bitIndex = 1;
		}
		else if (extRepositoryPermission == ExtRepositoryPermission.DELETE) {
			bitIndex = 4;
		}
		else if (extRepositoryPermission == ExtRepositoryPermission.UPDATE) {
			bitIndex = 3;
		}

		if (bitIndex == 0) {
			return false;
		}

		if ((_effectiveBasePermissionsBits & (1 << (bitIndex - 1))) == 0) {
			return false;
		}

		return true;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
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
		return _modifiedDate;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getOwner() {
		return StringPool.BLANK;
	}

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public boolean isRoot() {
		return false;
	}

	private final Date _createDate;
	private final long _effectiveBasePermissionsBits;
	private final String _extRepositoryModelKey;
	private final Date _modifiedDate;
	private final String _name;

}