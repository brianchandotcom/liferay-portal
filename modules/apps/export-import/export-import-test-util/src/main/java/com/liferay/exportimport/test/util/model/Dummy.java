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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Akos Thurzo
 */
@ImplementationClassName("com.liferay.exportimport.test.util.model.Dummy")
public class Dummy implements Serializable, StagedGroupedModel {

	public Dummy() {
	}

	public Dummy(
		long companyId, long groupId, long folderId, User user,
		List<DummyReference> dummyReferences) {

		_companyId = companyId;
		_groupId = groupId;
		_folderId = folderId;
		_dummyReferences = dummyReferences;

		_userId = user.getUserId();
		_userName = user.getScreenName();
		_userUuid = user.getUserUuid();
	}

	@Override
	public Object clone() {
		Dummy dummy = new Dummy();

		dummy.setCompanyId(_companyId);
		dummy.setCreateDate(_createDate);
		dummy.setDummyReferences(new ArrayList<>(_dummyReferences));
		dummy.setFolderId(_folderId);
		dummy.setGroupId(_groupId);
		dummy.setLastPublishDate(_lastPublishDate);
		dummy.setModifiedDate(_modifiedDate);
		dummy.setPrimaryKeyObj(_id);
		dummy.setUserId(_userId);
		dummy.setUserName(_userName);
		dummy.setUserUuid(_userUuid);
		dummy.setUuid(_uuid);

		return dummy;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Dummy)) {
			return false;
		}

		Dummy dummy = (Dummy)object;

		long primaryKey = dummy.getPrimaryKey();

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

	public List<DummyReference> getDummyReferences() {
		return _dummyReferences;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Dummy.class.getName(), getPrimaryKey());
	}

	public long getFolderId() {
		return _folderId;
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
		return Dummy.class;
	}

	@Override
	public String getModelClassName() {
		return Dummy.class.getName();
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
		return new StagedModelType(Dummy.class);
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

	public void setDummyReferences(List<DummyReference> dummyReferences) {
		_dummyReferences = dummyReferences;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
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
	private List<DummyReference> _dummyReferences = new ArrayList<>();
	private long _folderId;
	private long _groupId;
	private long _id = CounterLocalServiceUtil.increment();
	private Date _lastPublishDate;
	private Date _modifiedDate = new Date();
	private long _userId;
	private String _userName;
	private String _userUuid;
	private String _uuid = PortalUUIDUtil.generate();

}