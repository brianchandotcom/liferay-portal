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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceProductDefinitionLocalization}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionLocalization
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionLocalizationWrapper
	implements CommerceProductDefinitionLocalization,
		ModelWrapper<CommerceProductDefinitionLocalization> {
	public CommerceProductDefinitionLocalizationWrapper(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		_commerceProductDefinitionLocalization = commerceProductDefinitionLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductDefinitionLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductDefinitionLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("commerceProductDefinitionLocalizationId",
			getCommerceProductDefinitionLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("commerceProductDefinitionPK",
			getCommerceProductDefinitionPK());
		attributes.put("languageId", getLanguageId());
		attributes.put("title", getTitle());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceProductDefinitionLocalizationId = (Long)attributes.get(
				"commerceProductDefinitionLocalizationId");

		if (commerceProductDefinitionLocalizationId != null) {
			setCommerceProductDefinitionLocalizationId(commerceProductDefinitionLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long commerceProductDefinitionPK = (Long)attributes.get(
				"commerceProductDefinitionPK");

		if (commerceProductDefinitionPK != null) {
			setCommerceProductDefinitionPK(commerceProductDefinitionPK);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public CommerceProductDefinitionLocalization toEscapedModel() {
		return new CommerceProductDefinitionLocalizationWrapper(_commerceProductDefinitionLocalization.toEscapedModel());
	}

	@Override
	public CommerceProductDefinitionLocalization toUnescapedModel() {
		return new CommerceProductDefinitionLocalizationWrapper(_commerceProductDefinitionLocalization.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _commerceProductDefinitionLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceProductDefinitionLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceProductDefinitionLocalization.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceProductDefinitionLocalization.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceProductDefinitionLocalization> toCacheModel() {
		return _commerceProductDefinitionLocalization.toCacheModel();
	}

	@Override
	public int compareTo(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		return _commerceProductDefinitionLocalization.compareTo(commerceProductDefinitionLocalization);
	}

	@Override
	public int hashCode() {
		return _commerceProductDefinitionLocalization.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductDefinitionLocalization.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceProductDefinitionLocalizationWrapper((CommerceProductDefinitionLocalization)_commerceProductDefinitionLocalization.clone());
	}

	/**
	* Returns the description of this commerce product definition localization.
	*
	* @return the description of this commerce product definition localization
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceProductDefinitionLocalization.getDescription();
	}

	/**
	* Returns the language ID of this commerce product definition localization.
	*
	* @return the language ID of this commerce product definition localization
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _commerceProductDefinitionLocalization.getLanguageId();
	}

	/**
	* Returns the title of this commerce product definition localization.
	*
	* @return the title of this commerce product definition localization
	*/
	@Override
	public java.lang.String getTitle() {
		return _commerceProductDefinitionLocalization.getTitle();
	}

	/**
	* Returns the url title of this commerce product definition localization.
	*
	* @return the url title of this commerce product definition localization
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _commerceProductDefinitionLocalization.getUrlTitle();
	}

	@Override
	public java.lang.String toString() {
		return _commerceProductDefinitionLocalization.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceProductDefinitionLocalization.toXmlString();
	}

	/**
	* Returns the commerce product definition localization ID of this commerce product definition localization.
	*
	* @return the commerce product definition localization ID of this commerce product definition localization
	*/
	@Override
	public long getCommerceProductDefinitionLocalizationId() {
		return _commerceProductDefinitionLocalization.getCommerceProductDefinitionLocalizationId();
	}

	/**
	* Returns the commerce product definition pk of this commerce product definition localization.
	*
	* @return the commerce product definition pk of this commerce product definition localization
	*/
	@Override
	public long getCommerceProductDefinitionPK() {
		return _commerceProductDefinitionLocalization.getCommerceProductDefinitionPK();
	}

	/**
	* Returns the company ID of this commerce product definition localization.
	*
	* @return the company ID of this commerce product definition localization
	*/
	@Override
	public long getCompanyId() {
		return _commerceProductDefinitionLocalization.getCompanyId();
	}

	/**
	* Returns the mvcc version of this commerce product definition localization.
	*
	* @return the mvcc version of this commerce product definition localization
	*/
	@Override
	public long getMvccVersion() {
		return _commerceProductDefinitionLocalization.getMvccVersion();
	}

	/**
	* Returns the primary key of this commerce product definition localization.
	*
	* @return the primary key of this commerce product definition localization
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceProductDefinitionLocalization.getPrimaryKey();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceProductDefinitionLocalization.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce product definition localization ID of this commerce product definition localization.
	*
	* @param commerceProductDefinitionLocalizationId the commerce product definition localization ID of this commerce product definition localization
	*/
	@Override
	public void setCommerceProductDefinitionLocalizationId(
		long commerceProductDefinitionLocalizationId) {
		_commerceProductDefinitionLocalization.setCommerceProductDefinitionLocalizationId(commerceProductDefinitionLocalizationId);
	}

	/**
	* Sets the commerce product definition pk of this commerce product definition localization.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk of this commerce product definition localization
	*/
	@Override
	public void setCommerceProductDefinitionPK(long commerceProductDefinitionPK) {
		_commerceProductDefinitionLocalization.setCommerceProductDefinitionPK(commerceProductDefinitionPK);
	}

	/**
	* Sets the company ID of this commerce product definition localization.
	*
	* @param companyId the company ID of this commerce product definition localization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceProductDefinitionLocalization.setCompanyId(companyId);
	}

	/**
	* Sets the description of this commerce product definition localization.
	*
	* @param description the description of this commerce product definition localization
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceProductDefinitionLocalization.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceProductDefinitionLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceProductDefinitionLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceProductDefinitionLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the language ID of this commerce product definition localization.
	*
	* @param languageId the language ID of this commerce product definition localization
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_commerceProductDefinitionLocalization.setLanguageId(languageId);
	}

	/**
	* Sets the mvcc version of this commerce product definition localization.
	*
	* @param mvccVersion the mvcc version of this commerce product definition localization
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_commerceProductDefinitionLocalization.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_commerceProductDefinitionLocalization.setNew(n);
	}

	/**
	* Sets the primary key of this commerce product definition localization.
	*
	* @param primaryKey the primary key of this commerce product definition localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceProductDefinitionLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceProductDefinitionLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the title of this commerce product definition localization.
	*
	* @param title the title of this commerce product definition localization
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_commerceProductDefinitionLocalization.setTitle(title);
	}

	/**
	* Sets the url title of this commerce product definition localization.
	*
	* @param urlTitle the url title of this commerce product definition localization
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_commerceProductDefinitionLocalization.setUrlTitle(urlTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefinitionLocalizationWrapper)) {
			return false;
		}

		CommerceProductDefinitionLocalizationWrapper commerceProductDefinitionLocalizationWrapper =
			(CommerceProductDefinitionLocalizationWrapper)obj;

		if (Objects.equals(_commerceProductDefinitionLocalization,
					commerceProductDefinitionLocalizationWrapper._commerceProductDefinitionLocalization)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceProductDefinitionLocalization getWrappedModel() {
		return _commerceProductDefinitionLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceProductDefinitionLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceProductDefinitionLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceProductDefinitionLocalization.resetOriginalValues();
	}

	private final CommerceProductDefinitionLocalization _commerceProductDefinitionLocalization;
}