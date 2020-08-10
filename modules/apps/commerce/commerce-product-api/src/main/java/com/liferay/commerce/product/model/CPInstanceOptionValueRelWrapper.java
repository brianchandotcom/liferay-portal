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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
	implements CPInstanceOptionValueRel,
			   ModelWrapper<CPInstanceOptionValueRel> {

	public CPInstanceOptionValueRelWrapper(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		_cpInstanceOptionValueRel = cpInstanceOptionValueRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CPInstanceOptionValueRel.class;
	}

	@Override
	public String getModelClassName() {
		return CPInstanceOptionValueRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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
	public Object clone() {
		return new CPInstanceOptionValueRelWrapper(
			(CPInstanceOptionValueRel)_cpInstanceOptionValueRel.clone());
	}

	@Override
	public int compareTo(CPInstanceOptionValueRel cpInstanceOptionValueRel) {
		return _cpInstanceOptionValueRel.compareTo(cpInstanceOptionValueRel);
	}

	/**
	 * Returns the company ID of this cp instance option value rel.
	 *
	 * @return the company ID of this cp instance option value rel
	 */
	@Override
	public long getCompanyId() {
		return _cpInstanceOptionValueRel.getCompanyId();
	}

	/**
	 * Returns the cp definition option rel ID of this cp instance option value rel.
	 *
	 * @return the cp definition option rel ID of this cp instance option value rel
	 */
	@Override
	public long getCPDefinitionOptionRelId() {
		return _cpInstanceOptionValueRel.getCPDefinitionOptionRelId();
	}

	/**
	 * Returns the cp definition option value rel ID of this cp instance option value rel.
	 *
	 * @return the cp definition option value rel ID of this cp instance option value rel
	 */
	@Override
	public long getCPDefinitionOptionValueRelId() {
		return _cpInstanceOptionValueRel.getCPDefinitionOptionValueRelId();
	}

	/**
	 * Returns the cp instance ID of this cp instance option value rel.
	 *
	 * @return the cp instance ID of this cp instance option value rel
	 */
	@Override
	public long getCPInstanceId() {
		return _cpInstanceOptionValueRel.getCPInstanceId();
	}

	/**
	 * Returns the cp instance option value rel ID of this cp instance option value rel.
	 *
	 * @return the cp instance option value rel ID of this cp instance option value rel
	 */
	@Override
	public long getCPInstanceOptionValueRelId() {
		return _cpInstanceOptionValueRel.getCPInstanceOptionValueRelId();
	}

	/**
	 * Returns the create date of this cp instance option value rel.
	 *
	 * @return the create date of this cp instance option value rel
	 */
	@Override
	public Date getCreateDate() {
		return _cpInstanceOptionValueRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpInstanceOptionValueRel.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this cp instance option value rel.
	 *
	 * @return the group ID of this cp instance option value rel
	 */
	@Override
	public long getGroupId() {
		return _cpInstanceOptionValueRel.getGroupId();
	}

	/**
	 * Returns the modified date of this cp instance option value rel.
	 *
	 * @return the modified date of this cp instance option value rel
	 */
	@Override
	public Date getModifiedDate() {
		return _cpInstanceOptionValueRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this cp instance option value rel.
	 *
	 * @return the primary key of this cp instance option value rel
	 */
	@Override
	public long getPrimaryKey() {
		return _cpInstanceOptionValueRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpInstanceOptionValueRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this cp instance option value rel.
	 *
	 * @return the user ID of this cp instance option value rel
	 */
	@Override
	public long getUserId() {
		return _cpInstanceOptionValueRel.getUserId();
	}

	/**
	 * Returns the user name of this cp instance option value rel.
	 *
	 * @return the user name of this cp instance option value rel
	 */
	@Override
	public String getUserName() {
		return _cpInstanceOptionValueRel.getUserName();
	}

	/**
	 * Returns the user uuid of this cp instance option value rel.
	 *
	 * @return the user uuid of this cp instance option value rel
	 */
	@Override
	public String getUserUuid() {
		return _cpInstanceOptionValueRel.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp instance option value rel.
	 *
	 * @return the uuid of this cp instance option value rel
	 */
	@Override
	public String getUuid() {
		return _cpInstanceOptionValueRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpInstanceOptionValueRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpInstanceOptionValueRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpInstanceOptionValueRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpInstanceOptionValueRel.isNew();
	}

	@Override
	public void persist() {
		_cpInstanceOptionValueRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpInstanceOptionValueRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this cp instance option value rel.
	 *
	 * @param companyId the company ID of this cp instance option value rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_cpInstanceOptionValueRel.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition option rel ID of this cp instance option value rel.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID of this cp instance option value rel
	 */
	@Override
	public void setCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		_cpInstanceOptionValueRel.setCPDefinitionOptionRelId(
			CPDefinitionOptionRelId);
	}

	/**
	 * Sets the cp definition option value rel ID of this cp instance option value rel.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID of this cp instance option value rel
	 */
	@Override
	public void setCPDefinitionOptionValueRelId(
		long CPDefinitionOptionValueRelId) {

		_cpInstanceOptionValueRel.setCPDefinitionOptionValueRelId(
			CPDefinitionOptionValueRelId);
	}

	/**
	 * Sets the cp instance ID of this cp instance option value rel.
	 *
	 * @param CPInstanceId the cp instance ID of this cp instance option value rel
	 */
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		_cpInstanceOptionValueRel.setCPInstanceId(CPInstanceId);
	}

	/**
	 * Sets the cp instance option value rel ID of this cp instance option value rel.
	 *
	 * @param CPInstanceOptionValueRelId the cp instance option value rel ID of this cp instance option value rel
	 */
	@Override
	public void setCPInstanceOptionValueRelId(long CPInstanceOptionValueRelId) {
		_cpInstanceOptionValueRel.setCPInstanceOptionValueRelId(
			CPInstanceOptionValueRelId);
	}

	/**
	 * Sets the create date of this cp instance option value rel.
	 *
	 * @param createDate the create date of this cp instance option value rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_cpInstanceOptionValueRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_cpInstanceOptionValueRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpInstanceOptionValueRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpInstanceOptionValueRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this cp instance option value rel.
	 *
	 * @param groupId the group ID of this cp instance option value rel
	 */
	@Override
	public void setGroupId(long groupId) {
		_cpInstanceOptionValueRel.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this cp instance option value rel.
	 *
	 * @param modifiedDate the modified date of this cp instance option value rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpInstanceOptionValueRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpInstanceOptionValueRel.setNew(n);
	}

	/**
	 * Sets the primary key of this cp instance option value rel.
	 *
	 * @param primaryKey the primary key of this cp instance option value rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpInstanceOptionValueRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpInstanceOptionValueRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this cp instance option value rel.
	 *
	 * @param userId the user ID of this cp instance option value rel
	 */
	@Override
	public void setUserId(long userId) {
		_cpInstanceOptionValueRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp instance option value rel.
	 *
	 * @param userName the user name of this cp instance option value rel
	 */
	@Override
	public void setUserName(String userName) {
		_cpInstanceOptionValueRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp instance option value rel.
	 *
	 * @param userUuid the user uuid of this cp instance option value rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_cpInstanceOptionValueRel.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp instance option value rel.
	 *
	 * @param uuid the uuid of this cp instance option value rel
	 */
	@Override
	public void setUuid(String uuid) {
		_cpInstanceOptionValueRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPInstanceOptionValueRel>
		toCacheModel() {

		return _cpInstanceOptionValueRel.toCacheModel();
	}

	@Override
	public CPInstanceOptionValueRel toEscapedModel() {
		return new CPInstanceOptionValueRelWrapper(
			_cpInstanceOptionValueRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpInstanceOptionValueRel.toString();
	}

	@Override
	public CPInstanceOptionValueRel toUnescapedModel() {
		return new CPInstanceOptionValueRelWrapper(
			_cpInstanceOptionValueRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpInstanceOptionValueRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPInstanceOptionValueRelWrapper)) {
			return false;
		}

		CPInstanceOptionValueRelWrapper cpInstanceOptionValueRelWrapper =
			(CPInstanceOptionValueRelWrapper)object;

		if (Objects.equals(
				_cpInstanceOptionValueRel,
				cpInstanceOptionValueRelWrapper._cpInstanceOptionValueRel)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpInstanceOptionValueRel.getStagedModelType();
	}

	@Override
	public CPInstanceOptionValueRel getWrappedModel() {
		return _cpInstanceOptionValueRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpInstanceOptionValueRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpInstanceOptionValueRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpInstanceOptionValueRel.resetOriginalValues();
	}

	private final CPInstanceOptionValueRel _cpInstanceOptionValueRel;

}