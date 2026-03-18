/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPConfigurationEntrySetting}.
 * </p>
 *
 * @author Marco Leo
 * @see CPConfigurationEntrySetting
 * @generated
 */
public class CPConfigurationEntrySettingWrapper
	extends BaseModelWrapper<CPConfigurationEntrySetting>
	implements CPConfigurationEntrySetting,
			   ModelWrapper<CPConfigurationEntrySetting> {

	public CPConfigurationEntrySettingWrapper(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		super(cpConfigurationEntrySetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"CPConfigurationEntrySettingId",
			getCPConfigurationEntrySettingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPConfigurationEntryId", getCPConfigurationEntryId());
		attributes.put("type", getType());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPConfigurationEntrySettingId = (Long)attributes.get(
			"CPConfigurationEntrySettingId");

		if (CPConfigurationEntrySettingId != null) {
			setCPConfigurationEntrySettingId(CPConfigurationEntrySettingId);
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

		Long CPConfigurationEntryId = (Long)attributes.get(
			"CPConfigurationEntryId");

		if (CPConfigurationEntryId != null) {
			setCPConfigurationEntryId(CPConfigurationEntryId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public CPConfigurationEntrySetting cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cp configuration entry setting.
	 *
	 * @return the company ID of this cp configuration entry setting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp configuration entry ID of this cp configuration entry setting.
	 *
	 * @return the cp configuration entry ID of this cp configuration entry setting
	 */
	@Override
	public long getCPConfigurationEntryId() {
		return model.getCPConfigurationEntryId();
	}

	/**
	 * Returns the cp configuration entry setting ID of this cp configuration entry setting.
	 *
	 * @return the cp configuration entry setting ID of this cp configuration entry setting
	 */
	@Override
	public long getCPConfigurationEntrySettingId() {
		return model.getCPConfigurationEntrySettingId();
	}

	/**
	 * Returns the create date of this cp configuration entry setting.
	 *
	 * @return the create date of this cp configuration entry setting
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp configuration entry setting.
	 *
	 * @return the ct collection ID of this cp configuration entry setting
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this cp configuration entry setting.
	 *
	 * @return the group ID of this cp configuration entry setting
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this cp configuration entry setting.
	 *
	 * @return the modified date of this cp configuration entry setting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp configuration entry setting.
	 *
	 * @return the mvcc version of this cp configuration entry setting
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp configuration entry setting.
	 *
	 * @return the primary key of this cp configuration entry setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this cp configuration entry setting.
	 *
	 * @return the type of this cp configuration entry setting
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this cp configuration entry setting.
	 *
	 * @return the user ID of this cp configuration entry setting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp configuration entry setting.
	 *
	 * @return the user name of this cp configuration entry setting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp configuration entry setting.
	 *
	 * @return the user uuid of this cp configuration entry setting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp configuration entry setting.
	 *
	 * @return the uuid of this cp configuration entry setting
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the value of this cp configuration entry setting.
	 *
	 * @return the value of this cp configuration entry setting
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this cp configuration entry setting.
	 *
	 * @param companyId the company ID of this cp configuration entry setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp configuration entry ID of this cp configuration entry setting.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID of this cp configuration entry setting
	 */
	@Override
	public void setCPConfigurationEntryId(long CPConfigurationEntryId) {
		model.setCPConfigurationEntryId(CPConfigurationEntryId);
	}

	/**
	 * Sets the cp configuration entry setting ID of this cp configuration entry setting.
	 *
	 * @param CPConfigurationEntrySettingId the cp configuration entry setting ID of this cp configuration entry setting
	 */
	@Override
	public void setCPConfigurationEntrySettingId(
		long CPConfigurationEntrySettingId) {

		model.setCPConfigurationEntrySettingId(CPConfigurationEntrySettingId);
	}

	/**
	 * Sets the create date of this cp configuration entry setting.
	 *
	 * @param createDate the create date of this cp configuration entry setting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp configuration entry setting.
	 *
	 * @param ctCollectionId the ct collection ID of this cp configuration entry setting
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this cp configuration entry setting.
	 *
	 * @param groupId the group ID of this cp configuration entry setting
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this cp configuration entry setting.
	 *
	 * @param modifiedDate the modified date of this cp configuration entry setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp configuration entry setting.
	 *
	 * @param mvccVersion the mvcc version of this cp configuration entry setting
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp configuration entry setting.
	 *
	 * @param primaryKey the primary key of this cp configuration entry setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this cp configuration entry setting.
	 *
	 * @param type the type of this cp configuration entry setting
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this cp configuration entry setting.
	 *
	 * @param userId the user ID of this cp configuration entry setting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp configuration entry setting.
	 *
	 * @param userName the user name of this cp configuration entry setting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp configuration entry setting.
	 *
	 * @param userUuid the user uuid of this cp configuration entry setting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp configuration entry setting.
	 *
	 * @param uuid the uuid of this cp configuration entry setting
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the value of this cp configuration entry setting.
	 *
	 * @param value the value of this cp configuration entry setting
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<CPConfigurationEntrySetting, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPConfigurationEntrySetting, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPConfigurationEntrySettingWrapper wrap(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		return new CPConfigurationEntrySettingWrapper(
			cpConfigurationEntrySetting);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-241402693