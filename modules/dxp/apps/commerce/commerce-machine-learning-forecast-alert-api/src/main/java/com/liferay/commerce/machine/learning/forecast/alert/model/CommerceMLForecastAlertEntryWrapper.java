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

package com.liferay.commerce.machine.learning.forecast.alert.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
	implements CommerceMLForecastAlertEntry,
			   ModelWrapper<CommerceMLForecastAlertEntry> {

	public CommerceMLForecastAlertEntryWrapper(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		_commerceMLForecastAlertEntry = commerceMLForecastAlertEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceMLForecastAlertEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceMLForecastAlertEntry.class.getName();
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
	public Object clone() {
		return new CommerceMLForecastAlertEntryWrapper(
			(CommerceMLForecastAlertEntry)
				_commerceMLForecastAlertEntry.clone());
	}

	@Override
	public int compareTo(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		return _commerceMLForecastAlertEntry.compareTo(
			commerceMLForecastAlertEntry);
	}

	/**
	 * Returns the actual of this commerce ml forecast alert entry.
	 *
	 * @return the actual of this commerce ml forecast alert entry
	 */
	@Override
	public double getActual() {
		return _commerceMLForecastAlertEntry.getActual();
	}

	/**
	 * Returns the commerce account ID of this commerce ml forecast alert entry.
	 *
	 * @return the commerce account ID of this commerce ml forecast alert entry
	 */
	@Override
	public long getCommerceAccountId() {
		return _commerceMLForecastAlertEntry.getCommerceAccountId();
	}

	/**
	 * Returns the commerce ml forecast alert entry ID of this commerce ml forecast alert entry.
	 *
	 * @return the commerce ml forecast alert entry ID of this commerce ml forecast alert entry
	 */
	@Override
	public long getCommerceMLForecastAlertEntryId() {
		return _commerceMLForecastAlertEntry.
			getCommerceMLForecastAlertEntryId();
	}

	/**
	 * Returns the company ID of this commerce ml forecast alert entry.
	 *
	 * @return the company ID of this commerce ml forecast alert entry
	 */
	@Override
	public long getCompanyId() {
		return _commerceMLForecastAlertEntry.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce ml forecast alert entry.
	 *
	 * @return the create date of this commerce ml forecast alert entry
	 */
	@Override
	public Date getCreateDate() {
		return _commerceMLForecastAlertEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceMLForecastAlertEntry.getExpandoBridge();
	}

	/**
	 * Returns the forecast of this commerce ml forecast alert entry.
	 *
	 * @return the forecast of this commerce ml forecast alert entry
	 */
	@Override
	public double getForecast() {
		return _commerceMLForecastAlertEntry.getForecast();
	}

	/**
	 * Returns the modified date of this commerce ml forecast alert entry.
	 *
	 * @return the modified date of this commerce ml forecast alert entry
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceMLForecastAlertEntry.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce ml forecast alert entry.
	 *
	 * @return the primary key of this commerce ml forecast alert entry
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceMLForecastAlertEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceMLForecastAlertEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the relative change of this commerce ml forecast alert entry.
	 *
	 * @return the relative change of this commerce ml forecast alert entry
	 */
	@Override
	public double getRelativeChange() {
		return _commerceMLForecastAlertEntry.getRelativeChange();
	}

	/**
	 * Returns the status of this commerce ml forecast alert entry.
	 *
	 * @return the status of this commerce ml forecast alert entry
	 */
	@Override
	public int getStatus() {
		return _commerceMLForecastAlertEntry.getStatus();
	}

	/**
	 * Returns the timestamp of this commerce ml forecast alert entry.
	 *
	 * @return the timestamp of this commerce ml forecast alert entry
	 */
	@Override
	public Date getTimestamp() {
		return _commerceMLForecastAlertEntry.getTimestamp();
	}

	/**
	 * Returns the user ID of this commerce ml forecast alert entry.
	 *
	 * @return the user ID of this commerce ml forecast alert entry
	 */
	@Override
	public long getUserId() {
		return _commerceMLForecastAlertEntry.getUserId();
	}

	/**
	 * Returns the user name of this commerce ml forecast alert entry.
	 *
	 * @return the user name of this commerce ml forecast alert entry
	 */
	@Override
	public String getUserName() {
		return _commerceMLForecastAlertEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce ml forecast alert entry.
	 *
	 * @return the user uuid of this commerce ml forecast alert entry
	 */
	@Override
	public String getUserUuid() {
		return _commerceMLForecastAlertEntry.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce ml forecast alert entry.
	 *
	 * @return the uuid of this commerce ml forecast alert entry
	 */
	@Override
	public String getUuid() {
		return _commerceMLForecastAlertEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceMLForecastAlertEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceMLForecastAlertEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceMLForecastAlertEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceMLForecastAlertEntry.isNew();
	}

	@Override
	public void persist() {
		_commerceMLForecastAlertEntry.persist();
	}

	/**
	 * Sets the actual of this commerce ml forecast alert entry.
	 *
	 * @param actual the actual of this commerce ml forecast alert entry
	 */
	@Override
	public void setActual(double actual) {
		_commerceMLForecastAlertEntry.setActual(actual);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceMLForecastAlertEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account ID of this commerce ml forecast alert entry.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce ml forecast alert entry
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceMLForecastAlertEntry.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce ml forecast alert entry ID of this commerce ml forecast alert entry.
	 *
	 * @param commerceMLForecastAlertEntryId the commerce ml forecast alert entry ID of this commerce ml forecast alert entry
	 */
	@Override
	public void setCommerceMLForecastAlertEntryId(
		long commerceMLForecastAlertEntryId) {

		_commerceMLForecastAlertEntry.setCommerceMLForecastAlertEntryId(
			commerceMLForecastAlertEntryId);
	}

	/**
	 * Sets the company ID of this commerce ml forecast alert entry.
	 *
	 * @param companyId the company ID of this commerce ml forecast alert entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceMLForecastAlertEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce ml forecast alert entry.
	 *
	 * @param createDate the create date of this commerce ml forecast alert entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceMLForecastAlertEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceMLForecastAlertEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceMLForecastAlertEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceMLForecastAlertEntry.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the forecast of this commerce ml forecast alert entry.
	 *
	 * @param forecast the forecast of this commerce ml forecast alert entry
	 */
	@Override
	public void setForecast(double forecast) {
		_commerceMLForecastAlertEntry.setForecast(forecast);
	}

	/**
	 * Sets the modified date of this commerce ml forecast alert entry.
	 *
	 * @param modifiedDate the modified date of this commerce ml forecast alert entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceMLForecastAlertEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceMLForecastAlertEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce ml forecast alert entry.
	 *
	 * @param primaryKey the primary key of this commerce ml forecast alert entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceMLForecastAlertEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceMLForecastAlertEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the relative change of this commerce ml forecast alert entry.
	 *
	 * @param relativeChange the relative change of this commerce ml forecast alert entry
	 */
	@Override
	public void setRelativeChange(double relativeChange) {
		_commerceMLForecastAlertEntry.setRelativeChange(relativeChange);
	}

	/**
	 * Sets the status of this commerce ml forecast alert entry.
	 *
	 * @param status the status of this commerce ml forecast alert entry
	 */
	@Override
	public void setStatus(int status) {
		_commerceMLForecastAlertEntry.setStatus(status);
	}

	/**
	 * Sets the timestamp of this commerce ml forecast alert entry.
	 *
	 * @param timestamp the timestamp of this commerce ml forecast alert entry
	 */
	@Override
	public void setTimestamp(Date timestamp) {
		_commerceMLForecastAlertEntry.setTimestamp(timestamp);
	}

	/**
	 * Sets the user ID of this commerce ml forecast alert entry.
	 *
	 * @param userId the user ID of this commerce ml forecast alert entry
	 */
	@Override
	public void setUserId(long userId) {
		_commerceMLForecastAlertEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce ml forecast alert entry.
	 *
	 * @param userName the user name of this commerce ml forecast alert entry
	 */
	@Override
	public void setUserName(String userName) {
		_commerceMLForecastAlertEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce ml forecast alert entry.
	 *
	 * @param userUuid the user uuid of this commerce ml forecast alert entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceMLForecastAlertEntry.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce ml forecast alert entry.
	 *
	 * @param uuid the uuid of this commerce ml forecast alert entry
	 */
	@Override
	public void setUuid(String uuid) {
		_commerceMLForecastAlertEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceMLForecastAlertEntry> toCacheModel() {

		return _commerceMLForecastAlertEntry.toCacheModel();
	}

	@Override
	public CommerceMLForecastAlertEntry toEscapedModel() {
		return new CommerceMLForecastAlertEntryWrapper(
			_commerceMLForecastAlertEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceMLForecastAlertEntry.toString();
	}

	@Override
	public CommerceMLForecastAlertEntry toUnescapedModel() {
		return new CommerceMLForecastAlertEntryWrapper(
			_commerceMLForecastAlertEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceMLForecastAlertEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceMLForecastAlertEntryWrapper)) {
			return false;
		}

		CommerceMLForecastAlertEntryWrapper
			commerceMLForecastAlertEntryWrapper =
				(CommerceMLForecastAlertEntryWrapper)object;

		if (Objects.equals(
				_commerceMLForecastAlertEntry,
				commerceMLForecastAlertEntryWrapper.
					_commerceMLForecastAlertEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceMLForecastAlertEntry.getStagedModelType();
	}

	@Override
	public CommerceMLForecastAlertEntry getWrappedModel() {
		return _commerceMLForecastAlertEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceMLForecastAlertEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceMLForecastAlertEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceMLForecastAlertEntry.resetOriginalValues();
	}

	private final CommerceMLForecastAlertEntry _commerceMLForecastAlertEntry;

}