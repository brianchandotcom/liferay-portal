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

package com.liferay.portal.workflow.kaleo.forms.model;

import aQute.bnd.annotation.ProviderType;

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
 * This class is a wrapper for {@link KaleoProcess}.
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcess
 * @generated
 */
@ProviderType
public class KaleoProcessWrapper implements KaleoProcess,
	ModelWrapper<KaleoProcess> {
	public KaleoProcessWrapper(KaleoProcess kaleoProcess) {
		_kaleoProcess = kaleoProcess;
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoProcess.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoProcess.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("kaleoProcessId", getKaleoProcessId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("DDLRecordSetId", getDDLRecordSetId());
		attributes.put("DDMTemplateId", getDDMTemplateId());
		attributes.put("workflowDefinitionName", getWorkflowDefinitionName());
		attributes.put("workflowDefinitionVersion",
			getWorkflowDefinitionVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long kaleoProcessId = (Long)attributes.get("kaleoProcessId");

		if (kaleoProcessId != null) {
			setKaleoProcessId(kaleoProcessId);
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

		Long DDLRecordSetId = (Long)attributes.get("DDLRecordSetId");

		if (DDLRecordSetId != null) {
			setDDLRecordSetId(DDLRecordSetId);
		}

		Long DDMTemplateId = (Long)attributes.get("DDMTemplateId");

		if (DDMTemplateId != null) {
			setDDMTemplateId(DDMTemplateId);
		}

		String workflowDefinitionName = (String)attributes.get(
				"workflowDefinitionName");

		if (workflowDefinitionName != null) {
			setWorkflowDefinitionName(workflowDefinitionName);
		}

		Integer workflowDefinitionVersion = (Integer)attributes.get(
				"workflowDefinitionVersion");

		if (workflowDefinitionVersion != null) {
			setWorkflowDefinitionVersion(workflowDefinitionVersion);
		}
	}

	@Override
	public Object clone() {
		return new KaleoProcessWrapper((KaleoProcess)_kaleoProcess.clone());
	}

	@Override
	public int compareTo(KaleoProcess kaleoProcess) {
		return _kaleoProcess.compareTo(kaleoProcess);
	}

	/**
	* Returns the company ID of this kaleo process.
	*
	* @return the company ID of this kaleo process
	*/
	@Override
	public long getCompanyId() {
		return _kaleoProcess.getCompanyId();
	}

	/**
	* Returns the create date of this kaleo process.
	*
	* @return the create date of this kaleo process
	*/
	@Override
	public Date getCreateDate() {
		return _kaleoProcess.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordSet getDDLRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoProcess.getDDLRecordSet();
	}

	/**
	* Returns the ddl record set ID of this kaleo process.
	*
	* @return the ddl record set ID of this kaleo process
	*/
	@Override
	public long getDDLRecordSetId() {
		return _kaleoProcess.getDDLRecordSetId();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoProcess.getDDMTemplate();
	}

	/**
	* Returns the ddm template ID of this kaleo process.
	*
	* @return the ddm template ID of this kaleo process
	*/
	@Override
	public long getDDMTemplateId() {
		return _kaleoProcess.getDDMTemplateId();
	}

	@Override
	public String getDescription()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoProcess.getDescription();
	}

	@Override
	public String getDescription(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoProcess.getDescription(locale);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _kaleoProcess.getExpandoBridge();
	}

	/**
	* Returns the group ID of this kaleo process.
	*
	* @return the group ID of this kaleo process
	*/
	@Override
	public long getGroupId() {
		return _kaleoProcess.getGroupId();
	}

	/**
	* Returns the kaleo process ID of this kaleo process.
	*
	* @return the kaleo process ID of this kaleo process
	*/
	@Override
	public long getKaleoProcessId() {
		return _kaleoProcess.getKaleoProcessId();
	}

	@Override
	public java.util.List<KaleoProcessLink> getKaleoProcessLinks() {
		return _kaleoProcess.getKaleoProcessLinks();
	}

	/**
	* Returns the modified date of this kaleo process.
	*
	* @return the modified date of this kaleo process
	*/
	@Override
	public Date getModifiedDate() {
		return _kaleoProcess.getModifiedDate();
	}

	@Override
	public String getName()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoProcess.getName();
	}

	@Override
	public String getName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoProcess.getName(locale);
	}

	/**
	* Returns the primary key of this kaleo process.
	*
	* @return the primary key of this kaleo process
	*/
	@Override
	public long getPrimaryKey() {
		return _kaleoProcess.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoProcess.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this kaleo process.
	*
	* @return the user ID of this kaleo process
	*/
	@Override
	public long getUserId() {
		return _kaleoProcess.getUserId();
	}

	/**
	* Returns the user name of this kaleo process.
	*
	* @return the user name of this kaleo process
	*/
	@Override
	public String getUserName() {
		return _kaleoProcess.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo process.
	*
	* @return the user uuid of this kaleo process
	*/
	@Override
	public String getUserUuid() {
		return _kaleoProcess.getUserUuid();
	}

	/**
	* Returns the uuid of this kaleo process.
	*
	* @return the uuid of this kaleo process
	*/
	@Override
	public String getUuid() {
		return _kaleoProcess.getUuid();
	}

	@Override
	public String getWorkflowDefinition() {
		return _kaleoProcess.getWorkflowDefinition();
	}

	/**
	* Returns the workflow definition name of this kaleo process.
	*
	* @return the workflow definition name of this kaleo process
	*/
	@Override
	public String getWorkflowDefinitionName() {
		return _kaleoProcess.getWorkflowDefinitionName();
	}

	/**
	* Returns the workflow definition version of this kaleo process.
	*
	* @return the workflow definition version of this kaleo process
	*/
	@Override
	public int getWorkflowDefinitionVersion() {
		return _kaleoProcess.getWorkflowDefinitionVersion();
	}

	@Override
	public int hashCode() {
		return _kaleoProcess.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _kaleoProcess.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _kaleoProcess.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _kaleoProcess.isNew();
	}

	@Override
	public void persist() {
		_kaleoProcess.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_kaleoProcess.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this kaleo process.
	*
	* @param companyId the company ID of this kaleo process
	*/
	@Override
	public void setCompanyId(long companyId) {
		_kaleoProcess.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo process.
	*
	* @param createDate the create date of this kaleo process
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_kaleoProcess.setCreateDate(createDate);
	}

	/**
	* Sets the ddl record set ID of this kaleo process.
	*
	* @param DDLRecordSetId the ddl record set ID of this kaleo process
	*/
	@Override
	public void setDDLRecordSetId(long DDLRecordSetId) {
		_kaleoProcess.setDDLRecordSetId(DDLRecordSetId);
	}

	/**
	* Sets the ddm template ID of this kaleo process.
	*
	* @param DDMTemplateId the ddm template ID of this kaleo process
	*/
	@Override
	public void setDDMTemplateId(long DDMTemplateId) {
		_kaleoProcess.setDDMTemplateId(DDMTemplateId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_kaleoProcess.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_kaleoProcess.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_kaleoProcess.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this kaleo process.
	*
	* @param groupId the group ID of this kaleo process
	*/
	@Override
	public void setGroupId(long groupId) {
		_kaleoProcess.setGroupId(groupId);
	}

	/**
	* Sets the kaleo process ID of this kaleo process.
	*
	* @param kaleoProcessId the kaleo process ID of this kaleo process
	*/
	@Override
	public void setKaleoProcessId(long kaleoProcessId) {
		_kaleoProcess.setKaleoProcessId(kaleoProcessId);
	}

	/**
	* Sets the modified date of this kaleo process.
	*
	* @param modifiedDate the modified date of this kaleo process
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_kaleoProcess.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_kaleoProcess.setNew(n);
	}

	/**
	* Sets the primary key of this kaleo process.
	*
	* @param primaryKey the primary key of this kaleo process
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_kaleoProcess.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_kaleoProcess.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this kaleo process.
	*
	* @param userId the user ID of this kaleo process
	*/
	@Override
	public void setUserId(long userId) {
		_kaleoProcess.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo process.
	*
	* @param userName the user name of this kaleo process
	*/
	@Override
	public void setUserName(String userName) {
		_kaleoProcess.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo process.
	*
	* @param userUuid the user uuid of this kaleo process
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_kaleoProcess.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this kaleo process.
	*
	* @param uuid the uuid of this kaleo process
	*/
	@Override
	public void setUuid(String uuid) {
		_kaleoProcess.setUuid(uuid);
	}

	/**
	* Sets the workflow definition name of this kaleo process.
	*
	* @param workflowDefinitionName the workflow definition name of this kaleo process
	*/
	@Override
	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		_kaleoProcess.setWorkflowDefinitionName(workflowDefinitionName);
	}

	/**
	* Sets the workflow definition version of this kaleo process.
	*
	* @param workflowDefinitionVersion the workflow definition version of this kaleo process
	*/
	@Override
	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_kaleoProcess.setWorkflowDefinitionVersion(workflowDefinitionVersion);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<KaleoProcess> toCacheModel() {
		return _kaleoProcess.toCacheModel();
	}

	@Override
	public KaleoProcess toEscapedModel() {
		return new KaleoProcessWrapper(_kaleoProcess.toEscapedModel());
	}

	@Override
	public String toString() {
		return _kaleoProcess.toString();
	}

	@Override
	public KaleoProcess toUnescapedModel() {
		return new KaleoProcessWrapper(_kaleoProcess.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _kaleoProcess.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoProcessWrapper)) {
			return false;
		}

		KaleoProcessWrapper kaleoProcessWrapper = (KaleoProcessWrapper)obj;

		if (Objects.equals(_kaleoProcess, kaleoProcessWrapper._kaleoProcess)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _kaleoProcess.getStagedModelType();
	}

	@Override
	public KaleoProcess getWrappedModel() {
		return _kaleoProcess;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _kaleoProcess.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _kaleoProcess.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_kaleoProcess.resetOriginalValues();
	}

	private final KaleoProcess _kaleoProcess;
}