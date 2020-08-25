/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.model;

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
 * This class is a wrapper for {@link CProduct}.
 * </p>
 *
 * @author Marco Leo
 * @see CProduct
 * @generated
 */
public class CProductWrapper implements CProduct, ModelWrapper<CProduct> {

	public CProductWrapper(CProduct cProduct) {
		_cProduct = cProduct;
	}

	@Override
	public Class<?> getModelClass() {
		return CProduct.class;
	}

	@Override
	public String getModelClassName() {
		return CProduct.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("CProductId", getCProductId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("publishedCPDefinitionId", getPublishedCPDefinitionId());
		attributes.put("latestVersion", getLatestVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Long publishedCPDefinitionId = (Long)attributes.get(
			"publishedCPDefinitionId");

		if (publishedCPDefinitionId != null) {
			setPublishedCPDefinitionId(publishedCPDefinitionId);
		}

		Integer latestVersion = (Integer)attributes.get("latestVersion");

		if (latestVersion != null) {
			setLatestVersion(latestVersion);
		}
	}

	@Override
	public Object clone() {
		return new CProductWrapper((CProduct)_cProduct.clone());
	}

	@Override
	public int compareTo(CProduct cProduct) {
		return _cProduct.compareTo(cProduct);
	}

	/**
	 * Returns the company ID of this c product.
	 *
	 * @return the company ID of this c product
	 */
	@Override
	public long getCompanyId() {
		return _cProduct.getCompanyId();
	}

	/**
	 * Returns the c product ID of this c product.
	 *
	 * @return the c product ID of this c product
	 */
	@Override
	public long getCProductId() {
		return _cProduct.getCProductId();
	}

	/**
	 * Returns the create date of this c product.
	 *
	 * @return the create date of this c product
	 */
	@Override
	public Date getCreateDate() {
		return _cProduct.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cProduct.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this c product.
	 *
	 * @return the external reference code of this c product
	 */
	@Override
	public String getExternalReferenceCode() {
		return _cProduct.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this c product.
	 *
	 * @return the group ID of this c product
	 */
	@Override
	public long getGroupId() {
		return _cProduct.getGroupId();
	}

	/**
	 * Returns the latest version of this c product.
	 *
	 * @return the latest version of this c product
	 */
	@Override
	public int getLatestVersion() {
		return _cProduct.getLatestVersion();
	}

	/**
	 * Returns the modified date of this c product.
	 *
	 * @return the modified date of this c product
	 */
	@Override
	public Date getModifiedDate() {
		return _cProduct.getModifiedDate();
	}

	/**
	 * Returns the primary key of this c product.
	 *
	 * @return the primary key of this c product
	 */
	@Override
	public long getPrimaryKey() {
		return _cProduct.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cProduct.getPrimaryKeyObj();
	}

	/**
	 * Returns the published cp definition ID of this c product.
	 *
	 * @return the published cp definition ID of this c product
	 */
	@Override
	public long getPublishedCPDefinitionId() {
		return _cProduct.getPublishedCPDefinitionId();
	}

	/**
	 * Returns the user ID of this c product.
	 *
	 * @return the user ID of this c product
	 */
	@Override
	public long getUserId() {
		return _cProduct.getUserId();
	}

	/**
	 * Returns the user name of this c product.
	 *
	 * @return the user name of this c product
	 */
	@Override
	public String getUserName() {
		return _cProduct.getUserName();
	}

	/**
	 * Returns the user uuid of this c product.
	 *
	 * @return the user uuid of this c product
	 */
	@Override
	public String getUserUuid() {
		return _cProduct.getUserUuid();
	}

	/**
	 * Returns the uuid of this c product.
	 *
	 * @return the uuid of this c product
	 */
	@Override
	public String getUuid() {
		return _cProduct.getUuid();
	}

	@Override
	public int hashCode() {
		return _cProduct.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cProduct.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cProduct.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cProduct.isNew();
	}

	@Override
	public void persist() {
		_cProduct.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cProduct.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this c product.
	 *
	 * @param companyId the company ID of this c product
	 */
	@Override
	public void setCompanyId(long companyId) {
		_cProduct.setCompanyId(companyId);
	}

	/**
	 * Sets the c product ID of this c product.
	 *
	 * @param CProductId the c product ID of this c product
	 */
	@Override
	public void setCProductId(long CProductId) {
		_cProduct.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this c product.
	 *
	 * @param createDate the create date of this c product
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_cProduct.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_cProduct.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cProduct.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cProduct.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this c product.
	 *
	 * @param externalReferenceCode the external reference code of this c product
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_cProduct.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this c product.
	 *
	 * @param groupId the group ID of this c product
	 */
	@Override
	public void setGroupId(long groupId) {
		_cProduct.setGroupId(groupId);
	}

	/**
	 * Sets the latest version of this c product.
	 *
	 * @param latestVersion the latest version of this c product
	 */
	@Override
	public void setLatestVersion(int latestVersion) {
		_cProduct.setLatestVersion(latestVersion);
	}

	/**
	 * Sets the modified date of this c product.
	 *
	 * @param modifiedDate the modified date of this c product
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cProduct.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cProduct.setNew(n);
	}

	/**
	 * Sets the primary key of this c product.
	 *
	 * @param primaryKey the primary key of this c product
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cProduct.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cProduct.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the published cp definition ID of this c product.
	 *
	 * @param publishedCPDefinitionId the published cp definition ID of this c product
	 */
	@Override
	public void setPublishedCPDefinitionId(long publishedCPDefinitionId) {
		_cProduct.setPublishedCPDefinitionId(publishedCPDefinitionId);
	}

	/**
	 * Sets the user ID of this c product.
	 *
	 * @param userId the user ID of this c product
	 */
	@Override
	public void setUserId(long userId) {
		_cProduct.setUserId(userId);
	}

	/**
	 * Sets the user name of this c product.
	 *
	 * @param userName the user name of this c product
	 */
	@Override
	public void setUserName(String userName) {
		_cProduct.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this c product.
	 *
	 * @param userUuid the user uuid of this c product
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_cProduct.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this c product.
	 *
	 * @param uuid the uuid of this c product
	 */
	@Override
	public void setUuid(String uuid) {
		_cProduct.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CProduct> toCacheModel() {
		return _cProduct.toCacheModel();
	}

	@Override
	public CProduct toEscapedModel() {
		return new CProductWrapper(_cProduct.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cProduct.toString();
	}

	@Override
	public CProduct toUnescapedModel() {
		return new CProductWrapper(_cProduct.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cProduct.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CProductWrapper)) {
			return false;
		}

		CProductWrapper cProductWrapper = (CProductWrapper)object;

		if (Objects.equals(_cProduct, cProductWrapper._cProduct)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cProduct.getStagedModelType();
	}

	@Override
	public CProduct getWrappedModel() {
		return _cProduct;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cProduct.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cProduct.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cProduct.resetOriginalValues();
	}

	private final CProduct _cProduct;

}