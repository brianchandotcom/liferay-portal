/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ArrayableEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ArrayableEntry
 * @generated
 */
public class ArrayableEntryWrapper
	extends BaseModelWrapper<ArrayableEntry>
	implements ArrayableEntry, ModelWrapper<ArrayableEntry> {

	public ArrayableEntryWrapper(ArrayableEntry arrayableEntry) {
		super(arrayableEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("arrayableEntryId", getArrayableEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("integer", getInteger());
		attributes.put("name", getName());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long arrayableEntryId = (Long)attributes.get("arrayableEntryId");

		if (arrayableEntryId != null) {
			setArrayableEntryId(arrayableEntryId);
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

		Integer integer = (Integer)attributes.get("integer");

		if (integer != null) {
			setInteger(integer);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public ArrayableEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the arrayable entry ID of this arrayable entry.
	 *
	 * @return the arrayable entry ID of this arrayable entry
	 */
	@Override
	public long getArrayableEntryId() {
		return model.getArrayableEntryId();
	}

	/**
	 * Returns the company ID of this arrayable entry.
	 *
	 * @return the company ID of this arrayable entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the group ID of this arrayable entry.
	 *
	 * @return the group ID of this arrayable entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the integer of this arrayable entry.
	 *
	 * @return the integer of this arrayable entry
	 */
	@Override
	public int getInteger() {
		return model.getInteger();
	}

	/**
	 * Returns the name of this arrayable entry.
	 *
	 * @return the name of this arrayable entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this arrayable entry.
	 *
	 * @return the primary key of this arrayable entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this arrayable entry.
	 *
	 * @return the type of this arrayable entry
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this arrayable entry.
	 *
	 * @return the user ID of this arrayable entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this arrayable entry.
	 *
	 * @return the user uuid of this arrayable entry
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
	 * Sets the arrayable entry ID of this arrayable entry.
	 *
	 * @param arrayableEntryId the arrayable entry ID of this arrayable entry
	 */
	@Override
	public void setArrayableEntryId(long arrayableEntryId) {
		model.setArrayableEntryId(arrayableEntryId);
	}

	/**
	 * Sets the company ID of this arrayable entry.
	 *
	 * @param companyId the company ID of this arrayable entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the group ID of this arrayable entry.
	 *
	 * @param groupId the group ID of this arrayable entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the integer of this arrayable entry.
	 *
	 * @param integer the integer of this arrayable entry
	 */
	@Override
	public void setInteger(int integer) {
		model.setInteger(integer);
	}

	/**
	 * Sets the name of this arrayable entry.
	 *
	 * @param name the name of this arrayable entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this arrayable entry.
	 *
	 * @param primaryKey the primary key of this arrayable entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this arrayable entry.
	 *
	 * @param type the type of this arrayable entry
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this arrayable entry.
	 *
	 * @param userId the user ID of this arrayable entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this arrayable entry.
	 *
	 * @param userUuid the user uuid of this arrayable entry
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
	protected ArrayableEntryWrapper wrap(ArrayableEntry arrayableEntry) {
		return new ArrayableEntryWrapper(arrayableEntry);
	}

}
// SB-Hash:-357163528