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
public class KaleoTaskFormInstanceWrapper extends BaseModelWrapper<KaleoTaskFormInstance>
	implements KaleoTaskFormInstance, ModelWrapper<KaleoTaskFormInstance> {
	public KaleoTaskFormInstanceWrapper(
		KaleoTaskFormInstance kaleoTaskFormInstance) {
		super(kaleoTaskFormInstance);
	}

	/**
	* Returns the company ID of this kaleo task form instance.
	*
	* @return the company ID of this kaleo task form instance
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this kaleo task form instance.
	*
	* @return the create date of this kaleo task form instance
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the form value entry group ID of this kaleo task form instance.
	*
	* @return the form value entry group ID of this kaleo task form instance
	*/
	@Override
	public long getFormValueEntryGroupId() {
		return model.getFormValueEntryGroupId();
	}

	/**
	* Returns the form value entry ID of this kaleo task form instance.
	*
	* @return the form value entry ID of this kaleo task form instance
	*/
	@Override
	public long getFormValueEntryId() {
		return model.getFormValueEntryId();
	}

	/**
	* Returns the form value entry uuid of this kaleo task form instance.
	*
	* @return the form value entry uuid of this kaleo task form instance
	*/
	@Override
	public String getFormValueEntryUuid() {
		return model.getFormValueEntryUuid();
	}

	/**
	* Returns the form values of this kaleo task form instance.
	*
	* @return the form values of this kaleo task form instance
	*/
	@Override
	public String getFormValues() {
		return model.getFormValues();
	}

	/**
	* Returns the group ID of this kaleo task form instance.
	*
	* @return the group ID of this kaleo task form instance
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo task form instance.
	*
	* @return the kaleo definition version ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo instance ID of this kaleo task form instance.
	*
	* @return the kaleo instance ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoInstanceId() {
		return model.getKaleoInstanceId();
	}

	/**
	* Returns the kaleo task form ID of this kaleo task form instance.
	*
	* @return the kaleo task form ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoTaskFormId() {
		return model.getKaleoTaskFormId();
	}

	/**
	* Returns the kaleo task form instance ID of this kaleo task form instance.
	*
	* @return the kaleo task form instance ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoTaskFormInstanceId() {
		return model.getKaleoTaskFormInstanceId();
	}

	/**
	* Returns the kaleo task ID of this kaleo task form instance.
	*
	* @return the kaleo task ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoTaskId() {
		return model.getKaleoTaskId();
	}

	/**
	* Returns the kaleo task instance token ID of this kaleo task form instance.
	*
	* @return the kaleo task instance token ID of this kaleo task form instance
	*/
	@Override
	public long getKaleoTaskInstanceTokenId() {
		return model.getKaleoTaskInstanceTokenId();
	}

	/**
	* Returns the metadata of this kaleo task form instance.
	*
	* @return the metadata of this kaleo task form instance
	*/
	@Override
	public String getMetadata() {
		return model.getMetadata();
	}

	/**
	* Returns the modified date of this kaleo task form instance.
	*
	* @return the modified date of this kaleo task form instance
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the primary key of this kaleo task form instance.
	*
	* @return the primary key of this kaleo task form instance
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this kaleo task form instance.
	*
	* @return the user ID of this kaleo task form instance
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo task form instance.
	*
	* @return the user name of this kaleo task form instance
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo task form instance.
	*
	* @return the user uuid of this kaleo task form instance
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
	* Sets the company ID of this kaleo task form instance.
	*
	* @param companyId the company ID of this kaleo task form instance
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo task form instance.
	*
	* @param createDate the create date of this kaleo task form instance
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the form value entry group ID of this kaleo task form instance.
	*
	* @param formValueEntryGroupId the form value entry group ID of this kaleo task form instance
	*/
	@Override
	public void setFormValueEntryGroupId(long formValueEntryGroupId) {
		model.setFormValueEntryGroupId(formValueEntryGroupId);
	}

	/**
	* Sets the form value entry ID of this kaleo task form instance.
	*
	* @param formValueEntryId the form value entry ID of this kaleo task form instance
	*/
	@Override
	public void setFormValueEntryId(long formValueEntryId) {
		model.setFormValueEntryId(formValueEntryId);
	}

	/**
	* Sets the form value entry uuid of this kaleo task form instance.
	*
	* @param formValueEntryUuid the form value entry uuid of this kaleo task form instance
	*/
	@Override
	public void setFormValueEntryUuid(String formValueEntryUuid) {
		model.setFormValueEntryUuid(formValueEntryUuid);
	}

	/**
	* Sets the form values of this kaleo task form instance.
	*
	* @param formValues the form values of this kaleo task form instance
	*/
	@Override
	public void setFormValues(String formValues) {
		model.setFormValues(formValues);
	}

	/**
	* Sets the group ID of this kaleo task form instance.
	*
	* @param groupId the group ID of this kaleo task form instance
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo task form instance.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo instance ID of this kaleo task form instance.
	*
	* @param kaleoInstanceId the kaleo instance ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		model.setKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Sets the kaleo task form ID of this kaleo task form instance.
	*
	* @param kaleoTaskFormId the kaleo task form ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoTaskFormId(long kaleoTaskFormId) {
		model.setKaleoTaskFormId(kaleoTaskFormId);
	}

	/**
	* Sets the kaleo task form instance ID of this kaleo task form instance.
	*
	* @param kaleoTaskFormInstanceId the kaleo task form instance ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoTaskFormInstanceId(long kaleoTaskFormInstanceId) {
		model.setKaleoTaskFormInstanceId(kaleoTaskFormInstanceId);
	}

	/**
	* Sets the kaleo task ID of this kaleo task form instance.
	*
	* @param kaleoTaskId the kaleo task ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoTaskId(long kaleoTaskId) {
		model.setKaleoTaskId(kaleoTaskId);
	}

	/**
	* Sets the kaleo task instance token ID of this kaleo task form instance.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID of this kaleo task form instance
	*/
	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		model.setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Sets the metadata of this kaleo task form instance.
	*
	* @param metadata the metadata of this kaleo task form instance
	*/
	@Override
	public void setMetadata(String metadata) {
		model.setMetadata(metadata);
	}

	/**
	* Sets the modified date of this kaleo task form instance.
	*
	* @param modifiedDate the modified date of this kaleo task form instance
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the primary key of this kaleo task form instance.
	*
	* @param primaryKey the primary key of this kaleo task form instance
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this kaleo task form instance.
	*
	* @param userId the user ID of this kaleo task form instance
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo task form instance.
	*
	* @param userName the user name of this kaleo task form instance
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo task form instance.
	*
	* @param userUuid the user uuid of this kaleo task form instance
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected KaleoTaskFormInstanceWrapper wrap(
		KaleoTaskFormInstance kaleoTaskFormInstance) {
		return new KaleoTaskFormInstanceWrapper(kaleoTaskFormInstance);
	}
}