/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DispatchLog}.
 * </p>
 *
 * @author Matija Petanjek
 * @see DispatchLog
 * @generated
 */
public class DispatchLogWrapper
	extends BaseModelWrapper<DispatchLog>
	implements DispatchLog, ModelWrapper<DispatchLog> {

	public DispatchLogWrapper(DispatchLog dispatchLog) {
		super(dispatchLog);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("dispatchLogId", getDispatchLogId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("dispatchTriggerId", getDispatchTriggerId());
		attributes.put("endDate", getEndDate());
		attributes.put("error", getError());
		attributes.put("output", getOutput());
		attributes.put("startDate", getStartDate());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long dispatchLogId = (Long)attributes.get("dispatchLogId");

		if (dispatchLogId != null) {
			setDispatchLogId(dispatchLogId);
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

		Long dispatchTriggerId = (Long)attributes.get("dispatchTriggerId");

		if (dispatchTriggerId != null) {
			setDispatchTriggerId(dispatchTriggerId);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		String error = (String)attributes.get("error");

		if (error != null) {
			setError(error);
		}

		String output = (String)attributes.get("output");

		if (output != null) {
			setOutput(output);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public DispatchLog cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this dispatch log.
	 *
	 * @return the company ID of this dispatch log
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this dispatch log.
	 *
	 * @return the create date of this dispatch log
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the dispatch log ID of this dispatch log.
	 *
	 * @return the dispatch log ID of this dispatch log
	 */
	@Override
	public long getDispatchLogId() {
		return model.getDispatchLogId();
	}

	/**
	 * Returns the dispatch trigger ID of this dispatch log.
	 *
	 * @return the dispatch trigger ID of this dispatch log
	 */
	@Override
	public long getDispatchTriggerId() {
		return model.getDispatchTriggerId();
	}

	/**
	 * Returns the end date of this dispatch log.
	 *
	 * @return the end date of this dispatch log
	 */
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	/**
	 * Returns the error of this dispatch log.
	 *
	 * @return the error of this dispatch log
	 */
	@Override
	public String getError() {
		return model.getError();
	}

	/**
	 * Returns the modified date of this dispatch log.
	 *
	 * @return the modified date of this dispatch log
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this dispatch log.
	 *
	 * @return the mvcc version of this dispatch log
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the output of this dispatch log.
	 *
	 * @return the output of this dispatch log
	 */
	@Override
	public String getOutput() {
		return model.getOutput();
	}

	/**
	 * Returns the primary key of this dispatch log.
	 *
	 * @return the primary key of this dispatch log
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start date of this dispatch log.
	 *
	 * @return the start date of this dispatch log
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the status of this dispatch log.
	 *
	 * @return the status of this dispatch log
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this dispatch log.
	 *
	 * @return the user ID of this dispatch log
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this dispatch log.
	 *
	 * @return the user name of this dispatch log
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this dispatch log.
	 *
	 * @return the user uuid of this dispatch log
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
	 * Sets the company ID of this dispatch log.
	 *
	 * @param companyId the company ID of this dispatch log
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this dispatch log.
	 *
	 * @param createDate the create date of this dispatch log
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the dispatch log ID of this dispatch log.
	 *
	 * @param dispatchLogId the dispatch log ID of this dispatch log
	 */
	@Override
	public void setDispatchLogId(long dispatchLogId) {
		model.setDispatchLogId(dispatchLogId);
	}

	/**
	 * Sets the dispatch trigger ID of this dispatch log.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID of this dispatch log
	 */
	@Override
	public void setDispatchTriggerId(long dispatchTriggerId) {
		model.setDispatchTriggerId(dispatchTriggerId);
	}

	/**
	 * Sets the end date of this dispatch log.
	 *
	 * @param endDate the end date of this dispatch log
	 */
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	 * Sets the error of this dispatch log.
	 *
	 * @param error the error of this dispatch log
	 */
	@Override
	public void setError(String error) {
		model.setError(error);
	}

	/**
	 * Sets the modified date of this dispatch log.
	 *
	 * @param modifiedDate the modified date of this dispatch log
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this dispatch log.
	 *
	 * @param mvccVersion the mvcc version of this dispatch log
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the output of this dispatch log.
	 *
	 * @param output the output of this dispatch log
	 */
	@Override
	public void setOutput(String output) {
		model.setOutput(output);
	}

	/**
	 * Sets the primary key of this dispatch log.
	 *
	 * @param primaryKey the primary key of this dispatch log
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start date of this dispatch log.
	 *
	 * @param startDate the start date of this dispatch log
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets the status of this dispatch log.
	 *
	 * @param status the status of this dispatch log
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this dispatch log.
	 *
	 * @param userId the user ID of this dispatch log
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this dispatch log.
	 *
	 * @param userName the user name of this dispatch log
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this dispatch log.
	 *
	 * @param userUuid the user uuid of this dispatch log
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
	protected DispatchLogWrapper wrap(DispatchLog dispatchLog) {
		return new DispatchLogWrapper(dispatchLog);
	}

}