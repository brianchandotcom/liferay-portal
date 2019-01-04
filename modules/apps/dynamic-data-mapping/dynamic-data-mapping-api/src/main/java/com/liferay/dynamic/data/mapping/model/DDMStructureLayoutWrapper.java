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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureLayout}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayout
 * @generated
 */
@ProviderType
public class DDMStructureLayoutWrapper extends BaseModelWrapper<DDMStructureLayout>
	implements DDMStructureLayout, ModelWrapper<DDMStructureLayout> {
	public DDMStructureLayoutWrapper(DDMStructureLayout ddmStructureLayout) {
		super(ddmStructureLayout);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("structureLayoutId", getStructureLayoutId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("structureVersionId", getStructureVersionId());
		attributes.put("definition", getDefinition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long structureLayoutId = (Long)attributes.get("structureLayoutId");

		if (structureLayoutId != null) {
			setStructureLayoutId(structureLayoutId);
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

		Long structureVersionId = (Long)attributes.get("structureVersionId");

		if (structureVersionId != null) {
			setStructureVersionId(structureVersionId);
		}

		String definition = (String)attributes.get("definition");

		if (definition != null) {
			setDefinition(definition);
		}
	}

	/**
	* Returns the company ID of this ddm structure layout.
	*
	* @return the company ID of this ddm structure layout
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this ddm structure layout.
	*
	* @return the create date of this ddm structure layout
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public DDMFormLayout getDDMFormLayout() {
		return model.getDDMFormLayout();
	}

	/**
	* Returns the definition of this ddm structure layout.
	*
	* @return the definition of this ddm structure layout
	*/
	@Override
	public String getDefinition() {
		return model.getDefinition();
	}

	/**
	* Returns the group ID of this ddm structure layout.
	*
	* @return the group ID of this ddm structure layout
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the modified date of this ddm structure layout.
	*
	* @return the modified date of this ddm structure layout
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the primary key of this ddm structure layout.
	*
	* @return the primary key of this ddm structure layout
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the structure layout ID of this ddm structure layout.
	*
	* @return the structure layout ID of this ddm structure layout
	*/
	@Override
	public long getStructureLayoutId() {
		return model.getStructureLayoutId();
	}

	/**
	* Returns the structure version ID of this ddm structure layout.
	*
	* @return the structure version ID of this ddm structure layout
	*/
	@Override
	public long getStructureVersionId() {
		return model.getStructureVersionId();
	}

	/**
	* Returns the user ID of this ddm structure layout.
	*
	* @return the user ID of this ddm structure layout
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this ddm structure layout.
	*
	* @return the user name of this ddm structure layout
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this ddm structure layout.
	*
	* @return the user uuid of this ddm structure layout
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this ddm structure layout.
	*
	* @return the uuid of this ddm structure layout
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
	* Sets the company ID of this ddm structure layout.
	*
	* @param companyId the company ID of this ddm structure layout
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ddm structure layout.
	*
	* @param createDate the create date of this ddm structure layout
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the definition of this ddm structure layout.
	*
	* @param definition the definition of this ddm structure layout
	*/
	@Override
	public void setDefinition(String definition) {
		model.setDefinition(definition);
	}

	/**
	* Sets the group ID of this ddm structure layout.
	*
	* @param groupId the group ID of this ddm structure layout
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this ddm structure layout.
	*
	* @param modifiedDate the modified date of this ddm structure layout
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the primary key of this ddm structure layout.
	*
	* @param primaryKey the primary key of this ddm structure layout
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the structure layout ID of this ddm structure layout.
	*
	* @param structureLayoutId the structure layout ID of this ddm structure layout
	*/
	@Override
	public void setStructureLayoutId(long structureLayoutId) {
		model.setStructureLayoutId(structureLayoutId);
	}

	/**
	* Sets the structure version ID of this ddm structure layout.
	*
	* @param structureVersionId the structure version ID of this ddm structure layout
	*/
	@Override
	public void setStructureVersionId(long structureVersionId) {
		model.setStructureVersionId(structureVersionId);
	}

	/**
	* Sets the user ID of this ddm structure layout.
	*
	* @param userId the user ID of this ddm structure layout
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this ddm structure layout.
	*
	* @param userName the user name of this ddm structure layout
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddm structure layout.
	*
	* @param userUuid the user uuid of this ddm structure layout
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this ddm structure layout.
	*
	* @param uuid the uuid of this ddm structure layout
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DDMStructureLayoutWrapper wrap(
		DDMStructureLayout ddmStructureLayout) {
		return new DDMStructureLayoutWrapper(ddmStructureLayout);
	}
}