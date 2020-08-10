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
 * This class is a wrapper for {@link CommerceDataIntegrationProcess}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcess
 * @generated
 */
public class CommerceDataIntegrationProcessWrapper
	implements CommerceDataIntegrationProcess,
			   ModelWrapper<CommerceDataIntegrationProcess> {

	public CommerceDataIntegrationProcessWrapper(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		_commerceDataIntegrationProcess = commerceDataIntegrationProcess;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDataIntegrationProcess.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDataIntegrationProcess.class.getName();
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

	@Override
	public Object clone() {
		return new CommerceDataIntegrationProcessWrapper(
			(CommerceDataIntegrationProcess)
				_commerceDataIntegrationProcess.clone());
	}

	@Override
	public int compareTo(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		return _commerceDataIntegrationProcess.compareTo(
			commerceDataIntegrationProcess);
	}

	/**
	 * Returns the active of this commerce data integration process.
	 *
	 * @return the active of this commerce data integration process
	 */
	@Override
	public boolean getActive() {
		return _commerceDataIntegrationProcess.getActive();
	}

	/**
	 * Returns the commerce data integration process ID of this commerce data integration process.
	 *
	 * @return the commerce data integration process ID of this commerce data integration process
	 */
	@Override
	public long getCommerceDataIntegrationProcessId() {
		return _commerceDataIntegrationProcess.
			getCommerceDataIntegrationProcessId();
	}

	/**
	 * Returns the company ID of this commerce data integration process.
	 *
	 * @return the company ID of this commerce data integration process
	 */
	@Override
	public long getCompanyId() {
		return _commerceDataIntegrationProcess.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce data integration process.
	 *
	 * @return the create date of this commerce data integration process
	 */
	@Override
	public Date getCreateDate() {
		return _commerceDataIntegrationProcess.getCreateDate();
	}

	/**
	 * Returns the cron expression of this commerce data integration process.
	 *
	 * @return the cron expression of this commerce data integration process
	 */
	@Override
	public String getCronExpression() {
		return _commerceDataIntegrationProcess.getCronExpression();
	}

	/**
	 * Returns the end date of this commerce data integration process.
	 *
	 * @return the end date of this commerce data integration process
	 */
	@Override
	public Date getEndDate() {
		return _commerceDataIntegrationProcess.getEndDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDataIntegrationProcess.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce data integration process.
	 *
	 * @return the modified date of this commerce data integration process
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceDataIntegrationProcess.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce data integration process.
	 *
	 * @return the name of this commerce data integration process
	 */
	@Override
	public String getName() {
		return _commerceDataIntegrationProcess.getName();
	}

	/**
	 * Returns the primary key of this commerce data integration process.
	 *
	 * @return the primary key of this commerce data integration process
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceDataIntegrationProcess.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDataIntegrationProcess.getPrimaryKeyObj();
	}

	/**
	 * Returns the start date of this commerce data integration process.
	 *
	 * @return the start date of this commerce data integration process
	 */
	@Override
	public Date getStartDate() {
		return _commerceDataIntegrationProcess.getStartDate();
	}

	/**
	 * Returns the system of this commerce data integration process.
	 *
	 * @return the system of this commerce data integration process
	 */
	@Override
	public boolean getSystem() {
		return _commerceDataIntegrationProcess.getSystem();
	}

	/**
	 * Returns the type of this commerce data integration process.
	 *
	 * @return the type of this commerce data integration process
	 */
	@Override
	public String getType() {
		return _commerceDataIntegrationProcess.getType();
	}

	/**
	 * Returns the type settings of this commerce data integration process.
	 *
	 * @return the type settings of this commerce data integration process
	 */
	@Override
	public String getTypeSettings() {
		return _commerceDataIntegrationProcess.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties() {

		return _commerceDataIntegrationProcess.getTypeSettingsProperties();
	}

	/**
	 * Returns the user ID of this commerce data integration process.
	 *
	 * @return the user ID of this commerce data integration process
	 */
	@Override
	public long getUserId() {
		return _commerceDataIntegrationProcess.getUserId();
	}

	/**
	 * Returns the user name of this commerce data integration process.
	 *
	 * @return the user name of this commerce data integration process
	 */
	@Override
	public String getUserName() {
		return _commerceDataIntegrationProcess.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce data integration process.
	 *
	 * @return the user uuid of this commerce data integration process
	 */
	@Override
	public String getUserUuid() {
		return _commerceDataIntegrationProcess.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDataIntegrationProcess.hashCode();
	}

	/**
	 * Returns <code>true</code> if this commerce data integration process is active.
	 *
	 * @return <code>true</code> if this commerce data integration process is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return _commerceDataIntegrationProcess.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDataIntegrationProcess.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDataIntegrationProcess.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDataIntegrationProcess.isNew();
	}

	/**
	 * Returns <code>true</code> if this commerce data integration process is system.
	 *
	 * @return <code>true</code> if this commerce data integration process is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return _commerceDataIntegrationProcess.isSystem();
	}

	@Override
	public void persist() {
		_commerceDataIntegrationProcess.persist();
	}

	/**
	 * Sets whether this commerce data integration process is active.
	 *
	 * @param active the active of this commerce data integration process
	 */
	@Override
	public void setActive(boolean active) {
		_commerceDataIntegrationProcess.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDataIntegrationProcess.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce data integration process ID of this commerce data integration process.
	 *
	 * @param commerceDataIntegrationProcessId the commerce data integration process ID of this commerce data integration process
	 */
	@Override
	public void setCommerceDataIntegrationProcessId(
		long commerceDataIntegrationProcessId) {

		_commerceDataIntegrationProcess.setCommerceDataIntegrationProcessId(
			commerceDataIntegrationProcessId);
	}

	/**
	 * Sets the company ID of this commerce data integration process.
	 *
	 * @param companyId the company ID of this commerce data integration process
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceDataIntegrationProcess.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce data integration process.
	 *
	 * @param createDate the create date of this commerce data integration process
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDataIntegrationProcess.setCreateDate(createDate);
	}

	/**
	 * Sets the cron expression of this commerce data integration process.
	 *
	 * @param cronExpression the cron expression of this commerce data integration process
	 */
	@Override
	public void setCronExpression(String cronExpression) {
		_commerceDataIntegrationProcess.setCronExpression(cronExpression);
	}

	/**
	 * Sets the end date of this commerce data integration process.
	 *
	 * @param endDate the end date of this commerce data integration process
	 */
	@Override
	public void setEndDate(Date endDate) {
		_commerceDataIntegrationProcess.setEndDate(endDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceDataIntegrationProcess.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDataIntegrationProcess.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDataIntegrationProcess.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the modified date of this commerce data integration process.
	 *
	 * @param modifiedDate the modified date of this commerce data integration process
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDataIntegrationProcess.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce data integration process.
	 *
	 * @param name the name of this commerce data integration process
	 */
	@Override
	public void setName(String name) {
		_commerceDataIntegrationProcess.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDataIntegrationProcess.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce data integration process.
	 *
	 * @param primaryKey the primary key of this commerce data integration process
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDataIntegrationProcess.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDataIntegrationProcess.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the start date of this commerce data integration process.
	 *
	 * @param startDate the start date of this commerce data integration process
	 */
	@Override
	public void setStartDate(Date startDate) {
		_commerceDataIntegrationProcess.setStartDate(startDate);
	}

	/**
	 * Sets whether this commerce data integration process is system.
	 *
	 * @param system the system of this commerce data integration process
	 */
	@Override
	public void setSystem(boolean system) {
		_commerceDataIntegrationProcess.setSystem(system);
	}

	/**
	 * Sets the type of this commerce data integration process.
	 *
	 * @param type the type of this commerce data integration process
	 */
	@Override
	public void setType(String type) {
		_commerceDataIntegrationProcess.setType(type);
	}

	/**
	 * Sets the type settings of this commerce data integration process.
	 *
	 * @param typeSettings the type settings of this commerce data integration process
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		_commerceDataIntegrationProcess.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsProperties) {

		_commerceDataIntegrationProcess.setTypeSettingsProperties(
			typeSettingsProperties);
	}

	/**
	 * Sets the user ID of this commerce data integration process.
	 *
	 * @param userId the user ID of this commerce data integration process
	 */
	@Override
	public void setUserId(long userId) {
		_commerceDataIntegrationProcess.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce data integration process.
	 *
	 * @param userName the user name of this commerce data integration process
	 */
	@Override
	public void setUserName(String userName) {
		_commerceDataIntegrationProcess.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce data integration process.
	 *
	 * @param userUuid the user uuid of this commerce data integration process
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceDataIntegrationProcess.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceDataIntegrationProcess> toCacheModel() {

		return _commerceDataIntegrationProcess.toCacheModel();
	}

	@Override
	public CommerceDataIntegrationProcess toEscapedModel() {
		return new CommerceDataIntegrationProcessWrapper(
			_commerceDataIntegrationProcess.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceDataIntegrationProcess.toString();
	}

	@Override
	public CommerceDataIntegrationProcess toUnescapedModel() {
		return new CommerceDataIntegrationProcessWrapper(
			_commerceDataIntegrationProcess.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceDataIntegrationProcess.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDataIntegrationProcessWrapper)) {
			return false;
		}

		CommerceDataIntegrationProcessWrapper
			commerceDataIntegrationProcessWrapper =
				(CommerceDataIntegrationProcessWrapper)object;

		if (Objects.equals(
				_commerceDataIntegrationProcess,
				commerceDataIntegrationProcessWrapper.
					_commerceDataIntegrationProcess)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceDataIntegrationProcess getWrappedModel() {
		return _commerceDataIntegrationProcess;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDataIntegrationProcess.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDataIntegrationProcess.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDataIntegrationProcess.resetOriginalValues();
	}

	private final CommerceDataIntegrationProcess
		_commerceDataIntegrationProcess;

}