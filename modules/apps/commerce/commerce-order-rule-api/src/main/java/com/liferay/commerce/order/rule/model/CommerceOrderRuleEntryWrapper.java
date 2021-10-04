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

package com.liferay.commerce.order.rule.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceOrderRuleEntry}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntry
 * @generated
 */
public class CommerceOrderRuleEntryWrapper
	extends BaseModelWrapper<CommerceOrderRuleEntry>
	implements CommerceOrderRuleEntry, ModelWrapper<CommerceOrderRuleEntry> {

	public CommerceOrderRuleEntryWrapper(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		super(commerceOrderRuleEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceOrderRuleEntryId", getCommerceOrderRuleEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("active", isActive());
		attributes.put("description", getDescription());
		attributes.put("name", getName());
		attributes.put("priority", getPriority());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceOrderRuleEntryId = (Long)attributes.get(
			"commerceOrderRuleEntryId");

		if (commerceOrderRuleEntryId != null) {
			setCommerceOrderRuleEntryId(commerceOrderRuleEntryId);
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

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}
	}

	@Override
	public CommerceOrderRuleEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this commerce order rule entry.
	 *
	 * @return the active of this commerce order rule entry
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the commerce order rule entry ID of this commerce order rule entry.
	 *
	 * @return the commerce order rule entry ID of this commerce order rule entry
	 */
	@Override
	public long getCommerceOrderRuleEntryId() {
		return model.getCommerceOrderRuleEntryId();
	}

	/**
	 * Returns the company ID of this commerce order rule entry.
	 *
	 * @return the company ID of this commerce order rule entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce order rule entry.
	 *
	 * @return the create date of this commerce order rule entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this commerce order rule entry.
	 *
	 * @return the description of this commerce order rule entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the external reference code of this commerce order rule entry.
	 *
	 * @return the external reference code of this commerce order rule entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this commerce order rule entry.
	 *
	 * @return the modified date of this commerce order rule entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce order rule entry.
	 *
	 * @return the name of this commerce order rule entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce order rule entry.
	 *
	 * @return the primary key of this commerce order rule entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce order rule entry.
	 *
	 * @return the priority of this commerce order rule entry
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getSettingsProperties() {

		return model.getSettingsProperties();
	}

	@Override
	public String getSettingsProperty(String key) {
		return model.getSettingsProperty(key);
	}

	/**
	 * Returns the type of this commerce order rule entry.
	 *
	 * @return the type of this commerce order rule entry
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the type settings of this commerce order rule entry.
	 *
	 * @return the type settings of this commerce order rule entry
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	/**
	 * Returns the user ID of this commerce order rule entry.
	 *
	 * @return the user ID of this commerce order rule entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce order rule entry.
	 *
	 * @return the user name of this commerce order rule entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce order rule entry.
	 *
	 * @return the user uuid of this commerce order rule entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce order rule entry is active.
	 *
	 * @return <code>true</code> if this commerce order rule entry is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this commerce order rule entry is active.
	 *
	 * @param active the active of this commerce order rule entry
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the commerce order rule entry ID of this commerce order rule entry.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID of this commerce order rule entry
	 */
	@Override
	public void setCommerceOrderRuleEntryId(long commerceOrderRuleEntryId) {
		model.setCommerceOrderRuleEntryId(commerceOrderRuleEntryId);
	}

	/**
	 * Sets the company ID of this commerce order rule entry.
	 *
	 * @param companyId the company ID of this commerce order rule entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce order rule entry.
	 *
	 * @param createDate the create date of this commerce order rule entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this commerce order rule entry.
	 *
	 * @param description the description of this commerce order rule entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the external reference code of this commerce order rule entry.
	 *
	 * @param externalReferenceCode the external reference code of this commerce order rule entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce order rule entry.
	 *
	 * @param modifiedDate the modified date of this commerce order rule entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce order rule entry.
	 *
	 * @param name the name of this commerce order rule entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce order rule entry.
	 *
	 * @param primaryKey the primary key of this commerce order rule entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce order rule entry.
	 *
	 * @param priority the priority of this commerce order rule entry
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the type of this commerce order rule entry.
	 *
	 * @param type the type of this commerce order rule entry
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the type settings of this commerce order rule entry.
	 *
	 * @param typeSettings the type settings of this commerce order rule entry
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties unicodeProperties) {

		model.setTypeSettingsProperties(unicodeProperties);
	}

	/**
	 * Sets the user ID of this commerce order rule entry.
	 *
	 * @param userId the user ID of this commerce order rule entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce order rule entry.
	 *
	 * @param userName the user name of this commerce order rule entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce order rule entry.
	 *
	 * @param userUuid the user uuid of this commerce order rule entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceOrderRuleEntryWrapper wrap(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		return new CommerceOrderRuleEntryWrapper(commerceOrderRuleEntry);
	}

}