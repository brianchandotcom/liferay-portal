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

package com.liferay.documentum.repository.model;

import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfTime;

import com.liferay.document.library.repository.external.ExtRepositoryModel;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public abstract class DocumentumObject
	implements ExtRepositoryObject, ExtRepositoryModel {

	public DocumentumObject(IDfSysObject idfSysObject) {
		_idfSysObject = idfSysObject;
	}

	@Override
	public boolean containsPermission(
		ExtRepositoryPermission extRepositoryPermission) {

		if (_unsupportedExtRepositoryPermissions.contains(
				extRepositoryPermission)) {

			return false;
		}

		try {
			int permit = _permits.get(extRepositoryPermission);

			if (_idfSysObject.getPermit() >= permit) {
				return true;
			}

			return false;
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public Date getCreateDate() {
		try {
			IDfTime idfTime = _idfSysObject.getCreationDate();

			return idfTime.getDate();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public String getDescription() {
		return StringPool.BLANK;
	}

	@Override
	public String getExtension() {
		try {
			return FileUtil.getExtension(_idfSysObject.getObjectName());
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public String getExtRepositoryModelKey() {
		try {
			IDfId idfId = _idfSysObject.getObjectId();

			return idfId.getId();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	public IDfSysObject getIDfSysObject() {
		return _idfSysObject;
	}

	@Override
	public Date getModifiedDate() {
		try {
			IDfTime idfTime = _idfSysObject.getModifyDate();

			return idfTime.getDate();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public String getOwner() {
		try {
			return _idfSysObject.getOwnerName();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public long getSize() {
		try {
			return _idfSysObject.getContentSize();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	private static final Map<ExtRepositoryPermission, Integer> _permits =
		new HashMap<>();
	private static final Set<ExtRepositoryPermission>
		_unsupportedExtRepositoryPermissions = EnumSet.of(
			ExtRepositoryPermission.ADD_DISCUSSION,
			ExtRepositoryPermission.ADD_SHORTCUT,
			ExtRepositoryPermission.DELETE_DISCUSSION,
			ExtRepositoryPermission.PERMISSIONS,
			ExtRepositoryPermission.UPDATE_DISCUSSION);

	static {
		_permits.put(
			ExtRepositoryPermission.ACCESS, Constants.DF_PERMIT_BROWSE);
		_permits.put(
			ExtRepositoryPermission.ADD_DOCUMENT, Constants.DF_PERMIT_WRITE);
		_permits.put(
			ExtRepositoryPermission.ADD_FOLDER, Constants.DF_PERMIT_WRITE);
		_permits.put(
			ExtRepositoryPermission.ADD_SUBFOLDER, Constants.DF_PERMIT_WRITE);
		_permits.put(
			ExtRepositoryPermission.DELETE, Constants.DF_PERMIT_DELETE);
		_permits.put(ExtRepositoryPermission.UPDATE, Constants.DF_PERMIT_WRITE);
		_permits.put(ExtRepositoryPermission.VIEW, Constants.DF_PERMIT_READ);
	}

	private final IDfSysObject _idfSysObject;

}