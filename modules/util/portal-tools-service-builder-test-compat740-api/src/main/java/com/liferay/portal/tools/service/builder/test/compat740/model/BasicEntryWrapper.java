/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BasicEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BasicEntry
 * @generated
 */
public class BasicEntryWrapper
	extends BaseModelWrapper<BasicEntry>
	implements BasicEntry, ModelWrapper<BasicEntry> {

	public BasicEntryWrapper(BasicEntry basicEntry) {
		super(basicEntry);
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
	public BasicEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the basic entry ID of this basic entry.
	 *
	 * @return the basic entry ID of this basic entry
	 */
	@Override
	public long getBasicEntryId() {
		return model.getBasicEntryId();
	}

	/**
	 * Returns the company ID of this basic entry.
	 *
	 * @return the company ID of this basic entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this basic entry.
	 *
	 * @return the create date of this basic entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this basic entry.
	 *
	 * @return the description of this basic entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the group ID of this basic entry.
	 *
	 * @return the group ID of this basic entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this basic entry.
	 *
	 * @return the modified date of this basic entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this basic entry.
	 *
	 * @return the name of this basic entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this basic entry.
	 *
	 * @return the primary key of this basic entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this basic entry.
	 *
	 * @return the user ID of this basic entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this basic entry.
	 *
	 * @return the user name of this basic entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this basic entry.
	 *
	 * @return the user uuid of this basic entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the basic entry ID of this basic entry.
	 *
	 * @param basicEntryId the basic entry ID of this basic entry
	 */
	@Override
	public void setBasicEntryId(long basicEntryId) {
		model.setBasicEntryId(basicEntryId);
	}

	/**
	 * Sets the company ID of this basic entry.
	 *
	 * @param companyId the company ID of this basic entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this basic entry.
	 *
	 * @param createDate the create date of this basic entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this basic entry.
	 *
	 * @param description the description of this basic entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this basic entry.
	 *
	 * @param groupId the group ID of this basic entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this basic entry.
	 *
	 * @param modifiedDate the modified date of this basic entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this basic entry.
	 *
	 * @param name the name of this basic entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this basic entry.
	 *
	 * @param primaryKey the primary key of this basic entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this basic entry.
	 *
	 * @param userId the user ID of this basic entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this basic entry.
	 *
	 * @param userName the user name of this basic entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this basic entry.
	 *
	 * @param userUuid the user uuid of this basic entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected BasicEntryWrapper wrap(BasicEntry basicEntry) {
		return new BasicEntryWrapper(basicEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1062525096