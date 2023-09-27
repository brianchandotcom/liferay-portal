/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util.model;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Akos Thurzo
 */
@ImplementationClassName("com.liferay.exportimport.test.util.model.DummyFolder")
public class DummyFolder implements Serializable, StagedGroupedModel {

	public DummyFolder() {
	}

	public DummyFolder(long companyId, long groupId, User user) {
		_companyId = companyId;
		_groupId = groupId;

		_userId = user.getUserId();
		_userName = user.getScreenName();
		_userUuid = user.getUserUuid();
	}

	@Override
	public Object clone() {
		DummyFolder dummyFolder = new DummyFolder();

		dummyFolder.setCompanyId(_companyId);
		dummyFolder.setCreateDate(_createDate);
		dummyFolder.setGroupId(_groupId);
		dummyFolder.setLastPublishDate(_lastPublishDate);
		dummyFolder.setModifiedDate(_modifiedDate);
		dummyFolder.setPrimaryKeyObj(_id);
		dummyFolder.setUserId(_userId);
		dummyFolder.setUserName(_userName);
		dummyFolder.setUserUuid(_userUuid);
		dummyFolder.setUuid(_uuid);

		return dummyFolder;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DummyFolder)) {
			return false;
		}

		DummyFolder dummyFolder = (DummyFolder)object;

		long primaryKey = dummyFolder.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}

		return false;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), DummyFolder.class.getName(), getPrimaryKey());
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	public long getId() {
		return _id;
	}

	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public Class<?> getModelClass() {
		return DummyFolder.class;
	}

	@Override
	public String getModelClassName() {
		return DummyFolder.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public long getPrimaryKey() {
		return _id;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _id;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(DummyFolder.class);
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public String getUserUuid() {
		return _userUuid;
	}

	@Override
	public String getUuid() {
		return _uuid;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setId(long id) {
		_id = id;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setPrimaryKey(long primaryKey) {
		_id = primaryKey;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_id = (long)primaryKeyObj;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@Override
	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private long _companyId;
	private Date _createDate = new Date();
	private long _groupId;
	private long _id = CounterLocalServiceUtil.increment();
	private Date _lastPublishDate;
	private Date _modifiedDate = new Date();
	private long _userId;
	private String _userName;
	private String _userUuid;
	private String _uuid = PortalUUIDUtil.generate();

}