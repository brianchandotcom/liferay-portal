/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.model;

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
 * This class is a wrapper for {@link CommerceDataIntegrationProcessLog}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLog
 * @generated
 */
public class CommerceDataIntegrationProcessLogWrapper
	implements CommerceDataIntegrationProcessLog,
			   ModelWrapper<CommerceDataIntegrationProcessLog> {

	public CommerceDataIntegrationProcessLogWrapper(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		_commerceDataIntegrationProcessLog = commerceDataIntegrationProcessLog;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDataIntegrationProcessLog.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDataIntegrationProcessLog.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceDataIntegrationProcessLogId",
			getCommerceDataIntegrationProcessLogId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"CDataIntegrationProcessId", getCDataIntegrationProcessId());
		attributes.put("error", getError());
		attributes.put("output", getOutput());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDataIntegrationProcessLogId = (Long)attributes.get(
			"commerceDataIntegrationProcessLogId");

		if (commerceDataIntegrationProcessLogId != null) {
			setCommerceDataIntegrationProcessLogId(
				commerceDataIntegrationProcessLogId);
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

		Long CDataIntegrationProcessId = (Long)attributes.get(
			"CDataIntegrationProcessId");

		if (CDataIntegrationProcessId != null) {
			setCDataIntegrationProcessId(CDataIntegrationProcessId);
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

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public Object clone() {
		return new CommerceDataIntegrationProcessLogWrapper(
			(CommerceDataIntegrationProcessLog)
				_commerceDataIntegrationProcessLog.clone());
	}

	@Override
	public int compareTo(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		return _commerceDataIntegrationProcessLog.compareTo(
			commerceDataIntegrationProcessLog);
	}

	/**
	 * Returns the c data integration process ID of this commerce data integration process log.
	 *
	 * @return the c data integration process ID of this commerce data integration process log
	 */
	@Override
	public long getCDataIntegrationProcessId() {
		return _commerceDataIntegrationProcessLog.
			getCDataIntegrationProcessId();
	}

	/**
	 * Returns the commerce data integration process log ID of this commerce data integration process log.
	 *
	 * @return the commerce data integration process log ID of this commerce data integration process log
	 */
	@Override
	public long getCommerceDataIntegrationProcessLogId() {
		return _commerceDataIntegrationProcessLog.
			getCommerceDataIntegrationProcessLogId();
	}

	/**
	 * Returns the company ID of this commerce data integration process log.
	 *
	 * @return the company ID of this commerce data integration process log
	 */
	@Override
	public long getCompanyId() {
		return _commerceDataIntegrationProcessLog.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce data integration process log.
	 *
	 * @return the create date of this commerce data integration process log
	 */
	@Override
	public Date getCreateDate() {
		return _commerceDataIntegrationProcessLog.getCreateDate();
	}

	/**
	 * Returns the end date of this commerce data integration process log.
	 *
	 * @return the end date of this commerce data integration process log
	 */
	@Override
	public Date getEndDate() {
		return _commerceDataIntegrationProcessLog.getEndDate();
	}

	/**
	 * Returns the error of this commerce data integration process log.
	 *
	 * @return the error of this commerce data integration process log
	 */
	@Override
	public String getError() {
		return _commerceDataIntegrationProcessLog.getError();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDataIntegrationProcessLog.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce data integration process log.
	 *
	 * @return the modified date of this commerce data integration process log
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceDataIntegrationProcessLog.getModifiedDate();
	}

	/**
	 * Returns the output of this commerce data integration process log.
	 *
	 * @return the output of this commerce data integration process log
	 */
	@Override
	public String getOutput() {
		return _commerceDataIntegrationProcessLog.getOutput();
	}

	/**
	 * Returns the primary key of this commerce data integration process log.
	 *
	 * @return the primary key of this commerce data integration process log
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceDataIntegrationProcessLog.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDataIntegrationProcessLog.getPrimaryKeyObj();
	}

	/**
	 * Returns the start date of this commerce data integration process log.
	 *
	 * @return the start date of this commerce data integration process log
	 */
	@Override
	public Date getStartDate() {
		return _commerceDataIntegrationProcessLog.getStartDate();
	}

	/**
	 * Returns the status of this commerce data integration process log.
	 *
	 * @return the status of this commerce data integration process log
	 */
	@Override
	public int getStatus() {
		return _commerceDataIntegrationProcessLog.getStatus();
	}

	/**
	 * Returns the user ID of this commerce data integration process log.
	 *
	 * @return the user ID of this commerce data integration process log
	 */
	@Override
	public long getUserId() {
		return _commerceDataIntegrationProcessLog.getUserId();
	}

	/**
	 * Returns the user name of this commerce data integration process log.
	 *
	 * @return the user name of this commerce data integration process log
	 */
	@Override
	public String getUserName() {
		return _commerceDataIntegrationProcessLog.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce data integration process log.
	 *
	 * @return the user uuid of this commerce data integration process log
	 */
	@Override
	public String getUserUuid() {
		return _commerceDataIntegrationProcessLog.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDataIntegrationProcessLog.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDataIntegrationProcessLog.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDataIntegrationProcessLog.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDataIntegrationProcessLog.isNew();
	}

	@Override
	public void persist() {
		_commerceDataIntegrationProcessLog.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDataIntegrationProcessLog.setCachedModel(cachedModel);
	}

	/**
	 * Sets the c data integration process ID of this commerce data integration process log.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID of this commerce data integration process log
	 */
	@Override
	public void setCDataIntegrationProcessId(long CDataIntegrationProcessId) {
		_commerceDataIntegrationProcessLog.setCDataIntegrationProcessId(
			CDataIntegrationProcessId);
	}

	/**
	 * Sets the commerce data integration process log ID of this commerce data integration process log.
	 *
	 * @param commerceDataIntegrationProcessLogId the commerce data integration process log ID of this commerce data integration process log
	 */
	@Override
	public void setCommerceDataIntegrationProcessLogId(
		long commerceDataIntegrationProcessLogId) {

		_commerceDataIntegrationProcessLog.
			setCommerceDataIntegrationProcessLogId(
				commerceDataIntegrationProcessLogId);
	}

	/**
	 * Sets the company ID of this commerce data integration process log.
	 *
	 * @param companyId the company ID of this commerce data integration process log
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceDataIntegrationProcessLog.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce data integration process log.
	 *
	 * @param createDate the create date of this commerce data integration process log
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDataIntegrationProcessLog.setCreateDate(createDate);
	}

	/**
	 * Sets the end date of this commerce data integration process log.
	 *
	 * @param endDate the end date of this commerce data integration process log
	 */
	@Override
	public void setEndDate(Date endDate) {
		_commerceDataIntegrationProcessLog.setEndDate(endDate);
	}

	/**
	 * Sets the error of this commerce data integration process log.
	 *
	 * @param error the error of this commerce data integration process log
	 */
	@Override
	public void setError(String error) {
		_commerceDataIntegrationProcessLog.setError(error);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceDataIntegrationProcessLog.setExpandoBridgeAttributes(
			baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDataIntegrationProcessLog.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDataIntegrationProcessLog.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the modified date of this commerce data integration process log.
	 *
	 * @param modifiedDate the modified date of this commerce data integration process log
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDataIntegrationProcessLog.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDataIntegrationProcessLog.setNew(n);
	}

	/**
	 * Sets the output of this commerce data integration process log.
	 *
	 * @param output the output of this commerce data integration process log
	 */
	@Override
	public void setOutput(String output) {
		_commerceDataIntegrationProcessLog.setOutput(output);
	}

	/**
	 * Sets the primary key of this commerce data integration process log.
	 *
	 * @param primaryKey the primary key of this commerce data integration process log
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDataIntegrationProcessLog.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDataIntegrationProcessLog.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the start date of this commerce data integration process log.
	 *
	 * @param startDate the start date of this commerce data integration process log
	 */
	@Override
	public void setStartDate(Date startDate) {
		_commerceDataIntegrationProcessLog.setStartDate(startDate);
	}

	/**
	 * Sets the status of this commerce data integration process log.
	 *
	 * @param status the status of this commerce data integration process log
	 */
	@Override
	public void setStatus(int status) {
		_commerceDataIntegrationProcessLog.setStatus(status);
	}

	/**
	 * Sets the user ID of this commerce data integration process log.
	 *
	 * @param userId the user ID of this commerce data integration process log
	 */
	@Override
	public void setUserId(long userId) {
		_commerceDataIntegrationProcessLog.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce data integration process log.
	 *
	 * @param userName the user name of this commerce data integration process log
	 */
	@Override
	public void setUserName(String userName) {
		_commerceDataIntegrationProcessLog.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce data integration process log.
	 *
	 * @param userUuid the user uuid of this commerce data integration process log
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceDataIntegrationProcessLog.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceDataIntegrationProcessLog> toCacheModel() {

		return _commerceDataIntegrationProcessLog.toCacheModel();
	}

	@Override
	public CommerceDataIntegrationProcessLog toEscapedModel() {
		return new CommerceDataIntegrationProcessLogWrapper(
			_commerceDataIntegrationProcessLog.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceDataIntegrationProcessLog.toString();
	}

	@Override
	public CommerceDataIntegrationProcessLog toUnescapedModel() {
		return new CommerceDataIntegrationProcessLogWrapper(
			_commerceDataIntegrationProcessLog.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceDataIntegrationProcessLog.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDataIntegrationProcessLogWrapper)) {
			return false;
		}

		CommerceDataIntegrationProcessLogWrapper
			commerceDataIntegrationProcessLogWrapper =
				(CommerceDataIntegrationProcessLogWrapper)object;

		if (Objects.equals(
				_commerceDataIntegrationProcessLog,
				commerceDataIntegrationProcessLogWrapper.
					_commerceDataIntegrationProcessLog)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceDataIntegrationProcessLog getWrappedModel() {
		return _commerceDataIntegrationProcessLog;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDataIntegrationProcessLog.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDataIntegrationProcessLog.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDataIntegrationProcessLog.resetOriginalValues();
	}

	private final CommerceDataIntegrationProcessLog
		_commerceDataIntegrationProcessLog;

}