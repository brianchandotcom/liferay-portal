/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Role}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Role
 * @generated
 */
public class RoleWrapper implements Role, ModelWrapper<Role> {
	public RoleWrapper(Role role) {
		_role = role;
	}

	public Class<?> getModelClass() {
		return Role.class;
	}

	public String getModelClassName() {
		return Role.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("roleId", getRoleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("name", getName());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("subtype", getSubtype());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String subtype = (String)attributes.get("subtype");

		if (subtype != null) {
			setSubtype(subtype);
		}
	}

	/**
	* Returns the primary key of this role.
	*
	* @return the primary key of this role
	*/
	public long getPrimaryKey() {
		return _role.getPrimaryKey();
	}

	/**
	* Sets the primary key of this role.
	*
	* @param primaryKey the primary key of this role
	*/
	public void setPrimaryKey(long primaryKey) {
		_role.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the role ID of this role.
	*
	* @return the role ID of this role
	*/
	public long getRoleId() {
		return _role.getRoleId();
	}

	/**
	* Sets the role ID of this role.
	*
	* @param roleId the role ID of this role
	*/
	public void setRoleId(long roleId) {
		_role.setRoleId(roleId);
	}

	/**
	* Returns the company ID of this role.
	*
	* @return the company ID of this role
	*/
	public long getCompanyId() {
		return _role.getCompanyId();
	}

	/**
	* Sets the company ID of this role.
	*
	* @param companyId the company ID of this role
	*/
	public void setCompanyId(long companyId) {
		_role.setCompanyId(companyId);
	}

	/**
	* Returns the fully qualified class name of this role.
	*
	* @return the fully qualified class name of this role
	*/
	public java.lang.String getClassName() {
		return _role.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_role.setClassName(className);
	}

	/**
	* Returns the class name ID of this role.
	*
	* @return the class name ID of this role
	*/
	public long getClassNameId() {
		return _role.getClassNameId();
	}

	/**
	* Sets the class name ID of this role.
	*
	* @param classNameId the class name ID of this role
	*/
	public void setClassNameId(long classNameId) {
		_role.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this role.
	*
	* @return the class p k of this role
	*/
	public long getClassPK() {
		return _role.getClassPK();
	}

	/**
	* Sets the class p k of this role.
	*
	* @param classPK the class p k of this role
	*/
	public void setClassPK(long classPK) {
		_role.setClassPK(classPK);
	}

	/**
	* Returns the name of this role.
	*
	* @return the name of this role
	*/
	public java.lang.String getName() {
		return _role.getName();
	}

	/**
	* Sets the name of this role.
	*
	* @param name the name of this role
	*/
	public void setName(java.lang.String name) {
		_role.setName(name);
	}

	/**
	* Returns the title of this role.
	*
	* @return the title of this role
	*/
	public java.lang.String getTitle() {
		return _role.getTitle();
	}

	/**
	* Returns the localized title of this role in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this role
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _role.getTitle(locale);
	}

	/**
	* Returns the localized title of this role in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this role. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _role.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this role in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this role
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _role.getTitle(languageId);
	}

	/**
	* Returns the localized title of this role in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this role
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _role.getTitle(languageId, useDefault);
	}

	public java.lang.String getTitleCurrentLanguageId() {
		return _role.getTitleCurrentLanguageId();
	}

	public java.lang.String getTitleCurrentValue() {
		return _role.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this role.
	*
	* @return the locales and localized titles of this role
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _role.getTitleMap();
	}

	/**
	* Sets the title of this role.
	*
	* @param title the title of this role
	*/
	public void setTitle(java.lang.String title) {
		_role.setTitle(title);
	}

	/**
	* Sets the localized title of this role in the language.
	*
	* @param title the localized title of this role
	* @param locale the locale of the language
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_role.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this role in the language, and sets the default locale.
	*
	* @param title the localized title of this role
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_role.setTitle(title, locale, defaultLocale);
	}

	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_role.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this role from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this role
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_role.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this role from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this role
	* @param defaultLocale the default locale
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_role.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this role.
	*
	* @return the description of this role
	*/
	public java.lang.String getDescription() {
		return _role.getDescription();
	}

	/**
	* Returns the localized description of this role in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this role
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _role.getDescription(locale);
	}

	/**
	* Returns the localized description of this role in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this role. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _role.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this role in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this role
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _role.getDescription(languageId);
	}

	/**
	* Returns the localized description of this role in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this role
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _role.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _role.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _role.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this role.
	*
	* @return the locales and localized descriptions of this role
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _role.getDescriptionMap();
	}

	/**
	* Sets the description of this role.
	*
	* @param description the description of this role
	*/
	public void setDescription(java.lang.String description) {
		_role.setDescription(description);
	}

	/**
	* Sets the localized description of this role in the language.
	*
	* @param description the localized description of this role
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_role.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this role in the language, and sets the default locale.
	*
	* @param description the localized description of this role
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_role.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_role.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this role from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this role
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_role.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this role from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this role
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_role.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the type of this role.
	*
	* @return the type of this role
	*/
	public int getType() {
		return _role.getType();
	}

	/**
	* Sets the type of this role.
	*
	* @param type the type of this role
	*/
	public void setType(int type) {
		_role.setType(type);
	}

	/**
	* Returns the subtype of this role.
	*
	* @return the subtype of this role
	*/
	public java.lang.String getSubtype() {
		return _role.getSubtype();
	}

	/**
	* Sets the subtype of this role.
	*
	* @param subtype the subtype of this role
	*/
	public void setSubtype(java.lang.String subtype) {
		_role.setSubtype(subtype);
	}

	public boolean isNew() {
		return _role.isNew();
	}

	public void setNew(boolean n) {
		_role.setNew(n);
	}

	public boolean isCachedModel() {
		return _role.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_role.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _role.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _role.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_role.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _role.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_role.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new RoleWrapper((Role)_role.clone());
	}

	public int compareTo(com.liferay.portal.model.Role role) {
		return _role.compareTo(role);
	}

	@Override
	public int hashCode() {
		return _role.hashCode();
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale) {
		_role.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Role> toCacheModel() {
		return _role.toCacheModel();
	}

	public com.liferay.portal.model.Role toEscapedModel() {
		return new RoleWrapper(_role.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _role.toString();
	}

	public java.lang.String toXmlString() {
		return _role.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_role.persist();
	}

	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _role.getDescriptiveName();
	}

	public java.lang.String getTypeLabel() {
		return _role.getTypeLabel();
	}

	public boolean isTeam() {
		return _role.isTeam();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Role getWrappedRole() {
		return _role;
	}

	public Role getWrappedModel() {
		return _role;
	}

	public void resetOriginalValues() {
		_role.resetOriginalValues();
	}

	private Role _role;
}