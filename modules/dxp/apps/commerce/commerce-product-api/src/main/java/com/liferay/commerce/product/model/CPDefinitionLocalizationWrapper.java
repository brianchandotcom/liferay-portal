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
 * This class is a wrapper for {@link CPDefinitionLocalization}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionLocalization
 * @generated
 */
@ProviderType
public class CPDefinitionLocalizationWrapper implements CPDefinitionLocalization,
	ModelWrapper<CPDefinitionLocalization> {
	public CPDefinitionLocalizationWrapper(
		CPDefinitionLocalization cpDefinitionLocalization) {
		_cpDefinitionLocalization = cpDefinitionLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("cpDefinitionLocalizationId",
			getCpDefinitionLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("languageId", getLanguageId());
		attributes.put("title", getTitle());
		attributes.put("shortDescription", getShortDescription());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long cpDefinitionLocalizationId = (Long)attributes.get(
				"cpDefinitionLocalizationId");

		if (cpDefinitionLocalizationId != null) {
			setCpDefinitionLocalizationId(cpDefinitionLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String shortDescription = (String)attributes.get("shortDescription");

		if (shortDescription != null) {
			setShortDescription(shortDescription);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public CPDefinitionLocalization toEscapedModel() {
		return new CPDefinitionLocalizationWrapper(_cpDefinitionLocalization.toEscapedModel());
	}

	@Override
	public CPDefinitionLocalization toUnescapedModel() {
		return new CPDefinitionLocalizationWrapper(_cpDefinitionLocalization.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinitionLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinitionLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpDefinitionLocalization.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinitionLocalization.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinitionLocalization> toCacheModel() {
		return _cpDefinitionLocalization.toCacheModel();
	}

	@Override
	public int compareTo(CPDefinitionLocalization cpDefinitionLocalization) {
		return _cpDefinitionLocalization.compareTo(cpDefinitionLocalization);
	}

	@Override
	public int hashCode() {
		return _cpDefinitionLocalization.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinitionLocalization.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPDefinitionLocalizationWrapper((CPDefinitionLocalization)_cpDefinitionLocalization.clone());
	}

	/**
	* Returns the description of this cp definition localization.
	*
	* @return the description of this cp definition localization
	*/
	@Override
	public java.lang.String getDescription() {
		return _cpDefinitionLocalization.getDescription();
	}

	/**
	* Returns the language ID of this cp definition localization.
	*
	* @return the language ID of this cp definition localization
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _cpDefinitionLocalization.getLanguageId();
	}

	/**
	* Returns the short description of this cp definition localization.
	*
	* @return the short description of this cp definition localization
	*/
	@Override
	public java.lang.String getShortDescription() {
		return _cpDefinitionLocalization.getShortDescription();
	}

	/**
	* Returns the title of this cp definition localization.
	*
	* @return the title of this cp definition localization
	*/
	@Override
	public java.lang.String getTitle() {
		return _cpDefinitionLocalization.getTitle();
	}

	@Override
	public java.lang.String toString() {
		return _cpDefinitionLocalization.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpDefinitionLocalization.toXmlString();
	}

	/**
	* Returns the cp definition ID of this cp definition localization.
	*
	* @return the cp definition ID of this cp definition localization
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionLocalization.getCPDefinitionId();
	}

	/**
	* Returns the company ID of this cp definition localization.
	*
	* @return the company ID of this cp definition localization
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinitionLocalization.getCompanyId();
	}

	/**
	* Returns the cp definition localization ID of this cp definition localization.
	*
	* @return the cp definition localization ID of this cp definition localization
	*/
	@Override
	public long getCpDefinitionLocalizationId() {
		return _cpDefinitionLocalization.getCpDefinitionLocalizationId();
	}

	/**
	* Returns the mvcc version of this cp definition localization.
	*
	* @return the mvcc version of this cp definition localization
	*/
	@Override
	public long getMvccVersion() {
		return _cpDefinitionLocalization.getMvccVersion();
	}

	/**
	* Returns the primary key of this cp definition localization.
	*
	* @return the primary key of this cp definition localization
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinitionLocalization.getPrimaryKey();
	}

	/**
	* Sets the cp definition ID of this cp definition localization.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition localization
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinitionLocalization.setCPDefinitionId(CPDefinitionId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinitionLocalization.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp definition localization.
	*
	* @param companyId the company ID of this cp definition localization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinitionLocalization.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition localization ID of this cp definition localization.
	*
	* @param cpDefinitionLocalizationId the cp definition localization ID of this cp definition localization
	*/
	@Override
	public void setCpDefinitionLocalizationId(long cpDefinitionLocalizationId) {
		_cpDefinitionLocalization.setCpDefinitionLocalizationId(cpDefinitionLocalizationId);
	}

	/**
	* Sets the description of this cp definition localization.
	*
	* @param description the description of this cp definition localization
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_cpDefinitionLocalization.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinitionLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinitionLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinitionLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the language ID of this cp definition localization.
	*
	* @param languageId the language ID of this cp definition localization
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_cpDefinitionLocalization.setLanguageId(languageId);
	}

	/**
	* Sets the mvcc version of this cp definition localization.
	*
	* @param mvccVersion the mvcc version of this cp definition localization
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_cpDefinitionLocalization.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinitionLocalization.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition localization.
	*
	* @param primaryKey the primary key of this cp definition localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinitionLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinitionLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the short description of this cp definition localization.
	*
	* @param shortDescription the short description of this cp definition localization
	*/
	@Override
	public void setShortDescription(java.lang.String shortDescription) {
		_cpDefinitionLocalization.setShortDescription(shortDescription);
	}

	/**
	* Sets the title of this cp definition localization.
	*
	* @param title the title of this cp definition localization
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_cpDefinitionLocalization.setTitle(title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionLocalizationWrapper)) {
			return false;
		}

		CPDefinitionLocalizationWrapper cpDefinitionLocalizationWrapper = (CPDefinitionLocalizationWrapper)obj;

		if (Objects.equals(_cpDefinitionLocalization,
					cpDefinitionLocalizationWrapper._cpDefinitionLocalization)) {
			return true;
		}

		return false;
	}

	@Override
	public CPDefinitionLocalization getWrappedModel() {
		return _cpDefinitionLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinitionLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinitionLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinitionLocalization.resetOriginalValues();
	}

	private final CPDefinitionLocalization _cpDefinitionLocalization;
}