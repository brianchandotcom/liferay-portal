/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.forecast.alert.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceMLForecastAlertEntry}.
 * </p>
 *
 * @author Riccardo Ferrari
 * @see CommerceMLForecastAlertEntry
 * @generated
 */
public class CommerceMLForecastAlertEntryWrapper
	extends BaseModelWrapper<CommerceMLForecastAlertEntry>
	implements CommerceMLForecastAlertEntry,
			   ModelWrapper<CommerceMLForecastAlertEntry> {

	public CommerceMLForecastAlertEntryWrapper(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		super(commerceMLForecastAlertEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceMLForecastAlertEntryId",
			getCommerceMLForecastAlertEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("actual", getActual());
		attributes.put("forecast", getForecast());
		attributes.put("timestamp", getTimestamp());
		attributes.put("relativeChange", getRelativeChange());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceMLForecastAlertEntryId = (Long)attributes.get(
			"commerceMLForecastAlertEntryId");

		if (commerceMLForecastAlertEntryId != null) {
			setCommerceMLForecastAlertEntryId(commerceMLForecastAlertEntryId);
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

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}

		Double actual = (Double)attributes.get("actual");

		if (actual != null) {
			setActual(actual);
		}

		Double forecast = (Double)attributes.get("forecast");

		if (forecast != null) {
			setForecast(forecast);
		}

		Date timestamp = (Date)attributes.get("timestamp");

		if (timestamp != null) {
			setTimestamp(timestamp);
		}

		Double relativeChange = (Double)attributes.get("relativeChange");

		if (relativeChange != null) {
			setRelativeChange(relativeChange);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public CommerceMLForecastAlertEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the actual of this commerce ml forecast alert entry.
	 *
	 * @return the actual of this commerce ml forecast alert entry
	 */
	@Override
	public double getActual() {
		return model.getActual();
	}

	/**
	 * Returns the commerce account ID of this commerce ml forecast alert entry.
	 *
	 * @return the commerce account ID of this commerce ml forecast alert entry
	 */
	@Override
	public long getCommerceAccountId() {
		return model.getCommerceAccountId();
	}

	/**
	 * Returns the commerce ml forecast alert entry ID of this commerce ml forecast alert entry.
	 *
	 * @return the commerce ml forecast alert entry ID of this commerce ml forecast alert entry
	 */
	@Override
	public long getCommerceMLForecastAlertEntryId() {
		return model.getCommerceMLForecastAlertEntryId();
	}

	/**
	 * Returns the company ID of this commerce ml forecast alert entry.
	 *
	 * @return the company ID of this commerce ml forecast alert entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce ml forecast alert entry.
	 *
	 * @return the create date of this commerce ml forecast alert entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the forecast of this commerce ml forecast alert entry.
	 *
	 * @return the forecast of this commerce ml forecast alert entry
	 */
	@Override
	public double getForecast() {
		return model.getForecast();
	}

	/**
	 * Returns the modified date of this commerce ml forecast alert entry.
	 *
	 * @return the modified date of this commerce ml forecast alert entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce ml forecast alert entry.
	 *
	 * @return the primary key of this commerce ml forecast alert entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the relative change of this commerce ml forecast alert entry.
	 *
	 * @return the relative change of this commerce ml forecast alert entry
	 */
	@Override
	public double getRelativeChange() {
		return model.getRelativeChange();
	}

	/**
	 * Returns the status of this commerce ml forecast alert entry.
	 *
	 * @return the status of this commerce ml forecast alert entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the timestamp of this commerce ml forecast alert entry.
	 *
	 * @return the timestamp of this commerce ml forecast alert entry
	 */
	@Override
	public Date getTimestamp() {
		return model.getTimestamp();
	}

	/**
	 * Returns the user ID of this commerce ml forecast alert entry.
	 *
	 * @return the user ID of this commerce ml forecast alert entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce ml forecast alert entry.
	 *
	 * @return the user name of this commerce ml forecast alert entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce ml forecast alert entry.
	 *
	 * @return the user uuid of this commerce ml forecast alert entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce ml forecast alert entry.
	 *
	 * @return the uuid of this commerce ml forecast alert entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the actual of this commerce ml forecast alert entry.
	 *
	 * @param actual the actual of this commerce ml forecast alert entry
	 */
	@Override
	public void setActual(double actual) {
		model.setActual(actual);
	}

	/**
	 * Sets the commerce account ID of this commerce ml forecast alert entry.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce ml forecast alert entry
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		model.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce ml forecast alert entry ID of this commerce ml forecast alert entry.
	 *
	 * @param commerceMLForecastAlertEntryId the commerce ml forecast alert entry ID of this commerce ml forecast alert entry
	 */
	@Override
	public void setCommerceMLForecastAlertEntryId(
		long commerceMLForecastAlertEntryId) {

		model.setCommerceMLForecastAlertEntryId(commerceMLForecastAlertEntryId);
	}

	/**
	 * Sets the company ID of this commerce ml forecast alert entry.
	 *
	 * @param companyId the company ID of this commerce ml forecast alert entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce ml forecast alert entry.
	 *
	 * @param createDate the create date of this commerce ml forecast alert entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the forecast of this commerce ml forecast alert entry.
	 *
	 * @param forecast the forecast of this commerce ml forecast alert entry
	 */
	@Override
	public void setForecast(double forecast) {
		model.setForecast(forecast);
	}

	/**
	 * Sets the modified date of this commerce ml forecast alert entry.
	 *
	 * @param modifiedDate the modified date of this commerce ml forecast alert entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this commerce ml forecast alert entry.
	 *
	 * @param primaryKey the primary key of this commerce ml forecast alert entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the relative change of this commerce ml forecast alert entry.
	 *
	 * @param relativeChange the relative change of this commerce ml forecast alert entry
	 */
	@Override
	public void setRelativeChange(double relativeChange) {
		model.setRelativeChange(relativeChange);
	}

	/**
	 * Sets the status of this commerce ml forecast alert entry.
	 *
	 * @param status the status of this commerce ml forecast alert entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the timestamp of this commerce ml forecast alert entry.
	 *
	 * @param timestamp the timestamp of this commerce ml forecast alert entry
	 */
	@Override
	public void setTimestamp(Date timestamp) {
		model.setTimestamp(timestamp);
	}

	/**
	 * Sets the user ID of this commerce ml forecast alert entry.
	 *
	 * @param userId the user ID of this commerce ml forecast alert entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce ml forecast alert entry.
	 *
	 * @param userName the user name of this commerce ml forecast alert entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce ml forecast alert entry.
	 *
	 * @param userUuid the user uuid of this commerce ml forecast alert entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce ml forecast alert entry.
	 *
	 * @param uuid the uuid of this commerce ml forecast alert entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceMLForecastAlertEntryWrapper wrap(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		return new CommerceMLForecastAlertEntryWrapper(
			commerceMLForecastAlertEntry);
	}

}