/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link KaleoTaskFormInstance}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormInstance
 * @generated
 */
public class KaleoTaskFormInstanceWrapper
	extends BaseModelWrapper<KaleoTaskFormInstance>
	implements KaleoTaskFormInstance, ModelWrapper<KaleoTaskFormInstance> {

	public KaleoTaskFormInstanceWrapper(
		KaleoTaskFormInstance kaleoTaskFormInstance) {

		super(kaleoTaskFormInstance);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoTaskFormInstanceId", getKaleoTaskFormInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoInstanceId", getKaleoInstanceId());
		attributes.put("kaleoTaskId", getKaleoTaskId());
		attributes.put(
			"kaleoTaskInstanceTokenId", getKaleoTaskInstanceTokenId());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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

		Long kaleoDefinitionVersionId = (Long)attributes.get(
			"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
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

		String formValueEntryUuid = (String)attributes.get(
			"formValueEntryUuid");

		if (formValueEntryUuid != null) {
			setFormValueEntryUuid(formValueEntryUuid);
		}

		String metadata = (String)attributes.get("metadata");

		if (metadata != null) {
			setMetadata(metadata);
		}
	}

	@Override
	public KaleoTaskFormInstance cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the ct collection ID of this kaleo task form instance.
	 *
	 * @return the ct collection ID of this kaleo task form instance
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
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
	 * Returns the kaleo definition ID of this kaleo task form instance.
	 *
	 * @return the kaleo definition ID of this kaleo task form instance
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
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
	 * Returns the mvcc version of this kaleo task form instance.
	 *
	 * @return the mvcc version of this kaleo task form instance
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
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
	 * Sets the ct collection ID of this kaleo task form instance.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo task form instance
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
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
	 * Sets the kaleo definition ID of this kaleo task form instance.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo task form instance
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
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
	 * Sets the mvcc version of this kaleo task form instance.
	 *
	 * @param mvccVersion the mvcc version of this kaleo task form instance
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<KaleoTaskFormInstance, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoTaskFormInstance, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoTaskFormInstanceWrapper wrap(
		KaleoTaskFormInstance kaleoTaskFormInstance) {

		return new KaleoTaskFormInstanceWrapper(kaleoTaskFormInstance);
	}

}