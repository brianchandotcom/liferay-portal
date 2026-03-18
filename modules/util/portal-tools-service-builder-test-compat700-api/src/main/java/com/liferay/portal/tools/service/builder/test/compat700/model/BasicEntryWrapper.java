/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link BasicEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BasicEntry
 * @generated
 */
public class BasicEntryWrapper implements BasicEntry, ModelWrapper<BasicEntry> {

	public BasicEntryWrapper(BasicEntry basicEntry) {
		_basicEntry = basicEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return BasicEntry.class;
	}

	@Override
	public String getModelClassName() {
		return BasicEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("basicEntryId", getBasicEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long basicEntryId = (Long)attributes.get("basicEntryId");

		if (basicEntryId != null) {
			setBasicEntryId(basicEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public Object clone() {
		return new BasicEntryWrapper((BasicEntry)_basicEntry.clone());
	}

	@Override
	public int compareTo(BasicEntry basicEntry) {
		return _basicEntry.compareTo(basicEntry);
	}

	/**
	 * Returns the basic entry ID of this basic entry.
	 *
	 * @return the basic entry ID of this basic entry
	 */
	@Override
	public long getBasicEntryId() {
		return _basicEntry.getBasicEntryId();
	}

	/**
	 * Returns the company ID of this basic entry.
	 *
	 * @return the company ID of this basic entry
	 */
	@Override
	public long getCompanyId() {
		return _basicEntry.getCompanyId();
	}

	/**
	 * Returns the create date of this basic entry.
	 *
	 * @return the create date of this basic entry
	 */
	@Override
	public Date getCreateDate() {
		return _basicEntry.getCreateDate();
	}

	/**
	 * Returns the description of this basic entry.
	 *
	 * @return the description of this basic entry
	 */
	@Override
	public String getDescription() {
		return _basicEntry.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _basicEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this basic entry.
	 *
	 * @return the group ID of this basic entry
	 */
	@Override
	public long getGroupId() {
		return _basicEntry.getGroupId();
	}

	/**
	 * Returns the modified date of this basic entry.
	 *
	 * @return the modified date of this basic entry
	 */
	@Override
	public Date getModifiedDate() {
		return _basicEntry.getModifiedDate();
	}

	/**
	 * Returns the name of this basic entry.
	 *
	 * @return the name of this basic entry
	 */
	@Override
	public String getName() {
		return _basicEntry.getName();
	}

	/**
	 * Returns the primary key of this basic entry.
	 *
	 * @return the primary key of this basic entry
	 */
	@Override
	public long getPrimaryKey() {
		return _basicEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _basicEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this basic entry.
	 *
	 * @return the user ID of this basic entry
	 */
	@Override
	public long getUserId() {
		return _basicEntry.getUserId();
	}

	/**
	 * Returns the user name of this basic entry.
	 *
	 * @return the user name of this basic entry
	 */
	@Override
	public String getUserName() {
		return _basicEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this basic entry.
	 *
	 * @return the user uuid of this basic entry
	 */
	@Override
	public String getUserUuid() {
		return _basicEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _basicEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _basicEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _basicEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _basicEntry.isNew();
	}

	@Override
	public void persist() {
		_basicEntry.persist();
	}

	/**
	 * Sets the basic entry ID of this basic entry.
	 *
	 * @param basicEntryId the basic entry ID of this basic entry
	 */
	@Override
	public void setBasicEntryId(long basicEntryId) {
		_basicEntry.setBasicEntryId(basicEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_basicEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this basic entry.
	 *
	 * @param companyId the company ID of this basic entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_basicEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this basic entry.
	 *
	 * @param createDate the create date of this basic entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_basicEntry.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this basic entry.
	 *
	 * @param description the description of this basic entry
	 */
	@Override
	public void setDescription(String description) {
		_basicEntry.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_basicEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_basicEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_basicEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this basic entry.
	 *
	 * @param groupId the group ID of this basic entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_basicEntry.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this basic entry.
	 *
	 * @param modifiedDate the modified date of this basic entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_basicEntry.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this basic entry.
	 *
	 * @param name the name of this basic entry
	 */
	@Override
	public void setName(String name) {
		_basicEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_basicEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this basic entry.
	 *
	 * @param primaryKey the primary key of this basic entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_basicEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_basicEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this basic entry.
	 *
	 * @param userId the user ID of this basic entry
	 */
	@Override
	public void setUserId(long userId) {
		_basicEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this basic entry.
	 *
	 * @param userName the user name of this basic entry
	 */
	@Override
	public void setUserName(String userName) {
		_basicEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this basic entry.
	 *
	 * @param userUuid the user uuid of this basic entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_basicEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<BasicEntry>
		toCacheModel() {

		return _basicEntry.toCacheModel();
	}

	@Override
	public BasicEntry toEscapedModel() {
		return new BasicEntryWrapper(_basicEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _basicEntry.toString();
	}

	@Override
	public BasicEntry toUnescapedModel() {
		return new BasicEntryWrapper(_basicEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _basicEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BasicEntryWrapper)) {
			return false;
		}

		BasicEntryWrapper basicEntryWrapper = (BasicEntryWrapper)object;

		if (Objects.equals(_basicEntry, basicEntryWrapper._basicEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public BasicEntry getWrappedModel() {
		return _basicEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _basicEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _basicEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_basicEntry.resetOriginalValues();
	}

	private final BasicEntry _basicEntry;

}
// LIFERAY-SERVICE-BUILDER-HASH:1838195113