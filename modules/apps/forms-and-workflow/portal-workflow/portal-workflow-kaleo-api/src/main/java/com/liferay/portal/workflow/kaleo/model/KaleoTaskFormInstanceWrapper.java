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
 * This class is a wrapper for {@link KaleoTaskFormInstance}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormInstance
 * @generated
 */
@ProviderType
public class KaleoTaskFormInstanceWrapper implements KaleoTaskFormInstance,
	ModelWrapper<KaleoTaskFormInstance> {
	public KaleoTaskFormInstanceWrapper(
		KaleoTaskFormInstance kaleoTaskFormInstance) {
		_kaleoTaskFormInstance = kaleoTaskFormInstance;
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoTaskFormInstance.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoTaskFormInstance.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("kaleoTaskFormInstanceId", getKaleoTaskFormInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put("kaleoInstanceId", getKaleoInstanceId());
		attributes.put("kaleoTaskId", getKaleoTaskId());
		attributes.put("kaleoTaskInstanceTokenId", getKaleoTaskInstanceTokenId());
		attributes.put("kaleoTaskFormId", getKaleoTaskFormId());
		attributes.put("formValues", getFormValues());
		attributes.put("formValueEntryGroupId", getFormValueEntryGroupId());
		attributes.put("formValueEntryId", getFormValueEntryId());
		attributes.put("formValueEntryUuid", getFormValueEntryUuid());
		attributes.put("metadata", getMetadata());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long kaleoTaskFormInstanceId = (Long)attributes.get(
				"kaleoTaskFormInstanceId");

		if (kaleoTaskFormInstanceId != null) {
			setKaleoTaskFormInstanceId(kaleoTaskFormInstanceId);
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

		Long kaleoInstanceId = (Long)attributes.get("kaleoInstanceId");

		if (kaleoInstanceId != null) {
			setKaleoInstanceId(kaleoInstanceId);
		}

		Long kaleoTaskId = (Long)attributes.get("kaleoTaskId");

		if (kaleoTaskId != null) {
			setKaleoTaskId(kaleoTaskId);
		}

		Long kaleoTaskInstanceTokenId = (Long)attributes.get(
				"kaleoTaskInstanceTokenId");

		if (kaleoTaskInstanceTokenId != null) {
			setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
		}

		Long kaleoTaskFormId = (Long)attributes.get("kaleoTaskFormId");

		if (kaleoTaskFormId != null) {
			setKaleoTaskFormId(kaleoTaskFormId);
		}

		String formValues = (String)attributes.get("formValues");

		if (formValues != null) {
			setFormValues(formValues);
		}

		Long formValueEntryGroupId = (Long)attributes.get(
				"formValueEntryGroupId");

		if (formValueEntryGroupId != null) {
			setFormValueEntryGroupId(formValueEntryGroupId);
		}

		Long formValueEntryId = (Long)attributes.get("formValueEntryId");

		if (formValueEntryId != null) {
			setFormValueEntryId(formValueEntryId);
		}

		String formValueEntryUuid = (String)attributes.get("formValueEntryUuid");

		if (formValueEntryUuid != null) {
			setFormValueEntryUuid(formValueEntryUuid);
		}

		String metadata = (String)attributes.get("metadata");

		if (metadata != null) {
			setMetadata(metadata);
		}
	}

	@Override
	public KaleoTaskFormInstance toEscapedModel() {
		return new KaleoTaskFormInstanceWrapper(_kaleoTaskFormInstance.toEscapedModel());
	}

	@Override
	public KaleoTaskFormInstance toUnescapedModel() {
		return new KaleoTaskFormInstanceWrapper(_kaleoTaskFormInstance.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _kaleoTaskFormInstance.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _kaleoTaskFormInstance.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _kaleoTaskFormInstance.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _kaleoTaskFormInstance.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<KaleoTaskFormInstance> toCacheModel() {
		return _kaleoTaskFormInstance.toCacheModel();
	}

	@Override
	public int compareTo(KaleoTaskFormInstance kaleoTaskFormInstance) {
		return _kaleoTaskFormInstance.compareTo(kaleoTaskFormInstance);
	}

	@Override
	public int hashCode() {
		return _kaleoTaskFormInstance.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoTaskFormInstance.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new KaleoTaskFormInstanceWrapper((KaleoTaskFormInstance)_kaleoTaskFormInstance.clone());
	}

	/**
	* Returns the form value entry uuid of this kaleo task form instance.
	*
	* @return the form value entry uuid of this kaleo task form instance
	*/
	@Override
	public java.lang.String getFormValueEntryUuid() {
		return _kaleoTaskFormInstance.getFormValueEntryUuid();
	}

	/**
	* Returns the form values of this kaleo task form instance.
	*
	* @return the form values of this kaleo task form instance
	*/
	@Override
	public java.lang.String getFormValues() {
		return _kaleoTaskFormInstance.getFormValues();
	}

	/**
	* Returns the metadata of this kaleo task form instance.
	*
	* @return the metadata of this kaleo task form instance
	*/
	@Override
	public java.lang.String getMetadata() {
		return _kaleoTaskFormInstance.getMetadata();
	}

	/**
	* Returns the user name of this kaleo task form instance.
	*
	* @return the user name of this kaleo task form instance
	*/
	@Override
	public java.lang.String getUserName() {
		return _kaleoTaskFormInstance.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo task form instance.
	*
	* @return the user uuid of this kaleo task form instance
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _kaleoTaskFormInstance.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _kaleoTaskFormInstance.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _kaleoTaskFormInstance.toXmlString();
	}

	/**
	* Returns the create date of this kaleo task form instance.
	*
	* @return the create date of this kaleo task form instance
	*/
	@Override
	public Date getCreateDate() {
		return _kaleoTaskFormInstance.getCreateDate();
	}

	/**
	* Returns the modified date of this kaleo task form instance.
	*
	* @return the modified date of this kaleo task form instance
	*/
	@Override
	public Date getModifiedDate() {
		return _kaleoTaskFormInstance.getModifiedDate();
	}

	/**
	* Returns the company ID of this kaleo task form instance.
	*
	* @return the company ID of this kaleo task form instance
	*/
	@Override
	public long getCompanyId() {
		return _kaleoTaskFormInstance.getCompanyId();
	}

	/**
	* Returns the form value entry group ID of this kaleo task form instance.
	*
	* @return the form value entry group ID of this kaleo task form instance
	*/
	@Override
	public long getFormValueEntryGroupId() {
		return _kaleoTaskFormInstance.getFormValueEntryGroupId();
	}

	/**
	* Returns the form value entry ID of this kaleo task form instance.
	*
	* @return the form value entry ID of this kaleo task form instance
	*/
	@Override
	public long getFormValueEntryId() {
		return _kaleoTaskFormInstance.getFormValueEntryId();
	}

	/**
	* Returns the group ID of this kaleo task form instance.
	*
	* @return the group ID of this kaleo task form instance
	*/
	@Override
	public long getGroupId() {
		return _kaleoTaskFormInstance.getGroupId();
	}

	/**
	* Returns the kaleo definition ID of this kaleo task form instance.
	*
	* @return the kaleo definition ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoDefinitionId() {
		return _kaleoTaskFormInstance.getKaleoDefinitionId();
	}

	/**
	* Returns the kaleo instance ID of this kaleo task form instance.
	*
	* @return the kaleo instance ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoInstanceId() {
		return _kaleoTaskFormInstance.getKaleoInstanceId();
	}

	/**
	* Returns the kaleo task form ID of this kaleo task form instance.
	*
	* @return the kaleo task form ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoTaskFormId() {
		return _kaleoTaskFormInstance.getKaleoTaskFormId();
	}

	/**
	* Returns the kaleo task form instance ID of this kaleo task form instance.
	*
	* @return the kaleo task form instance ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoTaskFormInstanceId() {
		return _kaleoTaskFormInstance.getKaleoTaskFormInstanceId();
	}

	/**
	* Returns the kaleo task ID of this kaleo task form instance.
	*
	* @return the kaleo task ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoTaskId() {
		return _kaleoTaskFormInstance.getKaleoTaskId();
	}

	/**
	* Returns the kaleo task instance token ID of this kaleo task form instance.
	*
	* @return the kaleo task instance token ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoTaskInstanceTokenId() {
		return _kaleoTaskFormInstance.getKaleoTaskInstanceTokenId();
	}

	/**
	* Returns the primary key of this kaleo task form instance.
	*
	* @return the primary key of this kaleo task form instance
	*/
	@Override
	public long getPrimaryKey() {
		return _kaleoTaskFormInstance.getPrimaryKey();
	}

	/**
	* Returns the user ID of this kaleo task form instance.
	*
	* @return the user ID of this kaleo task form instance
	*/
	@Override
	public long getUserId() {
		return _kaleoTaskFormInstance.getUserId();
	}

	@Override
	public void persist() {
		_kaleoTaskFormInstance.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_kaleoTaskFormInstance.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this kaleo task form instance.
	*
	* @param companyId the company ID of this kaleo task form instance
	*/
	@Override
	public void setCompanyId(long companyId) {
		_kaleoTaskFormInstance.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo task form instance.
	*
	* @param createDate the create date of this kaleo task form instance
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_kaleoTaskFormInstance.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_kaleoTaskFormInstance.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_kaleoTaskFormInstance.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_kaleoTaskFormInstance.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the form value entry group ID of this kaleo task form instance.
	*
	* @param formValueEntryGroupId the form value entry group ID of this kaleo task form instance
	*/
	@Override
	public void setFormValueEntryGroupId(long formValueEntryGroupId) {
		_kaleoTaskFormInstance.setFormValueEntryGroupId(formValueEntryGroupId);
	}

	/**
	* Sets the form value entry ID of this kaleo task form instance.
	*
	* @param formValueEntryId the form value entry ID of this kaleo task form instance
	*/
	@Override
	public void setFormValueEntryId(long formValueEntryId) {
		_kaleoTaskFormInstance.setFormValueEntryId(formValueEntryId);
	}

	/**
	* Sets the form value entry uuid of this kaleo task form instance.
	*
	* @param formValueEntryUuid the form value entry uuid of this kaleo task form instance
	*/
	@Override
	public void setFormValueEntryUuid(java.lang.String formValueEntryUuid) {
		_kaleoTaskFormInstance.setFormValueEntryUuid(formValueEntryUuid);
	}

	/**
	* Sets the form values of this kaleo task form instance.
	*
	* @param formValues the form values of this kaleo task form instance
	*/
	@Override
	public void setFormValues(java.lang.String formValues) {
		_kaleoTaskFormInstance.setFormValues(formValues);
	}

	/**
	* Sets the group ID of this kaleo task form instance.
	*
	* @param groupId the group ID of this kaleo task form instance
	*/
	@Override
	public void setGroupId(long groupId) {
		_kaleoTaskFormInstance.setGroupId(groupId);
	}

	/**
	* Sets the kaleo definition ID of this kaleo task form instance.
	*
	* @param kaleoDefinitionId the kaleo definition ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		_kaleoTaskFormInstance.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Sets the kaleo instance ID of this kaleo task form instance.
	*
	* @param kaleoInstanceId the kaleo instance ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		_kaleoTaskFormInstance.setKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Sets the kaleo task form ID of this kaleo task form instance.
	*
	* @param kaleoTaskFormId the kaleo task form ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoTaskFormId(long kaleoTaskFormId) {
		_kaleoTaskFormInstance.setKaleoTaskFormId(kaleoTaskFormId);
	}

	/**
	* Sets the kaleo task form instance ID of this kaleo task form instance.
	*
	* @param kaleoTaskFormInstanceId the kaleo task form instance ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoTaskFormInstanceId(long kaleoTaskFormInstanceId) {
		_kaleoTaskFormInstance.setKaleoTaskFormInstanceId(kaleoTaskFormInstanceId);
	}

	/**
	* Sets the kaleo task ID of this kaleo task form instance.
	*
	* @param kaleoTaskId the kaleo task ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoTaskId(long kaleoTaskId) {
		_kaleoTaskFormInstance.setKaleoTaskId(kaleoTaskId);
	}

	/**
	* Sets the kaleo task instance token ID of this kaleo task form instance.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		_kaleoTaskFormInstance.setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Sets the metadata of this kaleo task form instance.
	*
	* @param metadata the metadata of this kaleo task form instance
	*/
	@Override
	public void setMetadata(java.lang.String metadata) {
		_kaleoTaskFormInstance.setMetadata(metadata);
	}

	/**
	* Sets the modified date of this kaleo task form instance.
	*
	* @param modifiedDate the modified date of this kaleo task form instance
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_kaleoTaskFormInstance.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_kaleoTaskFormInstance.setNew(n);
	}

	/**
	* Sets the primary key of this kaleo task form instance.
	*
	* @param primaryKey the primary key of this kaleo task form instance
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_kaleoTaskFormInstance.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_kaleoTaskFormInstance.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this kaleo task form instance.
	*
	* @param userId the user ID of this kaleo task form instance
	*/
	@Override
	public void setUserId(long userId) {
		_kaleoTaskFormInstance.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo task form instance.
	*
	* @param userName the user name of this kaleo task form instance
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_kaleoTaskFormInstance.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo task form instance.
	*
	* @param userUuid the user uuid of this kaleo task form instance
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_kaleoTaskFormInstance.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoTaskFormInstanceWrapper)) {
			return false;
		}

		KaleoTaskFormInstanceWrapper kaleoTaskFormInstanceWrapper = (KaleoTaskFormInstanceWrapper)obj;

		if (Objects.equals(_kaleoTaskFormInstance,
					kaleoTaskFormInstanceWrapper._kaleoTaskFormInstance)) {
			return true;
		}

		return false;
	}

	@Override
	public KaleoTaskFormInstance getWrappedModel() {
		return _kaleoTaskFormInstance;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _kaleoTaskFormInstance.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _kaleoTaskFormInstance.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_kaleoTaskFormInstance.resetOriginalValues();
	}

	private final KaleoTaskFormInstance _kaleoTaskFormInstance;
}