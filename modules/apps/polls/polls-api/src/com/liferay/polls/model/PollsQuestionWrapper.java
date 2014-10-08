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

package com.liferay.polls.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PollsQuestion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsQuestion
 * @generated
 */
@ProviderType
public class PollsQuestionWrapper implements PollsQuestion,
	ModelWrapper<PollsQuestion> {

	@Override
	public void setGroupPermissions(String... permissions) {
		_pollsQuestion.setGroupPermissions(permissions);
	}

	@Override
	public void setGuestPermissions(String... permissions) {
		_pollsQuestion.setGuestPermissions(permissions);
	}

	@Override
	public void validate() throws PortalException {
		_pollsQuestion.validate();
	}

	public PollsQuestionWrapper(PollsQuestion pollsQuestion) {
		_pollsQuestion = pollsQuestion;
	}

	@Override
	public boolean addChoice(PollsChoice pollsChoice) {
		return _pollsQuestion.addChoice(pollsChoice);
	}

	@Override
	public Class<?> getModelClass() {
		return PollsQuestion.class;
	}

	@Override
	public String getModelClassName() {
		return PollsQuestion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("questionId", getQuestionId());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastVoteDate", getLastVoteDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		Long questionId = (Long)attributes.get("questionId");

		if (questionId != null) {
			setQuestionId(questionId);
		}

		Long groupId = (Long)attributes.get("groupId");

		Long companyId = (Long)attributes.get("companyId");

		Long userId = (Long)attributes.get("userId");

		String userName = (String)attributes.get("userName");

		Date createDate = (Date)attributes.get("createDate");

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date lastVoteDate = (Date)attributes.get("lastVoteDate");

		if (lastVoteDate != null) {
			setLastVoteDate(lastVoteDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new PollsQuestionWrapper((PollsQuestion)_pollsQuestion.clone());
	}

	@Override
	public int compareTo(com.liferay.polls.model.PollsQuestion pollsQuestion) {
		return _pollsQuestion.compareTo(pollsQuestion);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _pollsQuestion.getAvailableLanguageIds();
	}

	@Override
	public java.util.List<com.liferay.polls.model.PollsChoice> getChoices() {
		return _pollsQuestion.getChoices();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _pollsQuestion.getDefaultLanguageId();
	}

	/**
	* Returns the description of this polls question.
	*
	* @return the description of this polls question
	*/
	@Override
	public java.lang.String getDescription() {
		return _pollsQuestion.getDescription();
	}

	/**
	* Returns the localized description of this polls question in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this polls question
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _pollsQuestion.getDescription(languageId);
	}

	/**
	* Returns the localized description of this polls question in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this polls question
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _pollsQuestion.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this polls question in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this polls question
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _pollsQuestion.getDescription(locale);
	}

	/**
	* Returns the localized description of this polls question in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this polls question. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _pollsQuestion.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _pollsQuestion.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _pollsQuestion.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this polls question.
	*
	* @return the locales and localized descriptions of this polls question
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _pollsQuestion.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pollsQuestion.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this polls question.
	*
	* @return the expiration date of this polls question
	*/
	@Override
	public java.util.Date getExpirationDate() {
		return _pollsQuestion.getExpirationDate();
	}

	/**
	* Returns the last vote date of this polls question.
	*
	* @return the last vote date of this polls question
	*/
	@Override
	public java.util.Date getLastVoteDate() {
		return _pollsQuestion.getLastVoteDate();
	}

	/**
	* Returns the primary key of this polls question.
	*
	* @return the primary key of this polls question
	*/
	@Override
	public long getPrimaryKey() {
		return _pollsQuestion.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _pollsQuestion.getPrimaryKeyObj();
	}

	/**
	* Returns the question ID of this polls question.
	*
	* @return the question ID of this polls question
	*/
	@Override
	public long getQuestionId() {
		return _pollsQuestion.getQuestionId();
	}

	/**
	* Returns the title of this polls question.
	*
	* @return the title of this polls question
	*/
	@Override
	public java.lang.String getTitle() {
		return _pollsQuestion.getTitle();
	}

	/**
	* Returns the localized title of this polls question in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this polls question
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _pollsQuestion.getTitle(languageId);
	}

	/**
	* Returns the localized title of this polls question in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this polls question
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _pollsQuestion.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this polls question in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this polls question
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _pollsQuestion.getTitle(locale);
	}

	/**
	* Returns the localized title of this polls question in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this polls question. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _pollsQuestion.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _pollsQuestion.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _pollsQuestion.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this polls question.
	*
	* @return the locales and localized titles of this polls question
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _pollsQuestion.getTitleMap();
	}

	@Override
	public java.util.List<com.liferay.polls.model.PollsVote> getVotes() {
		return _pollsQuestion.getVotes();
	}

	@Override
	public java.util.List<com.liferay.polls.model.PollsVote> getVotes(
		int start, int end) {
		return _pollsQuestion.getVotes(start, end);
	}

	@Override
	public int getVotesCount() {
		return _pollsQuestion.getVotesCount();
	}

	@Override
	public int hashCode() {
		return _pollsQuestion.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _pollsQuestion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _pollsQuestion.isEscapedModel();
	}

	@Override
	public boolean isExpired() {
		return _pollsQuestion.isExpired();
	}

	@Override
	public boolean isExpired(
		com.liferay.portal.service.ServiceContext serviceContext,
		java.util.Date defaultCreateDate) {
		return _pollsQuestion.isExpired(serviceContext, defaultCreateDate);
	}

	@Override
	public boolean isNew() {
		return _pollsQuestion.isNew();
	}

	@Override
	public void persist() {
		_pollsQuestion.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_pollsQuestion.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_pollsQuestion.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_pollsQuestion.setCachedModel(cachedModel);
	}

	/**
	* Sets the description of this polls question.
	*
	* @param description the description of this polls question
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_pollsQuestion.setDescription(description);
	}

	/**
	* Sets the localized description of this polls question in the language.
	*
	* @param description the localized description of this polls question
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_pollsQuestion.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this polls question in the language, and sets the default locale.
	*
	* @param description the localized description of this polls question
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_pollsQuestion.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_pollsQuestion.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this polls question from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this polls question
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_pollsQuestion.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this polls question from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this polls question
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_pollsQuestion.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_pollsQuestion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_pollsQuestion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pollsQuestion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this polls question.
	*
	* @param expirationDate the expiration date of this polls question
	*/
	@Override
	public void setExpirationDate(java.util.Date expirationDate) {
		_pollsQuestion.setExpirationDate(expirationDate);
	}

	/**
	* Sets the last vote date of this polls question.
	*
	* @param lastVoteDate the last vote date of this polls question
	*/
	@Override
	public void setLastVoteDate(java.util.Date lastVoteDate) {
		_pollsQuestion.setLastVoteDate(lastVoteDate);
	}

	@Override
	public void setNew(boolean n) {
		_pollsQuestion.setNew(n);
	}

	/**
	* Sets the primary key of this polls question.
	*
	* @param primaryKey the primary key of this polls question
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_pollsQuestion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_pollsQuestion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the question ID of this polls question.
	*
	* @param questionId the question ID of this polls question
	*/
	@Override
	public void setQuestionId(long questionId) {
		_pollsQuestion.setQuestionId(questionId);
	}

	/**
	* Sets the title of this polls question.
	*
	* @param title the title of this polls question
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_pollsQuestion.setTitle(title);
	}

	/**
	* Sets the localized title of this polls question in the language.
	*
	* @param title the localized title of this polls question
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_pollsQuestion.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this polls question in the language, and sets the default locale.
	*
	* @param title the localized title of this polls question
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_pollsQuestion.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_pollsQuestion.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this polls question from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this polls question
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_pollsQuestion.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this polls question from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this polls question
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_pollsQuestion.setTitleMap(titleMap, defaultLocale);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.polls.model.PollsQuestion> toCacheModel() {
		return _pollsQuestion.toCacheModel();
	}

	@Override
	public com.liferay.polls.model.PollsQuestion toEscapedModel() {
		return new PollsQuestionWrapper(_pollsQuestion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _pollsQuestion.toString();
	}

	@Override
	public com.liferay.polls.model.PollsQuestion toUnescapedModel() {
		return new PollsQuestionWrapper(_pollsQuestion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _pollsQuestion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PollsQuestionWrapper)) {
			return false;
		}

		PollsQuestionWrapper pollsQuestionWrapper = (PollsQuestionWrapper)obj;

		if (Validator.equals(_pollsQuestion, pollsQuestionWrapper._pollsQuestion)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public PollsQuestion getWrappedPollsQuestion() {
		return _pollsQuestion;
	}

	@Override
	public PollsQuestion getWrappedModel() {
		return _pollsQuestion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _pollsQuestion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _pollsQuestion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_pollsQuestion.resetOriginalValues();
	}

	private final PollsQuestion _pollsQuestion;

	@Override
	public PollsChoice createChoice() {
		return _pollsQuestion.createChoice();
	}
}