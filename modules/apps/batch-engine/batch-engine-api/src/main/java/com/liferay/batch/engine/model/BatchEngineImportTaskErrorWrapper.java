/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BatchEngineImportTaskError}.
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskError
 * @generated
 */
public class BatchEngineImportTaskErrorWrapper
	extends BaseModelWrapper<BatchEngineImportTaskError>
	implements BatchEngineImportTaskError,
			   ModelWrapper<BatchEngineImportTaskError> {

	public BatchEngineImportTaskErrorWrapper(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		super(batchEngineImportTaskError);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"batchEngineImportTaskErrorId", getBatchEngineImportTaskErrorId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("batchEngineImportTaskId", getBatchEngineImportTaskId());
		attributes.put("item", getItem());
		attributes.put("itemIndex", getItemIndex());
		attributes.put("message", getMessage());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long batchEngineImportTaskErrorId = (Long)attributes.get(
			"batchEngineImportTaskErrorId");

		if (batchEngineImportTaskErrorId != null) {
			setBatchEngineImportTaskErrorId(batchEngineImportTaskErrorId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long batchEngineImportTaskId = (Long)attributes.get(
			"batchEngineImportTaskId");

		if (batchEngineImportTaskId != null) {
			setBatchEngineImportTaskId(batchEngineImportTaskId);
		}

		String item = (String)attributes.get("item");

		if (item != null) {
			setItem(item);
		}

		Integer itemIndex = (Integer)attributes.get("itemIndex");

		if (itemIndex != null) {
			setItemIndex(itemIndex);
		}

		String message = (String)attributes.get("message");

		if (message != null) {
			setMessage(message);
		}
	}

	@Override
	public BatchEngineImportTaskError cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the batch engine import task error ID of this batch engine import task error.
	 *
	 * @return the batch engine import task error ID of this batch engine import task error
	 */
	@Override
	public long getBatchEngineImportTaskErrorId() {
		return model.getBatchEngineImportTaskErrorId();
	}

	/**
	 * Returns the batch engine import task ID of this batch engine import task error.
	 *
	 * @return the batch engine import task ID of this batch engine import task error
	 */
	@Override
	public long getBatchEngineImportTaskId() {
		return model.getBatchEngineImportTaskId();
	}

	/**
	 * Returns the company ID of this batch engine import task error.
	 *
	 * @return the company ID of this batch engine import task error
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this batch engine import task error.
	 *
	 * @return the create date of this batch engine import task error
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the item of this batch engine import task error.
	 *
	 * @return the item of this batch engine import task error
	 */
	@Override
	public String getItem() {
		return model.getItem();
	}

	/**
	 * Returns the item index of this batch engine import task error.
	 *
	 * @return the item index of this batch engine import task error
	 */
	@Override
	public int getItemIndex() {
		return model.getItemIndex();
	}

	/**
	 * Returns the message of this batch engine import task error.
	 *
	 * @return the message of this batch engine import task error
	 */
	@Override
	public String getMessage() {
		return model.getMessage();
	}

	/**
	 * Returns the modified date of this batch engine import task error.
	 *
	 * @return the modified date of this batch engine import task error
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch engine import task error.
	 *
	 * @return the mvcc version of this batch engine import task error
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this batch engine import task error.
	 *
	 * @return the primary key of this batch engine import task error
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this batch engine import task error.
	 *
	 * @return the user ID of this batch engine import task error
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this batch engine import task error.
	 *
	 * @return the user uuid of this batch engine import task error
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
	 * Sets the batch engine import task error ID of this batch engine import task error.
	 *
	 * @param batchEngineImportTaskErrorId the batch engine import task error ID of this batch engine import task error
	 */
	@Override
	public void setBatchEngineImportTaskErrorId(
		long batchEngineImportTaskErrorId) {

		model.setBatchEngineImportTaskErrorId(batchEngineImportTaskErrorId);
	}

	/**
	 * Sets the batch engine import task ID of this batch engine import task error.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID of this batch engine import task error
	 */
	@Override
	public void setBatchEngineImportTaskId(long batchEngineImportTaskId) {
		model.setBatchEngineImportTaskId(batchEngineImportTaskId);
	}

	/**
	 * Sets the company ID of this batch engine import task error.
	 *
	 * @param companyId the company ID of this batch engine import task error
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this batch engine import task error.
	 *
	 * @param createDate the create date of this batch engine import task error
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the item of this batch engine import task error.
	 *
	 * @param item the item of this batch engine import task error
	 */
	@Override
	public void setItem(String item) {
		model.setItem(item);
	}

	/**
	 * Sets the item index of this batch engine import task error.
	 *
	 * @param itemIndex the item index of this batch engine import task error
	 */
	@Override
	public void setItemIndex(int itemIndex) {
		model.setItemIndex(itemIndex);
	}

	/**
	 * Sets the message of this batch engine import task error.
	 *
	 * @param message the message of this batch engine import task error
	 */
	@Override
	public void setMessage(String message) {
		model.setMessage(message);
	}

	/**
	 * Sets the modified date of this batch engine import task error.
	 *
	 * @param modifiedDate the modified date of this batch engine import task error
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch engine import task error.
	 *
	 * @param mvccVersion the mvcc version of this batch engine import task error
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this batch engine import task error.
	 *
	 * @param primaryKey the primary key of this batch engine import task error
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this batch engine import task error.
	 *
	 * @param userId the user ID of this batch engine import task error
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this batch engine import task error.
	 *
	 * @param userUuid the user uuid of this batch engine import task error
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
	protected BatchEngineImportTaskErrorWrapper wrap(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		return new BatchEngineImportTaskErrorWrapper(
			batchEngineImportTaskError);
	}

}