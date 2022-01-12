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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CProduct}.
 * </p>
 *
 * @author Marco Leo
 * @see CProduct
 * @generated
 */
public class CProductWrapper
	extends BaseModelWrapper<CProduct>
	implements CProduct, ModelWrapper<CProduct> {

	public CProductWrapper(CProduct cProduct) {
		super(cProduct);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public CProduct cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this c product.
	 *
	 * @return the company ID of this c product
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the c product ID of this c product.
	 *
	 * @return the c product ID of this c product
	 */
	@Override
	public long getCProductId() {
		return model.getCProductId();
	}

	/**
	 * Returns the create date of this c product.
	 *
	 * @return the create date of this c product
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this c product.
	 *
	 * @return the ct collection ID of this c product
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the external reference code of this c product.
	 *
	 * @return the external reference code of this c product
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this c product.
	 *
	 * @return the group ID of this c product
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the latest version of this c product.
	 *
	 * @return the latest version of this c product
	 */
	@Override
	public int getLatestVersion() {
		return model.getLatestVersion();
	}

	/**
	 * Returns the modified date of this c product.
	 *
	 * @return the modified date of this c product
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this c product.
	 *
	 * @return the mvcc version of this c product
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this c product.
	 *
	 * @return the primary key of this c product
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the published cp definition ID of this c product.
	 *
	 * @return the published cp definition ID of this c product
	 */
	@Override
	public long getPublishedCPDefinitionId() {
		return model.getPublishedCPDefinitionId();
	}

	/**
	 * Returns the user ID of this c product.
	 *
	 * @return the user ID of this c product
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this c product.
	 *
	 * @return the user name of this c product
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this c product.
	 *
	 * @return the user uuid of this c product
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this c product.
	 *
	 * @return the uuid of this c product
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
	 * Sets the company ID of this c product.
	 *
	 * @param companyId the company ID of this c product
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the c product ID of this c product.
	 *
	 * @param CProductId the c product ID of this c product
	 */
	@Override
	public void setCProductId(long CProductId) {
		model.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this c product.
	 *
	 * @param createDate the create date of this c product
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this c product.
	 *
	 * @param ctCollectionId the ct collection ID of this c product
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the external reference code of this c product.
	 *
	 * @param externalReferenceCode the external reference code of this c product
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this c product.
	 *
	 * @param groupId the group ID of this c product
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the latest version of this c product.
	 *
	 * @param latestVersion the latest version of this c product
	 */
	@Override
	public void setLatestVersion(int latestVersion) {
		model.setLatestVersion(latestVersion);
	}

	/**
	 * Sets the modified date of this c product.
	 *
	 * @param modifiedDate the modified date of this c product
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this c product.
	 *
	 * @param mvccVersion the mvcc version of this c product
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this c product.
	 *
	 * @param primaryKey the primary key of this c product
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the published cp definition ID of this c product.
	 *
	 * @param publishedCPDefinitionId the published cp definition ID of this c product
	 */
	@Override
	public void setPublishedCPDefinitionId(long publishedCPDefinitionId) {
		model.setPublishedCPDefinitionId(publishedCPDefinitionId);
	}

	/**
	 * Sets the user ID of this c product.
	 *
	 * @param userId the user ID of this c product
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this c product.
	 *
	 * @param userName the user name of this c product
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this c product.
	 *
	 * @param userUuid the user uuid of this c product
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this c product.
	 *
	 * @param uuid the uuid of this c product
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CProduct, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CProduct, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CProductWrapper wrap(CProduct cProduct) {
		return new CProductWrapper(cProduct);
	}

}