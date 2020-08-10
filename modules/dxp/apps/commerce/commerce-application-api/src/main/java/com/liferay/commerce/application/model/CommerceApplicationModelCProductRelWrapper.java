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
 * This class is a wrapper for {@link CommerceApplicationModelCProductRel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRel
 * @generated
 */
public class CommerceApplicationModelCProductRelWrapper
	implements CommerceApplicationModelCProductRel,
			   ModelWrapper<CommerceApplicationModelCProductRel> {

	public CommerceApplicationModelCProductRelWrapper(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		_commerceApplicationModelCProductRel =
			commerceApplicationModelCProductRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceApplicationModelCProductRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceApplicationModelCProductRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceApplicationModelCProductRelId",
			getCommerceApplicationModelCProductRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceApplicationModelId", getCommerceApplicationModelId());
		attributes.put("CProductId", getCProductId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceApplicationModelCProductRelId = (Long)attributes.get(
			"commerceApplicationModelCProductRelId");

		if (commerceApplicationModelCProductRelId != null) {
			setCommerceApplicationModelCProductRelId(
				commerceApplicationModelCProductRelId);
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

		Long commerceApplicationModelId = (Long)attributes.get(
			"commerceApplicationModelId");

		if (commerceApplicationModelId != null) {
			setCommerceApplicationModelId(commerceApplicationModelId);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceApplicationModelCProductRelWrapper(
			(CommerceApplicationModelCProductRel)
				_commerceApplicationModelCProductRel.clone());
	}

	@Override
	public int compareTo(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		return _commerceApplicationModelCProductRel.compareTo(
			commerceApplicationModelCProductRel);
	}

	/**
	 * Returns the commerce application model c product rel ID of this commerce application model c product rel.
	 *
	 * @return the commerce application model c product rel ID of this commerce application model c product rel
	 */
	@Override
	public long getCommerceApplicationModelCProductRelId() {
		return _commerceApplicationModelCProductRel.
			getCommerceApplicationModelCProductRelId();
	}

	/**
	 * Returns the commerce application model ID of this commerce application model c product rel.
	 *
	 * @return the commerce application model ID of this commerce application model c product rel
	 */
	@Override
	public long getCommerceApplicationModelId() {
		return _commerceApplicationModelCProductRel.
			getCommerceApplicationModelId();
	}

	/**
	 * Returns the company ID of this commerce application model c product rel.
	 *
	 * @return the company ID of this commerce application model c product rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceApplicationModelCProductRel.getCompanyId();
	}

	/**
	 * Returns the c product ID of this commerce application model c product rel.
	 *
	 * @return the c product ID of this commerce application model c product rel
	 */
	@Override
	public long getCProductId() {
		return _commerceApplicationModelCProductRel.getCProductId();
	}

	/**
	 * Returns the create date of this commerce application model c product rel.
	 *
	 * @return the create date of this commerce application model c product rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceApplicationModelCProductRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceApplicationModelCProductRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce application model c product rel.
	 *
	 * @return the modified date of this commerce application model c product rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceApplicationModelCProductRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce application model c product rel.
	 *
	 * @return the primary key of this commerce application model c product rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceApplicationModelCProductRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceApplicationModelCProductRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce application model c product rel.
	 *
	 * @return the user ID of this commerce application model c product rel
	 */
	@Override
	public long getUserId() {
		return _commerceApplicationModelCProductRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce application model c product rel.
	 *
	 * @return the user name of this commerce application model c product rel
	 */
	@Override
	public String getUserName() {
		return _commerceApplicationModelCProductRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce application model c product rel.
	 *
	 * @return the user uuid of this commerce application model c product rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceApplicationModelCProductRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceApplicationModelCProductRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceApplicationModelCProductRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceApplicationModelCProductRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceApplicationModelCProductRel.isNew();
	}

	@Override
	public void persist() {
		_commerceApplicationModelCProductRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceApplicationModelCProductRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce application model c product rel ID of this commerce application model c product rel.
	 *
	 * @param commerceApplicationModelCProductRelId the commerce application model c product rel ID of this commerce application model c product rel
	 */
	@Override
	public void setCommerceApplicationModelCProductRelId(
		long commerceApplicationModelCProductRelId) {

		_commerceApplicationModelCProductRel.
			setCommerceApplicationModelCProductRelId(
				commerceApplicationModelCProductRelId);
	}

	/**
	 * Sets the commerce application model ID of this commerce application model c product rel.
	 *
	 * @param commerceApplicationModelId the commerce application model ID of this commerce application model c product rel
	 */
	@Override
	public void setCommerceApplicationModelId(long commerceApplicationModelId) {
		_commerceApplicationModelCProductRel.setCommerceApplicationModelId(
			commerceApplicationModelId);
	}

	/**
	 * Sets the company ID of this commerce application model c product rel.
	 *
	 * @param companyId the company ID of this commerce application model c product rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceApplicationModelCProductRel.setCompanyId(companyId);
	}

	/**
	 * Sets the c product ID of this commerce application model c product rel.
	 *
	 * @param CProductId the c product ID of this commerce application model c product rel
	 */
	@Override
	public void setCProductId(long CProductId) {
		_commerceApplicationModelCProductRel.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this commerce application model c product rel.
	 *
	 * @param createDate the create date of this commerce application model c product rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceApplicationModelCProductRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceApplicationModelCProductRel.setExpandoBridgeAttributes(
			baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceApplicationModelCProductRel.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceApplicationModelCProductRel.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the modified date of this commerce application model c product rel.
	 *
	 * @param modifiedDate the modified date of this commerce application model c product rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceApplicationModelCProductRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceApplicationModelCProductRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce application model c product rel.
	 *
	 * @param primaryKey the primary key of this commerce application model c product rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceApplicationModelCProductRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceApplicationModelCProductRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce application model c product rel.
	 *
	 * @param userId the user ID of this commerce application model c product rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceApplicationModelCProductRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce application model c product rel.
	 *
	 * @param userName the user name of this commerce application model c product rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceApplicationModelCProductRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce application model c product rel.
	 *
	 * @param userUuid the user uuid of this commerce application model c product rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceApplicationModelCProductRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceApplicationModelCProductRel> toCacheModel() {

		return _commerceApplicationModelCProductRel.toCacheModel();
	}

	@Override
	public CommerceApplicationModelCProductRel toEscapedModel() {
		return new CommerceApplicationModelCProductRelWrapper(
			_commerceApplicationModelCProductRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceApplicationModelCProductRel.toString();
	}

	@Override
	public CommerceApplicationModelCProductRel toUnescapedModel() {
		return new CommerceApplicationModelCProductRelWrapper(
			_commerceApplicationModelCProductRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceApplicationModelCProductRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceApplicationModelCProductRelWrapper)) {
			return false;
		}

		CommerceApplicationModelCProductRelWrapper
			commerceApplicationModelCProductRelWrapper =
				(CommerceApplicationModelCProductRelWrapper)object;

		if (Objects.equals(
				_commerceApplicationModelCProductRel,
				commerceApplicationModelCProductRelWrapper.
					_commerceApplicationModelCProductRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceApplicationModelCProductRel getWrappedModel() {
		return _commerceApplicationModelCProductRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceApplicationModelCProductRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceApplicationModelCProductRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceApplicationModelCProductRel.resetOriginalValues();
	}

	private final CommerceApplicationModelCProductRel
		_commerceApplicationModelCProductRel;

}