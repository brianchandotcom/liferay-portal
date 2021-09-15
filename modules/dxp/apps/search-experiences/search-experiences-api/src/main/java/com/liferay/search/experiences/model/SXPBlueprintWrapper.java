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

package com.liferay.search.experiences.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SXPBlueprint}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SXPBlueprint
 * @generated
 */
public class SXPBlueprintWrapper
	extends BaseModelWrapper<SXPBlueprint>
	implements ModelWrapper<SXPBlueprint>, SXPBlueprint {

	public SXPBlueprintWrapper(SXPBlueprint sxpBlueprint) {
		super(sxpBlueprint);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("sxpBlueprintId", getSXPBlueprintId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("configurationJSON", getConfigurationJSON());
		attributes.put("selectedElementsJSON", getSelectedElementsJSON());

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

		Long sxpBlueprintId = (Long)attributes.get("sxpBlueprintId");

		if (sxpBlueprintId != null) {
			setSXPBlueprintId(sxpBlueprintId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String configurationJSON = (String)attributes.get("configurationJSON");

		if (configurationJSON != null) {
			setConfigurationJSON(configurationJSON);
		}

		String selectedElementsJSON = (String)attributes.get(
			"selectedElementsJSON");

		if (selectedElementsJSON != null) {
			setSelectedElementsJSON(selectedElementsJSON);
		}
	}

	@Override
	public SXPBlueprint cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this sxp blueprint.
	 *
	 * @return the company ID of this sxp blueprint
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the configuration json of this sxp blueprint.
	 *
	 * @return the configuration json of this sxp blueprint
	 */
	@Override
	public String getConfigurationJSON() {
		return model.getConfigurationJSON();
	}

	/**
	 * Returns the create date of this sxp blueprint.
	 *
	 * @return the create date of this sxp blueprint
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
	 * Returns the description of this sxp blueprint.
	 *
	 * @return the description of this sxp blueprint
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this sxp blueprint in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this sxp blueprint
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this sxp blueprint in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this sxp blueprint. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this sxp blueprint in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this sxp blueprint
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this sxp blueprint in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this sxp blueprint
	 */
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return model.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return model.getDescriptionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized descriptions of this sxp blueprint.
	 *
	 * @return the locales and localized descriptions of this sxp blueprint
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the group ID of this sxp blueprint.
	 *
	 * @return the group ID of this sxp blueprint
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this sxp blueprint.
	 *
	 * @return the modified date of this sxp blueprint
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this sxp blueprint.
	 *
	 * @return the mvcc version of this sxp blueprint
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this sxp blueprint.
	 *
	 * @return the primary key of this sxp blueprint
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the selected elements json of this sxp blueprint.
	 *
	 * @return the selected elements json of this sxp blueprint
	 */
	@Override
	public String getSelectedElementsJSON() {
		return model.getSelectedElementsJSON();
	}

	/**
	 * Returns the status of this sxp blueprint.
	 *
	 * @return the status of this sxp blueprint
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this sxp blueprint.
	 *
	 * @return the status by user ID of this sxp blueprint
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this sxp blueprint.
	 *
	 * @return the status by user name of this sxp blueprint
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this sxp blueprint.
	 *
	 * @return the status by user uuid of this sxp blueprint
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this sxp blueprint.
	 *
	 * @return the status date of this sxp blueprint
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the sxp blueprint ID of this sxp blueprint.
	 *
	 * @return the sxp blueprint ID of this sxp blueprint
	 */
	@Override
	public long getSXPBlueprintId() {
		return model.getSXPBlueprintId();
	}

	/**
	 * Returns the title of this sxp blueprint.
	 *
	 * @return the title of this sxp blueprint
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the localized title of this sxp blueprint in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this sxp blueprint
	 */
	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
	}

	/**
	 * Returns the localized title of this sxp blueprint in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this sxp blueprint. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return model.getTitle(locale, useDefault);
	}

	/**
	 * Returns the localized title of this sxp blueprint in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this sxp blueprint
	 */
	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	/**
	 * Returns the localized title of this sxp blueprint in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this sxp blueprint
	 */
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return model.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return model.getTitleCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized titles of this sxp blueprint.
	 *
	 * @return the locales and localized titles of this sxp blueprint
	 */
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return model.getTitleMap();
	}

	/**
	 * Returns the user ID of this sxp blueprint.
	 *
	 * @return the user ID of this sxp blueprint
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this sxp blueprint.
	 *
	 * @return the user name of this sxp blueprint
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this sxp blueprint.
	 *
	 * @return the user uuid of this sxp blueprint
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this sxp blueprint.
	 *
	 * @return the uuid of this sxp blueprint
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this sxp blueprint is approved.
	 *
	 * @return <code>true</code> if this sxp blueprint is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this sxp blueprint is denied.
	 *
	 * @return <code>true</code> if this sxp blueprint is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this sxp blueprint is a draft.
	 *
	 * @return <code>true</code> if this sxp blueprint is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this sxp blueprint is expired.
	 *
	 * @return <code>true</code> if this sxp blueprint is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this sxp blueprint is inactive.
	 *
	 * @return <code>true</code> if this sxp blueprint is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this sxp blueprint is incomplete.
	 *
	 * @return <code>true</code> if this sxp blueprint is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this sxp blueprint is pending.
	 *
	 * @return <code>true</code> if this sxp blueprint is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this sxp blueprint is scheduled.
	 *
	 * @return <code>true</code> if this sxp blueprint is scheduled; <code>false</code> otherwise
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
	 * Sets the company ID of this sxp blueprint.
	 *
	 * @param companyId the company ID of this sxp blueprint
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the configuration json of this sxp blueprint.
	 *
	 * @param configurationJSON the configuration json of this sxp blueprint
	 */
	@Override
	public void setConfigurationJSON(String configurationJSON) {
		model.setConfigurationJSON(configurationJSON);
	}

	/**
	 * Sets the create date of this sxp blueprint.
	 *
	 * @param createDate the create date of this sxp blueprint
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this sxp blueprint.
	 *
	 * @param description the description of this sxp blueprint
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this sxp blueprint in the language.
	 *
	 * @param description the localized description of this sxp blueprint
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this sxp blueprint in the language, and sets the default locale.
	 *
	 * @param description the localized description of this sxp blueprint
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this sxp blueprint from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this sxp blueprint
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this sxp blueprint from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this sxp blueprint
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the group ID of this sxp blueprint.
	 *
	 * @param groupId the group ID of this sxp blueprint
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this sxp blueprint.
	 *
	 * @param modifiedDate the modified date of this sxp blueprint
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this sxp blueprint.
	 *
	 * @param mvccVersion the mvcc version of this sxp blueprint
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this sxp blueprint.
	 *
	 * @param primaryKey the primary key of this sxp blueprint
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the selected elements json of this sxp blueprint.
	 *
	 * @param selectedElementsJSON the selected elements json of this sxp blueprint
	 */
	@Override
	public void setSelectedElementsJSON(String selectedElementsJSON) {
		model.setSelectedElementsJSON(selectedElementsJSON);
	}

	/**
	 * Sets the status of this sxp blueprint.
	 *
	 * @param status the status of this sxp blueprint
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this sxp blueprint.
	 *
	 * @param statusByUserId the status by user ID of this sxp blueprint
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this sxp blueprint.
	 *
	 * @param statusByUserName the status by user name of this sxp blueprint
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this sxp blueprint.
	 *
	 * @param statusByUserUuid the status by user uuid of this sxp blueprint
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this sxp blueprint.
	 *
	 * @param statusDate the status date of this sxp blueprint
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the sxp blueprint ID of this sxp blueprint.
	 *
	 * @param sxpBlueprintId the sxp blueprint ID of this sxp blueprint
	 */
	@Override
	public void setSXPBlueprintId(long sxpBlueprintId) {
		model.setSXPBlueprintId(sxpBlueprintId);
	}

	/**
	 * Sets the title of this sxp blueprint.
	 *
	 * @param title the title of this sxp blueprint
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the localized title of this sxp blueprint in the language.
	 *
	 * @param title the localized title of this sxp blueprint
	 * @param locale the locale of the language
	 */
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		model.setTitle(title, locale);
	}

	/**
	 * Sets the localized title of this sxp blueprint in the language, and sets the default locale.
	 *
	 * @param title the localized title of this sxp blueprint
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitle(
		String title, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		model.setTitleCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized titles of this sxp blueprint from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this sxp blueprint
	 */
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		model.setTitleMap(titleMap);
	}

	/**
	 * Sets the localized titles of this sxp blueprint from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this sxp blueprint
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitleMap(
		Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {

		model.setTitleMap(titleMap, defaultLocale);
	}

	/**
	 * Sets the user ID of this sxp blueprint.
	 *
	 * @param userId the user ID of this sxp blueprint
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this sxp blueprint.
	 *
	 * @param userName the user name of this sxp blueprint
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this sxp blueprint.
	 *
	 * @param userUuid the user uuid of this sxp blueprint
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this sxp blueprint.
	 *
	 * @param uuid the uuid of this sxp blueprint
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected SXPBlueprintWrapper wrap(SXPBlueprint sxpBlueprint) {
		return new SXPBlueprintWrapper(sxpBlueprint);
	}

}