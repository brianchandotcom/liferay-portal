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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
public class KaleoTaskFormWrapper extends BaseModelWrapper<KaleoTaskForm>
	implements KaleoTaskForm, ModelWrapper<KaleoTaskForm> {
	public KaleoTaskFormWrapper(KaleoTaskForm kaleoTaskForm) {
		super(kaleoTaskForm);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("kaleoTaskFormId", getKaleoTaskFormId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

		Long kaleoDefinitionVersionId = (Long)attributes.get(
				"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
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

	/**
	* Returns the company ID of this kaleo task form.
	*
	* @return the company ID of this kaleo task form
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this kaleo task form.
	*
	* @return the create date of this kaleo task form
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the description of this kaleo task form.
	*
	* @return the description of this kaleo task form
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the form company ID of this kaleo task form.
	*
	* @return the form company ID of this kaleo task form
	*/
	@Override
	public long getFormCompanyId() {
		return model.getFormCompanyId();
	}

	/**
	* Returns the form definition of this kaleo task form.
	*
	* @return the form definition of this kaleo task form
	*/
	@Override
	public String getFormDefinition() {
		return model.getFormDefinition();
	}

	/**
	* Returns the form group ID of this kaleo task form.
	*
	* @return the form group ID of this kaleo task form
	*/
	@Override
	public long getFormGroupId() {
		return model.getFormGroupId();
	}

	/**
	* Returns the form ID of this kaleo task form.
	*
	* @return the form ID of this kaleo task form
	*/
	@Override
	public long getFormId() {
		return model.getFormId();
	}

	/**
	* Returns the form uuid of this kaleo task form.
	*
	* @return the form uuid of this kaleo task form
	*/
	@Override
	public String getFormUuid() {
		return model.getFormUuid();
	}

	/**
	* Returns the group ID of this kaleo task form.
	*
	* @return the group ID of this kaleo task form
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo task form.
	*
	* @return the kaleo definition version ID of this kaleo task form
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo node ID of this kaleo task form.
	*
	* @return the kaleo node ID of this kaleo task form
	*/
	@Override
	public long getKaleoNodeId() {
		return model.getKaleoNodeId();
	}

	/**
	* Returns the kaleo task form ID of this kaleo task form.
	*
	* @return the kaleo task form ID of this kaleo task form
	*/
	@Override
	public long getKaleoTaskFormId() {
		return model.getKaleoTaskFormId();
	}

	/**
	* Returns the kaleo task ID of this kaleo task form.
	*
	* @return the kaleo task ID of this kaleo task form
	*/
	@Override
	public long getKaleoTaskId() {
		return model.getKaleoTaskId();
	}

	/**
	* Returns the kaleo task name of this kaleo task form.
	*
	* @return the kaleo task name of this kaleo task form
	*/
	@Override
	public String getKaleoTaskName() {
		return model.getKaleoTaskName();
	}

	/**
	* Returns the metadata of this kaleo task form.
	*
	* @return the metadata of this kaleo task form
	*/
	@Override
	public String getMetadata() {
		return model.getMetadata();
	}

	/**
	* Returns the modified date of this kaleo task form.
	*
	* @return the modified date of this kaleo task form
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this kaleo task form.
	*
	* @return the mvcc version of this kaleo task form
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the name of this kaleo task form.
	*
	* @return the name of this kaleo task form
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the primary key of this kaleo task form.
	*
	* @return the primary key of this kaleo task form
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the priority of this kaleo task form.
	*
	* @return the priority of this kaleo task form
	*/
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	* Returns the user ID of this kaleo task form.
	*
	* @return the user ID of this kaleo task form
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo task form.
	*
	* @return the user name of this kaleo task form
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo task form.
	*
	* @return the user uuid of this kaleo task form
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
	* Sets the company ID of this kaleo task form.
	*
	* @param companyId the company ID of this kaleo task form
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo task form.
	*
	* @param createDate the create date of this kaleo task form
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this kaleo task form.
	*
	* @param description the description of this kaleo task form
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the form company ID of this kaleo task form.
	*
	* @param formCompanyId the form company ID of this kaleo task form
	*/
	@Override
	public void setFormCompanyId(long formCompanyId) {
		model.setFormCompanyId(formCompanyId);
	}

	/**
	* Sets the form definition of this kaleo task form.
	*
	* @param formDefinition the form definition of this kaleo task form
	*/
	@Override
	public void setFormDefinition(String formDefinition) {
		model.setFormDefinition(formDefinition);
	}

	/**
	* Sets the form group ID of this kaleo task form.
	*
	* @param formGroupId the form group ID of this kaleo task form
	*/
	@Override
	public void setFormGroupId(long formGroupId) {
		model.setFormGroupId(formGroupId);
	}

	/**
	* Sets the form ID of this kaleo task form.
	*
	* @param formId the form ID of this kaleo task form
	*/
	@Override
	public void setFormId(long formId) {
		model.setFormId(formId);
	}

	/**
	* Sets the form uuid of this kaleo task form.
	*
	* @param formUuid the form uuid of this kaleo task form
	*/
	@Override
	public void setFormUuid(String formUuid) {
		model.setFormUuid(formUuid);
	}

	/**
	* Sets the group ID of this kaleo task form.
	*
	* @param groupId the group ID of this kaleo task form
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo task form.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo task form
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo node ID of this kaleo task form.
	*
	* @param kaleoNodeId the kaleo node ID of this kaleo task form
	*/
	@Override
	public void setKaleoNodeId(long kaleoNodeId) {
		model.setKaleoNodeId(kaleoNodeId);
	}

	/**
	* Sets the kaleo task form ID of this kaleo task form.
	*
	* @param kaleoTaskFormId the kaleo task form ID of this kaleo task form
	*/
	@Override
	public void setKaleoTaskFormId(long kaleoTaskFormId) {
		model.setKaleoTaskFormId(kaleoTaskFormId);
	}

	/**
	* Sets the kaleo task ID of this kaleo task form.
	*
	* @param kaleoTaskId the kaleo task ID of this kaleo task form
	*/
	@Override
	public void setKaleoTaskId(long kaleoTaskId) {
		model.setKaleoTaskId(kaleoTaskId);
	}

	/**
	* Sets the kaleo task name of this kaleo task form.
	*
	* @param kaleoTaskName the kaleo task name of this kaleo task form
	*/
	@Override
	public void setKaleoTaskName(String kaleoTaskName) {
		model.setKaleoTaskName(kaleoTaskName);
	}

	/**
	* Sets the metadata of this kaleo task form.
	*
	* @param metadata the metadata of this kaleo task form
	*/
	@Override
	public void setMetadata(String metadata) {
		model.setMetadata(metadata);
	}

	/**
	* Sets the modified date of this kaleo task form.
	*
	* @param modifiedDate the modified date of this kaleo task form
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this kaleo task form.
	*
	* @param mvccVersion the mvcc version of this kaleo task form
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this kaleo task form.
	*
	* @param name the name of this kaleo task form
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the primary key of this kaleo task form.
	*
	* @param primaryKey the primary key of this kaleo task form
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the priority of this kaleo task form.
	*
	* @param priority the priority of this kaleo task form
	*/
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	* Sets the user ID of this kaleo task form.
	*
	* @param userId the user ID of this kaleo task form
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo task form.
	*
	* @param userName the user name of this kaleo task form
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo task form.
	*
	* @param userUuid the user uuid of this kaleo task form
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected KaleoTaskFormWrapper wrap(KaleoTaskForm kaleoTaskForm) {
		return new KaleoTaskFormWrapper(kaleoTaskForm);
	}
}