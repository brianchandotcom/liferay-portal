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

package com.liferay.portal.workflow.kaleo.model;

import aQute.bnd.annotation.ProviderType;

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
 * This class is a wrapper for {@link KaleoTaskForm}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskForm
 * @generated
 */
@ProviderType
public class KaleoTaskFormWrapper implements KaleoTaskForm,
	ModelWrapper<KaleoTaskForm> {
	public KaleoTaskFormWrapper(KaleoTaskForm kaleoTaskForm) {
		_kaleoTaskForm = kaleoTaskForm;
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoTaskForm.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoTaskForm.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("kaleoTaskFormId", getKaleoTaskFormId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put("kaleoNodeId", getKaleoNodeId());
		attributes.put("kaleoTaskId", getKaleoTaskId());
		attributes.put("kaleoTaskName", getKaleoTaskName());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("formCompanyId", getFormCompanyId());
		attributes.put("formDefinition", getFormDefinition());
		attributes.put("formGroupId", getFormGroupId());
		attributes.put("formId", getFormId());
		attributes.put("formUuid", getFormUuid());
		attributes.put("metadata", getMetadata());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long kaleoTaskFormId = (Long)attributes.get("kaleoTaskFormId");

		if (kaleoTaskFormId != null) {
			setKaleoTaskFormId(kaleoTaskFormId);
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

		Long kaleoDefinitionId = (Long)attributes.get("kaleoDefinitionId");

		if (kaleoDefinitionId != null) {
			setKaleoDefinitionId(kaleoDefinitionId);
		}

		Long kaleoNodeId = (Long)attributes.get("kaleoNodeId");

		if (kaleoNodeId != null) {
			setKaleoNodeId(kaleoNodeId);
		}

		Long kaleoTaskId = (Long)attributes.get("kaleoTaskId");

		if (kaleoTaskId != null) {
			setKaleoTaskId(kaleoTaskId);
		}

		String kaleoTaskName = (String)attributes.get("kaleoTaskName");

		if (kaleoTaskName != null) {
			setKaleoTaskName(kaleoTaskName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long formCompanyId = (Long)attributes.get("formCompanyId");

		if (formCompanyId != null) {
			setFormCompanyId(formCompanyId);
		}

		String formDefinition = (String)attributes.get("formDefinition");

		if (formDefinition != null) {
			setFormDefinition(formDefinition);
		}

		Long formGroupId = (Long)attributes.get("formGroupId");

		if (formGroupId != null) {
			setFormGroupId(formGroupId);
		}

		Long formId = (Long)attributes.get("formId");

		if (formId != null) {
			setFormId(formId);
		}

		String formUuid = (String)attributes.get("formUuid");

		if (formUuid != null) {
			setFormUuid(formUuid);
		}

		String metadata = (String)attributes.get("metadata");

		if (metadata != null) {
			setMetadata(metadata);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new KaleoTaskFormWrapper((KaleoTaskForm)_kaleoTaskForm.clone());
	}

	@Override
	public int compareTo(KaleoTaskForm kaleoTaskForm) {
		return _kaleoTaskForm.compareTo(kaleoTaskForm);
	}

	/**
	* Returns the company ID of this kaleo task form.
	*
	* @return the company ID of this kaleo task form
	*/
	@Override
	public long getCompanyId() {
		return _kaleoTaskForm.getCompanyId();
	}

	/**
	* Returns the create date of this kaleo task form.
	*
	* @return the create date of this kaleo task form
	*/
	@Override
	public Date getCreateDate() {
		return _kaleoTaskForm.getCreateDate();
	}

	/**
	* Returns the description of this kaleo task form.
	*
	* @return the description of this kaleo task form
	*/
	@Override
	public java.lang.String getDescription() {
		return _kaleoTaskForm.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _kaleoTaskForm.getExpandoBridge();
	}

	/**
	* Returns the form company ID of this kaleo task form.
	*
	* @return the form company ID of this kaleo task form
	*/
	@Override
	public long getFormCompanyId() {
		return _kaleoTaskForm.getFormCompanyId();
	}

	/**
	* Returns the form definition of this kaleo task form.
	*
	* @return the form definition of this kaleo task form
	*/
	@Override
	public java.lang.String getFormDefinition() {
		return _kaleoTaskForm.getFormDefinition();
	}

	/**
	* Returns the form group ID of this kaleo task form.
	*
	* @return the form group ID of this kaleo task form
	*/
	@Override
	public long getFormGroupId() {
		return _kaleoTaskForm.getFormGroupId();
	}

	/**
	* Returns the form ID of this kaleo task form.
	*
	* @return the form ID of this kaleo task form
	*/
	@Override
	public long getFormId() {
		return _kaleoTaskForm.getFormId();
	}

	/**
	* Returns the form uuid of this kaleo task form.
	*
	* @return the form uuid of this kaleo task form
	*/
	@Override
	public java.lang.String getFormUuid() {
		return _kaleoTaskForm.getFormUuid();
	}

	/**
	* Returns the group ID of this kaleo task form.
	*
	* @return the group ID of this kaleo task form
	*/
	@Override
	public long getGroupId() {
		return _kaleoTaskForm.getGroupId();
	}

	/**
	* Returns the kaleo definition ID of this kaleo task form.
	*
	* @return the kaleo definition ID of this kaleo task form
	*/
	@Override
	public long getKaleoDefinitionId() {
		return _kaleoTaskForm.getKaleoDefinitionId();
	}

	/**
	* Returns the kaleo node ID of this kaleo task form.
	*
	* @return the kaleo node ID of this kaleo task form
	*/
	@Override
	public long getKaleoNodeId() {
		return _kaleoTaskForm.getKaleoNodeId();
	}

	/**
	* Returns the kaleo task form ID of this kaleo task form.
	*
	* @return the kaleo task form ID of this kaleo task form
	*/
	@Override
	public long getKaleoTaskFormId() {
		return _kaleoTaskForm.getKaleoTaskFormId();
	}

	/**
	* Returns the kaleo task ID of this kaleo task form.
	*
	* @return the kaleo task ID of this kaleo task form
	*/
	@Override
	public long getKaleoTaskId() {
		return _kaleoTaskForm.getKaleoTaskId();
	}

	/**
	* Returns the kaleo task name of this kaleo task form.
	*
	* @return the kaleo task name of this kaleo task form
	*/
	@Override
	public java.lang.String getKaleoTaskName() {
		return _kaleoTaskForm.getKaleoTaskName();
	}

	/**
	* Returns the metadata of this kaleo task form.
	*
	* @return the metadata of this kaleo task form
	*/
	@Override
	public java.lang.String getMetadata() {
		return _kaleoTaskForm.getMetadata();
	}

	/**
	* Returns the modified date of this kaleo task form.
	*
	* @return the modified date of this kaleo task form
	*/
	@Override
	public Date getModifiedDate() {
		return _kaleoTaskForm.getModifiedDate();
	}

	/**
	* Returns the name of this kaleo task form.
	*
	* @return the name of this kaleo task form
	*/
	@Override
	public java.lang.String getName() {
		return _kaleoTaskForm.getName();
	}

	/**
	* Returns the primary key of this kaleo task form.
	*
	* @return the primary key of this kaleo task form
	*/
	@Override
	public long getPrimaryKey() {
		return _kaleoTaskForm.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoTaskForm.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this kaleo task form.
	*
	* @return the priority of this kaleo task form
	*/
	@Override
	public int getPriority() {
		return _kaleoTaskForm.getPriority();
	}

	/**
	* Returns the user ID of this kaleo task form.
	*
	* @return the user ID of this kaleo task form
	*/
	@Override
	public long getUserId() {
		return _kaleoTaskForm.getUserId();
	}

	/**
	* Returns the user name of this kaleo task form.
	*
	* @return the user name of this kaleo task form
	*/
	@Override
	public java.lang.String getUserName() {
		return _kaleoTaskForm.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo task form.
	*
	* @return the user uuid of this kaleo task form
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _kaleoTaskForm.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _kaleoTaskForm.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _kaleoTaskForm.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _kaleoTaskForm.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _kaleoTaskForm.isNew();
	}

	@Override
	public void persist() {
		_kaleoTaskForm.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_kaleoTaskForm.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this kaleo task form.
	*
	* @param companyId the company ID of this kaleo task form
	*/
	@Override
	public void setCompanyId(long companyId) {
		_kaleoTaskForm.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo task form.
	*
	* @param createDate the create date of this kaleo task form
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_kaleoTaskForm.setCreateDate(createDate);
	}

	/**
	* Sets the description of this kaleo task form.
	*
	* @param description the description of this kaleo task form
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_kaleoTaskForm.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_kaleoTaskForm.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_kaleoTaskForm.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_kaleoTaskForm.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the form company ID of this kaleo task form.
	*
	* @param formCompanyId the form company ID of this kaleo task form
	*/
	@Override
	public void setFormCompanyId(long formCompanyId) {
		_kaleoTaskForm.setFormCompanyId(formCompanyId);
	}

	/**
	* Sets the form definition of this kaleo task form.
	*
	* @param formDefinition the form definition of this kaleo task form
	*/
	@Override
	public void setFormDefinition(java.lang.String formDefinition) {
		_kaleoTaskForm.setFormDefinition(formDefinition);
	}

	/**
	* Sets the form group ID of this kaleo task form.
	*
	* @param formGroupId the form group ID of this kaleo task form
	*/
	@Override
	public void setFormGroupId(long formGroupId) {
		_kaleoTaskForm.setFormGroupId(formGroupId);
	}

	/**
	* Sets the form ID of this kaleo task form.
	*
	* @param formId the form ID of this kaleo task form
	*/
	@Override
	public void setFormId(long formId) {
		_kaleoTaskForm.setFormId(formId);
	}

	/**
	* Sets the form uuid of this kaleo task form.
	*
	* @param formUuid the form uuid of this kaleo task form
	*/
	@Override
	public void setFormUuid(java.lang.String formUuid) {
		_kaleoTaskForm.setFormUuid(formUuid);
	}

	/**
	* Sets the group ID of this kaleo task form.
	*
	* @param groupId the group ID of this kaleo task form
	*/
	@Override
	public void setGroupId(long groupId) {
		_kaleoTaskForm.setGroupId(groupId);
	}

	/**
	* Sets the kaleo definition ID of this kaleo task form.
	*
	* @param kaleoDefinitionId the kaleo definition ID of this kaleo task form
	*/
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		_kaleoTaskForm.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Sets the kaleo node ID of this kaleo task form.
	*
	* @param kaleoNodeId the kaleo node ID of this kaleo task form
	*/
	@Override
	public void setKaleoNodeId(long kaleoNodeId) {
		_kaleoTaskForm.setKaleoNodeId(kaleoNodeId);
	}

	/**
	* Sets the kaleo task form ID of this kaleo task form.
	*
	* @param kaleoTaskFormId the kaleo task form ID of this kaleo task form
	*/
	@Override
	public void setKaleoTaskFormId(long kaleoTaskFormId) {
		_kaleoTaskForm.setKaleoTaskFormId(kaleoTaskFormId);
	}

	/**
	* Sets the kaleo task ID of this kaleo task form.
	*
	* @param kaleoTaskId the kaleo task ID of this kaleo task form
	*/
	@Override
	public void setKaleoTaskId(long kaleoTaskId) {
		_kaleoTaskForm.setKaleoTaskId(kaleoTaskId);
	}

	/**
	* Sets the kaleo task name of this kaleo task form.
	*
	* @param kaleoTaskName the kaleo task name of this kaleo task form
	*/
	@Override
	public void setKaleoTaskName(java.lang.String kaleoTaskName) {
		_kaleoTaskForm.setKaleoTaskName(kaleoTaskName);
	}

	/**
	* Sets the metadata of this kaleo task form.
	*
	* @param metadata the metadata of this kaleo task form
	*/
	@Override
	public void setMetadata(java.lang.String metadata) {
		_kaleoTaskForm.setMetadata(metadata);
	}

	/**
	* Sets the modified date of this kaleo task form.
	*
	* @param modifiedDate the modified date of this kaleo task form
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_kaleoTaskForm.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this kaleo task form.
	*
	* @param name the name of this kaleo task form
	*/
	@Override
	public void setName(java.lang.String name) {
		_kaleoTaskForm.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_kaleoTaskForm.setNew(n);
	}

	/**
	* Sets the primary key of this kaleo task form.
	*
	* @param primaryKey the primary key of this kaleo task form
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_kaleoTaskForm.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_kaleoTaskForm.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this kaleo task form.
	*
	* @param priority the priority of this kaleo task form
	*/
	@Override
	public void setPriority(int priority) {
		_kaleoTaskForm.setPriority(priority);
	}

	/**
	* Sets the user ID of this kaleo task form.
	*
	* @param userId the user ID of this kaleo task form
	*/
	@Override
	public void setUserId(long userId) {
		_kaleoTaskForm.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo task form.
	*
	* @param userName the user name of this kaleo task form
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_kaleoTaskForm.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo task form.
	*
	* @param userUuid the user uuid of this kaleo task form
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_kaleoTaskForm.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<KaleoTaskForm> toCacheModel() {
		return _kaleoTaskForm.toCacheModel();
	}

	@Override
	public KaleoTaskForm toEscapedModel() {
		return new KaleoTaskFormWrapper(_kaleoTaskForm.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _kaleoTaskForm.toString();
	}

	@Override
	public KaleoTaskForm toUnescapedModel() {
		return new KaleoTaskFormWrapper(_kaleoTaskForm.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _kaleoTaskForm.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoTaskFormWrapper)) {
			return false;
		}

		KaleoTaskFormWrapper kaleoTaskFormWrapper = (KaleoTaskFormWrapper)obj;

		if (Objects.equals(_kaleoTaskForm, kaleoTaskFormWrapper._kaleoTaskForm)) {
			return true;
		}

		return false;
	}

	@Override
	public KaleoTaskForm getWrappedModel() {
		return _kaleoTaskForm;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _kaleoTaskForm.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _kaleoTaskForm.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_kaleoTaskForm.resetOriginalValues();
	}

	private final KaleoTaskForm _kaleoTaskForm;
}