/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DynamicQueryEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DynamicQueryEntry
 * @generated
 */
public class DynamicQueryEntryWrapper
	extends BaseModelWrapper<DynamicQueryEntry>
	implements DynamicQueryEntry, ModelWrapper<DynamicQueryEntry> {

	public DynamicQueryEntryWrapper(DynamicQueryEntry dynamicQueryEntry) {
		super(dynamicQueryEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dynamicQueryEntryId", getDynamicQueryEntryId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("amount", getAmount());
		attributes.put("description", getDescription());
		attributes.put("name", getName());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dynamicQueryEntryId = (Long)attributes.get("dynamicQueryEntryId");

		if (dynamicQueryEntryId != null) {
			setDynamicQueryEntryId(dynamicQueryEntryId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long amount = (Long)attributes.get("amount");

		if (amount != null) {
			setAmount(amount);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public DynamicQueryEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the amount of this dynamic query entry.
	 *
	 * @return the amount of this dynamic query entry
	 */
	@Override
	public long getAmount() {
		return model.getAmount();
	}

	/**
	 * Returns the create date of this dynamic query entry.
	 *
	 * @return the create date of this dynamic query entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this dynamic query entry.
	 *
	 * @return the description of this dynamic query entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the dynamic query entry ID of this dynamic query entry.
	 *
	 * @return the dynamic query entry ID of this dynamic query entry
	 */
	@Override
	public long getDynamicQueryEntryId() {
		return model.getDynamicQueryEntryId();
	}

	/**
	 * Returns the modified date of this dynamic query entry.
	 *
	 * @return the modified date of this dynamic query entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this dynamic query entry.
	 *
	 * @return the name of this dynamic query entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this dynamic query entry.
	 *
	 * @return the primary key of this dynamic query entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this dynamic query entry.
	 *
	 * @return the status of this dynamic query entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the amount of this dynamic query entry.
	 *
	 * @param amount the amount of this dynamic query entry
	 */
	@Override
	public void setAmount(long amount) {
		model.setAmount(amount);
	}

	/**
	 * Sets the create date of this dynamic query entry.
	 *
	 * @param createDate the create date of this dynamic query entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this dynamic query entry.
	 *
	 * @param description the description of this dynamic query entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the dynamic query entry ID of this dynamic query entry.
	 *
	 * @param dynamicQueryEntryId the dynamic query entry ID of this dynamic query entry
	 */
	@Override
	public void setDynamicQueryEntryId(long dynamicQueryEntryId) {
		model.setDynamicQueryEntryId(dynamicQueryEntryId);
	}

	/**
	 * Sets the modified date of this dynamic query entry.
	 *
	 * @param modifiedDate the modified date of this dynamic query entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this dynamic query entry.
	 *
	 * @param name the name of this dynamic query entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this dynamic query entry.
	 *
	 * @param primaryKey the primary key of this dynamic query entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this dynamic query entry.
	 *
	 * @param status the status of this dynamic query entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected DynamicQueryEntryWrapper wrap(
		DynamicQueryEntry dynamicQueryEntry) {

		return new DynamicQueryEntryWrapper(dynamicQueryEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1975450726