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
 * This class is a wrapper for {@link CommerceDataIntegrationProcess}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcess
 * @generated
 */
public class CommerceDataIntegrationProcessWrapper
	extends BaseModelWrapper<CommerceDataIntegrationProcess>
	implements CommerceDataIntegrationProcess,
			   ModelWrapper<CommerceDataIntegrationProcess> {

	public CommerceDataIntegrationProcessWrapper(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		super(commerceDataIntegrationProcess);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceDataIntegrationProcessId",
			getCommerceDataIntegrationProcessId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("system", isSystem());
		attributes.put("active", isActive());
		attributes.put("cronExpression", getCronExpression());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDataIntegrationProcessId = (Long)attributes.get(
			"commerceDataIntegrationProcessId");

		if (commerceDataIntegrationProcessId != null) {
			setCommerceDataIntegrationProcessId(
				commerceDataIntegrationProcessId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String cronExpression = (String)attributes.get("cronExpression");

		if (cronExpression != null) {
			setCronExpression(cronExpression);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}
	}

	/**
	 * Returns the active of this commerce data integration process.
	 *
	 * @return the active of this commerce data integration process
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the commerce data integration process ID of this commerce data integration process.
	 *
	 * @return the commerce data integration process ID of this commerce data integration process
	 */
	@Override
	public long getCommerceDataIntegrationProcessId() {
		return model.getCommerceDataIntegrationProcessId();
	}

	/**
	 * Returns the company ID of this commerce data integration process.
	 *
	 * @return the company ID of this commerce data integration process
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce data integration process.
	 *
	 * @return the create date of this commerce data integration process
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the cron expression of this commerce data integration process.
	 *
	 * @return the cron expression of this commerce data integration process
	 */
	@Override
	public String getCronExpression() {
		return model.getCronExpression();
	}

	/**
	 * Returns the end date of this commerce data integration process.
	 *
	 * @return the end date of this commerce data integration process
	 */
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	/**
	 * Returns the modified date of this commerce data integration process.
	 *
	 * @return the modified date of this commerce data integration process
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce data integration process.
	 *
	 * @return the name of this commerce data integration process
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce data integration process.
	 *
	 * @return the primary key of this commerce data integration process
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start date of this commerce data integration process.
	 *
	 * @return the start date of this commerce data integration process
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the system of this commerce data integration process.
	 *
	 * @return the system of this commerce data integration process
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	/**
	 * Returns the type of this commerce data integration process.
	 *
	 * @return the type of this commerce data integration process
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the type settings of this commerce data integration process.
	 *
	 * @return the type settings of this commerce data integration process
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties() {

		return model.getTypeSettingsProperties();
	}

	/**
	 * Returns the user ID of this commerce data integration process.
	 *
	 * @return the user ID of this commerce data integration process
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce data integration process.
	 *
	 * @return the user name of this commerce data integration process
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce data integration process.
	 *
	 * @return the user uuid of this commerce data integration process
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce data integration process is active.
	 *
	 * @return <code>true</code> if this commerce data integration process is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this commerce data integration process is system.
	 *
	 * @return <code>true</code> if this commerce data integration process is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return model.isSystem();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this commerce data integration process is active.
	 *
	 * @param active the active of this commerce data integration process
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the commerce data integration process ID of this commerce data integration process.
	 *
	 * @param commerceDataIntegrationProcessId the commerce data integration process ID of this commerce data integration process
	 */
	@Override
	public void setCommerceDataIntegrationProcessId(
		long commerceDataIntegrationProcessId) {

		model.setCommerceDataIntegrationProcessId(
			commerceDataIntegrationProcessId);
	}

	/**
	 * Sets the company ID of this commerce data integration process.
	 *
	 * @param companyId the company ID of this commerce data integration process
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce data integration process.
	 *
	 * @param createDate the create date of this commerce data integration process
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the cron expression of this commerce data integration process.
	 *
	 * @param cronExpression the cron expression of this commerce data integration process
	 */
	@Override
	public void setCronExpression(String cronExpression) {
		model.setCronExpression(cronExpression);
	}

	/**
	 * Sets the end date of this commerce data integration process.
	 *
	 * @param endDate the end date of this commerce data integration process
	 */
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	 * Sets the modified date of this commerce data integration process.
	 *
	 * @param modifiedDate the modified date of this commerce data integration process
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce data integration process.
	 *
	 * @param name the name of this commerce data integration process
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce data integration process.
	 *
	 * @param primaryKey the primary key of this commerce data integration process
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start date of this commerce data integration process.
	 *
	 * @param startDate the start date of this commerce data integration process
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets whether this commerce data integration process is system.
	 *
	 * @param system the system of this commerce data integration process
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the type of this commerce data integration process.
	 *
	 * @param type the type of this commerce data integration process
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the type settings of this commerce data integration process.
	 *
	 * @param typeSettings the type settings of this commerce data integration process
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsUnicodeProperties) {

		model.setTypeSettingsProperties(typeSettingsUnicodeProperties);
	}

	/**
	 * Sets the user ID of this commerce data integration process.
	 *
	 * @param userId the user ID of this commerce data integration process
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce data integration process.
	 *
	 * @param userName the user name of this commerce data integration process
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce data integration process.
	 *
	 * @param userUuid the user uuid of this commerce data integration process
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceDataIntegrationProcessWrapper wrap(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		return new CommerceDataIntegrationProcessWrapper(
			commerceDataIntegrationProcess);
	}

}