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
 * This class is a wrapper for {@link CommerceApplicationBrand}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrand
 * @generated
 */
public class CommerceApplicationBrandWrapper
	implements CommerceApplicationBrand,
			   ModelWrapper<CommerceApplicationBrand> {

	public CommerceApplicationBrandWrapper(
		CommerceApplicationBrand commerceApplicationBrand) {

		_commerceApplicationBrand = commerceApplicationBrand;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceApplicationBrand.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceApplicationBrand.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceApplicationBrandId", getCommerceApplicationBrandId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("logoId", getLogoId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceApplicationBrandId = (Long)attributes.get(
			"commerceApplicationBrandId");

		if (commerceApplicationBrandId != null) {
			setCommerceApplicationBrandId(commerceApplicationBrandId);
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

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceApplicationBrandWrapper(
			(CommerceApplicationBrand)_commerceApplicationBrand.clone());
	}

	@Override
	public int compareTo(CommerceApplicationBrand commerceApplicationBrand) {
		return _commerceApplicationBrand.compareTo(commerceApplicationBrand);
	}

	/**
	 * Returns the commerce application brand ID of this commerce application brand.
	 *
	 * @return the commerce application brand ID of this commerce application brand
	 */
	@Override
	public long getCommerceApplicationBrandId() {
		return _commerceApplicationBrand.getCommerceApplicationBrandId();
	}

	/**
	 * Returns the company ID of this commerce application brand.
	 *
	 * @return the company ID of this commerce application brand
	 */
	@Override
	public long getCompanyId() {
		return _commerceApplicationBrand.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce application brand.
	 *
	 * @return the create date of this commerce application brand
	 */
	@Override
	public Date getCreateDate() {
		return _commerceApplicationBrand.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceApplicationBrand.getExpandoBridge();
	}

	/**
	 * Returns the logo ID of this commerce application brand.
	 *
	 * @return the logo ID of this commerce application brand
	 */
	@Override
	public long getLogoId() {
		return _commerceApplicationBrand.getLogoId();
	}

	/**
	 * Returns the modified date of this commerce application brand.
	 *
	 * @return the modified date of this commerce application brand
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceApplicationBrand.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce application brand.
	 *
	 * @return the name of this commerce application brand
	 */
	@Override
	public String getName() {
		return _commerceApplicationBrand.getName();
	}

	/**
	 * Returns the primary key of this commerce application brand.
	 *
	 * @return the primary key of this commerce application brand
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceApplicationBrand.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceApplicationBrand.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce application brand.
	 *
	 * @return the user ID of this commerce application brand
	 */
	@Override
	public long getUserId() {
		return _commerceApplicationBrand.getUserId();
	}

	/**
	 * Returns the user name of this commerce application brand.
	 *
	 * @return the user name of this commerce application brand
	 */
	@Override
	public String getUserName() {
		return _commerceApplicationBrand.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce application brand.
	 *
	 * @return the user uuid of this commerce application brand
	 */
	@Override
	public String getUserUuid() {
		return _commerceApplicationBrand.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceApplicationBrand.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceApplicationBrand.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceApplicationBrand.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceApplicationBrand.isNew();
	}

	@Override
	public void persist() {
		_commerceApplicationBrand.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceApplicationBrand.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce application brand ID of this commerce application brand.
	 *
	 * @param commerceApplicationBrandId the commerce application brand ID of this commerce application brand
	 */
	@Override
	public void setCommerceApplicationBrandId(long commerceApplicationBrandId) {
		_commerceApplicationBrand.setCommerceApplicationBrandId(
			commerceApplicationBrandId);
	}

	/**
	 * Sets the company ID of this commerce application brand.
	 *
	 * @param companyId the company ID of this commerce application brand
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceApplicationBrand.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce application brand.
	 *
	 * @param createDate the create date of this commerce application brand
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceApplicationBrand.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceApplicationBrand.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceApplicationBrand.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceApplicationBrand.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the logo ID of this commerce application brand.
	 *
	 * @param logoId the logo ID of this commerce application brand
	 */
	@Override
	public void setLogoId(long logoId) {
		_commerceApplicationBrand.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this commerce application brand.
	 *
	 * @param modifiedDate the modified date of this commerce application brand
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceApplicationBrand.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce application brand.
	 *
	 * @param name the name of this commerce application brand
	 */
	@Override
	public void setName(String name) {
		_commerceApplicationBrand.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceApplicationBrand.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce application brand.
	 *
	 * @param primaryKey the primary key of this commerce application brand
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceApplicationBrand.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceApplicationBrand.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce application brand.
	 *
	 * @param userId the user ID of this commerce application brand
	 */
	@Override
	public void setUserId(long userId) {
		_commerceApplicationBrand.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce application brand.
	 *
	 * @param userName the user name of this commerce application brand
	 */
	@Override
	public void setUserName(String userName) {
		_commerceApplicationBrand.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce application brand.
	 *
	 * @param userUuid the user uuid of this commerce application brand
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceApplicationBrand.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceApplicationBrand>
		toCacheModel() {

		return _commerceApplicationBrand.toCacheModel();
	}

	@Override
	public CommerceApplicationBrand toEscapedModel() {
		return new CommerceApplicationBrandWrapper(
			_commerceApplicationBrand.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceApplicationBrand.toString();
	}

	@Override
	public CommerceApplicationBrand toUnescapedModel() {
		return new CommerceApplicationBrandWrapper(
			_commerceApplicationBrand.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceApplicationBrand.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceApplicationBrandWrapper)) {
			return false;
		}

		CommerceApplicationBrandWrapper commerceApplicationBrandWrapper =
			(CommerceApplicationBrandWrapper)object;

		if (Objects.equals(
				_commerceApplicationBrand,
				commerceApplicationBrandWrapper._commerceApplicationBrand)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceApplicationBrand getWrappedModel() {
		return _commerceApplicationBrand;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceApplicationBrand.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceApplicationBrand.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceApplicationBrand.resetOriginalValues();
	}

	private final CommerceApplicationBrand _commerceApplicationBrand;

}