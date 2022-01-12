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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CommerceCatalog}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceCatalog
 * @generated
 */
public class CommerceCatalogWrapper
	extends BaseModelWrapper<CommerceCatalog>
	implements CommerceCatalog, ModelWrapper<CommerceCatalog> {

	public CommerceCatalogWrapper(CommerceCatalog commerceCatalog) {
		super(commerceCatalog);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceCatalogId", getCommerceCatalogId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("commerceCurrencyCode", getCommerceCurrencyCode());
		attributes.put(
			"catalogDefaultLanguageId", getCatalogDefaultLanguageId());
		attributes.put("system", isSystem());

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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceCatalogId = (Long)attributes.get("commerceCatalogId");

		if (commerceCatalogId != null) {
			setCommerceCatalogId(commerceCatalogId);
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

		String commerceCurrencyCode = (String)attributes.get(
			"commerceCurrencyCode");

		if (commerceCurrencyCode != null) {
			setCommerceCurrencyCode(commerceCurrencyCode);
		}

		String catalogDefaultLanguageId = (String)attributes.get(
			"catalogDefaultLanguageId");

		if (catalogDefaultLanguageId != null) {
			setCatalogDefaultLanguageId(catalogDefaultLanguageId);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}
	}

	@Override
	public CommerceCatalog cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the catalog default language ID of this commerce catalog.
	 *
	 * @return the catalog default language ID of this commerce catalog
	 */
	@Override
	public String getCatalogDefaultLanguageId() {
		return model.getCatalogDefaultLanguageId();
	}

	/**
	 * Returns the commerce catalog ID of this commerce catalog.
	 *
	 * @return the commerce catalog ID of this commerce catalog
	 */
	@Override
	public long getCommerceCatalogId() {
		return model.getCommerceCatalogId();
	}

	/**
	 * Returns the commerce currency code of this commerce catalog.
	 *
	 * @return the commerce currency code of this commerce catalog
	 */
	@Override
	public String getCommerceCurrencyCode() {
		return model.getCommerceCurrencyCode();
	}

	/**
	 * Returns the company ID of this commerce catalog.
	 *
	 * @return the company ID of this commerce catalog
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce catalog.
	 *
	 * @return the create date of this commerce catalog
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this commerce catalog.
	 *
	 * @return the ct collection ID of this commerce catalog
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the external reference code of this commerce catalog.
	 *
	 * @return the external reference code of this commerce catalog
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	@Override
	public com.liferay.portal.kernel.model.Group getGroup() {
		return model.getGroup();
	}

	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce catalog.
	 *
	 * @return the modified date of this commerce catalog
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce catalog.
	 *
	 * @return the mvcc version of this commerce catalog
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this commerce catalog.
	 *
	 * @return the name of this commerce catalog
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce catalog.
	 *
	 * @return the primary key of this commerce catalog
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the system of this commerce catalog.
	 *
	 * @return the system of this commerce catalog
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	/**
	 * Returns the user ID of this commerce catalog.
	 *
	 * @return the user ID of this commerce catalog
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce catalog.
	 *
	 * @return the user name of this commerce catalog
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce catalog.
	 *
	 * @return the user uuid of this commerce catalog
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce catalog is system.
	 *
	 * @return <code>true</code> if this commerce catalog is system; <code>false</code> otherwise
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
	 * Sets the catalog default language ID of this commerce catalog.
	 *
	 * @param catalogDefaultLanguageId the catalog default language ID of this commerce catalog
	 */
	@Override
	public void setCatalogDefaultLanguageId(String catalogDefaultLanguageId) {
		model.setCatalogDefaultLanguageId(catalogDefaultLanguageId);
	}

	/**
	 * Sets the commerce catalog ID of this commerce catalog.
	 *
	 * @param commerceCatalogId the commerce catalog ID of this commerce catalog
	 */
	@Override
	public void setCommerceCatalogId(long commerceCatalogId) {
		model.setCommerceCatalogId(commerceCatalogId);
	}

	/**
	 * Sets the commerce currency code of this commerce catalog.
	 *
	 * @param commerceCurrencyCode the commerce currency code of this commerce catalog
	 */
	@Override
	public void setCommerceCurrencyCode(String commerceCurrencyCode) {
		model.setCommerceCurrencyCode(commerceCurrencyCode);
	}

	/**
	 * Sets the company ID of this commerce catalog.
	 *
	 * @param companyId the company ID of this commerce catalog
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce catalog.
	 *
	 * @param createDate the create date of this commerce catalog
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this commerce catalog.
	 *
	 * @param ctCollectionId the ct collection ID of this commerce catalog
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the external reference code of this commerce catalog.
	 *
	 * @param externalReferenceCode the external reference code of this commerce catalog
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce catalog.
	 *
	 * @param modifiedDate the modified date of this commerce catalog
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce catalog.
	 *
	 * @param mvccVersion the mvcc version of this commerce catalog
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this commerce catalog.
	 *
	 * @param name the name of this commerce catalog
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce catalog.
	 *
	 * @param primaryKey the primary key of this commerce catalog
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this commerce catalog is system.
	 *
	 * @param system the system of this commerce catalog
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the user ID of this commerce catalog.
	 *
	 * @param userId the user ID of this commerce catalog
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce catalog.
	 *
	 * @param userName the user name of this commerce catalog
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce catalog.
	 *
	 * @param userUuid the user uuid of this commerce catalog
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public Map<String, Function<CommerceCatalog, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CommerceCatalog, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CommerceCatalogWrapper wrap(CommerceCatalog commerceCatalog) {
		return new CommerceCatalogWrapper(commerceCatalog);
	}

}