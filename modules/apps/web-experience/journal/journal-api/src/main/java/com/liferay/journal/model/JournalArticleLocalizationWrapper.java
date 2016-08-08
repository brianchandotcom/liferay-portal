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

package com.liferay.journal.model;

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
 * This class is a wrapper for {@link JournalArticleLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleLocalization
 * @generated
 */
@ProviderType
public class JournalArticleLocalizationWrapper
	implements JournalArticleLocalization,
		ModelWrapper<JournalArticleLocalization> {
	public JournalArticleLocalizationWrapper(
		JournalArticleLocalization journalArticleLocalization) {
		_journalArticleLocalization = journalArticleLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalArticleLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return JournalArticleLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("articleLocalizationId", getArticleLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("articlePK", getArticlePK());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("languageId", getLanguageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long articleLocalizationId = (Long)attributes.get(
				"articleLocalizationId");

		if (articleLocalizationId != null) {
			setArticleLocalizationId(articleLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long articlePK = (Long)attributes.get("articlePK");

		if (articlePK != null) {
			setArticlePK(articlePK);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}
	}

	@Override
	public JournalArticleLocalization toEscapedModel() {
		return new JournalArticleLocalizationWrapper(_journalArticleLocalization.toEscapedModel());
	}

	@Override
	public JournalArticleLocalization toUnescapedModel() {
		return new JournalArticleLocalizationWrapper(_journalArticleLocalization.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _journalArticleLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _journalArticleLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _journalArticleLocalization.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _journalArticleLocalization.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<JournalArticleLocalization> toCacheModel() {
		return _journalArticleLocalization.toCacheModel();
	}

	@Override
	public int compareTo(JournalArticleLocalization journalArticleLocalization) {
		return _journalArticleLocalization.compareTo(journalArticleLocalization);
	}

	@Override
	public int hashCode() {
		return _journalArticleLocalization.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _journalArticleLocalization.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new JournalArticleLocalizationWrapper((JournalArticleLocalization)_journalArticleLocalization.clone());
	}

	/**
	* Returns the description of this journal article localization.
	*
	* @return the description of this journal article localization
	*/
	@Override
	public java.lang.String getDescription() {
		return _journalArticleLocalization.getDescription();
	}

	/**
	* Returns the language ID of this journal article localization.
	*
	* @return the language ID of this journal article localization
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _journalArticleLocalization.getLanguageId();
	}

	/**
	* Returns the title of this journal article localization.
	*
	* @return the title of this journal article localization
	*/
	@Override
	public java.lang.String getTitle() {
		return _journalArticleLocalization.getTitle();
	}

	@Override
	public java.lang.String toString() {
		return _journalArticleLocalization.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _journalArticleLocalization.toXmlString();
	}

	/**
	* Returns the article localization ID of this journal article localization.
	*
	* @return the article localization ID of this journal article localization
	*/
	@Override
	public long getArticleLocalizationId() {
		return _journalArticleLocalization.getArticleLocalizationId();
	}

	/**
	* Returns the article p k of this journal article localization.
	*
	* @return the article p k of this journal article localization
	*/
	@Override
	public long getArticlePK() {
		return _journalArticleLocalization.getArticlePK();
	}

	/**
	* Returns the company ID of this journal article localization.
	*
	* @return the company ID of this journal article localization
	*/
	@Override
	public long getCompanyId() {
		return _journalArticleLocalization.getCompanyId();
	}

	/**
	* Returns the primary key of this journal article localization.
	*
	* @return the primary key of this journal article localization
	*/
	@Override
	public long getPrimaryKey() {
		return _journalArticleLocalization.getPrimaryKey();
	}

	/**
	* Sets the article localization ID of this journal article localization.
	*
	* @param articleLocalizationId the article localization ID of this journal article localization
	*/
	@Override
	public void setArticleLocalizationId(long articleLocalizationId) {
		_journalArticleLocalization.setArticleLocalizationId(articleLocalizationId);
	}

	/**
	* Sets the article p k of this journal article localization.
	*
	* @param articlePK the article p k of this journal article localization
	*/
	@Override
	public void setArticlePK(long articlePK) {
		_journalArticleLocalization.setArticlePK(articlePK);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_journalArticleLocalization.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this journal article localization.
	*
	* @param companyId the company ID of this journal article localization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_journalArticleLocalization.setCompanyId(companyId);
	}

	/**
	* Sets the description of this journal article localization.
	*
	* @param description the description of this journal article localization
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_journalArticleLocalization.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_journalArticleLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_journalArticleLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_journalArticleLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the language ID of this journal article localization.
	*
	* @param languageId the language ID of this journal article localization
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_journalArticleLocalization.setLanguageId(languageId);
	}

	@Override
	public void setNew(boolean n) {
		_journalArticleLocalization.setNew(n);
	}

	/**
	* Sets the primary key of this journal article localization.
	*
	* @param primaryKey the primary key of this journal article localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_journalArticleLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_journalArticleLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the title of this journal article localization.
	*
	* @param title the title of this journal article localization
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_journalArticleLocalization.setTitle(title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalArticleLocalizationWrapper)) {
			return false;
		}

		JournalArticleLocalizationWrapper journalArticleLocalizationWrapper = (JournalArticleLocalizationWrapper)obj;

		if (Objects.equals(_journalArticleLocalization,
					journalArticleLocalizationWrapper._journalArticleLocalization)) {
			return true;
		}

		return false;
	}

	@Override
	public JournalArticleLocalization getWrappedModel() {
		return _journalArticleLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _journalArticleLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _journalArticleLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_journalArticleLocalization.resetOriginalValues();
	}

	private final JournalArticleLocalization _journalArticleLocalization;
}