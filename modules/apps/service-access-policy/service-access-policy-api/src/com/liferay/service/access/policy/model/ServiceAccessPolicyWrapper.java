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

package com.liferay.service.access.policy.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ServiceAccessPolicy}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicy
 * @generated
 */
@ProviderType
public class ServiceAccessPolicyWrapper implements ServiceAccessPolicy,
	ModelWrapper<ServiceAccessPolicy> {
	public ServiceAccessPolicyWrapper(ServiceAccessPolicy serviceAccessPolicy) {
		_serviceAccessPolicy = serviceAccessPolicy;
	}

	@Override
	public Class<?> getModelClass() {
		return ServiceAccessPolicy.class;
	}

	@Override
	public String getModelClassName() {
		return ServiceAccessPolicy.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("serviceAccessPolicyId", getServiceAccessPolicyId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("allowedServiceSignatures", getAllowedServiceSignatures());
		attributes.put("defaultServiceAccessPolicy",
			getDefaultServiceAccessPolicy());
		attributes.put("name", getName());
		attributes.put("title", getTitle());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long serviceAccessPolicyId = (Long)attributes.get(
				"serviceAccessPolicyId");

		if (serviceAccessPolicyId != null) {
			setServiceAccessPolicyId(serviceAccessPolicyId);
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

		String allowedServiceSignatures = (String)attributes.get(
				"allowedServiceSignatures");

		if (allowedServiceSignatures != null) {
			setAllowedServiceSignatures(allowedServiceSignatures);
		}

		Boolean defaultServiceAccessPolicy = (Boolean)attributes.get(
				"defaultServiceAccessPolicy");

		if (defaultServiceAccessPolicy != null) {
			setDefaultServiceAccessPolicy(defaultServiceAccessPolicy);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new ServiceAccessPolicyWrapper((ServiceAccessPolicy)_serviceAccessPolicy.clone());
	}

	@Override
	public int compareTo(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy) {
		return _serviceAccessPolicy.compareTo(serviceAccessPolicy);
	}

	/**
	* Returns the allowed service signatures of this service access policy.
	*
	* @return the allowed service signatures of this service access policy
	*/
	@Override
	public java.lang.String getAllowedServiceSignatures() {
		return _serviceAccessPolicy.getAllowedServiceSignatures();
	}

	@Override
	public java.util.List<java.lang.String> getAllowedServiceSignaturesList() {
		return _serviceAccessPolicy.getAllowedServiceSignaturesList();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _serviceAccessPolicy.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this service access policy.
	*
	* @return the company ID of this service access policy
	*/
	@Override
	public long getCompanyId() {
		return _serviceAccessPolicy.getCompanyId();
	}

	/**
	* Returns the create date of this service access policy.
	*
	* @return the create date of this service access policy
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _serviceAccessPolicy.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _serviceAccessPolicy.getDefaultLanguageId();
	}

	/**
	* Returns the default service access policy of this service access policy.
	*
	* @return the default service access policy of this service access policy
	*/
	@Override
	public boolean getDefaultServiceAccessPolicy() {
		return _serviceAccessPolicy.getDefaultServiceAccessPolicy();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _serviceAccessPolicy.getExpandoBridge();
	}

	/**
	* Returns the modified date of this service access policy.
	*
	* @return the modified date of this service access policy
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _serviceAccessPolicy.getModifiedDate();
	}

	/**
	* Returns the name of this service access policy.
	*
	* @return the name of this service access policy
	*/
	@Override
	public java.lang.String getName() {
		return _serviceAccessPolicy.getName();
	}

	/**
	* Returns the primary key of this service access policy.
	*
	* @return the primary key of this service access policy
	*/
	@Override
	public long getPrimaryKey() {
		return _serviceAccessPolicy.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _serviceAccessPolicy.getPrimaryKeyObj();
	}

	/**
	* Returns the service access policy ID of this service access policy.
	*
	* @return the service access policy ID of this service access policy
	*/
	@Override
	public long getServiceAccessPolicyId() {
		return _serviceAccessPolicy.getServiceAccessPolicyId();
	}

	/**
	* Returns the title of this service access policy.
	*
	* @return the title of this service access policy
	*/
	@Override
	public java.lang.String getTitle() {
		return _serviceAccessPolicy.getTitle();
	}

	/**
	* Returns the localized title of this service access policy in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this service access policy
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _serviceAccessPolicy.getTitle(languageId);
	}

	/**
	* Returns the localized title of this service access policy in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this service access policy
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _serviceAccessPolicy.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this service access policy in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this service access policy
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _serviceAccessPolicy.getTitle(locale);
	}

	/**
	* Returns the localized title of this service access policy in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this service access policy. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _serviceAccessPolicy.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _serviceAccessPolicy.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _serviceAccessPolicy.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this service access policy.
	*
	* @return the locales and localized titles of this service access policy
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _serviceAccessPolicy.getTitleMap();
	}

	/**
	* Returns the user ID of this service access policy.
	*
	* @return the user ID of this service access policy
	*/
	@Override
	public long getUserId() {
		return _serviceAccessPolicy.getUserId();
	}

	/**
	* Returns the user name of this service access policy.
	*
	* @return the user name of this service access policy
	*/
	@Override
	public java.lang.String getUserName() {
		return _serviceAccessPolicy.getUserName();
	}

	/**
	* Returns the user uuid of this service access policy.
	*
	* @return the user uuid of this service access policy
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _serviceAccessPolicy.getUserUuid();
	}

	/**
	* Returns the uuid of this service access policy.
	*
	* @return the uuid of this service access policy
	*/
	@Override
	public java.lang.String getUuid() {
		return _serviceAccessPolicy.getUuid();
	}

	@Override
	public int hashCode() {
		return _serviceAccessPolicy.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _serviceAccessPolicy.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this service access policy is default service access policy.
	*
	* @return <code>true</code> if this service access policy is default service access policy; <code>false</code> otherwise
	*/
	@Override
	public boolean isDefaultServiceAccessPolicy() {
		return _serviceAccessPolicy.isDefaultServiceAccessPolicy();
	}

	@Override
	public boolean isEscapedModel() {
		return _serviceAccessPolicy.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _serviceAccessPolicy.isNew();
	}

	@Override
	public void persist() {
		_serviceAccessPolicy.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_serviceAccessPolicy.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_serviceAccessPolicy.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the allowed service signatures of this service access policy.
	*
	* @param allowedServiceSignatures the allowed service signatures of this service access policy
	*/
	@Override
	public void setAllowedServiceSignatures(
		java.lang.String allowedServiceSignatures) {
		_serviceAccessPolicy.setAllowedServiceSignatures(allowedServiceSignatures);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_serviceAccessPolicy.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this service access policy.
	*
	* @param companyId the company ID of this service access policy
	*/
	@Override
	public void setCompanyId(long companyId) {
		_serviceAccessPolicy.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this service access policy.
	*
	* @param createDate the create date of this service access policy
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_serviceAccessPolicy.setCreateDate(createDate);
	}

	/**
	* Sets whether this service access policy is default service access policy.
	*
	* @param defaultServiceAccessPolicy the default service access policy of this service access policy
	*/
	@Override
	public void setDefaultServiceAccessPolicy(
		boolean defaultServiceAccessPolicy) {
		_serviceAccessPolicy.setDefaultServiceAccessPolicy(defaultServiceAccessPolicy);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_serviceAccessPolicy.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_serviceAccessPolicy.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_serviceAccessPolicy.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this service access policy.
	*
	* @param modifiedDate the modified date of this service access policy
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_serviceAccessPolicy.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this service access policy.
	*
	* @param name the name of this service access policy
	*/
	@Override
	public void setName(java.lang.String name) {
		_serviceAccessPolicy.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_serviceAccessPolicy.setNew(n);
	}

	/**
	* Sets the primary key of this service access policy.
	*
	* @param primaryKey the primary key of this service access policy
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_serviceAccessPolicy.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_serviceAccessPolicy.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the service access policy ID of this service access policy.
	*
	* @param serviceAccessPolicyId the service access policy ID of this service access policy
	*/
	@Override
	public void setServiceAccessPolicyId(long serviceAccessPolicyId) {
		_serviceAccessPolicy.setServiceAccessPolicyId(serviceAccessPolicyId);
	}

	/**
	* Sets the title of this service access policy.
	*
	* @param title the title of this service access policy
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_serviceAccessPolicy.setTitle(title);
	}

	/**
	* Sets the localized title of this service access policy in the language.
	*
	* @param title the localized title of this service access policy
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_serviceAccessPolicy.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this service access policy in the language, and sets the default locale.
	*
	* @param title the localized title of this service access policy
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_serviceAccessPolicy.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_serviceAccessPolicy.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this service access policy from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this service access policy
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_serviceAccessPolicy.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this service access policy from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this service access policy
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_serviceAccessPolicy.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this service access policy.
	*
	* @param userId the user ID of this service access policy
	*/
	@Override
	public void setUserId(long userId) {
		_serviceAccessPolicy.setUserId(userId);
	}

	/**
	* Sets the user name of this service access policy.
	*
	* @param userName the user name of this service access policy
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_serviceAccessPolicy.setUserName(userName);
	}

	/**
	* Sets the user uuid of this service access policy.
	*
	* @param userUuid the user uuid of this service access policy
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_serviceAccessPolicy.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this service access policy.
	*
	* @param uuid the uuid of this service access policy
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_serviceAccessPolicy.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.service.access.policy.model.ServiceAccessPolicy> toCacheModel() {
		return _serviceAccessPolicy.toCacheModel();
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy toEscapedModel() {
		return new ServiceAccessPolicyWrapper(_serviceAccessPolicy.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _serviceAccessPolicy.toString();
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy toUnescapedModel() {
		return new ServiceAccessPolicyWrapper(_serviceAccessPolicy.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _serviceAccessPolicy.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceAccessPolicyWrapper)) {
			return false;
		}

		ServiceAccessPolicyWrapper serviceAccessPolicyWrapper = (ServiceAccessPolicyWrapper)obj;

		if (Validator.equals(_serviceAccessPolicy,
					serviceAccessPolicyWrapper._serviceAccessPolicy)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _serviceAccessPolicy.getStagedModelType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public ServiceAccessPolicy getWrappedServiceAccessPolicy() {
		return _serviceAccessPolicy;
	}

	@Override
	public ServiceAccessPolicy getWrappedModel() {
		return _serviceAccessPolicy;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _serviceAccessPolicy.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _serviceAccessPolicy.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_serviceAccessPolicy.resetOriginalValues();
	}

	private final ServiceAccessPolicy _serviceAccessPolicy;
}