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
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link KaleoInstance}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoInstance
 * @generated
 */
@ProviderType
public class KaleoInstanceWrapper extends BaseModelWrapper<KaleoInstance>
	implements KaleoInstance, ModelWrapper<KaleoInstance> {
	public KaleoInstanceWrapper(KaleoInstance kaleoInstance) {
		super(kaleoInstance);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("kaleoInstanceId", getKaleoInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoDefinitionName", getKaleoDefinitionName());
		attributes.put("kaleoDefinitionVersion", getKaleoDefinitionVersion());
		attributes.put("rootKaleoInstanceTokenId", getRootKaleoInstanceTokenId());
		attributes.put("className", getClassName());
		attributes.put("classPK", getClassPK());
		attributes.put("completed", isCompleted());
		attributes.put("completionDate", getCompletionDate());
		attributes.put("workflowContext", getWorkflowContext());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long kaleoInstanceId = (Long)attributes.get("kaleoInstanceId");

		if (kaleoInstanceId != null) {
			setKaleoInstanceId(kaleoInstanceId);
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

		String kaleoDefinitionName = (String)attributes.get(
				"kaleoDefinitionName");

		if (kaleoDefinitionName != null) {
			setKaleoDefinitionName(kaleoDefinitionName);
		}

		Integer kaleoDefinitionVersion = (Integer)attributes.get(
				"kaleoDefinitionVersion");

		if (kaleoDefinitionVersion != null) {
			setKaleoDefinitionVersion(kaleoDefinitionVersion);
		}

		Long rootKaleoInstanceTokenId = (Long)attributes.get(
				"rootKaleoInstanceTokenId");

		if (rootKaleoInstanceTokenId != null) {
			setRootKaleoInstanceTokenId(rootKaleoInstanceTokenId);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Boolean completed = (Boolean)attributes.get("completed");

		if (completed != null) {
			setCompleted(completed);
		}

		Date completionDate = (Date)attributes.get("completionDate");

		if (completionDate != null) {
			setCompletionDate(completionDate);
		}

		String workflowContext = (String)attributes.get("workflowContext");

		if (workflowContext != null) {
			setWorkflowContext(workflowContext);
		}
	}

	/**
	* Returns the class name of this kaleo instance.
	*
	* @return the class name of this kaleo instance
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class pk of this kaleo instance.
	*
	* @return the class pk of this kaleo instance
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this kaleo instance.
	*
	* @return the company ID of this kaleo instance
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the completed of this kaleo instance.
	*
	* @return the completed of this kaleo instance
	*/
	@Override
	public boolean getCompleted() {
		return model.getCompleted();
	}

	/**
	* Returns the completion date of this kaleo instance.
	*
	* @return the completion date of this kaleo instance
	*/
	@Override
	public Date getCompletionDate() {
		return model.getCompletionDate();
	}

	/**
	* Returns the create date of this kaleo instance.
	*
	* @return the create date of this kaleo instance
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this kaleo instance.
	*
	* @return the group ID of this kaleo instance
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo definition name of this kaleo instance.
	*
	* @return the kaleo definition name of this kaleo instance
	*/
	@Override
	public String getKaleoDefinitionName() {
		return model.getKaleoDefinitionName();
	}

	/**
	* Returns the kaleo definition version of this kaleo instance.
	*
	* @return the kaleo definition version of this kaleo instance
	*/
	@Override
	public int getKaleoDefinitionVersion() {
		return model.getKaleoDefinitionVersion();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo instance.
	*
	* @return the kaleo definition version ID of this kaleo instance
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo instance ID of this kaleo instance.
	*
	* @return the kaleo instance ID of this kaleo instance
	*/
	@Override
	public long getKaleoInstanceId() {
		return model.getKaleoInstanceId();
	}

	/**
	* Returns the modified date of this kaleo instance.
	*
	* @return the modified date of this kaleo instance
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this kaleo instance.
	*
	* @return the mvcc version of this kaleo instance
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this kaleo instance.
	*
	* @return the primary key of this kaleo instance
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public KaleoInstanceToken getRootKaleoInstanceToken(
		Map<String, Serializable> workflowContext, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getRootKaleoInstanceToken(workflowContext, serviceContext);
	}

	@Override
	public KaleoInstanceToken getRootKaleoInstanceToken(
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getRootKaleoInstanceToken(serviceContext);
	}

	/**
	* Returns the root kaleo instance token ID of this kaleo instance.
	*
	* @return the root kaleo instance token ID of this kaleo instance
	*/
	@Override
	public long getRootKaleoInstanceTokenId() {
		return model.getRootKaleoInstanceTokenId();
	}

	/**
	* Returns the user ID of this kaleo instance.
	*
	* @return the user ID of this kaleo instance
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo instance.
	*
	* @return the user name of this kaleo instance
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo instance.
	*
	* @return the user uuid of this kaleo instance
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the workflow context of this kaleo instance.
	*
	* @return the workflow context of this kaleo instance
	*/
	@Override
	public String getWorkflowContext() {
		return model.getWorkflowContext();
	}

	/**
	* Returns <code>true</code> if this kaleo instance is completed.
	*
	* @return <code>true</code> if this kaleo instance is completed; <code>false</code> otherwise
	*/
	@Override
	public boolean isCompleted() {
		return model.isCompleted();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the class name of this kaleo instance.
	*
	* @param className the class name of this kaleo instance
	*/
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class pk of this kaleo instance.
	*
	* @param classPK the class pk of this kaleo instance
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this kaleo instance.
	*
	* @param companyId the company ID of this kaleo instance
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets whether this kaleo instance is completed.
	*
	* @param completed the completed of this kaleo instance
	*/
	@Override
	public void setCompleted(boolean completed) {
		model.setCompleted(completed);
	}

	/**
	* Sets the completion date of this kaleo instance.
	*
	* @param completionDate the completion date of this kaleo instance
	*/
	@Override
	public void setCompletionDate(Date completionDate) {
		model.setCompletionDate(completionDate);
	}

	/**
	* Sets the create date of this kaleo instance.
	*
	* @param createDate the create date of this kaleo instance
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this kaleo instance.
	*
	* @param groupId the group ID of this kaleo instance
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo definition name of this kaleo instance.
	*
	* @param kaleoDefinitionName the kaleo definition name of this kaleo instance
	*/
	@Override
	public void setKaleoDefinitionName(String kaleoDefinitionName) {
		model.setKaleoDefinitionName(kaleoDefinitionName);
	}

	/**
	* Sets the kaleo definition version of this kaleo instance.
	*
	* @param kaleoDefinitionVersion the kaleo definition version of this kaleo instance
	*/
	@Override
	public void setKaleoDefinitionVersion(int kaleoDefinitionVersion) {
		model.setKaleoDefinitionVersion(kaleoDefinitionVersion);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo instance.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo instance
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo instance ID of this kaleo instance.
	*
	* @param kaleoInstanceId the kaleo instance ID of this kaleo instance
	*/
	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		model.setKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Sets the modified date of this kaleo instance.
	*
	* @param modifiedDate the modified date of this kaleo instance
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this kaleo instance.
	*
	* @param mvccVersion the mvcc version of this kaleo instance
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this kaleo instance.
	*
	* @param primaryKey the primary key of this kaleo instance
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the root kaleo instance token ID of this kaleo instance.
	*
	* @param rootKaleoInstanceTokenId the root kaleo instance token ID of this kaleo instance
	*/
	@Override
	public void setRootKaleoInstanceTokenId(long rootKaleoInstanceTokenId) {
		model.setRootKaleoInstanceTokenId(rootKaleoInstanceTokenId);
	}

	/**
	* Sets the user ID of this kaleo instance.
	*
	* @param userId the user ID of this kaleo instance
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo instance.
	*
	* @param userName the user name of this kaleo instance
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo instance.
	*
	* @param userUuid the user uuid of this kaleo instance
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the workflow context of this kaleo instance.
	*
	* @param workflowContext the workflow context of this kaleo instance
	*/
	@Override
	public void setWorkflowContext(String workflowContext) {
		model.setWorkflowContext(workflowContext);
	}

	@Override
	protected KaleoInstanceWrapper wrap(KaleoInstance kaleoInstance) {
		return new KaleoInstanceWrapper(kaleoInstance);
	}
}