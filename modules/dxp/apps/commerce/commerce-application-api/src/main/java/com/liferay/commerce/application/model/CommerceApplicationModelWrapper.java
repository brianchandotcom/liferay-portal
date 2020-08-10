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

package com.liferay.commerce.application.model;

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
 * This class is a wrapper for {@link CommerceApplicationModel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModel
 * @generated
 */
public class CommerceApplicationModelWrapper
	implements CommerceApplicationModel,
			   ModelWrapper<CommerceApplicationModel> {

	public CommerceApplicationModelWrapper(
		CommerceApplicationModel commerceApplicationModel) {

		_commerceApplicationModel = commerceApplicationModel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceApplicationModel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceApplicationModel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceApplicationModelId", getCommerceApplicationModelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceApplicationBrandId", getCommerceApplicationBrandId());
		attributes.put("name", getName());
		attributes.put("year", getYear());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceApplicationModelId = (Long)attributes.get(
			"commerceApplicationModelId");

		if (commerceApplicationModelId != null) {
			setCommerceApplicationModelId(commerceApplicationModelId);
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

		Long commerceApplicationBrandId = (Long)attributes.get(
			"commerceApplicationBrandId");

		if (commerceApplicationBrandId != null) {
			setCommerceApplicationBrandId(commerceApplicationBrandId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String year = (String)attributes.get("year");

		if (year != null) {
			setYear(year);
		}
	}

	@Override
	public Object clone() {
		return new CommerceApplicationModelWrapper(
			(CommerceApplicationModel)_commerceApplicationModel.clone());
	}

	@Override
	public int compareTo(CommerceApplicationModel commerceApplicationModel) {
		return _commerceApplicationModel.compareTo(commerceApplicationModel);
	}

	/**
	 * Returns the commerce application brand ID of this commerce application model.
	 *
	 * @return the commerce application brand ID of this commerce application model
	 */
	@Override
	public long getCommerceApplicationBrandId() {
		return _commerceApplicationModel.getCommerceApplicationBrandId();
	}

	/**
	 * Returns the commerce application model ID of this commerce application model.
	 *
	 * @return the commerce application model ID of this commerce application model
	 */
	@Override
	public long getCommerceApplicationModelId() {
		return _commerceApplicationModel.getCommerceApplicationModelId();
	}

	/**
	 * Returns the company ID of this commerce application model.
	 *
	 * @return the company ID of this commerce application model
	 */
	@Override
	public long getCompanyId() {
		return _commerceApplicationModel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce application model.
	 *
	 * @return the create date of this commerce application model
	 */
	@Override
	public Date getCreateDate() {
		return _commerceApplicationModel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceApplicationModel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce application model.
	 *
	 * @return the modified date of this commerce application model
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceApplicationModel.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce application model.
	 *
	 * @return the name of this commerce application model
	 */
	@Override
	public String getName() {
		return _commerceApplicationModel.getName();
	}

	/**
	 * Returns the primary key of this commerce application model.
	 *
	 * @return the primary key of this commerce application model
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceApplicationModel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceApplicationModel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce application model.
	 *
	 * @return the user ID of this commerce application model
	 */
	@Override
	public long getUserId() {
		return _commerceApplicationModel.getUserId();
	}

	/**
	 * Returns the user name of this commerce application model.
	 *
	 * @return the user name of this commerce application model
	 */
	@Override
	public String getUserName() {
		return _commerceApplicationModel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce application model.
	 *
	 * @return the user uuid of this commerce application model
	 */
	@Override
	public String getUserUuid() {
		return _commerceApplicationModel.getUserUuid();
	}

	/**
	 * Returns the year of this commerce application model.
	 *
	 * @return the year of this commerce application model
	 */
	@Override
	public String getYear() {
		return _commerceApplicationModel.getYear();
	}

	@Override
	public int hashCode() {
		return _commerceApplicationModel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceApplicationModel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceApplicationModel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceApplicationModel.isNew();
	}

	@Override
	public void persist() {
		_commerceApplicationModel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceApplicationModel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce application brand ID of this commerce application model.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID of this commerce application model
	 */
	@Override
	public void setCommerceApplicationBrandId(long commerceApplicationBrandId) {
		_commerceApplicationModel.setCommerceApplicationBrandId(
			commerceApplicationBrandId);
	}

	/**
	 * Sets the commerce application model ID of this commerce application model.
	 *
	 * @param commerceApplicationModelId the commerce application model ID of this commerce application model
	 */
	@Override
	public void setCommerceApplicationModelId(long commerceApplicationModelId) {
		_commerceApplicationModel.setCommerceApplicationModelId(
			commerceApplicationModelId);
	}

	/**
	 * Sets the company ID of this commerce application model.
	 *
	 * @param companyId the company ID of this commerce application model
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceApplicationModel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce application model.
	 *
	 * @param createDate the create date of this commerce application model
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceApplicationModel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceApplicationModel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceApplicationModel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceApplicationModel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the modified date of this commerce application model.
	 *
	 * @param modifiedDate the modified date of this commerce application model
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceApplicationModel.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce application model.
	 *
	 * @param name the name of this commerce application model
	 */
	@Override
	public void setName(String name) {
		_commerceApplicationModel.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceApplicationModel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce application model.
	 *
	 * @param primaryKey the primary key of this commerce application model
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceApplicationModel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceApplicationModel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce application model.
	 *
	 * @param userId the user ID of this commerce application model
	 */
	@Override
	public void setUserId(long userId) {
		_commerceApplicationModel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce application model.
	 *
	 * @param userName the user name of this commerce application model
	 */
	@Override
	public void setUserName(String userName) {
		_commerceApplicationModel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce application model.
	 *
	 * @param userUuid the user uuid of this commerce application model
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceApplicationModel.setUserUuid(userUuid);
	}

	/**
	 * Sets the year of this commerce application model.
	 *
	 * @param year the year of this commerce application model
	 */
	@Override
	public void setYear(String year) {
		_commerceApplicationModel.setYear(year);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceApplicationModel>
		toCacheModel() {

		return _commerceApplicationModel.toCacheModel();
	}

	@Override
	public CommerceApplicationModel toEscapedModel() {
		return new CommerceApplicationModelWrapper(
			_commerceApplicationModel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceApplicationModel.toString();
	}

	@Override
	public CommerceApplicationModel toUnescapedModel() {
		return new CommerceApplicationModelWrapper(
			_commerceApplicationModel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceApplicationModel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceApplicationModelWrapper)) {
			return false;
		}

		CommerceApplicationModelWrapper commerceApplicationModelWrapper =
			(CommerceApplicationModelWrapper)object;

		if (Objects.equals(
				_commerceApplicationModel,
				commerceApplicationModelWrapper._commerceApplicationModel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceApplicationModel getWrappedModel() {
		return _commerceApplicationModel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceApplicationModel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceApplicationModel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceApplicationModel.resetOriginalValues();
	}

	private final CommerceApplicationModel _commerceApplicationModel;

}