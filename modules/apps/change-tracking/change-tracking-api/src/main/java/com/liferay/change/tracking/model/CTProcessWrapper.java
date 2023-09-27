/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTProcess}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTProcess
 * @generated
 */
public class CTProcessWrapper
	extends BaseModelWrapper<CTProcess>
	implements CTProcess, ModelWrapper<CTProcess> {

	public CTProcessWrapper(CTProcess ctProcess) {
		super(ctProcess);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctProcessId", getCtProcessId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("backgroundTaskId", getBackgroundTaskId());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctProcessId = (Long)attributes.get("ctProcessId");

		if (ctProcessId != null) {
			setCtProcessId(ctProcessId);
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

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long backgroundTaskId = (Long)attributes.get("backgroundTaskId");

		if (backgroundTaskId != null) {
			setBackgroundTaskId(backgroundTaskId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public CTProcess cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the background task ID of this ct process.
	 *
	 * @return the background task ID of this ct process
	 */
	@Override
	public long getBackgroundTaskId() {
		return model.getBackgroundTaskId();
	}

	/**
	 * Returns the company ID of this ct process.
	 *
	 * @return the company ID of this ct process
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ct process.
	 *
	 * @return the create date of this ct process
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ct process.
	 *
	 * @return the ct collection ID of this ct process
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ct process ID of this ct process.
	 *
	 * @return the ct process ID of this ct process
	 */
	@Override
	public long getCtProcessId() {
		return model.getCtProcessId();
	}

	/**
	 * Returns the mvcc version of this ct process.
	 *
	 * @return the mvcc version of this ct process
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ct process.
	 *
	 * @return the primary key of this ct process
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this ct process.
	 *
	 * @return the type of this ct process
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this ct process.
	 *
	 * @return the user ID of this ct process
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this ct process.
	 *
	 * @return the user uuid of this ct process
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
	 * Sets the background task ID of this ct process.
	 *
	 * @param backgroundTaskId the background task ID of this ct process
	 */
	@Override
	public void setBackgroundTaskId(long backgroundTaskId) {
		model.setBackgroundTaskId(backgroundTaskId);
	}

	/**
	 * Sets the company ID of this ct process.
	 *
	 * @param companyId the company ID of this ct process
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ct process.
	 *
	 * @param createDate the create date of this ct process
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ct process.
	 *
	 * @param ctCollectionId the ct collection ID of this ct process
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ct process ID of this ct process.
	 *
	 * @param ctProcessId the ct process ID of this ct process
	 */
	@Override
	public void setCtProcessId(long ctProcessId) {
		model.setCtProcessId(ctProcessId);
	}

	/**
	 * Sets the mvcc version of this ct process.
	 *
	 * @param mvccVersion the mvcc version of this ct process
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ct process.
	 *
	 * @param primaryKey the primary key of this ct process
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this ct process.
	 *
	 * @param type the type of this ct process
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this ct process.
	 *
	 * @param userId the user ID of this ct process
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this ct process.
	 *
	 * @param userUuid the user uuid of this ct process
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
	protected CTProcessWrapper wrap(CTProcess ctProcess) {
		return new CTProcessWrapper(ctProcess);
	}

}