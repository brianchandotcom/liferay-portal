/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectFolder}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFolder
 * @generated
 */
public class ObjectFolderWrapper
	extends BaseModelWrapper<ObjectFolder>
	implements ModelWrapper<ObjectFolder>, ObjectFolder {

	public ObjectFolderWrapper(ObjectFolder objectFolder) {
		super(objectFolder);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("objectFolderId", getObjectFolderId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("label", getLabel());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
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

		Long objectFolderId = (Long)attributes.get("objectFolderId");

		if (objectFolderId != null) {
			setObjectFolderId(objectFolderId);
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

		String label = (String)attributes.get("label");

		if (label != null) {
			setLabel(label);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public ObjectFolder cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this object folder.
	 *
	 * @return the company ID of this object folder
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object folder.
	 *
	 * @return the create date of this object folder
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the external reference code of this object folder.
	 *
	 * @return the external reference code of this object folder
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the label of this object folder.
	 *
	 * @return the label of this object folder
	 */
	@Override
	public String getLabel() {
		return model.getLabel();
	}

	/**
	 * Returns the localized label of this object folder in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized label of this object folder
	 */
	@Override
	public String getLabel(java.util.Locale locale) {
		return model.getLabel(locale);
	}

	/**
	 * Returns the localized label of this object folder in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this object folder. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getLabel(java.util.Locale locale, boolean useDefault) {
		return model.getLabel(locale, useDefault);
	}

	/**
	 * Returns the localized label of this object folder in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized label of this object folder
	 */
	@Override
	public String getLabel(String languageId) {
		return model.getLabel(languageId);
	}

	/**
	 * Returns the localized label of this object folder in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this object folder
	 */
	@Override
	public String getLabel(String languageId, boolean useDefault) {
		return model.getLabel(languageId, useDefault);
	}

	@Override
	public String getLabelCurrentLanguageId() {
		return model.getLabelCurrentLanguageId();
	}

	@Override
	public String getLabelCurrentValue() {
		return model.getLabelCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized labels of this object folder.
	 *
	 * @return the locales and localized labels of this object folder
	 */
	@Override
	public Map<java.util.Locale, String> getLabelMap() {
		return model.getLabelMap();
	}

	/**
	 * Returns the modified date of this object folder.
	 *
	 * @return the modified date of this object folder
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object folder.
	 *
	 * @return the mvcc version of this object folder
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object folder.
	 *
	 * @return the name of this object folder
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the object folder ID of this object folder.
	 *
	 * @return the object folder ID of this object folder
	 */
	@Override
	public long getObjectFolderId() {
		return model.getObjectFolderId();
	}

	/**
	 * Returns the primary key of this object folder.
	 *
	 * @return the primary key of this object folder
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this object folder.
	 *
	 * @return the user ID of this object folder
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object folder.
	 *
	 * @return the user name of this object folder
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object folder.
	 *
	 * @return the user uuid of this object folder
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object folder.
	 *
	 * @return the uuid of this object folder
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean isUncategorized() {
		return model.isUncategorized();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets the company ID of this object folder.
	 *
	 * @param companyId the company ID of this object folder
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object folder.
	 *
	 * @param createDate the create date of this object folder
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this object folder.
	 *
	 * @param externalReferenceCode the external reference code of this object folder
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the label of this object folder.
	 *
	 * @param label the label of this object folder
	 */
	@Override
	public void setLabel(String label) {
		model.setLabel(label);
	}

	/**
	 * Sets the localized label of this object folder in the language.
	 *
	 * @param label the localized label of this object folder
	 * @param locale the locale of the language
	 */
	@Override
	public void setLabel(String label, java.util.Locale locale) {
		model.setLabel(label, locale);
	}

	/**
	 * Sets the localized label of this object folder in the language, and sets the default locale.
	 *
	 * @param label the localized label of this object folder
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabel(
		String label, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setLabel(label, locale, defaultLocale);
	}

	@Override
	public void setLabelCurrentLanguageId(String languageId) {
		model.setLabelCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized labels of this object folder from the map of locales and localized labels.
	 *
	 * @param labelMap the locales and localized labels of this object folder
	 */
	@Override
	public void setLabelMap(Map<java.util.Locale, String> labelMap) {
		model.setLabelMap(labelMap);
	}

	/**
	 * Sets the localized labels of this object folder from the map of locales and localized labels, and sets the default locale.
	 *
	 * @param labelMap the locales and localized labels of this object folder
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabelMap(
		Map<java.util.Locale, String> labelMap,
		java.util.Locale defaultLocale) {

		model.setLabelMap(labelMap, defaultLocale);
	}

	/**
	 * Sets the modified date of this object folder.
	 *
	 * @param modifiedDate the modified date of this object folder
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object folder.
	 *
	 * @param mvccVersion the mvcc version of this object folder
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object folder.
	 *
	 * @param name the name of this object folder
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the object folder ID of this object folder.
	 *
	 * @param objectFolderId the object folder ID of this object folder
	 */
	@Override
	public void setObjectFolderId(long objectFolderId) {
		model.setObjectFolderId(objectFolderId);
	}

	/**
	 * Sets the primary key of this object folder.
	 *
	 * @param primaryKey the primary key of this object folder
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this object folder.
	 *
	 * @param userId the user ID of this object folder
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object folder.
	 *
	 * @param userName the user name of this object folder
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object folder.
	 *
	 * @param userUuid the user uuid of this object folder
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object folder.
	 *
	 * @param uuid the uuid of this object folder
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectFolderWrapper wrap(ObjectFolder objectFolder) {
		return new ObjectFolderWrapper(objectFolder);
	}

}