/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.versioning.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutContentVersion}.
 * </p>
 *
 * @author Lourdes Fernández Besada
 * @see LayoutContentVersion
 * @generated
 */
public class LayoutContentVersionWrapper
	extends BaseModelWrapper<LayoutContentVersion>
	implements LayoutContentVersion, ModelWrapper<LayoutContentVersion> {

	public LayoutContentVersionWrapper(
		LayoutContentVersion layoutContentVersion) {

		super(layoutContentVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("layoutContentVersionId", getLayoutContentVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("plid", getPlid());
		attributes.put("name", getName());
		attributes.put("version", getVersion());
		attributes.put("specSchemaVersion", getSpecSchemaVersion());
		attributes.put("data", getData());
		attributes.put("dataHash", getDataHash());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long layoutContentVersionId = (Long)attributes.get(
			"layoutContentVersionId");

		if (layoutContentVersionId != null) {
			setLayoutContentVersionId(layoutContentVersionId);
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

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String specSchemaVersion = (String)attributes.get("specSchemaVersion");

		if (specSchemaVersion != null) {
			setSpecSchemaVersion(specSchemaVersion);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}

		String dataHash = (String)attributes.get("dataHash");

		if (dataHash != null) {
			setDataHash(dataHash);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public LayoutContentVersion cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this layout content version.
	 *
	 * @return the company ID of this layout content version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout content version.
	 *
	 * @return the create date of this layout content version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the data of this layout content version.
	 *
	 * @return the data of this layout content version
	 */
	@Override
	public String getData() {
		return model.getData();
	}

	/**
	 * Returns the data hash of this layout content version.
	 *
	 * @return the data hash of this layout content version
	 */
	@Override
	public String getDataHash() {
		return model.getDataHash();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the external reference code of this layout content version.
	 *
	 * @return the external reference code of this layout content version
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this layout content version.
	 *
	 * @return the group ID of this layout content version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout content version ID of this layout content version.
	 *
	 * @return the layout content version ID of this layout content version
	 */
	@Override
	public long getLayoutContentVersionId() {
		return model.getLayoutContentVersionId();
	}

	/**
	 * Returns the modified date of this layout content version.
	 *
	 * @return the modified date of this layout content version
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this layout content version.
	 *
	 * @return the mvcc version of this layout content version
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this layout content version.
	 *
	 * @return the name of this layout content version
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this layout content version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this layout content version
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this layout content version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this layout content version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this layout content version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this layout content version
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this layout content version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this layout content version
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized names of this layout content version.
	 *
	 * @return the locales and localized names of this layout content version
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the plid of this layout content version.
	 *
	 * @return the plid of this layout content version
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the primary key of this layout content version.
	 *
	 * @return the primary key of this layout content version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the spec schema version of this layout content version.
	 *
	 * @return the spec schema version of this layout content version
	 */
	@Override
	public String getSpecSchemaVersion() {
		return model.getSpecSchemaVersion();
	}

	/**
	 * Returns the status of this layout content version.
	 *
	 * @return the status of this layout content version
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this layout content version.
	 *
	 * @return the status by user ID of this layout content version
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this layout content version.
	 *
	 * @return the status by user name of this layout content version
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this layout content version.
	 *
	 * @return the status by user uuid of this layout content version
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this layout content version.
	 *
	 * @return the status date of this layout content version
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the user ID of this layout content version.
	 *
	 * @return the user ID of this layout content version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout content version.
	 *
	 * @return the user name of this layout content version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout content version.
	 *
	 * @return the user uuid of this layout content version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the version of this layout content version.
	 *
	 * @return the version of this layout content version
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this layout content version is approved.
	 *
	 * @return <code>true</code> if this layout content version is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this layout content version is denied.
	 *
	 * @return <code>true</code> if this layout content version is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this layout content version is a draft.
	 *
	 * @return <code>true</code> if this layout content version is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this layout content version is expired.
	 *
	 * @return <code>true</code> if this layout content version is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this layout content version is inactive.
	 *
	 * @return <code>true</code> if this layout content version is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this layout content version is incomplete.
	 *
	 * @return <code>true</code> if this layout content version is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this layout content version is pending.
	 *
	 * @return <code>true</code> if this layout content version is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this layout content version is scheduled.
	 *
	 * @return <code>true</code> if this layout content version is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
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
	 * Sets the company ID of this layout content version.
	 *
	 * @param companyId the company ID of this layout content version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout content version.
	 *
	 * @param createDate the create date of this layout content version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the data of this layout content version.
	 *
	 * @param data the data of this layout content version
	 */
	@Override
	public void setData(String data) {
		model.setData(data);
	}

	/**
	 * Sets the data hash of this layout content version.
	 *
	 * @param dataHash the data hash of this layout content version
	 */
	@Override
	public void setDataHash(String dataHash) {
		model.setDataHash(dataHash);
	}

	/**
	 * Sets the external reference code of this layout content version.
	 *
	 * @param externalReferenceCode the external reference code of this layout content version
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this layout content version.
	 *
	 * @param groupId the group ID of this layout content version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout content version ID of this layout content version.
	 *
	 * @param layoutContentVersionId the layout content version ID of this layout content version
	 */
	@Override
	public void setLayoutContentVersionId(long layoutContentVersionId) {
		model.setLayoutContentVersionId(layoutContentVersionId);
	}

	/**
	 * Sets the modified date of this layout content version.
	 *
	 * @param modifiedDate the modified date of this layout content version
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this layout content version.
	 *
	 * @param mvccVersion the mvcc version of this layout content version
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this layout content version.
	 *
	 * @param name the name of this layout content version
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this layout content version in the language.
	 *
	 * @param name the localized name of this layout content version
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this layout content version in the language, and sets the default locale.
	 *
	 * @param name the localized name of this layout content version
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this layout content version from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this layout content version
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this layout content version from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this layout content version
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the plid of this layout content version.
	 *
	 * @param plid the plid of this layout content version
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the primary key of this layout content version.
	 *
	 * @param primaryKey the primary key of this layout content version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the spec schema version of this layout content version.
	 *
	 * @param specSchemaVersion the spec schema version of this layout content version
	 */
	@Override
	public void setSpecSchemaVersion(String specSchemaVersion) {
		model.setSpecSchemaVersion(specSchemaVersion);
	}

	/**
	 * Sets the status of this layout content version.
	 *
	 * @param status the status of this layout content version
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this layout content version.
	 *
	 * @param statusByUserId the status by user ID of this layout content version
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this layout content version.
	 *
	 * @param statusByUserName the status by user name of this layout content version
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this layout content version.
	 *
	 * @param statusByUserUuid the status by user uuid of this layout content version
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this layout content version.
	 *
	 * @param statusDate the status date of this layout content version
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the user ID of this layout content version.
	 *
	 * @param userId the user ID of this layout content version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout content version.
	 *
	 * @param userName the user name of this layout content version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout content version.
	 *
	 * @param userUuid the user uuid of this layout content version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the version of this layout content version.
	 *
	 * @param version the version of this layout content version
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected LayoutContentVersionWrapper wrap(
		LayoutContentVersion layoutContentVersion) {

		return new LayoutContentVersionWrapper(layoutContentVersion);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:826929976