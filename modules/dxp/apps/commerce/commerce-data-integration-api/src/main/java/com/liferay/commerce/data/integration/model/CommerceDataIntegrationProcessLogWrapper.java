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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<CommerceDataIntegrationProcessLog>
	implements CommerceDataIntegrationProcessLog,
			   ModelWrapper<CommerceDataIntegrationProcessLog> {

	public CommerceDataIntegrationProcessLogWrapper(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		super(commerceDataIntegrationProcessLog);
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

	/**
	 * Returns the c data integration process ID of this commerce data integration process log.
	 *
	 * @return the c data integration process ID of this commerce data integration process log
	 */
	@Override
	public long getCDataIntegrationProcessId() {
		return model.getCDataIntegrationProcessId();
	}

	/**
	 * Returns the commerce data integration process log ID of this commerce data integration process log.
	 *
	 * @return the commerce data integration process log ID of this commerce data integration process log
	 */
	@Override
	public long getCommerceDataIntegrationProcessLogId() {
		return model.getCommerceDataIntegrationProcessLogId();
	}

	/**
	 * Returns the company ID of this commerce data integration process log.
	 *
	 * @return the company ID of this commerce data integration process log
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce data integration process log.
	 *
	 * @return the create date of this commerce data integration process log
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the end date of this commerce data integration process log.
	 *
	 * @return the end date of this commerce data integration process log
	 */
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	/**
	 * Returns the error of this commerce data integration process log.
	 *
	 * @return the error of this commerce data integration process log
	 */
	@Override
	public String getError() {
		return model.getError();
	}

	/**
	 * Returns the modified date of this commerce data integration process log.
	 *
	 * @return the modified date of this commerce data integration process log
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the output of this commerce data integration process log.
	 *
	 * @return the output of this commerce data integration process log
	 */
	@Override
	public String getOutput() {
		return model.getOutput();
	}

	/**
	 * Returns the primary key of this commerce data integration process log.
	 *
	 * @return the primary key of this commerce data integration process log
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start date of this commerce data integration process log.
	 *
	 * @return the start date of this commerce data integration process log
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the status of this commerce data integration process log.
	 *
	 * @return the status of this commerce data integration process log
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this commerce data integration process log.
	 *
	 * @return the user ID of this commerce data integration process log
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce data integration process log.
	 *
	 * @return the user name of this commerce data integration process log
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce data integration process log.
	 *
	 * @return the user uuid of this commerce data integration process log
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
	 * Sets the c data integration process ID of this commerce data integration process log.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID of this commerce data integration process log
	 */
	@Override
	public void setCDataIntegrationProcessId(long CDataIntegrationProcessId) {
		model.setCDataIntegrationProcessId(CDataIntegrationProcessId);
	}

	/**
	 * Sets the commerce data integration process log ID of this commerce data integration process log.
	 *
	 * @param commerceDataIntegrationProcessLogId the commerce data integration process log ID of this commerce data integration process log
	 */
	@Override
	public void setCommerceDataIntegrationProcessLogId(
		long commerceDataIntegrationProcessLogId) {

		model.setCommerceDataIntegrationProcessLogId(
			commerceDataIntegrationProcessLogId);
	}

	/**
	 * Sets the company ID of this commerce data integration process log.
	 *
	 * @param companyId the company ID of this commerce data integration process log
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce data integration process log.
	 *
	 * @param createDate the create date of this commerce data integration process log
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the end date of this commerce data integration process log.
	 *
	 * @param endDate the end date of this commerce data integration process log
	 */
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	 * Sets the error of this commerce data integration process log.
	 *
	 * @param error the error of this commerce data integration process log
	 */
	@Override
	public void setError(String error) {
		model.setError(error);
	}

	/**
	 * Sets the modified date of this commerce data integration process log.
	 *
	 * @param modifiedDate the modified date of this commerce data integration process log
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the output of this commerce data integration process log.
	 *
	 * @param output the output of this commerce data integration process log
	 */
	@Override
	public void setOutput(String output) {
		model.setOutput(output);
	}

	/**
	 * Sets the primary key of this commerce data integration process log.
	 *
	 * @param primaryKey the primary key of this commerce data integration process log
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start date of this commerce data integration process log.
	 *
	 * @param startDate the start date of this commerce data integration process log
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets the status of this commerce data integration process log.
	 *
	 * @param status the status of this commerce data integration process log
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this commerce data integration process log.
	 *
	 * @param userId the user ID of this commerce data integration process log
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce data integration process log.
	 *
	 * @param userName the user name of this commerce data integration process log
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce data integration process log.
	 *
	 * @param userUuid the user uuid of this commerce data integration process log
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceDataIntegrationProcessLogWrapper wrap(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		return new CommerceDataIntegrationProcessLogWrapper(
			commerceDataIntegrationProcessLog);
	}

}