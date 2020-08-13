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

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.GroupServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class GroupSoap implements Serializable {

	public static GroupSoap toSoapModel(Group model) {
		GroupSoap soapModel = new GroupSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setUuid(model.getUuid());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreatorUserId(model.getCreatorUserId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setParentGroupId(model.getParentGroupId());
		soapModel.setLiveGroupId(model.getLiveGroupId());
		soapModel.setTreePath(model.getTreePath());
		soapModel.setGroupKey(model.getGroupKey());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setManualMembership(model.isManualMembership());
		soapModel.setMembershipRestriction(model.getMembershipRestriction());
		soapModel.setFriendlyURL(model.getFriendlyURL());
		soapModel.setSite(model.isSite());
		soapModel.setRemoteStagingGroupCount(
			model.getRemoteStagingGroupCount());
		soapModel.setInheritContent(model.isInheritContent());
		soapModel.setActive(model.isActive());

		return soapModel;
	}

	public static GroupSoap[] toSoapModels(Group[] models) {
		GroupSoap[] soapModels = new GroupSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static GroupSoap[][] toSoapModels(Group[][] models) {
		GroupSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new GroupSoap[models.length][models[0].length];
		}
		else {
			soapModels = new GroupSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static GroupSoap[] toSoapModels(List<Group> models) {
		List<GroupSoap> soapModels = new ArrayList<GroupSoap>(models.size());

		for (Group model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new GroupSoap[soapModels.size()]);
	}

	public GroupSoap() {
	}

	public long getPrimaryKey() {
		return _groupId;
	}

	public void setPrimaryKey(long pk) {
		setGroupId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public void setCreatorUserId(long creatorUserId) {
		_creatorUserId = creatorUserId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getParentGroupId() {
		return _parentGroupId;
	}

	public void setParentGroupId(long parentGroupId) {
		_parentGroupId = parentGroupId;
	}

	public long getLiveGroupId() {
		return _liveGroupId;
	}

	public void setLiveGroupId(long liveGroupId) {
		_liveGroupId = liveGroupId;
	}

	public String getTreePath() {
		return _treePath;
	}

	public void setTreePath(String treePath) {
		_treePath = treePath;
	}

	public String getGroupKey() {
		return _groupKey;
	}

	public void setGroupKey(String groupKey) {
		_groupKey = groupKey;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public boolean getManualMembership() {
		return _manualMembership;
	}

	public boolean isManualMembership() {
		return _manualMembership;
	}

	public void setManualMembership(boolean manualMembership) {
		_manualMembership = manualMembership;
	}

	public int getMembershipRestriction() {
		return _membershipRestriction;
	}

	public void setMembershipRestriction(int membershipRestriction) {
		_membershipRestriction = membershipRestriction;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public boolean getSite() {
		return _site;
	}

	public boolean isSite() {
		return _site;
	}

	public void setSite(boolean site) {
		_site = site;
	}

	public int getRemoteStagingGroupCount() {
		return _remoteStagingGroupCount;
	}

	public void setRemoteStagingGroupCount(int remoteStagingGroupCount) {
		_remoteStagingGroupCount = remoteStagingGroupCount;
	}

	public boolean getInheritContent() {
		return _inheritContent;
	}

	public boolean isInheritContent() {
		return _inheritContent;
	}

	public void setInheritContent(boolean inheritContent) {
		_inheritContent = inheritContent;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _groupId;
	private long _companyId;
	private long _creatorUserId;
	private long _classNameId;
	private long _classPK;
	private long _parentGroupId;
	private long _liveGroupId;
	private String _treePath;
	private String _groupKey;
	private String _name;
	private String _description;
	private int _type;
	private String _typeSettings;
	private boolean _manualMembership;
	private int _membershipRestriction;
	private String _friendlyURL;
	private boolean _site;
	private int _remoteStagingGroupCount;
	private boolean _inheritContent;
	private boolean _active;

}