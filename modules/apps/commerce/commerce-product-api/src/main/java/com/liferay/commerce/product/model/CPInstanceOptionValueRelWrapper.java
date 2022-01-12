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
 * This class is a wrapper for {@link CPInstanceOptionValueRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CPInstanceOptionValueRel
 * @generated
 */
public class CPInstanceOptionValueRelWrapper
	extends BaseModelWrapper<CPInstanceOptionValueRel>
	implements CPInstanceOptionValueRel,
			   ModelWrapper<CPInstanceOptionValueRel> {

	public CPInstanceOptionValueRelWrapper(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		super(cpInstanceOptionValueRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"CPInstanceOptionValueRelId", getCPInstanceOptionValueRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionOptionRelId", getCPDefinitionOptionRelId());
		attributes.put(
			"CPDefinitionOptionValueRelId", getCPDefinitionOptionValueRelId());
		attributes.put("CPInstanceId", getCPInstanceId());

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

		Long CPInstanceOptionValueRelId = (Long)attributes.get(
			"CPInstanceOptionValueRelId");

		if (CPInstanceOptionValueRelId != null) {
			setCPInstanceOptionValueRelId(CPInstanceOptionValueRelId);
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

		Long CPDefinitionOptionRelId = (Long)attributes.get(
			"CPDefinitionOptionRelId");

		if (CPDefinitionOptionRelId != null) {
			setCPDefinitionOptionRelId(CPDefinitionOptionRelId);
		}

		Long CPDefinitionOptionValueRelId = (Long)attributes.get(
			"CPDefinitionOptionValueRelId");

		if (CPDefinitionOptionValueRelId != null) {
			setCPDefinitionOptionValueRelId(CPDefinitionOptionValueRelId);
		}

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
		}
	}

	@Override
	public CPInstanceOptionValueRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cp instance option value rel.
	 *
	 * @return the company ID of this cp instance option value rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp definition option rel ID of this cp instance option value rel.
	 *
	 * @return the cp definition option rel ID of this cp instance option value rel
	 */
	@Override
	public long getCPDefinitionOptionRelId() {
		return model.getCPDefinitionOptionRelId();
	}

	/**
	 * Returns the cp definition option value rel ID of this cp instance option value rel.
	 *
	 * @return the cp definition option value rel ID of this cp instance option value rel
	 */
	@Override
	public long getCPDefinitionOptionValueRelId() {
		return model.getCPDefinitionOptionValueRelId();
	}

	/**
	 * Returns the cp instance ID of this cp instance option value rel.
	 *
	 * @return the cp instance ID of this cp instance option value rel
	 */
	@Override
	public long getCPInstanceId() {
		return model.getCPInstanceId();
	}

	/**
	 * Returns the cp instance option value rel ID of this cp instance option value rel.
	 *
	 * @return the cp instance option value rel ID of this cp instance option value rel
	 */
	@Override
	public long getCPInstanceOptionValueRelId() {
		return model.getCPInstanceOptionValueRelId();
	}

	/**
	 * Returns the create date of this cp instance option value rel.
	 *
	 * @return the create date of this cp instance option value rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp instance option value rel.
	 *
	 * @return the ct collection ID of this cp instance option value rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this cp instance option value rel.
	 *
	 * @return the group ID of this cp instance option value rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this cp instance option value rel.
	 *
	 * @return the modified date of this cp instance option value rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp instance option value rel.
	 *
	 * @return the mvcc version of this cp instance option value rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp instance option value rel.
	 *
	 * @return the primary key of this cp instance option value rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cp instance option value rel.
	 *
	 * @return the user ID of this cp instance option value rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp instance option value rel.
	 *
	 * @return the user name of this cp instance option value rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp instance option value rel.
	 *
	 * @return the user uuid of this cp instance option value rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp instance option value rel.
	 *
	 * @return the uuid of this cp instance option value rel
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this cp instance option value rel.
	 *
	 * @param companyId the company ID of this cp instance option value rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition option rel ID of this cp instance option value rel.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID of this cp instance option value rel
	 */
	@Override
	public void setCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		model.setCPDefinitionOptionRelId(CPDefinitionOptionRelId);
	}

	/**
	 * Sets the cp definition option value rel ID of this cp instance option value rel.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID of this cp instance option value rel
	 */
	@Override
	public void setCPDefinitionOptionValueRelId(
		long CPDefinitionOptionValueRelId) {

		model.setCPDefinitionOptionValueRelId(CPDefinitionOptionValueRelId);
	}

	/**
	 * Sets the cp instance ID of this cp instance option value rel.
	 *
	 * @param CPInstanceId the cp instance ID of this cp instance option value rel
	 */
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		model.setCPInstanceId(CPInstanceId);
	}

	/**
	 * Sets the cp instance option value rel ID of this cp instance option value rel.
	 *
	 * @param CPInstanceOptionValueRelId the cp instance option value rel ID of this cp instance option value rel
	 */
	@Override
	public void setCPInstanceOptionValueRelId(long CPInstanceOptionValueRelId) {
		model.setCPInstanceOptionValueRelId(CPInstanceOptionValueRelId);
	}

	/**
	 * Sets the create date of this cp instance option value rel.
	 *
	 * @param createDate the create date of this cp instance option value rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp instance option value rel.
	 *
	 * @param ctCollectionId the ct collection ID of this cp instance option value rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this cp instance option value rel.
	 *
	 * @param groupId the group ID of this cp instance option value rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this cp instance option value rel.
	 *
	 * @param modifiedDate the modified date of this cp instance option value rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp instance option value rel.
	 *
	 * @param mvccVersion the mvcc version of this cp instance option value rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp instance option value rel.
	 *
	 * @param primaryKey the primary key of this cp instance option value rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cp instance option value rel.
	 *
	 * @param userId the user ID of this cp instance option value rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp instance option value rel.
	 *
	 * @param userName the user name of this cp instance option value rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp instance option value rel.
	 *
	 * @param userUuid the user uuid of this cp instance option value rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp instance option value rel.
	 *
	 * @param uuid the uuid of this cp instance option value rel
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CPInstanceOptionValueRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPInstanceOptionValueRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPInstanceOptionValueRelWrapper wrap(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		return new CPInstanceOptionValueRelWrapper(cpInstanceOptionValueRel);
	}

}