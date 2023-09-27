/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class DXPGroup {

	public long getClassNameId() {
		return _classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public String getDescription() {
		return _description;
	}

	public String getDescriptionCurrentValue() {
		return _descriptionCurrentValue;
	}

	public String getDescriptiveName() {
		return _descriptiveName;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getGroupKey() {
		return _groupKey;
	}

	public String getId() {
		return _id;
	}

	public long getLiveGroupId() {
		return _liveGroupId;
	}

	public int getMembershipRestriction() {
		return _membershipRestriction;
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public String getName() {
		return _name;
	}

	public String getNameCurrentValue() {
		return _nameCurrentValue;
	}

	public long getParentGroupId() {
		return _parentGroupId;
	}

	public long getRemoteStagingGroupCount() {
		return _remoteStagingGroupCount;
	}

	public String getTreePath() {
		return _treePath;
	}

	public String getType() {
		return _type;
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isInheritContent() {
		return _inheritContent;
	}

	public boolean isManualMembership() {
		return _manualMembership;
	}

	public boolean isSite() {
		return _site;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreatorUserId(long creatorUserId) {
		_creatorUserId = creatorUserId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDescriptionCurrentValue(String descriptionCurrentValue) {
		_descriptionCurrentValue = descriptionCurrentValue;
	}

	public void setDescriptiveName(String descriptiveName) {
		_descriptiveName = descriptiveName;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setGroupKey(String groupKey) {
		_groupKey = groupKey;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setInheritContent(boolean inheritContent) {
		_inheritContent = inheritContent;
	}

	public void setLiveGroupId(long liveGroupId) {
		_liveGroupId = liveGroupId;
	}

	public void setManualMembership(boolean manualMembership) {
		_manualMembership = manualMembership;
	}

	public void setMembershipRestriction(int membershipRestriction) {
		_membershipRestriction = membershipRestriction;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNameCurrentValue(String nameCurrentValue) {
		_nameCurrentValue = nameCurrentValue;
	}

	public void setParentGroupId(long parentGroupId) {
		_parentGroupId = parentGroupId;
	}

	public void setRemoteStagingGroupCount(long remoteStagingGroupCount) {
		_remoteStagingGroupCount = remoteStagingGroupCount;
	}

	public void setSite(boolean site) {
		_site = site;
	}

	public void setTreePath(String treePath) {
		_treePath = treePath;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private boolean _active;
	private long _classNameId;
	private long _classPK;
	private long _companyId;
	private long _creatorUserId;
	private String _description;
	private String _descriptionCurrentValue;
	private String _descriptiveName;
	private String _friendlyURL;
	private long _groupId;
	private String _groupKey;
	private String _id;
	private boolean _inheritContent;
	private long _liveGroupId;
	private boolean _manualMembership;
	private int _membershipRestriction;
	private long _mvccVersion;
	private String _name;
	private String _nameCurrentValue;
	private long _parentGroupId;
	private long _remoteStagingGroupCount;
	private boolean _site;
	private String _treePath;
	private String _type;
	private String _uuid;

}